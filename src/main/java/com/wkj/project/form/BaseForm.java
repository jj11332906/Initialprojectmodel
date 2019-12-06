package com.wkj.project.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("通用Form")
@Data
public class BaseForm {


    @ApiModelProperty("accessToken")
    @NotNull
    private String accessToken;

}
