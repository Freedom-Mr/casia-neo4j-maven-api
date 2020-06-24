package casia.isiteam.api.neo4j.controller.imp;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.router.ApiRouter;
import casia.isiteam.api.neo4j.util.LogsUtil;
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

    public boolean create(String cql){
        return createApi.create(cql);
    }
    public boolean create(String cql,Object ... values){
        return createApi.create(cql,values);
    }
    public boolean create(String cql,List<Object[]> values){
        return createApi.create(cql,values);
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
    public boolean createRelationByNodeInfo(CreateType createType , RelationshipInfo... relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationByNodeInfoBuilder(createType,relationInfos);
    }
    public boolean createRelationByNodeInfo(CreateType createType , List<RelationshipInfo> relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationByNodeInfoBuilder(createType,relationInfos.toArray(new RelationshipInfo[relationInfos.size()]) );
    }
    /**
     *
     * @return
     */
    public boolean createRelationByNodeId(CreateType createType , long start_node_id,long end_node_id,String type){
        if( !Validator.check(type) ){logger.warn(LogsUtil.compositionLogEmpty(" relation type "));return true;
        }
        return createApi.createRelationByNodeInfoBuilder(createType,new RelationshipInfo(new NodeInfo().setId(start_node_id),new NodeInfo().setId(end_node_id),type));
    }
    public boolean createRelationByNodeId(CreateType createType , long start_node_id,long end_node_id,String type,boolean direction){
        if( !Validator.check(type) ){logger.warn(LogsUtil.compositionLogEmpty(" relation type "));return true;
        }
        return createApi.createRelationByNodeInfoBuilder(createType,new RelationshipInfo(new NodeInfo().setId(start_node_id),new NodeInfo().setId(end_node_id),type).setDirection(direction));
    }
    public boolean createRelationByNodeId(CreateType createType , RelationshipInfo... relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationByNodeInfoBuilder(createType,relationInfos);
    }
    public boolean createRelationByNodeId(CreateType createType ,  List<RelationshipInfo> relationInfos){
        if( !Validator.check(relationInfos) ){return true;}
        return createApi.createRelationByNodeInfoBuilder(createType,relationInfos.toArray(new RelationshipInfo[relationInfos.size()]));
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

    public boolean createLabelByNodeId( long _id, List<String> labels){
        if( !Validator.check(labels) ){return true;}
        return createApi.addLabelByNodeId(_id,labels);
    }
    public boolean createLabelByNodeId( long _id, String ... labels){
        if( !Validator.check(labels) ){return true;}
        return createApi.addLabelByNodeId(_id,Arrays.asList(labels));
    }
}
