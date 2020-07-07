package casia.isiteam.api.neo4j.controller;

import casia.isiteam.api.neo4j.common.enums.MatchLevel;
import casia.isiteam.api.neo4j.common.enums.ParameterCombine;
import casia.isiteam.api.toolutil.file.CasiaFileUtil;
import junit.framework.TestCase;
import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * ClassName: CasiaNeoTest
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/2
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaNeoTest extends TestCase {

    /**
     * 创建测试
     */
    public void testCreateNode() {
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("sz_create");

        //自定义
//        boolean graphResult = casiaNeo4j.create().create("merge (a:创建{_uuId:'123'}) return *");
//        System.out.println(graphResult);

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("_uuId",123);
//        boolean c = casiaNeo4j.create().create("UNWIND ? AS row MERGE (e:创建{_uuId:row._uuId})",jsonObject);
//        System.out.println(c);

        /*boolean graphResult = casiaNeo4j.create().create("MERGE (e:创建{_uuId:?}) return *","12");
        System.out.println(graphResult);*/

        //创建节点
        casiaNeo4j.create().createNode(CreateType.MERGE,new NodeInfo().set_uuId(12));
        casiaNeo4j.create().createNode(CreateType.MERGE,new NodeInfo().set_uuId(13).setLabels("博客"));
        casiaNeo4j.create().createNode(CreateType.MERGE,new NodeInfo().set_uuId(14).setLabels("博客","微博"));

        List<NodeInfo> nodes = new ArrayList<>();
        nodes.add(new NodeInfo().set_uuId(15).setLabels("博客"));
        nodes.add(new NodeInfo().set_uuId(15).setLabels("微博"));
        casiaNeo4j.create().createNode(CreateType.MERGE,nodes);

        Map<String,Object> parms = new HashMap<>();
        parms.put("site","新浪");
        parms.put("status",1);
        casiaNeo4j.create().createNode(CreateType.MERGE,new NodeInfo().set_uuId(16).setLabels("博客","微博").setParameters(parms));


        //增加标签
        boolean tag = casiaNeo4j.create().createLabelByNodeId(0L,"测试1");
        System.out.println(tag);

    }




    public void testCreateRelation() {
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("sz_create");

        Map<String,Object> parms = new HashMap<>();
        parms.put("status",1);

        Map<String,Object> parms1 = new HashMap<>();
        parms1.put("_uuId",15);

        Map<String,Object> parms2 = new HashMap<>();
        parms2.put("_uuId","123");

        casiaNeo4j.create().createRelationByNodeInfo(CreateType.MERGE,
                new RelationshipInfo(
                        new NodeInfo().setParameters(parms),
                        new NodeInfo().setParameters(parms1)
                ).set_uuId(13).setType("测试关系2"));

        casiaNeo4j.create().createRelationByNodeId(CreateType.MERGE,
                new RelationshipInfo(
                        new NodeInfo().setId(21L),
                        new NodeInfo().setParameters(parms2)
                ).set_uuId(12).setType("测试关系1"));

        casiaNeo4j.create().createRelationByNodeId(CreateType.MERGE,
                21L,40L,"测试关系3",true);

        casiaNeo4j.create().createRelationByNodeUuId(CreateType.MERGE,
                new RelationshipInfo(
                        new NodeInfo().set_uuId(15),
                        new NodeInfo().set_uuId(13)
                ).setType("测试关系4"),
                new RelationshipInfo(
                        new NodeInfo().set_uuId(15),
                        new NodeInfo().set_uuId(13)
                ).setType("测试关系4")
        );

    }

    public void testQuery() {
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("sz_query");
//        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("root","123",new String[]{"192.168.1.1:50001","192.168.1.2:50001"});
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("match (a) - [b] - ()   WITH b,COLLECT(distinct a._uuId) as c,COLLECT(distinct a) as d  return  *");

//        String cql = "MATCH (a:发射任务) WHERE id(a) in[52,53] \n" +
//                "CALL apoc.path.spanningTree(a, {relationshipFilter:'航天阵位',relationshipFilter:'火箭信息',limit:30}) YIELD path " +
//                "RETURN *";
//        String cql="match (a)-[:发帖|转发]-()-[:评论]-(c) where id(a)=473 or id(c)=473 with COLLECT(distinct c) as cc SKIP 0 LIMIT 10  return cc";
//        String cql="call dbms.cluster.routing.getServers";
        String cql="call dbms.cluster.role";

        GraphResult graphResult = casiaNeo4j.query().queryByCondition(cql);
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("match p=(a)-[*0..1]-() return * limit 10");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("MATCH p=(a:`老师1`)-->() RETURN p");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("MATCH p=()-->() RETURN p LIMIT 1");
//        GraphResult graphResult = casiaNeo4j.query().queryByCondition("MATCH (a)-[r]-(b) RETURN a,count(a) as c,a._uuId as u");
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(25L).queryNode();
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(25L).setOpenNodeRelation().queryNodeByLabel("卫星信息");
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).setOpenNodeRelation().setReturnField("<id>","_name").queryNodeById(46459L);
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).setOpenNodeRelation().queryNodeByIdNotIn(46459L,46479L);
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).setOpenNodeRelation().queryNodeByParametersNot(
//                ParameterCombine.OR,
//                new Attribute("_uuId","123"),
//                new Attribute("_uuId",123)
//        );
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).setOpenNodeRelation().queryNodeByLabelAndParameters(new String[]{"学生"}, ParameterCombine.AND,new Attribute(MatchLevel.Contains,"_name","张"));
//        GraphResult graphResult = casiaNeo4j.query().setFrom(0L).setSize(3L).setOpenNodeRelation().queryNodeByLabelAndParametersNot(Arrays.asList("学生"), ParameterCombine.AND,new Attribute(MatchLevel.Contains,"_name","张"));

