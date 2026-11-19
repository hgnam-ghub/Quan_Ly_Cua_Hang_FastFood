package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public final class NhaCungCap implements ITruyXuatMoHinh {
    private String ma;
    private String ho;
    private String ten;
    private String diaChi;
    private String soDienThoai;
    private String email;

    public NhaCungCap() {
        this("", "khong co", "khong co", "khong co", "khong co", "khong co");
    }

    public NhaCungCap(String ma, String ho, String ten, String diaChi, String soDienThoai, String email) {
        if (setMa(ma) == null) this.ma = "khong hop le";
        if (setHo(ho) == null) this.ho = "khong co";
        if (setTen(ten) == null) this.ten = "khong co";
        if (setDiaChi(diaChi) == null) this.diaChi = "khong co";
        if (setSoDienThoai(soDienThoai) == null) this.soDienThoai = "khong co";
        if (setEmail(email) == null) this.email = "khong co";
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

    @Override
    public String[] xuat() {
        return new String[]{
            ma,
            ho,
            ten,
            diaChi,
            soDienThoai,
            email
        };
    }

    @Override
    public NhaCungCap nhap() {
        setHo(NhapChuan.docChuoiKhongRong("Nhap ho nha cung cap"));
        setTen(NhapChuan.docChuoiKhongRong("Nhap ten nha cung cap"));
        setDiaChi(NhapChuan.docChuoiKhongRong("Nhap dia chi"));
        setSoDienThoai(NhapChuan.docChuoiKhongRong("Nhap so dien thoai"));
        setEmail(NhapChuan.docChuoiKhongRong("Nhap email"));
        return this;
    }

    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma);
        dos.writeUTF(ho);
        dos.writeUTF(ten);
        dos.writeUTF(diaChi);
        dos.writeUTF(soDienThoai);
        dos.writeUTF(email);
    }

    @Override
    public void docFile(DataInputStream dis) throws IOException {
        setMa(dis.readUTF());
        setHo(dis.readUTF());
        setTen(dis.readUTF());
        setDiaChi(dis.readUTF());
        setSoDienThoai(dis.readUTF());
        setEmail(dis.readUTF());
    }

    public final String getMa() { return ma; }
    public final String getHo() { return ho; }
    public final String getTen() { return ten; }
    public final String getDiaChi() { return diaChi; }
    public final String getSoDienThoai() { return soDienThoai; }
    public final String getEmail() { return email; }

    // Các hàm bổ trợ tương thích với DanhSachNhaCungCap
    public final String getMaNhaCungCap() { return ma; }
    public final String getTenNhaCungCap() { return ten; }

    public final NhaCungCap setMa(String maMoi) {
        if (maMoi == null || maMoi.trim().isEmpty()) return null;
        this.ma = maMoi;
        return this;
    }

    public final NhaCungCap setHo(String hoMoi) {
        if (hoMoi == null || hoMoi.trim().isEmpty()) return null;
        this.ho = hoMoi;
        return this;
    }

    public final NhaCungCap setTen(String tenMoi) {
        if (tenMoi == null || tenMoi.trim().isEmpty()) return null;
        this.ten = tenMoi;
        return this;
    }

    public final NhaCungCap setDiaChi(String diaChiMoi) {
        if (diaChiMoi == null || diaChiMoi.trim().isEmpty()) return null;
        this.diaChi = diaChiMoi;
        return this;
    }

    public final NhaCungCap setSoDienThoai(String sdtMoi) {
        if (sdtMoi == null || sdtMoi.trim().isEmpty()) return null;
        this.soDienThoai = sdtMoi;
        return this;
    }

    public final NhaCungCap setEmail(String emailMoi) {
        if (emailMoi == null || emailMoi.trim().isEmpty()) return null;
        this.email = emailMoi;
        return this;
    }
}