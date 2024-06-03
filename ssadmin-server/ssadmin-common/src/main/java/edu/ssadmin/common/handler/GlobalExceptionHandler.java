package edu.ssadmin.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ssadmin.common.exception.ServiceException;
import edu.ssadmin.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static edu.ssadmin.common.exception.GlobalErrorCodeConstants.*;


/**
 * @RestControllerAdvice是一个Spring MVC注解，它将一个类标记为全局异常处理类。
 * 这个注解与@ControllerAdvice类似，但它还会自动将返回值转换为JSON格式。
 * @RestControllerAdvice应用于整个应用程序，可以捕获和处理Controller抛出的异常。
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(value = Throwable.class)
    public CommonResult<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {

        System.out.println("全局捕获异常成功");
        if (ex instanceof HttpMessageNotReadableException) {
            return handleHttpMessageNotReadableException((HttpMessageNotReadableException) ex);
        }

        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionExceptionHandler((MethodArgumentNotValidException) ex);
        }

        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }

        if (ex instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) ex);
        }
        return defaultExceptionHandler(request, ex);
    }


    @ExceptionHandler(value = RuntimeException.class)
    public CommonResult<?> runtimeExceptionHandler(RuntimeException ex) {

        if (ex.getCause() instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) ex.getCause();
            return CommonResult.error(serviceException.getCode(), serviceException.getMessage());
        }

        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMsg());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public CommonResult<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // 处理 Required request body is missing 异常
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求正文缺失或格式不正确"));
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        // 返回 ERROR CommonResult
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMsg());
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警
        return CommonResult.error(BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> serviceExceptionHandler(ServiceException ex) {
        System.out.println("捕获异常");
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }
}
