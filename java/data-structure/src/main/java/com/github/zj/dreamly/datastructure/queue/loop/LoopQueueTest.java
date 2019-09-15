package com.github.zj.dreamly.datastructure.queue.loop;

/**
 * <h2>LoopQueueTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-30 09:25
 **/
public class LoopQueueTest {
	public static void main(String[] args){

		LoopQueue<Integer> queue = new LoopQueue<>(5);
		for(int i = 0 ; i < 10 ; i ++){
			queue.enqueue(i);
			System.out.println(queue);

			if(i % 3 == 2){
				queue.dequeue();
				System.out.println(queue);
			}
		}
	}
}
