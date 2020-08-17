package com.wuda.tester.mysql.generate;

import com.wuda.foundation.commons.CreateEmail;
import com.wuda.foundation.commons.CreatePhone;
import com.wuda.foundation.commons.property.CreatePropertyKey;
import com.wuda.foundation.commons.property.CreatePropertyValue;
import com.wuda.foundation.item.CreateItem;
import com.wuda.foundation.item.CreateItemDescription;
import com.wuda.foundation.item.CreateItemGeneral;
import com.wuda.foundation.item.CreateItemVariation;
import com.wuda.foundation.lang.Constant;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.store.BindStoreUser;
import com.wuda.foundation.store.CreateStore;
import com.wuda.foundation.store.CreateStoreGeneral;
import com.wuda.foundation.user.BindUserEmail;
import com.wuda.foundation.user.BindUserPhone;
import com.wuda.foundation.user.CreateUser;
import com.wuda.foundation.user.CreateUserAccount;
import com.wuda.tester.mysql.cli.CliArgs;
import com.wuda.tester.mysql.commons.utils.RandomUtilsExt;
import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成<a href = "https://github.com/wuda0112/foundation">foundation</a>项目需要的数据..
 *
 * @author wuda
 */
@Getter
public class FoundationBasedDataSet implements DataSet {

    private int batchSize = 500;
    private byte byte0 = (byte) 0;

    private List<CreateUser> users;
    private List<CreateUserAccount> userAccounts;
    private List<CreateEmail> emails;
    private List<BindUserEmail> bindUserEmails;
    private List<CreatePhone> phones;
    private List<BindUserPhone> bindUserPhones;
    private List<CreateStore> stores;
    private List<BindStoreUser> bindStoreUsers;
    private List<CreateStoreGeneral> storeGenerals;
    private List<CreateItem> items;
    private List<CreateItemGeneral> itemGenerals;
    private List<CreateItemVariation> itemVariations;
    private List<CreateItemDescription> itemDescriptions;
    private List<CreatePropertyKey> propertyKeys;
    private List<CreatePropertyValue> propertyValues;

    @Override
    public void prepare(CliArgs cliArgs) {
        users = newCreateUserList();
        userAccounts = newCreateUserAccountList(users);
        ImmutablePair<List<CreateEmail>, List<BindUserEmail>> userEmail = newUserEmail(users);
        emails = userEmail.left;
        bindUserEmails = userEmail.right;
        ImmutablePair<List<CreatePhone>, List<BindUserPhone>> userPhone = newUserPhone(users);
        phones = userPhone.left;
        bindUserPhones = userPhone.right;
        ImmutablePair<List<CreateStore>, List<BindStoreUser>> userStore = newUserStore(users);
        stores = userStore.left;
        bindStoreUsers = userStore.right;
        storeGenerals = newCreateStoreGeneralList(stores);
        items = newItemList(stores, cliArgs.getMaxItemPerUser());
        itemGenerals = newItemGeneralList(items);
        itemVariations = newItemVariationList(items);
        itemDescriptions = newItemDescriptionList(items);
        propertyKeys = newPropertyKeyList(items);
        propertyValues = newPropertyValueList(propertyKeys);
    }

    private List<CreateUser> newCreateUserList() {
        List<CreateUser> list = new ArrayList<>(batchSize);
        Long[] ids = FoundationContext.getLongKeyGenerator().next(batchSize);
        for (int i = 0; i < ids.length; i++) {
            long id = ids[i];
            CreateUser user = new CreateUser.Builder()
                    .setId(id)
                    .setUserType(byte0)
                    .setUserState(byte0)
                    .build();
            list.add(user);
        }
        return list;
    }

    private List<CreateUserAccount> newCreateUserAccountList(List<CreateUser> users) {
        List<CreateUserAccount> list = new ArrayList<>(users.size());
        Long[] ids = FoundationContext.getLongKeyGenerator().next(users.size());
        for (int i = 0; i < users.size(); i++) {
            CreateUser user = users.get(i);
            long id = ids[i];
            CreateUserAccount userAccount = new CreateUserAccount.Builder()
                    .setId(id)
                    .setUserId(user.getId())
                    .setPassword(RandomUtilsExt.enRandomString(10))
                    .setUsername(RandomUtilsExt.enRandomString(9))
                    .setState(byte0)
                    .build();
            list.add(userAccount);
        }
        return list;
    }

