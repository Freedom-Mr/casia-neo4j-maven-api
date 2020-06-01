package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;

/**
 * ClassName: AddBlank
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/27
 * Email: zhiyou_wang@foxmail.com
 */
public class AddBlank extends BasicParms {
    public static String addBlank(String key){
        return BLANK+key+BLANK;
    }
}
