package com.zcc.mobile.sell.domain.dao;

import com.zcc.mobile.sell.domain.entity.ProductEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@Mapper
public interface ProductDao {

    @Select("select * from product_info where #{condition} = '' or name like #{condition}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "code", property = "code"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "image", property = "image"),
            @Result(column = "category", property = "category"),
            @Result(column = "price", property = "price"),
            @Result(column = "stock", property = "stock"),
            @Result(column = "status", property = "status"),
            @Result(column = "datachange_lasttime", property = "updateTime"),
            @Result(column = "datachange_createtime", property = "createTime"),
    })
    List<ProductEntity> findAll(String condition);

    @Insert("insert into product_info (code,name,description,image,category,price,stock,status) values " +
            "(#{code},#{name},#{description},#{image},#{category},#{price},#{stock},#{status})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(ProductEntity product);

    @Update("update product_info set code = #{code}, name = #{name}, description = #{description}, image = #{image}, " +
            "category = #{category}, price = #{price}, stock = #{stock}, status = #{status} where id = #{id}")
    int update(ProductEntity product);
}