    private ImmutablePair<List<CreateEmail>, List<BindUserEmail>> newUserEmail(List<CreateUser> users) {
        List<CreateEmail> emailList = new ArrayList<>(users.size());
        List<BindUserEmail> bindingList = new ArrayList<>(users.size());
        Long[] emailIds = FoundationContext.getLongKeyGenerator().next(users.size());
        Long[] bindingIds = FoundationContext.getLongKeyGenerator().next(users.size());
        for (int i = 0; i < users.size(); i++) {
            CreateUser user = users.get(i);
            long emailId = emailIds[i];
            CreateEmail email = new CreateEmail.Builder()
                    .setId(emailId)
                    .setAddress(RandomUtilsExt.enRandomString(10) + RandomUtilsExt.randomEmailSuffix())
                    .setEmailState(byte0)
                    .build();

            long bindingId = bindingIds[i];
            BindUserEmail bindUserEmail = new BindUserEmail.Builder()
                    .setId(bindingId)
                    .setEmailId(emailId)
                    .setUserId(user.getId())
                    .setUse(byte0)
                    .setState(byte0)
                    .build();
            emailList.add(email);
            bindingList.add(bindUserEmail);
        }
        return new ImmutablePair<>(emailList, bindingList);
    }

    private ImmutablePair<List<CreatePhone>, List<BindUserPhone>> newUserPhone(List<CreateUser> users) {
        List<CreatePhone> phoneList = new ArrayList<>(users.size());
        List<BindUserPhone> bindingList = new ArrayList<>(users.size());
        Long[] phoneIds = FoundationContext.getLongKeyGenerator().next(users.size());
        Long[] bindingIds = FoundationContext.getLongKeyGenerator().next(users.size());
        for (int i = 0; i < users.size(); i++) {
            CreateUser user = users.get(i);
            long phoneId = phoneIds[i];
            CreatePhone phone = new CreatePhone.Builder()
                    .setId(phoneId)
                    .setNumber(RandomUtilsExt.enRandomString(11))
                    .setPhoneType(byte0)
                    .setPhoneState(byte0)
                    .build();

            long bindingId = bindingIds[i];
            BindUserPhone bindUserPhone = new BindUserPhone.Builder()
                    .setId(bindingId)
                    .setPhoneId(phoneId)
                    .setUserId(user.getId())
                    .setUse(byte0)
                    .setState(byte0)
                    .build();
            phoneList.add(phone);
            bindingList.add(bindUserPhone);
        }
        return new ImmutablePair<>(phoneList, bindingList);
    }

    private ImmutablePair<List<CreateStore>, List<BindStoreUser>> newUserStore(List<CreateUser> users) {
        List<CreateStore> storeList = new ArrayList<>(users.size());
        List<BindStoreUser> bindingList = new ArrayList<>(users.size());
        Long[] storeIds = FoundationContext.getLongKeyGenerator().next(users.size());
        Long[] bindingIds = FoundationContext.getLongKeyGenerator().next(users.size());
        for (int i = 0; i < users.size(); i++) {
            CreateUser user = users.get(i);
            long storeId = storeIds[i];
            CreateStore store = new CreateStore.Builder()
                    .setId(storeId)
                    .setStoreType(byte0)
                    .setStoreState(byte0)
                    .build();

            long bindingId = bindingIds[i];
            BindStoreUser bindStoreUser = new BindStoreUser.Builder()
                    .setId(bindingId)
                    .setUserId(user.getId())
                    .setStoreId(storeId)
                    .isStoreOwner(true)
                    .build();
            storeList.add(store);
            bindingList.add(bindStoreUser);
        }
        return new ImmutablePair<>(storeList, bindingList);
    }

    private List<CreateStoreGeneral> newCreateStoreGeneralList(List<CreateStore> stores) {
        List<CreateStoreGeneral> list = new ArrayList<>(stores.size());
        Long[] ids = FoundationContext.getLongKeyGenerator().next(stores.size());
        for (int i = 0; i < stores.size(); i++) {
            CreateStore store = stores.get(i);
            long id = ids[i];
            CreateStoreGeneral storeGeneral = new CreateStoreGeneral.Builder()
                    .setId(id)
                    .setStoreId(store.getId())
                    .setStoreName(RandomUtilsExt.enRandomString(5))
                    .build();
            list.add(storeGeneral);
        }
        return list;
    }

