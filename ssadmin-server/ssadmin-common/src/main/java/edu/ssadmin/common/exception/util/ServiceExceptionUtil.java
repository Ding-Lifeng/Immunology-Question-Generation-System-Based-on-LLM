package edu.ssadmin.common.exception.util;

import edu.ssadmin.common.exception.ErrorCode;
import edu.ssadmin.common.exception.ServiceException;

public class ServiceExceptionUtil {

    public static ServiceException exception(ErrorCode errorCode) {
        return exception2(errorCode.getCode(), errorCode.getMsg());
    }

    public static ServiceException exception2(Integer code, String message) {
        return new ServiceException(code, message);
    }

}
