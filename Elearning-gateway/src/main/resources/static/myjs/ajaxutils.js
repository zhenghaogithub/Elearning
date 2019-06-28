//后端API为确定，将所有ajax请求统一放在此文件中


/**
 * 发送ajax请求 并使用重载的POST方法实现PUT DELETE方法 需要后端开启对应的拦截器
 * @param options:{
 *     type : HTTP方法类型,
 *     data :参数的键值对,
 *     url : 请求的URL,
 *     success : 成功回调,
 *     error : 失败回调,
 *     contentType:消息体内容类型
 * }
 */
function send_ajax(options) {
    if (options.type == "DELETE") {  //由于SpringMVC不支持DELETE PUT方法直接传参 因此使用POST在隐藏域加入实际方法类型
        options.type = "POST";
        options.data._method = "DELETE";
    } else if (options.type == "PUT") {
        options.type = "POST";
        options.data._method = "PUT";
    }
    $.ajax({
        type: options.type == null ? "POST" : options.type,
        datatype: "json",
        data: options.data,
        contentType: options.contentType == null ? "application/json;charset=utf-8" : options.contentType,
        url: options.url,
        success: options.success,
        error: options.error == null ? function () {
            fail_toast("发生未知错误!");
        } : options.error,
    });
}