package com.yigwoo.simple.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


public class ProfileControllerTests extends AbstractContextControllerTests {

	@Autowired
	protected WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Before
	public void beforeTest() {
		SecurityManager securityManager = (DefaultWebSecurityManager) wac.getBean("securityManager");
		setSecurityManager(securityManager);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void getForm() throws Exception {
		Subject subject = new Subject.Builder(getSecurityManager()).buildSubject();
		AuthenticationToken token = new UsernamePasswordToken("superuser", "superuser"); 
		subject.login(token);
		setSubject(subject);
		this.mockMvc.perform(
				get("/profile")
				.accept(MediaType.TEXT_HTML)
				)
				.andDo(print());
	}

	@After
	public void afterTest() {
		clearSubject();
	}
	
}
