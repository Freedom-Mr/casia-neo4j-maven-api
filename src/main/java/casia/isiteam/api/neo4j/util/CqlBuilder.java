package casia.isiteam.api.neo4j.util;

import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.util.build.BuildNode;
import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ClassName: CqlBuilder
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@foxmail.com
 */
public class CqlBuilder extends BuildNode {
    public static String asKey(){
        return A+UUID.randomUUID().toString().replaceAll(CROSS,NONE);
    }

    public static String cqlB(String ... keywords){
        StringBuffer sb = new StringBuffer();
        for(String keyword:keywords){
            sb.append(keyword).append(BLANK);
        }
        return sb.toString();
    }


    /**
     * build cql info by create node
     * @param model
     * @param nodeInfo
     * @return
     */
    public static List<String> createNodeBuilder(CreateType model,NodeInfo ... nodeInfo){
        if( !Validator.check(model) || !Validator.check(nodeInfo) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(NodeInfo node:nodeInfo){
            if( !Validator.check(node.getLabels()) ){
                continue;
            }
            String asKey = asKey();

            String cql_node = node(asKey,node.getLabels(),node.get_uuId());
            String cql_set = buildSet(asKey,node.getParameters());

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
    public static List<String> createRelationBuilder(CreateType model, RelationshipInfo ... relationInfos){
        if( !Validator.check(model) || !Validator.check(relationInfos) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(RelationshipInfo relationInfo:relationInfos){
            if( !Validator.check(relationInfo.getStartNodeInfo())  || !Validator.check(relationInfo.getEndNodeInfo()) ){
                continue;
            }
            String cql = buildRelation(model,relationInfo,3);
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
    public static List<String> createRelationByNodeIdBuilder(CreateType model, RelationshipInfo ... relationInfos){
        if( !Validator.check(model) || !Validator.check(relationInfos) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(RelationshipInfo relationInfo:relationInfos){
            if( !Validator.check(relationInfo.getStartNodeInfo())  || !Validator.check(relationInfo.getEndNodeInfo()) ){
                continue;
            }
            String cql = buildRelation(model,relationInfo,1);
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
    public static List<String> createRelationByNodeUuIdBuilder(CreateType model, RelationshipInfo ... relationInfos){
        if( !Validator.check(model) || !Validator.check(relationInfos) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(RelationshipInfo relationInfo:relationInfos){
            if( !Validator.check(relationInfo.getStartNodeInfo())  || !Validator.check(relationInfo.getEndNodeInfo()) ){
                continue;
            }
            cqls.add(buildRelation(model,relationInfo,2));
        }
        return cqls;
    }

    /**
     * build new label cql info by create node
     * @param nodeInfo
     * @return
     */
    public static List<String> addLabelByNodeIdBuilder(NodeInfo ... nodeInfo){
        if( !Validator.check(nodeInfo) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(NodeInfo node:nodeInfo){
            if( !Validator.check(node.getId()) || !Validator.check(node.getLabels()) ){
                continue;
            }

            String asKey = asKey();
            StringBuffer sb = new StringBuffer();
            sb.append(addBlank(MATCH)).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES).append(addBlank(WHERE)).
                    append(ID).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES).append(EQUAL).append(node.getId())
                    .append(addBlank(SET))
                    .append(asKey);
            node.getLabels().forEach(s->{
                sb.append(COLON).append(s);
            });
            cqls.add(sb.toString());
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
    private static String buildRelation(CreateType model,RelationshipInfo relationInfo,int type){
        /************ START NODE **************/
        String start_asKey = asKey();
        StringBuffer satrt_node = new StringBuffer();

        /************ END NODE **************/
        String end_asKey = asKey();
        StringBuffer end_node = new StringBuffer();

        // 通过ID
        if( type == 1 ){
            if( !Validator.check(relationInfo.getStartNodeInfo().getId()) || !Validator.check(relationInfo.getEndNodeInfo().getId()) ){
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
                .append( buildRelationNode(line_asKey,relationInfo.getType(),relationInfo.get_uuId()) )
                .append(relationInfo.getDirection() ? CROSS_D : CROSS )
                .append(LEFT_PARENTHESES).append(end_asKey).append(RIGHT_PARENTHESES);

        if( Validator.check(relationInfo.getParameters()) ){
            sb_relation.append( buildSet(line_asKey,relationInfo.getParameters()) );
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
    private static String buildRelationNode(String asKey,String label,Object _uuid ){
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
