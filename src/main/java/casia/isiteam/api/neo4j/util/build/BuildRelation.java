package casia.isiteam.api.neo4j.util.build;

/**
 * ClassName: BuildRelation
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/28
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildRelation extends BuildWhere {

    public static String relation(){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_BRACKETS).append(RIGHT_BRACKETS).append(BLANK);
        return sb_node.toString();
    }
    public static String relation(String asKey){
        StringBuffer sb_node = new StringBuffer();
        sb_node.append(BLANK).append(LEFT_BRACKETS).append(asKey).append(RIGHT_BRACKETS).append(BLANK);
        return sb_node.toString();
    }
}
