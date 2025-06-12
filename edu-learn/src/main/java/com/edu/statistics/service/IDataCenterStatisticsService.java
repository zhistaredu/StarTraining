package com.edu.statistics.service;

import com.edu.statistics.domain.DataCenterStatistics;
import com.edu.statistics.domain.view.LearnProjectData;


public interface IDataCenterStatisticsService {

    public DataCenterStatistics getDataCenter();

    public LearnProjectData getLearningProject();
}
