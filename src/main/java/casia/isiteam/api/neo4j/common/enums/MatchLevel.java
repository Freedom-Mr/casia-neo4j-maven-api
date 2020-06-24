package casia.isiteam.api.neo4j.common.enums;

/**
 * ClassName: MatchType
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/27
 * Email: zhiyou_wang@foxmail.com
 */
public enum MatchLevel {

    /**
     * 完全匹配
     */
    Term("term"),
    /**
     * 包含
     */
    Contains("CONTAINS"),
    /**
     * 前缀匹配
     */
    Prefix("STARTS WITH"),
    /**
     * 前缀匹配
     */
    Suffix("ENDS WITH"),
    /**
     * 正则匹配
     */
    Regexp("Regexp"),
    /**
     * 属性不存在
     */
    Missing("Missing"),
    /**
     * 大于
     */
    Outgoing(">"),
    /**
     * 小于
     */
    Incoming("<");

    private String level;
    private MatchLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
}
