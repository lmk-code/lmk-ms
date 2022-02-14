package com.lmk.ms.common.mvc.dao;

import java.util.List;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmk.ms.common.mvc.entity.PkEntity;

/**
 * 基础的数据访问接口
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
public interface BaseDao<T extends PkEntity<PK>, PK extends Serializable> extends BaseMapper<T> {

    /**
     * 根据ID查询唯一对象
     * @param id
     * @return
     */
    T selectUnique(PK id);

    /**
     * 更新对象
     * @param entity
     */
    int updateWithId(T entity);

    /**
     * 更新状态
     * @param id
     * @param status
     */
    int updateStatus(@Param("id") PK id, @Param("status") int status);

    /**
     * 更新删除状态
     * @param id
     * @param delFlag
     */
    int updateDelFlag(@Param("id") PK id, @Param("delFlag") int delFlag);

    /**
     * 分页查询
     * @param search    查询条件（拼接成字符串）
     * @param sort      排序条件（拼接成字符串）
     * @return
     */
    <P extends IPage<T>> P pageList(P page, @Param("search") String search, @Param("sort") String sort);

    /**
     * 列表查询
     * @param search    查询条件（拼接成字符串）
     * @param sort      排序条件（拼接成字符串）
     * @return
     */
    List<T> loadList(@Param("search") String search, @Param("sort") String sort);

    /**
     * 按查询条件统计
     * @author LaoMake
     * @since 1.0
     * @param search
     * @return
     */
    Integer count(String search);
}
