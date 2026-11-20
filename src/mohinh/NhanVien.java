package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import tienich.NhapChuan;

public final class NhanVien {
    public static final DateTimeFormatter DINH_DANG_NGAY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String ma;
    private String ho;
    private String ten;
    private String chucVu;
    private String soDienThoai;
    private String gioiTinh;
    private double luongCoBan;
    private double tongTienDaBan;
    private LocalDate ngayVaoLam;

    public NhanVien(String ma, String ho, String ten, String chucVu, String soDienThoai,
                    String gioiTinh, double luongCoBan, double tongCongBan, LocalDate ngayVaoLam) {
        this.ma = ma;
        this.ho = ho;
        this.ten = ten;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
        this.luongCoBan = luongCoBan;
        this.tongTienDaBan = tongCongBan;
        this.ngayVaoLam = (ngayVaoLam != null) ? ngayVaoLam : LocalDate.now();
    }

    public NhanVien(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co", "nam", 0, 0, LocalDate.now());
    }

    public NhanVien(String ma, NhanVien nv) {
        this(ma, nv.ho, nv.ten, nv.chucVu, nv.soDienThoai, nv.gioiTinh, nv.luongCoBan, nv.tongTienDaBan, nv.ngayVaoLam);
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

    public void nhap() {
        this.ho = NhapChuan.docChuoiKhongRong("Nhap ho nhan vien");
        this.ten = NhapChuan.docChuoiKhongRong("Nhap ten nhan vien");
        this.chucVu = NhapChuan.docChuoiKhongRong("Nhap chuc vu");
        this.soDienThoai = NhapChuan.docChuoiKhongRong("Nhap so dien thoai");
        this.gioiTinh = NhapChuan.docChuoiKhongRong("Nhap gioi tinh");
        this.luongCoBan = NhapChuan.docSoThucKhongAm("Nhap luong co ban");
        this.tongTienDaBan = NhapChuan.docSoThucKhongAm("Nhap tong tien da ban");
        this.ngayVaoLam = docNgay("Nhap ngay vao lam (dd/MM/yyyy)");
    }

    public void xuat() {
        System.out.printf("Ma NV: %s | Ho ten: %s %s | Chuc vu: %s | SDT: %s | Gioi tinh: %s | Luong CB: %.2f | Tong ban: %.2f | Ngay VL: %s%n",
            ma, ho, ten, chucVu, soDienThoai, gioiTinh, luongCoBan, tongTienDaBan, dinhDangNgay(ngayVaoLam));
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma != null ? ma : "");
        dos.writeUTF(ho != null ? ho : "");
        dos.writeUTF(ten != null ? ten : "");
        dos.writeUTF(chucVu != null ? chucVu : "");
        dos.writeUTF(soDienThoai != null ? soDienThoai : "");
        dos.writeUTF(gioiTinh != null ? gioiTinh : "");
        dos.writeDouble(luongCoBan);
        dos.writeDouble(tongTienDaBan);
        LocalDate ngayGhi = (ngayVaoLam != null) ? ngayVaoLam : LocalDate.now();
        dos.writeLong(ngayGhi.toEpochDay());
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
        this.ngayVaoLam = LocalDate.ofEpochDay(dis.readLong());
    }

    public String getMa() { return ma; }
    public String getHo() { return ho; }
    public String getTen() { return ten; }
    public String getChucVu() { return chucVu; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getGioiTinh() { return gioiTinh; }
    public double getLuongCoBan() { return luongCoBan; }
    public double getTongTienDaBan() { return tongTienDaBan; }
    public LocalDate getNgayVaoLam() { return ngayVaoLam; }

    public boolean setMa(String maMoi) {
        if (maMoi == null || maMoi.trim().isEmpty()) return false;
        this.ma = maMoi.trim();
        return true;
    }

    public boolean setHo(String hoMoi) {
        if (hoMoi == null || hoMoi.trim().isEmpty()) return false;
        this.ho = hoMoi.trim();
        return true;
    }

    public boolean setTen(String tenMoi) {
        if (tenMoi == null || tenMoi.trim().isEmpty()) return false;
        this.ten = tenMoi.trim();
        return true;
    }

    public boolean setChucVu(String chucVuMoi) {
        if (chucVuMoi == null || chucVuMoi.trim().isEmpty()) return false;
        this.chucVu = chucVuMoi.trim();
        return true;
    }

    public boolean setSoDienThoai(String soDienThoaiMoi) {
        if (soDienThoaiMoi == null || soDienThoaiMoi.trim().isEmpty()) return false;
        this.soDienThoai = soDienThoaiMoi.trim();
        return true;
    }

    public boolean setGioiTinh(String gioiTinhMoi) {
        if (gioiTinhMoi == null || gioiTinhMoi.trim().isEmpty()) return false;
        this.gioiTinh = gioiTinhMoi.trim();
        return true;
    }

    public boolean setLuongCoBan(double luongCoBanMoi) {
        if (luongCoBanMoi < 0) return false;
        this.luongCoBan = luongCoBanMoi;
        return true;
    }

    public boolean setTongTienDaBan(double tongTienMoi) {
        if (tongTienMoi < 0) return false;
        this.tongTienDaBan = tongTienMoi;
        return true;
    }

    public boolean setNgayVaoLam(LocalDate ngayVaoLamMoi) {
        if (ngayVaoLamMoi == null) return false;
        this.ngayVaoLam = ngayVaoLamMoi;
        return true;
    }

    public static String dinhDangNgay(LocalDate ngay) {
        return (ngay != null) ? ngay.format(DINH_DANG_NGAY) : "";
    }

    public static LocalDate docNgay(String loiNhan) {
        while (true) {
            String chuoiNgay = NhapChuan.docChuoiKhongRong(loiNhan).trim();
            try {
                return LocalDate.parse(chuoiNgay, DINH_DANG_NGAY);
            } catch (DateTimeParseException e) {
                System.out.println(">> Dinh dang ngay khong hop le! Vui long nhap theo dinh dang dd/MM/yyyy (vi du: 20/11/2026).");
            }
        }
    }
}