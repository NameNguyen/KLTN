package com.shopme.khachhang;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.KhachHang;

@Controller
public class KhachhangController {

	@Autowired
	private KhachHangService khachHangService;
	
	
	@GetMapping("/dangky")
	public String showRegisterForm(Model model) {
		
		
		List<DatNuoc> dsDatnuoc = khachHangService.listAllCountries();
		
		model.addAttribute("khachHang", new KhachHang());
		model.addAttribute("dsDatnuoc", dsDatnuoc);
		
		return "dangky/form_dangky";
	}
	
	@PostMapping("/new_khachhang")
	public String createCustomer(KhachHang khachHang, HttpServletRequest request, Model model) 
			throws MessagingException, UnsupportedEncodingException {
		System.out.println(khachHang);
		khachHangService.registerCustomer(khachHang);	
		
//		sendVerificationEmail(request, khachHang);
		
//		model.addAttribute("pageTitle", "Đăng ký thành công!");
//		System.out.println(khachHang);	
		return "dangky/dangky_thanhcong";
	}
	
}
