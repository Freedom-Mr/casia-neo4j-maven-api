package casia.isiteam.api.neo4j.datasource.manage;

import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.neo4j.datasource.Neo4jDbSource;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.regex.CasiaRegexUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;

/**
 * ClassName: DataPoolManage
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/30
 * Email: zhiyou_wang@foxmail.com
 */
public class DataPoolManage extends BasicParms {
    private static Logger logger = LoggerFactory.getLogger( DataPoolManage.class);
    public static List<DruidDataSource> initDataPool(Map<String,Object> parms){
        List<DruidDataSource> dataSources = new ArrayList<>();
        try {
            if( parms.containsKey(BLOT) && Validator.check(parms.get(BLOT)) ){
                String[] blots = parms.get(BLOT).toString().split(COMMA_OR_SEMICOLON);
                for(String blot:blots){
                    Properties newProperties = new Properties();
                    parms.forEach((k,v)->{
                        newProperties.put(PROPERTY_PREFIX+DOT+(k.equals(BLOT)?URL:k),k.equals(BLOT)?JDBC_BOLT_COLON_SLASH+blot:v);
                    });
                    DruidDataSource druidDataSource = new DruidDataSource();
                    druidDataSource.setConnectProperties(newProperties);
                    dataSources.add(druidDataSource);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSources;
    }
    public static List<DruidDataSource> initDataPool(_Entity_Driver _entity_Driver ){
        List<DruidDataSource> dataSources = new ArrayList<>();
        _entity_Driver.getJdbcBolt().forEach(s->{
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(JDBC_DRIVER);
            dataSource.setUrl(s);
            dataSource.setUsername(_entity_Driver.getUsername());
            dataSource.setPassword(_entity_Driver.getPassword());
//        dataSource.setMinIdle(5);
            dataSource.setMaxWait(30000);
            dataSources.add(dataSource);
        });
        return dataSources;
    }
}
