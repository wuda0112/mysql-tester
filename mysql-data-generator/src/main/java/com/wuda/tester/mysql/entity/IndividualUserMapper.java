package com.wuda.tester.mysql.entity;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IndividualUserMapper {
    @InsertProvider(
            type = IndividualUserSqlBuilder.class,
            method = "insert"
    )
    int insert(IndividualUser individualUser);

    @Insert("<script>\r\n"
            + "INSERT INTO mysql_tester.individual_user(id,username,password,mobile_phone,email,status,create_time,create_user,last_modify_time,last_modify_user,is_deleted)\r\n"
            + " VALUES\r\n"
            + "<foreach item='entity' collection='list' open='' separator=',' close=''>\r\n"
            + "(#{entity.id},#{entity.username},#{entity.password},#{entity.mobilePhone},#{entity.email},#{entity.status},#{entity.createTime},#{entity.createUser},#{entity.lastModifyTime},#{entity.lastModifyUser},#{entity.isDeleted})\r\n"
            + "</foreach>\r\n"
            + "</script>")
    int batchInsert(@Param("list") List<IndividualUser> list);

    @DeleteProvider(
            type = IndividualUserSqlBuilder.class,
            method = "deleteByPrimaryKey"
    )
    int deleteByPrimaryKey(@Param("id") Long id);

    @UpdateProvider(
            type = IndividualUserSqlBuilder.class,
            method = "updateByPrimaryKey"
    )
    int updateByPrimaryKey(IndividualUser individualUser);

    @SelectProvider(
            type = IndividualUserSqlBuilder.class,
            method = "selectByPrimaryKey"
    )
    IndividualUser selectByPrimaryKey(@Param("id") Long id, @Param("columns") String[] columns);
}
