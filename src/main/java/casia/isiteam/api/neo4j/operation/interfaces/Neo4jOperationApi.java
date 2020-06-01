package casia.isiteam.api.neo4j.operation.interfaces;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.common.enums.FieldCombine;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * ClassName: Neo4jOperationApi
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class Neo4jOperationApi {
    public interface QueryApi {
        void setFrom(Long from);
        void setSize(Long size);
        void setReturnField(String ... field);
        GraphResult searchByCondition(String cql);
        GraphResult searchAllLabels();
        GraphResult searchAllRelationshipType();
        GraphResult searchNode();
        GraphResult searchNodeByLabel(List<String> labels);
        GraphResult searchNodeByIdIn(List<Long> ids);
        GraphResult searchNodeByIdNotIn(List<Long> ids);
        GraphResult searchNodeByParameters(FieldCombine fieldCombine, List<Attribute> attributes);
        GraphResult searchNodeByParametersNot(FieldCombine fieldCombine, List<Attribute> attributes);
        GraphResult searchNodeByLabelAndParameters(List<String> labels,FieldCombine parmsCombine,List<Attribute> attributes );
        GraphResult searchNodeByLabelAndParametersNot(List<String> labels,FieldCombine parmsCombine,List<Attribute> attributes );

        GraphResult searchRelation();
        GraphResult searchRelationByStartNode(NodeInfo nodeInfo);

        GraphResult searchNodeTotalByLabel(List<String> labels);
    }
    public interface DeleteApi {
    }
    public interface CreateApi {
        GraphResult create(String cql);
        boolean create(String cql,Object ... keysAndValues);
        boolean createNode(CreateType createType , NodeInfo ... node);
        boolean createRelationBuilder(CreateType createType , RelationshipInfo ... relationInfos);
        boolean createRelationByNodeIdBuilder(CreateType model, RelationshipInfo... relationInfos);
        boolean createRelationByNodeUuIdBuilder(CreateType createType ,  RelationshipInfo... relationInfos);
    }
    public interface TreeApi {
        GraphResult searchAllTreeByLables(List<String> labels);
        JSONArray searchAllJsonTreeByLables(List<String> labels);
    }
}
