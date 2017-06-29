package com.example.demo.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.*;

import com.example.demo.dao.EatPersonMapper;
import com.example.demo.dao.PersonMapper;
import com.example.demo.entity.EatPerson;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private EatPersonMapper eatPersonMapper;

    @Autowired
    private RepositoryService repositoryService;


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

    @Override
    public List<HistoricProcessInstance> getstartUserTaskList(String startUserId) {
        List<HistoricProcessInstance> tasks= null;
        tasks = historyService.createHistoricProcessInstanceQuery().involvedUser(startUserId).list();
        return null;
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

    @Override
    public void printProcessImg(String processInstanceId, HttpServletRequest request, HttpServletResponse response) {
        try
        {
            // 获取流程实例
            ProcessInstance processInstance =
                    runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null) {
            } else {
                BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
                List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .orderByHistoricActivityInstanceStartTime()
                        .asc()
                        .list(); //历史活动节点
                List activitiIds = new ArrayList();
                List flowIds = new ArrayList();
                ProcessDefinition processDef =
                        repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
                ProcessDefinitionEntity processDefinition =
                        (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
                flowIds = getHighLightedFlows(processDefinition, activityInstances);// 获取流程走过的线 (getHighLightedFlows是下面的方法)
                for (HistoricActivityInstance hai : activityInstances) {
                    activitiIds.add(hai.getActivityId());// 获取流程走过的节点
                }

                // 设置图片的字体
                ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
                defaultProcessEngine.getProcessEngineConfiguration().setActivityFontName("宋体"); // 有中文的话防止图片中出现乱码，否则会显示类似于“□”这样的字
                defaultProcessEngine.getProcessEngineConfiguration().setLabelFontName("宋体");
                defaultProcessEngine.getProcessEngineConfiguration().setAnnotationFontName("宋体");
                Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

                ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();

                InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel,
                        "png",
                        activitiIds,
                        flowIds,
                        defaultProcessEngine.getProcessEngineConfiguration().getActivityFontName(),
                        defaultProcessEngine.getProcessEngineConfiguration().getLabelFontName(),
                        defaultProcessEngine.getProcessEngineConfiguration().getAnnotationFontName(),
                        null,
                        1.0);
                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("获取流程图异常!");
        }

    }


    private List getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
                                     List<HistoricActivityInstance> historicActivityInstances)
    {
        List highFlows = new ArrayList();// 用 以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++)
        {// 对历史流程节点进行遍历

            ActivityImpl activityImpl =
                    processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList(); // 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 =
                    processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId()); // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++)
            {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j); // 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1); // 后续第二个节点
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime()))
                { // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 =
                            processDefinitionEntity.findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                }
                else
                {
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions)
            { // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl)pvmTransition.getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl))
                {
                    highFlows.add(pvmTransition.getId());
                }
            }

        }
        return highFlows;
    }
}
