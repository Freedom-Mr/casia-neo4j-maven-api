package casia.isiteam.api.neo4j.controller.imp;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
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
    public QueryBuider setCloseTableData(){
        queryApi.closeTableData();
        return this;
    }
    public QueryBuider setOpenNodeRelation(){
        queryApi.openNodeRelation();
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
    public GraphResult queryByCondition(String cql,Object ... values){
        if( !Validator.check(cql) ){logger.warn(LogsUtil.compositionLogEmpty("cql"));return new GraphResult();}
        return queryApi.searchByCondition(cql,values);
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
        return queryApi.searchNodeByIdIn(ConditionLevel.MUST, Arrays.asList(id));
    }
    public GraphResult queryNodeByIdIn(Long ... ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult();}
        return queryApi.searchNodeByIdIn(ConditionLevel.MUST,Arrays.asList(ids));
    }
    public GraphResult queryNodeByIdIn(List<Long> ids){
        if( !Validator.check(ids) ){logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult();}
        return queryApi.searchNodeByIdIn(ConditionLevel.MUST,ids);
    }
    public GraphResult queryNodeByIdNot(Long id){
        if( !Validator.check(id) ){ logger.warn(LogsUtil.compositionLogEmpty("id"));return new GraphResult(); }
        return queryApi.searchNodeByIdIn(ConditionLevel.NOT,Arrays.asList(id));
    }
    public GraphResult queryNodeByIdNotIn(Long ... ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        return queryApi.searchNodeByIdIn(ConditionLevel.NOT,Arrays.asList(ids));
    }
    public GraphResult queryNodeByIdNotIn(List<Long> ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        return queryApi.searchNodeByIdIn(ConditionLevel.NOT,ids);
    }

    public GraphResult queryNodeByParameters(ParameterCombine parmsCombine, Attribute ... attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByParameters(ConditionLevel.MUST,parmsCombine, Arrays.asList(attributes));
    }
    public GraphResult queryNodeByParameters(ParameterCombine parmsCombine, List<Attribute> attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByParameters(ConditionLevel.MUST,parmsCombine,attributes);
    }
    public GraphResult queryNodeByLabelAndParameters(List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes ){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParameters(ConditionLevel.MUST,labels,parmsCombine,attributes);
    }
    public GraphResult queryNodeByLabelAndParameters(List<String> labels, ParameterCombine parmsCombine, Attribute ... attributes ){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParameters(ConditionLevel.MUST,labels,parmsCombine,Arrays.asList(attributes));
    }
    public GraphResult queryNodeByLabelAndParameters(String[] labels, ParameterCombine parmsCombine, Attribute ... attributes ){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParameters( ConditionLevel.MUST, Validator.check(labels) ? Arrays.asList(labels):null ,parmsCombine,Arrays.asList(attributes));
    }
    public GraphResult queryNodeByParametersNot(ParameterCombine parmsCombine, Attribute ... attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByParameters(ConditionLevel.NOT,parmsCombine, Arrays.asList(attributes));
    }
    public GraphResult queryNodeByParametersNot(ParameterCombine parmsCombine, List<Attribute> attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByParameters(ConditionLevel.NOT,parmsCombine, attributes);
    }
    public GraphResult queryNodeByLabelAndParametersNot(List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParameters(ConditionLevel.NOT,labels,parmsCombine, attributes);
    }
    public GraphResult queryNodeByLabelAndParametersNot(List<String> labels, ParameterCombine parmsCombine, Attribute ... attributes){
        if( !Validator.check(attributes) ){ logger.warn(LogsUtil.compositionLogEmpty("attributes"));return new GraphResult(); }
        return queryApi.searchNodeByLabelAndParameters(ConditionLevel.NOT,labels,parmsCombine, Arrays.asList(attributes));
    }
    public GraphResult queryNodeTotal(){
        return queryApi.searchNodeTotal();
    }

    public GraphResult queryNodeTotalByLabel(List<String> labels){
        return queryApi.searchNodeTotalByLabel(labels);
    }
    public GraphResult queryNodeTotalByLabel(String ... labels){
        return queryApi.searchNodeTotalByLabel(Validator.check(labels) ? Arrays.asList(labels) : null);
    }
    public GraphResult queryNodeTotalByLabelAndParameters(List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes){
        return queryApi.searchNodeTotalByLabelAndParameters(ConditionLevel.MUST,labels,parmsCombine,attributes);
    }
    public GraphResult queryNodeTotalByLabelAndParameters(String[] labels, ParameterCombine parmsCombine, Attribute ... attributes){
        return queryApi.searchNodeTotalByLabelAndParameters(ConditionLevel.MUST,Arrays.asList(labels),parmsCombine,Arrays.asList(attributes));
    }
    public GraphResult queryNodeTotalByLabelAndParametersNot(List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes){
        return queryApi.searchNodeTotalByLabelAndParameters(ConditionLevel.NOT,labels,parmsCombine,attributes);
    }
    public GraphResult queryNodeTotalByLabelAndParametersNot(String[] labels, ParameterCombine parmsCombine, Attribute ... attributes){
        return queryApi.searchNodeTotalByLabelAndParameters(ConditionLevel.NOT,Arrays.asList(labels),parmsCombine,Arrays.asList(attributes));
    }

    public GraphResult queryRelationTotal(){
        return queryApi.searchRelationTotal();
    }
    public GraphResult queryRelationTotalByType(String types){
        return queryApi.searchRelationTotalByType(Arrays.asList(types));
    }
    public GraphResult queryRelationTotalByType(List<String> types){
        return queryApi.searchRelationTotalByType(types);
    }
    public GraphResult queryRelationTotalByType(String ... types){
        return queryApi.searchRelationTotalByType(Arrays.asList(types));
    }
    public GraphResult queryRelationTotalByTypeAndParameters(List<String> types, ParameterCombine parmsCombine, List<Attribute> attributes){
        return queryApi.searchRelationTotalByTypeAndParameters(types,parmsCombine,attributes);
    }
    public GraphResult queryRelationTotalByTypeAndParameters(String[] types, ParameterCombine parmsCombine, List<Attribute> attributes){
        return queryApi.searchRelationTotalByTypeAndParameters(Arrays.asList(types),parmsCombine,attributes);
    }
    public GraphResult queryRelationTotalByTypeAndParameters(String[] types, ParameterCombine parmsCombine, Attribute ... attributes){
        return queryApi.searchRelationTotalByTypeAndParameters(Arrays.asList(types),parmsCombine,Arrays.asList(attributes));
    }
    public GraphResult queryRelationTotalByTypeAndParameters(List<String> types, ParameterCombine parmsCombine, Attribute ... attributes){
        return queryApi.searchRelationTotalByTypeAndParameters(types,parmsCombine,Arrays.asList(attributes));
    }

    public GraphResult queryRelation(){
        return queryApi.searchNodeRelation();
    }
    public GraphResult queryRelationByType(List<String> types){
        return queryApi.searchNodeRelationByType(types);
    }
    public GraphResult queryRelationByIdIn( List<Long> ids){
        return queryApi.searchNodeRelationByIdIn(ConditionLevel.MUST,ids);
    }
    public GraphResult queryRelationById(Long id){
        return queryRelationByIdIn(Arrays.asList(id));
    }
    public GraphResult queryRelationByIdIn(Long ... ids){
        return queryRelationByIdIn(Arrays.asList(ids));
    }
    public GraphResult queryRelationByIdNotIn( List<Long> ids){
        return queryApi.searchNodeRelationByIdIn(ConditionLevel.NOT,ids);
    }
    public GraphResult queryRelationByIdNotIn( Long ... ids){
        return queryRelationByIdNotIn(Arrays.asList(ids));
    }

    public GraphResult queryRelationByStartNode(NodeInfo nodeInfo){
        if( !Validator.check(nodeInfo) ){ logger.warn(LogsUtil.compositionLogEmpty("nodeInfo"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNode(nodeInfo);
    }
    public GraphResult queryRelationByStartNodeId(long id){
        if( !Validator.check(id) ){ logger.warn(LogsUtil.compositionLogEmpty("id"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdIn(ConditionLevel.MUST,Arrays.asList(id));
    }
    public GraphResult queryRelationByStartNodeIdNot(long id){
        if( !Validator.check(id) ){ logger.warn(LogsUtil.compositionLogEmpty("id"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdIn(ConditionLevel.NOT,Arrays.asList(id));
    }
    public GraphResult queryRelationByStartNodeIdIn(List<Long> ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdIn(ConditionLevel.MUST,ids);
    }
    public GraphResult queryRelationByStartNodeIdIn(Long ... ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdIn(ConditionLevel.MUST,Arrays.asList(ids));
    }
    public GraphResult queryRelationByStartNodeIdNotIn(List<Long> ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdIn(ConditionLevel.NOT,ids);
    }
    public GraphResult queryRelationByStartNodeIdNotIn(Long ... ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdIn(ConditionLevel.NOT,Arrays.asList(ids));
    }

    public GraphResult queryRelationByStartNodeAndType(NodeInfo startNodeInfo,List<String> types){
        if( !Validator.check(startNodeInfo) ){ logger.warn(LogsUtil.compositionLogEmpty("startNodeInfo"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeAndType(startNodeInfo,types);
    }
    public GraphResult queryRelationByStartNodeIdInAndType(List<Long> start_node_ids ,List<String> type){
        if( !Validator.check(start_node_ids) ){ logger.warn(LogsUtil.compositionLogEmpty("start_node_ids"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdInAndType(start_node_ids,type);
    }
    public GraphResult queryRelationByStartNodeIdAndType(long start_node_id ,List<String> type){
        if( !Validator.check(start_node_id) ){ logger.warn(LogsUtil.compositionLogEmpty("start_node_id"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdInAndType(Arrays.asList(start_node_id),type);
    }

    public GraphResult queryRelationByStartNodeAndTypeAndEndNode(NodeInfo startNodeInfo,List<String> types,NodeInfo endNodeInfo){
        return queryApi.searchNodeRelationByStartNodeAndTypeAndEndNode(startNodeInfo,types,endNodeInfo);
    }
    public GraphResult queryRelationByStartNodeIdAndTypeAndEndNodeId(long start_node_id ,List<String> type,long end_node_id){
        return queryApi.searchNodeRelationByStartNodeIdAndTypeAndEndNodeId(Arrays.asList(start_node_id),type,Arrays.asList(end_node_id));
    }
    public GraphResult queryRelationByStartNodeIdInAndTypeAndEndNodeIdIn(List<Long> start_node_ids ,List<String> type,List<Long> end_node_ids){
        if( !Validator.check(start_node_ids) || !Validator.check(end_node_ids) ){ logger.warn(LogsUtil.compositionLogEmpty("start_node_ids or end_node_ids"));return new GraphResult(); }
        return queryApi.searchNodeRelationByStartNodeIdAndTypeAndEndNodeId(start_node_ids,type,end_node_ids);
    }

    public GraphResult queryRelationOnFuzzyByNodeIds(List<Long> ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        if( ids.size()<2 ){ logger.warn("id numbers must greater than or equal to 2");return new GraphResult(); }
        return queryApi.searchNodeRelationOnFuzzyByNodeIds(ids);
    }
    public GraphResult queryRelationOnFuzzyByNodeIds(Long ... ids){
        return queryRelationOnFuzzyByNodeIds(Arrays.asList(ids));
    }
    public GraphResult queryRelationOnPrecisionByNodeIds(List<Long> ids){
        if( !Validator.check(ids) ){ logger.warn(LogsUtil.compositionLogEmpty("ids"));return new GraphResult(); }
        if( ids.size()<2 ){ logger.warn("id numbers must greater than or equal to 2");return new GraphResult(); }
        return queryApi.searchNodeRelationOnPrecisionByNodeIds(ids);
    }
    public GraphResult queryRelationOnPrecisionByNodeIds(Long ... ids){
        return queryRelationOnPrecisionByNodeIds(Arrays.asList(ids));
    }

    public GraphResult queryRelationOnShortPathByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id,int pathLength){
        return queryApi.searchNodeRelationOnShortPathByStartNodeIdAndEndNodeId(start_node_id,end_node_id,pathLength);
    }

    public GraphResult queryNodeOnFullByQueryString(String indexName,String queryString){
        return queryApi.searchNodeOnFullByQueryString(indexName,queryString);
    }

    public GraphResult queryRelationOnFullByQueryString(String indexName,String queryString){
        return queryApi.searchNodeRelationOnFullByQueryString(indexName,queryString);
    }

    public GraphResult queryRelationByNodeId(long id,int maxLevel){
        return queryApi.searchNodeRelationByNodeId(id,maxLevel);
    }
}
