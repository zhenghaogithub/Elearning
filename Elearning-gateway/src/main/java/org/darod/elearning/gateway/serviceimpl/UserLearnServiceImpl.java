package org.darod.elearning.gateway.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CoursePageModel;
import org.darod.elearning.common.dto.UserLearnModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.darod.elearning.gateway.dao.OrderDOMapper;
import org.darod.elearning.gateway.dao.UserLearnDOMapper;
import org.darod.elearning.gateway.dataobject.CourseDO;
import org.darod.elearning.gateway.dataobject.OrderDO;
import org.darod.elearning.gateway.dataobject.UserLearnDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 8:28
 */
@Service
public class UserLearnServiceImpl implements UserLearnService {
    @Autowired
    UserLearnDOMapper userLearnDOMapper;
    @Autowired
    CourseDOMapper courseDOMapper;
    @Autowired
    OrderDOMapper orderDOMapper;

    @Override
    public CommonCountModel<List<UserLearnModel>> getCourseLearnedInfo(Integer userId, int page, int row) {
        Page pages = PageHelper.startPage(page, row);
        return CommonCountModel.getCountModelFromListWithMapper(pages, userLearnDOMapper.selectUserLearnAllByUserId(userId), UserLearnModel.class,
                UserLearnServiceImpl::convertUserLearnDOToUserLearnModel);
    }

    @Override
    public CommonCountModel<List<UserLearnModel>> getCourseLearnedInfoLimited(CoursePageModel coursePageModel, Integer userId) {
        Page pages = PageHelper.startPage(coursePageModel.getPage(), coursePageModel.getRow());
        return CommonCountModel.getCountModelFromListWithMapper(pages, userLearnDOMapper.selectUserLearnAllByUserIdLimited(coursePageModel), UserLearnModel.class,
                UserLearnServiceImpl::convertUserLearnDOToUserLearnModel);
    }

    @Override
    @Transactional
    public UserLearnModel addUserLearnedCourse(UserLearnModel userLearnModel) throws BusinessException {
        if (userLearnModel.getCourseId() == null) {
            throw new BusinessException(EmException.PARAMETER_VALIDATION_ERROR, "请输入课程ID");
        }
        //取出课程信息并加上行锁
        CourseDO courseDO = courseDOMapper.selectByPrimaryKeyForUpdate(userLearnModel.getCourseId());
        if (courseDO == null) {
            throw new BusinessException(EmException.COURSE_NOT_EXIST);
        }
        if (courseDO.getPrice() > 0) {
            if (orderDOMapper.selectByUserIdAndCourseId(userLearnModel.getUserId(), userLearnModel.getCourseId()).
                    stream().noneMatch(orderDO -> orderDO.getOrderState() == 1)) {
                throw new BusinessException(EmException.COURSE_NOT_PURCHASED);
            }
        }
        //设置学习课程信息
        UserLearnDO userLearnDO = CopyPropertiesUtils.copyProperties(userLearnModel, UserLearnDO.class);
        userLearnDO.setLastChapter(0);
        userLearnDO.setLearnTime(new Date());
        try {
            userLearnDOMapper.insertSelective(userLearnDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmException.COURSE_HAVE_LEARNED);
        }
        //更新课程学习人数
        courseDO.setLearnNum(courseDO.getLearnNum() + 1);
        courseDOMapper.updateByPrimaryKeySelective(courseDO);
        return convertUserLearnDOToUserLearnModel(userLearnDOMapper.selectUserLearnAllByLearnId(userLearnDO.getLearnId()));
    }

    private static UserLearnModel convertUserLearnDOToUserLearnModel(UserLearnDO userLearnDO) {
        UserLearnModel userLearnModel = new UserLearnModel();
        BeanUtils.copyProperties(userLearnDO, userLearnModel);
        if (userLearnDO.getChapterDO() != null) {
            CopyPropertiesUtils.copyPropertiesIgnoreNull(userLearnDO.getChapterDO(), userLearnModel);
        }
        if (userLearnDO.getCourseDO() != null) {
            CopyPropertiesUtils.copyPropertiesIgnoreNull(userLearnDO.getCourseDO(), userLearnModel);
        }
        return userLearnModel;
    }
}
