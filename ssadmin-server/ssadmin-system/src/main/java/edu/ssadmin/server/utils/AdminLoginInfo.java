package edu.ssadmin.server.utils;

import edu.ssadmin.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class AdminLoginInfo {
    public static final String AUTHORIZATION_BEARER = "Bearer";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public  String getLoginUsername() {
        String token = getRequestToken();
        if (token != null && token.startsWith(AUTHORIZATION_BEARER)) {
            token = token.substring(AUTHORIZATION_BEARER.length()).trim();
        }
        System.out.println("token");
        System.out.println(token);
        return jwtTokenUtil.getUsernameFromToken(token);
    }

    private  String getRequestToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        return token;
    }


}
