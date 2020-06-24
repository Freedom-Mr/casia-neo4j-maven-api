package casia.isiteam.api.neo4j.datasource.dbdao;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.datasource.Neo4jDbSource;
import casia.isiteam.api.toolutil.Validator;
import org.neo4j.driver.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * ClassName: Neo4jCommonDb
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class Neo4jCommonDb extends Neo4jDbSource {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    /**
     * 执行cql
     * @param cqls
     * @return
     */
    protected boolean executeWriteCql(List<String> cqls) {
        Driver driver = Ne4Driver();
        Session session = null;
        try {
            session = driver.session(AccessMode.WRITE);
            for(String cql : cqls){
                logger.debug("cql: {}",cql);
                try {
                    session = driver.session(AccessMode.WRITE);
                    Transaction ts = session.beginTransaction();
                    StatementResult statementResult = ts.run(cql);
                    ts.success();
                }catch (Exception e){
                    logger.error("run cql error，model:WRITE，cql:{}，error:{}",cqls.size(),e.getMessage());
                    continue;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("run cql error，model:WRITE，cql:{}，error:{}",cqls.size(),e.getMessage());
            return false;
        } finally {
            close(session, null);
        }
    }
    protected boolean executeWriteCql(String cql,Object ... keysAndValues) {
        Driver driver = Ne4Driver();
        Session session = null;
        try {
            session = driver.session(AccessMode.WRITE);
            while (true){
                try {
                    Transaction ts = session.beginTransaction();
                    if(Validator.check(keysAndValues)){
                        ts.run(cql,parameters(keysAndValues));
                    }else{
                        ts.run(cql);
                    }
                    ts.success();
                    break;
                }catch (Exception e){
                    logger.error("run cql error，model:WRITE，cql:{}，error:{}",cql,e.getMessage());
                    logger.error("sleep 20s，restart run cql!");
                    Thread.sleep(20*1000);
                    continue;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("run cql error，model:WRITE，cql:{}，error:{}",cql,e.getMessage());
            return false;
        } finally {
            close(session, null);
        }
    }
    /**
     * 执行cql
     * @param cql
     * @return
     */
    protected GraphResult executeWriteCql( String cql) {
        Driver driver = Ne4Driver();
        Session session = null;
        try {
            logger.debug("cql: {}",cql);
            session = driver.session(AccessMode.WRITE);
            Transaction ts = session.beginTransaction();
            StatementResult statementResult =  ts.run(cql);
            ts.success();
            return ParseResult.parseResult(statementResult);
        } catch (Exception e) {
            logger.error("run cql error，model:WRITE，cql:{}，error:{}",cql,e.getMessage());
            return new GraphResult();
        } finally {
            close(session, null);
        }
    }
    /**
     * 执行cql
     * @param cql
     * @return
     */
    protected GraphResult executeReadCql( String cql) {
        Driver driver = Ne4Driver();
        Session session = null;
        try {
            logger.debug("cql: {}",cql);
            session = driver.session(AccessMode.READ);
            StatementResult statementResult = session.run(cql);
            return ParseResult.parseResult(statementResult);
        } catch (Exception e) {
            logger.error("run cql error，model:READ，cql:{}，error:{}",cql,e.getMessage());
            return new GraphResult();
        } finally {
            close(session, null);
        }
    }

    /**************************************************** druid ************************************************************/
    /**
     * 执行cql
     * @param cql
     * @return
     */
    protected boolean executeDruidWriteCql(String cql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            logger.info("cql: {}",cql);
            connection =NeoDriver().getConnection();
            preparedStatement = connection.prepareStatement(cql);
            int result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("run cql error，model:WRITE，cql:{}，error:{}",cql,e.getMessage());
            return false;
        } finally {
            close(connection, preparedStatement,null);
        }
        return true;
    }
    /**
     * 执行cql
     * @param cql
     * @return
     */
    protected boolean executeDruidWriteCql(String cql,Object ... values) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            logger.info("cql: {}",cql);
            connection =NeoDriver().getConnection();
            preparedStatement = connection.prepareStatement(cql);
            if( Validator.check(values) ){
                for(int i=0;i<values.length;i++){
                    preparedStatement.setObject(i+1,values[i]);
                }
            }
            int result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("run cql error，model:WRITE，cql:{}，error:{}",cql,e.getMessage());
            return false;
        } finally {
            close(connection, preparedStatement,null);
        }
        return true;
    }
    /**
     * 批量 操作 cql
     * @param cql
     * @return
     */
    protected boolean executeDruidBatchWriteCql(String cql,List<Object[]> values) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            logger.info("cql: {}",cql);
            connection =NeoDriver().getConnection();
            preparedStatement = connection.prepareStatement(cql);
            if( Validator.check(values) ){
                for(Object[] s:values){
                    for(int i=0;i<s.length;i++){
                        preparedStatement.setObject(i+1,s[i]);
                    }
                    preparedStatement.addBatch();
                }
            }
           preparedStatement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("run cql error，model:WRITE，cql:{}，error:{}",cql,e.getMessage());
            return false;
        } finally {
            close(connection, preparedStatement,null);
        }
        return true;
    }
    protected GraphResult executeDruidReadCql(boolean openTableData, String cql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            logger.info("cql: {}",cql);
            connection =NeoDriver().getConnection();
            preparedStatement = connection.prepareStatement(cql);
            result = preparedStatement.executeQuery();
            return ParseResult.parseResult(result,openTableData);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("run cql error，model:READ，cql:{}，error:{}",cql,e.getMessage());
            return new GraphResult();
        } finally {
            close(connection, preparedStatement,result);
        }
    }
    protected GraphResult executeDruidReadCql(boolean openTableData, String cql,Object ... values) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            logger.info("cql: {}",cql);
            connection =NeoDriver().getConnection();
            preparedStatement = connection.prepareStatement(cql);
            if( Validator.check(values) ){
                for(int i=0;i<values.length;i++){
                    preparedStatement.setObject(i+1,values[i]);
                }
            }
            result = preparedStatement.executeQuery();
            return ParseResult.parseResult(result,openTableData);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("run cql error，model:READ，cql:{}，error:{}",cql,e.getMessage());
            return new GraphResult();
        } finally {
            close(connection, preparedStatement,result);
        }
    }
}
