package icu.burtry.writespaceadmin.interceptor;

import cn.hutool.core.util.StrUtil;
import icu.burtry.writespacemodel.entity.Admin;
import icu.burtry.writespaceutils.thread.AdminThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminTokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String adminId = request.getHeader("adminId");

        if (adminId != null) {
            Admin admin = new Admin();
            admin.setId(Long.valueOf(adminId));
            AdminThreadLocalUtil.setAdminThreadLocal(admin);

        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        AdminThreadLocalUtil.clear();
    }
}