package casia.isiteam.api.neo4j.operation.service;

import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.ConditionLevel;
import casia.isiteam.api.neo4j.common.enums.ParameterCombine;
import casia.isiteam.api.neo4j.datasource.dbdao.Neo4jCommonDb;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.util.LogsUtil;
import casia.isiteam.api.neo4j.util.build.*;
import casia.isiteam.api.toolutil.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ClassName: DeleteServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/26
 * Email: zhiyou_wang@foxmail.com
 */
public class DeleteServer extends Neo4jCommonDb implements Neo4jOperationApi.DeleteApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    public boolean delAll(){
        return executeDruidWriteCql(MATCH+ node(A)+ OPTIONAL +BLANK + MATCH+BLANK+ nodeRelation(A,R,NONE)+DELETE+BLANK+symbols(COMMA,R,A));
    }
    public boolean del(String cql){
        if( Validator.check(cql) ){
            return executeDruidWriteCql(cql);
        }
        return false;
    }
    public boolean del(String cql,Object ... values){
        if( Validator.check(cql) ){
            return executeDruidWriteCql(cql,values);
        }
        return false;
    }
    public boolean del(String cql,List<Object[]> values){
        if( Validator.check(cql) ){
            return executeDruidWriteCql(cql,values);
        }
        return false;
    }

    public boolean delNodeById(long id){
        String asKey = asKey();
        return executeDruidWriteCql(MATCH+ node(asKey)+ addBlank(WHERE)+ addId(asKey,id)+ DELETE+BLANK+asKey);
    }
    public boolean delNodeByIdIn(ConditionLevel conditionLevel , List<Long> ids){
        String asKey= asKey();
        return executeDruidWriteCql(s().append(MATCH).
                append(node(asKey)).
                append( addBlank(WHERE) ).append( addBlank(conditionLevel.getLevel())).
                append( addId(asKey, ids) ).
                append( addBlank(DELETE) ).append(asKey).toString());
    }
    public boolean delNodeByLabels(List<String> labels){
        return executeDruidWriteCql(MATCH+ node(A,labels)+ addBlank(DELETE)+A);
    }
    public boolean delNodeByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes ){
        return executeDruidWriteCql(s().append(MATCH).
                append( node(A,labels) ).
                append( addBlank(WHERE) ).append( addBlank(conditionLevel.getLevel())).
                append( symbols( parmsCombine.AND.name() ,A ,attributes)).
                append(  CqlInfoParms.addBlank(DELETE) ).append(A).toString());
    }



    public boolean delRelationById(long id){
        String asKey = asKey();
        return executeDruidWriteCql(MATCH+ nodeRelation(NONE,asKey,NONE)+
                addBlank(WHERE) + addId(asKey,id)+ DELETE+BLANK+asKey );
    }
    public boolean delRelationByIdIn(ConditionLevel conditionLevel , List<Long> ids){
        StringBuffer cql = new StringBuffer();
        String asKey= asKey();
        cql.append(MATCH).
                append( nodeRelation(NONE,asKey,NONE) ).
                append( addBlank(WHERE) ).append( addBlank(conditionLevel.getLevel())).
                append( addId( asKey ,ids)).
                append(  addBlank(DELETE) ).append(asKey);
        return executeDruidWriteCql(cql.toString());
    }
    public boolean delRelationByType(ConditionLevel conditionLevel ,String type){
        return executeDruidWriteCql( s().append(MATCH).
                append( nodeRelation(NONE,R,type,NONE) ).
                append( addBlank(DELETE) ).append(R).toString());
    }
    public boolean delRelationByTypeAndParameters(ConditionLevel conditionLevel , String type, ParameterCombine parmsCombine, List<Attribute> attributes){
        return executeDruidWriteCql(s().append(MATCH).
                append( nodeRelation(NONE,B,type,NONE) ).
                append( addBlank(WHERE) + addBlank(conditionLevel.getLevel()) ).
                append( symbols( parmsCombine.name() ,B,attributes)).
                append(  CqlInfoParms.addBlank(DELETE) ).append(B).toString());
    }
    public boolean delLabelByNodeId(long _id,List<String> labels){
        if( Validator.check(labels) ){
            return executeDruidWriteCql(s().append(MATCH).append(node(A)). append( addBlank(WHERE)).append(addId(A,_id)).
                    append(addBlank(REMOVE)).append(A).append(symbolsAll(COLON,labels)).toString() );
        }
        logger.warn(LogsUtil.compositionLogEmpty(" labels "));
        return false;
    }

    public boolean delRelationByStartNodeId(long start_node_id){
        return executeDruidWriteCql( s().append(MATCH).
                append( nodeRelation(A,R,NONE) ).
                append( addBlank(WHERE)).
                append( addId(A,start_node_id)).
                append(  CqlInfoParms.addBlank(DELETE) ).append(R).toString());
    }
    public boolean delRelationByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id){
        return executeDruidWriteCql( s().append(MATCH).
                append( nodeRelation(A,R,B) ).
                append( addBlank(WHERE)).
                append( symbols(addBlank(AND),addId(A,start_node_id),addId(B,end_node_id)) ).
                append( addBlank(DELETE) ).append(R).toString());
    }
    public boolean delRelationByStartNodeIdAndTypeAndEndNodeId(long start_node_id,String type,long end_node_id){
        return executeDruidWriteCql( s().append(MATCH).
                append( nodeRelation(A,R,type,B) ).
                append( addBlank(WHERE)).
                append( symbols(addBlank(AND),addId(A,start_node_id),addId(B,end_node_id)) ).
                append( String.format(delete_1,R) ).toString());
    }
}
