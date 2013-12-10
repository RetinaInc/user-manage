package com.yigwoo.web;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

import com.yigwoo.entity.User;
import com.yigwoo.service.AccountService;

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
public class UserManagementController {
	public static final String UNAUTHORIZED = "errors/unauthorized";
	public static final String MANAGE_USERS_LIST = "manage/usersList";
	public static final String MANAGE_ADMINS_LIST = "manage/adminsList";
	public static final String MANAGE_CREATE_ADMIN = "manage/createAdmin";
	public static final String MANAGE_EDIT_USER = "manage/editUser";
	public static final String MANAGE_EDIT_ADMIN = "manage/editAdmin";

	private Logger logger = LoggerFactory
			.getLogger("com.yigwoo.web.UserManagementController");
	@Autowired
	AccountService accountService;

	private boolean isAuthorizedAsAdmin() {
		Subject subject = SecurityUtils.getSubject();
		return subject.hasRole("admin");
	}

	private boolean isAuthorizedAsSuperuser() {
		Subject subject = SecurityUtils.getSubject();
		return subject.hasRole("superuser");
	}

	private boolean isAuthorizedAsAdminOrSuperuser() {
		return isAuthorizedAsAdmin() || isAuthorizedAsSuperuser();
	}

	@RequestMapping(value = "users", method = RequestMethod.GET)
	public String getAllUsersList(Model model) {
		if (isAuthorizedAsAdminOrSuperuser()) {
			List<User> users = accountService.findUserByRoles("user");
			model.addAttribute("users", users);
			return MANAGE_USERS_LIST;
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "admins", method = RequestMethod.GET)
	public String getAllAdminsList(Model model) {
		if (isAuthorizedAsSuperuser()) {
			List<User> admins = accountService.findUserByRoles("admin");
			model.addAttribute("admins", admins);
			return MANAGE_ADMINS_LIST;
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "admins/create", method = RequestMethod.GET)
	public String createAdminForm(Model model) {
		if (isAuthorizedAsSuperuser()) {
			return MANAGE_CREATE_ADMIN;
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "admins/create", method = RequestMethod.POST)
	public String createAdmin(@Valid User user,
			RedirectAttributes redirectAttributes) {
		if (isAuthorizedAsSuperuser()) {
			accountService.registerUser(user, "admin");
			logger.info("New administrator {} created", user.getUsername());
			redirectAttributes.addFlashAttribute("message",
					"Successfully create a new admin");
			return "redirect:/manage/admins";
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "users/edit/{id}", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") Long id, Model model) {
		if (isAuthorizedAsAdminOrSuperuser()) {
			model.addAttribute("user", accountService.getUser(id));
			return MANAGE_EDIT_USER;
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "users/edit", method = RequestMethod.POST)
	public String editUser(@Valid @ModelAttribute("user") User userModel,
			RedirectAttributes redirectAttributes) {
		if (isAuthorizedAsAdminOrSuperuser()) {
			User user = accountService.findUserByUsername(userModel
					.getUsername());
			user.setEmail(userModel.getEmail());
			user.setPlainPassword(userModel.getPlainPassword());
			accountService.updateUser(user);
			redirectAttributes.addFlashAttribute("message",
					"User information updated.");
			return "redirect:/manage/users";
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "users/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		if (isAuthorizedAsAdminOrSuperuser() && user.getRoles().equals("user")) {
			accountService.deleteUser(id);
			redirectAttributes.addFlashAttribute("message", "Successfully delete a user");
			return "redirect:/manage/users";
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "admins/edit/{id}", method = RequestMethod.GET)
	public String editAdmin(@PathVariable("id") Long id, Model model) {
		if (isAuthorizedAsSuperuser()) {
			User user = accountService.getUser(id);
			model.addAttribute("user", user);
			return MANAGE_EDIT_ADMIN;
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "admins/edit", method = RequestMethod.POST)
	public String editAdmin(@Valid @ModelAttribute("user") User userModel,
			RedirectAttributes redirectAttributes) {
		if (isAuthorizedAsSuperuser()) {
			User user = accountService.findUserByUsername(userModel
					.getUsername());
			user.setEmail(userModel.getEmail());
			user.setPlainPassword(userModel.getPlainPassword());
			accountService.updateUser(user);
			redirectAttributes.addFlashAttribute("message",
					"Admin information updated.");
			return "redirect:/manage/admins";
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "admins/delete/{id}")
	public String deleteAdmin(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		if (isAuthorizedAsSuperuser() && user.getRoles().equals("admin")) {
			accountService.deleteUser(id);
			redirectAttributes.addFlashAttribute("message", "Successfully delete a user");
			return "redirect:/manage/admins";
		} else {
			return UNAUTHORIZED;
		}
	}

	@RequestMapping(value = "checkEmail/{id}")
	@ResponseBody
	public String checkEmail(@RequestParam("email") String email,
			@PathVariable("id") Long id) {
		// logger.debug("{} {}", email, id);
		User user = accountService.findUserByEmail(email);
		if (user.getId().equals(id)) {
			return "true";
		} else {
			return "false";
		}
	}
}
