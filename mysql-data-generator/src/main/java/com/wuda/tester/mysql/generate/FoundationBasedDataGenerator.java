package com.wuda.tester.mysql.generate;

import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.commons.impl.EmailManagerImpl;
import com.wuda.foundation.commons.impl.PhoneManagerImpl;
import com.wuda.foundation.commons.impl.PropertyManagerImpl;
import com.wuda.foundation.commons.property.PropertyManager;
import com.wuda.foundation.item.ItemManager;
import com.wuda.foundation.item.impl.ItemManagerImpl;
import com.wuda.foundation.store.StoreManager;
import com.wuda.foundation.store.impl.StoreManagerImpl;
import com.wuda.foundation.user.UserManager;
import com.wuda.foundation.user.impl.UserManagerImpl;
import com.wuda.tester.mysql.TableName;
import com.wuda.tester.mysql.cli.CliArgs;

import javax.sql.DataSource;

/**
 * 基于<a href = "https://github.com/wuda0112/foundation">foundation</a>项目生成数据.
 *
 * @author wuda
 */
public class FoundationBasedDataGenerator extends DataGenerator<FoundationBasedDataSet> {

    private long opUserId = 1L;

    /**
     * 构造实例.
     *
     * @param cliArgs    命令行参数
     * @param dataSource datasource
     */
    public FoundationBasedDataGenerator(CliArgs cliArgs, DataSource dataSource) {
        super(cliArgs, dataSource);
    }

    @Override
    public FoundationBasedDataSet prepareDataSet(CliArgs cliArgs) {
        FoundationBasedDataSet dataSet = new FoundationBasedDataSet();
        dataSet.prepare(cliArgs);
        return dataSet;
    }

    @Override
    public void insert(FoundationBasedDataSet dataSet) {
        generateUserData(dataSet);
        generateStore(dataSet);
        generateItem(dataSet);
        generateProperty(dataSet);
    }

    public void generateUserData(FoundationBasedDataSet dataSet) {
        UserManager userManager = getUserManager();
        EmailManager emailManager = getEmailManager();
        PhoneManager phoneManager = getPhoneManager();

        userManager.directBatchInsertUser(dataSet.getUsers(), opUserId);
        userManager.directBatchInsertUserAccount(dataSet.getUserAccounts(), opUserId);
        emailManager.directBatchInsertEmail(dataSet.getEmails(), opUserId);
        userManager.directBatchBindUserEmail(dataSet.getBindUserEmails(), opUserId);
        phoneManager.directBatchInsertPhone(dataSet.getPhones(), opUserId);
        userManager.directBatchBindUserPhone(dataSet.getBindUserPhones(), opUserId);

        dataGenerateStat.insertedIncrementAndGet(TableName.USER, dataSet.getUsers().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.USER_ACCOUNT, dataSet.getUserAccounts().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.EMAIL, dataSet.getEmails().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.USER_EMAIL, dataSet.getBindUserEmails().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.PHONE, dataSet.getPhones().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.USER_PHONE, dataSet.getBindUserPhones().size());
    }

    public void generateStore(FoundationBasedDataSet dataSet) {
        StoreManager storeManager = getStoreManager();

        storeManager.directBatchInsertStore(dataSet.getStores(), opUserId);
        storeManager.directBatchBindStoreUser(dataSet.getBindStoreUsers(), opUserId);
        storeManager.directBatchInsertStoreGeneral(dataSet.getStoreGenerals(), opUserId);
        dataGenerateStat.insertedIncrementAndGet(TableName.STORE, dataSet.getStores().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.STORE_USER_RELATIONSHIP, dataSet.getBindStoreUsers().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.STORE_GENERAL, dataSet.getStoreGenerals().size());
    }

    public void generateItem(FoundationBasedDataSet dataSet) {
        ItemManager itemManager = getItemManager();

        itemManager.directBatchInsertItem(dataSet.getItems(), opUserId);
        itemManager.directBatchInsertItemGeneral(dataSet.getItemGenerals(), opUserId);
        itemManager.directBatchInsertItemVariation(dataSet.getItemVariations(), opUserId);
        itemManager.directBatchInsertItemDescription(dataSet.getItemDescriptions(), opUserId);

        dataGenerateStat.insertedIncrementAndGet(TableName.ITEM, dataSet.getItems().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.ITEM_GENERAL, dataSet.getItemGenerals().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.ITEM_VARIATION, dataSet.getItemVariations().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.ITEM_DESCRIPTION, dataSet.getItemDescriptions().size());
    }

    public void generateProperty(FoundationBasedDataSet dataSet) {
        PropertyManager propertyManager = getPropertyManager();

        propertyManager.directBatchInsertPropertyKey(dataSet.getPropertyKeys(), opUserId);
        propertyManager.directBatchInsertPropertyValue(dataSet.getPropertyValues(), opUserId);

        dataGenerateStat.insertedIncrementAndGet(TableName.PROPERTY_KEY, dataSet.getPropertyKeys().size());
        dataGenerateStat.insertedIncrementAndGet(TableName.PROPERTY_VALUE, dataSet.getPropertyValues().size());
    }

    private UserManager getUserManager() {
        UserManagerImpl userManager = new UserManagerImpl();
        userManager.setDataSource(dataSource);
        return userManager;
    }

    private EmailManager getEmailManager() {
        EmailManagerImpl emailManager = new EmailManagerImpl();
        emailManager.setDataSource(dataSource);
        return emailManager;
    }

    private PhoneManager getPhoneManager() {
        PhoneManagerImpl phoneManager = new PhoneManagerImpl();
        phoneManager.setDataSource(dataSource);
        return phoneManager;
    }

    private StoreManager getStoreManager() {
        StoreManagerImpl storeManager = new StoreManagerImpl();
        storeManager.setDataSource(dataSource);
        return storeManager;
    }

    private ItemManager getItemManager() {
        ItemManagerImpl storeManager = new ItemManagerImpl();
        storeManager.setDataSource(dataSource);
        return storeManager;
    }

    private PropertyManager getPropertyManager() {
        PropertyManagerImpl propertyManager = new PropertyManagerImpl();
        propertyManager.setDataSource(dataSource);
        return propertyManager;
    }
}
