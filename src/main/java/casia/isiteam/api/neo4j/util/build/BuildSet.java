package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.util.EscapUtil;
import casia.isiteam.api.neo4j.util.TypeUtil;
import casia.isiteam.api.toolutil.Validator;

import java.util.List;
import java.util.Map;

/**
 * ClassName: BuildSet
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/27
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildSet extends BuildReturn{
    /**
     * build set
     * @param asKey
     * @param parms
     * @return
     */
    public static String buildSet(String asKey, Map<String,Object> parms){
        if( Validator.check(parms) ){
            StringBuffer sb_set = new StringBuffer();
            sb_set.append(addBlank(SET));

            StringBuffer sb_parm = new StringBuffer();
            parms.forEach((k,v)->{
                sb_parm.append(sb_parm.length()>0? COMMA : NONE).append(asKey).append(DOT).append(k).append(EQUAL).append(
                        TypeUtil.typeRecognition( EscapUtil.repalceChars(v) )
                );
            });
            return  sb_set.toString()+sb_parm.append(BLANK);
        }
        return NONE;
    }
}
