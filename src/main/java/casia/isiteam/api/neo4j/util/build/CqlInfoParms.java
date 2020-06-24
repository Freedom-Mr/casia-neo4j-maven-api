package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.neo4j.util.TypeUtil;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.escape.CasiaEscapeUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ClassName: CqlInfoParms
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/27
 * Email: zhiyou_wang@foxmail.com
 */
public class CqlInfoParms extends BasicParms {

    protected final String node_0 = " () ";
    protected final String node_1 = " (%s) ";
    protected final String node_2 = " (%s:%s) ";
    protected final String node_3 = " (%s:%s{_uuId:%s}) ";
    protected final String node_4 = " (%s:%s{%s}) ";
    protected final String node_5 = " (%s%s{_uuId:%s}) ";

    protected final String relation_0 = " [] ";
    protected final String relation_1 = " [%s] ";
    protected final String relation_2 = " [%s%s] ";
    protected final String relation_3 = " [%s:%s] ";

    protected final String nodeRelation_0 = " ()-[]-() ";
    protected final String nodeRelation_2 = " ()-[%s]-() ";
    protected final String nodeRelation_3 = " (%s)-[%s]-(%s) ";
    protected final String nodeRelation_4 = " (%s)-[%s:%s]-(%s) ";
    protected final String nodeRelation_5 = " (%s)-[%s:%s{%s}]-(%s) ";

    protected final String nodeRelation_0_0 = " ()-[]->() ";
    protected final String nodeRelation_2_2 = " ()-[%s]->() ";
    protected final String nodeRelation_3_3 = " (%s)-[%s]->(%s) ";
    protected final String nodeRelation_4_4 = " (%s)-[%s:%s]->(%s) ";
    protected final String nodeRelation_5_5 = " (%s)-[%s:%s{%s}]->(%s) ";

    protected final String id_1 = " id(%s)=%d ";
    protected final String id_2 = " id(%s) in %s ";

    /**扩展到子图并返回该子图中的所有节点和关系
     * apoc.path.subgraphAll(startNode <id>Node/list, {maxLevel, relationshipFilter, labelFilter, bfs:true, filterStartNode:true, limit:-1}) yield nodes, relationships
     * */
    protected final String apoc_1 = " call apoc.path.subgraphAll(%s %s) ";
    /**扩展生成树
     * apoc.path.spanningTree(startNode <id>Node/list, {maxLevel, relationshipFilter, labelFilter, bfs:true, filterStartNode:true, limit:-1, optional:false}) yield path
     * */
    protected final String apoc_2 = " call apoc.path.spanningTree(%s %s) ";


    protected final String return_0 = " return * ";
    protected final String return_1 = " return %s ";
    protected final String return_2 = " return count(%s) ";

    protected final String delete_0 = " delete * ";
    protected final String delete_1 = " delete %s ";

    protected final String remove_0 = " remove * ";

    protected final String _uuId_ = " _uuId:%s ";
    /**
     * UUid
     * @return
     */
    public String asKey(){
        return A+ UUID.randomUUID().toString().replaceAll(CROSS,NONE);
    }
    /**
     * new StringBuffer
     * @return
     */
    protected StringBuffer s(){
        return new StringBuffer();
    }
    /**
     * add blank
     * @param key
     * @return
     */
    public static String addBlank(String key){
        return BLANK+key+BLANK;
    }

    /**
     * add p
     * @param nodeLation
     * @return
     */
    public static String addP(String nodeLation){
        return BLANK+P+EQUAL+nodeLation+BLANK;
    }

    /**
     * transafe fields
     * @param symbol
     * @param fields
     * @return
     */
    protected String symbolsAll(String symbol,String ... fields){
        if( Validator.check(fields) ){
            return symbolsAll(symbol, Arrays.asList(fields));
        }
        return s().toString();
    }
    protected String symbols(String symbol,String ... fields){
        if( Validator.check(fields) ){
            return symbols(symbol, Arrays.asList(fields));
        }
        return s().toString();
    }
    /**
     * transafe fields
     * @param symbol
     * @param fields
     * @return
     */
    protected String symbolsAll(String symbol, List<String> fields){
        StringBuffer result = s();
        if( Validator.check(fields) ){
            for(String field:fields){
                result.append(symbol+field);
            }
        }
        return result.length()>0? addBlank(result.toString()):NONE;
    }
    protected String symbols(String symbol, List<String> fields){
        StringBuffer result = s();
        if( Validator.check(fields) ){
            for(String field:fields){
                result.append(result.length() == 0? field : symbol+field );
            }
        }
        return result.length()>0? addBlank(result.toString()):NONE;
    }
    protected String symbols(String symbol,String keyValueSymbol, Map<String,Object> parms){
        StringBuffer result = s();
        if( Validator.check(parms) ){
            parms.forEach((k,v)->{
                String keyValue = k + keyValueSymbol + TypeUtil.typeRecognition(CasiaEscapeUtil.neo4jEscape(v));
                result.append(result.length() == 0? keyValue : symbol+keyValue);
            });
        }
        return result.length()>0? addBlank(result.toString()):NONE;
    }
    protected String symbolsAll(String symbol,String keyValueSymbol, Map<String,Object> parms){
        StringBuffer result = s();
        if( Validator.check(parms) ){
            parms.forEach((k,v)->{
                result.append( symbol+ k + keyValueSymbol + TypeUtil.typeRecognition(CasiaEscapeUtil.neo4jEscape(v)) );
            });
        }
        return result.length()>0? addBlank(result.toString()):NONE;
    }
    protected String symbols(String symbol,String keyValueSymbol,String asKey, Map<String,Object> parms){
        StringBuffer result = s();
        if( Validator.check(parms) ){
            parms.forEach((k,v)->{
                String keyValue = asKey+DOT+k + keyValueSymbol + TypeUtil.typeRecognition(CasiaEscapeUtil.neo4jEscape(v));
                result.append(result.length() == 0? keyValue : symbol+keyValue);
            });
        }
        return result.length()>0? addBlank(result.toString()):NONE;
    }
    protected String symbolsAll(String symbol,String keyValueSymbol,String asKey, Map<String,Object> parms){
        StringBuffer result = s();
        if( Validator.check(parms) ){
            parms.forEach((k,v)->{
                result.append( symbol+ asKey+DOT+k + keyValueSymbol + TypeUtil.typeRecognition(CasiaEscapeUtil.neo4jEscape(v)) );
            });
        }
        return result.length()>0? addBlank(result.toString()):NONE;
    }

    protected String symbols(String symbol,String asKey,  List<Attribute> attributes){
        StringBuffer result = s();
        if( Validator.check(attributes) ){
            attributes.forEach(s->{
                String ps = asKey+DOT+s.getKey()+ TypeUtil.LevelRecognition(s.getMatchLevel(),s.getValue() );
                result.append(result.length() == 0? ps : addBlank(symbol)+ps);
            });
        }
        return result.length()>0? addBlank(result.toString()):NONE;
    }
}
