package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<?> handleException(Exception e) {
        //System.out.println("RestExceptionHandler-Exception异常处理");
        e.printStackTrace();

        return ResultData.failed(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<?> handleException(BusinessException e) {
        // System.out.println("RestExceptionHandler-BusinessException异常处理");


        ResultData resultData = ResultData.failed(e.getMessage());


        return ResultData.failed(e.getMessage());
    }


}
