package casia.isiteam.api.neo4j.datasource.dbdao;


import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.result.RelationInfo;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;

import java.util.*;

/**
 * ClassName: ParseValues
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/30
 * Email: zhiyou_wang@foxmail.com
 */
public class ParseValues extends BasicParms {
    protected static void parseNode(Node node, Map<Long, NodeInfo> nodeInfoMap){
        List<String> list = new ArrayList<>();
        node.labels().forEach(s->list.add(s));
        if( nodeInfoMap.containsKey(node.id()) ){
            nodeInfoMap.get(node.id()).
                    setId(node.id()).
                    setLabels( list ).
                    setParameters(node.asMap()).
                    set_uuId(node.asMap().containsKey(_UUID) ?node.asMap().get(_UUID) : NULL );
        }else{
            nodeInfoMap.put(node.id(),
                    new NodeInfo().
                            setId(node.id()).
                            setLabels( list ).
                            setParameters(node.asMap()).
                            set_uuId(node.asMap().containsKey(_UUID) ?node.asMap().get(_UUID) : NULL )
            );
        }
    }
    protected static void parseNode(Map<String,Object> map, Map<Long, NodeInfo> nodeInfoMap){
        NodeInfo nodeInfo = new NodeInfo();
        map.forEach((k,v)->{
            if( k.equals(_ID) ){
                nodeInfo.setId(Long.parseLong(v.toString()));
            }else if( k.equals(_UUID) ){
                nodeInfo.set_uuId(map.get(_UUID));
                nodeInfo.setParameters(k,v);
            }else if( k.equals(_LABELS) ){
                nodeInfo.setLabels( (List<String>)v );
            }else {
                nodeInfo.setParameters(k,v);
            }
        });
        nodeInfoMap.put(nodeInfo.getId(),nodeInfo);
    }
    protected static void parseRelation(Relationship relationship, Map<Long, RelationInfo> relationInfoMap){
        if( relationInfoMap.containsKey(relationship.id()) ){
            relationInfoMap.get(relationship.id()).
                    setId(relationship.id()).
                    setStartNodeId(relationship.startNodeId()).
                    setEndNodeId(relationship.endNodeId()).
                    setType(relationship.type()).setParameters(relationship.asMap()).
                    set_uuId(relationship.asMap().containsKey(_UUID) ?relationship.asMap().get(_UUID) : NULL );
        }else{
            relationInfoMap.put(relationship.id(),
                    new RelationInfo().
                            setId(relationship.id()).
                            setStartNodeId(relationship.startNodeId()).
                            setEndNodeId(relationship.endNodeId()).
                            setType(relationship.type()).setParameters(relationship.asMap()).
                            set_uuId(relationship.asMap().containsKey(_UUID) ?relationship.asMap().get(_UUID) : NULL )
            );
        }

    }
    protected static void parseRelation(Map<String,Object> map,  Map<Long, RelationInfo> relationInfoMap){
        RelationInfo relationInfo = new RelationInfo();
        map.forEach((k,v)->{
            if( k.equals(_ID) ){
                relationInfo.setId(Long.parseLong(v.toString()));
            }else if( k.equals(_UUID) ){
                relationInfo.set_uuId(map.get(_UUID));
                relationInfo.setParameters(k,v);
            }else if( k.equals(_TYPE) ){
                relationInfo.setType(v.toString());
            }else if( k.equals(_STARID) ){
                relationInfo.setStartNodeId(Long.parseLong(v.toString()));
            }else if( k.equals(_ENDID) ){
                relationInfo.setEndNodeId(Long.parseLong(v.toString()));
            }else {
                relationInfo.setParameters(k,v);
            }
        });
        relationInfoMap.put(relationInfo.getId(),relationInfo);
    }
}
