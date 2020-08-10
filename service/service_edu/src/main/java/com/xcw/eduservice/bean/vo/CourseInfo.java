package com.xcw.eduservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 添加课程时使用的实体类
 */
@Data
public class CourseInfo {
    @ApiModelProperty(value = "课程id")
    private String id;
    @ApiModelProperty(value = "老师id")
    private String teacherId;
    @ApiModelProperty(value = "课程分类id")
    private String subjectId;
    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "价格,0时即为免费")
    private BigDecimal price;
    @ApiModelProperty(value = "总课时数")
    private Integer lessonNum;
    @ApiModelProperty(value = "封面地址")
    private String cover;
    @ApiModelProperty(value = "课程描述")
    private String description;
}
