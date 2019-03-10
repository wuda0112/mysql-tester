package com.wuda.tester.mysql.entity;

import com.wuda.yhan.code.generator.lang.TableEntity;
import com.wuda.yhan.util.commons.IsSetField;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(
        schema = "mysql_tester",
        name = "individual_user"
)
public final class IndividualUser implements TableEntity, Serializable {
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
            name = "username",
            length = 45,
            columnDefinition = "VARCHAR(45)"
    )
    private String username;

    @IsSetField(
            referenceField = "username"
    )
    private boolean usernameIsSet;

    @Column(
            name = "password",
            length = 45,
            columnDefinition = "VARCHAR(45)"
    )
    private String password;

    @IsSetField(
            referenceField = "password"
    )
    private boolean passwordIsSet;

    @Column(
            name = "mobile_phone",
            length = 10,
            columnDefinition = "INT(10) UNSIGNED"
    )
    private Integer mobilePhone;

    @IsSetField(
            referenceField = "mobilePhone"
    )
    private boolean mobilePhoneIsSet;

    @Column(
            name = "email",
            length = 45,
            columnDefinition = "VARCHAR(45)"
    )
    private String email;

    @IsSetField(
            referenceField = "email"
    )
    private boolean emailIsSet;

    @Column(
            name = "status",
            length = 3,
            columnDefinition = "TINYINT(3) UNSIGNED"
    )
    private Integer status;

    @IsSetField(
            referenceField = "status"
    )
    private boolean statusIsSet;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.usernameIsSet = true;
    }

    public boolean getUsernameIsSet() {
        return usernameIsSet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.passwordIsSet = true;
    }

    public boolean getPasswordIsSet() {
        return passwordIsSet;
    }

    public Integer getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Integer mobilePhone) {
        this.mobilePhone = mobilePhone;
        this.mobilePhoneIsSet = true;
    }

    public boolean getMobilePhoneIsSet() {
        return mobilePhoneIsSet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.emailIsSet = true;
    }

    public boolean getEmailIsSet() {
        return emailIsSet;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        this.statusIsSet = true;
    }

    public boolean getStatusIsSet() {
        return statusIsSet;
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
