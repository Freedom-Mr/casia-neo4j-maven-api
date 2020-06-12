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
    public static DruidDataSource initDataPool(Map<String,Object> parms){
        DruidDataSource dataSource = new DruidDataSource();
        try {
            Properties newProperties = new Properties();
            parms.forEach((k,v)->{
                newProperties.put(PROPERTY_PREFIX+DOT+(k.equals(BLOT)?URL:k),k.equals(BLOT)?JDBC_BOLT_COLON_SLASH+v:v);
            });
            dataSource.setConnectProperties(newProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
    public static DruidDataSource initDataPool(_Entity_Driver _entity_Driver ){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl( _entity_Driver.getJdbcBolt() );
        dataSource.setUsername(_entity_Driver.getUsername());
        dataSource.setPassword(_entity_Driver.getPassword());
//        dataSource.setMinIdle(5);
        dataSource.setMaxWait(30000);
        return dataSource;
    }
}
