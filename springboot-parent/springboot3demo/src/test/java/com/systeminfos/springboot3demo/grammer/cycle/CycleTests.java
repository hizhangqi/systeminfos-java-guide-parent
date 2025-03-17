package com.systeminfos.springboot3demo.grammer.cycle;

import com.systeminfos.springboot3demo.grammar.cycle.Louzai1;
import com.systeminfos.springboot3demo.grammar.cycle.Louzai2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * 循环依赖测试
 */
@SpringBootTest
public class CycleTests {

    @Autowired
    private Louzai1 louzai1;
    @Autowired
    private Louzai2 louzai2;

    @Test
    public void test() {
        louzai1.test1();
        louzai2.test2();
    }

}
