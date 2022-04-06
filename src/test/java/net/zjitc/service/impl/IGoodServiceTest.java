package net.zjitc.service.impl;

import net.zjitc.entity.Good;
import net.zjitc.service.GoodService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class IGoodServiceTest {
    @Autowired
    private GoodService goodService;

    @Test
    public void test(){
        Good good = goodService.selectGoodById(1);
        System.out.println(good);
    }
}