package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.neo4j.util.EscapUtil;
import casia.isiteam.api.neo4j.util.TypeUtil;
import casia.isiteam.api.toolutil.Validator;

import java.util.List;
import java.util.Map;

/**
 * ClassName: BuildNode
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/27
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildNode extends BuildRelation {
    public static String node(){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_PARENTHESES).append(RIGHT_PARENTHESES).append(BLANK);
        return sb_node.toString();
    }
    public static String node(String asKey){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES).append(BLANK);
        return sb_node.toString();
    }
    public static String node(String asKey, List<String> labels){
        return node(asKey, labels,null);
    }
    public static String node(String asKey, List<String> labels, Map<String,Object> parms){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_PARENTHESES).append(asKey);
        if( Validator.check(labels) ){
            labels.forEach(s->sb_node.append(COLON).append(s));
        }
        if( Validator.check(parms) ){
            StringBuffer pa = new StringBuffer();
            parms.forEach((k,v)->{
                pa.append(pa.length() > 0 ? COMMA : NONE).append(k).append(COLON).append(TypeUtil.typeRecognition(EscapUtil.repalceChars(v)));
            });
            sb_node.append(LEFT_BRACES).append(pa).append(RIGHT_BRACES);
        }
        sb_node.append(RIGHT_PARENTHESES);
        return sb_node.toString();
    }
    public static String node(String asKey,List<String> labels,Object _uuid){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_PARENTHESES).append(asKey);
        if( Validator.check(labels) ){
            labels.forEach(s->sb_node.append(COLON).append(s));
        }
        if( Validator.check(_uuid) ){
            sb_node.append(LEFT_BRACES).append(_UUID).append(COLON).append(TypeUtil.typeRecognition(EscapUtil.repalceChars(_uuid))).append(RIGHT_BRACES);
        }
        sb_node.append(RIGHT_PARENTHESES);
        return sb_node.toString();
    }


}
