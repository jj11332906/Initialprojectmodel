package com.wkj.project.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("视频")
@Data
public class VideoForm extends BaseForm{


    @ApiModelProperty("视频标题")
    @NotNull
    private String videoTitle;
    @ApiModelProperty("视频路径")
    @NotNull
    private String videoPath;
    @ApiModelProperty("视频MP5")
    @NotNull
    private String videoMp5;
}
