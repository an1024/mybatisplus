package com.mybatisplus.config;


import com.mybatisplus.utils.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * 全局异常处理
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 参数验证失败处理
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseMsg handleBindExceptionException(HttpServletRequest request, HttpServletResponse response, BindException e) {
        Map<String, String> data = new ConcurrentHashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError ->
                data.put(fieldError.getField(), fieldError.getDefaultMessage()));
        logger.info("数据验证失败：{}", e.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), data);
    }

    /**
     * 全局异常统一处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseMsg handelException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        int status;
        String msg;
        if (e.getClass().equals(AccessDeniedException.class)) {
            status = HttpStatus.FORBIDDEN.value();
            msg = HttpStatus.FORBIDDEN.getReasonPhrase();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            msg = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }
        response.setStatus(status);
        e.printStackTrace();
        return new ResponseMsg(status, false, msg + ": " + e.getMessage(), null);
    }
}
