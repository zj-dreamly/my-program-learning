package com.atguigu.demo.mapper;

import com.atguigu.demo.bean.People;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 *    @author  : shkstart
 *    email   : shkstart@126.com
 *    time    : 15:21
 *    version : v1.0
 * </pre>
 */
@Repository
public interface PeopleMapper {
    List<People> getPeopleList();
}
