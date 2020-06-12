package casia.isiteam.api.neo4j.operation.service;

import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.ConditionLevel;
import casia.isiteam.api.neo4j.common.enums.ParameterCombine;
import casia.isiteam.api.neo4j.datasource.dbdao.Neo4jCommonDb;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
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
        return executeDruidWriteCql(MATCH+ node(asKey)+ where(asKey,NONE,id)+ DELETE+BLANK+asKey);
    }
    public boolean delNodeByIdIn(ConditionLevel conditionLevel , List<Long> ids){
        String asKey= asKey();
        return executeDruidWriteCql(s().append(MATCH).
                append(node(asKey)).
                append( where(asKey, conditionLevel.getLevel() ,ids.toArray(new Long[ids.size()]))).
                append(  AddBlank.addBlank(DELETE) ).append(asKey).toString());
    }
    public boolean delNodeByLabels(List<String> labels){
        return executeDruidWriteCql(MATCH+ node(A,labels)+AddBlank.addBlank(DELETE)+A);
    }
    public boolean delNodeByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes ){
        return executeDruidWriteCql(s().append(MATCH).
                append( Validator.check(labels) ? node(A,labels) : node(A)).
                append( where(A,NONE, parmsCombine ,attributes)).
                append(  AddBlank.addBlank(DELETE) ).append(A).toString());
    }



    public boolean delRelationById(long id){
        String asKey = asKey();
        return executeDruidWriteCql(MATCH+ nodeRelation(NONE,asKey,NONE)+ where(asKey,NONE,id)+ DELETE+BLANK+asKey,null);
    }
    public boolean delRelationByIdIn(ConditionLevel conditionLevel , List<Long> ids){
        StringBuffer cql = new StringBuffer();
        String asKey= asKey();
        cql.append(MATCH).
                append( nodeRelation(NONE,asKey,NONE) ).
                append( where(asKey, conditionLevel.getLevel() ,ids.toArray(new Long[ids.size()]))).
                append(  AddBlank.addBlank(DELETE) ).append(asKey);
        return executeDruidWriteCql(cql.toString());
    }
    public boolean delRelationByType(ConditionLevel conditionLevel ,String type){
        return executeDruidWriteCql( s().append(MATCH).
                append( nodeRelation(NONE,A,type,NONE) ).
                append(  AddBlank.addBlank(DELETE) ).append(A).toString());
    }
    public boolean delRelationByTypeAndParameters(ConditionLevel conditionLevel , String type, ParameterCombine parmsCombine, List<Attribute> attributes){
        return executeDruidWriteCql(s().append(MATCH).
                append( nodeRelation(NONE,B,type,NONE) ).
                append( where(B,conditionLevel.getLevel(), parmsCombine ,attributes)).
                append(  AddBlank.addBlank(DELETE) ).append(B).toString());
    }
    public boolean delLabelByNodeId(long _id,List<String> labels){
        return executeDruidWriteCql(s().append(MATCH).append(node(A)).append(where(A, ConditionLevel.MUST.getLevel(),_id)).
                append(remove(A,labels)).toString());
    }

    public boolean delRelationByStartNodeId(long start_node_id){
        return executeDruidWriteCql( s().append(MATCH).
                append( nodeRelation(A,R,NONE) ).
                append( where(A,NONE,start_node_id)).
                append(  AddBlank.addBlank(DELETE) ).append(R).toString());
    }
    public boolean delRelationByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id){
        return executeDruidWriteCql( s().append(MATCH).
                append( nodeRelation(A,R,B) ).
                append( addBlank(WHERE)).
                append(ID).append(LEFT_PARENTHESES).append(A).append(RIGHT_PARENTHESES).append(EQUAL).append(start_node_id).
                append( addBlank(AND)  ).
                append(ID).append(LEFT_PARENTHESES).append(B).append(RIGHT_PARENTHESES).append(EQUAL).append(end_node_id).
                append( addBlank(DELETE) ).append(R).toString());
    }
    public boolean delRelationByStartNodeIdAndTypeAndEndNodeId(long start_node_id,String type,long end_node_id){
        return executeDruidWriteCql( s().append(MATCH).
                append( nodeRelation(A,R,type,B) ).
                append( addBlank(WHERE)).
                append(ID).append(LEFT_PARENTHESES).append(A).append(RIGHT_PARENTHESES).append(EQUAL).append(start_node_id).
                append( addBlank(AND) ).
                append(ID).append(LEFT_PARENTHESES).append(B).append(RIGHT_PARENTHESES).append(EQUAL).append(end_node_id).
                append(  addBlank(DELETE) ).append(R).toString());
    }
}
