package com.systeminfos.shardingJdbc.mapper;

import com.systeminfos.shardingJdbc.model.UserInfo;
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