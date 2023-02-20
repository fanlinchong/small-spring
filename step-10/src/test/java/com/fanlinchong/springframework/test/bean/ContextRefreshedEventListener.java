package com.fanlinchong.springframework.test.bean;

import com.fanlinchong.springframework.context.ApplicationListener;
import com.fanlinchong.springframework.context.event.ContextRefreshedEvent;

import java.time.LocalDateTime;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.printf("【事件类型】：%s消息；【时间】：%s\n", event.getClass().getSimpleName(), LocalDateTime.now());
	}
}
