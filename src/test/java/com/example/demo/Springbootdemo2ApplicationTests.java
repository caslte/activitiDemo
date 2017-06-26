package com.example.demo;

import com.example.demo.entity.TestEntity;
import com.example.demo.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springbootdemo2ApplicationTests {
	@Resource
	private TestService testService;

	@Test
	public void contextLoads() {
		TestEntity t = testService.loadTest(2L);
		System.out.println(t.getUserName());
	}

}
