package casia.isiteam.api.neo4j.operation.service;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.result.RelationInfo;
import casia.isiteam.api.neo4j.datasource.dbdao.Neo4jCommonDb;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.util.LogsUtil;
import casia.isiteam.api.neo4j.util.build.AddBlank;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
            logger.warn(LogsUtil.compositionLogEmpty("labels"));
            return new GraphResult();
        }
        StringBuffer cql = new StringBuffer();
        cql.append(MATCH).
                append( node(A,labels) ).
                append( AddBlank.addBlank(OPTIONAL) ).
                append( MATCH ).
                append(node(B,labels)).
                append( CROSS ).
                append(relation(R)).
                append( CROSS ).
                append( node() ).
                append(returnField(null,null,0L,1000L));
        return executeDruidReadCql(this.openTableData,cql.toString());
    }
    /**
     * search all tree
     * @param labels
     * @return
     */
    public JSONArray searchAllJsonTreeAndCountByLables(List<String> labels){
        JSONArray jsonTree = new JSONArray();
        GraphResult graphResult = searchAllTreeByLables(labels);

        if( Validator.check(graphResult.getNodeInfos()) ){
            parseTree(true,jsonTree,graphResult.getNodeInfos(),graphResult.getRelationInfos(),new ArrayList<>(),1);
        }
        return jsonTree;
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
            parseTree(false,jsonTree,graphResult.getNodeInfos(),graphResult.getRelationInfos(),new ArrayList<>(),1);
        }
        return jsonTree;
    }
    private void parseTree(boolean searchCount,JSONArray jsonArray , List<NodeInfo> nodeInfos, List<RelationInfo> relationInfos , List<NodeInfo> nextNodeInfos, int hierarchy_number){
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
                parseTree(searchCount, newJson, nodeInfos, relationInfos ,child , hierarchy_number+1);
                jsonArray.getJSONObject(i).put(NEXT,newJson);
                if( searchCount ){
                    long sum = 0L;
                    for(Object s:newJson){
                        long count = JSON.parseObject(s.toString()).getLong(COUNT);
                        sum = sum +count;
                    }
                    jsonArray.getJSONObject(i).put(COUNT,sum);
                }
            }else{
                if( searchCount ){
                    jsonArray.getJSONObject(i).put(COUNT,searchNodeTotalByLabel( Arrays.asList(jsonObject.getJSONObject(PARAMETERS).getString(_NAME) )));
                }
            }
        }
        jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getJSONObject(PARAMETERS).getInteger(SORT)));
    }

    private long searchNodeTotalByLabel(List<String> labels){
        StringBuffer cql = new StringBuffer();
        String n_asKey= asKey();
        cql.append(MATCH).
                append( !Validator.check(labels) ? node(n_asKey) : node(n_asKey,labels) ).
                append( returnCount(n_asKey)
                );
        List<LinkedHashMap<String,Object>> counts = executeDruidReadCql(this.openTableData,cql.toString()).getKwds();
        if( Validator.check(counts) ){
            Optional<Object> count =  counts.get(0).values().stream().findFirst();
           return Long.parseLong(count.get().toString());
        }
        return 0L;
    }

}
