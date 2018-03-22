package com.online.shop.config.handlers;
import com.online.shop.exception.RequestException;
import com.online.shop.util.ResponseMessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
        //resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ResponseMessageConstants.FORBIDDEN);
        throw new RequestException(ResponseMessageConstants.FORBIDDEN, HttpStatus.FORBIDDEN);
    }


}
