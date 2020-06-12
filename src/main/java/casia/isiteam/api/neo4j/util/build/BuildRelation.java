package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.toolutil.Validator;

import java.util.List;

/**
 * ClassName: BuildRelation
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/28
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildRelation extends BuildNode {

    public String relation(){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_BRACKETS).append(RIGHT_BRACKETS).append(BLANK);
        return sb_node.toString();
    }
    public String relation(String asKey){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_BRACKETS).append(asKey).append(RIGHT_BRACKETS).append(BLANK);
        return sb_node.toString();
    }
    public String relation(String asKey,String type){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_BRACKETS).append(asKey).append(COLON).append(type).append(RIGHT_BRACKETS).append(BLANK);
        return sb_node.toString();
    }
    public String relation(String asKey, List<String> types){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_BRACKETS).append(asKey);
        StringBuffer typec = new StringBuffer();
        if( Validator.check(types) ){
            types.forEach(s->{
                typec.append( typec.length() ==0 ? COLON : LINE ).append(s);
            });
        }
        sb_node.append(typec).append(RIGHT_BRACKETS).append(BLANK);
        return sb_node.toString();
    }

    public String relation_full_text(String indexName,String queryString){
        return s().append(CALL).append(BLANK).append(DB).append(DOT).append(INDEX).append(DOT).append(FULLTEXT).append(DOT).append(QUERYRELATIONSHIPS).
                append(LEFT_PARENTHESES).append(SINGLE_QUOTE).append(indexName).append(SINGLE_QUOTE).append(COMMA).
                append(SINGLE_QUOTE).append(queryString).append(SINGLE_QUOTE).append(RIGHT_PARENTHESES).append(addBlank(YIELD)).append("relationship").append(BLANK).toString();
    }
}
