package com.systeminfos.shardingJdbc5.mapper;

import com.systeminfos.shardingJdbc5.model.UserInfo;

import java.util.List;

//@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> selectList(UserInfo record);

}