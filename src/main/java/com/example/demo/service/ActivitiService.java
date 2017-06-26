package com.example.demo.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
public interface ActivitiService {

    //开始流程，传入申请者的id以及公司的id
    public void startProcess(Long personId, Long compId);

    //获得某个人的任务别表
    public List<Task> getTasks(String assignee);

    //完成任务
    public void completeTasks(Boolean joinApproved, String taskId);
}
