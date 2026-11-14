package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public class NhaCungCap implements ITruyXuatMoHinh {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String diaChi;
    private String soDienThoai;

    public NhaCungCap() {
        this("", "", "", "");
    }

    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String diaChi, String soDienThoai) {
        if(setMaNhaCungCap(maNhaCungCap) == null) this.maNhaCungCap = "khong hop le";
        if(setTenNhaCungCap(tenNhaCungCap) == null) this.tenNhaCungCap = "khong co";
        if(setDiaChi(diaChi) == null) this.diaChi = "khong co";
        if(setSoDienThoai(soDienThoai) == null) this.soDienThoai = "khong co";
    }

    public NhaCungCap(NhaCungCap ncc) {
        this(ncc.maNhaCungCap, ncc.tenNhaCungCap, ncc.diaChi, ncc.soDienThoai);
    }

    public NhaCungCap saoChep() {
        return new NhaCungCap(this);
    }

    public String[] xuat() {
        String[] truongDuLieu = new String[4];
        truongDuLieu[0] = maNhaCungCap;
        truongDuLieu[1] = tenNhaCungCap;
        truongDuLieu[2] = diaChi;
        truongDuLieu[3] = soDienThoai;
        return truongDuLieu;
    }

    public NhaCungCap nhap() {
        setTenNhaCungCap(NhapChuan.docChuoiKhongRong("Ten nha cung cap"));
        setDiaChi(NhapChuan.docChuoiKhongRong("Dia chi"));
        setSoDienThoai(NhapChuan.docChuoiKhongRong("So dien thoai"));
        return this;
    }

    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maNhaCungCap);
        dos.writeUTF(tenNhaCungCap);
        dos.writeUTF(diaChi);
        dos.writeUTF(soDienThoai);
    }

    @Override
    public void docFile(DataInputStream dis) throws IOException {
        maNhaCungCap = dis.readUTF();
        tenNhaCungCap = dis.readUTF();
        diaChi = dis.readUTF();
        soDienThoai = dis.readUTF();
    }

    public final String getMaNhaCungCap() { return maNhaCungCap; }
    public final String getTenNhaCungCap() { return tenNhaCungCap; }
    public final String getDiaChi() { return diaChi; }
    public final String getSoDienThoai() { return soDienThoai; }

    public final NhaCungCap setMaNhaCungCap(String maMoi) {
        if(maMoi.isEmpty()) return null;
        maNhaCungCap = maMoi;
        return this;
    }

    public final NhaCungCap setTenNhaCungCap(String tenMoi) {
        if(tenMoi.isEmpty()) return null;
        tenNhaCungCap = tenMoi;
        return this;
    }

    public final NhaCungCap setDiaChi(String diaChiMoi) {
        if(diaChiMoi.isEmpty()) return null;
        diaChi = diaChiMoi;
        return this;
    }

    public final NhaCungCap setSoDienThoai(String sdtMoi) {
        if(sdtMoi.isEmpty()) return null;
        soDienThoai = sdtMoi;
        return this;
    }
}