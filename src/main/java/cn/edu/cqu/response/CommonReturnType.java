package cn.edu.cqu.response;

/**
 * 通用的响应结果类
 */
public class CommonReturnType {

    //表明对应请求的返回结果，包括"success"和"fail"
    private String status;

    //若status = "success"，则data返回前端需要的json字符串
    //若status = "fail"，则data内使用通用的错误码格式
    private Object data;


    //定义一个通用的用于创建响应结果对象的创建方法
    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setStatus(status);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
