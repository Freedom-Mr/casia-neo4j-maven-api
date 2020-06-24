package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.util.TypeUtil;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.escape.CasiaEscapeUtil;
import com.alibaba.fastjson.JSONArray;
import org.neo4j.driver.internal.util.Extract;
import org.neo4j.driver.internal.util.Iterables;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ClassName: BuildCqls
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/4
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildCqls extends CqlInfoParms {
    private Logger logger = LoggerFactory.getLogger( this.getClass());

    /*************************** node *******************************/
    protected String node(){
        return node_0;
    }
    protected String node(String asKey){
        return String.format(node_1,asKey);
    }
    protected String node(String asKey, List<String> labels){
        return Validator.check(labels) ? String.format( node_2,asKey,symbols(COLON,labels) ) : node(asKey);
    }
    protected String node(String asKey, List<String> labels, Map<String,Object> parms){
        return String.format( node_4,asKey,symbolsAll(COLON,labels),symbolsAll(COMMA,COLON,parms) );
    }
    protected String node(String asKey,List<String> labels,Object _uuid){
        return String.format( node_5,asKey,symbolsAll(COLON,labels),TypeUtil.typeRecognition(CasiaEscapeUtil.neo4jEscape(_uuid)) );
    }


    /*************************** relation *******************************/
    protected String relation(){
        return relation_0;
    }
    protected String relation(String asKey){
        return String.format(relation_1,asKey);
    }
    protected String relation(String asKey,String type){
        return  Validator.check(type) ? String.format(relation_3,asKey,type) : relation(asKey);
    }
    protected String relation(String asKey, List<String> types){
        return  Validator.check(types) ? String.format(relation_3,asKey,symbols(LINE,types)) : relation(asKey) ;
    }


    /*************************** nodeRelation *******************************/
    protected String nodeRelation(){
        return nodeRelation_0;
    }
    protected String nodeRelation(String start_node_asKey,String relation_asKey,String end_node_asKey){
        return  String.format(nodeRelation_3,start_node_asKey,relation_asKey,end_node_asKey);
    }
    protected String nodeRelation(String start_node_asKey,String relation_asKey,String type,String end_node_asKey){
        return  Validator.check(type) ?
                String.format(nodeRelation_4,start_node_asKey,relation_asKey,type,end_node_asKey):
                nodeRelation(start_node_asKey,relation_asKey,end_node_asKey) ;
    }
    protected String nodeRelation(String start_node_asKey,String relation_asKey,List<String> type,String end_node_asKey){
        return  Validator.check(type) ?
                String.format(nodeRelation_4,start_node_asKey,relation_asKey,symbols(LINE,type),end_node_asKey):
                nodeRelation(start_node_asKey,relation_asKey,end_node_asKey) ;
    }
    protected String nodeRelation(String start_node_asKey,String relation_asKey,String type,Object _uuid, String end_node_asKey){
        return  String.format(nodeRelation_5,start_node_asKey,relation_asKey,type,
                Validator.check(_uuid)? String.format( _uuId_,TypeUtil.typeRecognition(CasiaEscapeUtil.neo4jEscape(_uuid)) ) : NONE
                ,end_node_asKey);
    }
    protected String nodeRelationDirection(){
        return nodeRelation_0_0;
    }
    protected String nodeRelationDirection(String start_node_asKey,String relation_asKey,String end_node_asKey){
        return  String.format(nodeRelation_3_3,start_node_asKey,relation_asKey,end_node_asKey);
    }//指向型
    protected String nodeRelationDirection(String start_node_asKey,String relation_asKey,String type,String end_node_asKey){
        return  Validator.check(type) ?
                String.format(nodeRelation_4_4,start_node_asKey,relation_asKey,type,end_node_asKey):
                nodeRelationDirection(start_node_asKey,relation_asKey,end_node_asKey) ;
    }
    protected String nodeRelationDirection(String start_node_asKey,String relation_asKey,List<String> type,String end_node_asKey){
        return  Validator.check(type) ?
                String.format(nodeRelation_4_4,start_node_asKey,relation_asKey,symbols(LINE,type),end_node_asKey):
                nodeRelationDirection(start_node_asKey,relation_asKey,end_node_asKey) ;
    }
    protected String nodeRelationDirection(String start_node_asKey,String relation_asKey,String type,Object _uuid, String end_node_asKey){
        return  String.format(nodeRelation_5_5,start_node_asKey,relation_asKey,type,
                Validator.check(_uuid)? String.format( _uuId_,TypeUtil.typeRecognition(CasiaEscapeUtil.neo4jEscape(_uuid)) ) : NONE,
                end_node_asKey);
    }


    /*************************** id *******************************/
    protected String addId(String asKey,long id){
        return String.format(id_1,asKey,id);
    }
    protected String addId(String asKey,List<Long> ids){
        return  String.format(id_2,asKey,JSONArray.toJSON(ids));
//        return addBlank(ID+node(asKey)+addBlank(IN)+ JSONArray.toJSON(ids));
    }

    /*************************** skip limit *******************************/
    protected String addLimit(long limit){
        return addBlank(LIMIT+BLANK+limit);
    }
    protected String addLimit(long skip,long limit){
        return addBlank(SKIP+BLANK+skip+BLANK+LIMIT+BLANK+limit);
    }



    /*************************** apoc *******************************/
    protected String addApocParms(Object ... keysAndValues){
        if( Validator.check(keysAndValues) ){
            if (keysAndValues.length % 2 != 0) {
                logger.error("Parameters function requires an even number of arguments, alternating key and value. Arguments were: " + Arrays.toString(keysAndValues) + ".");
            } else {
//                HashMap<String, Object> map = Iterables.newHashMapWithSize(keysAndValues.length / 2);
                HashMap<String, Object> map = new HashMap<>();
                for(int i = 0; i < keysAndValues.length; i += 2) {
                    Object value = keysAndValues[i + 1];
                    if( !Validator.check(value) ){
                        continue;
                    }
                    if( keysAndValues[i].toString().equals(LIMIT) && Long.parseLong(value.toString()) <0 ){
                        continue;
                    }
                    map.put(keysAndValues[i].toString(),value );
                }
                return s().append(COMMA).append(BLANK).append(LEFT_BRACES).append(symbols(COMMA,COLON,map)).append(RIGHT_BRACES).append(BLANK).toString();
            }
        }
        return NONE;
    }


    /*************************** return *******************************/
    protected String returnAll(){
        return return_0;
    }
    protected String returnCount(String asKey){
        return String.format(return_2,asKey);
    }
    public String returnField(String asKey){
        return  addBlank(RETURN)+asKey+BLANK;
    }
    public String returnField(String asKey, List<String> returnFields){
        StringBuffer sb_return = s();
        if( Validator.check(returnFields) ){
            returnFields.stream().filter(s->Validator.check(s)).forEach(s->{
                sb_return.append(sb_return.length()>0? COMMA : NONE);
                if( s.equals(_ID_) ){
                    sb_return.append(ID).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES);
                }else{
                    sb_return.append(asKey).append(DOT).append(s);
                }
            });
            return  addBlank(RETURN)+sb_return+BLANK;
        }else{
            return  returnAll();
        }
    }


    /*************************** fullText *******************************/
    protected String node_full_text(String indexName,String queryString){
        return s().append(CALL).append(BLANK).append(DB).append(DOT).append(INDEX).append(DOT).append(FULLTEXT).append(DOT).append(QUERYNODES).
                append(LEFT_PARENTHESES).append(SINGLE_QUOTE).append(indexName).append(SINGLE_QUOTE).append(COMMA).
                append(SINGLE_QUOTE).append(queryString).append(SINGLE_QUOTE).append(RIGHT_PARENTHESES).append(addBlank(YIELD)).append("node").toString();
    }
    public String relation_full_text(String indexName,String queryString){
        return s().append(CALL).append(BLANK).append(DB).append(DOT).append(INDEX).append(DOT).append(FULLTEXT).append(DOT).append(QUERYRELATIONSHIPS).
                append(LEFT_PARENTHESES).append(SINGLE_QUOTE).append(indexName).append(SINGLE_QUOTE).append(COMMA).
                append(SINGLE_QUOTE).append(queryString).append(SINGLE_QUOTE).append(RIGHT_PARENTHESES).append(addBlank(YIELD)).append("relationship").append(BLANK).toString();
    }
}
