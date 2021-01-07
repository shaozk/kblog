package org.example.blog.response;

public class ResponseResult {
    private boolean success;
    private int code;
    private String message;
    private Object data;

    public ResponseResult(ResponseState responseState) {
        this.success = responseState.isSuccess();
        this.code = responseState.getCode();
        this.message = responseState.getMessage();
    }

    public static ResponseResult SUCCESS() {
        return new ResponseResult(ResponseState.SUCCESS);
    }

    public static ResponseResult SUCCESS(String message) {
        ResponseResult responseResult = new ResponseResult(ResponseState.SUCCESS);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult FAILED() {
        return new ResponseResult(ResponseState.FAILED);
    }

    public static ResponseResult FAILED(String message) {
        ResponseResult responseResult = new ResponseResult(ResponseState.FAILED);
        responseResult.setMessage(message);
        return responseResult;
    }


    public boolean isSuccess() {
        return success;
    }



    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDat() {
        return data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }
}
