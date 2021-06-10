package com.shopme.admin.baocao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaocaoController {

	@GetMapping("/baocao/banhang")
	public String viewSalesReportHome(Model model) {
		model.addAttribute("pageTitle", "Báo cáo bán hàng");
		return "baocao/baocao_banhang";
	}
//	@GetMapping("/barChart")
//	public String barChart(Model model)
//	{
//		Map<String, Integer> data = new LinkedHashMap<String, Integer>();
//		data.put("Ashish", 30);
//		data.put("Ankit", 50);
//		data.put("Gurpreet", 70);
//		data.put("Mohit", 90);
//		data.put("Manish", 25);
//		model.addAttribute("keySet", data.keySet());
//		model.addAttribute("values", data.values());
//		return "baocao/barChart";
//		
//	}
//	
//	@GetMapping("/pieChart")
//	public String pieChart(Model model) {
//		model.addAttribute("pass", 90);
//		model.addAttribute("fail", 10);
//		return "baocao/pieChart";
//		
//	}
}
