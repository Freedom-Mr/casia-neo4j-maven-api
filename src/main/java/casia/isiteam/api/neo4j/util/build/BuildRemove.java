package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.toolutil.Validator;

import java.util.List;

/**
 * ClassName: BuildRemove
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/6/3
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildRemove extends BuildWhere{
    /**
     * remove labels
     * @param asKey
     * @param labels
     * @return
     */
    public String remove(String asKey, List<String> labels){
        if( Validator.check(labels) ){
            StringBuffer sb_set = new StringBuffer();
            sb_set.append(addBlank(REMOVE)).append(asKey);
            labels.forEach(v->{
                sb_set.append(COLON).append(v);
            });
            return  sb_set.append(BLANK).toString();
        }
        return NONE;
    }
}
