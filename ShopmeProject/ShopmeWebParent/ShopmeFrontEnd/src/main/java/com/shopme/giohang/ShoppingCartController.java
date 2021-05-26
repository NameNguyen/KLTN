package com.shopme.giohang;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.Tienich;
import com.shopme.caidat.CaidatService;
import com.shopme.caidat.CurrencySettingBag;
import com.shopme.caidat.EmailSettingBag;
import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.DonHang;
import com.shopme.common.entity.GiaVanChuyen;
import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.MatHangGioHang;
import com.shopme.common.entity.PhuongThucThanhToan;
import com.shopme.diachi.DiachiService;
import com.shopme.donhang.DonhangService;
import com.shopme.khachhang.KhachHangService;
import com.shopme.security.CustomerUserDetails;

@Controller
public class ShoppingCartController {

	@Autowired private ShoppingCartService cartService;
	
	@Autowired private KhachHangService khachHangService;
	
	@Autowired private DiachiService diaChiService;
	
	@Autowired private DonhangService donHangService;
	
	@Autowired private CaidatService caiDatService;
	
	@GetMapping("/giohang")
	public String showShoppingCart(Model model,
			@AuthenticationPrincipal  CustomerUserDetails userDetails) {
		
		GiaVanChuyen giaVanChuyen = null;
		
		
		
		String userEmail = userDetails.getUsername();
		KhachHang khachHang = khachHangService.getCustomerByEmail(userEmail);
//		KhachHang khachHang = khachHangService.getCurrentlyLoggedInCustomer(authentication);
		DiaChi diaChiMacdinh = diaChiService.getDefaultDiachiOf(khachHang);
		
		if(diaChiMacdinh != null) {
			giaVanChuyen = cartService.getShippingRateForAddress(diaChiMacdinh);
//			System.out.println(giaVanChuyen);
		}else {
			giaVanChuyen = cartService.getShippingRateForCustomer(khachHang);
//			System.out.println(giaVanChuyen);
		}
//		System.out.println("Vận chuyển có sẵn? " + (giaVanChuyen != null));
		
		
		if(giaVanChuyen != null) {
//			System.out.println(giaVanChuyen.getDatNuoc().getTen());
//			System.out.println(giaVanChuyen.getTinh());
//			System.out.println(giaVanChuyen.getGia());
//			System.out.println(giaVanChuyen.getSoNgay());
//			System.out.println("Hỗ trợ phí COD?" + giaVanChuyen.isHoTroCOD());
		}
		
		List<MatHangGioHang> dsGioHang = cartService.listCartItem(khachHang);
		
		
		model.addAttribute("giaVanchuyen", giaVanChuyen);
		model.addAttribute("dsGioHang", dsGioHang);
		model.addAttribute("pageTitle", "Giỏ hàng");
		
		return "giohang";
	}
	
	@GetMapping("/checkout")
	public String showCheckOutPage(Model model, @AuthenticationPrincipal CustomerUserDetails userDetails,
			HttpServletRequest request) {
		
		String userEmail = userDetails.getUsername();
		KhachHang khachHang = khachHangService.getCustomerByEmail(userEmail);
//		KhachHang khachHang = khachHangService.getCurrentlyLoggedInCustomer(authentication);
		
		DiaChi diaChiMacdinh = diaChiService.getDefaultDiachiOf(khachHang);
		
		GiaVanChuyen giaVanChuyen = null;
		
		if(diaChiMacdinh != null) {
			giaVanChuyen = cartService.getShippingRateForAddress(diaChiMacdinh);
			model.addAttribute("diaChiNhanhang", khachHang.getDiachi());
			System.out.println(khachHang.getDiachi());
		}else {
			giaVanChuyen = cartService.getShippingRateForCustomer(khachHang);
		}
		
		if (giaVanChuyen == null) {
			return "redirect:/giohang";
		}
		
		
		String currencyCode = caiDatService.getCurrencyCode();
		
		List<MatHangGioHang> dsGiohang = cartService.listCartItem(khachHang);
		float tongTien = cartService.calculateProductTotal(dsGiohang);
		System.out.println(tongTien);
		
		float tongPhiVanchuyen = cartService.calculateShippingCost(dsGiohang, giaVanChuyen);
		
		float tongPhi = tongTien + tongPhiVanchuyen;
		Date deliverDate = donHangService.calculateDeliverDate(giaVanChuyen.getSoNgay());
		
		model.addAttribute("currencyCode", currencyCode);
		model.addAttribute("dsGiohang", dsGiohang);
		model.addAttribute("khachHang", khachHang);
		
		model.addAttribute("tong", tongTien);
		model.addAttribute("tongPhiVanchuyen", tongPhiVanchuyen);
		model.addAttribute("tongPhi", tongPhi);
		model.addAttribute("deliverDate", deliverDate);
		model.addAttribute("giaVanchuyen", giaVanChuyen);
		model.addAttribute("pageTitle", "Checkout");
		
		return "checkout";
	}
	
