package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.FieldCombine;
import casia.isiteam.api.neo4j.util.EscapUtil;
import casia.isiteam.api.neo4j.util.TypeUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * ClassName: BuildWhere
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/27
 * Email: zhiyou_wang@foxmail.com
 */
public class BuildWhere extends BuildSet {
    /**
     * build where
     * @param asKey
     * @param fieldCombine
     * @param attributes
     * @return
     */
    public static String where(String asKey,String isNot , FieldCombine fieldCombine, List<Attribute> attributes){
        StringBuffer sb_parm = new StringBuffer();
        if( Validator.check(attributes) ){
            attributes.forEach(s->{
                sb_parm.append(sb_parm.length()>0? addBlank(fieldCombine.name()) : NONE).
                        append( addBlank(isNot) ).
                        append(asKey).append(DOT).
                        append(s.getKey()).
                        append( TypeUtil.LevelRecognition(s.getMatchLevel(),s.getValue() ) );
            });
        }
        return sb_parm.length()>0 ? addBlank(WHERE)+sb_parm : NONE;
    }
    public static String where(String asKey,String isNot , FieldCombine fieldCombine,Map<String,Object> attributes){
        StringBuffer sb_parm = new StringBuffer();
        if( Validator.check(attributes) ){
            attributes.forEach((k,v)->{
                sb_parm.append(sb_parm.length()>0? addBlank(fieldCombine.name()) : NONE).
                        append( addBlank(isNot) ).
                        append(asKey).append(DOT).
                        append(k).
                        append( TypeUtil.typeRecognition(EscapUtil.repalceChars(v)) );
            });
        }
        return sb_parm.length()>0 ? addBlank(WHERE)+sb_parm : NONE;
    }
    public static String where(String asKey, String isNot, Long id){
        return where(asKey ,isNot, new Long[]{id});
    }
    public static String where(String asKey,String isNot, Long ... ids){
        StringBuffer sb_where = new StringBuffer();
        if( Validator.check(ids) ){
            sb_where.append(sb_where.length()>0?  isNot : NONE).
                    append(ID).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES).append(addBlank(IN)).append(JSONArray.toJSON(ids));
        }
        return sb_where.length()>0 ? addBlank(WHERE)+sb_where+BLANK : NONE;
    }



    public static String where(String asKey, Map<String,Object> parameter){
        StringBuffer sb_parm = new StringBuffer();
        if( Validator.check(parameter) ){
            parameter.forEach((k,v)->{
                sb_parm.append(sb_parm.length()>0? COMMA : NONE).
                        append(asKey).append(DOT).
                        append(k).
                        append( EQUAL ).
                        append( TypeUtil.typeRecognition( EscapUtil.repalceChars(v) ) );
            });
        }
        return sb_parm.length()>0 ? addBlank(WHERE)+sb_parm : NONE;
    }
}
