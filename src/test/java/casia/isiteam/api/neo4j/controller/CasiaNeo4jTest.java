package casia.isiteam.api.neo4j.controller;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.common.enums.FieldCombine;
import casia.isiteam.api.neo4j.common.enums.MatchLevel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CasiaNeo4jTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaNeo4jTest extends TestCase {

    public void testCreateNode() {
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("disk");

        /*casiaNeo4j.create().createNode(CreateType.MERGE,new NodeInfo().set_uuId(12));
        casiaNeo4j.create().createNode(CreateType.MERGE,new NodeInfo().set_uuId(13).setLabels("博客"));
        casiaNeo4j.create().createNode(CreateType.MERGE,new NodeInfo().set_uuId(14).setLabels("博客","微博"));

        Map<String,Object> parms = new HashMap<>();
        parms.put("site","新浪");
        parms.put("status",1);
        casiaNeo4j.create().createNode(CreateType.MERGE,new NodeInfo().set_uuId(16).setLabels("博客","微博").setParameters(parms));*/

//        GraphResult graphResult = casiaNeo4j.create().create("merge (a:创建{_uuId:'123'}) return *");
//        out(graphResult);

        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("_uuId",123);
        boolean graphResult = casiaNeo4j.create().create("UNWIND {batch} AS row MERGE (e:创建{_uuId:row._uuId})","batch",jsonObject);
        System.out.println(graphResult);*/

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("_uuId",234);
        jsonArray.add(jsonObject);
        /*boolean graphResult = casiaNeo4j.create().create("UNWIND {batch} AS row MERGE (e:创建{_uuId:row._uuId})","batch",jsonArray);
        System.out.println(graphResult);*/

    }
    public void testCreateRelation() {
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("disk");

        Map<String,Object> parms = new HashMap<>();
        parms.put("status",1);

        Map<String,Object> parms1 = new HashMap<>();
        parms.put("_name","张三");

        Map<String,Object> parms2 = new HashMap<>();
        parms.put("_name","李四");

        casiaNeo4j.create().createRelation(CreateType.MERGE,
                new RelationshipInfo(
                    new NodeInfo().setParameters(parms1),
                    new NodeInfo().setParameters(parms2)
                ).set_uuId(12).setType("同事"));

        casiaNeo4j.create().createRelationByNodeId(CreateType.MERGE,
                new RelationshipInfo(
                        new NodeInfo().setId(20L),
                        new NodeInfo().setId(0L)
                ).set_uuId(12).setType("测试"));

        casiaNeo4j.create().createRelationByNodeUuId(CreateType.MERGE,
                new RelationshipInfo(
                        new NodeInfo().set_uuId(234),
                        new NodeInfo().set_uuId(11)
                ).setType("测试2"),
                new RelationshipInfo(
                        new NodeInfo().set_uuId(234),
                        new NodeInfo().set_uuId(11)
                ).setType("测试3")
        );
    }

    public void testQuery() {
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("disk");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("match (a) return *");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("match (a)-[r]-(b) return *");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("match p=(a)-[r]-()-[]-() return p");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("MATCH p=(a:`老师1`)-->() RETURN p");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("MATCH p=()-->() RETURN p LIMIT 1");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("MATCH (a)-[r]-(b) RETURN a,count(a) as c,a._uuId as u");
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).setReturnField("<id>","_name").queryNodeById(0L);
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).queryNodeByParameters(
//                FieldCombine.OR,
//                new Attribute("_name","张三"),
//                new Attribute("id",1)
//        );
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).queryNodeByLabelAndParameters(new String[]{"学生"}, FieldCombine.AND,new Attribute(MatchLevel.Term,"_name","张"));
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).queryNodeByLabelAndParameters(new String[]{"卫星信息"}, null,null);
//        GraphResult graphResult = casiaNeo4j.query().queryAllLabels();
//        GraphResult graphResult = casiaNeo4j.query().queryAllRelationshipType();
//        GraphResult graphResult = casiaNeo4j.query().queryNodeTotalByLabel("卫星信息");
        GraphResult graphResult = casiaNeo4j.query().queryRelationByStartNode(new NodeInfo().setId(55817));


    }

    public void testTree() {
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("disk");
        GraphResult graphResult = casiaNeo4j.tree().queryAllNodeTreeByLables("LabelsTree");

        out(graphResult);

        CasiaNeo4j casiaNeo4j2 = new CasiaNeo4j("disk");
        System.out.println(casiaNeo4j2.tree().queryAllJsonTreeByLables("LabelsTree"));
    }

    private void out( GraphResult graphResult ){
        System.out.println("nodeInfo:");
        graphResult.getNodeInfos().forEach(s->{
            System.out.println(JSONObject.toJSONString(s));
        });
        System.out.println("relationInfo:");
        graphResult.getRelationInfos().forEach(s->{
            System.out.println(JSONObject.toJSONString(s));
        });
        System.out.println("kw:");
        graphResult.getKwds().forEach(s->{
            for(Object o:s){
                System.out.print(o+"\t");
            }
            System.out.println("");
        });
    }
}
