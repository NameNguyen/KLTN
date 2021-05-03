package com.shopme.khachhang;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.KhachHang;


public interface KhachHangReponsitory extends CrudRepository<KhachHang, Integer>{

	@Query(value = "SELECT * FROM khachhang c WHERE c.email = :email", nativeQuery = true)
	public KhachHang getKhachhangByEmail(@Param("email") String email);
}
