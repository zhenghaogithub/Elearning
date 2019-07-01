package org.darod.elearning.gateway.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.UserLearnModel;
import org.darod.elearning.common.service.user.UserLearnService;
import org.darod.elearning.gateway.dao.UserLearnDOMapper;
import org.darod.elearning.gateway.dataobject.UserLearnDO;
import org.darod.elearning.gateway.utils.CopyPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public CommonCountModel<List<UserLearnModel>> getCourseLearnedInfo(Integer userId, int page, int row) {
        CommonCountModel<List<UserLearnModel>> commonCountModel = new CommonCountModel<>();
        Page pages = PageHelper.startPage(page, row);
        List<UserLearnDO> userLearnDOList = userLearnDOMapper.selectByUserId(userId);
        commonCountModel.setTotal(pages.getTotal());
        List<UserLearnModel> userLearnModelList = CopyPropertiesUtils.mapListObject(userLearnDOList,UserLearnModel.class);

        commonCountModel.setDataList(userLearnModelList);
        return  commonCountModel;
    }


}
