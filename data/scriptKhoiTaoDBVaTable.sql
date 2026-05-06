-- 1. Tạo database nếu chưa tồn tại
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'COFFEESHOP')
BEGIN
    CREATE DATABASE COFFEESHOP;
END
GO

-- 2. Chuyển sang sử dụng database COFFEESHOP
USE COFFEESHOP;
GO

-- 3. Tạo các bảng (Giữ nguyên cấu trúc của bạn)
CREATE TABLE TaiKhoan (
    maTaiKhoan VARCHAR(50) PRIMARY KEY,
    tenTaiKhoan NVARCHAR(100) NOT NULL UNIQUE,
    matKhau NVARCHAR(255) NOT NULL,
    trangThaiHoatDong BIT 
);

CREATE TABLE Mon (
    maMon VARCHAR(50) PRIMARY KEY,
    tenMon NVARCHAR(255) NOT NULL,
    moTaMon NVARCHAR(MAX),
    donGiaMua FLOAT,
    donGiaBan FLOAT,
    duongDanAnh NVARCHAR(500),
    trangThai BIT, 
    phanLoaiMonAn NVARCHAR(50) 
        CHECK (phanLoaiMonAn IN ('PASTRY', 'TEA', 'COFFEE'))
);

CREATE TABLE HoaDon (
    maHD VARCHAR(50) PRIMARY KEY,
    maTaiKhoanLap VARCHAR(50), 
    ngayGioLap DATE, 
    trangThaiTT BIT,
    phuongThucTT NVARCHAR(50)
        CHECK (phuongThucTT IN ('TIENMAT', 'CHUYENKHOAN')), 
    tongTien FLOAT,
    CONSTRAINT FK_HoaDon_TaiKhoan FOREIGN KEY (maTaiKhoanLap) REFERENCES TaiKhoan(maTaiKhoan)
);

CREATE TABLE ChiTietHoaDon (
    maCTHD VARCHAR(50) PRIMARY KEY,
    maHD VARCHAR(50),
    maMon VARCHAR(50),
    soLuongMon INT,
    ghiChuKhachHang NVARCHAR(MAX),
    thanhTien FLOAT,
    CONSTRAINT FK_CTHD_HoaDon FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
    CONSTRAINT FK_CTHD_Mon FOREIGN KEY (maMon) REFERENCES Mon(maMon)
);
GO
