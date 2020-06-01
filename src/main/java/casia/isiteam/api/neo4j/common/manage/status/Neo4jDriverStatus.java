package casia.isiteam.api.neo4j.common.manage.status;

import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import org.neo4j.driver.v1.Driver;


import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: Neo4jDriverStatus
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/20
 * Email: zhiyou_wang@foxmail.com
 */
public class Neo4jDriverStatus extends Neo4jSearchStatus {
    protected static Map<String, _Entity_Driver> dbSource = new HashMap<>();
    protected static _Entity_Driver dbinfo = new _Entity_Driver("");
    protected Map<String,Driver>  _drivers = new HashMap<>();
}
