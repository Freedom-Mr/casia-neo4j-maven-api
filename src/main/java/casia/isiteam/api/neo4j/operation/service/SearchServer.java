package casia.isiteam.api.neo4j.operation.service;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.ConditionLevel;
import casia.isiteam.api.neo4j.common.enums.ParameterCombine;
import casia.isiteam.api.neo4j.datasource.dbdao.Neo4jCommonDb;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.toolutil.Validator;
import jdk.nashorn.internal.objects.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ClassName: SearchServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/26
 * Email: zhiyou_wang@foxmail.com
 */
public class SearchServer extends Neo4jCommonDb implements Neo4jOperationApi.QueryApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    /**
     * 返回标识，默认返回全部
     * @param field
     */
    public void setReturnField(String ... field){
        if( !Validator.check(field) ){
            return;
        }
        for(String info:field){
            if( !returnFields.contains(field) ){
                returnFields.add(info);
            }
        }
    }

    /**
     * 分页
     * @param from
     */
    public void setFrom(Long from){
        this.skip= from;
    }
    /**
     * 分页
     * @param size
     */
    public void setSize(Long size){
        this.limit= size;
    }
    /**
     * 开启打印表数据
     * @param
     */
    public void closeTableData(){
        this.openTableData= false;
    }

    /**
     * 开启节点之间关系信息(只对查询节点函数有效)
     */
    public void openNodeRelation(){
        this.openNodeRelation= true;
    }

    /**
     * custom search cql
     * return GraphResult
     * @return
     */
    public GraphResult searchByCondition(String cql){
        return executeDruidReadCql(this.openTableData,cql);
    }
    public GraphResult searchByCondition(String cql,Object ... values){
        return executeDruidReadCql(this.openTableData,cql,values);
    }

    /**
     * query all labels
     */
    public GraphResult searchAllLabels(){
        return executeDruidReadCql(this.openTableData,CALL+BLANK+DB+DOT+LABELS+PARENTHESES);
    }
    /**
     * query all relation type
     */
    public GraphResult searchAllRelationshipType(){
        return executeDruidReadCql(this.openTableData,CALL+BLANK+DB+DOT+RELATIONSHIPTYPES+PARENTHESES);
    }

    /**
     * search Node
     */
    public GraphResult searchNode(){
        GraphResult graphResult = executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( openNodeRelation ? addP(nodeRelation(A,PATH_1,NONE)) : node(A) ).
                append(returnField(A,returnFields,skip,limit)
                ).toString() );
        return graphResult;
    }
    public GraphResult searchNodeByLabel(List<String> labels){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( openNodeRelation ?
                        addP( node(A,labels)+CROSS+relation(PATH_1)+CROSS+node(NONE,labels) ) :
                        !Validator.check(labels) ? node(A) : node(A,labels) ).
                append( returnField(A,returnFields,skip,limit)
                ).toString());
    };
    /**
     * search Node by PRIMARY KEY
     */
    public GraphResult searchNodeByIdIn(ConditionLevel conditionLevel , List<Long> ids){
        if( !Validator.check(ids) ){
            return new GraphResult();
        }
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( openNodeRelation ? addP(nodeRelation(A,PATH_1,B)) : node(A) ).
                append( where(A, conditionLevel.getLevel() ,ids.toArray(new Long[ids.size()])) ).
                append( openNodeRelation ? where(B, conditionLevel.getLevel() ,ids.toArray(new Long[ids.size()])).replace(WHERE,AND) : NONE ).
                append( returnField(A,returnFields,skip,limit)
                ).toString());
    }

    /**
     * search Node by build Parameters
     */
    public GraphResult searchNodeByParameters(ConditionLevel conditionLevel , ParameterCombine parmsCombine, List<Attribute> attributes){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( openNodeRelation ? addP(nodeRelation(A,PATH_1,B)) : node(A) ).
                append( where(A, conditionLevel.getLevel(), parmsCombine ,attributes) ).
                append( openNodeRelation ? where(B, conditionLevel.getLevel() ,parmsCombine ,attributes).replace(WHERE,AND) : NONE  ).
                append(returnField(A,returnFields,skip,limit)
                ).toString());
    }
    /**
     * search Node by build node info
     */
    public GraphResult searchNodeByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes ){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( openNodeRelation ? addP(node(A,labels)+CROSS+relation(PATH_1)+CROSS+node(B,labels) )
                        : node(A,labels) ).
                append( where(A,conditionLevel.getLevel(), parmsCombine ,attributes) ).
                append( openNodeRelation ? where(B, conditionLevel.getLevel() ,parmsCombine ,attributes).replace(WHERE,AND) : NONE  ).
                append( returnField(A,returnFields,skip,limit)
                ).toString());
    }
    public GraphResult searchNodeTotal(){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( node(A) ).
                append( returnCount(A)
                ).toString() );
    }
    public GraphResult searchNodeTotalByLabel(List<String> labels){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( !Validator.check(labels) ? node(A) : node(A,labels) ).
                append( returnCount(A)
                ).toString() );
    }
    public GraphResult searchNodeTotalByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( !Validator.check(labels) ? node(A) : node(A,labels) ).
                append( where(A,conditionLevel.getLevel(),parmsCombine,attributes) ).
                append( returnCount(A)
                ).toString() );
    }
    public GraphResult searchRelationTotal(){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( String.format(nodeRelation_2,R) ).
                append( returnCount(R)
                ).toString() );
    }
    public GraphResult searchRelationTotalByType(List<String> types){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( String.format(nodeRelation_2,R+COLON+symbols(LINE,types)) ).
                append( returnCount(R)
                ).toString() );
    }
    public GraphResult searchRelationTotalByTypeAndParameters(List<String> types, ParameterCombine parmsCombine, List<Attribute> attributes){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( String.format(nodeRelation_2,R+COLON+symbols(LINE,types)) ).
                append( where(R,NONE,parmsCombine,attributes) ).
                append( returnCount(R)
                ).toString() );
    }


    /**
     * search relation by limit
     * @return
     */
    public GraphResult searchNodeRelation(){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( node(A) ).
                append(CROSS).
                append( relation(R) ).
                append(CROSS).
                append( node(B) ).
                append(returnField( B ,returnFields,skip,limit)
                ).toString());
    }
    public GraphResult searchNodeRelationByType(List<String> types){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( node(A) ).
                append(CROSS).
                append( relation(R,types) ).
                append(CROSS).
                append( node(B) ).
                append(returnField( B ,returnFields,skip,limit)
                ).toString());
    }
    /**
     * search Node by PRIMARY KEY
     */
    public GraphResult searchNodeRelationByIdIn(ConditionLevel conditionLevel , List<Long> ids){
        if( !Validator.check(ids) ){
            return new GraphResult();
        }
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( node(A) ).
                append(CROSS).
                append( relation(R) ).
                append(CROSS).
                append( node(B) ).
                append( where(R,conditionLevel.getLevel(),ids)).
                append(returnField( B ,returnFields,skip,limit)
                ).toString());
    }

    /**
     * search all relationByNode
     * @param nodeInfo
     * @return
     */
    public GraphResult searchNodeRelationByStartNode(NodeInfo nodeInfo){
        StringBuffer cql = new StringBuffer();
        if( Validator.check(nodeInfo.get_uuId()) ){
            nodeInfo.getParameters().put(_UUID,nodeInfo.get_uuId());
        }
        cql.append(MATCH).
                append( !Validator.check(nodeInfo.getLabels()) ? node(A) : node(A,nodeInfo.getLabels()) ).
                append(CROSS).
                append( relation(R) ).
                append(CROSS).
                append( node(B) ).
                append( Validator.check(nodeInfo.getId()) ?  where(A,NONE,nodeInfo.getId()) : where(A, NONE , ParameterCombine.AND ,nodeInfo.getParameters() ) ).
                append(returnField( A ,returnFields,skip,limit)
                );
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByNodeId(long id,int maxLevel){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( String.format(node_1,A) ).append(WHERE).append( String.format(id_1,A,id)).
                append( addBlank(CALL) ).append( String.format(apoc_1,A,maxLevel) ).
                append( addBlank(YIELD) ). append( symbols(COMMA,NODES,RELATIONSHIPS)).append( addBlank(RETURN) ).
                append(symbols(COMMA,NODES,RELATIONSHIPS)).append(skipLimit(skip,limit))
                .toString());
    }
    public GraphResult searchNodeRelationByStartNodeIdIn(ConditionLevel conditionLevel ,List<Long> ids){
        StringBuffer cql = new StringBuffer();
        cql.append(MATCH).
                append( node(A) ).
                append(CROSS).
                append( relation(R) ).
                append(CROSS).
                append( node(B) ).
                append( where(A,conditionLevel.getLevel(),ids)  ).
                append(returnField( A ,returnFields,skip,limit)
                );
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByStartNodeAndType(NodeInfo startNodeInfo,List<String> type){
        StringBuffer cql = new StringBuffer();
        if( Validator.check(startNodeInfo.get_uuId()) ){
            startNodeInfo.getParameters().put(_UUID,startNodeInfo.get_uuId());
        }
        cql.append(MATCH).
                append( !Validator.check(startNodeInfo.getLabels()) ? node(A) : node(A,startNodeInfo.getLabels()) ).
                append(CROSS).
                append( relation(R,type) ).
                append(CROSS).
                append( node(B) ).
                append( Validator.check(startNodeInfo.getId()) ?  where(A,NONE,startNodeInfo.getId()) : where(A, NONE , ParameterCombine.AND ,startNodeInfo.getParameters() ) ).
                append(returnField( A ,returnFields,skip,limit)
                );
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByStartNodeIdInAndType(List<Long> start_node_ids ,List<String> type){
        StringBuffer cql = s();
        cql.append(MATCH).
                append(  node(A) ).
                append(CROSS).
                append( relation(R,type) ).
                append(CROSS).
                append( node(B) ).
                append(   where(A,NONE,start_node_ids)).
                append(returnField( A ,returnFields,skip,limit)
                );
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByStartNodeAndTypeAndEndNode(NodeInfo startNodeInfo,List<String> type,NodeInfo endNodeInfo){
        StringBuffer cql = new StringBuffer();
        if( Validator.check(startNodeInfo.get_uuId()) ){
            startNodeInfo.getParameters().put(_UUID,startNodeInfo.get_uuId());
        }
        if( Validator.check(endNodeInfo.get_uuId()) ){
            endNodeInfo.getParameters().put(_UUID,endNodeInfo.get_uuId());
        }
        cql.append(MATCH).
                append( !Validator.check(startNodeInfo.getLabels()) ? node(A) : node(A,startNodeInfo.getLabels()) ).
                append(CROSS).
                append( relation(R,type) ).
                append(CROSS).
                append( !Validator.check(endNodeInfo.getLabels()) ? node(B) : node(B,endNodeInfo.getLabels()) ).
                append( Validator.check(startNodeInfo.getId()) ?  where(A,NONE,startNodeInfo.getId()) : where(A, NONE , ParameterCombine.AND ,startNodeInfo.getParameters() ) ).
                append( (Validator.check(endNodeInfo.getId()) ?  where(B,NONE,endNodeInfo.getId()) : where(B, NONE , ParameterCombine.AND ,endNodeInfo.getParameters() )).replace(WHERE,AND) ).
                append(returnField( skip,limit)
                );
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByStartNodeIdAndTypeAndEndNodeId(List<Long> start_node_ids,List<String> type,List<Long> end_node_ids){
        StringBuffer cql = new StringBuffer();
        cql.append(MATCH).
                append( node(A) ).
                append(CROSS).
                append( relation(R,type) ).
                append(CROSS).
                append( node(B) ).
                append( where(A,NONE,start_node_ids) ).
                append( where(B,NONE,end_node_ids).replace(WHERE,AND) ).
                append(returnField( skip,limit)
                );
        return executeDruidReadCql(this.openTableData,cql.toString());
    }

    /**
     * 多点精确，根据多个节点的id，扩展出全部关联的节点
     * @return
     */
    public GraphResult searchNodeRelationOnPrecisionByNodeIds(List<Long> ids){
        StringBuffer match_ =s();
        StringBuffer where_ =s();
        StringBuffer return_ =s();
        ids.forEach(s->{
            match_.append(match_.length()==0?MATCH+BLANK:COMMA).append(P+s).append(EQUAL).append(nodeRelation(A+s,NONE,B));
            where_.append(where_.length()==0?WHERE:AND).append(where(A+s,NONE,s).replace(WHERE,NONE));
            return_.append(return_.length()==0?addBlank(RETURN):COMMA).append(P+s);
        });
        return executeDruidReadCql(this.openTableData,
                match_.append(where_).append(return_).append(page())
                .toString()
        );
    }
    /**
     * 多点模糊，根据多个节点的id，扩展出两两关联的节点
     * @return
     */
    public GraphResult searchNodeRelationOnFuzzyByNodeIds(List<Long> ids){
        String r1_asKey =asKey();
        String r2_asKey =asKey();
        return executeDruidReadCql(this.openTableData,
                s().append(MATCH).
                        append( node(A) ).
                        append(CROSS).
                        append( relation(r1_asKey) ).
                        append(CROSS).
                        append( node(C)).
                        append(CROSS).
                        append( relation(r2_asKey) ).
                        append(CROSS).
                        append( node(B) ).
                        append( where(A,NONE,ids) ).
                        append( where(B,NONE,ids).replace(WHERE,AND) ).
                        append(returnField( skip,limit)
                        ).toString()
        );
    }

    public GraphResult searchNodeRelationOnShortPathByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id,int pathLength){
        return executeDruidReadCql(this.openTableData,
                s().append(MATCH).append(BLANK).append(P).append(EQUAL).
                        append( ALLSHORTESTPATHS ).append(LEFT_PARENTHESES).
                        append(nodeRelation(A,RANGE_PATH_1_+(pathLength<2?2:pathLength),B)).append(RIGHT_PARENTHESES).append(where(A,NONE,start_node_id)).
                        append(AND)
                        .append(where(B,NONE,end_node_id).replace(WHERE,NONE)).
                        append(returnField(P, skip,limit)
                        ).toString()
        );
    }

    public GraphResult searchNodeOnFullByQueryString(String indexName,String queryString){
        return executeDruidReadCql(this.openTableData,
                s().append(node_full_text(indexName,queryString)).
                        append(returnField(skip,limit)
                        ).toString()
        );
    }
    public GraphResult searchNodeRelationOnFullByQueryString(String indexName,String queryString){
        return executeDruidReadCql(this.openTableData,
                s().append(relation_full_text(indexName,queryString)).
                        append(returnField(skip,limit)
                        ).toString()
        );
    }
}
