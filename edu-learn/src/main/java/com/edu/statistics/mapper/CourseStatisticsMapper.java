package com.edu.statistics.mapper;

import com.edu.statistics.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseStatisticsMapper {

    public List<CourseUserReport> getCourseUserStatistics(Map<String,Object> conditions);

    public List<CourseDeptReport> getCourseDeptStatistics(Map<String,Object> conditions);

}
