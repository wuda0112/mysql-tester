package com.wuda.tester.mysql.orm;

import com.wuda.yhan.code.generator.lang.TableEntity;
import com.wuda.yhan.code.generator.lang.TableEntityUtils;

import java.util.List;

/**
 * 每一个表对应了一个唯一的{@link TableEntity}类,通过{@link TableEntity}实例之间调用setter方法(一般都是外键对应的属性的setter方法),
 * 就可以建立起对应的主外键关系.
 *
 * @author wuda
 */
public interface ObjectRelationalMapper {

    /**
     * 返回的{@link TableEntity}s每次必须都是不同实例,即每次都必须是通过<code>new</code>关键字生成的实例,然后它们之间必须已经建立了关系,
     * 最后调用者在同一个事物中把它们保存到数据库.调用者会不断的调用这个方法,导致的结果就是每调用一次这个方法,就表示新生成了一批数据,
     * 然后有多少个不同类型的{@link TableEntity}就表示有对应个数的表将插入新的记录.
     * 比如数据库中有用户表<b>user</b>和订单表<b>order</b>,则它们对应的{@link TableEntity}分别是<b>User</b>
     * 和<b>Order</b>,现在我们要生成用户数据,并且每个用户生成多个订单,则此方法体的伪代码如下:
     *
     * <pre>
     * User user = new User();
     * user.setId(randomId);
     *
     * Order orderA = new Order();
     * orderA.setId(randomId);
     * orderA.setUserId(user.getId()); // 重点,通过这样就建立了用户和订单表的关系
     *
     * Order orderB = new Order();
     * orderB.setId(randomId);
     * orderB.setUserId(user.getId());
     *
     * List<TableEntity> list = new ArrayList<>(3);
     * list.add(user);
     * list.add(orderA);
     * list.add(orderB);
     * return List;
     * </pre>
     * <p>
     * 在上面例子中,调用者每调用一次该方法,都将生成三条记录,其中User表一条,Order表两条,把这三条记录保存到数据库后,它们之间自然就有了关联关系.
     * 可以使用{@link TableEntityUtils#genRandomValueInstance(Class)}生成具有随机属性值的实例.
     *
     * @return entities
     */
    List<TableEntity> insertTransaction();

}
