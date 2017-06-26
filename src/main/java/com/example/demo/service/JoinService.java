package com.example.demo.service;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
public interface JoinService {

    //加入公司操作，可从DelegateExecution获取流程中的变量
    public void joinGroup(DelegateExecution execution) ;

    //获取符合条件的审批人，演示这里写死，使用应用使用实际代码
    public List<String> findUsers(DelegateExecution execution);
}
