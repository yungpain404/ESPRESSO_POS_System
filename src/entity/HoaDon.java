package entity;

import java.time.LocalDate;
import java.util.*;

public class HoaDon {
    private String maHD;
    private TaiKhoan taiKhoanLap;
    private LocalDate ngayGioLap;
    private boolean trangThaiTT;
    private PhuongThucThanhToan phuongThucTT;
    private double tongTien;
    private List<ChiTietHoaDon> dsChiTiet = new ArrayList<>();

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public LocalDate getNgayGioLap() { return ngayGioLap; }
    public void setNgayGioLap(LocalDate ngayGioLap) { this.ngayGioLap = ngayGioLap; }

    public boolean isTrangThaiTT() { return trangThaiTT; }
    public void setTrangThaiTT(boolean trangThaiTT) { this.trangThaiTT = trangThaiTT; }

    public PhuongThucThanhToan getPhuongThucTT() { return phuongThucTT; }
    public void setPhuongThucTT(PhuongThucThanhToan phuongThucTT) { this.phuongThucTT = phuongThucTT; }

    public double getTongTien() { return tongTien; }
    public void setTongTien() {
        double tong = 0;
        for (ChiTietHoaDon ct : dsChiTiet) {
            tong += ct.getThanhTien();
        }
        this.tongTien = tong;
    }
   

    public TaiKhoan getTaiKhoanLap() {
		return taiKhoanLap;
	}
	public void setTaiKhoanLap(TaiKhoan taiKhoanLap) {
		this.taiKhoanLap = taiKhoanLap;
	}
	public List<ChiTietHoaDon> getDsChiTiet() { return dsChiTiet; }
    public void setDsChiTiet(List<ChiTietHoaDon> dsChiTiet) { this.dsChiTiet = dsChiTiet; }

    public HoaDon() {}

    public HoaDon(String maHD, TaiKhoan taiKhoanLap, LocalDate ngayGioLap,
                  boolean trangThaiTT, PhuongThucThanhToan phuongThucTT) {
        setMaHD(maHD);
        setNgayGioLap(ngayGioLap);
        setTaiKhoanLap(taiKhoanLap);
        setTrangThaiTT(trangThaiTT);
        setPhuongThucTT(phuongThucTT);
        setTongTien();
    }

    @Override
    public int hashCode() { return Objects.hash(maHD); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HoaDon other = (HoaDon) obj;
        return Objects.equals(maHD, other.maHD);
    }
	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", taiKhoanLap=" + taiKhoanLap + ", ngayGioLap=" + ngayGioLap + ", trangThaiTT="
				+ trangThaiTT + ", phuongThucTT=" + phuongThucTT + ", tongTien=" + tongTien + ", dsChiTiet=" + dsChiTiet
				+ "]";
	}

   
}