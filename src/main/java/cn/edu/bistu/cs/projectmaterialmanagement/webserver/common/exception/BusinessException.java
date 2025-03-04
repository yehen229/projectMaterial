package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.IResult;

/**
 * 自定义的业务异常类型
 */
public class BusinessException extends RuntimeException {
    private final Integer code;
    private final String message;

    public BusinessException(IResult result) {
        super(result.getCode() + result.getMessage());
        this.code = result.getCode();
        this.message = result.getMessage();
    }
    public BusinessException(Integer code,
                             String message) {
        super(code + message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.code = 1;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
