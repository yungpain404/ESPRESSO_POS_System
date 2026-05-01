package entity;

import java.time.LocalDate;
import java.util.*;

public class HoaDon {
	private String maHD;
	private KhachHang khachHang;
	private NhanVien nvLap;
	private LocalDate ngayGioLap;
	private boolean trangThaiTT;
	private PhuongThucThanhToan phuongThucTT;
	private double tongTien;
	private List<ChiTietHoaDon> dsChiTiet = new ArrayList<>();
	
	public String getMaHD() {
		return maHD;
	}



	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}



	public KhachHang getKhachHang() {
		return khachHang;
	}



	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}



	public NhanVien getNvLap() {
		return nvLap;
	}



	public void setNvLap(NhanVien nvLap) {
		this.nvLap = nvLap;
	}



	public LocalDate getNgayGioLap() {
		return ngayGioLap;
	}



	public void setNgayGioLap(LocalDate ngayGioLap) {
		this.ngayGioLap = ngayGioLap;
	}



	public boolean isTrangThaiTT() {
		return trangThaiTT;
	}



	public void setTrangThaiTT(boolean trangThaiTT) {
		this.trangThaiTT = trangThaiTT;
	}



	public PhuongThucThanhToan getPhuongThucTT() {
		return phuongThucTT;
	}



	public void setPhuongThucTT(PhuongThucThanhToan phuongThucTT) {
		this.phuongThucTT = phuongThucTT;
	}



	public double getTongTien() {
		return tongTien;
	}



	public void setTongTien() {
		double tong = 0;
        for (ChiTietHoaDon ct : dsChiTiet) {
            tong += ct.getThanhTien(); 
        }
        this.tongTien = tong;
	}
	public void setDsChiTiet(List<ChiTietHoaDon> dsChiTiet) {
	    this.dsChiTiet = dsChiTiet;
	}


	public HoaDon() {
		
	}

	public HoaDon(String maHD, KhachHang khachHang, NhanVien nvLap, LocalDate ngayGioLap, boolean trangThaiTT,
			PhuongThucThanhToan phuongThucTT) {
		setMaHD(maHD);
		setKhachHang(khachHang);
		setNvLap(nvLap);
		setNgayGioLap(ngayGioLap);
		setTrangThaiTT(trangThaiTT);
		setPhuongThucTT(phuongThucTT);
		setTongTien();
	}



	@Override
	public int hashCode() {
		return Objects.hash(maHD);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHD, other.maHD);
	}

	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", khachHang=" + khachHang + ", nvLap=" + nvLap + ", ngayGioLap=" + ngayGioLap
				+ ", trangThaiTT=" + trangThaiTT + ", phuongThucTT=" + phuongThucTT + ", tongTien=" + tongTien + "]";
	}

	public List<ChiTietHoaDon> getDsChiTiet() {
	    return dsChiTiet;
	}
	

}
