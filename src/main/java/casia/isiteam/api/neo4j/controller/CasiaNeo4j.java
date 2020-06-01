package casia.isiteam.api.neo4j.controller;

import casia.isiteam.api.neo4j.common.entity.vo._Entity_Driver;
import casia.isiteam.api.neo4j.common.manage.status.Neo4jDriverStatus;
import casia.isiteam.api.neo4j.controller.imp.CreateBuider;
import casia.isiteam.api.neo4j.controller.imp.DeleteBuider;
import casia.isiteam.api.neo4j.controller.imp.QueryBuider;
import casia.isiteam.api.neo4j.controller.imp.TreeBuider;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.net.ServerAddress;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * ClassName: CasiaNeo4jCreate
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/20
 * Email: zhiyou_wang@foxmail.com
 */
public class CasiaNeo4j {
    private Logger logger = LoggerFactory.getLogger( this.getClass() );
    private _Entity_Driver entity_driver;

    public CasiaNeo4j(String driverName) {
        entity_driver= new _Entity_Driver(driverName);
    }
    public CasiaNeo4j(String userName,String password,String ... blots) {
        entity_driver= new _Entity_Driver(userName,password, blots);
    }
    public CasiaNeo4j(String ... blots) {
        entity_driver= new _Entity_Driver(blots);
    }

    public CreateBuider create(){
        return new CreateBuider(entity_driver);
    }
    public QueryBuider query(){
        return new QueryBuider(entity_driver);
    }
    public DeleteBuider delete(){
        return new DeleteBuider(entity_driver);
    }
    public TreeBuider tree(){
        return new TreeBuider(entity_driver);
    }
}
