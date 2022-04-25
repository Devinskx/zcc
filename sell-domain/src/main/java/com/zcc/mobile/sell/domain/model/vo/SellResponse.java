package com.zcc.mobile.sell.domain.model.vo;

import com.zcc.mobile.sell.domain.enums.ResponseStatusEnum;
import lombok.Data;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@Data
public class SellResponse {

    private Integer code;
    private String message;
    private Object data;

    private SellResponse() {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final static class Builder {
        private Integer code;
        private String message;
        private Object data;

        private Builder() {
            this.code = ResponseStatusEnum.SUCCESS.getCode();
            this.message = ResponseStatusEnum.FAILURE.getMessage();
            this.data = null;
        }

        public Builder setCode(Integer code) {
            this.code = code;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setData(Object data) {
            this.data = data;
            return this;
        }

        public SellResponse build() {
            SellResponse response = new SellResponse();
            response.code = this.code;
            response.message = this.message;
            response.data = this.data;
            return response;
        }
    }

}
