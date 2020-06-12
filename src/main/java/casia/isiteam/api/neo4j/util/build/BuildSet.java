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
public class BuildSet extends BuildNodeRelation {
    /**
     * build set
     * @param asKey
     * @param parms
     * @return
     */
    public String set(String asKey, Map<String,Object> parms){
        if( Validator.check(parms) ){
            StringBuffer sb_set = s();
            sb_set.append(addBlank(SET));

            StringBuffer sb_parm = s();
            parms.forEach((k,v)->{
                sb_parm.append(sb_parm.length()>0? COMMA : NONE).append(asKey).append(DOT).append(k).append(EQUAL).append(
                        TypeUtil.typeRecognition( EscapUtil.repalceChars(v) )
                );
            });
            return  sb_set.toString()+sb_parm.append(BLANK);
        }
        return NONE;
    }

    /**
     * build set labels
     * @param asKey
     * @param labels
     * @return
     */
    public String set(String asKey, List<String> labels){
        if( Validator.check(labels) ){
            StringBuffer sb_set = s();
            sb_set.append(addBlank(SET)).append(asKey);
            labels.forEach(v->{
                sb_set.append(COLON).append(v);
            });
            return  sb_set.append(BLANK).toString();
        }
        return NONE;
    }
}
