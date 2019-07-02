package com.github.zj.dreamly.spring.annotation.tx;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h2>UserService</h2>
 *
 * @author: 苍海之南
 * @since: 2019-07-02 08:17
 **/
@Service
@AllArgsConstructor
public class UserService {
	private UserDao userDao;

	@Transactional(rollbackFor = Exception.class)
	public void insertUser() {
		userDao.insert();
		System.out.println("插入完成...");
		int i = 10 / 0;
	}
}
