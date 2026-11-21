package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public final class ChiTietPhieuNhapHang implements ITruyXuatMoHinh{
    private String maPNH; 
    private String maNL; 
    private double donGiaNhap;
    private double thanhTien;
    private int soLuong;

    public ChiTietPhieuNhapHang (String maPNH, String maNL, double donGiaNhap, double thanhTien, int soLuong){
        if(setMaPNH(maPNH) == null) {this.maPNH = "khong hop le";}
        if(setMaNL(maNL) == null) {this.maNL = "khong hop le";}
        if(setDonGiaNhap(donGiaNhap) == null) this.donGiaNhap = 0;
        if(setThanhTien(thanhTien) == null) this.thanhTien = 0;
        if(setSoLuong(soLuong) == null) this.soLuong = 1;
    }
    public ChiTietPhieuNhapHang () {this("chua co", "chua co", 0, 0, 1);}
    public ChiTietPhieuNhapHang (ChiTietPhieuNhapHang CTPNH) {this(CTPNH.maPNH, CTPNH.maNL, CTPNH.donGiaNhap, CTPNH.thanhTien, CTPNH.soLuong);}
    public ChiTietPhieuNhapHang saoChep() {return new ChiTietPhieuNhapHang(this);}

    @Override
    public  ChiTietPhieuNhapHang nhap() {
        donGiaNhap = NhapChuan.docSoNguyenKhongAm("So Luong");
        return this;
    }
    @Override 
    public String[] xuat() {
        return new String[] {maPNH, maNL, String.valueOf(donGiaNhap), String.valueOf(thanhTien), String.valueOf(soLuong)};
    }
    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maPNH);
        dos.writeUTF(maNL);
        dos.writeDouble(donGiaNhap);
        dos.writeDouble(thanhTien);
        dos.writeInt(soLuong);
    }
    @Override
    public void docFile(DataInputStream dis) throws IOException {
        maPNH = dis.readUTF();
        maNL = dis.readUTF();
        donGiaNhap = dis.readDouble();
        thanhTien = dis.readDouble();
        soLuong = dis.readInt();
    }

    public String getMaPNH() {return maPNH;}
    public String getMaNL() {return maNL;}
    public double getDonGiaNhap() {return donGiaNhap;}
    public double getThanhTien() {return thanhTien;}
    public int getSoLuong() {return soLuong;}

    public ChiTietPhieuNhapHang setMaPNH(String maPNHMoi) {
        if(maPNHMoi.isEmpty()) return null;
        maPNH = maPNHMoi;
        return this;
    }
    public ChiTietPhieuNhapHang setMaNL(String maNLMoi) {
        if((maNLMoi.isEmpty())) return null;
        maNL = maNLMoi;
        return this;
    }
    public ChiTietPhieuNhapHang setDonGiaNhap(double donGiaNhapMoi) {
        if(donGiaNhapMoi < 0 ) return null;
        donGiaNhap = donGiaNhapMoi;
        return this;
    }
    public ChiTietPhieuNhapHang setThanhTien(double thanhTienMoi) {
        if(thanhTienMoi < 0 ) return null;
        thanhTien = thanhTienMoi;
        return this;
    }
    public ChiTietPhieuNhapHang setSoLuong(int soLuongMoi) {
        if(soLuongMoi <= 1) return null;
        soLuong = soLuongMoi;
        return this;
    }
}
