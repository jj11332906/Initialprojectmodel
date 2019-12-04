package com.wkj.project.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("文章")
@Data
public class ArticleForm {


    @ApiModelProperty("文章标题")
    @NotNull
    private String title;

    @ApiModelProperty("文章内容，存的是html")
    @NotNull
    private String content;

    @ApiModelProperty("备注")
    @NotNull
    private String remark;

}
