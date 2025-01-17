package com.github.alenfive.rocketapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alenfive.rocketapi.datasource.DataSourceManager;
import com.github.alenfive.rocketapi.entity.SysUser;
import com.github.alenfive.rocketapi.entity.vo.LoginVo;
import com.github.alenfive.rocketapi.extend.IUserAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

/**
 * 登录工具类
 */
@Component
public class LoginService {

    @Autowired
    DataSourceManager dataSourceManager;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IUserAuthorization userAuthorization;

    private final static String rocketUserToken = "rocket-user-token";

    /**
     * 根据token 验证登录用户
     * @param request
     */
    public String getUser(HttpServletRequest request){
        String token = request.getHeader(rocketUserToken);
        if (StringUtils.isEmpty(token)){
            return null;
        }
        try {
            SysUser qsysUser = dataSourceManager.getStoreApiDataSource().
                    listByEntity(SysUser.builder()
                            .token(token)
                            .build())
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (qsysUser!=null){
                return qsysUser.getName();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成token
     */
    public String getToken(LoginVo loginVo){
        try {
            String user = userAuthorization.validate(loginVo.getUsername(), loginVo.getPassword());
            if (StringUtils.isEmpty(user)){
                return null;
            }
            SysUser qsysUser = dataSourceManager.getStoreApiDataSource().
                    listByEntity(SysUser.builder()
                            .name(loginVo.getUsername())
                            .password(loginVo.getPassword())
                            .build())
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (qsysUser!=null){
                return qsysUser.getToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
