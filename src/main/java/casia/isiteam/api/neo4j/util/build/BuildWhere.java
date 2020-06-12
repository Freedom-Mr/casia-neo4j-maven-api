package casia.isiteam.api.neo4j.util.build;

import casia.isiteam.api.neo4j.common.entity.vo.Attribute;
import casia.isiteam.api.neo4j.common.enums.ConditionLevel;
import casia.isiteam.api.neo4j.common.enums.ParameterCombine;
import casia.isiteam.api.neo4j.util.EscapUtil;
import casia.isiteam.api.neo4j.util.TypeUtil;
import casia.isiteam.api.toolutil.Validator;
import com.alibaba.fastjson.JSONArray;

import java.util.Arrays;
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
     * @param parameterCombine
     * @param attributes
     * @return
     */
    public String where(String asKey, String isNot , ParameterCombine parameterCombine, List<Attribute> attributes){
        StringBuffer sb_parm = new StringBuffer();
        if( Validator.check(attributes) ){
            attributes.forEach(s->{
                sb_parm.append(sb_parm.length()>0? addBlank(parameterCombine.name()) : NONE).
                        append( addBlank(isNot) ).
                        append(asKey).append(DOT).
                        append(s.getKey()).
                        append( TypeUtil.LevelRecognition(s.getMatchLevel(),s.getValue() ) );
            });
        }
        return sb_parm.length()>0 ? addBlank(WHERE)+sb_parm : NONE;
    }
    public String where(String asKey, String isNot , ParameterCombine parameterCombine, Map<String,Object> attributes){
        StringBuffer sb_parm = new StringBuffer();
        if( Validator.check(attributes) ){
            attributes.forEach((k,v)->{
                sb_parm.append(sb_parm.length()>0? addBlank(parameterCombine.name()) : NONE).
                        append( addBlank(isNot) ).
                        append(asKey).append(DOT).
                        append(k).
                        append( TypeUtil.typeRecognition(EscapUtil.repalceChars(v)) );
            });
        }
        return sb_parm.length()>0 ? addBlank(WHERE)+sb_parm : NONE;
    }
    public String where(String asKey, String isNot, Long id){
        return where(asKey ,isNot, Arrays.asList(id));
    }
    public String where(String asKey,String isNot, Long ... ids){
        return where(asKey,isNot, Validator.check(ids) ? Arrays.asList( ids) :null);
    }
    public String where(String asKey,String isNot, List<Long> ids){
        StringBuffer sb_where = new StringBuffer();
        if( Validator.check(ids) ){
            sb_where.append(addBlank(isNot)).
                    append(ID).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES)
                    .append( ids.size()==1 ? EQUAL+ids.get(0) : addBlank(IN) +JSONArray.toJSON(ids) );
        }
        return sb_where.length()>0 ? addBlank(WHERE)+sb_where+BLANK : NONE;
    }
    public String where(String asKey, ConditionLevel conditionLevel, String type){
        StringBuffer sb_where = new StringBuffer();
        if( Validator.check(type) ){
            sb_where.append( conditionLevel.getLevel() ).
                    append(TYPE).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES).append(EQUAL).append(TypeUtil.typeRecognition(type));
        }
        return sb_where.length()>0 ? addBlank(WHERE)+sb_where+BLANK : NONE;
    }


    public String where(String asKey, Map<String,Object> parameter){
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
