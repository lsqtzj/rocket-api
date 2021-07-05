package com.github.alenfive.rocketapi.extend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.alenfive.rocketapi.entity.vo.cPasswordVo;

/**
 * 用户授权接口
 */
public interface IUserAuthorization {
    public String validate(String username, String password);
    public String validateToken(String username, String Token);
    public String newToken(String username, String Token);

    public String  changepassword(cPasswordVo loginVo);
}
