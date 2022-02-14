package com.lmk.ms.common.mvc.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmk.ms.common.api.StatusEnum;
import com.lmk.ms.common.db.QueryParams;
import com.lmk.ms.common.db.Search;
import com.lmk.ms.common.db.Sort;
import com.lmk.ms.common.db.PageResult;
import com.lmk.ms.common.mvc.dao.BaseDao;
import com.lmk.ms.common.mvc.entity.PkEntity;
import com.lmk.ms.common.mvc.dto.Meta;
import com.lmk.ms.common.mvc.service.BaseService;
import com.lmk.ms.common.mvc.dto.ServiceResult;
import com.lmk.ms.common.utils.BeanUtils;

/**
 * 基础服务的实现
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@Slf4j
public abstract class BaseServiceImpl<D extends BaseDao<T, PK>, T extends PkEntity<PK>, PK extends Serializable> implements BaseService<T, PK> {

    @Autowired
    protected D dao;

    public D getBaseMapper() {
        return dao;
    }

    /** DAO接口类型 */
    protected Class<D> daoClass = currentMapperClass();

    /** 实体类的类型 */
    protected Class<T> entityClass = currentEntityClass();

    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public Meta loadMetaInfo() {
        return new Meta();
    }

    /** 获取DAO接口类型 */
    protected Class<D> currentMapperClass() {
        return BeanUtils.getSuperClassGenerics(this.getClass(), 0);
    }

    /** 获取实体类的类型 */
    protected Class<T> currentEntityClass() {
        return BeanUtils.getSuperClassGenerics(this.getClass(), 1);
    }

    @Override
    public T getById(PK id) {
        return dao.selectUnique(id);
    }

    @Override
    public T getById(PK id, String[] columns) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.select(columns);
        return dao.selectOne(wrapper);
    }

    @Override
    public PageResult<T> list(QueryParams queryParams, List<Search> searchList, List<Sort> sortList) {
        String search = Search.build(searchList);
        String sort = Sort.build(sortList);

        Page<T> page = new Page<>(queryParams.getPage(), queryParams.getSize());
        dao.pageList(page, search, sort);

        return new PageResult<>(page);
    }

    @Override
    public PageResult<T> pageList(QueryParams queryParams, List<Search> searchList, List<Sort> sortList) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for (Search search : searchList) {
            switch (search.getOperator()) {
                case eq:
                    wrapper.eq(search.getName(), search.getValue());
                    break;
                case notEq:
                    wrapper.ne(search.getName(), search.getValue());
                    break;
                case like:
                    wrapper.like(search.getName(), search.getValue());
                    break;
                case gt:
                    wrapper.gt(search.getName(), search.getValue());
                    break;
                case lt:
                    wrapper.lt(search.getName(), search.getValue());
                    break;
            }
        }

        // 排序参数
        for (Sort sort : sortList) {
            if ("ASC".equals(sort.getType())) {
                wrapper.orderByAsc(sort.getColumn());
            } else {
                wrapper.orderByDesc(sort.getColumn());
            }
        }

        // 设置查询字段
        wrapper.select(queryParams.getColumns());

        Page<T> page = new Page<>(queryParams.getPage(), queryParams.getSize());
        dao.selectPage(page, wrapper);

        return new PageResult<>(page);
    }

    @Override
    public ServiceResult create(T entity) {
        dao.insert(entity);
        return new ServiceResult(StatusEnum.SUCCESS);
    }

    @Override
    public ServiceResult update(T entity) {
        dao.updateWithId(entity);
        return new ServiceResult(StatusEnum.SUCCESS);
    }

    @Override
    public ServiceResult deleteById(PK id) {
        dao.updateDelFlag(id, 1);
        return new ServiceResult(StatusEnum.SUCCESS);
    }

    @Override
    public ServiceResult deleteByIds(Collection<PK> ids) {
        ids.forEach(this::deleteById);
        return new ServiceResult(StatusEnum.SUCCESS);
    }
}
