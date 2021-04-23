package com.shopme.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tinh")
public class Tinh {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maTinh;
	
	
	private String ten;
	
	@ManyToOne
	@JoinColumn(name = "ma_datnuoc")
	private DatNuoc datNuoc;

	public Tinh(String ten, DatNuoc datNuoc) {
		this.ten = ten;
		this.datNuoc = datNuoc;
	}

	public Tinh() {
		// TODO Auto-generated constructor stub
	}

	public Integer getMa() {
		return maTinh;
	}

	public void setMa(Integer ma) {
		this.maTinh = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public DatNuoc getDatNuoc() {
		return datNuoc;
	}

	public void setDatNuoc(DatNuoc datNuoc) {
		this.datNuoc = datNuoc;
	}
	
	

}
