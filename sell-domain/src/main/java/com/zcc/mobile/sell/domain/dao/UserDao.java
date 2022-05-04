package com.zcc.mobile.sell.domain.dao;

import com.zcc.mobile.sell.domain.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from user")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "age", property = "age")
    })
    List<UserEntity> findUsers();

    @Insert("insert into user values (#{id},#{age})")
    int save(UserEntity userEntity);
}
