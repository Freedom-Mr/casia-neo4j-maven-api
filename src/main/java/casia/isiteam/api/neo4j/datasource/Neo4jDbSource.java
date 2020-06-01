package casia.isiteam.api.neo4j.datasource;

import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.neo4j.common.manage.status.Neo4jDriverStatus;
import casia.isiteam.api.neo4j.util.LogsUtil;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.regex.CasiaRegexUtil;
import casia.isiteam.api.toolutil.secretKey.base.CasiaBaseUtil;
import org.neo4j.driver.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
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
            extractInfos.forEach((k,v)->initEsDb(k,v));
        }
    }
    private static void initEsDb(String dbName,Map<String,Object> parms){
        synchronized (Neo4jDbSource.dbSource){
            if( !Neo4jDbSource.dbSource.containsKey(String.valueOf(dbName)) ){
                Neo4jDbSource.dbSource.put(dbName,new _Entity_Driver(dbName,
                        parms.containsKey(BasicParms.USERNAME)? String.valueOf( parms.get(BasicParms.USERNAME) ) : null,
                        parms.containsKey(BasicParms.PASSWORD)? String.valueOf( parms.get(BasicParms.PASSWORD) ) : null,
                        parms.containsKey(BasicParms.BLOT)? String.valueOf( parms.get(BasicParms.BLOT) ).split(COMMA+LINE+SEMICOLON) : null
                ));
            }
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
    protected static synchronized _Entity_Driver getDb(String driverName){
        return Neo4jDbSource.dbSource.containsKey(driverName)? Neo4jDbSource.dbSource.get(driverName) : new _Entity_Driver("");
    }
    protected Driver neoDriver(){
        if( Validator.check(dbinfo) ){
            _Entity_Driver driverInfo = Validator.check(dbinfo.getDbname()) ? getDb(dbinfo.getDbname()) : dbinfo;

            String base = CasiaBaseUtil.encrypt64(driverInfo.getDbname()+driverInfo.getBolts()+driverInfo.getUsername()+driverInfo.getPassword());
            if( _drivers.containsKey(base) ){
                return _drivers.get(base);
            }

            if( !Validator.check(driverInfo) || !Validator.check(driverInfo.getBolts()) ){
                logger.error(LogsUtil.compositionLogEmpty("The neo4j dbsource blot") );
                return null;
            }
            if( driverInfo.getBolts().size() >1 ){
                Config config = Config.builder()
                        .withResolver( address -> new HashSet<>( driverInfo.getBolts() ) )
                        .build();
                _drivers.put(base, Validator.check( driverInfo.getUsername() ) ? GraphDatabase.driver( VIRTUALURI, AuthTokens.basic( driverInfo.getUsername(), driverInfo.getPassword() ),
                        config ) : GraphDatabase.driver( VIRTUALURI, config) );
            }else{
                _drivers.put(base, Validator.check( driverInfo.getUsername() ) ?  GraphDatabase.driver( driverInfo.getBolt() , AuthTokens.basic( driverInfo.getUsername(), driverInfo.getPassword() ) ):
                        GraphDatabase.driver( VIRTUALURI ) );
            }
            return _drivers.get(base);
        }else{
            logger.error(LogsUtil.compositionLogEmpty("The neo4j dbsource") );
            return null;
        }
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
}
