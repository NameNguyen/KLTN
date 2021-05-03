package com.shopme.khachhang;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.KhachHang;
import com.shopme.security.oauth.CustomOAuth2User;

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
	
	@GetMapping("/khachhang")
	public String viewKhachhanghome(Model model, HttpServletRequest request) {
		
		Object principal = request.getUserPrincipal();
		
		String emailKhachhang = "";
		
		if(principal instanceof UsernamePasswordAuthenticationToken 
				|| principal instanceof RememberMeAuthenticationToken
				|| principal instanceof PersistentRememberMeToken) {
			
			emailKhachhang = request.getUserPrincipal().getName();
		} else if (principal instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
			CustomOAuth2User oauth2User = (CustomOAuth2User) token.getPrincipal();
			emailKhachhang = oauth2User.getEmail();
		}
		KhachHang khachHang = khachHangService.getCustomerByEmail(emailKhachhang);
		
		
		List<DatNuoc> dsDatnuoc = khachHangService.listAllCountries();
		
		model.addAttribute("listCountries", dsDatnuoc);		
		model.addAttribute("pageTitle", khachHang.getHoTen() + " - Customer Home");
		model.addAttribute("customer", khachHang);
		
		return "khachhang/khachhang_home";
	}
}
