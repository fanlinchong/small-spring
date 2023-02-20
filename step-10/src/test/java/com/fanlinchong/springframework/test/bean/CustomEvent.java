package com.fanlinchong.springframework.test.bean;

import com.fanlinchong.springframework.context.event.ApplicationContextEvent;

public class CustomEvent extends ApplicationContextEvent {
	private Integer id;
	private String message;

	/**
	 * Constructs a prototypical Event.
	 *
	 * @param source The object on which the Event initially occurred.
	 * @throws IllegalArgumentException if source is null.
	 */
	public CustomEvent(Object source, Integer id, String message) {
		super(source);
		this.id = id;
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}
}
