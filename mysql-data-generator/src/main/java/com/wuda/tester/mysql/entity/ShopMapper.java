package com.wuda.tester.mysql.entity;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopMapper {
    @InsertProvider(
            type = ShopSqlBuilder.class,
            method = "insert"
    )
    int insert(Shop shop);

    @Insert("<script>\r\n"
            + "INSERT INTO mysql_tester.shop(id,user_id,shop_name,create_time,create_user,last_modify_time,last_modify_user,is_deleted)\r\n"
            + " VALUES\r\n"
            + "<foreach item='entity' collection='list' open='' separator=',' close=''>\r\n"
            + "(#{entity.id},#{entity.userId},#{entity.shopName},#{entity.createTime},#{entity.createUser},#{entity.lastModifyTime},#{entity.lastModifyUser},#{entity.isDeleted})\r\n"
            + "</foreach>\r\n"
            + "</script>")
    int batchInsert(@Param("list") List<Shop> list);

    @DeleteProvider(
            type = ShopSqlBuilder.class,
            method = "deleteByPrimaryKey"
    )
    int deleteByPrimaryKey(@Param("id") Long id);

    @UpdateProvider(
            type = ShopSqlBuilder.class,
            method = "updateByPrimaryKey"
    )
    int updateByPrimaryKey(Shop shop);

    @SelectProvider(
            type = ShopSqlBuilder.class,
            method = "selectByPrimaryKey"
    )
    Shop selectByPrimaryKey(@Param("id") Long id, @Param("columns") String[] columns);
}
