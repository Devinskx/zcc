package com.zcc.mobile.sell.domain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@Data
public class CategoryEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 类型code
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

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
