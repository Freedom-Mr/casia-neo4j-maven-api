package casia.isiteam.api.neo4j.common.manage.status;

import casia.isiteam.api.neo4j.util.BuilderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: Neo4jSearchStatus
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/26
 * Email: zhiyou_wang@foxmail.com
 */
public class Neo4jSearchStatus  extends BuilderUtil {
    protected Long skip = 0L;
    protected Long limit = 25L;
    protected List<String> returnFields = new ArrayList<>();
    protected boolean openTableData = true; // 开启结果列表
    protected boolean openNodeRelation = false; // 开启结果关系信息
    protected boolean openDirection = false;//开启指向
    protected String page(){
        if( this.limit < 0 ){
            return NONE;
        }
        return BLANK+SKIP+BLANK+this.skip+BLANK+LIMIT+BLANK+this.limit+BLANK;
    }
}
