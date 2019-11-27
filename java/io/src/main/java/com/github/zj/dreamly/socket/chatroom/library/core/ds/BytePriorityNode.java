package com.github.zj.dreamly.socket.chatroom.library.core.ds;

/**
 * 带优先级的节点, 可用于构成链表
 * @author 苍海之南
 */
public class BytePriorityNode<Item> {
	public byte priority;
	public Item item;
	public BytePriorityNode<Item> next;

	public BytePriorityNode(Item item) {
		this.item = item;
	}

	/**
	 * 按优先级追加到当前链表中
	 *
	 * @param node Node
	 */
	public void appendWithPriority(BytePriorityNode<Item> node) {
		if (next == null) {
			next = node;
		} else {
			BytePriorityNode<Item> after = this.next;
			if (after.priority < node.priority) {
				// 中间位置插入
				this.next = node;
				node.next = after;
			} else {
				after.appendWithPriority(node);
			}
		}
	}
}
