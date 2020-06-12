package casia.isiteam.api.neo4j.datasource.dbdao;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.result.RelationInfo;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName: ParseResult
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/30
 * Email: zhiyou_wang@foxmail.com
 */
public class ParseResult extends ParseValues  {

    public static GraphResult parseResult(StatementResult statementResult){
        GraphResult graphResult= new GraphResult();

        Map<Long, NodeInfo> nodeInfoMap = new HashMap<>();
        Map<Long, RelationInfo> relationInfoMap = new HashMap<>();

        int nums = 1;
        while (statementResult.hasNext()) {
            Record record = statementResult.next();
            List<Value> values = record.values();

            List<Object> list = new ArrayList<>();
            values.forEach(value->{
                switch ( value.type().name() ) {
                    case NODE:
                        Node node = value.asNode();
                        parseNode(node,nodeInfoMap);
                        break;
                    case RELATIONSHIP:
                        Relationship relationship = value.asRelationship();
                        parseRelation(relationship,relationInfoMap);
                        break;
                    case PATH:
                        Path path = value.asPath();
                        if (path.length() == 0) {
                        } else if (path.length() >= 1) {
                            path.relationships().forEach(s -> {
                                parseRelation(s,relationInfoMap);
                            });
                            path.nodes().forEach(s->{
                                parseNode(s,nodeInfoMap);
                            });
                        }
                        break;
                    case INTEGER:
                        list.add(value.asInt());
                        break;
                    case FLOAT:
                        list.add(value.asFloat());
                        break;
                    case LONG:
                        list.add(value.asLong());
                        break;
                    case DOUBLE:
                        list.add(value.asDouble());
                        break;
                    case NUMBER:
                        list.add(value.asNumber().floatValue());
                        break;
                    case MAP:
                        list.add(value.asMap());
                        break;
                    case BOOLEAN:
                        list.add(value.asBoolean());
                        break;
                    case BYTES:
                        list.add(value.asByteArray());
                        break;
                    case STRING:
                        list.add(value.asString());
                        break;
                    case LIST:
                        list.add(value.asList());
                        break;
                    case POINT:
                        list.add(value.asPoint().srid());
                        break;
                    case DATE:
                        list.add(value.asLocalDate());
                        break;
                    case TIME:
                        list.add(value.asLocalTime());
                        break;
                    case LOCAL_TIME:
                        list.add(value.asLocalTime());
                        break;
                    case LOCAL_DATE_TIME:
                        list.add(value.asLocalDateTime());
                        break;
                    case DATE_TIME:
                        list.add(value.asLocalDateTime());
                        break;
                    case DURATION:
                        list.add(value.asIsoDuration());
                        break;
                    default:
                        break;
                }
            });
            if( Validator.check(list) ){
//                graphResult.setKwds(nums+NONE,list.toArray());
            }
            nums++;
        }

        nodeInfoMap.forEach((k,v)->{
            graphResult.setNodeInfos(v);
        });
        relationInfoMap.forEach((k,v)->{
            graphResult.setRelationInfos(v);
        });

        return graphResult;
    }

    public static GraphResult parseResult(ResultSet result,boolean openTableData) throws SQLException {
        GraphResult graphResult= new GraphResult();

        Map<Long, NodeInfo> nodeInfoMap = new HashMap<>();
        Map<Long, RelationInfo> relationInfoMap = new HashMap<>();
        List<LinkedHashMap<String,Object>> kwds = new ArrayList<>();

        while (result.next()) {
            LinkedHashMap<String,Object> kwd_map = new LinkedHashMap<>();
            int columnNum = result.getMetaData().getColumnCount();
            for(int i = 1; i <= columnNum; i++){
                String columnName = result.getMetaData().getColumnName(i);
                Object columnValue = result.getObject(i);
                // all node and relation
                transafeData(columnValue,nodeInfoMap,relationInfoMap);
                // all column data
                if( openTableData ){
                    transafeData(columnName,columnValue,kwd_map);
                }
            }
            if( openTableData ){
                kwds.add(kwd_map);
            }
        }
        nodeInfoMap.forEach((k,v)->{
            graphResult.setNodeInfos(v);
        });
        relationInfoMap.forEach((k,v)->{
            graphResult.setRelationInfos(v);
        });
        graphResult.setKwds(kwds);
        return graphResult;
    }
    private static void transafeData(Object value,Map<Long, NodeInfo> nodeInfoMap ,Map<Long, RelationInfo> relationInfoMap){
        if( value instanceof HashMap){
            Map<String,Object> map = ((HashMap) value);
            if( map.containsKey(_LABELS) ){
                parseNode(map,nodeInfoMap);
            }else if( map.containsKey(_TYPE) ){
                parseRelation(map,relationInfoMap);
            }
        }else if( value instanceof ArrayList){
            List<Object> list = (ArrayList<Object>) value;
            list.forEach(s->{
                transafeData(s,nodeInfoMap,relationInfoMap);
            });
        }else if( value instanceof Node ){
            parseNode((Node)value,nodeInfoMap);
        }else if( value instanceof Relationship ){
            parseRelation((Relationship)value,relationInfoMap);
        }
    }
    private static void transafeData(String key,Object value,LinkedHashMap<String,Object> kwd_map){
        Map<Long, NodeInfo> nodeInfoMap = new HashMap<>();
        Map<Long, RelationInfo> relationInfoMap = new HashMap<>();
        if( value instanceof HashMap){
            Map<String,Object> map = ((HashMap) value);
            if( map.containsKey(_LABELS) ){
                parseNode(map,nodeInfoMap);
                nodeInfoMap.forEach((k,v)->{
                    kwd_map.put(key, JSON.toJSON(v) );
                });
            }else if( map.containsKey(_TYPE) ){
                parseRelation(map,relationInfoMap);
                relationInfoMap.forEach((k,v)->{
                    kwd_map.put(key, JSON.toJSON(v) );
                });
            }else{
                kwd_map.put(key,value);
            }
        }else if( value instanceof ArrayList){
            List<Object> list = (ArrayList<Object>) value;
            List<Object> new_list = new ArrayList<>();
            list.forEach(s->{
                LinkedHashMap<String,Object> kwd_map_child = new LinkedHashMap<>();
                transafeData(key,s,kwd_map_child);
                new_list.add(kwd_map_child.get(key));
            });
            kwd_map.put(key,new_list);
        }else if( value instanceof Node ){
            parseNode((Node)value,nodeInfoMap);
            nodeInfoMap.forEach((k,v)->{
                kwd_map.put(key, JSON.toJSON(v) );
            });
        }else if( value instanceof Relationship ){
            parseRelation((Relationship)value,relationInfoMap);
            relationInfoMap.forEach((k,v)->{
                kwd_map.put(key, JSON.toJSON(v) );
            });
        }else {
            kwd_map.put(key,value);
        }
    }

}
