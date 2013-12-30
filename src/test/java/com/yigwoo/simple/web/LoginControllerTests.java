package com.yigwoo.simple.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class LoginControllerTests extends AbstractContextControllerTests {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	private Logger logger = LoggerFactory.getLogger(LoginControllerTests.class);

	@Before
	public void beforeTests() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void getForm() throws Exception {
		mockMvc.perform(get("/login").accept(MediaType.TEXT_HTML))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/views/user/login.jsp"));
	}

	/*
	 * the login session is taken over by shiro, you must figure out how to test
	 * it
	 */
	@Test
	public void submitSuccess() throws Exception {
		mockMvc.perform(
				post("/login")
				.param("username", "superuser")
				.param("password","superuser"))
				.andDo(print());
	}

}
