package casia.isiteam.api.neo4j.common.entity.result;
import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ClassName: NodeInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/21
 * Email: zhiyou_wang@foxmail.com
 */
public class NodeInfo {
    /**
     * node id
     */
    private long id = -1;
    /**
     * node uniq id
     */
    private Object _uuId ;
    /**
     * node label
     */
    private List<String> labels = new ArrayList<>();
    /**
     * node parm
     */
    private Map<String,Object> parameters = new HashMap<>();


    public NodeInfo(){};

    public NodeInfo(long id, List<String> label) {
        this.id = id;
        label.stream().filter(s->!labels.contains(s)).forEach(s->{
            this.labels.add( s );
        });
    }
    public NodeInfo(long id, Object _uuId, List<String> label) {
        this.id = id;
        if( !Validator.check(this._uuId) ){
            this._uuId = _uuId;
        }
        label.stream().filter(s->!labels.contains(s)).forEach(s->{
            this.labels.add( s );
        });
    }

    public NodeInfo(long id, Object _uuId, List<String> label, Map<String,Object> parameter) {
        this.id = id;
        if( !Validator.check(this._uuId) ){
            this._uuId = _uuId;
        }
        label.stream().filter(s->!labels.contains(s)).forEach(s->{
            this.labels.add( s );
        });
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

    public NodeInfo setId(long id) {
        this.id = id;
        return this;
    }

    public List<String> getLabels() {
        return labels;
    }

    public NodeInfo setLabels(String ... label) {
        for(String info: label){
            if(!labels.contains(info)){
                this.labels.add(info);
            }
        }
       return this;
    }
    public NodeInfo setLabels(List<String> label) {
        label.stream().filter(s->!labels.contains(s)).forEach(s->{
            this.labels.add( s );
        });
        return this;
    }

    public Object get_uuId() {
        return _uuId;
    }

    public NodeInfo set_uuId(Object _uuId) {
        if( !Validator.check(this._uuId) ){
            this._uuId = _uuId;
        }
        return this;
    }


    public Map<String,Object> getParameters() {
        return parameters;
    }

    public NodeInfo setParameters(Map<String,Object> parameter) {
        if( Validator.check(parameter) ){
            parameter.forEach((k,v)->{
                if( !this.parameters.containsKey(k) ){
                    this.parameters.put(k,v);
                }
            });
        }
        return this;
    }
    public NodeInfo setParameters(String key,Object value) {
        if( Validator.check(key) ){
            if( !this.parameters.containsKey(key) ){
                this.parameters.put(key,value);
            }
        }
        return this;
    }
}
