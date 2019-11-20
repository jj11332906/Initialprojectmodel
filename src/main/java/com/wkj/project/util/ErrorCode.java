package com.wkj.project.util;

public enum ErrorCode {
    LOGIN_SUCCESS("登录成功",1000),
    LOGIN_NAME_IS_EMPTY("登录用户名为空",1),
    PWD_IS_EMPTY("登录密码为空",2),
    NOT_FUND_USER("登录用户不存在",3),
    PWD_NOT_MATCH("密码不匹配",4),



    CHGPWD_FAIL("密码修改成功",5),
    CHGPWD_CODE_EMPTY("验证码为空",6),
    CHGPWD_CODE_ERROR("验证码不正确",7),
    CHGPWD_PHONE_ERROR("手机号错误",8),
    CHGPWD_SUCCESS("密码修改成功",1000),




    PARAM_ERROR("参数错误",100),

    FILE_NOT_FOND("文件不存在",301),
    OP_NODATA("没有找到指定内容",400),
    FILE_UPLOAD_EMPTY("上传的文件不存在",701),
    FILE_UPLOAD_FAIL("文件上传失败",702),
    FILE_UPLOAD_NAME_ERROR("上传的文件名不符合规范",701),


    AUTH_NOLOGIN("用户未登录或没有权限",501),
    AUTH_LACK("用户权限不足",502),
    TASK_TYPE_UNKONW("未知的任务类别",601),
    TASK_BUSY("当前正在进行数据同步，无法添加任务,请稍候再试",602),
    TASK_NODATA("上级任务尚未制定，无法添加当前任务",603),
    TASK_STORE_AREA_FAILD("门店与区域无法对应，请确认！",604),

    APP_RELEASE_FAIL("app发布失败",701),
    APP_VERSION_EMPTY("没有找到对应平台的app",702),

    UNKNOW_ERROR("未知错误",998),
    OP_ERROR("处理失败",999),
    OP_SUCCESS("处理成功",1000);

    private String msg;
    private int code;


    ErrorCode(String msg, int code){
        this.msg = msg;
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

}
