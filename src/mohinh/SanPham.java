package mohinh;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public abstract class SanPham implements ITruyXuatMoHinh{
    private String ma;
    private String ten;
    private String donVi;
    private String phuongThucCheBien;
    private double giaBan;
    private int soLuong;

    protected SanPham() {this("", "", "", "", 0, 1);}
    protected SanPham(String ma, String ten, String donVi, String phuongThucCheBien, double giaBan, int soLuong) {
        if(setMa(ma) == null) this.ma = "khong hop le";
        if(setTen(ten) == null) this.ten = "khong co";
        if(setDonVi(donVi) == null) this.donVi = "khong co";
        if(setPhuongThucCheBien(phuongThucCheBien) == null) this.phuongThucCheBien = "khong co";
        if(setGiaBan(giaBan) == null) this.giaBan = 0;
        if(setSoLuong(soLuong) == null) this.soLuong = 1;
    }
    protected SanPham(SanPham sanPham) {this(sanPham.ma, sanPham.ten, sanPham.donVi, sanPham.phuongThucCheBien, sanPham.giaBan, sanPham.soLuong);}
    public abstract SanPham saoChep();

    public String[] xuat() {
        String[] truongDuLieu = new String[6];
        truongDuLieu[0] = ma;
        truongDuLieu[1] = ten;
        truongDuLieu[2] = donVi;
        truongDuLieu[3] = phuongThucCheBien;
        truongDuLieu[4] = String.valueOf(giaBan);
        truongDuLieu[5] = String.valueOf(soLuong);
        return truongDuLieu;
    }
    public SanPham nhap() {
        setTen(NhapChuan.docChuoiKhongRong("Ten"));
        setDonVi(NhapChuan.docChuoiKhongRong("Don vi"));
        setPhuongThucCheBien(NhapChuan.docChuoiKhongRong("Phuong thuc che bien"));
        setGiaBan(NhapChuan.docSoNguyen("Gia ban"));
        setSoLuong(NhapChuan.docSoNguyenKhongAm("So luong"));
        return this;
    }

    @Override
    public void ghiFile(DataOutputStream dos) throws IOException{
        dos.writeUTF(ma);
        dos.writeUTF(ten);
        dos.writeUTF(donVi);
        dos.writeUTF(phuongThucCheBien);
        dos.writeDouble(giaBan);
        dos.writeInt(soLuong);
    }
    @Override
    public void docFile(DataInputStream dis) throws IOException {
        ten = dis.readUTF();
        donVi = dis.readUTF();
        phuongThucCheBien = dis.readUTF();
        giaBan = dis.readDouble();
        soLuong = dis.readInt();
    }

    public final String getMa(){return ma;}
    public final String getTen(){return ten;}
    public final String getDonVi(){return donVi;}
    public final String getPhuongThucCheBien(){return phuongThucCheBien;}
    public final double getGia(){return giaBan;}
    public final int getSoLuong(){return soLuong;}

    public final SanPham setMa(String maMoi) {
        if(maMoi.isEmpty()) return null;
        ma = maMoi;
        return this;
    }
    public final SanPham setTen(String tenMoi) {
        if(tenMoi.isEmpty()) return null;
        ten = tenMoi;
        return this;
    }
    public final SanPham setDonVi(String donViMoi) {
        if(donViMoi.isEmpty()) return null;
        donVi = donViMoi;
        return this;
    }
    public final SanPham setPhuongThucCheBien(String phuongThucCheBienMoi){
        if(phuongThucCheBienMoi.isEmpty()) return null;
        phuongThucCheBien = phuongThucCheBienMoi;
        return this;
    }
    public final SanPham setGiaBan(double giaBanMoi) {
        if(giaBanMoi < 0) return null;
        giaBan = giaBanMoi;
        return this;
    }
    public final SanPham setSoLuong(int soLuongMoi) {
        if(soLuongMoi < 0) return null;
        soLuong = soLuongMoi;
        return this;
    }
    public final SanPham thayDoiSoLuong(int luongThayDoi) {
        return setSoLuong(soLuong + luongThayDoi);
    }
}
