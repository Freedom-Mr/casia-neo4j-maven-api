package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.toolutil.Validator;

import java.util.List;

/**
 * ClassName: BuildNodeRelation
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/1
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildNodeRelation extends BuildRelation {

    public String nodeRelation(String start_node_asKey,String relation_asKey,String end_node_asKey){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).
                append(LEFT_PARENTHESES).append(Validator.check(start_node_asKey) ? start_node_asKey:NONE).append(RIGHT_PARENTHESES).
                append(CROSS).
                append(LEFT_BRACKETS).append(Validator.check(relation_asKey) ? relation_asKey:NONE).append(RIGHT_BRACKETS).
                append(CROSS).
                append(LEFT_PARENTHESES).append(Validator.check(end_node_asKey) ? end_node_asKey:NONE).append(RIGHT_PARENTHESES).
                append(BLANK);
        return sb_node.toString();
    }
    public String nodeRelation(String start_node_asKey,String relation_asKey,String type,String end_node_asKey){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).
                append(LEFT_PARENTHESES).append(Validator.check(start_node_asKey) ? start_node_asKey:NONE).append(RIGHT_PARENTHESES).
                append(CROSS).
                append(relation(relation_asKey,type)).
                append(CROSS).
                append(LEFT_PARENTHESES).append(Validator.check(end_node_asKey) ? end_node_asKey:NONE).append(RIGHT_PARENTHESES).
                append(BLANK);
        return sb_node.toString();
    }


}
