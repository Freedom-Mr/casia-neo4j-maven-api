package casia.isiteam.api.neo4j.util;

/**
 * ClassName: EscapUtil
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/29
 * Email: zhiyou_wang@foxmail.com
 */
public class EscapUtil {
    /**
     * @param
     * @return
     * @Description: TODO(替换影响数据入库的特殊字符)
     */
    public static Object repalceChars(Object object) {

        if (object instanceof String) {
            String entityName = (String) object;
            if (entityName != null) {

                // 先替换反斜杠
                entityName = entityName.replace("\\", "\\\\");

                // 再替换单引号
                entityName = String.valueOf(entityName).replace("'", "\\'");

                // 再替换双引号
                entityName = String.valueOf(entityName).replace("\"", "\\\"");
                return entityName;
            } else {
                return object;
            }
        } else {
            return object;
        }
    }
}
