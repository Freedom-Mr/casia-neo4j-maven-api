package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.util.CqlBuilder;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * ClassName: BuildReturn
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/27
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildReturn extends AddBlank {

    public static String returnField(String asKey, List<String> returnFields){
        StringBuffer sb_return = new StringBuffer();
        if( Validator.check(returnFields) ){
            returnFields.stream().filter(s->Validator.check(s)).forEach(s->{
                sb_return.append(sb_return.length()>0? COMMA : NONE);
                if( s.equals(_ID_) ){
                    sb_return.append(ID).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES);
                }else{
                    sb_return.append(asKey).append(DOT).append(s);
                }
            });
        }else{
            sb_return.append(addBlank(STAR));
        }
        return sb_return.length()>0 ? addBlank(RETURN)+sb_return+BLANK : NONE;
    }
    public static String returnField(String asKey, List<String> returnFields,Long from,Long to){
        StringBuffer sb_return = new StringBuffer();
        sb_return.append(returnField(asKey,returnFields));
        if( Validator.check(from) ){
            sb_return.append(addBlank(SKIP)).append(from);
        }
        if( Validator.check(to) ){
            sb_return.append(addBlank(LIMIT)).append(to);
        }

        return sb_return.toString();
    }
    public static String returnCount(String asKey){
        StringBuffer sb_return = new StringBuffer();
        sb_return.append(addBlank(RETURN)).append(addBlank(COUNT)).append(CqlBuilder.node(asKey));
        return sb_return.toString();
    }
}
