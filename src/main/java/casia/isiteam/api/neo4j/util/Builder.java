package casia.isiteam.api.neo4j.util;

import casia.isiteam.api.neo4j.common.entity.result.NodeInfo;
import casia.isiteam.api.neo4j.common.entity.model.RelationshipInfo;
import casia.isiteam.api.neo4j.common.enums.CreateType;
import casia.isiteam.api.neo4j.util.build.BuildCql;
import casia.isiteam.api.neo4j.util.build.BuildNode;
import casia.isiteam.api.neo4j.util.build.BuildReturn;
import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ClassName: Builder
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@foxmail.com
 */
public class Builder extends BuildCql {



    /**
     * build new label cql info by create node
     * @param nodeInfo
     * @return
     */
    public List<String> addLabelByNodeIdBuilder(NodeInfo ... nodeInfo){
        if( !Validator.check(nodeInfo) ){
            return new ArrayList<>();
        }

        List<String> cqls = new ArrayList<>();
        for(NodeInfo node:nodeInfo){
            if( !Validator.check(node.getId()) || !Validator.check(node.getLabels()) ){
                continue;
            }

            String asKey = asKey();
            StringBuffer sb = new StringBuffer();
            sb.append(addBlank(MATCH)).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES).append(addBlank(WHERE)).
                    append(ID).append(LEFT_PARENTHESES).append(asKey).append(RIGHT_PARENTHESES).append(EQUAL).append(node.getId())
                    .append(addBlank(SET))
                    .append(asKey);
            node.getLabels().forEach(s->{
                sb.append(COLON).append(s);
            });
            cqls.add(sb.toString());
        }
        return cqls;
    }














}
