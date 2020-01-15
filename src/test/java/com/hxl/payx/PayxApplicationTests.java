package com.hxl.payx;

import com.hxl.payx.dao.CategoryMapper;
import com.hxl.payx.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayxApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void contextLoads() {
        Category category = categoryMapper.findById(100001);
        System.out.println(category.toString());
    }

}
