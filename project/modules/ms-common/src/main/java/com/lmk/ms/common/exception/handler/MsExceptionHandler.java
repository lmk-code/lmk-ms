package com.lmk.ms.common.exception.handler;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lmk.ms.common.api.StatusEnum;
import com.lmk.ms.common.api.ResponseResult;
import com.lmk.ms.common.exception.BizException;
import com.lmk.ms.common.validate.ValidateError;

/**
 * MS全局的异常处理
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/07/12
 */
@Slf4j
@ControllerAdvice
public class MsExceptionHandler {
    /**
     * 全局业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseEntity<?> bizExceptionHandler(BizException e){
        // log.warn(e.getMessage(), e);
        if(e.getErrors() != null){
            return new ResponseEntity<>(ResponseResult.error(e.getStatus(), e.getErrors()), e.getHttpStatus());
        }
        return new ResponseEntity<>(ResponseResult.error(e.getStatus(), e.getMessage()), e.getHttpStatus());
    }

    /**
     * 处理参数绑定异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseResult<?> bindExceptionHandler(BindException e) {
        // log.warn(e.getMessage(), e);
        StatusEnum status = StatusEnum.BAD_REQUEST_ParameterNotValid;
        List<ValidateError> errors = ValidateError.convert(e.getBindingResult());
        return ResponseResult.error(status, errors);
    }
}
