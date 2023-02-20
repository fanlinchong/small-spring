package com.fanlinchong.springframework.test.bean;

import com.fanlinchong.springframework.context.ApplicationListener;

import java.time.LocalDateTime;

public class CustomEventListener implements ApplicationListener<CustomEvent> {
	@Override
	public void onApplicationEvent(CustomEvent event) {
		System.out.printf("【事件类型】：%s；【时间】：%s；source：%s；\n", event.getClass().getSimpleName(), LocalDateTime.now(), event.getSource());
		System.out.printf("【消息内容】：id=%d，message=%s\n", event.getId(), event.getMessage());
	}
}
