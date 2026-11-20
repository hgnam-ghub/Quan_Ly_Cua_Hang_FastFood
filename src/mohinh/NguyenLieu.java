package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tienich.NhapChuan;

public final class NguyenLieu {
    private String ma;
    private String ten;
    private String donVi;
    private double donGia;
    private int soLuong;

    public NguyenLieu(String ma, String ten, String donVi, double donGia, int soLuong) {
        this.ma = ma;
        this.ten = ten;
        this.donVi = donVi;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }
    public NguyenLieu(String ma) {
        this(ma, "Khong co", "N/A", 0, 0);
    }
    public NguyenLieu(String ma, NguyenLieu nguyenLieu) {
        this(ma, nguyenLieu.ten, nguyenLieu.donVi, nguyenLieu.donGia, nguyenLieu.soLuong);
    }
    public NguyenLieu(NguyenLieu nguyenLieu) {
        this(nguyenLieu.ma, nguyenLieu);
    }
    public NguyenLieu saoChep() {
        return new NguyenLieu(this);
    }
    public NguyenLieu saoChepVoiMaMoi(String maMoi) {
        return new NguyenLieu(maMoi, this);
    }

    public void nhap() {
        this.ten = NhapChuan.docChuoiKhongRong("Nhap ten nguyen lieu");
        this.donVi = NhapChuan.docChuoiKhongRong("Nhap don vi tinh");
        this.donGia = NhapChuan.docSoThucKhongAm("Nhap don gia");
        this.soLuong = NhapChuan.docSoNguyenKhongAm("Nhap so luong ban dau");
    }

    public void xuat() {
        System.out.printf("Ma NL: %s | Ten: %s | Don vi: %s | Don gia: %.2f | So luong: %d\n", 
            ma, ten, donVi, donGia, soLuong);
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma);
        dos.writeUTF(ten);
        dos.writeUTF(donVi);
        dos.writeDouble(donGia);
        dos.writeInt(soLuong);
    }

    public void docFile(DataInputStream dis) throws IOException {
        this.ma = dis.readUTF();
        this.ten = dis.readUTF();
        this.donVi = dis.readUTF();
        this.donGia = dis.readDouble();
        this.soLuong = dis.readInt();
    }

    public String getMa() { return ma; }
    public String getTen() { return ten; }
    public String getDonVi() { return donVi; }
    public double getDonGia() { return donGia; }
    public int getSoLuong() { return soLuong; }

    public boolean setTen(String tenMoi) { 
        if(tenMoi == null || tenMoi.trim().isEmpty()) return false;
        this.ten = tenMoi;
        return true; 
    }
    public boolean setDonVi(String donViMoi) { 
        if(donViMoi == null || donViMoi.trim().isEmpty()) return false;
        this.donVi = donViMoi;
        return true; 
    }
    public boolean setDonGia(double donGiaMoi) { 
        if(donGiaMoi < 0) return false;
        this.donGia = donGiaMoi;
        return true; 
    }
    public boolean setSoLuong(int soLuongMoi) {
        if(soLuongMoi < 0) return false;
        soLuong = soLuongMoi;
        return true;
    }
    public boolean them(int luongThem) {
        return setSoLuong(soLuong + luongThem);
    }
    public boolean giam(int luongGiam) {
        return setSoLuong(soLuong - luongGiam);
    }
}
