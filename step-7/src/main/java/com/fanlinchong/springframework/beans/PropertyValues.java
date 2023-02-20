package com.fanlinchong.springframework.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
	private final List<PropertyValue> propertyValueList = new ArrayList<>();

	public void addPropertyValue(PropertyValue propertyValue) {
		propertyValueList.add(propertyValue);
	}

	public PropertyValue[] getPropertyValues() {
		return this.propertyValueList.toArray(new PropertyValue[0]);
	}

	public PropertyValue getPropertyValue(String propertyName) {
		for (PropertyValue v : this.propertyValueList) {
			if (v.getName().equals(propertyName)) {
				return v;
			}
		}

		return null;
	}
}
