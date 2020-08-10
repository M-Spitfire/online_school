package com.xcw.code_generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class Generator {
    @Test
    public void test1(){
        //获取当前项目的路径信息
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
    }


    @Test
    public void codeGenerator(){
        AutoGenerator generator = new AutoGenerator();
        String projectPath = System.getProperty("user.dir");

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig()
                .setAuthor("xcw")//设置作者,会写进注释里
                .setOutputDir(projectPath + "\\src\\main\\java")//设置生成文件的物理路径
                .setOpen(false)//生成后默认不打开
                .setFileOverride(false)//多次重复生成的话覆盖原文件
                .setIdType(IdType.ID_WORKER_STR)//设置自增策略
                .setServiceName("%sService")//设置service的名字,表叫user就转换成UserService
                .setDateType(DateType.ONLY_DATE)//
                .setSwagger2(true);
        //配置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUsername("root")
                .setPassword("123456")
                .setUrl("jdbc:mysql://localhost:3306/school")
                .setDriverName("com.mysql.cj.jdbc.Driver");

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig()
                .setCapitalMode(true)//首字母大写
                .setNaming(NamingStrategy.underline_to_camel)//下划线转换成驼峰命名
                .setInclude("edu_chapter", "edu_course", "edu_course_description", "edu_video")//对哪些表进行自动生成
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true);//url中驼峰转连字符

        //包名策略配置
        PackageConfig packageConfig = new PackageConfig()
                .setParent("com.xcw.eduservice")
                .setController("controller")
                .setEntity("bean")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.serviceImpl")
                .setXml("mapper");//设置生成的xml和mapper放在同一个包下,默认设置是在mapper包内再创建一个xml包用来放xml文件

        //整合进autoConfig
        generator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        //执行
        generator.execute();
    }
}
