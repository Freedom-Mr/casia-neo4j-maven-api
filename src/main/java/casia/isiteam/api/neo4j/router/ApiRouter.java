package casia.isiteam.api.neo4j.router;

import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.manage.status.Neo4jDriverStatus;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.operation.service.*;

/**
 * ClassName: ApiRouter
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class ApiRouter extends Routers{

    public static Neo4jOperationApi.TreeApi getTreeRouter(_Entity_Driver entity_driver) {
        dbinfo = entity_driver;
        return treeRouter = new TreeServer();
    }

    public static void setTreeRouter(Neo4jOperationApi.TreeApi treeRouter) {
        ApiRouter.treeRouter = treeRouter;
    }

    public static Neo4jOperationApi.CreateApi getCreateRouter(_Entity_Driver entity_driver) {
        dbinfo = entity_driver;
        return createRouter = new CreateServer();
    }

    public static void setCreateRouter(Neo4jOperationApi.CreateApi createRouter) {
        ApiRouter.createRouter = createRouter;
    }

    public static Neo4jOperationApi.QueryApi getQueryRouter(_Entity_Driver entity_driver) {
        dbinfo = entity_driver;
        return queryRouter = new SearchServer();
    }

    public static void setQueryRouter(Neo4jOperationApi.QueryApi queryRouter) {
        ApiRouter.queryRouter = queryRouter;
    }

    public static Neo4jOperationApi.DeleteApi getDeleteRouter(_Entity_Driver entity_driver) {
        dbinfo = entity_driver;
        return deleteRouter = new DeleteServer();
    }

    public static void setDeleteRouter(Neo4jOperationApi.DeleteApi deleteRouter) {
        ApiRouter.deleteRouter = deleteRouter;
    }

    public static Neo4jOperationApi.UpdateApi getUpdateRouter(_Entity_Driver entity_driver) {
        dbinfo = entity_driver;
        return updateRouter = new UpdateServer();
    }

    public static void setUpdateRouter(Neo4jOperationApi.UpdateApi updateRouter) {
        ApiRouter.updateRouter = updateRouter;
    }
}
