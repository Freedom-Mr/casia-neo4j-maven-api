package casia.isiteam.api.neo4j.controller.imp;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.enums.FieldCombine;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.router.ApiRouter;
import casia.isiteam.api.neo4j.util.LogsUtil;
import casia.isiteam.api.toolutil.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: QueryBuider
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class QueryBuider {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    private Neo4jOperationApi.QueryApi queryApi;

    public QueryBuider(_Entity_Driver entity_driver){
        queryApi = ApiRouter.getQueryRouter(entity_driver);
    }

    public QueryBuider setFrom(Long from){
        queryApi.setFrom(from);
        return this;
    }
    public QueryBuider setSize(Long size){
        queryApi.setSize(size);
        return this;
    }
    public QueryBuider setReturnField(String ... field){
        queryApi.setReturnField(field);
        return this;
    }
    public QueryBuider setReturnField(List<String> field){
        queryApi.setReturnField(Validator.check(field) ? field.toArray(new String[field.size()]): null);
        return this;
    }

    public GraphResult queryAllLabels(){
        return queryApi.searchAllLabels();
    }
    public GraphResult queryAllRelationshipType(){
        return queryApi.searchAllRelationshipType();
    }

    public GraphResult queryByCondition(String cql){
        if( !Validator.check(cql) ){logger.warn(LogsUtil.compositionLogEmpty("cql"));return new GraphResult();}
        return queryApi.searchByCondition(cql);
    }
    public GraphResult queryNode(){
        return queryApi.searchNode();
    }
    public GraphResult queryNodeByLabel(String ... labels){
        return queryApi.searchNodeByLabel(Validator.check(labels)?Arrays.asList(labels):null);
    }
    public GraphResult queryNodeByLabel(List<String> labels){
        return queryApi.searchNodeByLabel(Validator.check(labels)?labels :null);
    }
    public GraphResult queryNodeById(Long id){
        if( !Validator.check(id) ){ logger.warn(LogsUtil.compositionLogEmpty("id"));return new GraphResult(); }
        return queryApi.searchNodeByIdIn(Arrays.asList(id));
    }
    public GraphResult queryNodeByIdIn(Long ... ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult();}
        return queryApi.searchNodeByIdIn(Arrays.asList(ids));
    }
    public GraphResult queryNodeByIdIn(List<Long> ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult();}
        return queryApi.searchNodeByIdIn(ids);
    }
    public GraphResult queryNodeByIdNot(Long id){
        if( !Validator.check(id) ){ logger.warn(LogsUtil.compositionLogEmpty("id"));return new GraphResult(); }
        return queryApi.searchNodeByIdNotIn(Arrays.asList(id));
    }
    public GraphResult queryNodeByIdNotIn(Long ... ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        return queryApi.searchNodeByIdNotIn(Arrays.asList(ids));
    }
    public GraphResult queryNodeByIdNotIn(List<Long> ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        return queryApi.searchNodeByIdNotIn(ids);
    }

    public GraphResult queryNodeByParameters(FieldCombine parmsCombine, Attribute ... attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByParameters(parmsCombine, Arrays.asList(attributes));
    }
    public GraphResult queryNodeByParameters(FieldCombine parmsCombine, List<Attribute> attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByParameters(parmsCombine,attributes);
    }
    public GraphResult queryNodeByLabelAndParameters(List<String> labels,FieldCombine parmsCombine,List<Attribute> attributes ){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParameters(labels,parmsCombine,attributes);
    }
    public GraphResult queryNodeByLabelAndParameters(List<String> labels,FieldCombine parmsCombine,Attribute ... attributes ){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParameters(labels,parmsCombine,Arrays.asList(attributes));
    }
    public GraphResult queryNodeByLabelAndParameters(String[] labels,FieldCombine parmsCombine,Attribute ... attributes ){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParameters( Validator.check(labels) ? Arrays.asList(labels):null ,parmsCombine,Arrays.asList(attributes));
    }
    public GraphResult queryNodeByParametersNot(FieldCombine parmsCombine, Attribute ... attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByParametersNot(parmsCombine, Arrays.asList(attributes));
    }
    public GraphResult queryNodeByParametersNot(FieldCombine parmsCombine, List<Attribute> attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByParametersNot(parmsCombine, attributes);
    }
    public GraphResult queryNodeByLabelAndParametersNot(List<String> labels,FieldCombine parmsCombine, List<Attribute> attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParametersNot(labels,parmsCombine, attributes);
    }
    public GraphResult queryNodeByLabelAndParametersNot(List<String> labels,FieldCombine parmsCombine, Attribute ... attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParametersNot(labels,parmsCombine, Arrays.asList(attributes));
    }

    public GraphResult queryRelation(){
        return queryApi.searchRelation();
    }
    public GraphResult queryRelationByStartNode(NodeInfo nodeInfo){
        if( !Validator.check(nodeInfo) ){ logger.warn(LogsUtil.compositionLogEmpty("nodeInfo"));return new GraphResult(); }
        return queryApi.searchRelationByStartNode(nodeInfo);
    }


    public GraphResult queryNodeTotalByLabel(List<String> labels){
        return queryApi.searchNodeTotalByLabel(labels);
    }
    public GraphResult queryNodeTotalByLabel(String ... labels){
        return queryApi.searchNodeTotalByLabel(Validator.check(labels) ? Arrays.asList(labels) : null);
    }
}
