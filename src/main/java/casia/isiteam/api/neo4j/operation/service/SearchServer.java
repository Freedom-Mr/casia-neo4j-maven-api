package casia.isiteam.api.neo4j.operation.service;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.FieldCombine;
import casia.isiteam.api.neo4j.datasource.dbdao.Neo4jCommonDb;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.util.CqlBuilder;
import casia.isiteam.api.toolutil.Validator;
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
     * custom search cql
     * return GraphResult
     * @return
     */
    public GraphResult searchByCondition(String cql){
        return executeReadCql(cql);
    }
    /**
     * search Node
     */
    public GraphResult searchNode(){
        StringBuffer cql = new StringBuffer();
        String asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append(CqlBuilder.node(asKey)).
                append(CqlBuilder.returnField(asKey,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }
    public GraphResult searchNodeByLabel(List<String> labels){
        StringBuffer cql = new StringBuffer();
        String asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append( Validator.check(labels) ? CqlBuilder.node(asKey) : CqlBuilder.node(asKey,labels) ).
                append(CqlBuilder.returnField(asKey,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    };
    /**
     * search Node by PRIMARY KEY
     */
    public GraphResult searchNodeByIdIn(List<Long> ids){
        if( !Validator.check(ids) ){
            return new GraphResult();
        }
        StringBuffer cql = new StringBuffer();
        String asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append(CqlBuilder.node(asKey)).
                append( CqlBuilder.where(asKey, NONE ,ids.toArray(new Long[ids.size()])) ).
                append(CqlBuilder.returnField(asKey,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }
    public GraphResult searchNodeByIdNotIn(List<Long> ids){
        StringBuffer cql = new StringBuffer();
        String asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append(CqlBuilder.node(asKey)).
                append( CqlBuilder.where(asKey, NOT ,ids.toArray(new Long[ids.size()])) ).
                append(CqlBuilder.returnField(asKey,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }

    /**
     * search Node by build Parameters
     */
    public GraphResult searchNodeByParameters(FieldCombine parmsCombine,List<Attribute> attributes){
        StringBuffer cql = new StringBuffer();
        String asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append(CqlBuilder.node(asKey)).
                append( CqlBuilder.where(asKey, NONE, parmsCombine ,attributes) ).
                append(CqlBuilder.returnField(asKey,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }
    public GraphResult searchNodeByParametersNot(FieldCombine parmsCombine,List<Attribute> attributes){
        StringBuffer cql = new StringBuffer();
        String asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append(CqlBuilder.node(asKey)).
                append( CqlBuilder.where(asKey, NOT , parmsCombine ,attributes) ).
                append(CqlBuilder.returnField(asKey,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }
    /**
     * search Node by build node info
     */
    public GraphResult searchNodeByLabelAndParameters(List<String> labels,FieldCombine parmsCombine,List<Attribute> attributes ){
        StringBuffer cql = new StringBuffer();
        String asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append( Validator.check(labels) ? CqlBuilder.node(asKey,labels) : CqlBuilder.node(asKey)).
                append( CqlBuilder.where(asKey,NONE, parmsCombine ,attributes) ).
                append(CqlBuilder.returnField(asKey,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }
    public GraphResult searchNodeByLabelAndParametersNot(List<String> labels,FieldCombine parmsCombine,List<Attribute> attributes ){
        StringBuffer cql = new StringBuffer();
        String asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append( !Validator.check(labels) ? CqlBuilder.node(asKey) : CqlBuilder.node(asKey,labels) ).
                append( CqlBuilder.where(asKey, NOT , parmsCombine ,attributes) ).
                append(CqlBuilder.returnField(asKey,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }
    /**
     * search relation by limit
     * @return
     */
    public GraphResult searchRelation(){
        StringBuffer cql = new StringBuffer();
        String n_asKey= CqlBuilder.asKey();
        String r_asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append( CqlBuilder.node(n_asKey) ).
                append(CROSS).
                append( CqlBuilder.relation(r_asKey) ).
                append(CROSS).
                append( CqlBuilder.node(n_asKey) ).
                append(CqlBuilder.returnField( n_asKey ,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }
    /**
     * search all relationByNode
     * @param nodeInfo
     * @return
     */
    public GraphResult searchRelationByStartNode(NodeInfo nodeInfo){
        StringBuffer cql = new StringBuffer();
        String n_asKey= CqlBuilder.asKey();
        String r_asKey= CqlBuilder.asKey();
        if( Validator.check(nodeInfo.get_uuId()) ){
            nodeInfo.getParameters().put(_UUID,nodeInfo.get_uuId());
        }
        cql.append(MATCH).
                append( !Validator.check(nodeInfo.getLabels()) ? CqlBuilder.node(n_asKey) : CqlBuilder.node(n_asKey,nodeInfo.getLabels()) ).
                append(CROSS).
                append( CqlBuilder.relation(r_asKey) ).
                append(CROSS).
                append( CqlBuilder.node() ).
                append( Validator.check(nodeInfo.getId()) ?  CqlBuilder.where(n_asKey,NONE,nodeInfo.getId()) : CqlBuilder.where(n_asKey, NONE , FieldCombine.AND ,nodeInfo.getParameters() ) ).
                append(CqlBuilder.returnField( n_asKey ,returnFields,skip,limit)
                );
        return executeReadCql(cql.toString());
    }
    /**
     * query all labels
     */
    public GraphResult searchAllLabels(){
        return executeReadCql(CALL+BLANK+DB+DOT+LABELS+PARENTHESES);
    }
    /**
     * query all relation type
     */
    public GraphResult searchAllRelationshipType(){
        return executeReadCql(CALL+BLANK+DB+DOT+RELATIONSHIPTYPES+PARENTHESES);
    }

    public GraphResult searchNodeTotalByLabel(List<String> labels){
        StringBuffer cql = new StringBuffer();
        String n_asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append( !Validator.check(labels) ? CqlBuilder.node(n_asKey) : CqlBuilder.node(n_asKey,labels) ).
                append( CqlBuilder.returnCount(n_asKey)
                );
        return executeReadCql(cql.toString());
    }

}
