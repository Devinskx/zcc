package com.zcc.mobile.sell.domain.model.bo;

import lombok.Data;

import java.util.List;

@Data
public class GoodInfo {
    private String name;
    private Integer type;
    private List<FoodInfo> foods;
}
