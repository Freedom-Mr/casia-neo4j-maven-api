package casia.isiteam.api.neo4j.common.entity.result;

import casia.isiteam.api.toolutil.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: RelationInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class RelationInfo {

    private long id;
    private long startNodeId;
    private long endNodeId;
    private String type;
    private Object _uuId;
    private Map<String,Object> parameters = new HashMap<>();

    public RelationInfo(){};
    public RelationInfo(long id, long startNodeId, long endNodeId) {
        this.id = id;
        this.startNodeId = startNodeId;
        this.endNodeId = endNodeId;
    }

    public RelationInfo(long id, long startNodeId, long endNodeId, String type) {
        this.id = id;
        this.startNodeId = startNodeId;
        this.endNodeId = endNodeId;
        if( !Validator.check(this.type) ){
            this.type = type;
        }
    }

    public RelationInfo(long id, long startNodeId, long endNodeId, String type, Object _uuId) {
        this.id = id;
        this.startNodeId = startNodeId;
        this.endNodeId = endNodeId;
        if( !Validator.check(this._uuId) ){
            this._uuId = _uuId;
        }
        if( !Validator.check(this.type) ){
            this.type = type;
        }
    }

    public RelationInfo(long id, long startNodeId, long endNodeId, String type, Object _uuId, Map<String,Object> parameter) {
        this.id = id;
        this.startNodeId = startNodeId;
        this.endNodeId = endNodeId;
        if( !Validator.check(this._uuId) ){
            this._uuId = _uuId;
        }
        if( !Validator.check(this.type) ){
            this.type = type;
        }
        if( Validator.check(parameter) ){
            parameter.forEach((k,v)->{
                if( !this.parameters.containsKey(k) ){
                    this.parameters.put(k,v);
                }
            });
        }
    }

    public long getId() {
        return id;
    }

    public RelationInfo setId(long id) {
        this.id = id;
        return this;
    }

    public long getStartNodeId() {
        return startNodeId;
    }

    public RelationInfo setStartNodeId(long startNodeId) {
        this.startNodeId = startNodeId;
        return this;
    }

    public long getEndNodeId() {
        return endNodeId;
    }

    public RelationInfo setEndNodeId(long endNodeId) {
        this.endNodeId = endNodeId;
        return this;
    }

    public String getType() {
        return type;
    }

    public RelationInfo setType(String type) {
        if( !Validator.check(this.type) ){
            this.type = type;
        }
        return this;
    }

    public Map<String,Object> getParameters() {
        return parameters;
    }

    public RelationInfo setParameters(Map<String,Object> parameter) {
        if( Validator.check(parameter) ){
            parameter.forEach((k,v)->{
                if( !this.parameters.containsKey(k) ){
                    this.parameters.put(k,v);
                }
            });
        }
        return this;
    }
    public RelationInfo setParameters(String key,Object value) {
        if( Validator.check(key) ){
            if( !this.parameters.containsKey(key) ){
                this.parameters.put(key,value);
            }
        }
        return this;
    }
    public Object get_uuId() {
        return _uuId;
    }

    public RelationInfo set_uuId(Object _uuId) {
        if( !Validator.check(this._uuId) ){
            this._uuId = _uuId;
        }
        return this;
    }

}
