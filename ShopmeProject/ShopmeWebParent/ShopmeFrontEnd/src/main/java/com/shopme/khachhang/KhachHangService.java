package com.shopme.khachhang;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.KhachHang;
import com.shopme.security.CustomerUserDetails;
import com.shopme.security.oauth.CustomOAuth2User;

@Service
@Transactional
public class KhachHangService {
	
	@Autowired private DatNuocReponsitory datNuocRp;
	
	@Autowired private KhachHangReponsitory khachHangReponsive;

	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<DatNuoc> listAllCountries() {
		return (List<DatNuoc>) datNuocRp.findAll();
	}
	
	public KhachHang getCustomerByEmail(String email) {
		return khachHangReponsive.getKhachhangByEmail(email);
	}
	
	
	public void registerCustomer(KhachHang khachHang) {
		encodePassword(khachHang);		
		khachHang.setThoiGianTao(new Date());
		khachHang.setTrangThai(true);
		
//		String randomCode = RandomString.make(64);
//		customer.setVerificationCode(randomCode);
		
		khachHangReponsive.save(khachHang);
	}
	
	public void createNewCustomerAfterOAuthLoginSuccess(String email, String name) {
		KhachHang khachHang = new KhachHang();
		khachHang.setEmail(email);
		khachHang.setTrangThai(true);
		khachHang.setThoiGianTao(new Date());
		khachHang.setHo(name);
		
//		customer.setAuthProvider(provider);
		
		
		khachHangReponsive.save(khachHang);
	}
	public void save(KhachHang khachHang) {
		encodePassword(khachHang);
		khachHangReponsive.save(khachHang);
	}
	
	private void encodePassword(KhachHang khachHang) {
		String encodedPassword = passwordEncoder.encode(khachHang.getMatKhau());
		khachHang.setMatKhau(encodedPassword);		
	}
	
	public KhachHang getCurrentlyLoggedInCustomer(Authentication authentication) {
		if(authentication == null) 
			return null;
		
		
		KhachHang khachHang = null;
		Object principal = authentication.getPrincipal();
		
		if(principal instanceof CustomerUserDetails) {
			khachHang = ((CustomerUserDetails) principal).getKhachhang();
		} else if (principal instanceof CustomOAuth2User) {
			
			String email = ((CustomOAuth2User) principal).getEmail();
			khachHang = getCustomerByEmail(email);
		}
		
		System.out.println(khachHang);
		return khachHang;
	}
}
