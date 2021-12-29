package com.atguigu.redis.service;

import com.atguigu.redis.entities.User;
import com.atguigu.redis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2021-05-01 14:58
 */
@Service
@Slf4j
public class UserService {

    public static final String CACHE_KEY_USER = "user:";

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;

    public void addUser(User user)
    {
        //1 先插入mysql并成功
        int i = userMapper.insertSelective(user);

        if(i > 0)
        {
            //2 需要再次查询一下mysql将数据捞回来并ok
            user = userMapper.selectByPrimaryKey(user.getId());
            //3 将捞出来的user存进redis，完成新增功能的数据一致性。
            String key = CACHE_KEY_USER+user.getId();
            redisTemplate.opsForValue().set(key,user);
        }
    }

    public void deleteUser(Integer id)
    {
        int i = userMapper.deleteByPrimaryKey(id);

        if(i > 0)
        {
            String key = CACHE_KEY_USER+id;
            redisTemplate.delete(key);
        }
    }

    public void updateUser(User user)
    {
        int i = userMapper.updateByPrimaryKeySelective(user);
        if(i > 0)
        {
            //2 需要再次查询一下mysql将数据捞回来并ok
            user = userMapper.selectByPrimaryKey(user.getId());
            //3 将捞出来的user存进redis，完成修改
            String key = CACHE_KEY_USER+user.getId();
            redisTemplate.opsForValue().set(key,user);
        }
    }

    /**
     * 业务逻辑并没有写错，对于小厂中厂(QPS《=1000)可以使用，但是大厂不行
     * @param id
     * @return
     */
    public User findUserById(Integer id)
    {
        User user = null;
        String key = CACHE_KEY_USER+id;

        //1 先从redis里面查询，如果有直接返回结果，如果没有再去查询mysql
        user = (User) redisTemplate.opsForValue().get(key);

        if(user == null)
        {
            //2 redis里面无，继续查询mysql
            user = userMapper.selectByPrimaryKey(id);
            if(user == null)
            {
                //3.1 redis+mysql 都无数据
                return user;
            }else{
                //3.2 mysql有，需要将数据写回redis，保证下一次的缓存命中率
                redisTemplate.opsForValue().set(key,user);
            }
        }
        return user;
    }


    /**
     * 加强补充，避免突然key实现了，打爆mysql，做一下预防，尽量不出现击穿的情况。
     * @param id
     * @return
     */
    public User findUserById2(Integer id)
    {
        User user = null;
        String key = CACHE_KEY_USER+id;

        //1 先从redis里面查询，如果有直接返回结果，如果没有再去查询mysql
        user = (User) redisTemplate.opsForValue().get(key);

        if(user == null)
        {
            //2 大厂用，对于高QPS的优化，进来就先加锁，保证一个请求操作，让外面的redis等待一下，避免击穿mysql
            synchronized (UserService.class){
                user = (User) redisTemplate.opsForValue().get(key);
                //3 二次查redis还是null，可以去查mysql了(mysql默认有数据)
                if (user == null) {
                    //4 查询mysql拿数据
                    user = userMapper.selectByPrimaryKey(id);//mysql有数据默认
                    if (user == null) {
                        return null;
                    }else{
                        //5 mysql里面有数据的，需要回写redis，完成数据一致性的同步工作
                        redisTemplate.opsForValue().setIfAbsent(key,user,7L,TimeUnit.DAYS);
                    }
                }
            }
        }
        return user;
    }

}

