package casia.isiteam.api.neo4j.util;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.util.build.BuildCqls;
import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BuilderUtil
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@foxmail.com
 */
public class BuilderUtil extends BuildCqls {



    /**
     * build cql info by create node
     * @param model
     * @param nodeInfo
     * @return
     */
    public List<String> createNodeCql(CreateType model, NodeInfo... nodeInfo){
        if( !Validator.check(model) || !Validator.check(nodeInfo) ){
            return new ArrayList<>();
        }
        List<String> cqls = new ArrayList<>();
        for(NodeInfo info:nodeInfo){
            String cql_set = symbols(COMMA,EQUAL,A,info.getParameters());
            cqls.add(model.name()+ node(A,info.getLabels(),info.get_uuId()) + (Validator.check(cql_set) ? addBlank(SET)+cql_set : cql_set) );
        }
        return cqls;
    }

    /**
     * build cql info by create relation
     * @param model
     * @param relationInfos
     * @return
     */
    public List<String> createRelationCql(CreateType model, RelationshipInfo... relationInfos){
        if( !Validator.check(model) || !Validator.check(relationInfos) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(RelationshipInfo relationInfo:relationInfos){
            if( !Validator.check(relationInfo.getStartNodeInfo())  || !Validator.check(relationInfo.getEndNodeInfo()) ){
                continue;
            }
            String cql = buildCreateRelationCql(model,relationInfo,relationInfo.getStartNodeInfo().getId()>-1 && relationInfo.getEndNodeInfo().getId()>-1 ? 1:3);
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
    public List<String> createRelationByNodeUuIdCql(CreateType model, RelationshipInfo ... relationInfos){
        if( !Validator.check(model) || !Validator.check(relationInfos) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(RelationshipInfo relationInfo:relationInfos){
            if( !Validator.check(relationInfo.getStartNodeInfo())  || !Validator.check(relationInfo.getEndNodeInfo()) ){
                continue;
            }
            cqls.add(buildCreateRelationCql(model,relationInfo,2));
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
    private String buildCreateRelationCql(CreateType model,RelationshipInfo relationInfo,int type){
        /************ START NODE **************/
        String start_asKey = A;
        StringBuffer satrt_node = s();

        /************ END NODE **************/
        String end_asKey = B;
        StringBuffer end_node = s();

        // 通过ID
        if( type == 1 ){
            if( relationInfo.getStartNodeInfo().getId() <-1 || relationInfo.getEndNodeInfo().getId()<-1 ){
                return NONE;
            }
            satrt_node.append(addBlank(MATCH)).append( node(start_asKey,relationInfo.getStartNodeInfo().getLabels()) ).
                    append( addBlank(WHERE) ).append( addId(start_asKey ,relationInfo.getStartNodeInfo().getId()) );
            end_node.append(addBlank(MATCH)).append( node(end_asKey,relationInfo.getEndNodeInfo().getLabels()) ).
                    append( addBlank(WHERE) ).append( addId(end_asKey ,relationInfo.getEndNodeInfo().getId()) );
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
                    append( addBlank(WHERE) ).append(  symbols(AND,EQUAL,start_asKey,relationInfo.getStartNodeInfo().getParameters() ) );
            end_node.append(addBlank(MATCH)).append( node(end_asKey,relationInfo.getEndNodeInfo().getLabels() ) ).
                    append( addBlank(WHERE) ).append(  symbols(AND,EQUAL,end_asKey,relationInfo.getEndNodeInfo().getParameters() ) );
        }

        /************ LINE **************/
        String line_asKey = R;
        StringBuffer sb_relation = s();
        sb_relation.append(addBlank(model.name()))
                .append( !relationInfo.getDirection() ?
                        nodeRelation(start_asKey,line_asKey,relationInfo.getType(),relationInfo.get_uuId(),end_asKey ) :
                        nodeRelationDirection(start_asKey,line_asKey,relationInfo.getType(),relationInfo.get_uuId(),end_asKey )
                );

        if( Validator.check(relationInfo.getParameters()) ){
            sb_relation.append(addBlank(SET)).append( symbols( COMMA,EQUAL,line_asKey,relationInfo.getParameters()) );
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
    /**
     * build new label cql info by create node
     * @param nodeInfo
     * @return
     */
    public List<String> addLabelByNodeIdBuilder(NodeInfo ... nodeInfo){
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






}
