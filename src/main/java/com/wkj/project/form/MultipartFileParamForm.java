package com.wkj.project.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@ApiModel("大文件分片入参实体")
@Data
public class MultipartFileParamForm {

    @ApiModelProperty("文件传输任务ID")
    private String taskId;

    @ApiModelProperty("当前为第几分片")
    private int chunk;

    @ApiModelProperty("每个分块的大小")
    private long size;

    @ApiModelProperty("文件总大小")
    private long fileSize;


    @ApiModelProperty("分片总数")
    private int chunkTotal;

    @ApiModelProperty("分块文件传输对象")
    private MultipartFile file;

}
