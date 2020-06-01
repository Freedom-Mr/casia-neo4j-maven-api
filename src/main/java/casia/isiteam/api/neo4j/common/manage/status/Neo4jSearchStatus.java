package casia.isiteam.api.neo4j.common.manage.status;

import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: Neo4jSearchStatus
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/26
 * Email: zhiyou_wang@foxmail.com
 */
public class Neo4jSearchStatus extends BasicParms {
    protected Long skip;
    protected Long limit;
    protected List<String> returnFields = new ArrayList<>();

    protected String cql;
}
