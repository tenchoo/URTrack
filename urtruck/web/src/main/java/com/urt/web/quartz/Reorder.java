package com.urt.web.quartz;

import org.springframework.beans.factory.annotation.Autowired;

import com.urt.interfaces.reorder.ReorderService;

public class Reorder {
	/**
	 * 定时任务执行的方法
	 */
	@Autowired
	private ReorderService reorderService;
	public void execute() {
		reorderService.sendMsg();
	}
}
