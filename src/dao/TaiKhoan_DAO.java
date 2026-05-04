package dao;

import connectDB.ConnectDB;
import entity.TaiKhoan;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoan_DAO {

    public List<TaiKhoan> getAll() {
        List<TaiKhoan> dsTaiKhoan = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        try (Connection con = ConnectDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println(rs);
            while (rs.next()) {
                dsTaiKhoan.add(new TaiKhoan(
                    rs.getString("maTaiKhoan"),
                    rs.getString("tenTaiKhoan"),
                    rs.getString("matKhau"),
                    rs.getBoolean("trangThaiHoatDong")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsTaiKhoan;
    }

    public TaiKhoan kiemTraDangNhap(String tenDangNhap, String matKhauNhapVao) {
        String sql = "SELECT * FROM TaiKhoan WHERE tenTaiKhoan = ? AND trangThaiHoatDong = 1";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, tenDangNhap);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashed = rs.getString("matKhau");
                    if (BCrypt.checkpw(matKhauNhapVao, hashed)) {
                        return new TaiKhoan(
                            rs.getString("maTaiKhoan"),
                            rs.getString("tenTaiKhoan"),
                            hashed,
                            rs.getBoolean("trangThaiHoatDong")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}