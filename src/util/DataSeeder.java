package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import connectDB.ConnectDB;
import entity.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class DataSeeder {
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void main(String[] args) {
        try (Connection con = ConnectDB.getConnection()) {
            if (con == null) {
                System.out.println("Kết nối Database thất bại!");
                return;
            }
            System.out.println("Bắt đầu seed dữ liệu theo cấu trúc mới...");

            seedTaiKhoan(con);
            seedMon(con);
            seedHoaDon(con);
            seedChiTietHoaDon(con);

            System.out.println("Hoàn tất nạp dữ liệu mẫu!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void seedTaiKhoan(Connection con) throws Exception {
        List<TaiKhoan> list = mapper.readValue(new File("data/TaiKhoan.json"), new TypeReference<List<TaiKhoan>>(){});
        String sql = "INSERT INTO TaiKhoan (maTaiKhoan, tenTaiKhoan, matKhau, trangThaiHoatDong) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        for (TaiKhoan i : list) {
            ps.setString(1, i.getMaTaiKhoan());
            ps.setNString(2, i.getTenTaiKhoan());
            ps.setString(3, i.getMatKhau());
            ps.setBoolean(4, i.isTrangThaiHoatDong());
            ps.addBatch();
        }
        ps.executeBatch();
        System.out.println("- Seed TaiKhoan thành công.");
    }

    private static void seedMon(Connection con) throws Exception {
        List<Mon> list = mapper.readValue(new File("data/mon.json"), new TypeReference<List<Mon>>(){});
        String sql = "INSERT INTO Mon (maMon, tenMon, moTaMon, donGiaMua, donGiaBan, duongDanAnh, trangThai, phanLoaiMonAn) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        for (Mon i : list) {
            ps.setString(1, i.getMaMon());
            ps.setNString(2, i.getTenMon());
            ps.setNString(3, i.getMoTaMon());
            ps.setDouble(4, i.getDonGiaMua());
            ps.setDouble(5, i.getDonGiaBan());
            ps.setNString(6, i.getDuongDanAnh());
            ps.setBoolean(7, i.isTrangThai());
            ps.setString(8, i.getPhanLoaiMonAn().name());
            ps.addBatch();
        }
        ps.executeBatch();
        System.out.println("- Seed Mon thành công.");
    }

    private static void seedHoaDon(Connection con) throws Exception {
        List<HoaDon> list = mapper.readValue(new File("data/hoaDon.json"), new TypeReference<List<HoaDon>>(){});
        // Cập nhật SQL: Loại bỏ maKH, thay maNV bằng maTaiKhoanLap
        String sql = "INSERT INTO HoaDon (maHD, maTaiKhoanLap, ngayGioLap, trangThaiTT, phuongThucTT, tongTien) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        for (HoaDon i : list) {
            ps.setString(1, i.getMaHD());
            ps.setString(2, i.getTaiKhoanLap() != null ? i.getTaiKhoanLap().getMaTaiKhoan() : null);
            ps.setObject(3, i.getNgayGioLap());
            ps.setBoolean(4, i.isTrangThaiTT());
            ps.setString(5, i.getPhuongThucTT() != null ? i.getPhuongThucTT().name() : null);
            ps.setDouble(6, i.getTongTien());
            ps.addBatch();
        }
        ps.executeBatch();
        System.out.println("- Seed HoaDon thành công.");
    }

    private static void seedChiTietHoaDon(Connection con) throws Exception {
        List<ChiTietHoaDon> list = mapper.readValue(new File("data/chiTietHoaDon.json"), new TypeReference<List<ChiTietHoaDon>>(){});
        String sql = "INSERT INTO ChiTietHoaDon (maCTHD, maHD, maMon, soLuongMon, ghiChuKhachHang, thanhTien) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        for (ChiTietHoaDon i : list) {
            ps.setString(1, i.getMaCTHD());
            ps.setString(2, i.getHoaDon() != null ? i.getHoaDon().getMaHD() : null);
            ps.setString(3, i.getMon() != null ? i.getMon().getMaMon() : null);
            ps.setInt(4, i.getSoLuongMon());
            ps.setNString(5, i.getGhiChuKhachHang());
            ps.setDouble(6, i.getThanhTien());
            ps.addBatch();
        }
        ps.executeBatch();
        System.out.println("- Seed ChiTietHoaDon thành công.");
    }
}