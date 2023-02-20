package com.fanlinchong.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.fanlinchong.springframework.beans.BeansException;
import com.fanlinchong.springframework.beans.PropertyValue;
import com.fanlinchong.springframework.beans.factory.config.BeanDefinition;
import com.fanlinchong.springframework.beans.factory.config.BeanReference;
import com.fanlinchong.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.fanlinchong.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.fanlinchong.springframework.core.io.Resource;
import com.fanlinchong.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		super(registry, resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(Resource resource) throws BeansException {
		try (InputStream inputStream = resource.getInputStream()) {
			this.doLoadBeanDefinitions(inputStream);
		} catch (IOException | ClassNotFoundException e) {
			throw new BeansException("IOException parsing XML document from " + resource, e);
		}
	}

	@Override
	public void loadBeanDefinitions(Resource... resources) throws BeansException {
		for (Resource resource : resources) {
			this.loadBeanDefinitions(resource);
		}
	}

	@Override
	public void loadBeanDefinitions(String location) throws BeansException {
		Resource resource = this.getResourceLoader().getResource(location);
		this.loadBeanDefinitions(resource);
	}


	@Override
	public void loadBeanDefinitions(String... locations) throws BeansException {
		for (String location : locations) {
			this.loadBeanDefinitions(location);
		}
	}

	private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
		Document doc = XmlUtil.readXML(inputStream);
		Element root = doc.getDocumentElement();

		NodeList childNodes = root.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (!(node instanceof Element)) {
				continue;
			}

			if (!"bean".equals(node.getNodeName())) {
				continue;
			}

			Element element = (Element) node;
			String id = element.getAttribute("id");
			String name = element.getAttribute("name");
			String className = element.getAttribute("class");

			String initMethodName = element.getAttribute("init-method");
			String destroyMethodName = element.getAttribute("destroy-method");

			// 获取 Class，方便获取类中的名称
			Class<?> clazz = Class.forName(className);
			// 优先级 id > name
			String beanName = StrUtil.isNotEmpty(id) ? id : name;
			if (StrUtil.isEmpty(beanName)) {
				beanName = StrUtil.lowerFirst(clazz.getSimpleName());
			}

			BeanDefinition beanDefinition = new BeanDefinition(clazz);
			beanDefinition.setInitMethodName(initMethodName);
			beanDefinition.setDestroyMethodName(destroyMethodName);

			for (int j = 0; j < element.getChildNodes().getLength(); j++) {
				Node childNode = element.getChildNodes().item(j);
				if (!(childNode instanceof Element)) {
					continue;
				}
				if (!"property".equals(childNode.getNodeName())) {
					continue;
				}

				Element propertyElement = (Element) childNode;
				String attrName = propertyElement.getAttribute("name");
				String attrValue = propertyElement.getAttribute("value");
				String attrRef = propertyElement.getAttribute("ref");

				Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
				PropertyValue propertyValue = new PropertyValue(attrName, value);

				beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
			}

			if (getRegistry().containBeanDefinition(beanName)) {
				throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
			}

			// 注册 BeanDefinition
			getRegistry().registerBeanDefinition(beanName, beanDefinition);
		}
	}
}
