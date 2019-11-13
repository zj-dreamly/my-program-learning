package com.github.zj.dreamly.concurrent.util.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 苍海之南
 */
public class CountDownLatchExample4 {

	private static Random random = new Random(System.currentTimeMillis());

	static class Event {
		int id;

		public Event(int id) {
			this.id = id;
		}
	}

	interface Watcher {

		void done(Table table);
	}

	static class TaskBatch implements Watcher {

		private CountDownLatch countDownLatch;

		private TaskGroup taskGroup;

		public TaskBatch(TaskGroup taskGroup, int size) {
			this.taskGroup = taskGroup;
			this.countDownLatch = new CountDownLatch(size);
		}

		@Override
		public void done(Table table) {
			countDownLatch.countDown();
			if (countDownLatch.getCount() == 0) {
				System.out.println("The table " + table.tableName + " finished work,[" + table + "]");
				taskGroup.done(table);
			}

		}
	}

	static class TaskGroup implements Watcher {

		private CountDownLatch countDownLatch;

		private Event event;

		public TaskGroup(int size, Event e) {
			this.event = e;
			this.countDownLatch = new CountDownLatch(size);
		}

		@Override
		public void done(Table table) {
			countDownLatch.countDown();
			if (countDownLatch.getCount() == 0) {
				System.out.println("=====All of table done in event:" + event.id);
			}

		}
	}

	static class Table {
		String tableName;
		long sourceRecordCount = 10;
		long targetCount;
		String sourceColumnSchema = "<table name='a'><column name='col1' type='varchar2'/></table>";
		String targetColumnSchema = "";

		public Table(String tableName, long sourceRecordCount) {
			this.tableName = tableName;
			this.sourceRecordCount = sourceRecordCount;
		}

		@Override
		public String toString() {
			return "Table{" +
				"tableName='" + tableName + '\'' +
				", sourceRecordCount=" + sourceRecordCount +
				", targetCount=" + targetCount +
				", sourceColumnSchema='" + sourceColumnSchema + '\'' +
				", targetColumnSchema='" + targetColumnSchema + '\'' +
				'}';
		}
	}

	private static List<Table> capture(Event event) {
		List<Table> list = new ArrayList<Table>();
		for (int i = 0; i < 10; i++) {
			list.add(new Table("table-" + event.id + "-" + i, i * 1000));
		}

		return list;
	}

	public static void main(String[] args) {
		Event[] events = {new Event(1), new Event(2)};
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		for (Event event : events) {
			List<Table> tables = capture(event);
			TaskGroup taskGroup = new TaskGroup(tables.size(), event);

			for (Table table : tables) {
				TaskBatch taskBatch = new TaskBatch(taskGroup, 2);
				TrustSourceColumns columnsRunnable = new TrustSourceColumns(table, taskBatch);
				TrustSourceRecordCount recordCountRunnable = new TrustSourceRecordCount(table, taskBatch);

				executorService.submit(columnsRunnable);
				executorService.submit(recordCountRunnable);
			}
		}

	}

	static class TrustSourceRecordCount implements Runnable {

		private final Table table;

		private final TaskBatch taskBatch;

		TrustSourceRecordCount(Table table, TaskBatch taskBatch) {
			this.table = table;
			this.taskBatch = taskBatch;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(random.nextInt(10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			table.targetCount = table.sourceRecordCount;
			//System.out.println("The table " + table.tableName + " target record count capture done and update the data");
			taskBatch.done(table);
		}
	}

	static class TrustSourceColumns implements Runnable {

		private final Table table;

		private final TaskBatch taskBatch;

		TrustSourceColumns(Table table, TaskBatch taskBatch) {
			this.table = table;
			this.taskBatch = taskBatch;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(random.nextInt(10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			table.targetColumnSchema = table.sourceColumnSchema;
			//System.out.println("The table " + table.tableName + " target columns capture done and update the data");
			taskBatch.done(table);
		}
	}
}
