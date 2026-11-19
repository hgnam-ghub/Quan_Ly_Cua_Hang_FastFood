package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public final class NhanVien implements ITruyXuatMoHinh {
    private String ma;
    private String ho;
    private String ten;
    private String chucVu;
    private String soDienThoai;
    private String gioiTinh;
    private double luongCoBan;
    private double tongTienDaBan;

    public NhanVien() {
        this("", "khong co", "khong co", "khong co", "khong co", "nam", 0, 0);
    }

    public NhanVien(String ma, String ho, String ten, String chucVu, String soDienThoai, String gioiTinh, double luongCoBan, double tongCongBan) {
        if (setMa(ma) == null) this.ma = "khong hop le";
        if (setHo(ho) == null) this.ho = "khong co";
        if (setTen(ten) == null) this.ten = "khong co";
        if (setChucVu(chucVu) == null) this.chucVu = "khong co";
        if (setSoDienThoai(soDienThoai) == null) this.soDienThoai = "khong co";
        if (setGioiTinh(gioiTinh) == null) this.gioiTinh = "nam";
        if (setLuongCoBan(luongCoBan) == null) this.luongCoBan = 0;
        if (setTongTienDaBan(tongCongBan) == null) this.tongTienDaBan = 0;
    }

    public NhanVien(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co", "nam", 0, 0);
    }

    public NhanVien(String ma, NhanVien nv) {
        this(ma, nv.ho, nv.ten, nv.chucVu, nv.soDienThoai, nv.gioiTinh, nv.luongCoBan, nv.tongTienDaBan);
    }

    public NhanVien(NhanVien nv) {
        this(nv.ma, nv);
    }

    public NhanVien saoChep() {
        return new NhanVien(this);
    }

    public NhanVien saoChepVoiMaMoi(String maMoi) {
        return new NhanVien(maMoi, this);
    }

    @Override
    public String[] xuat() {
        return new String[]{
            ma,
            ho,
            ten,
            chucVu,
            soDienThoai,
            gioiTinh,
            String.valueOf(luongCoBan),
            String.valueOf(tongTienDaBan)
        };
    }

    @Override
    public NhanVien nhap() {
        setHo(NhapChuan.docChuoiKhongRong("Nhap ho nhan vien"));
        setTen(NhapChuan.docChuoiKhongRong("Nhap ten nhan vien"));
        setChucVu(NhapChuan.docChuoiKhongRong("Nhap chuc vu"));
        setSoDienThoai(NhapChuan.docChuoiKhongRong("Nhap so dien thoai"));
        setGioiTinh(NhapChuan.docChuoiKhongRong("Nhap gioi tinh"));
        setLuongCoBan(NhapChuan.docSoThucKhongAm("Nhap luong co ban"));
        setTongTienDaBan(NhapChuan.docSoThucKhongAm("Nhap tong tien da ban"));
        return this;
    }

    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma);
        dos.writeUTF(ho);
        dos.writeUTF(ten);
        dos.writeUTF(chucVu);
        dos.writeUTF(soDienThoai);
        dos.writeUTF(gioiTinh);
        dos.writeDouble(luongCoBan);
        dos.writeDouble(tongTienDaBan);
    }

    @Override
    public void docFile(DataInputStream dis) throws IOException {
        setMa(dis.readUTF());
        setHo(dis.readUTF());
        setTen(dis.readUTF());
        setChucVu(dis.readUTF());
        setSoDienThoai(dis.readUTF());
        setGioiTinh(dis.readUTF());
        setLuongCoBan(dis.readDouble());
        setTongTienDaBan(dis.readDouble());
    }

    public final String getMa() { return ma; }
    public final String getHo() { return ho; }
    public final String getTen() { return ten; }
    public final String getChucVu() { return chucVu; }
    public final String getSoDienThoai() { return soDienThoai; }
    public final String getGioiTinh() { return gioiTinh; }
    public final double getLuongCoBan() { return luongCoBan; }
    public final double getTongTienDaBan() { return tongTienDaBan; }

    public final NhanVien setMa(String maMoi) {
        if (maMoi == null || maMoi.trim().isEmpty()) return null;
        this.ma = maMoi;
        return this;
    }

    public final NhanVien setHo(String hoMoi) {
        if (hoMoi == null || hoMoi.trim().isEmpty()) return null;
        this.ho = hoMoi;
        return this;
    }

    public final NhanVien setTen(String tenMoi) {
        if (tenMoi == null || tenMoi.trim().isEmpty()) return null;
        this.ten = tenMoi;
        return this;
    }

    public final NhanVien setChucVu(String chucVuMoi) {
        if (chucVuMoi == null || chucVuMoi.trim().isEmpty()) return null;
        this.chucVu = chucVuMoi;
        return this;
    }

    public final NhanVien setSoDienThoai(String soDienThoaiMoi) {
        if (soDienThoaiMoi == null || soDienThoaiMoi.trim().isEmpty()) return null;
        this.soDienThoai = soDienThoaiMoi;
        return this;
    }

    public final NhanVien setGioiTinh(String gioiTinhMoi) {
        if (gioiTinhMoi == null || gioiTinhMoi.trim().isEmpty()) return null;
        this.gioiTinh = gioiTinhMoi;
        return this;
    }

    public final NhanVien setLuongCoBan(double luongCoBanMoi) {
        if (luongCoBanMoi < 0) return null;
        this.luongCoBan = luongCoBanMoi;
        return this;
    }

    public final NhanVien setTongTienDaBan(double tongTienMoi) {
        if (tongTienMoi < 0) return null;
        this.tongTienDaBan = tongTienMoi;
        return this;
    }
}