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
window.debug = false;

function send_ajax(options) {
    if (options.type == "GET") {  //get方法把参数放在url里
        options.url.replace(/{(.*?)}/g, function (result) {
            let dateName = result.substr(1, -1); //获取{中的属性名}
            if(options.data[dateName] != null)
                return options.data[dateName];
            return "";
        });
        options.data = null; //GET方法没有data域
    }
    if (options.data != null) {
        options.data = JSON.stringify(options.data);
    }
    $.ajax({
        type: options.type == null ? "POST" : options.type,
        datatype: "json",
        data: options.data,
        contentType: options.contentType == null ? "application/json;charset=utf-8" : options.contentType,
        url: options.url,
        success: options.success,
        async: options.async == null ? true : options.async,
        error: options.error == null ? function () {
            fail_toast("发生未知错误!");
        } : options.error,
    });
}

function send_ajax_quick(options) {
    send_ajax({
        type: options.type,
        url: options.url,
        data: options.data,
        async: options.async == null ? true : options.async,
        success: function (data) {
            if (data.status == 200) {
                if (options.success == null) {
                    success_toast(options.option + "成功!");
                } else {
                    options.success(data.data);
                }
            } else {
                fail_toast(options.option + "失败!原因是:" + data.message);
            }
        },
        error: options.error == null ? function () {
            fail_toast(options.option + "失败!发生未知错误");
        } : options.error,
    })
}

function send_ajax_quick_quick(url, optionName, dataModel, modal, success, additional, type) {
    let data = $.bindModelReverse(dataModel);
    if (additional != null) {
        for (let i in additional)
            data[i] = additional[i];
    }
    send_ajax_quick({
        type: type,
        url: url,
        data: data,
        option: optionName,
        success: function (data) {
            success_toast(optionName + "成功!");
            if (modal != null)
                modal.modal("hide");
            if (success != null) {
                success(data);
            }
        }
    });
}

function uploadFile(url, file, fileName, limitSize, options, success, error, xhr) {
    if (file == null) {
        fail_toast("未选择任何文件!")
        return false;
    }
    if (file.size > limitSize) {
        fail_toast("文件大小超过限制!")
        return false;
    }
    let formData = new FormData();
    formData.append(fileName, file);
    formData.append('token', window.token);
    for (let option in options) {
        formData.append(option, options[option]);
    }
    let ajaxBody = {
        url: url,
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        success: success,
        error: error
    };
    if (xhr != null) {
        ajaxBody["xhr"] = xhr;
    }
    $.ajax(ajaxBody);
    return true;
}