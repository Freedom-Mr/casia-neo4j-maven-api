package casia.isiteam.api.neo4j.util;

import casia.isiteam.api.neo4j.common.enums.MatchLevel;
import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.toolutil.Validator;

/**
 * ClassName: TypeUtil
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@foxmail.com
 */
public class TypeUtil extends BasicParms {
    public static Object LevelRecognition(MatchLevel matchLevel,Object parm){
        Object pm = typeRecognition(parm);
        if( !Validator.check(matchLevel) || MatchLevel.Term.getLevel().equals( matchLevel.getLevel()) ){
            return EQUAL+pm;
        }else if( MatchLevel.Prefix.getLevel().equals( matchLevel.getLevel()) ||
                MatchLevel.Suffix.getLevel().equals( matchLevel.getLevel()) ||
                MatchLevel.Contains.getLevel().equals( matchLevel.getLevel()) ){
            return matchLevel.getLevel()+pm;
        }else if(  MatchLevel.Regexp.getLevel().equals( matchLevel.getLevel()) ){
            return EQUAL+BREAK_LINE+typeRecognition(DOT+STAR+parm+DOT+STAR);
        }else if( MatchLevel.Missing.getLevel().equals( matchLevel.getLevel())){
            return NULL;
        }
        return pm;
    }

    public static Object typeRecognition(Object parm){
        if( parm instanceof String){
            return "'"+EscapUtil.repalceChars(parm)+"'";
        }else{
            return parm;
        }
    }
}
