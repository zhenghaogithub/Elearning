package org.darod.elearning.gateway.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.darod.elearning.common.dto.CommonCountModel;
import org.darod.elearning.common.dto.CourseModel;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.service.user.CourseService;
import org.darod.elearning.gateway.dao.CourseDOMapper;
import org.darod.elearning.gateway.dao.TeacherDOMapper;
import org.darod.elearning.gateway.dataobject.CourseDO;
import org.darod.elearning.common.utils.CopyPropertiesUtils;
import org.darod.elearning.common.dto.CoursePageModel;
import org.darod.elearning.gateway.dataobject.TeacherDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    TeacherDOMapper teacherDOMapper;

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
        CourseDO courseDO = courseDOMapper.selectByPrimaryKey(courseId);
        if (courseDO == null) {
            throw new BusinessException(EmException.COURSE_NOT_EXIST);
        }
        return CopyPropertiesUtils.copyProperties(courseDO, CourseModel.class);
    }

//    //教师视角的课程信息 目前和普通用户视角的一样
//    @Override
//    public CourseModel getCourseToTeacherById(Integer userId, Integer courseId) {
//        CourseDO courseDO = courseDOMapper.selectByPrimaryKey(courseId);
//        if (!courseDO.getTeacherId().equals(getTeacherDOByUserId(userId).getTeacherId())) {
//            throw new BusinessException(EmException.PERMISSION_DENIED, "该课程不是您发布的");
//        }
//        return CopyPropertiesUtils.copyProperties(courseDO, CourseModel.class);
//    }
//
//    //根据用户ID找到对应的教师Id，然后找到该Id的所有课程
//    @Override
//    public CommonCountModel<List<CourseModel>> getCourseByUserId(CoursePageModel coursePageModel) {
//        TeacherDO teacherDO = getTeacherDOByUserId(coursePageModel.getUserId());
//        coursePageModel.setTeacherId(teacherDO.getTeacherId());
//        return CommonCountModel.getCountModelFromList(PageHelper.startPage(coursePageModel.getPage(), coursePageModel.getRow()),
//                courseDOMapper.getCourseInfoLimited(coursePageModel), CourseModel.class);
//    }

    //    private CommonCountModel<List<CourseModel>> convertCourseDOListToCountModel(Page page, List<CourseDO> list) {
//        CommonCountModel<List<CourseModel>> commonCountModel = new CommonCountModel<>();
//        commonCountModel.setTotal(page.getTotal());
//        List<CourseModel> courseModels = CopyPropertiesUtils.mapListObject(list, CourseModel.class);
//        commonCountModel.setDataList(courseModels);
//        return commonCountModel;
//    }

    @Override
    public CourseModel addCourse(CourseModel courseModel) {
        courseModel.setPublishTime(new Date());
        courseModel.setCourseState(0); //课程就不需要审核了
        return CopyPropertiesUtils.copyAndInsertThenReturn(courseModel, CourseDO.class, courseDOMapper::insertSelective,
                (x) -> courseDOMapper.selectByPrimaryKey(x.getCourseId()));
    }

    @Override
    public CourseModel updateCourse(CourseModel courseModel) {
        return CopyPropertiesUtils.copyAndInsertThenReturn(courseModel, CourseDO.class, courseDOMapper::updateByPrimaryKeySelective,
                (x) -> courseDOMapper.selectByPrimaryKey(x.getCourseId()));
    }

//    @Override
//    public CourseModel updateCourseImage(CourseModel courseModel) {
//        return null;
//    }

    @Override
    public void deleteCourse(Integer courseId) {
        courseDOMapper.deleteByPrimaryKey(courseId);
    }
}
