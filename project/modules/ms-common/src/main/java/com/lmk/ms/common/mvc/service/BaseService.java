package com.lmk.ms.common.mvc.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import com.lmk.ms.common.db.PageResult;
import com.lmk.ms.common.db.QueryParams;
import com.lmk.ms.common.db.Search;
import com.lmk.ms.common.db.Sort;
import com.lmk.ms.common.mvc.dto.Meta;
import com.lmk.ms.common.mvc.dto.ServiceResult;
import com.lmk.ms.common.mvc.entity.PkEntity;

/**
 * 基础的服务接口
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
public interface BaseService<T extends PkEntity<PK>, PK extends Serializable> {

    /**
     * 加载模块元素数据
     * @return
     */
    Meta loadMetaInfo();

    /**
     * 根据ID获取实体对象
     * @param id
     * @return
     */
    T getById(PK id);

    /**
     * 根据ID获取实体对象
     * @param id
     * @param columns   字段列表
     * @return
     */
    T getById(PK id, String[] columns);

    /**
     * 传统的分页查询
     * @param queryParams
     * @param searchList
     * @param sortList
     * @return
     */
    PageResult<T> list(QueryParams queryParams, List<Search> searchList, List<Sort> sortList);

    /**
     * 使用MyBatisPlus的分页查询
     * @param queryParams        分页参数
     * @param searchList        查询条件
     * @param sortList          排序条件
     * @return
     */
    PageResult<T> pageList(QueryParams queryParams, List<Search> searchList, List<Sort> sortList);

    /**
     * 创建对象
     * @param entity
     * @return
     */
    ServiceResult create(T entity);

    /**
     * 更新对象
     * @param entity
     * @return
     */
    ServiceResult update(T entity);

    /**
     * 根据ID删除，默认为伪删除。
     * @param id
     * @return
     */
    ServiceResult deleteById(PK id);

    /**
     * 根据ID列表删除，默认为伪删除。
     * @param ids
     * @return
     */
    ServiceResult deleteByIds(Collection<PK> ids);

}
