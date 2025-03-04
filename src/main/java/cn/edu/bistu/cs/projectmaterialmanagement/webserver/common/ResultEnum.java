package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;

public enum ResultEnum implements IResult{
    SUCCESS(200,"成功"),
    VALIDATE_FAILED(400,"参数错误"),
    COMMON_FAILED(500,"系统错误"),
    FORBIDDEN(2004,"没有权限");

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