//         GraphResult graphResult = casiaNeo4j.query().queryNodeTotal();
//          GraphResult graphResult = casiaNeo4j.query().queryNodeTotalByLabel("卫星信息");
//          GraphResult graphResult = casiaNeo4j.query().queryNodeTotalByLabelAndParametersNot(new String[]{"卫星信息"}, ParameterCombine.AND,new Attribute("_uuId","5a2c8a18227cbb0bc2634e610a3c1746"));
//        GraphResult graphResult = casiaNeo4j.query().queryRelationTotal();
//        GraphResult graphResult = casiaNeo4j.query().queryRelationTotalByType("卫星信息");
//        GraphResult graphResult = casiaNeo4j.query().queryRelationTotalByTypeAndParameters(new String[]{"卫星信息"}, ParameterCombine.AND,new Attribute("_uuId","5a2c8a18227cbb0bc2634e610a3c1746"));

//          GraphResult graphResult = casiaNeo4j.query().queryAllLabels();
//        GraphResult graphResult = casiaNeo4j.query().queryAllRelationshipType();

//        GraphResult graphResult = casiaNeo4j.query().queryRelation();
//        GraphResult graphResult = casiaNeo4j.query().setCloseTableData().queryRelationByType(Arrays.asList("发射任务","发射任务"));
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByIdIn(12L,13L);
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByIdNotIn(12L,13L);
//        GraphResult graphResult = casiaNeo4j.query().setOpenDirection().queryRelationByStartNode(new NodeInfo().setId(172));
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByNodeId(19,4);
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByNodeId(19,4);
//        GraphResult graphResult = casiaNeo4j.query().setSize(10L).queryRelationByNodeId(19,4,"火箭信息","火箭信息");
//        GraphResult graphResult = casiaNeo4j.query().setOpenDirection().queryRelationByStartNodeId(172);
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByStartNodeIdNot(172);
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByStartNodeIdIn(114L,7L);
//        GraphResult graphResult = casiaNeo4j.query().setOpenDirection().queryRelationByStartNodeIdNotIn(114L,7L);
//        GraphResult graphResult = casiaNeo4j.query().setOpenDirection().queryRelationByStartNodeAndType(new NodeInfo().setId(172),Arrays.asList("发射任务"));
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByStartNodeIdInAndType(Arrays.asList(114L),Arrays.asList("国家"));
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByStartNodeIdAndType(114L,Arrays.asList("国家"));
//        GraphResult graphResult = casiaNeo4j.query().setOpenDirection().queryRelationByStartNodeAndTypeAndEndNode(new NodeInfo().setId(172),null,new NodeInfo().setId(2262));
//        GraphResult graphResult = casiaNeo4j.query().queryRelationByStartNodeIdAndTypeAndEndNodeId(172L,null,2262L);
//        GraphResult graphResult = casiaNeo4j.query().setOpenDirection().queryRelationByStartNodeIdInAndTypeAndEndNodeIdIn(Arrays.asList(172L),null,Arrays.asList(2262L));

        //多点模糊
