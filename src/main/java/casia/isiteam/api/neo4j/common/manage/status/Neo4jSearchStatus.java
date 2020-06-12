package casia.isiteam.api.neo4j.common.manage.status;

import casia.isiteam.api.neo4j.util.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: Neo4jSearchStatus
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/26
 * Email: zhiyou_wang@foxmail.com
 */
public class Neo4jSearchStatus  extends Builder {
    protected Long skip = 0L;
    protected Long limit = 25L;
    protected List<String> returnFields = new ArrayList<>();
    protected boolean openTableData = true;
    protected boolean openNodeRelation = false;
    protected String page(){
        return BLANK+SKIP+BLANK+this.skip+BLANK+LIMIT+BLANK+this.limit+BLANK;
    }
}
