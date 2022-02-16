package com.lmk.ms.common.mvc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lmk.ms.common.api.StatusEnum;
import com.lmk.ms.common.api.ResponseResult;
import com.lmk.ms.common.db.QueryParams;
import com.lmk.ms.common.db.PageResult;
import com.lmk.ms.common.db.Search;
import com.lmk.ms.common.db.Sort;
import com.lmk.ms.common.exception.BizException;
import com.lmk.ms.common.mvc.dto.ServiceResult;
import com.lmk.ms.common.mvc.dto.PageData;
import com.lmk.ms.common.mvc.entity.PkEntity;
import com.lmk.ms.common.mvc.service.BaseService;
import com.lmk.ms.common.utils.BeanUtils;
import com.lmk.ms.common.utils.JsonUtils;
import com.lmk.ms.common.utils.encrypt.TextEncrypt;

/**
 * 基类控制器
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@Slf4j
public abstract class BaseController<S extends BaseService<T, PK>, T extends PkEntity<PK>, PK extends Serializable> {

    @Autowired
    protected S baseService;

    /** 实体类的类型 */
    protected Class<T> entityType;

    /** 服务接口的类型 */
    protected Class<S> serviceClass = currentServiceClass();

    /** 实体类的类型 */
    protected Class<T> entityClass = currentEntityClass();

    /**
     * 服务接口的类型
     * @return
     */
    protected Class<S> currentServiceClass() {
        return BeanUtils.getSuperClassGenerics(this.getClass(), 0);
    }

    /** 获取实体类的类型 */
    protected Class<T> currentEntityClass() {
        return BeanUtils.getSuperClassGenerics(this.getClass(), 1);
    }

    /**
     * 根据ID获取单个对象
     * @param id       ID，注意路径变量只能通过正则表达式校验
     *                 这里配置了不以0开头的数字
     * @return
     */
    @GetMapping("{id:[1-9]\\d*}")
    public ResponseResult<T> findById(@PathVariable("id") PK id, HttpServletRequest request){
        return ResponseResult.success(baseService.getById(id));
    }

    /**
     * 分页查询
     * @param queryParams    参数
     * @param request
     * @return
     */
    @GetMapping("")
    public ResponseResult<PageData<T>> loadList(@Validated QueryParams queryParams, HttpServletRequest request) {

        // 解析查询条件
        List<Search> searchList = parseSearch(queryParams.getSearch());
        List<Sort> sortList = parseSort(queryParams.getSort());

        // 设置查询字段
        String[] columns = queryParams.getColumns();
        if(columns == null || columns.length == 0){
            queryParams.setColumns(new String[]{"*"});
        }

        // 调用子类重写的回调函数
        beforeList(queryParams, searchList, sortList, request);

        // 执行查询
        PageResult<T> page = baseService.list(queryParams, searchList, sortList);

        // 调用子类重写后的回调函数
        afterList(queryParams, page, request);

        PageData<T> pd = new PageData<>(baseService.loadMetaInfo(), page);
        return ResponseResult.success(pd);
    }

    @GetMapping("page")
    public PageResult<T> pageList(@Validated QueryParams queryParams, HttpServletRequest request) {

        // 解析查询条件
        List<Search> searchList = parseSearch(queryParams.getSearch());
        List<Sort> sortList = parseSort(queryParams.getSort());

        // 设置查询字段
        String[] columns = queryParams.getColumns();
        if(columns == null || columns.length == 0){
            queryParams.setColumns(new String[]{"*"});
        }

        // 调用子类重写的回调函数
        beforeList(queryParams, searchList, sortList, request);

        // 执行查询
        PageResult<T> page = baseService.pageList(queryParams, searchList, sortList);

        // 调用子类重写后的回调函数
        afterList(queryParams, page, request);

        return page;
    }

    /**
     * 创建对象
     * @param entity
     * @param request
     * @return
     */
    @PostMapping("")
    public ResponseResult<PK> create(@RequestBody @Validated T entity, HttpServletRequest request){
        ResponseResult<PK> result = null;

        // 调用子类重写的回调函数，可以补充部分参数，或进行数据校验
        StatusEnum status = beforeCreate(entity, request);
        if(status != StatusEnum.SUCCESS){
            return ResponseResult.error(status);
        }

        ServiceResult sr = baseService.create(entity);

        // 调用子类重写的回调函数
        afterCreate(entity, sr, request);

        status = sr.getStatus();
        if(status != StatusEnum.SUCCESS){
            result = ResponseResult.error(status);
        }

        return ResponseResult.success(entity.getId());
    }

    /**
     * 更新对象
     * @param entity
     * @param request
     * @return
     */
    @PutMapping("")
    public ResponseResult<?> update(@RequestBody @Validated T entity, HttpServletRequest request){
        // 校验主键ID是否有效
        PK id = entity.getId();
        if(id == null){
            BizException.throw200(StatusEnum.BAD_REQUEST_ParameterNotValid, "id不可以为空");
        }

        T dbEntity = baseService.getById(id);
        if(dbEntity == null){
            BizException.throw200(StatusEnum.BAD_REQUEST, "没有找到需要更新的对象");
        }

        // 调用子类重写的回调函数，可以补充部分参数，或进行数据校验
        StatusEnum status = beforeUpdate(entity, dbEntity, request);
        if(status != StatusEnum.SUCCESS){
            return ResponseResult.error(status);
        }
        ServiceResult sr = baseService.update(entity);
        // 调用子类重写的回调函数
        afterUpdate(entity, dbEntity, sr, request);

        status = sr.getStatus();
        return ResponseResult.status(status);
    }

    /**
     * 根据ID列表删除
     * @param id       ID，注意路径变量只能通过正则表达式校验
     *                 这里配置了不以0开头的数字
     * @param request
     * @return
     */
    @DeleteMapping("{id:[1-9]\\d*}")
    public ResponseResult<?> delete(@PathVariable("id") PK id, HttpServletRequest request){
        // 校验主键ID是否有效
        if(id == null){
            BizException.throw200(StatusEnum.BAD_REQUEST_ParameterNotValid, "id不可以为空");
        }

        T dbEntity = baseService.getById(id);
        if(dbEntity == null){
            BizException.throw200(StatusEnum.BAD_REQUEST, "没有找到需要删除的对象");
        }

        // 调用子类重写的回调函数，可以补充部分参数，或进行数据校验
        StatusEnum status = beforeDelete(dbEntity, request);
        if(status != StatusEnum.SUCCESS){
            return ResponseResult.error(status);
        }

        ServiceResult sr = baseService.deleteById(id);
        // 调用子类重写的回调函数
        afterDelete(dbEntity, sr, request);

        status = sr.getStatus();
        return ResponseResult.status(status);
    }

    /**
     * 回调函数，在执行列表查询之前调用，可以修改过滤条件及排序条件
     * @param queryParams        原始参数，包含分页、查询、排序等参数
     * @param searchList    解析后的查询条件列表
     * @param sortList      解析后的排序条件列表
     * @param request
     */
    protected void beforeList(QueryParams queryParams, List<Search> searchList, List<Sort> sortList, HttpServletRequest request) {
    }

    /**
     * 回调函数，在执行列表查询之后调用
     * @param queryParams
     * @param page
     * @param request
     */
    protected void afterList(QueryParams queryParams, PageResult<T> page, HttpServletRequest request) {
    }

    /**
     * 回调函数：在保存新对象之前调用，可以继续设置entity属性
     * @param entity
     * @param request
     * @return 校验码，仅StatusCode.Success为校验通过
     */
    protected StatusEnum beforeCreate(T entity, HttpServletRequest request) {
        return StatusEnum.SUCCESS;
    }

    /**
     * 回调函数：在保存新对象之后调用
     * @param entity
     * @param sc
     * @param request
     */
    protected void afterCreate(T entity, ServiceResult sc, HttpServletRequest request) {
    }

    /**
     * 回调函数：在更新对象之前调用，可以继续设置entity属性
     * @param entity
     * @param dbEntity
     * @param request
     * @return
     */
    protected StatusEnum beforeUpdate(T entity, T dbEntity, HttpServletRequest request) {
        return StatusEnum.SUCCESS;
    }

    /**
     * 回调函数：在更新对象之后调用
     * @param entity
     * @param dbEntity
     * @param sc
     * @param request
     */
    protected void afterUpdate(T entity, T dbEntity, ServiceResult sc, HttpServletRequest request) {
    }

    /**
     * 回调函数：在删除对象之前调用，可以校验用户权限
     * @param dbEntity
     * @param request
     * @return 校验码，仅StatusCode.Success为校验通过
     */
    protected StatusEnum beforeDelete(T dbEntity, HttpServletRequest request) {
        return StatusEnum.SUCCESS;
    }

    /**
     * 回调函数：在删除对象之后调用
     * @param dbEntity
     * @param sc
     * @param request
     */
    protected void afterDelete(T dbEntity, ServiceResult sc, HttpServletRequest request) {
    }

    /**
     * 解析查询参数，指定表名
     * @param search
     */
    protected List<Search> parseSearch(String search) {
        List<Search> searchList = null;
        if (StringUtils.isNotBlank(search)) {
            search = TextEncrypt.fromBase64(search);
            return searchList = JsonUtils.parseList(search, Search.class);
        }
        return new ArrayList<>();
    }

    /**
     * 解析排序条件
     * @param sortStr
     * @return
     */
    protected List<Sort> parseSort(String sortStr) {
        List<Sort> sortList = new ArrayList<>();;
        if (StringUtils.isNotBlank(sortStr)) {
            // 如：id_ASC 或 id_DESC__age_ASC等 */
            String[] items = sortStr.split("__");
            for (String item : items){
                if(StringUtils.isNotBlank(item)){
                    Sort sort = new Sort();
                    int index = item.lastIndexOf("_");
                    sort.setColumn(item.substring(0, index));
                    sort.setType(item.substring(index + 1));
                    sortList.add(sort);
                }
            }
        }
        return sortList;
    }
}