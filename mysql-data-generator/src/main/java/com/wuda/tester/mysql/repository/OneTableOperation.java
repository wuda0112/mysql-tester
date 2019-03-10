package com.wuda.tester.mysql.repository;

import com.wuda.tester.mysql.SpringApplicationContextHolder;
import com.wuda.tester.mysql.generate.DataGenerator;
import com.wuda.yhan.code.generator.lang.Constant;
import com.wuda.yhan.code.generator.lang.TableEntity;
import com.wuda.yhan.code.generator.lang.TableEntityUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 表示对单一一个表的操作.不需要指定表名,
 * 因为根据约定,该类中的mybatis mapper
 * 就已经代表了操作哪一个表.
 *
 * @author wuda
 */
@AllArgsConstructor
public class OneTableOperation {

    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(DataGenerator.class);

    /**
     * 表对应的mybatis mapper.
     */
    private Object mybatisMapper;
    /**
     * insert 方法.
     */
    private Method insertMethod;
    /**
     * list of entity.
     */
    private List<TableEntity> tableEntities;

    /**
     * 执行insert方法.
     *
     * @return 数据库影响的行数
     */
    public int insert() {
        int count;
        if (insertMethod.getName().equals(Constant.MAPPER_INSERT)) {
            singleInsert();
            count = 1;
        } else {
            batchInsert();
            count = tableEntities.size();
        }
        return count;
    }

    /**
     * 单个插入.
     */
    private void singleInsert() {
        for (TableEntity entity : tableEntities) {
            try {
                insertMethod.invoke(mybatisMapper, entity);
            } catch (Exception e) {
                onException(e);
            }
        }
    }

    /**
     * 批量插入.
     */
    private void batchInsert() {
        try {
            insertMethod.invoke(mybatisMapper, tableEntities);
        } catch (Exception e) {
            onException(e);
        }
    }

    /**
     * 当发生异常时.
     *
     * @param e exception
     */
    private void onException(Exception e) {
        String message = "parameter=" + tableEntities + ",mapper=" + mybatisMapper + ",调用" + insertMethod + "方法异常!";
        logger.warn(message);
        throw new RuntimeException(message, e);
    }

    /**
     * 将相同类型的{@link TableEntity}合并,这样每种类型的{@link TableEntity}
     * 就只需要查找一次对应的<i>Mybatis Mapper</i>类以及<i>Mapper</i>类的<i>insert</i>
     * 方法,减少了重复反射查找次数.
     *
     * @param entities {@link TableEntity}s
     * @return {@link OneTableOperation} list
     */
    public static List<OneTableOperation> merge(List<TableEntity> entities) {
        Map<Class<? extends TableEntity>, List<TableEntity>> map = entities.stream()
                .collect(Collectors.groupingBy(TableEntity::getClass));
        Set<Map.Entry<Class<? extends TableEntity>, List<TableEntity>>> entrySet = map.entrySet();
        Object mybatisMapper;
        String insertMethodName;
        Method insertMethod;
        Class<? extends TableEntity> entityClazz;
        List<OneTableOperation> mappers = new ArrayList<>(entrySet.size());
        for (Map.Entry<Class<? extends TableEntity>, List<TableEntity>> entry : entrySet) {
            entityClazz = entry.getKey();
            mybatisMapper = getMybatisMapperFromContext(entityClazz);
            List<TableEntity> tableEntities = entry.getValue();
            if (tableEntities == null || tableEntities.isEmpty()) {
                continue;
            } else if (tableEntities.size() == 1) {
                insertMethodName = Constant.MAPPER_INSERT;
                insertMethod = BeanUtils.findMethod(mybatisMapper.getClass(), insertMethodName, entityClazz);
            } else {
                insertMethodName = Constant.MAPPER_BATCH_INSERT;
                insertMethod = BeanUtils.findMethod(mybatisMapper.getClass(), insertMethodName, List.class);
            }
            logger.debug("entity class={},mapper={},insert method={}", entityClazz.getSimpleName(), mybatisMapper.toString(), insertMethodName);
            OneTableOperation oneTableOperation = new OneTableOperation(mybatisMapper, insertMethod, tableEntities);
            mappers.add(oneTableOperation);
        }
        return mappers;
    }

    /**
     * 根据给定的entity找到对应的mapper实例.
     *
     * @param entityClazz 表对应的entity
     * @return entity对应的mapper
     */
    private static Object getMybatisMapperFromContext(Class<? extends TableEntity> entityClazz) {
        Class<?> mapperClazz = TableEntityUtils.getMybatisMapper(entityClazz);
        try {
            return SpringApplicationContextHolder.getApplicationContext().getBean(mapperClazz);
        } catch (Exception e) {
            String message = "entity类找不到对应的mapper!entityClazz=" + entityClazz;
            logger.warn(message, e);
            throw new IllegalStateException(message);
        }
    }
}
