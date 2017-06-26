package com.example.demo.service;

import com.example.demo.dao.TestMapper;
import com.example.demo.entity.TestEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/23]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@Service("testService")
public class TestServiceImpl implements TestService{

    @Resource
    private TestMapper testMapper;

    @Override
    public TestEntity loadTest(Long id) {

        TestEntity param = new TestEntity();
        param.setId(id);
        TestEntity t = testMapper.selectOne(param);
        return t;
    }
}
