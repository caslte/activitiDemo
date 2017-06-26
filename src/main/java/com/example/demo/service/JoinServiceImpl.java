package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import com.example.demo.entity.Comp;
import com.example.demo.entity.Person;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CompMapper;
import com.example.demo.dao.PersonMapper;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/22]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@Service("joinService")
public class JoinServiceImpl implements JoinService{
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private CompMapper compMapper;

    //加入公司操作，可从DelegateExecution获取流程中的变量
    public void joinGroup(DelegateExecution execution) {
        Boolean bool = (Boolean) execution.getVariable("joinApproved");
        if (bool) {
            Long personId = (Long) execution.getVariable("personId");
            Long compId = (Long) execution.getVariable("compId");
            Comp parm = new Comp();
            parm.setCompId(compId);
            Comp comp = compMapper.selectOne(parm);
            Person parm2 = new Person();
            parm2.setPersonId(personId);
            Person person = personMapper.selectOne(parm2);
            person.setCompId(comp.getCompId());
            personMapper.updateByPrimaryKeySelective(person);
            System.out.println("加入组织成功");
        } else {
            System.out.println("加入组织失败");
        }
    }

    //获取符合条件的审批人，演示这里写死，使用应用使用实际代码
    public List<String> findUsers(DelegateExecution execution) {
        return Arrays.asList("admin", "wtr");
    }
}
