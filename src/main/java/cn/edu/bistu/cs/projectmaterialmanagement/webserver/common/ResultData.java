package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;

import lombok.Data;


@Data
public class ResultData<T> {

    private Integer code;
    private String message;
    private T data;

    public ResultData() {
        this(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
        this.data = null;
    }

    public ResultData(Integer code,
                      String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }


    public ResultData(IResult resultCode) {
        this(resultCode.getCode(), resultCode.getMessage());
        this.data = null;
    }

    public ResultData(IResult resultCode,
                      T data) {
        this(resultCode);
        this.data = data;
    }

    public ResultData(Integer code,
                      String message,
                      T data) {
        this(code, message);
        this.data = data;
    }

    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static <T> ResultData<T> success(String message,
                                            T data) {
        return new ResultData<>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> ResultData<T> failed(T data) {
        return new ResultData<>(ResultEnum.COMMON_FAILED.getCode(), ResultEnum.COMMON_FAILED.getMessage(), data);
    }

    public static <T> ResultData<T> failed(String message) {
        return new ResultData<>(ResultEnum.COMMON_FAILED.getCode(), message, null);
    }

    public static <T> ResultData<T> failed(IResult result) {
        return new ResultData<>(result.getCode(), result.getMessage(), null);
    }

    public static <T> ResultData<T> failed(Integer code,
                                           String message) {
        return new ResultData<>(code, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }


}
