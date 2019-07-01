package org.darod.elearning.gateway.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.service.user.CourseService;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.darod.elearning.gateway.dataobject.CourseDO;
import org.darod.elearning.gateway.utils.CopyPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/1 0001 15:24
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDOMapper courseDOMapper;

    @Override
    public CommonCountModel<List<CourseModel>> getAllCourseInfo(int page, int row) {
        CommonCountModel<List<CourseModel>> commonCountModel = new CommonCountModel<>();
        Page pages = PageHelper.startPage(page, row);
        List<CourseDO> courseDOS = courseDOMapper.getAllCourseInfo();
        commonCountModel.setTotal(pages.getTotal());
        List<CourseModel> courseModels = CopyPropertiesUtils.mapListObject(courseDOS, CourseModel.class);
        commonCountModel.setDataList(courseModels);
        return commonCountModel;
    }

}
