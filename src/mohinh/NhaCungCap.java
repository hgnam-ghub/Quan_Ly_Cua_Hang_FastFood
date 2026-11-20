package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import tienich.NhapChuan;

public final class NhaCungCap {
    private String ma;
    private String ho;
    private String ten;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private LocalDate ngayHopTac;

    public NhaCungCap(String ma, String ho, String ten, String diaChi, String soDienThoai, String email, LocalDate ngayHopTac) {
        this.ma = ma;
        this.ho = ho;
        this.ten = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.ngayHopTac = (ngayHopTac != null) ? ngayHopTac : LocalDate.now();
    }

    public NhaCungCap(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co", "khong co", LocalDate.now());
    }

    public NhaCungCap(String ma, NhaCungCap nhaCungCap) {
        this(ma, nhaCungCap.ho, nhaCungCap.ten, nhaCungCap.diaChi, nhaCungCap.soDienThoai, nhaCungCap.email, nhaCungCap.ngayHopTac);
    }

    public NhaCungCap(NhaCungCap nhaCungCap) {
        this(nhaCungCap.ma, nhaCungCap);
    }

    public NhaCungCap saoChep() {
        return new NhaCungCap(this);
    }

    public NhaCungCap saoChepVoiMaMoi(String maMoi) {
        return new NhaCungCap(maMoi, this);
    }

    public void nhap() {
        this.ho = NhapChuan.docChuoiKhongRong("Nhap ho nha cung cap");
        this.ten = NhapChuan.docChuoiKhongRong("Nhap ten nha cung cap");
        this.diaChi = NhapChuan.docChuoiKhongRong("Nhap dia chi");
        this.soDienThoai = NhapChuan.docChuoiKhongRong("Nhap so dien thoai");
        this.email = NhapChuan.docChuoiKhongRong("Nhap email");
        this.ngayHopTac = NhanVien.docNgay("Nhap ngay hop tac (dd/MM/yyyy)");
    }

    public void xuat() {
        System.out.printf("Ma NCC: %s | Ho ten: %s %s | Dia chi: %s | SDT: %s | Email: %s | Ngay HT: %s%n",
            ma, ho, ten, diaChi, soDienThoai, email, NhanVien.dinhDangNgay(ngayHopTac));
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma != null ? ma : "");
        dos.writeUTF(ho != null ? ho : "");
        dos.writeUTF(ten != null ? ten : "");
        dos.writeUTF(diaChi != null ? diaChi : "");
        dos.writeUTF(soDienThoai != null ? soDienThoai : "");
        dos.writeUTF(email != null ? email : "");
        LocalDate ngayGhi = (ngayHopTac != null) ? ngayHopTac : LocalDate.now();
        dos.writeLong(ngayGhi.toEpochDay());
    }

    public void docFile(DataInputStream dis) throws IOException {
        this.ma = dis.readUTF();
        this.ho = dis.readUTF();
        this.ten = dis.readUTF();
        this.diaChi = dis.readUTF();
        this.soDienThoai = dis.readUTF();
        this.email = dis.readUTF();
        this.ngayHopTac = LocalDate.ofEpochDay(dis.readLong());
    }

    public String getMa() { return ma; }
    public String getHo() { return ho; }
    public String getTen() { return ten; }
    public String getDiaChi() { return diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getEmail() { return email; }
    public LocalDate getNgayHopTac() { return ngayHopTac; }

    public String getMaNhaCungCap() { return ma; }
    public String getTenNhaCungCap() { return ten; }

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

    public boolean setDiaChi(String diaChiMoi) {
        if (diaChiMoi == null || diaChiMoi.trim().isEmpty()) return false;
        this.diaChi = diaChiMoi.trim();
        return true;
    }

    public boolean setSoDienThoai(String sdtMoi) {
        if (sdtMoi == null || sdtMoi.trim().isEmpty()) return false;
        this.soDienThoai = sdtMoi.trim();
        return true;
    }

    public boolean setEmail(String emailMoi) {
        if (emailMoi == null || emailMoi.trim().isEmpty()) return false;
        this.email = emailMoi.trim();
        return true;
    }

    public boolean setNgayHopTac(LocalDate ngayHopTacMoi) {
        if (ngayHopTacMoi == null) return false;
        this.ngayHopTac = ngayHopTacMoi;
        return true;
    }
}