package casia.isiteam.api.neo4j.controller.imp;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.router.ApiRouter;
import casia.isiteam.api.toolutil.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: CreateBuider
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class CreateBuider {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    private Neo4jOperationApi.CreateApi createApi;

    public CreateBuider(_Entity_Driver entity_driver){
        createApi = ApiRouter.getCreateRouter(entity_driver);
    }

    public GraphResult create(String cql){
        return createApi.create(cql);
    }
    public boolean create(String cql,Object ... keysAndValues){
        return createApi.create(cql,keysAndValues);
    }
    /**
     *
     * @return
     */
    public boolean createNode(CreateType createType , NodeInfo ... node){
        if( !Validator.check(node) ){return true;}
        return createApi.createNode(createType,node);
    }
    public boolean createNode(CreateType createType , List<NodeInfo> node){
        if( !Validator.check(node) ){return true;}
        return createApi.createNode(createType, node.toArray(new NodeInfo[node.size()]));
    }
    /**
     *create relation
     * @return
     */
    public boolean createRelation(CreateType createType , RelationshipInfo... relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationBuilder(createType,relationInfos);
    }
    public boolean createRelation(CreateType createType , List<RelationshipInfo> relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationBuilder(createType,relationInfos.toArray(new RelationshipInfo[relationInfos.size()]) );
    }
    /**
     *
     * @return
     */
    public boolean createRelationByNodeId(CreateType createType , RelationshipInfo... relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationByNodeIdBuilder(createType,relationInfos);
    }
    public boolean createRelationByNodeId(CreateType createType ,  List<RelationshipInfo> relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationByNodeIdBuilder(createType,relationInfos.toArray(new RelationshipInfo[relationInfos.size()]));
    }
    /**
     *
     * @return
     */
    public boolean createRelationByNodeUuId(CreateType createType , RelationshipInfo ... relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationByNodeUuIdBuilder(createType,relationInfos);
    }
    public boolean createRelationByNodeUuId(CreateType createType , List<RelationshipInfo> relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationByNodeUuIdBuilder(createType,relationInfos.toArray(new RelationshipInfo[relationInfos.size()]));
    }
}
