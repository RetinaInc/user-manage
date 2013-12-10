package com.yigwoo.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yigwoo.entity.User;
import com.yigwoo.service.AccountService;

/**
 * The controller which controls the registeration
 * procedure of a new user.
 * @author YigWoo
 *
 */

@Controller
@RequestMapping(value = "/register")
public class RegisterController {
	private static final String USER_ROLE = "user";
	private Logger logger = LoggerFactory.getLogger("com.yigwoo.web.RegisterController");
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "user/register";
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	public String register(@Valid User user,
			RedirectAttributes redirectAttributes) {
		accountService.registerUser(user,USER_ROLE);
		redirectAttributes.addFlashAttribute("username", user.getUsername());
		logger.info("New user {} with email address {} registered", user.getUsername(), user.getEmail());
		return "redirect:/login";
	}
	
	/**
	 * Ajax mapping to check whether a username is occupied
	 */
	@RequestMapping(value="checkUsername")
	@ResponseBody
	public String checkUsername(@RequestParam("username") String username) {
		if (accountService.findUserByUsername(username) == null) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * Ajax mapping to check whether an email address is occupied
	 */
	@RequestMapping(value="checkEmail")
	@ResponseBody
	public String checkEmail(@RequestParam("email") String email) {
		if (accountService.findUserByEmail(email) == null) {
			return "true";
		} else {
			return "false";
		}
	}
}
