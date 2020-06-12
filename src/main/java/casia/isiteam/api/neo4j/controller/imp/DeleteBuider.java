package casia.isiteam.api.neo4j.controller.imp;

import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.enums.ConditionLevel;
import casia.isiteam.api.neo4j.common.enums.ParameterCombine;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.router.ApiRouter;
import casia.isiteam.api.neo4j.util.LogsUtil;
import casia.isiteam.api.toolutil.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: DeleteBuider
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class DeleteBuider {
    private Logger logger = LoggerFactory.getLogger( this.getClass());
    private Neo4jOperationApi.DeleteApi deleteApi;
    public DeleteBuider(_Entity_Driver entity_driver){
        deleteApi = ApiRouter.getDeleteRouter(entity_driver);
    }

    /**
     * 慎用
     * 删除所有节点及其关系
     * @return
     */
    public boolean deleteAll(){
        logger.warn("start delete all node and relation by neo4j !");
        return deleteApi.delAll();
    }
    public boolean delete(String cql){
        if( !Validator.check(cql)){
            logger.warn(LogsUtil.compositionLogEmpty("cql"));
            return false;
        }
        return deleteApi.del(cql);
    }
    public boolean delete(String cql,Object ... values){
        if( !Validator.check(cql)){
            logger.warn(LogsUtil.compositionLogEmpty("cql"));
            return false;
        }
        return deleteApi.del(cql,values);
    }
    public boolean delete(String cql,List<Object> values){
        if( !Validator.check(cql)){
            logger.warn(LogsUtil.compositionLogEmpty("cql"));
            return false;
        }
        return deleteApi.del(cql,values);
    }
    public boolean deleteNodeById(long id){
        return deleteApi.delNodeById(id);
    }
    public boolean deleteNodeByIdIn(List<Long> ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids")); return false;}
        return deleteApi.delNodeByIdIn(ConditionLevel.MUST,ids);
    }
    public boolean deleteNodeByIdIn(Long ... ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids")); return false;}
        return deleteApi.delNodeByIdIn(ConditionLevel.MUST, Arrays.asList(ids));
    }
    public boolean deleteNodeByIdNotIn(List<Long> ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids")); return false;}
        return deleteApi.delNodeByIdIn(ConditionLevel.NOT,ids);
    }
    public boolean deleteNodeByIdNotIn(Long ... ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids")); return false;}
        return deleteApi.delNodeByIdIn(ConditionLevel.NOT, Arrays.asList(ids));
    }
    public boolean deleteNodeByLabels(List<String> labels){
        if( !Validator.check(labels) ){logger.warn(LogsUtil.compositionLogEmpty("labels")); return false;}
        return deleteApi.delNodeByLabels(labels);
    }
    public boolean deleteNodeByLabelAndParameters(List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes){
        return deleteApi.delNodeByLabelAndParameters(ConditionLevel.MUST,labels,parmsCombine,attributes);
    }
    public boolean deleteNodeByLabelAndParametersNot(List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes){
        return deleteApi.delNodeByLabelAndParameters(ConditionLevel.NOT,labels,parmsCombine,attributes);
    }

    public boolean deleteRelationById(long id){
        return deleteApi.delRelationById(id);
    }
    public boolean deleteRelationByIdIn(List<Long> ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids")); return false;}
        return deleteApi.delRelationByIdIn(ConditionLevel.MUST,ids);
    }
    public boolean deleteRelationByIdIn(Long ... ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids")); return false;}
        return deleteApi.delRelationByIdIn(ConditionLevel.MUST,Arrays.asList(ids));
    }
    public boolean deleteRelationByIdInNot(List<Long> ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids")); return false;}
        return deleteApi.delRelationByIdIn(ConditionLevel.NOT,ids);
    }
    public boolean deleteRelationByIdInNot(Long ... ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids")); return false;}
        return deleteApi.delRelationByIdIn(ConditionLevel.NOT,Arrays.asList(ids));
    }
    public boolean deleteRelationByType(String type){
        if( !Validator.check(type) ){logger.warn(LogsUtil.compositionLogEmpty("type")); return false;}
        return deleteApi.delRelationByType(ConditionLevel.MUST,type);
    }
    public boolean deleteRelationByTypeNot(String type){
        if( !Validator.check(type) ){logger.warn(LogsUtil.compositionLogEmpty("type")); return false;}
        return deleteApi.delRelationByType(ConditionLevel.NOT,type);
    }
    public boolean deleteRelationByTypeAndParameters(String type, ParameterCombine parmsCombine, List<Attribute> attributes){
        return deleteApi.delRelationByTypeAndParameters(ConditionLevel.MUST,type,parmsCombine,attributes);
    }
    public boolean deleteRelationByTypeAndParametersNot(String type, ParameterCombine parmsCombine, List<Attribute> attributes){
        return deleteApi.delRelationByTypeAndParameters(ConditionLevel.NOT,type,parmsCombine,attributes);
    }
    public boolean deleteLabelByNodeId(long id,List<String> labels){
        return deleteApi.delLabelByNodeId(id,labels);
    }
    public boolean deleteRelationByStartNodeId(long start_node_id){
        return deleteApi.delRelationByStartNodeId(start_node_id);
    }
    public boolean deleteRelationByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id){
        return deleteApi.delRelationByStartNodeIdAndEndNodeId(start_node_id,end_node_id);
    }
    public boolean deleteRelationByStartNodeIdAndTypeAndEndNodeId(long start_node_id,String type,long end_node_id){
        return deleteApi.delRelationByStartNodeIdAndTypeAndEndNodeId(start_node_id,type,end_node_id);
    }

}
