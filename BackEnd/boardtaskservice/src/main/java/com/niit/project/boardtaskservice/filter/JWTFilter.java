package com.niit.project.boardtaskservice.filter;

import com.niit.project.boardtaskservice.domain.UserDetails;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JWTFilter extends GenericFilterBean {
    private String secretKey;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        String authHeader=httpServletRequest.getHeader("authorization");
        if(authHeader==null){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServletOutputStream outputStream=httpServletResponse.getOutputStream();
            outputStream.println("Token Missing");
        }
        else{
            String jwtToken=authHeader.substring(7);
            String userDetail= Jwts.parser().setSigningKey("SecretKey").parseClaimsJws(jwtToken).getBody().getSubject();
            httpServletRequest.setAttribute("emailId",userDetail);
            filterChain.doFilter(servletRequest,servletResponse);

        }

    }

}
