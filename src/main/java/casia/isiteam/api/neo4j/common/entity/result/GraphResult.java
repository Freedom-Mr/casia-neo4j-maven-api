package casia.isiteam.api.neo4j.common.entity.result;

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
    private List<Object[]> kwds = new ArrayList<>();

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

    public GraphResult(List<Object[]> kwd) {
        kwd.stream().filter(s->!this.kwds.contains(s)).forEach(s->{
            this.kwds.add(s);
        });
    }
    public GraphResult(Object[] ... kwd) {
        for(Object[] info : kwd){
            if(!this.kwds.contains(info) ){
                this.kwds.add(info);
            }
        }
    }
    public GraphResult setKwds(List<Object[]> kwd) {
        kwd.stream().filter(s->!this.kwds.contains(s)).forEach(s->{
            this.kwds.add(s);
        });
        return this;
    }
    public GraphResult setKwds(Object[] ... kwd) {
        for(Object[] info : kwd){
            if(!this.kwds.contains(info) ){
                this.kwds.add(info);
            }
        }
        return this;
    }
    public List<NodeInfo> getNodeInfos() {
        return nodeInfos;
    }
    public List<RelationInfo> getRelationInfos() {
        return relationInfos;
    }

    public List<Object[]> getKwds() {
        return kwds;
    }


}
