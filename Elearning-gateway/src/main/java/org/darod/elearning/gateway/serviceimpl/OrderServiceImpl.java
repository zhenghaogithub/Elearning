package org.darod.elearning.gateway.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CommonPageModel;
import org.darod.elearning.common.dto.OrderModel;
import org.darod.elearning.common.dto.UserLearnModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.OrderService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.darod.elearning.gateway.dao.OrderDOMapper;
import org.darod.elearning.gateway.dao.SequenceDOMapper;
import org.darod.elearning.gateway.dataobject.OrderDO;
import org.darod.elearning.gateway.dataobject.SequenceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/13 0013 11:38
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDOMapper orderDOMapper;
    @Autowired
    private SequenceDOMapper sequenceDOMapper;
    @Autowired
    private CourseDOMapper courseDOMapper;
    @Autowired
    private UserLearnServiceImpl userLearnService;

    @Override
    public CommonCountModel<List<OrderModel>> getAllOrderInfo(CommonPageModel commonPageModel) throws BusinessException {
        return CommonCountModel.getCountModelFromList(PageHelper.startPage(commonPageModel.getPage(), commonPageModel.getRow()),
                orderDOMapper.getAllOrderInfo(), OrderModel.class);
    }

    @Override
    public OrderModel getOrderInfoById(String orderId) {
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        if (orderDO == null) {
            throw new BusinessException(EmException.ORDER_NOT_EXIST);
        }
        return CopyPropertiesUtils.copyProperties(orderDO, OrderModel.class);
    }

    @Override
    @Transactional
    public OrderModel createOrder(OrderModel orderModel) throws BusinessException {
        //判断课程是否存在
        if (courseDOMapper.selectByPrimaryKey(orderModel.getCourseId()) == null) {
            throw new BusinessException(EmException.COURSE_NOT_EXIST);
        }
        //判断是否已经购买过 如果有相同课程的订单已经支付或者还未支付 则拒绝创建
        if (orderDOMapper.selectByUserIdAndCourseIdForUpdate(orderModel.getUserId(), orderModel.getCourseId()).
                stream().anyMatch(orderDO -> (orderDO.getOrderState() == 1 || orderDO.getOrderState() == 0))) {
            throw new BusinessException(EmException.COURSE_HAVE_PURCHASED, "您已购买该课程或已创建相同订单");
        }
        return CopyPropertiesUtils.copyAndInsertThenReturn(orderModel,OrderDO.class,orderDO -> {
            orderDO.setOrderId(generateOrderNo());
            orderDO.setCreateTime(new Date());
            orderDO.setOrderState(0);
            orderDOMapper.insertSelective(orderDO);
        },x -> orderDOMapper.selectByPrimaryKey(x.getOrderId()));
//        String id = generateOrderNo();
//        orderDO.setOrderId(id);
//        orderDO.setCreateTime(new Date());
//        orderDO.setOrderState(0);
//        orderDOMapper.insertSelective(orderDO);
//        return CopyPropertiesUtils.copyProperties(orderDOMapper.selectByPrimaryKey(id), OrderModel.class);
    }

    @Override
    @Transactional
    public OrderModel payOrder(OrderModel orderModel) throws BusinessException {
        OrderDO orderDO = orderDOMapper.selectByPrimaryKeyForUpdate(orderModel.getOrderId());
        if (orderDO == null) {
            throw new BusinessException(EmException.ORDER_NOT_EXIST);
        }
        if (!orderDO.getUserId().equals(orderModel.getUserId())) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "该订单不是您创建的");
        }
        switch (orderDO.getOrderState()) {
            case 1:
                throw new BusinessException(EmException.COURSE_HAVE_PURCHASED);
            case 2:
                throw new BusinessException(EmException.ORDER_DELETED);
            case 3:
                throw new BusinessException(EmException.ORDER_EXPIRED);
        }
        orderDO.setOrderState(1);
        orderDO.setPayTime(new Date());
        orderDOMapper.updateByPrimaryKeySelective(orderDO);
        return CopyPropertiesUtils.copyProperties(orderDO, OrderModel.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo() {
        StringBuilder stringBuilder = new StringBuilder();
        //16位 前8年月日 中间6位为自增序列 最后两位为分库分表位
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);

        //中间
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_no");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKey(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //最后两位
        stringBuilder.append("00");
        return stringBuilder.toString();
    }


}
