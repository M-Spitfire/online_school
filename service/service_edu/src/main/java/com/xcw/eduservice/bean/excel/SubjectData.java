package com.xcw.eduservice.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneLevelName;

    @ExcelProperty(index = 1)
    private String twoLevelName;
}
