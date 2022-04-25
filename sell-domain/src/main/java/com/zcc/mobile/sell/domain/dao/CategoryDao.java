package com.zcc.mobile.sell.domain.dao;

import com.zcc.mobile.sell.domain.entity.CategoryEntity;
import org.apache.ibatis.annotations.*;

import javax.annotation.Generated;
import java.util.List;

/**
 * @author Devin sun
 * @date 2022/3/1
 */
@Mapper
public interface CategoryDao {


    @Select("select * from category_info where status = #{status}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "type", property = "type"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "status", property = "status"),
            @Result(column = "datachange_createtime", property = "createTime"),
            @Result(column = "datachange_lasttime", property = "updateTime"),
    })
    List<CategoryEntity> findAll(@Param("status") int status);

    @Insert("insert into category_info (type, name, status, description) values " +
            "(#{type}, #{name}, #{status}, #{description})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int createCategory(CategoryEntity categoryEntity);

    @Update("update category_info set type = #{type}, name = #{name}, description = #{description}, " +
            "status = #{status} where id = #{id}")
    int updateCategory(CategoryEntity categoryEntity);

    @Update("update category_info set status = #{status} where id = #{id}")
    int deleteCategory(@Param("status") int status, @Param("id") long id);
}
