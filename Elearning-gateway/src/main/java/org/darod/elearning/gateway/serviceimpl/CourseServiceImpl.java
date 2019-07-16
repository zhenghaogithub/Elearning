package org.darod.elearning.gateway.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.service.user.CourseService;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.darod.elearning.gateway.dataobject.CourseDO;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.common.dto.CoursePageModel;
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
    @Deprecated //统一使用getCourseInfoLimited
    public CommonCountModel<List<CourseModel>> getAllCourseInfo(int page, int row) {
        return CommonCountModel.getCountModelFromList(PageHelper.startPage(page, row),
                courseDOMapper.getAllCourseInfo(), CourseModel.class);
    }

    @Override
    @Deprecated //统一使用getCourseInfoLimited
    public CommonCountModel<List<CourseModel>> getAllFreeCourseInfo(int page, int row) {
        return CommonCountModel.getCountModelFromList(PageHelper.startPage(page, row),
                courseDOMapper.getAllFreeCourseInfo(), CourseModel.class);
    }

    @Override
    public CommonCountModel<List<CourseModel>> getCourseInfoLimited(CoursePageModel coursePageModel) {
        return CommonCountModel.getCountModelFromList(PageHelper.startPage(coursePageModel.getPage(), coursePageModel.getRow()),
                courseDOMapper.getCourseInfoLimited(coursePageModel), CourseModel.class);
    }

    @Override
    public CourseModel getCourseById(Integer courseId) {
        return CopyPropertiesUtils.copyProperties(courseDOMapper.selectByPrimaryKey(courseId), CourseModel.class);
    }

//    private CommonCountModel<List<CourseModel>> convertCourseDOListToCountModel(Page page, List<CourseDO> list) {
//        CommonCountModel<List<CourseModel>> commonCountModel = new CommonCountModel<>();
//        commonCountModel.setTotal(page.getTotal());
//        List<CourseModel> courseModels = CopyPropertiesUtils.mapListObject(list, CourseModel.class);
//        commonCountModel.setDataList(courseModels);
//        return commonCountModel;
//    }
}
