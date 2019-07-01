package org.darod.elearning.gateway.serviceimpl;

import org.darod.elearning.common.dto.UserLearnModel;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.gateway.dao.UserLearnDOMapper;
import org.darod.elearning.gateway.dataobject.UserLearnDO;
import org.darod.elearning.gateway.utils.CopyPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<UserLearnModel> getCourseLearned(Integer userId) {
        List<UserLearnDO> userLearnDOList = userLearnDOMapper.selectByUserId(userId);
        List<UserLearnModel> userLearnModelList = userLearnDOList.stream().map(userLearnDO -> {
            UserLearnModel userLearnModel = CopyPropertiesUtils.copyProperties(userLearnDO, UserLearnModel.class);
            return userLearnModel;
        }).collect(Collectors.toList());
        return userLearnModelList;
    }
}
