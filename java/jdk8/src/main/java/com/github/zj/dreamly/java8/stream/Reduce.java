package com.github.zj.dreamly.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Reduce {
 private String id;
 private int nums;
 private int sums;

	@Override
	public String toString() {
		return "Reduce{" +
			"id='" + id + '\'' +
			", nums=" + nums +
			", sums=" + sums +
			'}';
	}

	public String getId() {
  return id;
 }
 public void setId(String id) {
  this.id = id;
 }
 public int getNums() {
  return nums;
 }
 public void setNums(int nums) {
  this.nums = nums;
 }
 public int getSums() {
  return sums;
 }
 public void setSums(int sums) {
  this.sums = sums;
 }

	public static void main(String[] args) {

		List<Reduce> reduceList = new ArrayList<>();
		Reduce reduce = new Reduce();
		reduce.setId("1001");
		reduce.setNums(2);
		reduce.setSums(100);
		reduceList.add(reduce);

		Reduce reduce2 = new Reduce();
		reduce2.setId("1001");
		reduce2.setNums(3);
		reduce2.setSums(100);
		reduceList.add(reduce2);

		List<Reduce> result = merge(reduceList);
		result.forEach(System.out::println);
	}

	/**
	 * 将id进行合并nums, sums 相加道回合并后的集合使用Java8的流进行处理
	 */
	public static List<Reduce> merge(List<Reduce> list) {
		List<Reduce> result = list.stream()
			// 表示id为key， 接着如果有重复的，那么从BillsNums对象o1与o2中筛选出一个，这里选择o1，
			// 并把id重复，需要将nums和sums与o1进行合并的o2, 赋值给o1，最后返回o1
			.collect(Collectors.toMap(Reduce::getId, a -> a, (o1, o2)-> {
				o1.setNums(o1.getNums() + o2.getNums());
				o1.setSums(o1.getSums() + o2.getSums());
				return o1;
			})).values().stream().collect(Collectors.toList());
		return result ;
	}

}
