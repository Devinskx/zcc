package com.zcc.mobile.sell.web.controller;

import com.zcc.mobile.sell.common.constant.RestConstant;
import com.zcc.mobile.sell.common.exceptions.SellException;
import com.zcc.mobile.sell.domain.enums.ResponseStatusEnum;
import com.zcc.mobile.sell.domain.model.vo.SellResponse;
import com.zcc.mobile.sell.domain.model.vo.category.CategoryInfoVO;
import com.zcc.mobile.sell.domain.model.vo.category.ModifyCategoryRequest;
import com.zcc.mobile.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Objects;

/**
 * @author Devin sun
 * @date 2022/3/1
 */
@RestController
@RequestMapping(value = RestConstant.REST_PREFIX + RestConstant.CATEGORY_MODULE,
        produces = "application/json;charset=utf-8")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(RestConstant.LIST)
    public SellResponse getCategoryList() {
        try {
            List<CategoryInfoVO> categoryInfos = categoryService.getAllCategory();
            return SellResponse.newBuilder()
                    .setData(categoryInfos)
                    .build();
        } catch (Exception e) {
            if (e instanceof SellException) {
                return SellResponse.newBuilder()
                        .setCode(ResponseStatusEnum.FAILURE.getCode())
                        .setMessage("get product category failed!")
                        .build();
            } else {
                log.error("get product category error", e);
                return SellResponse.newBuilder()
                        .setCode(ResponseStatusEnum.FAILURE.getCode())
                        .setMessage(ResponseStatusEnum.FAILURE.getMessage())
                        .build();
            }
        }
    }

    @PostMapping(RestConstant.CREATE)
    public SellResponse createCategory(@RequestBody ModifyCategoryRequest request) {
        if (Objects.isNull(request)
                || StringUtils.isEmpty(request.getAccount())) {
            return SellResponse.newBuilder()
                    .setCode(ResponseStatusEnum.FAILURE.getCode())
                    .setMessage("Please Login First!")
                    .setData(false)
                    .build();
        }
        try {
            categoryService.createCategory(request);
            return SellResponse.newBuilder()
                    .setData(true)
                    .build();
        } catch (Exception e) {
            if (e instanceof SellException) {
                return SellResponse.newBuilder()
                        .setCode(ResponseStatusEnum.FAILURE.getCode())
                        .setMessage("create product category failed!")
                        .setData(false)
                        .build();
            } else {
                log.error("create product category error!");
                return SellResponse.newBuilder()
                        .setCode(ResponseStatusEnum.FAILURE.getCode())
                        .setData(false)
                        .setMessage(ResponseStatusEnum.FAILURE.getMessage())
                        .build();
            }
        }
    }

    @PostMapping(RestConstant.UPDATE)
    public SellResponse updateCategory(@RequestBody ModifyCategoryRequest request) {
        if (Objects.isNull(request)
                || StringUtils.isEmpty(request.getAccount())) {
            return SellResponse.newBuilder()
                    .setCode(ResponseStatusEnum.FAILURE.getCode())
                    .setMessage("Please Login First!")
                    .setData(false)
                    .build();
        }
        try {
            categoryService.updateCategory(request);
            return SellResponse.newBuilder()
                    .setData(true)
                    .build();
        } catch (Exception e) {
            if (e instanceof SellException) {
                return SellResponse.newBuilder()
                        .setCode(ResponseStatusEnum.FAILURE.getCode())
                        .setMessage("update product category failed!")
                        .setData(false)
                        .build();
            } else {
                log.error("update product category error!");
                return SellResponse.newBuilder()
                        .setCode(ResponseStatusEnum.FAILURE.getCode())
                        .setData(false)
                        .setMessage(ResponseStatusEnum.FAILURE.getMessage())
                        .build();
            }
        }
    }

    @GetMapping(RestConstant.DELETE)
    public SellResponse deleteCategory(@RequestParam("id") Long id) {
        try {
            categoryService.deleteCategory(id);
            return SellResponse.newBuilder()
                    .setData(true)
                    .build();
        } catch (Exception e) {
            if (e instanceof SellException) {
                return SellResponse.newBuilder()
                        .setCode(ResponseStatusEnum.FAILURE.getCode())
                        .setMessage("delete product category failed!")
                        .setData(false)
                        .build();
            } else {
                log.error("delete product category error!");
                return SellResponse.newBuilder()
                        .setCode(ResponseStatusEnum.FAILURE.getCode())
                        .setData(false)
                        .setMessage(ResponseStatusEnum.FAILURE.getMessage())
                        .build();
            }
        }
    }


}
