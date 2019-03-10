package com.wuda.tester.mysql.entity;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseMapper {
    @InsertProvider(
            type = WarehouseSqlBuilder.class,
            method = "insert"
    )
    int insert(Warehouse warehouse);

    @Insert("<script>\r\n"
            + "INSERT INTO mysql_tester.warehouse(id,user_id,shop_id,warehouse_type,warehouse_name,create_time,create_user,last_modify_time,last_modify_user,is_deleted)\r\n"
            + " VALUES\r\n"
            + "<foreach item='entity' collection='list' open='' separator=',' close=''>\r\n"
            + "(#{entity.id},#{entity.userId},#{entity.shopId},#{entity.warehouseType},#{entity.warehouseName},#{entity.createTime},#{entity.createUser},#{entity.lastModifyTime},#{entity.lastModifyUser},#{entity.isDeleted})\r\n"
            + "</foreach>\r\n"
            + "</script>")
    int batchInsert(@Param("list") List<Warehouse> list);

    @DeleteProvider(
            type = WarehouseSqlBuilder.class,
            method = "deleteByPrimaryKey"
    )
    int deleteByPrimaryKey(@Param("id") Long id);

    @UpdateProvider(
            type = WarehouseSqlBuilder.class,
            method = "updateByPrimaryKey"
    )
    int updateByPrimaryKey(Warehouse warehouse);

    @SelectProvider(
            type = WarehouseSqlBuilder.class,
            method = "selectByPrimaryKey"
    )
    Warehouse selectByPrimaryKey(@Param("id") Long id, @Param("columns") String[] columns);
}
