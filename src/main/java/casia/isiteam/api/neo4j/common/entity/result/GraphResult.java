package casia.isiteam.api.neo4j.common.entity.result;

import casia.isiteam.api.toolutil.Validator;

import java.util.*;

/**
 * ClassName: GraphResult
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@foxmail.com
 */
public class GraphResult {
    private List<NodeInfo> nodeInfos = new ArrayList<>();
    private List<RelationInfo> relationInfos = new ArrayList<>();
    private List<LinkedHashMap<String,Object>> kwds = new ArrayList<>();

    public GraphResult() {

    }

    public GraphResult(List<NodeInfo> nodeInfo, List<RelationInfo> relationInfo, List<Object> kwd) {
        for( NodeInfo info : nodeInfo){
            if( !this.nodeInfos.contains(info) ){
                this.nodeInfos.add(info);
            }
        }
        for( RelationInfo info : relationInfo){
            if( !this.relationInfos.contains(info) ){
                this.relationInfos.add(info);
            }
        }
    }

    public GraphResult(NodeInfo... nodeInfo) {
        for( NodeInfo info : nodeInfo){
            if( !this.nodeInfos.contains(info) ){
                this.nodeInfos.add(info);
            }
        }
    }
    public GraphResult setNodeInfos(NodeInfo... nodeInfo) {
        for( NodeInfo info : nodeInfo){
            if( !this.nodeInfos.contains(info) ){
                this.nodeInfos.add(info);
            }
        }
        return this;
    }
    public GraphResult setNodeInfos(List<NodeInfo> nodeInfo) {
        for( NodeInfo info : nodeInfo){
            if( !this.nodeInfos.contains(info) ){
                this.nodeInfos.add(info);
            }
        }
        return this;
    }

    public GraphResult(RelationInfo... relationInfo) {
        for( RelationInfo info : relationInfo){
            if( !this.relationInfos.contains(info) ){
                this.relationInfos.add(info);
            }
        }
    }
    public GraphResult setRelationInfos(RelationInfo... relationInfo) {
        for( RelationInfo info : relationInfo){
            if( !this.relationInfos.contains(info) ){
                this.relationInfos.add(info);
            }
        }
        return this;
    }
    public GraphResult setRelationInfos(List<RelationInfo> relationInfo) {
        for( RelationInfo info : relationInfo){
            if( !this.relationInfos.contains(info) ){
                this.relationInfos.add(info);
            }
        }
        return this;
    }

    public GraphResult(List<LinkedHashMap<String,Object>> kwd) {
        if(Validator.check(kwd)){
            this.kwds.addAll(kwd);
        }
    }

    public GraphResult setKwds(List<LinkedHashMap<String,Object>> kwd) {
        if(Validator.check(kwd)){
            this.kwds.addAll(kwd);
        }
        return this;
    }
    public List<NodeInfo> getNodeInfos() {
        return nodeInfos;
    }
    public List<RelationInfo> getRelationInfos() {
        return relationInfos;
    }

    public List<LinkedHashMap<String,Object>> getKwds() {
        return kwds;
    }


}
