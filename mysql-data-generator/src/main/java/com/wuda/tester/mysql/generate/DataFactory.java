package com.wuda.tester.mysql.generate;

import com.wuda.tester.mysql.TableInsertion;
import com.wuda.tester.mysql.TableName;
import com.wuda.tester.mysql.cli.CliArgs;
import com.wuda.tester.mysql.commons.keygen.KeyGeneratorSnowflake;
import com.wuda.tester.mysql.commons.utils.RandomUtilsExt;
import org.apache.commons.lang3.RandomUtils;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.wuda.tester.mysql.orm.tables.IndividualUserGeneral.INDIVIDUAL_USER_GENERAL;
import static com.wuda.tester.mysql.orm.tables.Item.ITEM;
import static com.wuda.tester.mysql.orm.tables.ItemDescription.ITEM_DESCRIPTION;
import static com.wuda.tester.mysql.orm.tables.ItemGeneral.ITEM_GENERAL;
import static com.wuda.tester.mysql.orm.tables.Store.STORE;
import static com.wuda.tester.mysql.orm.tables.StoreGeneral.STORE_GENERAL;
import static com.wuda.tester.mysql.orm.tables.User.USER;

/**
 * 数据工厂,这个工厂的业务就是生成各种数据.
 *
 * @author wuda
 */
@Component
public class DataFactory {

    @Autowired
    private DSLContext create;

    private KeyGeneratorSnowflake keyGeneratorSnowflake = new KeyGeneratorSnowflake(1);
    private long adminUserId = 0;
    private long notDeleted = 0;

    private CliArgs cliArgs;

    void setCliArgs(CliArgs cliArgs) {
        this.cliArgs = cliArgs;
    }

    /**
     * 随机生成店铺类型.
     *
     * @return 店铺类型
     */
    private byte genStoreType() {
        return (byte) RandomUtils.nextInt(1, 6);
    }

    /**
     * 随机生成店铺状态
     *
     * @return 店铺状态
     */
    private byte genStoreStatus() {
        return (byte) RandomUtils.nextInt(1, 10);
    }

    /**
     * 随机生成用户类型.
     *
     * @return 用户类型
     */
    private byte genUserType() {
        return (byte) RandomUtils.nextInt(1, 6);
    }

    /**
     * 随机生成用户状态
     *
     * @return 用户状态
     */
    private byte genUserStatus() {
        return (byte) RandomUtils.nextInt(1, 10);
    }

    /**
     * 随机生成物品状态
     *
     * @return 物品状态
     */
    private byte genItemStatus() {
        return (byte) RandomUtils.nextInt(1, 10);
    }

    /**
     * 生成用户.
     *
     * @param count    生成的用户的数量
     * @param appender 用于追加values
     * @return 用户id
     */
    private List<Long> userValues(int count, InsertValuesStep8 appender) {
        if (count < 0) {
            throw new IllegalStateException("生成的用户的数量不能小于0");
        }
        long[] userIds = keyGeneratorSnowflake.next(count);

        for (long userId : userIds) {
            Date now = new Date();
            appender.values(userId, genUserType(), genUserStatus(), now, adminUserId, now, adminUserId, notDeleted);
        }
        return asList(userIds);
    }

    /**
     * arrty to list
     *
     * @param array array
     * @return list
     */
    private List<Long> asList(long[] array) {
        List<Long> list = new ArrayList<>(array.length);
        for (long v : array) {
            list.add(v);
        }
        return list;
    }

    /**
     * 为用户生成基本信息.
     *
     * @param userIds  用户id
     * @param appender 用于追加values
     */
    private void individualUserGeneralValues(List<Long> userIds, InsertValuesStep10 appender) {
        long[] individualUserGeneralIds = keyGeneratorSnowflake.next(userIds.size());
        Date now = new Date();
        for (int i = 0; i < userIds.size(); i++) {
            long userId = userIds.get(i);
            long individualUserGeneralId = individualUserGeneralIds[i];
            appender.values(individualUserGeneralId, userId, RandomUtilsExt.enRandomString(15), RandomUtilsExt.enRandomString(20), RandomUtilsExt.enRandomString(18), now, adminUserId, now, adminUserId, notDeleted);
        }
    }

