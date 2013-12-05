package com.yigwoo.web;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yigwoo.service.ShiroDbRealm.ShiroUser;


@Controller
@RequestMapping(value="/profile")
public class ProfileController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String returnProfile(Model model) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "profile";
	}
}
