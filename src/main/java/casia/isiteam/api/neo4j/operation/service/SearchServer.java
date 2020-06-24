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
     * 开启节点之间关系信息(只对查询节点函数有效)
     */
    public void openDirection(){
        this.openDirection= true;
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
                append( this.openNodeRelation ? addP(nodeRelation(A,PATH_1,NONE)) : node(A) ).
                append( returnField(A,returnFields)
                ).append( page() ).toString() );
        return graphResult;
    }
    public GraphResult searchNodeByLabel(List<String> labels){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( this.openNodeRelation ?
                        addP(nodeRelation(A+symbolsAll(COLON,labels),PATH_1,B+symbolsAll(COLON,labels))) :
                        node(A,labels) ).
                append( returnField(A,returnFields)
                ).append( page() ).toString() );
    };
    /**
     * search Node by PRIMARY KEY
     */
    public GraphResult searchNodeByIdIn(ConditionLevel conditionLevel , List<Long> ids){
        if( !Validator.check(ids) ){
            return new GraphResult();
        }
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( this.openNodeRelation ? addP(nodeRelation(A,PATH_1,B)) : node(A) ).
                append(addBlank(WHERE)).append( conditionLevel.getLevel() ).
                append( addId(A,ids) ).
                append( this.openNodeRelation ?
                          addBlank(AND) +
                                  conditionLevel.getLevel()  + addId(B, ids ) : NONE).
                                        append( returnField(A,returnFields)
                                        ).append( addLimit(this.skip,this.limit) ).toString());
    }

    /**
     * search Node by build Parameters
     */
    public GraphResult searchNodeByParameters(ConditionLevel conditionLevel , ParameterCombine parmsCombine, List<Attribute> attributes){
        StringBuffer s = s();
        s.append(MATCH).append( this.openNodeRelation ? addP(nodeRelation(A,PATH_1,B)) : node(A) );
        if( Validator.check(attributes) ){
            s. append( addBlank(WHERE) ).append( conditionLevel.getLevel() ).
                    append( node(symbols( parmsCombine.name() ,A, attributes)) ).
                    append( this.openNodeRelation ? addBlank(AND) + conditionLevel.getLevel() + node( symbols( parmsCombine.name() ,B, attributes)) : NONE );
        }
        return executeDruidReadCql(this.openTableData,
                s.append( returnField(A,returnFields)
                ).append( page() ).toString());
    }
    /**
     * search Node by build node info
     */
    public GraphResult searchNodeByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes ){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( this.openNodeRelation ?
                        addP( nodeRelation( A+symbolsAll(COLON,labels), PATH_1, B+symbolsAll(COLON,labels)) )
                        : node(A,labels) ).
                append( addBlank(WHERE)).append(conditionLevel.getLevel() ).
                append( symbols(parmsCombine.name(),A,attributes) ).
                append( this.openNodeRelation ? addBlank(AND) + conditionLevel.getLevel()  + symbols( parmsCombine.name() ,B, attributes) : NONE  ).
                append( returnField(A,returnFields)
                ).append( page() ).toString());
    }


    public GraphResult searchNodeTotal(){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( node(A) ).
                append( returnCount(A)
                ).toString() );
    }
    public GraphResult searchNodeTotalByLabel(List<String> labels){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( node(A,labels) ).
                append( returnCount(A) ).toString() );
    }
    public GraphResult searchNodeTotalByLabelAndParameters(ConditionLevel conditionLevel , List<String> labels, ParameterCombine parmsCombine, List<Attribute> attributes){
        return executeDruidReadCql(this.openTableData, s().append(MATCH).
                append( node(A,labels) ).append(addBlank(WHERE)).append(conditionLevel.getLevel()).
                append( symbols(parmsCombine.name(),A,attributes) ).
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
                append(addBlank(WHERE)).
                append( symbols(parmsCombine.name(),R,attributes) ).
                append( returnCount(R)
                ).toString() );
    }


    /**
     * search relation by limit
     * @return
     */
    public GraphResult searchNodeRelation(){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( this.openNodeRelation ? nodeRelationDirection(A,R,B) : nodeRelation(A,R,B) ).
                append( returnField( A ,returnFields)).append(page())
                .toString());
    }
    public GraphResult searchNodeRelationByType(List<String> types){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                        append( this.openNodeRelation ? nodeRelationDirection(A,R ,types,B) : nodeRelation(A,R,types,B) ).
                        append( returnField( A ,returnFields)).append(page())
                .toString());
    }
    /**
     * search Node by PRIMARY KEY
     */
    public GraphResult searchNodeRelationByIdIn(ConditionLevel conditionLevel , List<Long> ids){
        if( !Validator.check(ids) ){
            return new GraphResult();
        }
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( this.openDirection ? nodeRelationDirection(A,R,B): nodeRelation(A,R,B) ).append(addBlank(WHERE)).append(conditionLevel.getLevel()).
                append(addId(R,ids)).
                append( returnField( B ,returnFields)).append(page()
                ).toString());
    }

    /**
     * search all relationByNode
     * @param nodeInfo
     * @return
     */
    public GraphResult searchNodeRelationByStartNode(NodeInfo nodeInfo){
        StringBuffer cql = s();
        if( Validator.check(nodeInfo.get_uuId()) ){
            nodeInfo.getParameters().put(_UUID,nodeInfo.get_uuId());
        }
        cql.append(MATCH).
                append( !this.openDirection ?  nodeRelation(A+symbolsAll(COLON,nodeInfo.getLabels()),R,B)  :  nodeRelationDirection( A+symbolsAll(COLON,nodeInfo.getLabels()),R,B));
                if( Validator.check(nodeInfo.getId()) && nodeInfo.getId()>-1 ){
                    cql.append(addBlank(WHERE)).append(addId(A,nodeInfo.getId()));
                }else if( Validator.check(nodeInfo.getParameters()) ){
                    cql.append(addBlank(WHERE)).append(symbols(ParameterCombine.AND.name(),EQUAL,A,nodeInfo.getParameters()));
                }
        cql.append(returnField( A ,returnFields)).append(page());
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByNodeId(long id,int maxLevel,String relationshipFilter,String labelFilter){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( node(A) ).append(WHERE).append( addId(A,id)).
                append( String.format(apoc_1,A,addApocParms(MAXLEVEL,maxLevel,LIMIT,this.limit,RELATIONSHIPFILTER,relationshipFilter,LABELFILTER,labelFilter)) ).
                append( addBlank(YIELD) ). append( symbols(COMMA,NODES,RELATIONSHIPS) ).append( addBlank(RETURN) ).
                append( symbols(COMMA,NODES,RELATIONSHIPS) )
                .toString());
    }
    public GraphResult searchNodeRelationByStartNodeIdIn(ConditionLevel conditionLevel ,List<Long> ids){
        StringBuffer cql = new StringBuffer();
        cql.append(MATCH).
                append( this.openDirection ? nodeRelationDirection(A,R,B):nodeRelation(A,R,B) ).
                append(addBlank(WHERE)).append(addBlank(conditionLevel.getLevel())).append(addId(A,ids)).
                append(returnField( A ,returnFields)).append( page()
                );
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByStartNodeAndType(NodeInfo startNodeInfo,List<String> type){
        StringBuffer cql = new StringBuffer();
        if( Validator.check(startNodeInfo.get_uuId()) ){
            startNodeInfo.getParameters().put(_UUID,startNodeInfo.get_uuId());
        }
        cql.append(MATCH).
                append( this.openDirection ? nodeRelationDirection(A+symbolsAll(COLON,startNodeInfo.getLabels()),R,type,B):
                        nodeRelation(A+symbolsAll(COLON,startNodeInfo.getLabels()),R,type,B) ).
                append(addBlank(WHERE)).
                append( Validator.check(startNodeInfo.getId()) ?  addId(A,startNodeInfo.getId()) : symbols(  ParameterCombine.AND.name() ,EQUAL ,A,startNodeInfo.getParameters() ) ).
                append(returnField( A ,returnFields)).append(page()
                );
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByStartNodeIdInAndType(List<Long> start_node_ids ,List<String> type){
        StringBuffer cql = s();
        cql.append(MATCH).
                append( this.openDirection ? nodeRelationDirection(A,R,type,B):nodeRelation(A,R,type,B) ).
                append(addBlank(WHERE)).
                append(   addId(A,start_node_ids)).
                append(returnField( A ,returnFields)).append(page()
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
                append( openDirection ? nodeRelationDirection(A+symbolsAll(COLON,startNodeInfo.getLabels()),R,type,B+symbolsAll(COLON,endNodeInfo.getLabels())):
                        nodeRelation(A+symbolsAll(COLON,startNodeInfo.getLabels()),R,type,B+symbolsAll(COLON,endNodeInfo.getLabels()) ) )
                ;
                StringBuffer where = s();
                where.append( startNodeInfo.getId()>-1 ?  addId(A,startNodeInfo.getId()) : symbols(ParameterCombine.AND.name() ,EQUAL,A ,startNodeInfo.getParameters() ) ).
                append(where.length()>0 ? addBlank(AND) : NONE).append( endNodeInfo.getId()>-1 ?   addId(A,endNodeInfo.getId()) : symbols(ParameterCombine.AND.name() ,EQUAL,B ,endNodeInfo.getParameters() ) );
                cql.append( where.length()> 0 ? addBlank(WHERE)+where : NONE ).append(returnField(A,returnFields)).append(page());
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    public GraphResult searchNodeRelationByStartNodeIdAndTypeAndEndNodeId(List<Long> start_node_ids,List<String> type,List<Long> end_node_ids){
        StringBuffer cql = new StringBuffer();
        cql.append(MATCH).
                append( this.openDirection ? nodeRelationDirection(A,R,type,B):
                        nodeRelation(A,R,type,B)).append(addBlank(WHERE)).append(addId(A,start_node_ids)).append(addBlank(AND)).append(addId(B,end_node_ids))
                .append(returnField(A,returnFields)).append(page());
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
            where_.append(where_.length()==0?WHERE:AND).append(addId(A+s,s));
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
                        append(addBlank(WHERE)).
                        append( addId(A,ids) ).append(addBlank(AND)).
                        append(  addId(B,ids) ).
                        append( returnField(C,returnFields) ).append(page())
                        .toString()
        );
    }

    /**
     * 最短路径
     * @param start_node_id
     * @param end_node_id
     * @param pathLength
     * @return
     */
    public GraphResult searchNodeRelationOnShortPathByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id,int pathLength){
        return executeDruidReadCql(this.openTableData,
                s().append(MATCH).append(BLANK).append(P).append(EQUAL).
                        append( ALLSHORTESTPATHS ).append(LEFT_PARENTHESES).
                        append(nodeRelation(A,RANGE_PATH_1_+(pathLength<2?2:pathLength),B)).append(RIGHT_PARENTHESES).
                        append( addBlank(WHERE)).
                        append( addId(A,start_node_id)).
                        append(AND)
                        .append( addId(B,end_node_id) ).
                        append(returnField(P)).append(page()
                        ).toString()
        );
    }

    /**
     * 两点关联发现
     * 根据关系层级范围，查询两节点关系详情
     * @param start_node_id
     * @param end_node_id
     * @param startPathLength
     * @param endPathLength
     * @return
     */
    public GraphResult searchNodeRelationOnPathLengthByStartNodeIdAndEndNodeId(long start_node_id,long end_node_id,int startPathLength,int endPathLength){
        String pathLength = STAR+startPathLength+".."+endPathLength;
        return executeDruidReadCql(this.openTableData,
                s().append(MATCH).append(BLANK).append(
                        addP( this.openDirection ? nodeRelationDirection(A,pathLength,B) :nodeRelation(A,pathLength,B) )
                ).append( addBlank(WHERE)).
                        append( addId(A,start_node_id)).
                        append(AND)
                        .append( addId(B,end_node_id) ).
                        append(returnField(P)).append(page()
                ).toString()
        );
    }

    public GraphResult searchNodeOnFullByQueryString(String indexName,String queryString){
        return executeDruidReadCql(this.openTableData,
                s().append(node_full_text(indexName,queryString)).
                        append(returnAll()).append(page()
                        ).toString()
        );
    }
    public GraphResult searchNodeRelationOnFullByQueryString(String indexName,String queryString){
        return executeDruidReadCql(this.openTableData,
                s().append(relation_full_text(indexName,queryString)).
                        append(returnAll()).append(page()
                        ).toString()
        );
    }
    public GraphResult searchNodeRelationOnExtendTreeByNodeIdIn(List<Long> ids,int maxLevel,String relationshipFilter,String labelFilter ){
        return executeDruidReadCql(this.openTableData,s().append(MATCH).
                append( node(A) ).append(WHERE).append( addId(A,ids)).
                append( String.format(apoc_2,A,addApocParms(MAXLEVEL,maxLevel,LIMIT,this.limit,RELATIONSHIPFILTER,relationshipFilter,LABELFILTER,labelFilter)) ).
                append( addBlank(YIELD) ). append( "path" ).append( addBlank(RETURN) ).
                append( "path" )
                .toString());
    }

}
