package com.xcw.eduservice.service;

import com.xcw.eduservice.EduApplication;
import com.xcw.utils.ResultCode;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EduApplication.class)
public class teacherServiceTest {
    @Test
    public void test1(){
        System.out.println(ResultCode.SUCCESS);
    }

}
