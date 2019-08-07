package org.darod.elearning.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.darod.elearning.common.dto.CommonPageModel;
import org.darod.elearning.common.dto.OrderModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.darod.elearning.gateway.serviceimpl.OrderServiceImpl;
import org.darod.elearning.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/13 0013 11:28
 */
@RestController
@RequestMapping("/user/curuser/order")
@Api(tags = "用户订单接口")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;


    @GetMapping("/orders")
    @ApiOperation(value = "获取所有", httpMethod = "GET")
    public CommonResponse getOrders(@RequestParam(required = false) String orderId, @Validated CommonPageModel commonPageModel) throws BusinessException {
        commonPageModel.checkPage();
        if (!StringUtils.isEmpty(orderId)) {
            return ResponseUtils.getOKResponse(orderService.getOrderInfoById(orderId));
        } else {
            return ResponseUtils.getOKResponse(orderService.getAllOrderInfo(commonPageModel).toJSONObject("orders"));
        }
    }

    @PostMapping("/orders")
    @ApiOperation(value = "创建订单", httpMethod = "POST")
    public CommonResponse createOrder(@RequestBody OrderModel orderModel) throws BusinessException {
        orderModel.setUserId(ShiroUtils.getCurUserId());
        return ResponseUtils.getOKResponse(orderService.createOrder(orderModel));
    }

    @PutMapping("/orders")
    @ApiOperation(value = "支付订单", httpMethod = "PUT")
    public CommonResponse payOrder(@RequestBody OrderModel orderModel) throws BusinessException {
        orderModel.setUserId(ShiroUtils.getCurUserId());
        if (StringUtils.isEmpty(orderModel.getOrderId())) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "订单号不能为空");
        }
        if (orderModel.getOrderState() != 1) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "状态码错误");
        }
        return ResponseUtils.getOKResponse(orderService.payOrder(orderModel));
    }
}
