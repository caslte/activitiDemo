package com.example.demo.dao;

import com.example.demo.entity.Person;
import com.example.demo.util.CommMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@Mapper
public interface PersonMapper extends CommMapper<Person> {
    public Person findByPersonName(String personName);
}
