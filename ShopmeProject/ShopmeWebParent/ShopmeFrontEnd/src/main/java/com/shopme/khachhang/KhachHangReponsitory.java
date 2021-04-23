package com.shopme.khachhang;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.KhachHang;

public interface KhachHangReponsitory extends CrudRepository<KhachHang, Integer>{

	@Query("SELECT kh FROM khachhang kh where kh.email = ?1")
	public KhachHang findByEmail(String email);
	
	
}
