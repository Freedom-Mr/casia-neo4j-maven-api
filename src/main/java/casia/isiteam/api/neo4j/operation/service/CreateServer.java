package casia.isiteam.api.neo4j.operation.service;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.datasource.dbdao.Neo4jCommonDb;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.util.CqlBuilder;
import casia.isiteam.api.toolutil.Validator;
import org.neo4j.driver.v1.AccessMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ClassName: CreateServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class CreateServer extends Neo4jCommonDb implements Neo4jOperationApi.CreateApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public GraphResult create(String cql){
        if( Validator.check(cql) ){
            return executeWriteCql(cql);
        }
        return new GraphResult();
    }
    public boolean create(String cql,Object ... keysAndValues){
        if( Validator.check(cql) ){
            return executeWriteCql(cql,keysAndValues);
        }
        return false;
    }
    /**
     * create node
     * @param createType
     * @param node
     * @return
     */
   public boolean createNode(CreateType createType , NodeInfo ... node){
       List<String> cqls = CqlBuilder.createNodeBuilder(createType,node);
       if( Validator.check(cqls) ){
           executeWriteCql(cqls);
       }
       return true;
   }

    /**
     * build cql info by create relation
     * @param model
     * @param relationInfos
     * @return
     */
    public boolean createRelationBuilder(CreateType model, RelationshipInfo... relationInfos){
        List<String> cqls = CqlBuilder.createRelationBuilder(model,relationInfos);
        if( Validator.check(cqls) ){
            executeWriteCql(cqls);
        }
        return true;
    }
    /**
     * build cql info by create relation
     * @param model
     * @param relationInfos
     * @return
     */
    public boolean createRelationByNodeIdBuilder(CreateType model, RelationshipInfo ... relationInfos){
        List<String> cqls = CqlBuilder.createRelationByNodeIdBuilder(model,relationInfos);
        if( Validator.check(cqls) ){
            executeWriteCql(cqls);
        }
        return true;
    }

    /**
     * build cql info by create relation
     * @param model
     * @param relationInfos
     * @return
     */
    public boolean createRelationByNodeUuIdBuilder(CreateType model, RelationshipInfo ... relationInfos){
        List<String> cqls = CqlBuilder.createRelationByNodeUuIdBuilder(model,relationInfos);
        if( Validator.check(cqls) ){
            executeWriteCql(cqls);
        }
        return true;
    }
}
