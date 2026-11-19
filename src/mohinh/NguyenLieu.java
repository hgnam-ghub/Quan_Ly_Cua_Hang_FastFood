package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public final class NguyenLieu implements ITruyXuatMoHinh {
    private String ma;
    private String ten;
    private String donVi;
    private double donGia;
    private int soLuong;

    public NguyenLieu() {
        this("", "khong co", "N/A", 0, 0);
    }

    public NguyenLieu(String ma, String ten, String donVi, double donGia, int soLuong) {
        if (setMa(ma) == null) this.ma = "khong hop le";
        if (setTen(ten) == null) this.ten = "khong co";
        if (setDonVi(donVi) == null) this.donVi = "N/A";
        if (setDonGia(donGia) == null) this.donGia = 0;
        if (setSoLuong(soLuong) == null) this.soLuong = 0;
    }

    public NguyenLieu(String ma) {
        this(ma, "khong co", "N/A", 0, 0);
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

    @Override
    public String[] xuat() {
        return new String[]{
            ma,
            ten,
            donVi,
            String.valueOf(donGia),
            String.valueOf(soLuong)
        };
    }

    @Override
    public NguyenLieu nhap() {
        setTen(NhapChuan.docChuoiKhongRong("Nhap ten nguyen lieu"));
        setDonVi(NhapChuan.docChuoiKhongRong("Nhap don vi tinh"));
        setDonGia(NhapChuan.docSoThucKhongAm("Nhap don gia"));
        setSoLuong(NhapChuan.docSoNguyenKhongAm("Nhap so luong ban dau"));
        return this;
    }

    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma);
        dos.writeUTF(ten);
        dos.writeUTF(donVi);
        dos.writeDouble(donGia);
        dos.writeInt(soLuong);
    }

    @Override
    public void docFile(DataInputStream dis) throws IOException {
        setMa(dis.readUTF());
        setTen(dis.readUTF());
        setDonVi(dis.readUTF());
        setDonGia(dis.readDouble());
        setSoLuong(dis.readInt());
    }

    public final String getMa() { return ma; }
    public final String getTen() { return ten; }
    public final String getDonVi() { return donVi; }
    public final double getDonGia() { return donGia; }
    public final int getSoLuong() { return soLuong; }

    public final NguyenLieu setMa(String maMoi) {
        if (maMoi == null || maMoi.trim().isEmpty()) return null;
        this.ma = maMoi;
        return this;
    }

    public final NguyenLieu setTen(String tenMoi) {
        if (tenMoi == null || tenMoi.trim().isEmpty()) return null;
        this.ten = tenMoi;
        return this;
    }

    public final NguyenLieu setDonVi(String donViMoi) {
        if (donViMoi == null || donViMoi.trim().isEmpty()) return null;
        this.donVi = donViMoi;
        return this;
    }

    public final NguyenLieu setDonGia(double donGiaMoi) {
        if (donGiaMoi < 0) return null;
        this.donGia = donGiaMoi;
        return this;
    }

    public final NguyenLieu setSoLuong(int soLuongMoi) {
        if (soLuongMoi < 0) return null;
        this.soLuong = soLuongMoi;
        return this;
    }

    public final NguyenLieu them(int luongThem) {
        return setSoLuong(soLuong + luongThem);
    }

    public final NguyenLieu giam(int luongGiam) {
        return setSoLuong(soLuong - luongGiam);
    }
}