package com.yigwoo.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.yigwoo.entity.Account;
import com.yigwoo.service.AccountService;
import com.yigwoo.service.ShiroDbRealm.ShiroUser;

/**
 * Administration Controller. 'superuser' could see all users(include
 * 'superuser', 'admin', 'user') list add/remove 'admin'/'user' account. 'admin'
 * could see all 'user' users and himself
 * 
 * @author YigWoo
 * 
 */
@Controller
@RequestMapping(value = "/manage")
public class AccountManagementController {
	public static final String COMMON_USER = "common user";
	public static final String SUPER_ADMIN = "super admin";
	public static final String ADMIN = "admin";
	public static final String UNAUTHORIZED = "errors/unauthorized";
	public static final String MANAGE_USERS_LIST = "manage/usersList";
	public static final String MANAGE_CREATE_USER = "manage/createUser";
	public static final String MANAGE_EDIT_USER = "manage/editUser";

	private static final Map<String, String> userTypes = Maps.newLinkedHashMap();
	static {
		userTypes.put("common user", COMMON_USER);
		userTypes.put("admin", ADMIN);
	}
	
	private Logger logger = LoggerFactory
			.getLogger(AccountManagementController.class);

	@Autowired
	AccountService accountService;

	@RequiresRoles("admin")
	@RequestMapping(value = "users", method = RequestMethod.GET)
	public String getAllCommonUsersList(Model model) {
		List<ShiroUser> users = accountService
				.findShiroUsersByRole(COMMON_USER);
		model.addAttribute("users", users);
		return MANAGE_USERS_LIST;
	}

	/*
	 * @RequestMapping(value = "admins", method = RequestMethod.GET) public
	 * String getAllAdminsList(Model model) { if (isAuthorizedAsSuperuser()) {
	 * // List<Account> admins = // accountService.findAccountByRoles("admin");
	 * // model.addAttribute("admins", admins); return MANAGE_ADMINS_LIST; }
	 * else { return UNAUTHORIZED; } }
	 */

	@RequiresRoles("super admin")
	@RequestMapping(value = "users/create", method = RequestMethod.GET)
	public String createUserForm(Model model) {
		model.addAttribute("userTypes", userTypes);
		return MANAGE_CREATE_USER;
	}

	@RequiresRoles("super admin")
	@RequestMapping(value = "users/create", method = RequestMethod.POST)
	public String createUser(@Valid Account account,
			RedirectAttributes redirectAttributes) {
		logger.info("New Admin {} created", account.getUsername());
		accountService.registerAdminAccount(account);
		redirectAttributes.addFlashAttribute("message",
				"Successfully create a new admin");
		return "redirect:/manage/users";
	}

	@RequiresRoles("super admin")
	@RequestMapping(value = "users/edit/{id}", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", accountService.findAccount(id));
		return MANAGE_EDIT_USER;
	}

	@RequiresRoles("super admin")
	@RequestMapping(value = "users/edit", method = RequestMethod.POST)
	public String editUser(@Valid @ModelAttribute("user") Account accountModel,
			RedirectAttributes redirectAttributes) {
		accountService.updateAccountProfile(accountModel);
		redirectAttributes.addFlashAttribute("message",
				"User information updated.");
		return "redirect:/manage/users";
	}

	@RequiresRoles("super admin")
	@RequestMapping(value = "users/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		boolean isDeleted = accountService.deleteAccount(id);
		String message;
		if (isDeleted) message = "Successfully delete a user";
		else message = "Cannot Delete Super Admin";
		redirectAttributes.addFlashAttribute("message",
				message);
		return "redirect:/manage/users";
	}

	/*
	 * @RequestMapping(value = "admins/edit/{id}", method = RequestMethod.GET)
	 * public String editAdmin(@PathVariable("id") Long id, Model model) { if
	 * (isAuthorizedAsSuperuser()) { Account account =
	 * accountService.findAccount(id); model.addAttribute("user", account);
	 * return MANAGE_EDIT_ADMIN; } else { return UNAUTHORIZED; } }
	 * 
	 * @RequestMapping(value = "admins/edit", method = RequestMethod.POST)
	 * public String editAdmin(
	 * 
	 * @Valid @ModelAttribute("user") Account accountModel, RedirectAttributes
	 * redirectAttributes) { if (isAuthorizedAsSuperuser()) { Account account =
	 * accountService.findAccountByUsername(accountModel .getUsername());
	 * account.setEmail(accountModel.getEmail());
	 * account.setPlainPassword(accountModel.getPlainPassword());
	 * accountService.updateAccount(account);
	 * redirectAttributes.addFlashAttribute("message",
	 * "Admin information updated."); return "redirect:/manage/admins"; } else {
	 * return UNAUTHORIZED; } }
	 * 
	 * @RequestMapping(value = "admins/delete/{id}") public String
	 * deleteAdmin(@PathVariable("id") Long id, RedirectAttributes
	 * redirectAttributes) { Account account = accountService.findAccount(id);
	 * if (isAuthorizedAsSuperuser()) { accountService.deleteAccount(id);
	 * redirectAttributes.addFlashAttribute("message",
	 * "Successfully delete a user"); return "redirect:/manage/admins"; } else {
	 * return UNAUTHORIZED; } }
	 */

	@RequestMapping(value = "checkEmail/{id}")
	@ResponseBody
	public String checkEmail(@RequestParam("email") String email,
			@PathVariable("id") Long id) {
		// logger.debug("{} {}", email, id);
		Account account = accountService.findAccountByEmail(email);
		if (account.getId().equals(id)) {
			return "true";
		} else {
			return "false";
		}
	}
}
