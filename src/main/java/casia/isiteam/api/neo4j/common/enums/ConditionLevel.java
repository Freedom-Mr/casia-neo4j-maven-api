package casia.isiteam.api.neo4j.common.enums;

/**
 * ClassName: ConditionLevel
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/1
 * Email: zhiyou_wang@foxmail.com
 */
public enum ConditionLevel {

    NOT(" not "),
    MUST("");

    private String level;
    private ConditionLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
}
