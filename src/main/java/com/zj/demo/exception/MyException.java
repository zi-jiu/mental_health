package com.zj.demo.exception;

/**
 * @ClassName MyException
 * @Author 字九
 * @Date 2021/3/23 11:30
 * @Description
 **/
public class MyException extends RuntimeException {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
