package com.zcc.mobile.sell.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@Data
public class ProductEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    private String image;

    /**
     * 分类
     */
    private Integer category;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 状态
     * 0：在用
     * 1：废弃
     */
    private Integer status;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 最后更新时间
     */

    private Date updateTime;
}
