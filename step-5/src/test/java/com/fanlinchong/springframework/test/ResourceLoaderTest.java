package com.fanlinchong.springframework.test;

import cn.hutool.core.io.IoUtil;
import com.fanlinchong.springframework.core.io.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceLoaderTest {
	private ResourceLoader resourceLoader;

	@Before
	public void init() {
		resourceLoader = new DefaultResourceLoader();
	}

	@Test
	public void test_LoadFromClassPath() throws IOException {
		Resource resource = new ClassPathResource("important.properties");
		InputStream inputStream = resource.getInputStream();
		String content = IoUtil.readUtf8(inputStream);

		Assert.assertTrue("key=small_spring_test".equals(content));
	}

	@Test
	public void test_LoadFromFile() throws IOException {
		Resource resource = new FileSystemResource("src/test/resources/important.properties");
		InputStream inputStream = resource.getInputStream();
		String content = IoUtil.readUtf8(inputStream);

		Assert.assertTrue("key=small_spring_test".equals(content));
	}

	@Test
	public void test_LoadFromUrl() throws IOException {
		Resource resource = resourceLoader.getResource("https://raw.githubusercontent.com/fanlinchong/small-spring/main/important.properties");
		InputStream inputStream = resource.getInputStream();
		String content = IoUtil.readUtf8(inputStream);

		Assert.assertTrue("key=small_spring_test".equals(content));
	}
}
