package com.pnctraining.configuration;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setHeader("Access-Control-Allow-Method","GET,POST,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Max-Age","3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "token");

        if("OPTIONS".equals((httpServletRequest.getMethod()))){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }else{
            chain.doFilter(httpServletRequest,httpServletResponse);
        }

    }

}
