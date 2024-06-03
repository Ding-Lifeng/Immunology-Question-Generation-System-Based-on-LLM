package edu.ssadmin.security.service;


import edu.ssadmin.common.pojo.AccessToken;

public interface TokenService {
    AccessToken createAccessToken(String username,Long userId);
}
