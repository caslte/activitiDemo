package com.example.demo.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/27]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
public interface EatService {
    //开始流程
    public void startProcess(Long personId);

    //获得某个人的任务别表
    public List<Task> getTasks(String assignee);

    //获得某个人的任务别表
    public List<HistoricActivityInstance> getHistoryTasks(String assignee);

    //根据流程实例ID ,查询当前总实例信息
    public HistoricProcessInstance getTaskProcessInstance(String piId);

    //根据流程实例ID ,查询当前总实例信息
    public List<HistoricProcessInstance> getstartUserTaskList(String startUserId);

    //检查通过
    public void checkEat(Boolean isEat, String taskId);

    //开动
    public void doEat(String taskId);

    void xishou(DelegateExecution execution);

    //得到审批人
    List<String> getAdmins(DelegateExecution execution);
    //得到开饭人
    List<String> getKFAdmins(DelegateExecution execution);

    //打印流程图
    void printProcessImg(String processInstanceId, HttpServletRequest request, HttpServletResponse response);

}
