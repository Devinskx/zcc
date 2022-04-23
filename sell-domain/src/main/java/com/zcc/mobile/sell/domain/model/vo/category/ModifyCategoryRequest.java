package com.zcc.mobile.sell.domain.model.vo.category;

import com.zcc.mobile.sell.domain.model.vo.AbstractRequest;
import lombok.Data;

@Data
public class ModifyCategoryRequest extends AbstractRequest {

    private CategoryType categoryType;
}
