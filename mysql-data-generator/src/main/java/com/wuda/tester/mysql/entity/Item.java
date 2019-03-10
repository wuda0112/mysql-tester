package com.wuda.tester.mysql.entity;

import com.wuda.yhan.code.generator.lang.TableEntity;
import com.wuda.yhan.util.commons.IsSetField;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(
        schema = "mysql_tester",
        name = "item"
)
public final class Item implements TableEntity, Serializable {
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
            name = "item_type",
            length = 3,
            columnDefinition = "TINYINT(3) UNSIGNED"
    )
    private Integer itemType;

    @IsSetField(
            referenceField = "itemType"
    )
    private boolean itemTypeIsSet;

    @Column(
            name = "item_name",
            length = 45,
            columnDefinition = "VARCHAR(45)"
    )
    private String itemName;

    @IsSetField(
            referenceField = "itemName"
    )
    private boolean itemNameIsSet;

    @Column(
            name = "category_one_id",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long categoryOneId;

    @IsSetField(
            referenceField = "categoryOneId"
    )
    private boolean categoryOneIdIsSet;

    @Column(
            name = "category_two_id",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long categoryTwoId;

    @IsSetField(
            referenceField = "categoryTwoId"
    )
    private boolean categoryTwoIdIsSet;

    @Column(
            name = "category_three_id",
            length = 20,
            columnDefinition = "BIGINT(20) UNSIGNED"
    )
    private Long categoryThreeId;

    @IsSetField(
            referenceField = "categoryThreeId"
    )
    private boolean categoryThreeIdIsSet;

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

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
        this.itemTypeIsSet = true;
    }

    public boolean getItemTypeIsSet() {
        return itemTypeIsSet;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        this.itemNameIsSet = true;
    }

    public boolean getItemNameIsSet() {
        return itemNameIsSet;
    }

    public Long getCategoryOneId() {
        return categoryOneId;
    }

    public void setCategoryOneId(Long categoryOneId) {
        this.categoryOneId = categoryOneId;
        this.categoryOneIdIsSet = true;
    }

    public boolean getCategoryOneIdIsSet() {
        return categoryOneIdIsSet;
    }

    public Long getCategoryTwoId() {
        return categoryTwoId;
    }

    public void setCategoryTwoId(Long categoryTwoId) {
        this.categoryTwoId = categoryTwoId;
        this.categoryTwoIdIsSet = true;
    }

    public boolean getCategoryTwoIdIsSet() {
        return categoryTwoIdIsSet;
    }

    public Long getCategoryThreeId() {
        return categoryThreeId;
    }

    public void setCategoryThreeId(Long categoryThreeId) {
        this.categoryThreeId = categoryThreeId;
        this.categoryThreeIdIsSet = true;
    }

    public boolean getCategoryThreeIdIsSet() {
        return categoryThreeIdIsSet;
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
