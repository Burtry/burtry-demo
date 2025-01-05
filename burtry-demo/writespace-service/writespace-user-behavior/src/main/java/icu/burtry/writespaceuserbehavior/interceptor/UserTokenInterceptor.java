package icu.burtry.writespaceuserbehavior.interceptor;

import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespaceutils.thread.AdminThreadLocalUtil;
import icu.burtry.writespaceutils.thread.UserThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserTokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("userId");

        if (userId != null) {
            User user = new User();
            user.setId(Long.valueOf(userId));
            UserThreadLocalUtil.setUserThreadLocal(user);

        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        AdminThreadLocalUtil.clear();
    }
}