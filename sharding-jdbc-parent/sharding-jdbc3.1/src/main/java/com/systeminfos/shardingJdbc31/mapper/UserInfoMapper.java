package com.systeminfos.shardingJdbc31.mapper;

import com.systeminfos.shardingJdbc31.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}