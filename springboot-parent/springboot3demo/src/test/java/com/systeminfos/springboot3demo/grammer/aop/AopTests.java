package com.systeminfos.springboot3demo.grammer.aop;

import com.systeminfos.springboot3demo.grammar.aop.Louzai;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AopTests {

    @Autowired
    private Louzai louzai;

    @Test
    public void test() {
        louzai.everyDay();
    }

}
