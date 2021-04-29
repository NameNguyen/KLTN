package com.shopme.khachhang;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.KhachHang;

@Service
@Transactional
public class KhachHangService {
	
	@Autowired private DatNuocReponsitory datNuocRp;
	
	@Autowired private KhachHangReponsitory khachHangReponsive;

	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<DatNuoc> listAllCountries() {
		return (List<DatNuoc>) datNuocRp.findAll();
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
}
