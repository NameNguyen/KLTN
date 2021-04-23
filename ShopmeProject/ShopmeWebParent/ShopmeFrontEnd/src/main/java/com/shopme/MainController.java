package com.shopme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.danhmuc.DanhMucService;
import com.shopme.common.entity.DanhMuc;

@Controller
public class MainController {
	
	@Autowired private DanhMucService danhMucService;
	
	@GetMapping("")
	public String viewHomePage(Model model) {
		List<DanhMuc> listCategories = danhMucService.listNoChildrenCategories();
		model.addAttribute("listCategories", listCategories);
		
		return "index";
	}

}
