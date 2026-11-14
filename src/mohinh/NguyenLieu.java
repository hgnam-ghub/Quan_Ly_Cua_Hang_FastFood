package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public class NguyenLieu implements ITruyXuatMoHinh {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String donViTinh;
    private double soLuongTon;
    private double giaTien;
    private String maNhaCungCap;

    public NguyenLieu() {
        this("", "", "", 0, 0, "");
    }

    public NguyenLieu(String maNguyenLieu, String tenNguyenLieu, String donViTinh, double soLuongTon, double giaTien, String maNhaCungCap) {
        if(setMaNguyenLieu(maNguyenLieu) == null) this.maNguyenLieu = "khong hop le";
        if(setTenNguyenLieu(tenNguyenLieu) == null) this.tenNguyenLieu = "khong co";
        if(setDonViTinh(donViTinh) == null) this.donViTinh = "khong co";
        if(setSoLuongTon(soLuongTon) == null) this.soLuongTon = 0;
        if(setGiaTien(giaTien) == null) this.giaTien = 0;
        if(setMaNhaCungCap(maNhaCungCap) == null) this.maNhaCungCap = "khong co";
    }

    public NguyenLieu(NguyenLieu nl) {
        this(nl.maNguyenLieu, nl.tenNguyenLieu, nl.donViTinh, nl.soLuongTon, nl.giaTien, nl.maNhaCungCap);
    }

    public NguyenLieu saoChep() {
        return new NguyenLieu(this);
    }

    public String[] xuat() {
        String[] truongDuLieu = new String[6];
        truongDuLieu[0] = maNguyenLieu;
        truongDuLieu[1] = tenNguyenLieu;
        truongDuLieu[2] = donViTinh;
        truongDuLieu[3] = String.valueOf(soLuongTon);
        truongDuLieu[4] = String.valueOf(giaTien);
        truongDuLieu[5] = maNhaCungCap;
        return truongDuLieu;
    }

    public NguyenLieu nhap() {
        setTenNguyenLieu(NhapChuan.docChuoiKhongRong("Ten nguyen lieu"));
        setDonViTinh(NhapChuan.docChuoiKhongRong("Don vi tinh"));
        setSoLuongTon(NhapChuan.docSoNguyenKhongAm("So luong ton"));
        setGiaTien(NhapChuan.docSoNguyen("Gia tien"));
        setMaNhaCungCap(NhapChuan.docChuoiKhongRong("Ma nha cung cap"));
        return this;
    }

    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maNguyenLieu);
        dos.writeUTF(tenNguyenLieu);
        dos.writeUTF(donViTinh);
        dos.writeDouble(soLuongTon);
        dos.writeDouble(giaTien);
        dos.writeUTF(maNhaCungCap);
    }

    @Override
    public void docFile(DataInputStream dis) throws IOException {
        tenNguyenLieu = dis.readUTF();
        donViTinh = dis.readUTF();
        soLuongTon = dis.readDouble();
        giaTien = dis.readDouble();
        maNhaCungCap = dis.readUTF();
    }

    public final String getMaNguyenLieu() { return maNguyenLieu; }
    public final String getTenNguyenLieu() { return tenNguyenLieu; }
    public final String getDonViTinh() { return donViTinh; }
    public final double getSoLuongTon() { return soLuongTon; }
    public final double getGiaTien() { return giaTien; }
    public final String getMaNhaCungCap() { return maNhaCungCap; }

    public final NguyenLieu setMaNguyenLieu(String maMoi) {
        if(maMoi.isEmpty()) return null;
        maNguyenLieu = maMoi;
        return this;
    }

    public final NguyenLieu setTenNguyenLieu(String tenMoi) {
        if(tenMoi.isEmpty()) return null;
        tenNguyenLieu = tenMoi;
        return this;
    }

    public final NguyenLieu setDonViTinh(String donViMoi) {
        if(donViMoi.isEmpty()) return null;
        donViTinh = donViMoi;
        return this;
    }

    public final NguyenLieu setSoLuongTon(double soLuongMoi) {
        if(soLuongMoi < 0) return null;
        soLuongTon = soLuongMoi;
        return this;
    }

    public final NguyenLieu setGiaTien(double giaTienMoi) {
        if(giaTienMoi < 0) return null;
        giaTien = giaTienMoi;
        return this;
    }

    public final NguyenLieu setMaNhaCungCap(String maNccMoi) {
        if(maNccMoi.isEmpty()) return null;
        maNhaCungCap = maNccMoi;
        return this;
    }

    public final NguyenLieu thayDoiSoLuongTon(double luongThayDoi) {
        return setSoLuongTon(soLuongTon + luongThayDoi);
    }
}