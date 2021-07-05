package com.github.alenfive.rocketapi.extend;

import com.github.alenfive.rocketapi.entity.ApiExample;
import com.github.alenfive.rocketapi.entity.ApiInfo;
import com.github.alenfive.rocketapi.entity.vo.DocApi;
import com.github.alenfive.rocketapi.entity.vo.DocsInfo;
import com.github.alenfive.rocketapi.json.JsonSchema;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.github.alenfive.rocketapi.entity.ApiDirectory;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.apache.commons.httpclient.NameValuePair;

/**
 * 默认API信息接口同步，
 */
@Slf4j
@Component
public class DefaultApiDocSync implements IApiDocSync {
    @Value("${app.doctoken}")
    private String doctoken;
    @Value("${app.docurl}")
    private String docurl;
    @Override
    public String sync(DocsInfo docsInfo) throws Exception {
        try{
            JSONObject project= sendGet(docurl +"/api/project/get");
            if (project==null){
                return "Error";
            }
            if (project.getInt("errcode")>0){
                return project.getString("errmsg");
            }
            int projectId= project.getJSONObject("data").getInt("_id");
            JSONObject CatMenu= sendGet(docurl +"/api/interface/getCatMenu");
            List<ApiDirectory> directoryList=docsInfo.getDirectoryList();
            if (directoryList==null){
                return "null";
            }
            for (int i = 0; i < directoryList.size(); i++){
                ApiDirectory dir= directoryList.get(i);
                JSONArray menudata=CatMenu.getJSONArray("data");
                JSONObject menucat=null;
                for (int iii = 0; iii < menudata.length(); iii++){
                    JSONObject _menucat=menudata.getJSONObject(iii);
                    if (_menucat.getString("name").equals(dir.getName())){
                        menucat=_menucat;
                        break;
                    }
                }
                int catId=0;
                if (menucat!=null){
                    catId=menucat.getInt("_id");
                }else {
                    NameValuePair[] data = {
                            new NameValuePair("name",dir.getName()),
                            new NameValuePair("project_id",String.valueOf(projectId)),
                            new NameValuePair("token", doctoken)
                    };
                    JSONObject cat= sendPost(docurl +"/api/interface/add_cat",data);
                    if (cat==null){
                        return "Error";
                    }
                    if (cat.getInt("errcode")>0){
                        return cat.getString("errmsg");
                    }
                    catId= cat.getJSONObject("data").getInt("_id");
                }
                List<DocApi> docApiList= docsInfo.getDocApiList();
                if (docApiList==null){
                    continue;
                }
                for (int ii=0;ii < docApiList.size();ii++){
                    DocApi doc=docApiList.get(ii);
                    ApiInfo apiInfo =doc.getApiInfo();
                    if (apiInfo==null){
                        continue;
                    }
                    if (dir.id.equals(apiInfo.getDirectoryId())){
                        JSONObject json = new JSONObject();
                        json.put("token", doctoken);
                        json.put("title",apiInfo.getName());
                        json.put("path",apiInfo.getFullPath());
                        json.put("catid",catId);
                        json.put("res_body_type","json");
                        json.put("res_body_is_json_schema", true);
                        ApiExample apiExample =doc.getApiExample();
                        if (apiExample!=null){
                            try{
                                String jsonSchemaString =new JsonSchema().fromString(apiExample.getResponseBody());
                                json.put("res_body",jsonSchemaString);
                            }
                            catch (Exception ex){

                            }
                            if (apiExample.getRequestHeader()!=null){
                                JSONObject d=new JSONObject(apiExample.getRequestHeader());
                                Iterator x = d.keys();
                                JSONArray req_headers = new JSONArray();
                                while (x.hasNext()){
                                    String key = (String) x.next();
                                    JSONObject js=new JSONObject();
                                    js.put("name",key);
                                    js.put("example",d.getString(key));
                                    js.put("value",d.getString(key));
                                    req_headers.put(js);
                                }
                                json.put("req_headers",req_headers);
                            }
                            JSONObject d= apiExample.GetQuery();
                            if (d!=null){
                                Iterator x = d.keys();
                                JSONArray req_query = new JSONArray();
                                while (x.hasNext()){
                                    String key = (String) x.next();
                                    JSONObject js=new JSONObject();
                                    js.put("name",key);
                                    js.put("example",d.getString(key));
                                    req_query.put(js);
                                }
                                json.put("req_query",req_query);
                            }
                        }
                        json.put("method",apiInfo.getMethod());
                        JSONObject save= sendPost(docurl +"/api/interface/save",json);
                    }
                }
            }
            return "Successful push";
        }
        catch (Exception ex){
            return "Error:"+ex;
        }
    }

    public JSONObject sendGet(String url) throws IOException {
        BufferedReader in = null;
        HttpURLConnection con = null;
        try {
            URL obj = new URL(url+"?token="+ doctoken);
            con = (HttpURLConnection) obj.openConnection();
            //默认值我GET
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("charset", "utf-8");
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),"UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //打印结果
            JSONObject json = new JSONObject(response.toString());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
            in.close();
        }
        return null;
    }
    private JSONObject sendPost(String url,NameValuePair[] parameters) throws Exception {
        PostMethod postMethod = null;
        org.apache.commons.httpclient.HttpClient httpClient = null;
        try {
            postMethod = new PostMethod(url) ;
            //添加请求头
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8") ;
            postMethod.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            postMethod.setRequestBody(parameters);
            httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            String result = postMethod.getResponseBodyAsString() ;
            result=java.net.URLDecoder.decode(result,"utf-8");
            JSONObject json = new JSONObject(result);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod= null;
            httpClient= null;
        }
        return null;
    }
    private JSONObject sendPost(String url,JSONObject json) throws Exception {
        PostMethod postMethod = null;
        org.apache.commons.httpclient.HttpClient httpClient = null;
        try {
            postMethod = new PostMethod(url) ;
            //添加请求头
            postMethod.setRequestHeader("Content-Type", "application/json;charset=utf-8") ;
            postMethod.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            RequestEntity entity = new StringRequestEntity(json.toString());
            postMethod.setRequestEntity(entity);
            httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            String result = postMethod.getResponseBodyAsString() ;
            result=java.net.URLDecoder.decode(result,"utf-8");
            JSONObject _json = new JSONObject(result);
            return _json;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod= null;
            httpClient= null;
        }
        return null;
    }
}
