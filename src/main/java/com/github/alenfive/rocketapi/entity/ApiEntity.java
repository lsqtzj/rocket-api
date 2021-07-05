package com.github.alenfive.rocketapi.entity;

import com.github.alenfive.rocketapi.annotation.ApiId;
import lombok.Data;

@Data
public abstract class ApiEntity {
    /**
     * 唯一标识符
     */
    @ApiId
    public String id;

}
