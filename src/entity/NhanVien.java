package entity;

import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
	private String maNV;
	private String hoTen;
	private LocalDate ngayVaoLam;
	private String diaChi;
	private String soDienThoai;
	private TaiKhoan taiKhoan;
	
	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public LocalDate getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setNgayVaoLam(LocalDate ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public NhanVien() {
		
	}

	public NhanVien(String maNV, String hoTen, LocalDate ngayVaoLam, String diaChi, String soDienThoai,
			TaiKhoan taiKhoan) {
		setMaNV(maNV);
		setHoTen(hoTen);
		setNgayVaoLam(ngayVaoLam);
		setDiaChi(diaChi);
		setSoDienThoai(soDienThoai);
		setTaiKhoan(taiKhoan);
	}

	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}

	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", hoTen=" + hoTen + ", ngayVaoLam=" + ngayVaoLam + ", diaChi=" + diaChi
				+ ", soDienThoai=" + soDienThoai + ", taiKhoan=" + taiKhoan + "]";
	}
	
}
