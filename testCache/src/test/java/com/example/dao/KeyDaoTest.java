package com.example.dao;

import com.example.entity.DataPair;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class KeyDaoTest {
    @Autowired
    private KeyDao keyDao;
    @Test
    void selectValue() {
        String key = "test2";
        DataPair dp = keyDao.selectValue(key);
        System.out.println(dp.getTestValue());
    }
}
