package com.example.demo.dao;

import com.example.demo.entity.EatPerson;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Person;
import com.example.demo.util.CommMapper;

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
public interface EatPersonMapper extends CommMapper<EatPerson> {
}
