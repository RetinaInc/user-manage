package com.yigwoo.simple.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.junit.After;
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
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) wac.getBean("securityManager");
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		securityManager.setSessionManager(sessionManager);
		setSecurityManager(securityManager);
		Subject subject = new Subject.Builder(getSecurityManager()).buildSubject();
		AuthenticationToken token = new UsernamePasswordToken("superuser", "superuser"); 
		subject.login(token);
		setSubject(subject);
	}
	
	@Test
	public void getForm() throws Exception {
		this.mockMvc.perform(
				get("/profile")
				.accept(MediaType.TEXT_HTML)
				)
				.andDo(print())
				.andExpect(view().name("user/profile"))
				.andExpect(forwardedUrl("/WEB-INF/views/user/profile.jsp"))
				.andExpect(model().size(1))
				.andExpect(model().attributeExists("account"))
				;
				
	}

	@After
	public void afterTest() {
		clearSubject();
	}
	
}
