package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.UserCourseTrue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface UserCourseTrueMapper {
    List<UserCourseTrue> querylistbybid(int courseid, String  studentnumber);
    int adduserCourseTrue(UserCourseTrue userCourseTrue);
    int deletemember(UserCourseTrue userCourseTrue);
}
