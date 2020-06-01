package casia.isiteam.api.neo4j.datasource._source;

import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.neo4j.util.LogsUtil;
import casia.isiteam.api.toolutil.regex.CasiaRegexUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
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
    private static Map<String,ComboPooledDataSource> dataSource;

    private void initDataPool(String dbName,Map<String,Object> parms){
        ComboPooledDataSource dataSos = new ComboPooledDataSource();
        dataSos.setUser( parms.containsKey(BasicParms.USERNAME)? String.valueOf( parms.get(BasicParms.USERNAME) ) : null );
        dataSos.setPassword( parms.containsKey(BasicParms.PASSWORD)? String.valueOf( parms.get(BasicParms.PASSWORD) ) : null );
//        dataSos.setJdbcUrl( parms.containsKey(BasicParms.BLOT)? String.valueOf( parms.get(BasicParms.BLOT) ).split(COMMA+LINE+SEMICOLON) : null );

    }
}
