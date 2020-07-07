package casia.isiteam.api.neo4j.datasource;

import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.neo4j.common.manage.status.Neo4jDriverStatus;
import casia.isiteam.api.neo4j.datasource.manage.DataPoolManage;
import casia.isiteam.api.neo4j.util.LogsUtil;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.random.CasiaRandomUtil;
import casia.isiteam.api.toolutil.regex.CasiaRegexUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.neo4j.driver.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName: Neo4jDbSource
 * Description: neo4j config source
 * <p>
 * Created by casia.wzy on 2020/5/20
 * Email: zhiyou_wang@foxmail.com
 */
public class Neo4jDbSource extends Neo4jDriverStatus {
    private static Logger logger = LoggerFactory.getLogger( Neo4jDbSource.class);
    private static synchronized void configure(){
        Map<String,Map<String,Object>> extractInfos = new HashMap<>();
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(APPLICATION);
            Enumeration enumeration = resourceBundle.getKeys();
            while (enumeration.hasMoreElements()) {
                Object key = enumeration.nextElement();
                Object value =  resourceBundle.getObject(String.valueOf(key));
                extraction(key,value,extractInfos);
            }
        }catch (Exception e){
            try {
                Properties props = new Properties() ;
                props.load(new FileInputStream(new File(CONFIG_APPLICATION)));
                Enumeration enumeration = props.keys();
                while (enumeration.hasMoreElements()) {
                    Object key = enumeration.nextElement();
                    Object value =  props.getProperty(String.valueOf(key));
                    extraction(key,value,extractInfos);
                }
                props.clone();
            }catch (Exception E){
                logger.warn(LogsUtil.compositionLogEmpty(APPLICATION));
            }
        }finally {
            extractInfos.forEach((k,v)->{if(!_dataPool.containsKey(k)){_dataPool.put(k,DataPoolManage.initDataPool(v));}});
        }
    }
    protected DruidDataSource NeoDriver(){
        if( Validator.check(dbinfo) ){
            if( Validator.check(dbinfo.getDbname()) ){
                return _dataPool.get(dbinfo.getDbname()).get(CasiaRandomUtil.randomNumber(0,_dataPool.get(dbinfo.getDbname()).size()-1));
            }else{if( _dataPool.containsKey(dbinfo.hexMd5()) ){ return _dataPool.get(dbinfo.getDbname()).get(CasiaRandomUtil.randomNumber(0,_dataPool.get(dbinfo.hexMd5()).size()-1));} else{ synchronized (_dataPool){_dataPool.put(dbinfo.hexMd5(),DataPoolManage.initDataPool(dbinfo));}
                return _dataPool.get(dbinfo.getDbname()).get(CasiaRandomUtil.randomNumber(0,_dataPool.get(dbinfo.hexMd5()).size()-1)); }
            }
        }else{
            logger.error(LogsUtil.compositionLogEmpty("The neo4j dbsource") );
            return null;
        }
    }
    protected Driver Ne4Driver(){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));
       return driver;
    }
    static{configure();}
    protected void close(Session session,Driver driver){
        if( Validator.check(session) ){
            session.close();
        }
        if(Validator.check(driver)){
            driver.close();
        }
    }
    protected void close(Connection connection,PreparedStatement preparedStatement,ResultSet result){
        try {
            if( Validator.check(result) ){
                result.close();
            }
            if(Validator.check(preparedStatement)){
                preparedStatement.close();
            }
            if(Validator.check(connection)){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void extraction(Object key,Object value,Map<String,Map<String,Object>> extractInfos){
        try {
            if( CasiaRegexUtil.isMatch(key.toString(),DB_KEY) ){
                String[] splits = String.valueOf(key).split(SLASH_DOT);
                if( extractInfos.containsKey(splits[3]) ){
                    extractInfos.get(splits[3]).put(splits[4],value);
                }else{
                    Map<String,Object> hv = new HashMap<>();
                    hv.put(splits[4],value);
                    extractInfos.put(splits[3],hv);
                }
            };
        }catch (Exception E){
            logger.error(E.getMessage());
        }
    }
}
