package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.util.TypeUtil;
import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BuildCql
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/4
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildCql extends BuildReturn {

    protected String node_1 = " (%s) ";
    protected String node_2 = " (%s:%s) ";
    protected String node_3 = " (%s:%s{_uuId:%s}) ";

    protected String nodeRelation_1 = " ()-[]-() ";
    protected String nodeRelation_2 = " ()-[%s]-() ";

    protected String id_1 = " id(%s)=%d ";

    protected String apoc_1 = " apoc.path.subgraphAll(%s, {maxLevel:%d}) ";


    protected String symbols(String symbol,String ... fields){
        StringBuffer result = s();
        for(String field:fields){
            result.append(result.length() == 0? field : symbol+field);
        }
        return result.toString();
    }
    protected String symbols(String symbol,List<String> fields){
        StringBuffer result = s();
        for(String field:fields){
            result.append(result.length() == 0? field : symbol+field);
        }
        return result.toString();
    }
    /**
     * build cql info by create node
     * @param model
     * @param nodeInfo
     * @return
     */
    public List<String> buildCreateNodeCql(CreateType model, NodeInfo... nodeInfo){
        if( !Validator.check(model) || !Validator.check(nodeInfo) ){
            return new ArrayList<>();
        }
        List<String> cqls = new ArrayList<>();
        for(NodeInfo node:nodeInfo){
            String cql_node = node(A,node.getLabels(),node.get_uuId());
            String cql_set = set(A,node.getParameters());
            cqls.add(model.name()+cql_node+cql_set);
        }
        return cqls;
    }

    /**
     * build cql info by create relation
     * @param model
     * @param relationInfos
     * @return
     */
    public List<String> buildCreateNodeRelationByNodeId(CreateType model, RelationshipInfo ... relationInfos){
        if( !Validator.check(model) || !Validator.check(relationInfos) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(RelationshipInfo relationInfo:relationInfos){
            if( !Validator.check(relationInfo.getStartNodeInfo())  || !Validator.check(relationInfo.getEndNodeInfo()) ){
                continue;
            }
            String cql = buildCreateNodeRelationCql(model,relationInfo,1);
            if( Validator.check(cql) ){
                cqls.add(cql);
            };
        }
        return cqls;
    }
    /**
     * build cql info by create relation
     * @param model
     * @param relationInfos
     * @return
     */
    public List<String> buildCreateNodeRelationCql(CreateType model, RelationshipInfo... relationInfos){
        if( !Validator.check(model) || !Validator.check(relationInfos) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(RelationshipInfo relationInfo:relationInfos){
            if( !Validator.check(relationInfo.getStartNodeInfo())  || !Validator.check(relationInfo.getEndNodeInfo()) ){
                continue;
            }
            String cql = buildCreateNodeRelationCql(model,relationInfo,relationInfo.getStartNodeInfo().getId()>-1 && relationInfo.getEndNodeInfo().getId()>-1 ? 1:3);
            if( Validator.check(cql) ){
                cqls.add(cql);
            };
        }
        return cqls;
    }
    /**
     * build cql info by create relation
     * @param model
     * @param relationInfos
     * @return
     */
    public List<String> buildCreateNodeRelationByNodeUuIdCql(CreateType model, RelationshipInfo ... relationInfos){
        if( !Validator.check(model) || !Validator.check(relationInfos) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(RelationshipInfo relationInfo:relationInfos){
            if( !Validator.check(relationInfo.getStartNodeInfo())  || !Validator.check(relationInfo.getEndNodeInfo()) ){
                continue;
            }
            cqls.add(buildCreateNodeRelationCql(model,relationInfo,2));
        }
        return cqls;
    }

    /**
     * build relation
     * @param model
     * @param relationInfo
     * @param type 1-id, 2-uuId ,3-allParms
     * @return
     */
    private String buildCreateNodeRelationCql(CreateType model,RelationshipInfo relationInfo,int type){
        /************ START NODE **************/
        String start_asKey = A;
        StringBuffer satrt_node = new StringBuffer();

        /************ END NODE **************/
        String end_asKey = B;
        StringBuffer end_node = new StringBuffer();

        // 通过ID
        if( type == 1 ){
            if( relationInfo.getStartNodeInfo().getId() <-1 || relationInfo.getEndNodeInfo().getId()<-1 ){
                return NONE;
            }
            satrt_node.append(addBlank(MATCH)).append( node(start_asKey,relationInfo.getStartNodeInfo().getLabels(),null) ).append( where(start_asKey, NONE ,relationInfo.getStartNodeInfo().getId()) );
            end_node.append(addBlank(MATCH)).append( node(end_asKey,relationInfo.getEndNodeInfo().getLabels(),null) ).append( where(end_asKey, NONE,relationInfo.getEndNodeInfo().getId()) );
        }
        // 通过uuId
        else if( type == 2 ){
            if( !Validator.check(relationInfo.getStartNodeInfo().get_uuId()) || !Validator.check(relationInfo.getEndNodeInfo().get_uuId()) ){
                return NONE;
            }
            satrt_node.append(addBlank(MATCH)).append( node(start_asKey,relationInfo.getStartNodeInfo().getLabels(),relationInfo.getStartNodeInfo().get_uuId()) );
            end_node.append(addBlank(MATCH)).append( node(end_asKey,relationInfo.getEndNodeInfo().getLabels(),relationInfo.getEndNodeInfo().get_uuId()) );
        }
        // 通过所有参数
        else if( type == 3 ){
            if( !Validator.check(relationInfo.getStartNodeInfo().getParameters()) || !Validator.check(relationInfo.getEndNodeInfo().getParameters()) ){
                return NONE;
            }
            if( Validator.check( relationInfo.getStartNodeInfo().get_uuId() ) ){
                relationInfo.getStartNodeInfo().getParameters().put(_UUID,relationInfo.getStartNodeInfo().get_uuId());
            }
            if( Validator.check( relationInfo.getEndNodeInfo().get_uuId() ) ){
                relationInfo.getEndNodeInfo().getParameters().put(_UUID,relationInfo.getEndNodeInfo().get_uuId());
            }
            satrt_node.append(addBlank(MATCH)).append( node(start_asKey,relationInfo.getStartNodeInfo().getLabels()) ).
                    append( where(start_asKey,relationInfo.getStartNodeInfo().getParameters() ) );
            end_node.append(addBlank(MATCH)).append( node(end_asKey,relationInfo.getEndNodeInfo().getLabels() ) ).
                    append( where(end_asKey,relationInfo.getEndNodeInfo().getParameters() ) );
        }

        /************ LINE **************/
        String line_asKey = asKey();
        StringBuffer sb_relation = new StringBuffer();
        sb_relation.append(addBlank(model.name()))
                .append(LEFT_PARENTHESES).append(start_asKey).append(RIGHT_PARENTHESES)
                .append(CROSS)
                .append( buildRelation(line_asKey,relationInfo.getType(),relationInfo.get_uuId()) )
                .append(relationInfo.getDirection() ? CROSS_D : CROSS )
                .append(LEFT_PARENTHESES).append(end_asKey).append(RIGHT_PARENTHESES);

        if( Validator.check(relationInfo.getParameters()) ){
            sb_relation.append( set(line_asKey,relationInfo.getParameters()) );
        }
        return NONE + satrt_node + end_node +sb_relation;
    }

    /**
     * build relation node
     * @param asKey
     * @param label
     * @param _uuid
     * @return
     */
    private String buildRelation(String asKey,String label,Object _uuid ){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(LEFT_BRACKETS).append(asKey);
        if( Validator.check(label) ){
            sb_node.append(COLON).append(label);
        }
        if( Validator.check(_uuid) ){
            sb_node.append(LEFT_BRACES).append(_UUID).append(COLON).append(TypeUtil.typeRecognition(_uuid)).append(RIGHT_BRACES);
        }
        sb_node.append(RIGHT_BRACKETS);
        return sb_node.toString();
    }
}
