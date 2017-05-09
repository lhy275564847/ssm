package com.lhy.ssm.test;

import com.lhy.ssm.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author haiyang.li
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring/spring-core.xml")
public class UserTest {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void testUser(){
        System.out.println(applicationContext.getBean("user"));
    }
}