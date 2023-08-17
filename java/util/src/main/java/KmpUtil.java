
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class KmpUtil {

	/**
	 * kmp搜索算法
	 *
	 * @param content 源字符串
	 * @param keyword 字串
	 * @return 如果是-1就是没匹配到，否则返回第一个匹配的位置
	 */
	public static List<KmpDomain> kmpSearch(String content, String keyword) {

		List<KmpDomain> kmpDomainList = new ArrayList<>();
		// 遍历
		for (int i = 0, j = 0; i < content.length(); i++) {
			// 需要处理str1.charAt(i) ！= str2.charAt(j),调整j的大小
			// kmp算法核心点
			while (j > 0 && content.charAt(i) != keyword.charAt(j)) {
				j = KmpUtil.kmpNext(keyword)[j - 1];//*************
			}
			if (content.charAt(i) == keyword.charAt(j)) {
				j++;
			}
			if (j == keyword.length()) {
				j = 0;

				KmpDomain kmpDomain = new KmpDomain();
				kmpDomain.setStart(i - j + 1 - keyword.length());
				kmpDomain.setEnd(i - j + 1);
				kmpDomainList.add(kmpDomain);
			}
		}
		return kmpDomainList;
	}

	/**
	 * 获取到一个字符串（子串）的部分匹配值表
	 */
	public static int[] kmpNext(String dest) {
		// 创建一个next数组保存部分匹配值
		int[] next = new int[dest.length()];
		next[0] = 0;//如果字符串的长度为1，那么不管它是什么，它的部分匹配值都是0
		for (int i = 1, j = 0; i < dest.length(); i++) {
			// 当dest.charAt(i) != dest.charAt(j)满足时，我们需要从next[j-1]获取新的j
			// 直到我们发现有dest.charAt(i) == dest.charAt(j)成立时才退出
			// 这是kmp算法的核心点
			while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
				j = next[j - 1];
			}
			// 当dest.charAt(i) == dest.charAt(j)满足时，部分匹配值就+1
			if (dest.charAt(i) == dest.charAt(j)) {
				j++;
			}
			next[i] = j;
		}
		return next;
	}
}
