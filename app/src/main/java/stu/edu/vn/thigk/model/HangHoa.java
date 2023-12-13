package stu.edu.vn.thigk.model;

import java.io.Serializable;

public class HangHoa implements Serializable {
    private String maHang,tenHang;
    private String Tenloai;
    private String hinhanh;
    private   double gia,dungtich;

    public HangHoa() {
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getTenloai() {
        return Tenloai;
    }

    public void setLoaiHangHoa(String tenLoai) {
        this.Tenloai = tenLoai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public double getDungtich() {
        return dungtich;
    }

    public void setDungtich(double dungtich) {
        this.dungtich = dungtich;
    }

    public HangHoa(String maHang, String tenHang, String Tenloai, String hinhanh, double gia, double dungtich) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.Tenloai = Tenloai;
        this.hinhanh = hinhanh;
        this.gia = gia;
        this.dungtich = dungtich;
    }
}
