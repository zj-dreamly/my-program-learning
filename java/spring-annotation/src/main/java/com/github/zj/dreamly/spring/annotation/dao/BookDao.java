package com.github.zj.dreamly.spring.annotation.dao;

import org.springframework.stereotype.Repository;

/**
 * <h2>book dao</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-27 17:30
 **/
@Repository
public class BookDao {

	private String lable = "1";

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	@Override
	public String toString() {
		return "BookDao [lable=" + lable + "]";
	}


}
