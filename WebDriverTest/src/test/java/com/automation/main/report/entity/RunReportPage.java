package com.automation.main.report.entity;

import com.automation.main.report.page.ReportResultSection;

/**
 * Created by Lenovo on 20/06/2017.
 */
public interface RunReportPage {

    void runReportAndWaitForResult(ReportResultSection.AggregationTime year);

    void chooseWhatToFillter(String course, String instructor, String recording, String viewer);
}
