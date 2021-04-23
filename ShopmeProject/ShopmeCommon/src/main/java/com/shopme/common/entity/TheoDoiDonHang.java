package com.shopme.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "theodoi_donhang")
public class TheoDoiDonHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maTheoDoiDH;
	
	@ManyToOne
	@JoinColumn(name = "donhang_id")
	private DonHang donhang;
	
	private String chuThich;
	
	@Column(name = "thoigian_capnhat")
	private Date thoigian_capnhat;
	
	@Enumerated(EnumType.STRING)
	private TinhTrangDonHang tinhTrangDonHang;
}
