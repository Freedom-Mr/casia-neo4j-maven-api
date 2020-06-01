package casia.isiteam.api.neo4j.common.entity.vo;

import casia.isiteam.api.neo4j.common.manage.parms.BasicParms;
import casia.isiteam.api.toolutil.Validator;
import casia.isiteam.api.toolutil.regex.CasiaRegexUtil;
import org.neo4j.driver.v1.net.ServerAddress;

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
    private List<ServerAddress> bolts = new ArrayList<>();
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
            ServerAddress serverAddress = ServerAddress.of(blot.split(COLON)[0],Integer.parseInt(blot.split(COLON)[1]) );
            if( !this.bolts.contains(serverAddress) ){
                this.bolts.add(serverAddress);
            }
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
            ServerAddress serverAddress = ServerAddress.of(blot.split(COLON)[0],Integer.parseInt(blot.split(COLON)[1]) );
            if( !this.bolts.contains(serverAddress) ){
                this.bolts.add(serverAddress);
            }
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
            ServerAddress serverAddress = ServerAddress.of(blot.split(COLON)[0],Integer.parseInt(blot.split(COLON)[1]) );
            if( !this.bolts.contains(serverAddress) ){
                this.bolts.add(serverAddress);
            }
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

    public List<ServerAddress> getBolts() {
        return bolts;
    }
    public String getBolt() {
        return Validator.check(this.address) ? BOLT_COLON_SLASH+address.stream().findFirst().get() : NULL;
    }
    public void setBolts(List<ServerAddress> bolts) {
        this.bolts = bolts;
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
}