    /**
     * 为给定的用户生成店铺.
     *
     * @param userId   用户id
     * @param count    生成的店铺的数量
     * @param appender 用于追加values
     * @return 店铺id
     */
    private List<Long> storeValues(long userId, int count, InsertValuesStep9 appender) {
        if (count < 0) {
            throw new IllegalStateException("生成的店铺的数量不能小于0");
        }
        List<Long> storeIds = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            Date now = new Date();
            long storeId = keyGeneratorSnowflake.next();
            storeIds.add(storeId);
            appender.values(storeId, userId, genStoreType(), genStoreStatus(), now, adminUserId, now, adminUserId, notDeleted);
        }
        return storeIds;
    }

    /**
     * 为店铺生成基本信息.
     *
     * @param storeIds 店铺id
     * @param appender 用于追加values
     */
    private void storeGeneralValues(List<Long> storeIds, InsertValuesStep8 appender) {
        for (Long storeId : storeIds) {
            Date now = new Date();
            long storeGeneralId = keyGeneratorSnowflake.next();
            appender.values(storeGeneralId, storeId, RandomUtilsExt.enRandomString(20), now, adminUserId, now, adminUserId, notDeleted);
        }
    }

    /**
     * 生成item.
     *
     * @param storeId  店铺id
     * @param quantity 生成的item的数量
     * @param appender 用于追加values
     * @return item id
     */
    private List<Long> itemValues(long storeId, int quantity, InsertValuesStep8 appender) {
        if (quantity < 0) {
            throw new IllegalStateException("生成的item的数量不能小于0");
        }
        long[] itemIds = keyGeneratorSnowflake.next(quantity);

        Date now = new Date();
        for (long itemId : itemIds) {
            appender.values(itemId, storeId, genItemStatus(), now, adminUserId, now, adminUserId, notDeleted);
        }
        return asList(itemIds);
    }

    /**
     * 为item生成基本信息.
     *
     * @param itemIds  item id
     * @param appender 用于追加values
     */
    private void itemGeneralValues(List<Long> itemIds, InsertValuesStep9 appender) {
        long[] itemGeneralIds = keyGeneratorSnowflake.next(itemIds.size());
        Date now = new Date();
        long itemVariationId = 0;
        for (int i = 0; i < itemIds.size(); i++) {
            long itemId = itemIds.get(i);
            long itemGeneralId = itemGeneralIds[i];
            appender.values(itemGeneralId, itemId, itemVariationId, RandomUtilsExt.enRandomString(20), now, adminUserId, now, adminUserId, notDeleted);
        }
    }

    /**
     * 为item生成描述信息.
     *
     * @param itemIds  item id
     * @param appender 用于追加values
     */
    private void itemDescriptionValues(List<Long> itemIds, InsertValuesStep9 appender) {
        long[] itemDescriptionIds = keyGeneratorSnowflake.next(itemIds.size());
        Date now = new Date();
        long itemVariationId = 0;
        for (int i = 0; i < itemIds.size(); i++) {
            long itemId = itemIds.get(i);
            long itemDescriptionId = itemDescriptionIds[i];
            appender.values(itemDescriptionId, itemId, itemVariationId, RandomUtilsExt.enRandomString(20), now, adminUserId, now, adminUserId, notDeleted);
        }
    }

    /**
     * 获取每个表生成数据的定义,使用持有的{@link org.jooq.InsertReturningStep}就可以执行insert into 语句.
     *
     * @return 为每个表生成数据的定义
     */
    public List<TableInsertion> getInsertions() {
        TableInsertion userInsertion = userInsertion();
        TableInsertion individualUserGeneralInsertion = individualUserGeneralInsertion();
        TableInsertion storeInsertion = storeInsertion();
        TableInsertion storeGeneralInsertion = storeGeneralInsertion();
        TableInsertion itemInsertion = itemInsertion();
        TableInsertion itemGeneralInsertion = itemGeneralInsertion();
        TableInsertion itemDescriptionInsertion = itemDescriptionInsertion();

        List<Long> userIds = userValues(CliArgs.BATCH_SIZE_DEFAULT, (InsertValuesStep8) userInsertion.getInsertReturningStep());
        userInsertion.incValues(userIds.size());

        individualUserGeneralValues(userIds, (InsertValuesStep10) individualUserGeneralInsertion.getInsertReturningStep());
        individualUserGeneralInsertion.incValues(userIds.size());

        for (Long userId : userIds) {
            List<Long> storeIds = storeValues(userId, 1, (InsertValuesStep9) storeInsertion.getInsertReturningStep());
            storeInsertion.incValues(storeIds.size());

            storeGeneralValues(storeIds, (InsertValuesStep8) storeGeneralInsertion.getInsertReturningStep());
            storeGeneralInsertion.incValues(storeIds.size());

            for (Long storeId : storeIds) {
                List<Long> itemIds = itemValues(storeId, cliArgs.getMaxItemPerUser(), (InsertValuesStep8) itemInsertion.getInsertReturningStep());
                itemInsertion.incValues(itemIds.size());

                itemGeneralValues(itemIds, (InsertValuesStep9) itemGeneralInsertion.getInsertReturningStep());
                itemGeneralInsertion.incValues(itemIds.size());

                itemDescriptionValues(itemIds, (InsertValuesStep9) itemDescriptionInsertion.getInsertReturningStep());
                itemDescriptionInsertion.incValues(itemIds.size());
            }
        }


        return Arrays.asList(userInsertion, individualUserGeneralInsertion,
                storeInsertion, storeGeneralInsertion,
                itemInsertion, itemGeneralInsertion, itemDescriptionInsertion);
    }

    private TableInsertion storeInsertion() {
        InsertValuesStep9 insertValuesStep9 =
                create.insertInto(STORE,
                        STORE.STORE_ID,
                        STORE.USER_ID,
                        STORE.TYPE,
                        STORE.STATUS,
                        STORE.CREATE_TIME,
                        STORE.CREATE_USER_ID,
                        STORE.LAST_MODIFY_TIME,
                        STORE.LAST_MODIFY_USER_ID,
                        STORE.IS_DELETED);
        return new TableInsertion(TableName.STORE, insertValuesStep9);
    }

    private TableInsertion storeGeneralInsertion() {
        InsertValuesStep8 insertValuesStep8 =
                create.insertInto(STORE_GENERAL,
                        STORE_GENERAL.STORE_GENERAL_ID,
                        STORE_GENERAL.STORE_ID,
                        STORE_GENERAL.STORE_NAME,
                        STORE_GENERAL.CREATE_TIME,
                        STORE_GENERAL.CREATE_USER_ID,
                        STORE_GENERAL.LAST_MODIFY_TIME,
                        STORE_GENERAL.LAST_MODIFY_USER_ID,
                        STORE_GENERAL.IS_DELETED);
        return new TableInsertion(TableName.STORE_GENERAL, insertValuesStep8);
    }

    private TableInsertion userInsertion() {
        InsertValuesStep8 insertValuesStep8 =
                create.insertInto(USER,
                        USER.USER_ID,
                        USER.TYPE,
                        USER.STATUS,
                        USER.CREATE_TIME,
                        USER.CREATE_USER_ID,
                        USER.LAST_MODIFY_TIME,
                        USER.LAST_MODIFY_USER_ID,
                        USER.IS_DELETED);
        return new TableInsertion(TableName.USER, insertValuesStep8);
    }

    private TableInsertion individualUserGeneralInsertion() {
        InsertValuesStep10 insertValuesStep10 =
                create.insertInto(INDIVIDUAL_USER_GENERAL,
                        INDIVIDUAL_USER_GENERAL.INDIVIDUAL_USER_GENERAL_ID,
                        INDIVIDUAL_USER_GENERAL.USER_ID,
                        INDIVIDUAL_USER_GENERAL.NICKNAME,
                        INDIVIDUAL_USER_GENERAL.BIOGRAPHY,
                        INDIVIDUAL_USER_GENERAL.PICTURE,
                        INDIVIDUAL_USER_GENERAL.CREATE_TIME,
                        INDIVIDUAL_USER_GENERAL.CREATE_USER_ID,
                        INDIVIDUAL_USER_GENERAL.LAST_MODIFY_TIME,
                        INDIVIDUAL_USER_GENERAL.LAST_MODIFY_USER_ID,
                        INDIVIDUAL_USER_GENERAL.IS_DELETED);
        return new TableInsertion(TableName.INDIVIDUAL_USER_GENERAL, insertValuesStep10);
    }

    private TableInsertion itemInsertion() {
        InsertValuesStep8 insertValuesStep8 =
                create.insertInto(ITEM,
                        ITEM.ITEM_ID,
                        ITEM.STORE_ID,
                        ITEM.TYPE,
                        ITEM.CREATE_TIME,
                        ITEM.CREATE_USER_ID,
                        ITEM.LAST_MODIFY_TIME,
                        ITEM.LAST_MODIFY_USER_ID,
                        ITEM.IS_DELETED);
        return new TableInsertion(TableName.ITEM, insertValuesStep8);
    }

    private TableInsertion itemGeneralInsertion() {
        InsertValuesStep9 insertValuesStep =
                create.insertInto(ITEM_GENERAL,
                        ITEM_GENERAL.ITEM_GENERAL_ID,
                        ITEM_GENERAL.ITEM_ID,
                        ITEM_GENERAL.ITEM_VARIATION_ID,
                        ITEM_GENERAL.ITEM_NAME,
                        ITEM_GENERAL.CREATE_TIME,
                        ITEM_GENERAL.CREATE_USER_ID,
                        ITEM_GENERAL.LAST_MODIFY_TIME,
                        ITEM_GENERAL.LAST_MODIFY_USER_ID,
                        ITEM_GENERAL.IS_DELETED);
        return new TableInsertion(TableName.ITEM_GENERAL, insertValuesStep);
    }

    private TableInsertion itemDescriptionInsertion() {
        InsertValuesStep9 insertValuesStep =
                create.insertInto(ITEM_DESCRIPTION,
                        ITEM_DESCRIPTION.ITEM_DESCRIPTION_ID,
                        ITEM_DESCRIPTION.ITEM_ID,
                        ITEM_DESCRIPTION.ITEM_VARIATION_ID,
                        ITEM_DESCRIPTION.CONTENT,
                        ITEM_DESCRIPTION.CREATE_TIME,
                        ITEM_DESCRIPTION.CREATE_USER_ID,
                        ITEM_DESCRIPTION.LAST_MODIFY_TIME,
                        ITEM_DESCRIPTION.LAST_MODIFY_USER_ID,
                        ITEM_DESCRIPTION.IS_DELETED);
        return new TableInsertion(TableName.ITEM_DESCRIPTION, insertValuesStep);
    }

}
