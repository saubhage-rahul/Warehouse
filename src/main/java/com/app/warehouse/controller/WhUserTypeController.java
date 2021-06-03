package com.app.warehouse.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.warehouse.Exception.WhUserTypeNotFoundException;
import com.app.warehouse.model.WhUserType;
import com.app.warehouse.service.IWhUserTypeService;
import com.app.warehouse.util.EmailUtil;
import com.app.warehouse.util.WhUserTypeUtil;

@Controller
@RequestMapping("/wh")
public class WhUserTypeController {

	private static final Logger log = LoggerFactory.getLogger(WhUserTypeController.class);

	@Autowired
	private IWhUserTypeService service;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private WhUserTypeUtil whUTypeutil;

	@Autowired
	private ServletContext context;

	// 1. Show Register Page
	@GetMapping("/register")
	public String showUserTypeRegisterPage() {
		log.info("Inside showUserTypeRegisterPage(): ");
		return "WhUserTypeRegister";
	}

	// 2. Save WhUserType
	@PostMapping("/save")
	public String saveWhUserType(@ModelAttribute WhUserType whUserType, Model model) {
		log.info("Inside saveWhUserType(): ");
		try {
			Integer id = service.saveWhUserType(whUserType);

			// Send Email Details by using Thread

			if (id > 0) {
				new Thread(() -> {
					emailUtil.sendEmail(whUserType.getUserEmail(), "AUTO GENERATED EMAIL",
							emailUtil.getWhUserTemplateData(whUserType));
				}).start();
			}

			String msg = "WhUserType is Created : " + id;
			log.debug("WhUserType Inserted : " + msg);
			model.addAttribute("message", msg);
		} catch (Exception e) {
			log.error("Exception Inside saveWhUserType():" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About WhUserTypeRegister UI Page:");
		return "WhUserTypeRegister";
	}

	// 3. Fetch All WhUserType
	@GetMapping("/all")
	public String fetchAllWhUserType(Model model) {
		log.info("Inside fetchAllWhUserType():");
		try {
			commonLogic(model);

		} catch (Exception e) {
			log.error("Exception inside fetchAllWhUserType():" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About WhUserTypeData UI Page:");
		return "WhUserTypeData";
	}

	// 4. Common Method Logic
	private void commonLogic(Model model) {
		log.info("Inside commonLogic():");

		List<WhUserType> list = service.getAllWhUserType();
		log.debug("All Record Fetched : " + list);
		model.addAttribute("list", list);

	}

	// 5. Delete WhUserType
	@GetMapping("/delete")
	public String deleteWhUserType(@RequestParam Integer id, Model model) {
		log.info("Inside deleteWhUserType():");
		try {

			service.deleteWhUserType(id);
			String msg = "WhUserType Deleted : " + id;
			log.debug(msg);
			model.addAttribute("message", msg);

			commonLogic(model);

		} catch (WhUserTypeNotFoundException e) {
			log.error("Exception inside deleteWhUserType():" + e.getMessage());
			model.addAttribute("message", e.getMessage());

			e.printStackTrace();
		}
		log.info("About WhUserTypeData UI Page:");
		return "WhUserTypeData";
	}

	// 6. Edit WhUserType Based On Id
	@GetMapping("/edit")
	public String editWhUserType(@RequestParam Integer id, Model model) {
		log.info("Inside editWhUserType():");
		String page = null;
		try {

			WhUserType whUserType = service.getWhUserType(id);
			model.addAttribute("whUserType", whUserType);
			log.debug("Edit WhUserType Based on Id : " + whUserType);
			page = "WhUserTypeEdit";

		} catch (WhUserTypeNotFoundException e) {
			log.error("Exception inside editWhUserType :" + e.getMessage());
			model.addAttribute("message", e.getMessage());
			page = "WhUserTypeData";
			e.printStackTrace();
		}
		log.info("Inside WhUserTypeEdit UI Page:");
		return page;
	}

	// 7. Update WhUserType
	@PostMapping("/update")
	public String updateWhUserType(@ModelAttribute WhUserType whUserType, Model model) {
		log.info("Inside updateWhUserType():");
		try {
			service.updateWhUserType(whUserType);
			model.addAttribute("message", "WhUserType Updated Successfully");

			commonLogic(model);

		} catch (Exception e) {
			log.error("Exception inside updateWhUserType(): " + e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		log.info("About WhUserTypeData UI Page:");
		return "WhUserTypeData";
	}

	// 8. AJEX Validations for WhUserType Code Count
	@GetMapping("/validate")
	@ResponseBody
	public String validateWhUserCode(@RequestParam String code, @RequestParam Integer id) {
		log.info("Inside validateWhUserTypeCode():");
		String message = "";

		try {
			if (id == 0 && service.validateWhUserCode(code))
				message = code + ",Already Exit";
			else if (id != 0 && service.validateWhUserCodeAndId(code, id))
				message = code + ",Already Exist";

		} catch (Exception e) {
			log.error("Exception inside validateWhUserTypeCode():" + e.getMessage());
			e.printStackTrace();
		}

		return message;
	}

	// 9. AJEX Validations for WhUserType Email Count
	@GetMapping("/validateEmail")
	@ResponseBody
	public String validateWhUserEmail(String email, Integer id) {
		log.info("Inside validateWhUserEmail():");
		String message = "";
		try {
			if (id == 0 && service.validateWhUserEmail(email))

				message = email + ",Already Exit";
			else if (id != 0 && service.validateWhUserEmailAndId(email, id))

				message = email + ",Already Exit";

		} catch (Exception e) {
			log.error("Exception inside validateWhUserEmail():" + e.getMessage());
			e.printStackTrace();
		}

		return message;
	}

	// 10. AJEX Validations for WhUserType ID Number Count

	@GetMapping("/validateIdNumber")
	@ResponseBody
	public String validateWhUserIdNumber(@RequestParam String number, @RequestParam Integer id) {
		log.info("Inside validateWhUserIdNumber():");
		String message = "";
		try {
			if (id == 0 && service.validateWhUserIdNumber(number))
				message = number + ",Already Exit";
			else if (id != 0 && service.validateWhUserIdNumberAndId(number, id))
				message = number + ",Already Exit";
		} catch (Exception e) {
			log.error("Exception inside validateWhUserIdNumber():" + e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	@GetMapping("/chart")
	public String generateChart() {
		log.info("Inside generateChart() :");
		List<Object[]> list = service.getWhUserTypUserIdTypeChart();
		String path = context.getRealPath("/");
		whUTypeutil.generateFreeChart(path, list);
		return "WhUserIDTypeChart";
	}
}
