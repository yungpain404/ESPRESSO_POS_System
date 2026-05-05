package dao;

import connectDB.ConnectDB;
import entity.Mon;
import entity.PhanLoaiMonAn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mon_DAO {

    public List<Mon> getAll() {
        List<Mon> dsMon = new ArrayList<>();
        String sql = "SELECT * FROM Mon";
        try (Connection con = ConnectDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Mon m = new Mon(
                    rs.getString("maMon"),
                    rs.getNString("tenMon"),
                    rs.getDouble("donGiaMua"),
                    rs.getDouble("donGiaBan"),
                    rs.getBoolean("trangThai"),
                    PhanLoaiMonAn.valueOf(rs.getString("phanLoaiMonAn")),
                    rs.getNString("moTaMon"),
                    rs.getString("duongDanAnh")
                );
                dsMon.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsMon;
    }

    public Mon getMon(String maMon) {
        Mon mon = null;
        String sql = "SELECT * FROM Mon WHERE maMon = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maMon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mon = new Mon(
                        rs.getString("maMon"),
                        rs.getNString("tenMon"),
                        rs.getDouble("donGiaMua"),
                        rs.getDouble("donGiaBan"),
                        rs.getBoolean("trangThai"),
                        PhanLoaiMonAn.valueOf(rs.getString("phanLoaiMonAn")),
                        rs.getNString("moTaMon"),
                        rs.getString("duongDanAnh")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mon;
    }

    public boolean addMon(Mon mon) {
        String sql = "INSERT INTO Mon VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mon.getMaMon());
            ps.setNString(2, mon.getTenMon());
            ps.setNString(3, mon.getMoTaMon());
            ps.setDouble(4, mon.getDonGiaMua());
            ps.setDouble(5, mon.getDonGiaBan());
            ps.setString(6, mon.getDuongDanAnh());
            ps.setBoolean(7, mon.isTrangThai());
            ps.setString(8, mon.getPhanLoaiMonAn().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMon(Mon mon) {
        String sql = "UPDATE Mon SET tenMon=?, moTaMon=?, donGiaMua=?, donGiaBan=?, duongDanAnh=?, trangThai=?, phanLoaiMonAn=? WHERE maMon=?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setNString(1, mon.getTenMon());
            ps.setNString(2, mon.getMoTaMon());
            ps.setDouble(3, mon.getDonGiaMua());
            ps.setDouble(4, mon.getDonGiaBan());
            ps.setString(5, mon.getDuongDanAnh());
            ps.setBoolean(6, mon.isTrangThai());
            ps.setString(7, mon.getPhanLoaiMonAn().name());
            ps.setString(8, mon.getMaMon());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMonById(String maMon) {
        String sql = "DELETE FROM Mon WHERE maMon = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}