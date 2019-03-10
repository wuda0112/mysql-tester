package com.wuda.tester.mysql.entity;

import com.wuda.yhan.code.generator.lang.TableEntity;
import com.wuda.yhan.util.commons.IsSetField;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(
        schema = "mysql_tester",
        name = "warehouse"
)
public final class Warehouse implements TableEntity, Serializable {
    @Column(
            name = "id",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long id;

    @IsSetField(
            referenceField = "id"
    )
    private boolean idIsSet;

    @Column(
            name = "user_id",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long userId;

    @IsSetField(
            referenceField = "userId"
    )
    private boolean userIdIsSet;

    @Column(
            name = "shop_id",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long shopId;

    @IsSetField(
            referenceField = "shopId"
    )
    private boolean shopIdIsSet;

    @Column(
            name = "warehouse_type",
            length = 3,
            columnDefinition = "TINYINT(3) UNSIGNED"
    )
    private Integer warehouseType;

    @IsSetField(
            referenceField = "warehouseType"
    )
    private boolean warehouseTypeIsSet;

    @Column(
            name = "warehouse_name",
            length = 45,
            columnDefinition = "VARCHAR(45)"
    )
    private String warehouseName;

    @IsSetField(
            referenceField = "warehouseName"
    )
    private boolean warehouseNameIsSet;

    @Column(
            name = "create_time",
            length = 0,
            columnDefinition = "DATETIME"
    )
    private Timestamp createTime;

    @IsSetField(
            referenceField = "createTime"
    )
    private boolean createTimeIsSet;

    @Column(
            name = "create_user",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long createUser;

    @IsSetField(
            referenceField = "createUser"
    )
    private boolean createUserIsSet;

    @Column(
            name = "last_modify_time",
            length = 0,
            columnDefinition = "DATETIME"
    )
    private Timestamp lastModifyTime;

    @IsSetField(
            referenceField = "lastModifyTime"
    )
    private boolean lastModifyTimeIsSet;

    @Column(
            name = "last_modify_user",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long lastModifyUser;

    @IsSetField(
            referenceField = "lastModifyUser"
    )
    private boolean lastModifyUserIsSet;

    @Column(
            name = "is_deleted",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long isDeleted;

    @IsSetField(
            referenceField = "isDeleted"
    )
    private boolean isDeletedIsSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.idIsSet = true;
    }

    public boolean getIdIsSet() {
        return idIsSet;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.userIdIsSet = true;
    }

    public boolean getUserIdIsSet() {
        return userIdIsSet;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
        this.shopIdIsSet = true;
    }

    public boolean getShopIdIsSet() {
        return shopIdIsSet;
    }

    public Integer getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(Integer warehouseType) {
        this.warehouseType = warehouseType;
        this.warehouseTypeIsSet = true;
    }

    public boolean getWarehouseTypeIsSet() {
        return warehouseTypeIsSet;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
        this.warehouseNameIsSet = true;
    }

    public boolean getWarehouseNameIsSet() {
        return warehouseNameIsSet;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        this.createTimeIsSet = true;
    }

    public boolean getCreateTimeIsSet() {
        return createTimeIsSet;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
        this.createUserIsSet = true;
    }

    public boolean getCreateUserIsSet() {
        return createUserIsSet;
    }

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
        this.lastModifyTimeIsSet = true;
    }

    public boolean getLastModifyTimeIsSet() {
        return lastModifyTimeIsSet;
    }

    public Long getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(Long lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
        this.lastModifyUserIsSet = true;
    }

    public boolean getLastModifyUserIsSet() {
        return lastModifyUserIsSet;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
        this.isDeletedIsSet = true;
    }

    public boolean getIsDeletedIsSet() {
        return isDeletedIsSet;
    }
}
