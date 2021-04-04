package com.github.zj.dreamly.zookeeper.curator.utils;

public class RedisConfig {

	private String type;	// add 新增配置	update 更新配置	delete 删除配置
	private String url;		// 如果是add或update，则提供下载地址
	private String remark;	// 备注

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
