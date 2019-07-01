package com.github.zj.dreamly.spring.annotation.service;

import com.github.zj.dreamly.spring.annotation.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h2>BookService</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-27 17:32
 **/
@Service
public class BookService {

	@Autowired
	private BookDao bookDao;

	public void print() {
		System.out.println(bookDao);
	}

	@Override
	public String toString() {
		return "BookService [bookDao=" + bookDao + "]";
	}

}
