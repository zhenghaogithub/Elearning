package org.darod.elearning.common.service.user;

import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CommonPageModel;
import org.darod.elearning.common.dto.OrderModel;
import org.darod.elearning.common.exception.BusinessException;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/13 0013 11:32
 */
public interface OrderService {
    OrderModel createOrder(OrderModel orderModel) throws BusinessException;

    CommonCountModel<List<OrderModel>> getAllOrderInfo(CommonPageModel commonPageModel) throws BusinessException;

    OrderModel getOrderInfoById(String orderId) throws BusinessException;

    OrderModel payOrder(OrderModel orderModel) throws BusinessException;
}
