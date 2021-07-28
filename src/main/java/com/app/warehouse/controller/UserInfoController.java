package com.app.warehouse.controller;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.warehouse.constant.UserMode;
import com.app.warehouse.model.UserInfo;
import com.app.warehouse.service.IRoleService;
import com.app.warehouse.service.IUserInfoService;
import com.app.warehouse.util.EmailUtil;
import com.app.warehouse.util.MyAppUtil;
import com.app.warehouse.util.UserInfoUtil;

@Controller
@RequestMapping("/user")
public class UserInfoController {

	@Autowired
	private IUserInfoService service;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private EmailUtil mailUtil;

	@Autowired
	private BCryptPasswordEncoder encoder;

	private void commonui(Model model) {
		model.addAttribute("rolesMap", roleService.getRolesMap());
	}

	@GetMapping("/register")
	public String showUserRegPage(Model model) {
		commonui(model);
		return "userInfoRegister";
	}

	@PostMapping("/create")
	public String saveUser(@ModelAttribute UserInfo userInfo, Model model) {

		String pwd = MyAppUtil.generatePassword();
		String otp = MyAppUtil.generateOTP();

		userInfo.setPassword(pwd);
		userInfo.setOtp(otp);

		Integer id = service.saveUserInfo(userInfo);
		if (id != 0) {
			String text = " UserName " + userInfo.getEmail() + ",password " + pwd + ", OTP " + otp + ", Roles are "
					+ UserInfoUtil.getRolesAsString(userInfo.getRoles());

			System.out.println(text);
			mailUtil.sendEmail(userInfo.getEmail(), "User is Created", text);
		}
		String msg = "User Created : " + id;
		model.addAttribute("message", msg);

		commonui(model);
		return "userInfoRegister";
	}

	// Show Login Page
	@GetMapping("/login")
	public String showUserLogin() {
		return "UserInfoLogin";
	}

	// Display All Users
	@GetMapping("/all")
	public String showAllUsers(Model model) {

		model.addAttribute("list", service.getAllUserInfo());
		return "UserInfoData";
	}

	// Setup Method
	@GetMapping("/setup")
	public String doSetUp(HttpSession sessiion, Principal p) {
		String emailId = p.getName();
		UserInfo info = service.getOneUserInfoByEmail(emailId).get();
		sessiion.setAttribute("currentUser", info);
		sessiion.setAttribute("isAdmin", UserInfoUtil.getRolesAsString(info.getRoles()).contains("ADMIN"));
		return "redirect:/uom/uomRegister";
	}

	// enable user
	@GetMapping("/enable")
	public String enableUser(@RequestParam Integer id) {
		service.updateUserStatus(id, UserMode.ENABLED);
		return "redirect:all";
	}

	// enable user
	@GetMapping("/disable")
	public String disableUser(@RequestParam Integer id) {
		service.updateUserStatus(id, UserMode.DISABLED);
		return "redirect:all";
	}

	// User Profile
	@GetMapping("/profile")
	public String showUserProfile(HttpSession session, Model model) {

		// read current user from session
		UserInfo info = (UserInfo) session.getAttribute("currentUser");

		// convert roles into String format for display
		Set<String> roles = UserInfoUtil.getRolesAsString(info.getRoles());

		// send details to UI
		model.addAttribute("userInfo", info);
		model.addAttribute("roles", roles);
		return "UserInfoProfile";
	}

	// Show User Page
	@GetMapping("/showForgot")
	public String showForgotPwdPage() {
		return "UserInfoForgotPwd";
	}

	// Regenerate New Password
	@PostMapping("/reGenNewPwd")
	public String reGenNewPwd(@RequestParam String username, Model model) {
		Optional<UserInfo> opt = service.getOneUserInfoByEmail(username);
		// If UserName is Exit
		if (opt.isPresent()) {
			// Generate New Password
			String password = MyAppUtil.generatePassword();

			// Encode and Update to DB
			String encPwd = encoder.encode(password);
			service.updateUserPassword(username, encPwd);

			// Send Email
			String text = " Hello: " + username + ",New Password " + password;
			System.out.println(text);
			mailUtil.sendEmail(username, "New Password is Generated!", text);

			// show message at UI
			model.addAttribute("message", "Password Updated,Please Check Your Mail Box!");
		} else {
			// User name not exist
			model.addAttribute("message", "User Not Exit!");
		}

		return "UserInfoForgotPwd";
	}

	// show Password Update after login
	@GetMapping("/showUpdatePwd")
	public String showUpdatePwd() {
		return "UserNewPwdUpdate";
	}

	// show Password after login
	@PostMapping("/doUpdateNewPwd")
	public String doUpdateNewPassword(@RequestParam String password1, HttpSession session) {
		// read current user data from HttpSession
		UserInfo info = (UserInfo) session.getAttribute("currentUser");

		// Encode password entered in Form
		String encodePwd = encoder.encode(password1);

		// update latest password in DB
		service.updateUserPassword(info.getEmail(), encodePwd);

		// back to profile page
		return "redirect:profile";
	}

	// Show User Activation by OTP
	@GetMapping("/showUserActiveOtp")
	public String showUserActiveOtp() {
		return "UserInfoActiveOtp";
	}

	@PostMapping("/doUserActiveOtp")
	public String doUserActiveOTP(@RequestParam String username, @RequestParam String otp, Model model) {

		// check given emailId/un exist in DB or not?
		Optional<UserInfo> opt = service.getOneUserInfoByEmail(username);
		if (opt.isPresent()) {
			UserInfo user = opt.get();
			if (!otp.equals(user.getOtp())) {
				model.addAttribute("message", "Invalid OTP!");
			} else {
				service.updateUserStatus(user.getId(), UserMode.ENABLED);
				model.addAttribute("message", "User is Active,You can Login!!!!!");
			}
		} else {
			model.addAttribute("message", "User Not Exist!");
		}
		return "UserInfoActiveOtp";
	}
}
