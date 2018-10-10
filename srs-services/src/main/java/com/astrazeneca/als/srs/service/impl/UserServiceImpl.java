package com.astrazeneca.als.srs.service.impl;

import com.astrazeneca.als.srs.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

/**
 * Created by krvh271 on 5/2/17.
 */
@Service
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String getUserPrid(){
        LOGGER.info("GetUserDetails information");
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    @Override
    public String getUserName(){
        LOGGER.info("GetUserDetails information");
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        Map detailsMap = (HashMap) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
        return detailsMap.get("displayName").toString();
    }

    @Override
    public boolean isAdmin() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> simpleGrantedAuthorities= (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        return  simpleGrantedAuthorities.stream().filter(authorities->authorities.getAuthority().equals("RAT-Admin")).collect(Collectors.toList()).size()>0;
    }
}
