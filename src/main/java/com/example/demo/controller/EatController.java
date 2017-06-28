package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.TestEntity;
import com.example.demo.service.EatService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ActivitiService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@RestController
@RequestMapping("/eat")
public class EatController
{
    @Autowired
    private EatService myService;

    @RequestMapping(value = "/apply")
    public ModelAndView apply()
    {
        ModelAndView result = new ModelAndView("apply");
        return result;
    }
    @RequestMapping(value = "/eatList")
    public ModelAndView eatList()
    {
        ModelAndView result = new ModelAndView("eatList");
        return result;
    }


    //开启流程实例
    @RequestMapping(value = "/start")
    public void startProcessInstance(@RequestParam Long personId)
    {
        myService.startProcess(personId);
    }
    
    //获取当前人的任务
    @RequestMapping(value = "/tasks")
    public List<TaskRepresentation> getTasks(@RequestParam String assignee)
    {
        List<Task> tasks = myService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TaskRepresentation t = new TaskRepresentation();
            t.setId(task.getId());
            t.setName(task.getName());
            t.setProcInstId(task.getProcessInstanceId());
            HistoricProcessInstance hpi = myService.getTaskProcessInstance(task.getProcessInstanceId());
            t.setLiuchengname(hpi.getProcessDefinitionName());
            t.setCreateTime(formatter.format(task.getCreateTime()));
            t.setTaskStartTime(formatter.format(hpi.getStartTime()));
            t.setStartUserId(hpi.getStartUserId());
            dtos.add(t);
        }
        return dtos;
    }

    @RequestMapping(value = "/hiTasksByProcDefId")
    public List<TaskRepresentation> getHiTasksByProcDefId(@RequestParam String assignee)
    {
        List<HistoricActivityInstance> tasks = myService.getHistoryTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (HistoricActivityInstance task : tasks)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dtos.add(new TaskRepresentation(task.getId(), task.getActivityName(),formatter.format(task.getEndTime()),null,null));
        }
        return dtos;
    }

    @RequestMapping(value = "/taskProcessInstance")
    public TaskRepresentation getTaskProcessInstance(@RequestParam String assignee)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HistoricProcessInstance task = myService.getTaskProcessInstance(assignee);
        TaskRepresentation t = new TaskRepresentation(task.getId(),task.getProcessDefinitionName(),formatter.format(task.getStartTime()),task.getStartUserId(),null);
        return t;
    }

    //检查
    @RequestMapping(value = "/checkEat")
    public String checkEat(@RequestParam Boolean joinApproved, @RequestParam String taskId)
    {
        myService.checkEat(joinApproved, taskId);
        return "checkEat";
    }

    //检查
    @RequestMapping(value = "/doEat")
    public String doEat(@RequestParam String taskId)
    {
        myService.doEat( taskId);
        return "doEat";
    }
    
    //Task的dto
    static class TaskRepresentation
    
    {
        private String id;
        
        private String name;

        private String createTime;

        private String startUserId;

        private String procInstId;

        private String liuchengname;

        private String taskStartTime;
        public TaskRepresentation()
        {

        }
        
        public TaskRepresentation(String id, String name,String createTime,String startUserId,String procInstId)
        {
            this.id = id;
            this.name = name;
            this.createTime = createTime;
            this.startUserId =  startUserId;
            this.procInstId = procInstId;
        }

        public String getTaskStartTime() {
            return taskStartTime;
        }

        public void setTaskStartTime(String taskStartTime) {
            this.taskStartTime = taskStartTime;
        }

        public String getProcInstId() {
            return procInstId;
        }

        public void setProcInstId(String procInstId) {
            this.procInstId = procInstId;
        }

        public String getLiuchengname() {
            return liuchengname;
        }

        public void setLiuchengname(String liuchengname) {
            this.liuchengname = liuchengname;
        }

        public String getId()
        {
            return id;
        }
        
        public void setId(String id)
        {
            this.id = id;
        }
        
        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStartUserId() {
            return startUserId;
        }

        public void setStartUserId(String startUserId) {
            this.startUserId = startUserId;
        }
    }
}
