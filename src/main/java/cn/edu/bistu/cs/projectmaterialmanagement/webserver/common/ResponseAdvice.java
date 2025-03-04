package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.DownloadFile;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getDeclaringClass().isAnnotationPresent(ResponseNotIntercept.class)) {
            //如果类中加了ResponseNotIntercept，则不需要该方法返回统一结果
            return false;
        }
        //如果方法中加了ResponseNotIntercept，则不需要该方法返回统一结果
        return !returnType.getMethod().isAnnotationPresent(ResponseNotIntercept.class);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        //System.out.println("beforeBodyWrite");
        if (body instanceof String) {

            Gson gson = new Gson();
            return gson.toJson(ResultData.success(body));
            // return objectMapper.writeValueAsString(ResultData.success(body));
        }

        if (body == null) {
            // System.out.println("beforeBodyWrite-null");
            Gson gson = new Gson();
            return gson.toJson(ResultData.failed("null"));
            //  return ResultData.failed("null");
        }

        if (body instanceof ResultData) {
            return body;
        }

        //如果是下载文件，则不用返回结果，文件内容直接写在Response里面，详见ExcelUtils中的内容
        if (body instanceof DownloadFile)
            return null;

        if (body instanceof byte[])
            return body;

        return ResultData.success(body);
    }


}
