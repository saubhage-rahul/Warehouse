package com.app.warehouse.controller;

import java.util.List;

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

import com.app.warehouse.Exception.WhUserTypeNotFoundException;
import com.app.warehouse.model.WhUserType;
import com.app.warehouse.service.IWhUserTypeService;
import com.app.warehouse.util.EmailUtil;

@Controller
@RequestMapping("/wh")
public class WhUserTypeController {

	private static final Logger log = LoggerFactory.getLogger(WhUserTypeController.class);

	@Autowired
	private IWhUserTypeService service;

	@Autowired
	private EmailUtil util;

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
					util.sendEmail(whUserType.getUserEmail(), "User Registered",
							"Hello user:" + "User Code: " + whUserType.getUserCode());
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
	public void commonLogic(Model model) {
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

}
