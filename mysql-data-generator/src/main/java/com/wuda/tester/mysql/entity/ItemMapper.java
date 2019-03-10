package com.wuda.tester.mysql.entity;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {
    @InsertProvider(
            type = ItemSqlBuilder.class,
            method = "insert"
    )
    int insert(Item item);

    @Insert("<script>\r\n"
            + "INSERT INTO mysql_tester.item(id,shop_id,item_type,item_name,category_one_id,category_two_id,category_three_id,create_time,create_user,last_modify_time,last_modify_user,is_deleted)\r\n"
            + " VALUES\r\n"
            + "<foreach item='entity' collection='list' open='' separator=',' close=''>\r\n"
            + "(#{entity.id},#{entity.shopId},#{entity.itemType},#{entity.itemName},#{entity.categoryOneId},#{entity.categoryTwoId},#{entity.categoryThreeId},#{entity.createTime},#{entity.createUser},#{entity.lastModifyTime},#{entity.lastModifyUser},#{entity.isDeleted})\r\n"
            + "</foreach>\r\n"
            + "</script>")
    int batchInsert(@Param("list") List<Item> list);

    @DeleteProvider(
            type = ItemSqlBuilder.class,
            method = "deleteByPrimaryKey"
    )
    int deleteByPrimaryKey(@Param("id") Long id);

    @UpdateProvider(
            type = ItemSqlBuilder.class,
            method = "updateByPrimaryKey"
    )
    int updateByPrimaryKey(Item item);

    @SelectProvider(
            type = ItemSqlBuilder.class,
            method = "selectByPrimaryKey"
    )
    Item selectByPrimaryKey(@Param("id") Long id, @Param("columns") String[] columns);
}
