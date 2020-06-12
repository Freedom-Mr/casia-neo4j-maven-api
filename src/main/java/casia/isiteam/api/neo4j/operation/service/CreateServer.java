package casia.isiteam.api.neo4j.operation.service;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.enums.ConditionLevel;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.datasource.dbdao.Neo4jCommonDb;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.util.LogsUtil;
import casia.isiteam.api.toolutil.Validator;
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

    public boolean create(String cql){
        if( Validator.check(cql) ){
            return executeDruidWriteCql(cql);
        }
        logger.warn(LogsUtil.compositionLogEmpty(" cql "));
        return false;
    }
    public boolean create(String cql,Object ... values){
        if( Validator.check(cql) ){
            return executeDruidWriteCql(cql,values);
        }
        logger.warn(LogsUtil.compositionLogEmpty(" cql "));
        return false;
    }
    public boolean create(String cql,List<Object[]> values){
        if( Validator.check(cql) ){
            return executeDruidWriteCql(cql,values);
        }
        logger.warn(LogsUtil.compositionLogEmpty(" cql "));
        return false;
    }
    /**
     * create node
     * @param createType
     * @param node
     * @return
     */
   public boolean createNode(CreateType createType , NodeInfo ... node){
       List<String> cqls = buildCreateNodeCql(createType,node);
       if( Validator.check(cqls) ){
           cqls.forEach(cql->{
               executeDruidWriteCql(cql);
           });
       }
       return true;
   }

    /**
     * build cql info by create relation
     * @param model
     * @param relationInfos
     * @return
     */
    public boolean createRelationByNodeInfoBuilder(CreateType model, RelationshipInfo... relationInfos){
        List<String> cqls = buildCreateNodeRelationCql(model,relationInfos);
        if( Validator.check(cqls) ){
            cqls.forEach(cql->{
                executeDruidWriteCql(cql);
            });
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
        List<String> cqls = buildCreateNodeRelationByNodeId(model,relationInfos);
        if( Validator.check(cqls) ){
            cqls.forEach(cql->{
                executeDruidWriteCql(cql);
            });
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
        List<String> cqls = buildCreateNodeRelationByNodeUuIdCql(model,relationInfos);
        if( Validator.check(cqls) ){
            cqls.forEach(cql->{
                executeDruidWriteCql(cql);
            });
        }
        return true;
    }

    /**
     * add label to node by node's id
     * @param _id
     * @param labels
     * @return
     */
    @Override
    public boolean addLabelByNodeId(long _id, List<String> labels) {
        return executeDruidWriteCql(s().append(MATCH).append(node(A)).append(where(A, ConditionLevel.MUST.getLevel(),_id)).
                append(set(A,labels)).toString());
    }


}
