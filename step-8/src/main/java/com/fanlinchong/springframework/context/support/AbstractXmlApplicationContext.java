package com.fanlinchong.springframework.context.support;

import com.fanlinchong.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.fanlinchong.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
		String[] locations = this.getConfigLocations();
		if (locations != null) {
			beanDefinitionReader.loadBeanDefinitions(locations);
		}
	}

	protected abstract String[] getConfigLocations();
}
