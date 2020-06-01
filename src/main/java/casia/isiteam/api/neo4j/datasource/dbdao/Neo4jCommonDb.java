package casia.isiteam.api.neo4j.datasource.dbdao;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.result.RelationInfo;
import casia.isiteam.api.neo4j.datasource.Neo4jDbSource;
import casia.isiteam.api.toolutil.Validator;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public boolean executeWriteCql(List<String> cqls) {
        Driver driver = neoDriver();
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
    public boolean executeWriteCql(String cql,Object ... keysAndValues) {
        Driver driver = neoDriver();
        Session session = null;
        try {
            session = driver.session(AccessMode.WRITE);
            while (true){
                try {
                    Transaction ts = session.beginTransaction();
                    ts.run(cql,parameters(keysAndValues));
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
    public GraphResult executeWriteCql( String cql) {
        Driver driver = neoDriver();
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
    public GraphResult executeReadCql( String cql) {
        Driver driver = neoDriver();
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
}
