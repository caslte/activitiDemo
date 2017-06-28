package com.example.demo.service;

import java.lang.reflect.Array;
import java.util.*;

import com.example.demo.dao.EatPersonMapper;
import com.example.demo.dao.PersonMapper;
import com.example.demo.entity.EatPerson;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
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
@Service("EatService")
public class EatServiceImpl implements EatService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private EatPersonMapper eatPersonMapper;

    //开始流程，传入申请者的id以及公司的id
    public void startProcess(Long personId) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("personId", personId);

        identityService.setAuthenticatedUserId(personId.toString());
        runtimeService.startProcessInstanceByKey("eatProcess", variables);
    }

    //获得某个人的任务别表
    public List<Task> getTasks(String assignee) {
        List<Task> tasks = null;
        tasks = taskService.createTaskQuery().taskCandidateUser(assignee).list();

        return tasks;
    }
    //根据流程实例ID ,查询当前总实例信息
    public HistoricProcessInstance getTaskProcessInstance(String piId) {
        HistoricProcessInstance tasks= null;
        tasks = historyService.createHistoricProcessInstanceQuery().processInstanceId(piId).singleResult();
        return tasks;
    }

    //根据流程实例ID ,查询已完成的历史流程操作
    public List<HistoricActivityInstance> getHistoryTasks(String piId) {
        List<HistoricActivityInstance> tasks= null;
        tasks = historyService.createHistoricActivityInstanceQuery().processInstanceId(piId).finished().orderByHistoricActivityInstanceEndTime().asc().list();
        return tasks;
    }

    @Override
    public void checkEat(Boolean isEat, String taskId) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("isEat", isEat);
        taskService.complete(taskId, taskVariables);
    }

    @Override
    public void doEat(String taskId) {
        taskService.complete(taskId);
    }

    @Override
    public void xishou(DelegateExecution execution) {
        Long personId = (Long) execution.getVariable("personId");
        EatPerson eatPerson = new EatPerson();
        eatPerson.setPersonId(personId);
        eatPerson.setIsWashHands(1L);
        eatPersonMapper.updateByPrimaryKeySelective(eatPerson);
    }

    @Override
    public List<String> getAdmins(DelegateExecution execution) {
        EatPerson eatPerson = new EatPerson();
        eatPerson.setIsAdmin(1L);
        List<EatPerson> list = eatPersonMapper.select(eatPerson);

        List<String> strList = new ArrayList<>();
        for(EatPerson e : list){
            strList.add(e.getPersonId().toString());
        }
        return strList;
    }

    @Override
    public List<String> getKFAdmins(DelegateExecution execution) {
        return Arrays.asList("3");
    }
}
