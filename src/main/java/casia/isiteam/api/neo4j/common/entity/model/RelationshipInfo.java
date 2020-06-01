package casia.isiteam.api.neo4j.common.entity.model;

import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.toolutil.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: RelationshipInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@foxmail.com
 */
public class RelationshipInfo {
    private NodeInfo startNodeInfo;
    private NodeInfo endNodeInfo;

    private String type;
    private Boolean direction = false;
    private Object _uuId;
    private Map<String,Object> parameters = new HashMap<>();

    public RelationshipInfo(NodeInfo startNodeInfo, NodeInfo endNodeInfo) {
        this.startNodeInfo = startNodeInfo;
        this.endNodeInfo = endNodeInfo;
    }

    public RelationshipInfo(NodeInfo startNodeInfo, NodeInfo endNodeInfo, String type) {
        this.startNodeInfo = startNodeInfo;
        this.endNodeInfo = endNodeInfo;
        this.type = type;
    }

    public RelationshipInfo(NodeInfo startNodeInfo, NodeInfo endNodeInfo, String type,Boolean direction, Object _uuId, Map<String,Object> parameter ) {
        this.startNodeInfo = startNodeInfo;
        this.endNodeInfo = endNodeInfo;
        this.type = type;
        this._uuId = _uuId;
        this.direction = direction;
        if( Validator.check(parameter) ){
            parameter.forEach((k,v)->{
                if( !this.parameters.containsKey(k) ){
                    this.parameters.put(k,v);
                }
            });
        }
    }

    public Object get_uuId() {
        return _uuId;
    }

    public RelationshipInfo set_uuId(Object _uuId) {
        this._uuId = _uuId;
        return this;
    }

    public String getType() {
        return type;
    }

    public RelationshipInfo setType(String type) {
        this.type = type;
        return this;
    }

    public Map<String,Object> getParameters() {
        return parameters;
    }

    public RelationshipInfo setParameters(Map<String,Object> parameter) {
        if( Validator.check(parameter) ){
            parameter.forEach((k,v)->{
                if( !this.parameters.containsKey(k) ){
                    this.parameters.put(k,v);
                }
            });
        }
        return this;
    }

    public Boolean getDirection() {
        return direction;
    }

    public RelationshipInfo setDirection(Boolean direction) {
        this.direction = direction;
        return this;
    }

    public NodeInfo getStartNodeInfo() {
        return startNodeInfo;
    }

    public RelationshipInfo setStartNodeInfo(NodeInfo startNodeInfo) {
        this.startNodeInfo = startNodeInfo;
        return this;
    }

    public NodeInfo getEndNodeInfo() {
        return endNodeInfo;
    }

    public RelationshipInfo setEndNodeInfo(NodeInfo endNodeInfo) {
        this.endNodeInfo = endNodeInfo;
        return this;
    }
}
