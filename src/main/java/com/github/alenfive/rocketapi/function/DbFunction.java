package com.github.alenfive.rocketapi.function;

import com.github.alenfive.rocketapi.datasource.DataSourceManager;
import com.github.alenfive.rocketapi.entity.vo.Page;
import com.github.alenfive.rocketapi.extend.ApiInfoContent;
import com.github.alenfive.rocketapi.extend.IApiPager;
import com.github.alenfive.rocketapi.extend.IDBCache;
import com.github.alenfive.rocketapi.extend.ISQLInterceptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作函数
 */
@SuppressWarnings("DuplicatedCode")
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class DbFunction implements IFunction {

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private ApiInfoContent apiInfoContent;

    @Autowired
    private IApiPager apiPager;

    @Autowired
    private UtilsFunction utilsFunction;

    @Autowired
    private ISQLInterceptor sqlInterceptor;

    @Autowired
    private IDBCache dbCache;

    private String cacheKey;
    private Long cacheTime;

    public DbFunction cache(String cacheKey,Long cacheTime){
        return new DbFunction(dataSourceManager,apiInfoContent,apiPager,utilsFunction,sqlInterceptor,dbCache,cacheKey,cacheTime);
    }

    public void cacheClear(String cacheKey){
        dbCache.remove(cacheKey);
    }

    @Override
    public String getVarName() {
        return "db";
    }

    private String parseSql(String script){
        if (script.startsWith("sql")){
            script = script.substring(3);
        }
        if (script.startsWith("\n")){
            script = script.substring(1);
        }
        if (script.endsWith("\n")){
            script = script.substring(0,script.length()-1);
        }
        return script;
    }

    @Deprecated
    public Long count(String script,String dataSource,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        List<Map<String,Object>> list = find(script,dataSource,params);
        if (CollectionUtils.isEmpty(list))return 0L;
        if (list.size()>1){
            return Long.valueOf(list.size());
        }
        Object[] fieldValues = list.get(0).values().toArray();
        if (fieldValues.length>1 || !(fieldValues[0] instanceof Number)){
            return 1L;
        }

        return Long.valueOf(fieldValues[0].toString());
    }

    @Deprecated
    public Map<String,Object> findOne(String script,String dataSource,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        List<Map<String,Object>> list = find(script,dataSource,params);
        if (list.size() == 0)return null;
        return list.get(0);
    }

    @Deprecated
    public List<Map<String,Object>> find(String script,String dataSource,Map<String,Object> params) throws Exception {

        //获取缓存对象
        if (this.cacheKey != null){
            Object value = dbCache.get(this.cacheKey);
            if (value != null){
                return (List<Map<String, Object>>) value;
            }
        }

        script = parseSql(script);
        StringBuilder sbScript = new StringBuilder(sqlInterceptor.before(script));
        List<Map<String,Object>> result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = dataSourceManager.find(sbScript,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource,params);
        }finally {
            long diff = System.currentTimeMillis() - startTime;
            if (apiInfoContent.getIsDebug()){
                apiInfoContent.putLog(String.format("Elapsed Time:%sms , execute script: %s",diff,sbScript));
            }
            log.debug("Elapsed Time:{}ms , execute script: {}",diff,sbScript);
            sqlInterceptor.after(sbScript.toString());
        }

        //设置缓存对象
        if (this.cacheKey != null){
            dbCache.set(this.cacheKey,result,cacheTime);
        }
        return result;
    }

    @Deprecated
    public Object insert(String script,String dataSource,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        StringBuilder sbScript = new StringBuilder(sqlInterceptor.before(script));
        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = dataSourceManager.insert(sbScript,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource,params);
        }finally {
            long diff = System.currentTimeMillis() - startTime;
            if (apiInfoContent.getIsDebug()){
                apiInfoContent.putLog(String.format("Elapsed Time:%sms , execute script: %s",diff,sbScript));
            }
            log.debug("Elapsed Time:{}ms , execute script: {}",diff,sbScript);
            sqlInterceptor.after(sbScript.toString());
        }
        return result;
    }

    @Deprecated
    public Object remove(String script,String dataSource,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        StringBuilder sbScript = new StringBuilder(sqlInterceptor.before(script));
        Object result =  null;
        long startTime = System.currentTimeMillis();
        try {
            result = dataSourceManager.remove(sbScript,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource,params);
        }finally {
            long diff = System.currentTimeMillis() - startTime;
            if (apiInfoContent.getIsDebug()){
                apiInfoContent.putLog(String.format("Elapsed Time:%sms , execute script: %s",diff,sbScript));
            }
            log.debug("Elapsed Time:{}ms , execute script: {}",diff,sbScript);
            sqlInterceptor.after(sbScript.toString());
        }
        return result;
    }

    @Deprecated
    public Long update(String script,String dataSource,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        StringBuilder sbScript = new StringBuilder(sqlInterceptor.before(script));
        Long result =  null;
        long startTime = System.currentTimeMillis();
        try {
            result = dataSourceManager.update(sbScript,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource,params);
        }finally {
            long diff = System.currentTimeMillis() - startTime;
            if (apiInfoContent.getIsDebug()){
                apiInfoContent.putLog(String.format("Elapsed Time:%sms , execute script: %s",diff,sbScript));
            }
            log.debug("Elapsed Time:{}ms , execute script: {}",diff,sbScript);
            sqlInterceptor.after(sbScript.toString());
        }
        return result;
    }

    @Deprecated
    public Object pager(String script,String dataSource,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        Page page = Page.builder()
                .pageNo(Integer.valueOf(utilsFunction.val(apiPager.getPageNoVarName()).toString()))
                .pageSize(Integer.valueOf(utilsFunction.val(apiPager.getPageSizeVarName()).toString()))
                .build();
        String totalSql = dataSourceManager.buildCountScript(script,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource,params,apiPager,page);
        Long total = this.count(totalSql,dataSource,params);
        List<Map<String,Object>> data = null;
        if (total > 0){
            String pageSql = dataSourceManager.buildPageScript(script,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams(),dataSource,params,apiPager,page);
            data = this.find(pageSql,dataSource,params);
        }else{
            data = Collections.emptyList();
        }
        return apiPager.buildPager(total,data,apiInfoContent.getApiInfo(),apiInfoContent.getApiParams());
    }

    /*重载 script*/
    public Object pager(String script) throws Exception {
        script = parseSql(script);
        return this.pager(script,null,null);
    }

    public Long count(String script) throws Exception {
        script = parseSql(script);
        return this.count(script,null,null);
    }

    public Map<String,Object> findOne(String script) throws Exception {
        script = parseSql(script);
        return this.findOne(script,null,null);
    }

    public List<Map<String,Object>> find(String script) throws Exception {
        script = parseSql(script);
        return this.find(script,null,null);
    }

    public Object insert(String script) throws Exception {
        script = parseSql(script);
        return this.insert(script,null,null);
    }

    public Object remove(String script) throws Exception {
        script = parseSql(script);
        return this.remove(script,null,null);
    }

    public Long update(String script) throws Exception {
        script = parseSql(script);
        return this.update(script,null,null);
    }


    /*重载 datasource*/
    @Deprecated
    public Object pager(String script,String datasource) throws Exception {
        script = parseSql(script);
        return this.pager(script,datasource,null);
    }

    @Deprecated
    public Long count(String script,String datasource) throws Exception {
        script = parseSql(script);
        return this.count(script,datasource,null);
    }

    @Deprecated
    public Map<String,Object> findOne(String script,String datasource) throws Exception {
        script = parseSql(script);
        return this.findOne(script,datasource,null);
    }

    @Deprecated
    public List<Map<String,Object>> find(String script,String datasource) throws Exception {
        script = parseSql(script);
        return this.find(script,datasource,null);
    }

    @Deprecated
    public Object insert(String script,String datasource) throws Exception {
        script = parseSql(script);
        return this.insert(script,datasource,null);
    }

    @Deprecated
    public Object remove(String script,String datasource) throws Exception {
        script = parseSql(script);
        return this.remove(script,datasource,null);
    }

    @Deprecated
    public Long update(String script,String datasource) throws Exception {
        script = parseSql(script);
        return this.update(script,datasource,null);
    }

    /*重载 params*/
    public Object pager(String script,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        return this.pager(script,null,params);
    }

    public Long count(String script,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        return this.count(script,null,params);
    }

    public Map<String,Object> findOne(String script,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        return this.findOne(script,null,params);
    }

    public List<Map<String,Object>> find(String script,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        return this.find(script,null,params);
    }

    public Object insert(String script,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        return this.insert(script,null,params);
    }

    public Object remove(String script,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        return this.remove(script,null,params);
    }

    public Long update(String script,Map<String,Object> params) throws Exception {
        script = parseSql(script);
        return this.update(script,null,params);
    }
}
