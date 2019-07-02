package com.github.zj.dreamly.spring.annotation.tx;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * <h2>UserDao</h2>
 *
 * @author: 苍海之南
 * @since: 2019-07-02 08:16
 **/
@Repository
@AllArgsConstructor
public class UserDao {
	private final JdbcTemplate jdbcTemplate;

	public void insert() {
		String sql = "INSERT INTO `tbl_user`(username,age) VALUES(?,?)";
		String username = UUID.randomUUID().toString().substring(0, 5);
		jdbcTemplate.update(sql, username, 19);
	}
}
