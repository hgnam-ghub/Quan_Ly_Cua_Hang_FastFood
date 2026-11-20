package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import tienich.NhapChuan;

public final class NguyenLieu {
    private String ma;
    private String ten;
    private String donVi;
    private double donGia;
    private int soLuong;
    private LocalDate ngayNhap;

    public NguyenLieu(String ma, String ten, String donVi, double donGia, int soLuong, LocalDate ngayNhap) {
        this.ma = ma;
        this.ten = ten;
        this.donVi = donVi;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.ngayNhap = (ngayNhap != null) ? ngayNhap : LocalDate.now();
    }

    public NguyenLieu(String ma) {
        this(ma, "Khong co", "N/A", 0, 0, LocalDate.now());
    }

    public NguyenLieu(String ma, NguyenLieu nguyenLieu) {
        this(ma, nguyenLieu.ten, nguyenLieu.donVi, nguyenLieu.donGia, nguyenLieu.soLuong, nguyenLieu.ngayNhap);
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
        this.ngayNhap = NhanVien.docNgay("Nhap ngay nhap (dd/MM/yyyy)");
    }

    public void xuat() {
        System.out.printf("Ma NL: %s | Ten: %s | Don vi: %s | Don gia: %.2f | So luong: %d | Ngay nhap: %s%n",
            ma, ten, donVi, donGia, soLuong, NhanVien.dinhDangNgay(ngayNhap));
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma != null ? ma : "");
        dos.writeUTF(ten != null ? ten : "");
        dos.writeUTF(donVi != null ? donVi : "");
        dos.writeDouble(donGia);
        dos.writeInt(soLuong);
        LocalDate ngayGhi = (ngayNhap != null) ? ngayNhap : LocalDate.now();
        dos.writeLong(ngayGhi.toEpochDay());
    }

    public void docFile(DataInputStream dis) throws IOException {
        this.ma = dis.readUTF();
        this.ten = dis.readUTF();
        this.donVi = dis.readUTF();
        this.donGia = dis.readDouble();
        this.soLuong = dis.readInt();
        this.ngayNhap = LocalDate.ofEpochDay(dis.readLong());
    }

    public String getMa() { return ma; }
    public String getTen() { return ten; }
    public String getDonVi() { return donVi; }
    public double getDonGia() { return donGia; }
    public int getSoLuong() { return soLuong; }
    public LocalDate getNgayNhap() { return ngayNhap; }

    public boolean setMa(String maMoi) {
        if (maMoi == null || maMoi.trim().isEmpty()) return false;
        this.ma = maMoi.trim();
        return true;
    }

    public boolean setTen(String tenMoi) {
        if (tenMoi == null || tenMoi.trim().isEmpty()) return false;
        this.ten = tenMoi.trim();
        return true;
    }

    public boolean setDonVi(String donViMoi) {
        if (donViMoi == null || donViMoi.trim().isEmpty()) return false;
        this.donVi = donViMoi.trim();
        return true;
    }

    public boolean setDonGia(double donGiaMoi) {
        if (donGiaMoi < 0) return false;
        this.donGia = donGiaMoi;
        return true;
    }

    public boolean setSoLuong(int soLuongMoi) {
        if (soLuongMoi < 0) return false;
        this.soLuong = soLuongMoi;
        return true;
    }

    public boolean setNgayNhap(LocalDate ngayNhapMoi) {
        if (ngayNhapMoi == null) return false;
        this.ngayNhap = ngayNhapMoi;
        return true;
    }

    public boolean them(int luongThem) {
        return setSoLuong(soLuong + luongThem);
    }

    public boolean giam(int luongGiam) {
        return setSoLuong(soLuong - luongGiam);
    }
}