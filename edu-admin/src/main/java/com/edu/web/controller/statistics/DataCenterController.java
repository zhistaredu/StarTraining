package com.edu.web.controller.statistics;

import com.edu.common.core.domain.AjaxResult;
import com.edu.statistics.domain.DataCenterStatistics;
import com.edu.statistics.domain.view.LearnProjectData;
import com.edu.statistics.service.IDataCenterStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics/dataCenter")
public class DataCenterController {

    @Autowired
    private IDataCenterStatisticsService dataCenterStatisticsService;

    @GetMapping
    public AjaxResult dataCenter(){
        DataCenterStatistics info = dataCenterStatisticsService.getDataCenter();
        return AjaxResult.success(info);
    }

    @GetMapping("/learningProject")
    public AjaxResult learningProject(){
        LearnProjectData learnProjectData = dataCenterStatisticsService.getLearningProject();
        return AjaxResult.success(learnProjectData);
    }
}
