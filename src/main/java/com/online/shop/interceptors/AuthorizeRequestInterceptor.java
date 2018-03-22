package com.online.shop.interceptors;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizeRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("pre -> " + response.getStatus());

        if(response.getStatus() == HttpServletResponse.SC_FORBIDDEN){
            response.getWriter().write("You don't have permission for this action.");
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }


        return true;
    }
}
