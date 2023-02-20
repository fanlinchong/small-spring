package com.fanlinchong.springframework.context;

import com.fanlinchong.springframework.beans.factory.HierarchicalBeanFactory;
import com.fanlinchong.springframework.beans.factory.ListableBeanFactory;
import com.fanlinchong.springframework.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
