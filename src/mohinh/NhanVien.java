package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tienich.NhapChuan;

public final class NhanVien {
    String ma;
    String ho;
    String ten;
    String chucVu;
    String soDienThoai;
    String gioiTinh;
    double luongCoBan;
    double tongTienDaBan;

    public NhanVien(String ma, String ho, String ten, String chucVu, String soDienThoai, String gioiTinh, double luongCoBan, double tongCongBan) {
        this.ma = ma;
        this.ho = ho;
        this.ten = ten;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
        this.luongCoBan = luongCoBan;
        this.tongTienDaBan = tongCongBan;
    }
    public NhanVien(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co", "nam", 0, 0);
    }
    public NhanVien(String ma, NhanVien nv) {
        this(ma, nv.ho, nv.ten, nv.chucVu, nv.soDienThoai, nv.gioiTinh, nv.luongCoBan, nv.tongTienDaBan);
    }
    public NhanVien(NhanVien nv) { this(nv.ma, nv); }
    public NhanVien saoChep() {
        return new NhanVien(this);
    }
    public NhanVien saoChepVoiMaMoi(String maMoi) {
        return new NhanVien(maMoi, this);
    }

    public void nhap() {
        this.ho = NhapChuan.docChuoiKhongRong("Nhap ho nhan vien");
        this.ten = NhapChuan.docChuoiKhongRong("Nhap ten nhan vien");
        this.chucVu = NhapChuan.docChuoiKhongRong("Nhap chuc vu");
        this.soDienThoai = NhapChuan.docChuoiKhongRong("Nhap so dien thoai");
        this.gioiTinh = NhapChuan.docChuoiKhongRong("Nhap gioi tinh");
        this.luongCoBan = NhapChuan.docSoThucKhongAm("Nhap luong co ban");
        this.tongTienDaBan = NhapChuan.docSoThucKhongAm("Nhap tong tien da ban");
    }

    public void xuat() {
        System.out.printf("Ma NV: %s | Ho ten: %s %s | Chuc vu: %s | SDT: %s | Gioi tinh: %s | Luong CB: %.2f | Tong ban: %.2f\n", 
            ma, ho, ten, chucVu, soDienThoai, gioiTinh, luongCoBan, tongTienDaBan);
    }

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

    public void docFile(DataInputStream dis) throws IOException {
        this.ma = dis.readUTF();
        this.ho = dis.readUTF();
        this.ten = dis.readUTF();
        this.chucVu = dis.readUTF();
        this.soDienThoai = dis.readUTF();
        this.gioiTinh = dis.readUTF();
        this.luongCoBan = dis.readDouble();
        this.tongTienDaBan = dis.readDouble();
    }

    public String getMa() {return ma;}
    public String getHo() {return ho;}
    public String getTen() {return ten;}
    public String getChucVu() {return chucVu;}
    public String getSoDienThoai() {return soDienThoai;}
    public String getGioiTinh() {return gioiTinh;}
    public double getLuongCoBan() {return luongCoBan;}
    public double getTongTienDaBan() {return tongTienDaBan;}

    public boolean setMa(String maMoi) {
        if(maMoi == null || maMoi.trim().isEmpty()) return false;
        this.ma = maMoi;
        return true;
    }
    public boolean setHo(String hoMoi) {
        if(hoMoi == null || hoMoi.trim().isEmpty()) return false;
        this.ho = hoMoi;
        return true;
    }
    public boolean setTen(String tenMoi) {
        if(tenMoi == null || tenMoi.trim().isEmpty()) return false;
        this.ten = tenMoi;
        return true;
    }
    public boolean setChucVu(String chucVuMoi) {
        if(chucVuMoi == null || chucVuMoi.trim().isEmpty()) return false;
        this.chucVu = chucVuMoi;
        return true;
    }
    public boolean setSoDienThoai(String soDienThoaiMoi) {
        if(soDienThoaiMoi == null || soDienThoaiMoi.trim().isEmpty()) return false;
        this.soDienThoai = soDienThoaiMoi;
        return true;
    }
    public boolean setGioiTinh(String gioiTinhMoi) {
        if(gioiTinhMoi == null || gioiTinhMoi.trim().isEmpty()) return false;
        this.gioiTinh = gioiTinhMoi;
        return true;
    }
    public boolean setLuongCoBan(double luongCoBanMoi) {
        if(luongCoBanMoi < 0) return false;
        this.luongCoBan = luongCoBanMoi;
        return true;
    }
    public boolean setTongTienDaBan(double tongTienMoi) {
        if(tongTienMoi < 0) return false;
        this.tongTienDaBan = tongTienMoi;
        return true;
    }
}