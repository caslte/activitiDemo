package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@Service("ActivitiService")
public class ActivitiServiceImpl implements ActivitiService{
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    //开始流程，传入申请者的id以及公司的id
    public void startProcess(Long personId, Long compId) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("personId", personId);
        variables.put("compId", compId);
        runtimeService.startProcessInstanceByKey("joinProcess", variables);
    }

    //获得某个人的任务别表
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskCandidateUser(assignee).list();
    }

    //完成任务
    public void completeTasks(Boolean joinApproved, String taskId) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("joinApproved", joinApproved);
        taskService.complete(taskId, taskVariables);
    }
}
