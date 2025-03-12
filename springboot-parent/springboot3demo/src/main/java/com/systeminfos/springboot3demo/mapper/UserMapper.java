package com.systeminfos.springboot3demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.systeminfos.springboot3demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangqi
 * @version 1.0
 * @desc Mapper
 * @date 2024-12-12 11:24:24
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 批量插入数据集
     *
     * @param param
     * @return Boolean
     */
    Boolean saveBatch(@Param("paramList") List<User> param);

}
