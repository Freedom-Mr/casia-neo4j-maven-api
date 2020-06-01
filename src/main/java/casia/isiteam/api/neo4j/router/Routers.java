package casia.isiteam.api.neo4j.router;

import casia.isiteam.api.neo4j.common.manage.status.Neo4jDriverStatus;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;

/**
 * ClassName: Routers
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class Routers extends Neo4jDriverStatus {
    protected static Neo4jOperationApi.CreateApi createRouter;
    protected static Neo4jOperationApi.QueryApi queryRouter;
    protected static Neo4jOperationApi.DeleteApi deleteRouter;
    protected static Neo4jOperationApi.TreeApi treeRouter;
}
