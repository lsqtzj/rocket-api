package com.github.alenfive.rocketapi.entity;

import com.github.alenfive.rocketapi.annotation.ApiTable;
import com.github.alenfive.rocketapi.annotation.ApiUpdateField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiTable("sys_user")
public class SysUser extends ApiEntity {
    private String name;
    private String password;
    private String token;
}
