package cn.orekiyuta.ark.dto;

import cn.orekiyuta.ark.exception.CustomizeErrorCode;
import cn.orekiyuta.ark.exception.CustomizeException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by orekiyuta on  2019/11/1 - 16:34
 **/
public class ResultDTO<T> {
    private  Integer code;
    private  String message;
    private T data;



    public static ResultDTO errorof(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorof(CustomizeErrorCode errorCode) {
        return errorof(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorof(CustomizeException e) {
        return errorof(e.getCode(),e.getMessage());
    }

    public static ResultDTO okof(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("Success-1");
        return resultDTO;
    }
    public static <T> ResultDTO okof(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("Success-2");
        resultDTO.setData(t);
        return resultDTO;
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
