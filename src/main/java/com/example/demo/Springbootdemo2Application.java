package com.example.demo;

import com.example.demo.dao.CompMapper;
import com.example.demo.dao.PersonMapper;
import com.example.demo.entity.Comp;
import com.example.demo.entity.Person;
import com.example.demo.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Springbootdemo2Application
{
    @Autowired
    private CompMapper compMapper;

    @Autowired
    private PersonMapper personMapper;
    
    public static void main(String[] args)
    {
        SpringApplication.run(Springbootdemo2Application.class, args);
    }
    
    //初始化模拟数据
    @Bean
    public CommandLineRunner init(final ActivitiService myService)
    {
        return new CommandLineRunner()
        {
            public void run(String... strings)
                throws Exception
            {

                if (personMapper.selectAll().size() == 0)
                {
					System.out.println("初始化数据");
					System.out.println("初始化个人");

                    personMapper.insertSelective(new Person(1L, "小王"));
                    personMapper.insertSelective(new Person(2L, "小孙"));
                    personMapper.insertSelective(new Person(3L, "admin"));
                }
                if (compMapper.selectAll().size() == 0)
                {
					System.out.println("初始化企业");
                    Comp group = new Comp(1L, "XX公司");
                    compMapper.insertSelective(group);
                    Person admin = personMapper.selectOne(new Person(3L, null));
                    Person xw = personMapper.selectOne(new Person(1L, null));
                    admin.setCompId(group.getCompId());
                    xw.setCompId(group.getCompId());
                    personMapper.updateByPrimaryKeySelective(admin);
                    personMapper.updateByPrimaryKeySelective(xw);
					System.out.println("初始化数据完毕");
                }

            }
        };
    }
}
