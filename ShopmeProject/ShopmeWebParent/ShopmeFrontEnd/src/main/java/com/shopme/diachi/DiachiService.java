package com.shopme.diachi;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.KhachHang;

@Service
@Transactional
public class DiachiService  {

	@Autowired private DiachiReponsitory diaChiRepo;
	
	public DiaChi getDefaultDiachiOf(KhachHang khachHang) {
		return diaChiRepo.findDefaultByKhachhang(khachHang.getMaKhachHang());
	}
}
