package casia.isiteam.api.neo4j.common.entity.vo;

import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.regex.CasiaRegexUtil;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: _Entity_Driver
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/20
 * Email: zhiyou_wang@foxmail.com
 */
public class _Entity_Driver extends BasicParms {
    private String dbname;
    private String md5;
    private List<String> address = new ArrayList<>();
    private String username;
    private String password;

    public _Entity_Driver(String dbname) {
        this.dbname = dbname;
    }

    public _Entity_Driver(String ... blots) {
        for(String blot:blots){
            if( CasiaRegexUtil.isMatch(blot,IP) ){
                continue;
            };
            if( !this.address.contains(blot) ){
                this.address.add(blot);
            }
        }
    }
    public _Entity_Driver( String username, String password,String ... blots) {
        for(String blot:blots){
            if( CasiaRegexUtil.isMatch(blot,IP) ){
                continue;
            };
            if( !this.address.contains(blot) ){
                this.address.add(blot);
            }
        }
        this.username = username;
        this.password = password;
    }

    public _Entity_Driver(String dbname, String username, String password,String ... blots) {
        this.dbname = dbname;
        for(String blot:blots){
            if( CasiaRegexUtil.isMatch(blot,IP) ){
                continue;
            };
            if( !this.address.contains(blot) ){
                this.address.add(blot);
            }
        }
        this.username = username;
        this.password = password;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getBolt() {
        return Validator.check(this.address) ? BOLT_COLON_SLASH+address.stream().findFirst().get() : NULL;
    }
    public String getJdbcBolt() {
        return Validator.check(this.address) ? JDBC_BOLT_COLON_SLASH+address.stream().findFirst().get() : NULL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String hexMd5() {
        StringBuffer sb = new StringBuffer();
        this.address.forEach(s->{ sb.append(s);});
        return DigestUtils.md5Hex(this.username+sb+this.password );
    }
}