    private List<CreateItem> newItemList(List<CreateStore> stores, int perStore) {
        List<CreateItem> list = new ArrayList<>(stores.size());
        for (int i = 0; i < stores.size(); i++) {
            CreateStore store = stores.get(i);
            int actualPerStore = RandomUtils.nextInt(1, perStore);
            Long[] ids = FoundationContext.getLongKeyGenerator().next(actualPerStore);
            for (int size = 0; size < actualPerStore; size++) { // 为每个店铺生成不同数量的item
                long id = ids[size];
                CreateItem item = new CreateItem.Builder()
                        .setId(id)
                        .setStoreId(store.getId())
                        .setCategoryId(1L)
                        .setItemState(byte0)
                        .setItemType(byte0)
                        .build();
                list.add(item);
            }
        }
        return list;
    }

    private List<CreateItemGeneral> newItemGeneralList(List<CreateItem> items) {
        List<CreateItemGeneral> list = new ArrayList<>(items.size());
        Long[] ids = FoundationContext.getLongKeyGenerator().next(items.size());
        for (int i = 0; i < items.size(); i++) {
            CreateItem item = items.get(i);
            long id = ids[i];
            CreateItemGeneral itemGeneral = new CreateItemGeneral.Builder()
                    .setId(id)
                    .setItemId(item.getId())
                    .setName(RandomUtilsExt.enRandomString(8))
                    .build();
            list.add(itemGeneral);
        }
        return list;
    }

    private List<CreateItemVariation> newItemVariationList(List<CreateItem> items) {
        List<CreateItemVariation> list = new ArrayList<>(items.size());
        Long[] ids = FoundationContext.getLongKeyGenerator().next(items.size());
        for (int i = 0; i < items.size(); i++) {
            CreateItem item = items.get(i);
            long id = ids[i];
            CreateItemVariation itemVariation = new CreateItemVariation.Builder()
                    .setId(id)
                    .setItemId(item.getId())
                    .setName(RandomUtilsExt.enRandomString(8))
                    .setState(byte0)
                    .build();
            list.add(itemVariation);
        }
        return list;
    }

    private List<CreateItemDescription> newItemDescriptionList(List<CreateItem> items) {
        List<CreateItemDescription> list = new ArrayList<>(items.size());
        Long[] ids = FoundationContext.getLongKeyGenerator().next(items.size());
        for (int i = 0; i < items.size(); i++) {
            CreateItem item = items.get(i);
            long id = ids[i];
            CreateItemDescription itemDescription = new CreateItemDescription.Builder()
                    .setId(id)
                    .setItemId(item.getId())
                    .setItemVariationId(Constant.NOT_EXISTS_ID)
                    .setContent(RandomUtilsExt.enRandomString(8))
                    .build();
            list.add(itemDescription);
        }
        return list;
    }

    private List<CreatePropertyKey> newPropertyKeyList(List<CreateItem> items) {
        List<CreatePropertyKey> list = new ArrayList<>(items.size());
        Long[] ids = FoundationContext.getLongKeyGenerator().next(items.size());
        for (int i = 0; i < items.size(); i++) {
            CreateItem item = items.get(i);
            long id = ids[i];
            CreatePropertyKey createPropertyKey = new CreatePropertyKey.Builder()
                    .setId(id)
                    .setOwner(new LongIdentifier(item.getId(), BuiltinIdentifierType.TABLE_ITEM))
                    .setType(byte0)
                    .setUse(byte0)
                    .setKey(RandomUtilsExt.enRandomString(6))
                    .build();
            list.add(createPropertyKey);
        }
        return list;
    }

    private List<CreatePropertyValue> newPropertyValueList(List<CreatePropertyKey> propertyKeys) {
        List<CreatePropertyValue> list = new ArrayList<>(propertyKeys.size());
        Long[] ids = FoundationContext.getLongKeyGenerator().next(propertyKeys.size());
        for (int i = 0; i < propertyKeys.size(); i++) {
            CreatePropertyKey propertyKey = propertyKeys.get(i);
            long id = ids[i];
            CreatePropertyValue createPropertyValue = new CreatePropertyValue.Builder()
                    .setId(id)
                    .setPropertyKeyId(propertyKey.getId())
                    .setValue(RandomUtilsExt.enRandomString(8))
                    .build();
            list.add(createPropertyValue);
        }
        return list;
    }
}
