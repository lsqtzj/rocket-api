package com.github.alenfive.rocketapi.extend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.alenfive.rocketapi.config.QLRequestMappingFactory;
import com.github.alenfive.rocketapi.datasource.DataSourceDialect;
import com.github.alenfive.rocketapi.datasource.DataSourceManager;
import com.github.alenfive.rocketapi.entity.ApiConfig;
import com.github.alenfive.rocketapi.entity.ApiInfo;
import com.github.alenfive.rocketapi.entity.SysUser;
import com.github.alenfive.rocketapi.entity.vo.LoginVo;
import com.github.alenfive.rocketapi.entity.vo.cPasswordVo;
import com.github.alenfive.rocketapi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Random;

/**
 * 默认用户授权实现类
 */

@Component
public class DefaultUserAuthorization implements IUserAuthorization {
    @Autowired
    DataSourceManager dataSourceManager;
    @Override
    public String validate(String username, String password) {
        try {
            SysUser qsysUser = dataSourceManager.getStoreApiDataSource().
                    listByEntity(SysUser.builder()
                            .name(username)
                            .password(password)
                            .build())
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (qsysUser!=null) {
                return qsysUser.getName();
            }
            return null;
        }catch (Exception ex){
            return null;
        }
    }
    @Override
    public String validateToken(String username, String token)  {
        try {
            SysUser qsysUser = dataSourceManager.getStoreApiDataSource().
                    listByEntity(SysUser.builder()
                            .name(username)
                            .token(token)
                            .build())
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (qsysUser!=null) {
                return qsysUser.getName();
            }
            return null;
        }catch (Exception ex){
            return null;
        }
    }
    @Override
    public String changepassword(cPasswordVo cpvo){
        try {
            SysUser qsysUser = dataSourceManager.getStoreApiDataSource().
                    listByEntity(SysUser.builder()
                            .name(cpvo.getUsername())
                            .password(cpvo.getOld_password())
                            .build())
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (qsysUser!=null) {
                qsysUser.setPassword(cpvo.getNew_password());
                dataSourceManager.getStoreApiDataSource().updateEntityById(qsysUser);
                return "Ok";
            }
            return null;
        }catch (Exception ex){
            return null;
        }
    }
    @Override
    public String newToken(String username, String token){
        try {
            SysUser qsysUser = dataSourceManager.getStoreApiDataSource().
                    listByEntity(SysUser.builder()
                            .name(username)
                            .token(token)
                            .build())
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (qsysUser!=null) {
                token = makeToken();
                qsysUser.setToken(token);
                dataSourceManager.getStoreApiDataSource().updateEntityById(qsysUser);
                return token;
            }
            return null;
        }catch (Exception ex){
            return null;
        }
    }
    public String makeToken(){
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        //数据指纹   128位长   16个字节  md5
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(token.getBytes());
            //base64编码--任意二进制编码明文字符   adfsdfsdfsf
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