//        GraphResult graphResult = casiaNeo4j.query(). queryRelationOnFuzzyByNodeIds(new Long[]{171L,1912L,172L});
        //多点精确
//        GraphResult graphResult = casiaNeo4j.query(). queryRelationOnPrecisionByNodeIds(new Long[]{171L,1912L,172L});
        //最短路径
//        GraphResult graphResult = casiaNeo4j.query(). queryRelationOnShortPathByStartNodeIdAndEndNodeId(1912,172,3);
        //两点深度关联
//        GraphResult graphResult = casiaNeo4j.query(). queryRelationOnPathLengthByStartNodeIdAndEndNodeId(1912,172,1,2);
        //树形扩展
//        GraphResult graphResult = casiaNeo4j.query().setSize(-1L). queryRelationOnExtendTreeByNodeId(52,1);
//        GraphResult graphResult = casiaNeo4j.query().setSize(-1L). queryRelationOnExtendTreeByNodeId(52,2,"航天阵位|火箭信息",null);


//        GraphResult graphResult = casiaNeo4j.query().queryNodeOnFullByQueryString("all","I");
//        GraphResult graphResult = casiaNeo4j.query().queryRelationOnFullByQueryString("测试关系","北京");
        out(graphResult);
    }



    public void testDelete(){
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("disk");
        casiaNeo4j.delete().deleteRelationByIdIn(41L);
        casiaNeo4j.delete().deleteRelationByIdIn(146L);
        casiaNeo4j.delete().deleteNodeById(0L);
        casiaNeo4j.delete().deleteRelationById(2077);
        casiaNeo4j.delete().deleteLabelByNodeId(2L, Arrays.asList("博客","微博"));
        casiaNeo4j.delete().deleteNodeByLabels(Arrays.asList("测试","微博"));
        casiaNeo4j.delete().deleteNodeByLabelAndParameters(Arrays.asList("测试"), ParameterCombine.AND,Arrays.asList(new Attribute().setKey("cc").setValue(123)));
        casiaNeo4j.delete().deleteRelationByType("测试关系1");
        casiaNeo4j.delete().deleteRelationByTypeAndParameters("测试关系2", ParameterCombine.AND,Arrays.asList(new Attribute().setKey("_uuId").setValue(13)) );
        casiaNeo4j.delete().deleteRelationByStartNodeId(1);
        casiaNeo4j.delete().deleteRelationByStartNodeIdAndEndNodeId(1937,1914);
        casiaNeo4j.delete().deleteRelationByStartNodeIdAndTypeAndEndNodeId(1937,"测试关系1",1914);
    }





    public void testTree() {
        CasiaNeo4j casiaNeo4j = new CasiaNeo4j("disk");
        GraphResult graphResult = casiaNeo4j.tree().queryAllNodeTreeByLables("LabelsTree");

        out(graphResult);

        CasiaNeo4j casiaNeo4j2 = new CasiaNeo4j("beihang");
//        System.out.println(casiaNeo4j2.tree().queryAllJsonTreeByLables("LabelsTree"));
        System.out.println(casiaNeo4j2.tree().queryAllJsonTreeAndCountByLables("LabelsTree"));
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
            s.forEach((k,v)->{
                System.out.print(k+":"+v+"；");
            });
            System.out.println("");
        });
    }
}