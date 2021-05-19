package com.shopme.admin.donhang;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.caidat.CaidatService;
import com.shopme.admin.khachhang.KhachhangNotFoundException;
import com.shopme.admin.sanpham.SanPhamService;
import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.DonHang;

@Controller
public class DonhangController {

	@Autowired
	private DonhangService orderService;
	
	@Autowired
	private SanPhamService productService;
	
	@Autowired
	private CaidatService settingService;
	
	@GetMapping("/donhang")
	public String listAll(Model model, HttpServletRequest request) {
		return listByPage(model, request, 1, "thoiGianDatHang", "desc", null);
	}
	
	@GetMapping("/donhang/page/{pageNum}")
	public String listByPage(Model model, HttpServletRequest request,
						@PathVariable(name = "pageNum") int pageNum,
						@Param("sortField") String sortField,
						@Param("sortDir") String sortDir,
						@Param("keyword") String keyword
			) {
		
		Page<DonHang> page = orderService.listAll(pageNum, sortField, sortDir, keyword);
		List<DonHang> listOrders = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		long startCount = (pageNum - 1) * DonhangService.ORDERS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + DonhangService.ORDERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		if (page.getTotalPages() > 1) {
			model.addAttribute("pageTitle", "Orders (page " + pageNum + ")");
		} else {
			model.addAttribute("pageTitle", "Orders");
		}
		
		loadCurrencySetting(request);
		
		return "donhang/donhang";
	}	
	private void loadCurrencySetting(HttpServletRequest request) {
		List<CaiDat> currencySettings = settingService.getCurrencySettings();
		
		for (CaiDat setting : currencySettings) {
			request.setAttribute(setting.getTuKhoa(), setting.getGiaTri());
		}	
	}
	@GetMapping("/donhang/detail/{id}")
	public String viewOrder(@PathVariable("id") Integer id, Model model, 
			RedirectAttributes ra,
			HttpServletRequest request) {
		
		try {
			DonHang order = orderService.get(id);
			loadCurrencySetting(request);
			model.addAttribute("order", order);
			return "donhang/donhang_detail_modal";
		} catch (DonhangNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/donhang";
		}
		
	}
	@GetMapping("/donhang/delete/{id}")
	public String deleteOrder(@PathVariable("id") Integer id, 
			Model model, RedirectAttributes ra,
			HttpServletRequest request) {
		try {
			orderService.delete(id);;
			loadCurrencySetting(request);
			
			ra.addFlashAttribute("message", "Đơn hàng có mã " + id + " đã được xóa.");
			
		} catch (DonhangNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/donhang";
		
	}
	@GetMapping("/donhang/edit/{id}")
	public String editOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra,
			HttpServletRequest request) {
		try {
			DonHang order = orderService.get(id);;
			
			model.addAttribute("pageTitle", "Chỉnh sửa Đơn hàng (ID: " + id + ")");
			model.addAttribute("order", order);
			
			return "donhang/donhang_form";
			
		} catch (DonhangNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/donhang";
		}
		
	}
}
