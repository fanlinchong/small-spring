package com.fanlinchong.springframework.test.bean;

import com.fanlinchong.springframework.context.ApplicationListener;
import com.fanlinchong.springframework.context.event.ContextClosedEvent;

import java.time.LocalDateTime;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		System.out.printf("【事件类型】：%s；【时间】：%s\n", event.getClass().getSimpleName(), LocalDateTime.now());
	}
}
