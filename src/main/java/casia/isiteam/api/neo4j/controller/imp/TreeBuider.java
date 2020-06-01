package casia.isiteam.api.neo4j.controller.imp;

import casia.isiteam.api.neo4j.common.entity.result.GraphResult;
import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.result.RelationInfo;
import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.operation.interfaces.Neo4jOperationApi;
import casia.isiteam.api.neo4j.router.ApiRouter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: TreeBuider
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/28
 * Email: zhiyou_wang@foxmail.com
 */
public class TreeBuider {

    private Neo4jOperationApi.TreeApi treeApi;

    public TreeBuider(_Entity_Driver entity_driver){
        treeApi = ApiRouter.getTreeRouter(entity_driver);
    }

    public GraphResult queryAllNodeTreeByLables(List<String> labels){
        return treeApi.searchAllTreeByLables(labels);
    }
    public GraphResult queryAllNodeTreeByLables(String ... labels){
        return treeApi.searchAllTreeByLables(Arrays.asList(labels));
    }

    public JSONArray queryAllJsonTreeByLables(String ... labels) {
        return treeApi.searchAllJsonTreeByLables(Arrays.asList(labels));
    }
}
