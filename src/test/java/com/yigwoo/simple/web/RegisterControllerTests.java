package com.yigwoo.simple.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
						"file:src/main/webapp/WEB-INF/spring-mvc.xml"})
public class RegisterControllerTests  {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); 
	}
	
	@Test
	public void getForm() throws Exception {
		this.mockMvc.perform(
				get("/register")
				.accept(MediaType.TEXT_HTML)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/views/user/register.jsp"));
	}
	
	@Test
	@Transactional
	public void submitSuccess() throws Exception {
		this.mockMvc.perform(
				post("/register")
				.param("username", "test1")
				.param("email", "test@test.com")
				.param("plainPassword", "123456")
				)
				.andDo(print())
				.andExpect(redirectedUrl("/login"))
				.andExpect(flash().attribute("username", "test1"));
	}
}
