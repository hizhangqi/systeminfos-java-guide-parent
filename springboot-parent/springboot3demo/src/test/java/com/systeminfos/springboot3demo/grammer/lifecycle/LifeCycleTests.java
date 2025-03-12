package com.systeminfos.springboot3demo.grammer.lifecycle;

import com.systeminfos.springboot3demo.grammar.lifecycle.LouzaiBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 循环依赖测试
 */
@SpringBootTest
public class LifeCycleTests {

    @Autowired
    private LouzaiBean louisaiBean;

    /**
     * <bean name="myBeanPostProcessor" class="com.demo.MyBeanPostProcessor" />
     * <bean name="louzaiBean" class="com.demo.LouzaiBean" init-method="init" destroy-method="destroyMethod">
     * <property name="name" value="楼仔" />
     * </bean>
     * ApplicationContext context =new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
     * LouzaiBean louzaiBean = (LouzaiBean) context.getBean("louzaiBean");
     * louzaiBean.work();
     * ((ClassPathXmlApplicationContext) context).destroy();
     */
    @Test
    public void test() {
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/java/com/systeminfos/springboot3demo/grammar/lifecycle/config.xml");
        LouzaiBean louzaiBean = (LouzaiBean) context.getBean("louzaiBean");
        louzaiBean.work();
        ((ClassPathXmlApplicationContext) context).close();
    }

    @Test
    public void test2() {
        louisaiBean.setName("xxxx");
        louisaiBean.work();
    }

    @Test
    public void test3() {
        // 创建 Spring 应用上下文
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(LouzaiBean.class); // 注册测试的 Bean
            context.refresh(); // 初始化上下文

            // 获取 Bean
            LouzaiBean bean = context.getBean(LouzaiBean.class);
            bean.setName("sss");
            bean.work();
            // 验证 BeanNameAware 的实现

            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
            System.out.println(beanFactory.containsBean("louzaiBean"));
            beanFactory.destroyBean(bean);
        }
    }

}
