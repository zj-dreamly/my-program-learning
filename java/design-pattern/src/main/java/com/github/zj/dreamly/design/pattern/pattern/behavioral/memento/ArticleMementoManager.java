package com.github.zj.dreamly.design.pattern.pattern.behavioral.memento;

import java.util.Stack;

/**
 * @author 苍海之南
 */
public class ArticleMementoManager {

	private final Stack<ArticleMemento> ARTICLE_MEMENTO_STACK = new Stack<ArticleMemento>();

	public ArticleMemento getMemento() {
		ArticleMemento articleMemento = ARTICLE_MEMENTO_STACK.pop();
		return articleMemento;
	}

	public void addMemento(ArticleMemento articleMemento) {
		ARTICLE_MEMENTO_STACK.push(articleMemento);
	}

}
