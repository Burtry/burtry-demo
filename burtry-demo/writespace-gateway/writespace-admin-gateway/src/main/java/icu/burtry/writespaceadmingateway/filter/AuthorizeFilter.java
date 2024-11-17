package icu.burtry.writespaceadmingateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import icu.burtry.writespaceadmingateway.result.Result;
import icu.burtry.writespaceadmingateway.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 放行登录和验证码相关的路径
        if (request.getURI().getPath().contains("/login") || request.getURI().getPath().contains("/code")) {
            return chain.filter(exchange);
        }

        // 获取 token
        String token = request.getHeaders().getFirst("token");

        // 如果 token 为空，返回统一错误结果
        if (StringUtils.isBlank(token)) {
            return buildErrorResponse(response, Result.error("Token为空"));
        }

        // 判断 token 是否有效
        try {
            Claims claimsBody = JwtUtil.getClaimsBody(token);
            int result = JwtUtil.verifyToken(claimsBody);

            if (result == 1 || result == 2) {
                return buildErrorResponse(response, Result.error("Token 已过期"));
            }

            // 获取用户信息并存储到 header 中
            Object adminId = claimsBody.get("id");
            ServerHttpRequest serverHttpRequest = request.mutate()
                    .headers(httpHeaders -> httpHeaders.add("adminId", adminId + ""))
                    .build();
            exchange.mutate().request(serverHttpRequest);
        } catch (Exception e) {
            log.error("Token validation error: {}", e.getMessage());
            return buildErrorResponse(response, Result.error("Token 认证失败"));
        }

        // 放行
        return chain.filter(exchange);
    }

    /**
     * 构建统一的错误响应
     */
    private Mono<Void> buildErrorResponse(ServerHttpResponse response, Result<?> result) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            String json = objectMapper.writeValueAsString(result);
            DataBuffer buffer = response.bufferFactory().wrap(json.getBytes());
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error("Error serializing response: {}", e.getMessage());
            return response.setComplete();
        }
    }
}
