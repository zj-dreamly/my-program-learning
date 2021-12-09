package com.atguigu.demo.service;


import com.atguigu.demo.bean.People;
import com.atguigu.demo.mapper.PeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *    @author  : shkstart
 *    email   : shkstart@126.com
 *    time    : 15:26
 *    desc    :
 *    version : v1.0
 * </pre>
 */
@Service
public class PeopleSevice {
    @Autowired
    PeopleMapper peopleMapper;
    public List<People> getPeopleList(){
        return peopleMapper.getPeopleList();
    }

}
