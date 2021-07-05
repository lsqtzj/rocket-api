package com.github.alenfive.rocketapi.entity;

import com.github.alenfive.rocketapi.annotation.ApiTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 模拟参数实体对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiTable("api_example")
public class ApiExample extends ApiEntity{
    private String apiInfoId;
    private String url;
    private String method;
    private String requestHeader;
    private String requestBody;
    private String responseHeader;
    private String responseBody;
    private String status;
    private Integer elapsedTime;
    private String editor;
    private String options;
    private String createTime;
    /**
     * 解析url
     *
     */
    public JSONObject GetQuery() throws JSONException {
        if (url == null) {
            return null;
        }
        url = url.trim();
        if (url.equals("")) {
            return null;
        }
        String[] urlParts = url.split("\\?");
        //没有参数
        if (urlParts.length == 1) {
            return null;
        }
        //有参数
        String[] params = urlParts[1].split("&");
        JSONObject json = new JSONObject();
        for (String param : params) {
            String[] keyValue = param.split("=");
            json.put(keyValue[0], keyValue[1]);
        }
        return json;
    }
}
