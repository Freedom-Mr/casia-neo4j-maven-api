package casia.isiteam.api.neo4j.datasource.dbdao;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.result.RelationInfo;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.toolutil.Validator;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ParseResult
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/30
 * Email: zhiyou_wang@foxmail.com
 */
public class ParseResult extends BasicParms {
    public static GraphResult parseResult(StatementResult statementResult){
        GraphResult graphResult= new GraphResult();

        Map<Long, NodeInfo> nodeInfoMap = new HashMap<>();
        Map<Long, RelationInfo> relationInfoMap = new HashMap<>();

        while (statementResult.hasNext()) {
            Record record = statementResult.next();
            List<Value> values = record.values();

            List<Object> list = new ArrayList<>();
            values.forEach(value->{
//                System.out.println(value.type().name());

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
                        list.add(value.asOffsetDateTime());
                        System.out.println(value);
                        break;
                    case DURATION:
                        list.add(value.asIsoDuration());
                        break;
                    default:
                        break;
                }
            });
            if( Validator.check(list) ){
                graphResult.setKwds(list.toArray());
            }

        }

        nodeInfoMap.forEach((k,v)->{
            graphResult.setNodeInfos(v);
        });
        relationInfoMap.forEach((k,v)->{
            graphResult.setRelationInfos(v);
        });

        return graphResult;
    }

    private static void parseNode(Node node, Map<Long,NodeInfo> nodeInfoMap){
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

    private static void parseRelation(Relationship relationship,Map<Long,RelationInfo> relationInfoMap){
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
}
