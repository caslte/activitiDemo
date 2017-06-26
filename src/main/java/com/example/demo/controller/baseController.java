package com.example.demo.controller;

import com.example.demo.entity.TestEntity;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/5/26]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@RestController
public class baseController
{
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/")
    public ModelAndView hello()
    {
        ModelAndView result = new ModelAndView("index");
        TestEntity t = testService.loadTest(2L);
        System.out.println(t.getUserName());
        return result;
    }

    @RequestMapping(value = "/showJson")
    public TestEntity showJson()
    {
        TestEntity t = testService.loadTest(2L);
        System.out.println(t.getUserName());
        return t;
    }

    @RequestMapping(value = "/showPage")
    public String showPage()
    {
        ModelAndView mv = new ModelAndView();
        //添加模型数据 可以是任意的POJO对象
        mv.addObject("message", "Hello World!");
        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        mv.setViewName("index");
        return "index";
    }
}
