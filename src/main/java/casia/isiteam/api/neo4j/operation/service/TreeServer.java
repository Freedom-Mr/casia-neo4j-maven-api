package casia.isiteam.api.neo4j.operation.service;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.result.RelationInfo;
import casia.isiteam.api.neo4j.datasource.dbdao.Neo4jCommonDb;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.util.CqlBuilder;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ClassName: TreeServer
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/28
 * Email: zhiyou_wang@foxmail.com
 */
public class TreeServer extends Neo4jCommonDb implements Neo4jOperationApi.TreeApi {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    /**
     * search all tree
     * @param labels
     * @return
     */
    public GraphResult searchAllTreeByLables(List<String> labels){
        if(!Validator.check(labels)){
            return new GraphResult();
        }
        StringBuffer cql = new StringBuffer();
        String node_a_asKey= CqlBuilder.asKey();
        String node_b_asKey= CqlBuilder.asKey();
        String relation_asKey= CqlBuilder.asKey();
        cql.append(MATCH).
                append(CqlBuilder.node(node_a_asKey,labels)).
                append( CROSS ).
                append(CqlBuilder.relation(relation_asKey)).
                append( CROSS ).
                append( CqlBuilder.node() ).
                append( COMMA ).
                append( CqlBuilder.node(node_b_asKey,labels) ).
                append(CqlBuilder.returnField(null,null,0L,1000L)
                );
        return executeReadCql(cql.toString());

    }
    /**
     * search all tree
     * @param labels
     * @return
     */
    public JSONArray searchAllJsonTreeByLables(List<String> labels){
        JSONArray jsonTree = new JSONArray();
        GraphResult graphResult = searchAllTreeByLables(labels);

        if( Validator.check(graphResult.getNodeInfos()) ){
            parseTree(jsonTree,graphResult.getNodeInfos(),graphResult.getRelationInfos(),new ArrayList<>(),1);
        }
        return jsonTree;
    }
    private void parseTree(JSONArray jsonArray , List<NodeInfo> nodeInfos, List<RelationInfo> relationInfos , List<NodeInfo> nextNodeInfos, int hierarchy_number){
        if( hierarchy_number == 1 ){
            nodeInfos.stream().filter(s->  Integer.parseInt( s.getParameters().get(HIERARCHY).toString() ) == hierarchy_number ).forEach(s->{
                jsonArray.add(JSONObject.parseObject(JSONObject.toJSONString(s)) );
            });
        }else{
            nextNodeInfos.stream().forEach(s->{
                jsonArray.add(JSONObject.parseObject(JSONObject.toJSONString(s)) );
            });
        }
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            List<NodeInfo> child = new ArrayList<>();
            relationInfos.forEach(c->{
                long _id = jsonObject.getLong(ID);
                if( c.getStartNodeId() == _id ){
                    nodeInfos.stream().filter(o-> o.getId() == c.getEndNodeId() ).forEach(o->{
                        child.add(o);
                    });
                }
            });
            if( Validator.check(child) ){
                JSONArray newJson = new JSONArray();
                parseTree( newJson, nodeInfos, relationInfos ,child , hierarchy_number+1);
                jsonArray.getJSONObject(i).put(NEXT,newJson);
            }
        }
        jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getJSONObject(PARAMETERS).getInteger(SORT)));
    }
}
