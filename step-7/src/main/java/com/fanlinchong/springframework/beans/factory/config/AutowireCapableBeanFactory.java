package com.fanlinchong.springframework.beans.factory.config;

import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
	/**
	 * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
	 *
	 * @param existingBean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

	/**
	 * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
	 *
	 * @param existingBean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
