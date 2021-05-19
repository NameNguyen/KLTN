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

	public Integer getMaTheoDoiDH() {
		return maTheoDoiDH;
	}

	public void setMaTheoDoiDH(Integer maTheoDoiDH) {
		this.maTheoDoiDH = maTheoDoiDH;
	}

	public DonHang getDonhang() {
		return donhang;
	}

	public void setDonhang(DonHang donhang) {
		this.donhang = donhang;
	}

	public String getChuThich() {
		return chuThich;
	}

	public void setChuThich(String chuThich) {
		this.chuThich = chuThich;
	}

	public Date getThoigian_capnhat() {
		return thoigian_capnhat;
	}

	public void setThoigian_capnhat(Date thoigian_capnhat) {
		this.thoigian_capnhat = thoigian_capnhat;
	}

	public TinhTrangDonHang getTinhTrangDonHang() {
		return tinhTrangDonHang;
	}

	public void setTinhTrangDonHang(TinhTrangDonHang tinhTrangDonHang) {
		this.tinhTrangDonHang = tinhTrangDonHang;
	}
	
}
