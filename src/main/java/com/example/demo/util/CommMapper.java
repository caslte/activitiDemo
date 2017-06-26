package com.example.demo.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/23]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
public interface CommMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
