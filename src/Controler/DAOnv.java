package Controler;

import Model.Nhanvien;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOnv {

    private Statement stm;
    private Connection conn;

    public DAOnv() {
        var server = "PHANTHANHTAN\\SQLEXPRESS";
        var user = "sa";
        var password = "123456789";
        var db = "qlnv";
        var port = 1433;

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setServerName(server);
        ds.setPortNumber(   port);
        ds.setTrustServerCertificate(true);

        try {
            conn = ds.getConnection();
            System.out.println("Kết nối với SQL Server thành công!");
            System.out.println("Database hiện tại: " + conn.getCatalog());
            stm = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Phương thức để lấy danh sách nhân viên từ cơ sở dữ liệu
    public List<Nhanvien> getListNV() {
        List<Nhanvien> listNV = new ArrayList<>();
        try {
            // Thực hiện truy vấn để lấy dữ liệu từ bảng nhanvien
            String selectQuery = "SELECT * FROM nhanvien";
            ResultSet rs = stm.executeQuery(selectQuery);
            // Lặp qua kết quả và tạo đối tượng Nhanvien từ mỗi hàng
            while (rs.next()) {
                int id = rs.getInt("id");
                String Manv = rs.getString("Manv");
                String tennv = rs.getString("tennv");
                String Chucvu = rs.getString("Chucvu");
                String Date = rs.getString("Date");
                String Gt = rs.getString("Gt");
                String Diachi = rs.getString("Diachi");
                String SDT = rs.getString("SDT");
                String Email = rs.getString("Email");
                int Luong = rs.getInt("Luong");
                // Tạo đối tượng Nhanvien và thêm vào danh sách
                Nhanvien nv = new Nhanvien(id, Manv, tennv, Chucvu, Date, Gt, Diachi, SDT, Email, Luong);
                listNV.add(nv);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listNV;
    }

    public void AddNV(Nhanvien n) {
        try {
            String sql = "INSERT INTO nhanvien (Manv, tennv, Chucvu, Date, Gt, Diachi, SDT, Email, Luong) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, n.getManv());
            ps.setString(2, n.getTennv());
            ps.setString(3, n.getChucvu());
            ps.setString(4, n.getDate());
            ps.setString(5, n.getGt());
            ps.setString(6, n.getDiachi());
            ps.setString(7, n.getSDT());
            ps.setString(8, n.getEmail());
            ps.setInt(9, n.getLuong());
            ps.executeUpdate();
            System.out.println("Thêm nhân viên thành công");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void DeleteNV(int id) {
        try {
            String sql = "DELETE FROM nhanvien WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Xóa nhân viên thành công");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Nhanvien> FindbyName(String Name) {
        ArrayList<Nhanvien> listNV = new ArrayList<>();
        try {
            String sql = "SELECT * FROM nhanvien WHERE tennv LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + Name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String Manv = rs.getString("Manv");
                String tennv = rs.getString("tennv");
                String Chucvu = rs.getString("Chucvu");
                String Date = rs.getString("Date");
                String Gt = rs.getString("Gt");
                String Diachi = rs.getString("Diachi");
                String SDT = rs.getString("SDT");
                String Email = rs.getString("Email");
                int Luong = rs.getInt("Luong");
                Nhanvien nv = new Nhanvien(id, Manv, tennv, Chucvu, Date, Gt, Diachi, SDT, Email, Luong);
                listNV.add(nv);
            }
        } catch (SQLException ex) {
        }
        return listNV;
    }

    // Phương thức khác nếu cần
    public static void main(String[] args) {
        DAOnv dao = new DAOnv();
        // Lấy danh sách nhân viên từ cơ sở dữ liệu
        List<Nhanvien> listNV = dao.getListNV();
        // In thông tin của từng nhân viên trong danh sách
        for (Nhanvien nv : listNV) {
            System.out.println("ID: " + nv.getId() + ", Mã nhân viên: " + nv.getManv() + ", Tên nhân viên: " + nv.getTennv());
        }
    }
}
