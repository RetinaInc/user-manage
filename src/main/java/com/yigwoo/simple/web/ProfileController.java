package com.yigwoo.simple.web;

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

import com.yigwoo.simple.domain.Account;
import com.yigwoo.simple.service.AccountService;
import com.yigwoo.simple.service.ShiroDbRealm.ShiroUser;


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
		Account account = accountService.getAccount(shiroUser.id);
		model.addAttribute("account", account);
		return "user/editProfile";
	}
	
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String editProfile(@Valid @ModelAttribute("account") Account accountModel, RedirectAttributes redirectAttributes) {
		//logger.debug("User {} {}", userModel.getEmail(), userModel.getUsername());
		Account account = accountService.updateAccount(accountModel);
		updateShiroUserInfo(account);
		String message = "Successfully Updated Your Profile";
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/profile";
	}

	@RequestMapping(value="checkEmail")
	@ResponseBody
	public String checkEmail(@RequestParam("email") String email) {
		//logger.debug("{}", email);
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Account account = accountService.getAccountByEmail(email);
		if (account != null && !account.getUsername().equals(shiroUser.getUsername())) {
			return "false";
		} else {
			return "true";
		}
	}
	
	private void updateShiroUserInfo(Account account) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		shiroUser.email = account.getEmail();
	}
}
