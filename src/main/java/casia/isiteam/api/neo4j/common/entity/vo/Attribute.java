package casia.isiteam.api.neo4j.common.entity.vo;

import casia.isiteam.api.neo4j.common.enums.MatchLevel;

/**
 * ClassName: Attribute
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/27
 * Email: zhiyou_wang@foxmail.com
 */
public class Attribute {
    private MatchLevel matchLevel;
    private String key;
    private Object value;

    public Attribute() {
    }

    public Attribute(MatchLevel matchLevel, String key, Object value) {
        this.matchLevel = matchLevel;
        this.key = key;
        this.value = value;
    }

    public Attribute(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public MatchLevel getMatchLevel() {
        return matchLevel;
    }

    public Attribute setMatchLevel(MatchLevel matchLevel) {
        this.matchLevel = matchLevel;
        return this;
    }

    public String getKey() {
        return key;
    }

    public Attribute setKey(String key) {
        this.key = key;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Attribute setValue(Object value) {
        this.value = value;
        return this;
    }
}
