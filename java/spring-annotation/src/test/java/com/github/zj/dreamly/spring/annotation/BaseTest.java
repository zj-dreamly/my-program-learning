package com.github.zj.dreamly.spring.annotation;

import com.github.zj.dreamly.spring.annotation.bean.Person;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h2>base test</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-27 17:15
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Person.class)
public class BaseTest {
}
