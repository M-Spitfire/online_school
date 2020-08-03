package com.xcw.service;

import com.xcw.EduApplication;
import com.xcw.service.serviceImpl.EduTeacherServiceImpl;
import com.xcw.utils.ResultCode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EduApplication.class)
public class teacherServiceTest {
    @Test
    public void test1(){
        System.out.println(ResultCode.SUCCESS);
    }

}
