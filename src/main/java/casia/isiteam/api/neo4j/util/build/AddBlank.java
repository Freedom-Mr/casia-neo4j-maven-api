package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;

import java.util.UUID;

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
    public static String addP(String nodeLation){
        return BLANK+P+EQUAL+nodeLation+BLANK;
    }
    protected StringBuffer s(){
        return new StringBuffer();
    }

    public String asKey(){
        return A+ UUID.randomUUID().toString().replaceAll(CROSS,NONE);
    }
}
