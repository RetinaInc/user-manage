package com.yigwoo.web;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yigwoo.entity.User;
import com.yigwoo.service.AccountService;
import com.yigwoo.service.ShiroDbRealm.ShiroUser;


@Controller
@RequestMapping(value="/profile")
public class ProfileController {
	private static Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getProfile(Model model) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "user/profile";
	}
	
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String getProfileForm(Model model) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		User user = accountService.getUser(shiroUser.id);
		model.addAttribute("user", user);
		return "user/editProfile";
	}
	
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String editProfile(@Valid @ModelAttribute("user") User userModel, RedirectAttributes redirectAttributes) {
		/*
		 * Since the the form in "editProfile" will generate a new User object,
		 * and the form just contains partial information of the old User object,
		 * So we want to keep the username infomation in the new User object in editProfile.jsp
		 * then in this function, retrieve the old User object, combine it with
		 * the new User object, to form the User object we want to save!
		 */
		//logger.debug("User {} {}", userModel.getEmail(), userModel.getUsername());
		User user = accountService.findUserByUsername(userModel.getUsername());
		user.setEmail(userModel.getEmail());
		user.setPlainPassword(userModel.getPlainPassword());
		accountService.updateUser(user);
		updateShiroUserInfo(user);
		String message = "Successfully Updated Your Profile";
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/profile";
	}

	@RequestMapping(value="checkEmail")
	@ResponseBody
	public String checkEmail(@RequestParam("email") String email) {
		logger.debug("{}", email);
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByEmail(email);
		if (user != null && !user.getUsername().equals(shiroUser.getUsername())) {
			return "false";
		} else {
			return "true";
		}
	}
	
	private void updateShiroUserInfo(User user) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		shiroUser.email = user.getEmail();
	}
}