	@PostMapping("/dathang")
	public String placeOrder(Model model, @AuthenticationPrincipal CustomerUserDetails userDetails, 
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		String paymentType = request.getParameter("paymentMethod");
		System.out.println("Payment Method: " + paymentType);
		String userEmail = userDetails.getUsername();
		KhachHang khachHang = khachHangService.getCustomerByEmail(userEmail);
		
		DiaChi diaChiMacdinh = diaChiService.getDefaultDiachiOf(khachHang);
		
		GiaVanChuyen giaVanChuyen = null;
		
		if(diaChiMacdinh != null) {
			giaVanChuyen = cartService.getShippingRateForAddress(diaChiMacdinh);
		}else {
			model.addAttribute("diaChiGiaohang", khachHang.getDiachi());
			System.out.println(khachHang.getDiachi());
			giaVanChuyen = cartService.getShippingRateForCustomer(khachHang);
		}
		
		if (giaVanChuyen == null) {
			return "redirect:/giohang";
		}
		
		List<MatHangGioHang> dsGiohang = cartService.listCartItem(khachHang);
		float tongTien = cartService.calculateProductTotal(dsGiohang);
		
		float tongPhiVanchuyen = cartService.calculateShippingCost(dsGiohang, giaVanChuyen);
		
		float tongPhi = tongTien + tongPhiVanchuyen;
		PhuongThucThanhToan paymentMethod = PhuongThucThanhToan.valueOf(paymentType);
		
		DonHang saveDonhang = donHangService.datHang(khachHang, diaChiMacdinh, dsGiohang, 
				paymentMethod, tongPhi, tongPhi, tongPhiVanchuyen, tongTien, giaVanChuyen.getSoNgay());
		
		sendOrderConfirmationEmail(request, saveDonhang);
		return "dathang_hoanthanh";
	}
	private void sendOrderConfirmationEmail(HttpServletRequest request, DonHang donHang) throws UnsupportedEncodingException, MessagingException {
		EmailSettingBag emailSetting = caiDatService.getEmailSettings();
		
		JavaMailSenderImpl mailSender = Tienich.prepareMailSender(emailSetting);
		
		String toAddress = donHang.getKhachHang().getEmail();
		
		String subject = emailSetting.getOrderConfirmationSubject();
		String content = emailSetting.getOrderConfirmationContent();
		
		subject = subject.replace("[[maDonhang]]", String.valueOf(donHang.getMaDonHang()));
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(emailSetting.getFromAddress(), emailSetting.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);
		
		
		DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss E, dd MMM yyyy");
		String orderTime = dateFormatter.format(donHang.getThoiGianDatHang());
		String tongDonhang = formatCurrency(donHang.getTong());
		
		content = content.replace("[[HoTen]]", donHang.getKhachHang().getHoTen());
		content = content.replace("[[MaDonHang]]", String.valueOf(donHang.getMaDonHang()));
		content = content.replace("[[Thoigiandathang]]", orderTime);
		content = content.replace("[[Diachigiao]]", donHang.getShippingAddress());
		content = content.replace("[[Tong]]", tongDonhang);
		content = content.replace("[[Phuongthucthanhtoan]]", donHang.getPhuongThucThanhToan().toString());
		
		String orderURL = Tienich.getSiteURL(request) + "/";
		String orderLink = "<a href=\"" + orderURL + "\">Nhấn vào đây</a>";
		
		content = content.replace("[[Linkdonhang]]", orderLink);
		
		helper.setText(content, true);
		
		mailSender.send(message);
		
		System.out.println("Email xác nhận đơn hàng đã được gửi");	
		
	}
	
	private String formatCurrency(float amount) {
		CurrencySettingBag settings = caiDatService.getCurrencySettingBag();
		
		
		String symbol = settings.getSymbol();
		String symbolPosition = settings.getSymbolPosition();
		String decimalPointType = settings.getDecimalPointType();
		String thousandPointType = settings.getThousandPointType();
		String decimalDigits = settings.getDecimalDigits();
		String pattern = symbolPosition.equals("before") ? symbol : "";
		
		char thousandSeparator = thousandPointType.equals("POINT") ? '.' : ',';
		char decimalSeparator = decimalPointType.equals("POINT") ? '.' : ',';
		
		pattern += "###,###";
		int numberOfDigitsAfterDecimalPoint = Integer.parseInt(decimalDigits);
		
		if (numberOfDigitsAfterDecimalPoint > 0) {
			pattern += ".";
			for (int i = 1; i <= numberOfDigitsAfterDecimalPoint; i++) {
				pattern += "#";
			}
		}		
				
		pattern += symbolPosition.equals("after") ? symbol : "";
		
		System.out.println("Currency pattern: " + pattern);
		
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
		decimalFormatSymbols.setDecimalSeparator(decimalSeparator);
		decimalFormatSymbols.setGroupingSeparator(thousandSeparator);
		
		DecimalFormat formatter = new DecimalFormat(pattern, decimalFormatSymbols);
		return formatter.format(amount);
	}
}
