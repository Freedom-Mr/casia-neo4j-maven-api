package casia.isiteam.api.neo4j.operation.interfaces;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.ConditionLevel;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.common.enums.ParameterCombine;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * ClassName: Neo4jOperationApi
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class Neo4jOperationApi{
    public interface QueryApi {
        void setFrom(Long from);
        void setSize(Long size);
        void setReturnField(String ... field);
        void closeTableData();
        void openNodeRelation();
        void openDirection();
        GraphResult searchByCondition(String cql);
        GraphResult searchByCondition(String cql,Object ... values);
        GraphResult searchAllLabels();
        GraphResult searchAllRelationshipType();

        GraphResult searchNode();
        GraphResult searchNodeByLabel(List<String> labels);
        GraphResult searchNodeByIdIn(ConditionLevel conditionLevel ,List<Long> ids);
        GraphResult searchNodeByParameters(ConditionLevel conditionLevel , ParameterCombine parameterCombine, List<Attribute> attributes);
        GraphResult searchNodeByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes );

        GraphResult searchNodeTotal();
        GraphResult searchNodeTotalByLabel(List<String> labels);
        GraphResult searchNodeTotalByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes);
        GraphResult searchRelationTotal();
        GraphResult searchRelationTotalByType(List<String> types);
        GraphResult searchRelationTotalByTypeAndParameters(List<String> types, ParameterCombine parmsCombine, List<Attribute> attributes);


        GraphResult searchNodeRelation();
        GraphResult searchNodeRelationByType(List<String> types);
        GraphResult searchNodeRelationByIdIn(ConditionLevel conditionLevel , List<Long> ids);
        GraphResult searchNodeRelationByStartNode(NodeInfo nodeInfo);
        GraphResult searchNodeRelationByNodeId(long id,int maxLevel,String relationshipFilter,String labelFilter);
        GraphResult searchNodeRelationByStartNodeIdIn(ConditionLevel conditionLevel ,List<Long> ids);
        GraphResult searchNodeRelationByStartNodeAndType(NodeInfo startNodeInfo,List<String> types);
        GraphResult searchNodeRelationByStartNodeIdInAndType(List<Long> start_node_ids ,List<String> type);
        GraphResult searchNodeRelationByStartNodeAndTypeAndEndNode(NodeInfo startNodeInfo,List<String> type,NodeInfo endNodeInfo);
        GraphResult searchNodeRelationByStartNodeIdAndTypeAndEndNodeId(List<Long> start_node_ids,List<String> type,List<Long> end_node_ids);

        GraphResult searchNodeRelationOnFuzzyByNodeIds(List<Long> ids);
        GraphResult searchNodeRelationOnPrecisionByNodeIds(List<Long> ids);
        GraphResult searchNodeRelationOnShortPathByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id,int pathLength);
        GraphResult searchNodeRelationOnPathLengthByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id,int startPathLength,int endPathLength);
        GraphResult searchNodeRelationOnExtendTreeByNodeIdIn(List<Long> ids,int maxLevel,String relationshipFilter,String labelFilter );

        GraphResult searchNodeOnFullByQueryString(String indexName,String queryString);
        GraphResult searchNodeRelationOnFullByQueryString(String indexName,String queryString);
    }
    public interface DeleteApi {
        boolean delAll();
        boolean del(String cql);
        boolean del(String cql,Object ... values);
        boolean del(String cql,List<Object[]> values);
        boolean delNodeById(long id);
        boolean delNodeByIdIn(ConditionLevel conditionLevel , List<Long> ids);
        boolean delNodeByLabels(List<String> labels);
        boolean delNodeByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes );
        boolean delRelationById(long id);
        boolean delRelationByIdIn(ConditionLevel conditionLevel , List<Long> ids);
        boolean delRelationByType(ConditionLevel conditionLevel ,String type);
        boolean delRelationByTypeAndParameters(ConditionLevel conditionLevel , String type, ParameterCombine parmsCombine, List<Attribute> attributes);
        boolean delLabelByNodeId(long id,List<String> labels);
        boolean delRelationByStartNodeId(long start_node_id);
        boolean delRelationByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id);
        boolean delRelationByStartNodeIdAndTypeAndEndNodeId(long start_node_id,String type,long end_node_id);
    }
    public interface CreateApi {
        boolean create(String cql);
        boolean create(String cql,Object ... values);
        boolean create(String cql,List<Object[]> values);
        boolean createNode(CreateType createType , NodeInfo ... node);
        boolean createRelationByNodeInfoBuilder(CreateType createType , RelationshipInfo ... relationInfos);
        boolean createRelationByNodeUuIdBuilder(CreateType createType ,  RelationshipInfo... relationInfos);

        boolean addLabelByNodeId(long _id, List<String> labels);
    }
    public interface UpdateApi {

    }
    public interface TreeApi {
        GraphResult searchAllTreeByLables(List<String> labels);
        JSONArray searchAllJsonTreeByLables(List<String> labels);
        JSONArray searchAllJsonTreeAndCountByLables(List<String> labels);
    }
}
