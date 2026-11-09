package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import quanly.NhapChuan;

public final class NhaCungCap {
    String ma;
    String ho;
    String ten;
    String diaChi;
    String soDienThoai;
    String email;

    public NhaCungCap(String ma, String ho, String ten, String diaChi, String soDienThoai, String email) {
        this.ma = ma;
        this.ho = ho;
        this.ten = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }
    public NhaCungCap(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co", "khong co");
    }
    public NhaCungCap(String ma, NhaCungCap nhaCungCap) {
        this(ma, nhaCungCap.ho, nhaCungCap.ten, nhaCungCap.diaChi, nhaCungCap.soDienThoai, nhaCungCap.email);
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
    }

    public void xuat() {
        System.out.printf("Ma NCC: %s | Ho ten: %s %s | Dia chi: %s | SDT: %s | Email: %s\n", 
            ma, ho, ten, diaChi, soDienThoai, email);
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma);
        dos.writeUTF(ho);
        dos.writeUTF(ten);
        dos.writeUTF(diaChi);
        dos.writeUTF(soDienThoai);
        dos.writeUTF(email);
    }

    public void docFile(DataInputStream dis) throws IOException {
        this.ma = dis.readUTF();
        this.ho = dis.readUTF();
        this.ten = dis.readUTF();
        this.diaChi = dis.readUTF();
        this.soDienThoai = dis.readUTF();
        this.email = dis.readUTF();
    }

    public String getMa() { return ma; }
    public String getHo() { return ho; }
    public String getTen() { return ten; }
    public String getDiaChi() { return diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getEmail() { return email; }

    // Các hàm bổ trợ tương thích với DanhSachNhaCungCap
    public String getMaNhaCungCap() { return ma; }
    public String getTenNhaCungCap() { return ten; }

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
    public boolean setDiaChi(String diaChiMoi) {
        if(diaChiMoi == null || diaChiMoi.trim().isEmpty()) return false;
        this.diaChi = diaChiMoi;
        return true;
    }
    public boolean setSoDienThoai(String sdtMoi) {
        if(sdtMoi == null || sdtMoi.trim().isEmpty()) return false;
        this.soDienThoai = sdtMoi;
        return true;
    }
    public boolean setEmail(String emailMoi) {
        if(emailMoi == null || emailMoi.trim().isEmpty()) return false;
        this.email = emailMoi;
        return true;
    }
}