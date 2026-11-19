package mohinh;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public class ChiTietHoaDon implements ITruyXuatMoHinh {
    private String maHD;
    private String maSP;
    private double donGiaBan;
    private double thanhTien;
    private int soLuong;

    public ChiTietHoaDon() {this("", "", 0, 0, 1);}
    protected ChiTietHoaDon (String maHD, String maSP, double donGiaBan, double thanhTien, int soLuong) {
        if(setMa(maHD, maSP) == null) {this.maHD = "khong hop le"; this.maSP = "khong hop le";}
        if(setDonGiaBan(donGiaBan) == null) this.donGiaBan = 0;
        if(setThanhTien(thanhTien) == null) this.thanhTien = 0;
        if(setSoLuong(soLuong) == null) this.soLuong = 0;
    }
    protected ChiTietHoaDon (ChiTietHoaDon cthd) {
        this(cthd.maHD, cthd.maSP, cthd.donGiaBan, cthd.thanhTien, cthd.soLuong);
    }
    public ChiTietHoaDon saoChep() {return new ChiTietHoaDon(this){};}

    public String[] xuat() {
        return new String[] {maHD,maSP,String.valueOf(donGiaBan),String.valueOf(thanhTien),String.valueOf(soLuong)};
    }

    public ChiTietHoaDon nhap() {
        setMa(NhapChuan.docChuoiKhongRong("Ma hoa don"), NhapChuan.docChuoiKhongRong("Ma san pham"));
        setDonGiaBan(NhapChuan.docSoThucKhongAm("Don gia ban"));
        setSoLuong(NhapChuan.docSoNguyenKhongAm("So luong"));
        thanhTien = donGiaBan * soLuong; // Tự động tính thành tiền
        return this;
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maHD);
        dos.writeUTF(maSP);
        dos.writeDouble(donGiaBan);
        dos.writeDouble(thanhTien);
        dos.writeInt(soLuong);
    }

    public void docFile(DataInputStream dis) throws IOException {
        maHD = dis.readUTF();
        maSP = dis.readUTF();
        donGiaBan = dis.readDouble();
        thanhTien = dis.readDouble();
        soLuong = dis.readInt();
    }

    public String getMaHoaDon() {return maHD;}
    public String getMaSanPham() {return maSP;}
    public double getDonGiaBan() {return donGiaBan;}
    public double getThanhTien() {return thanhTien;}
    public int getSoLuong() {return soLuong;}

    public ChiTietHoaDon setMa(String maHDMoi, String maSPMoi) {
        if(maHDMoi.isEmpty() || maSPMoi.isEmpty()) return null;
        maHD = maHDMoi;
        maSP = maSPMoi;
        return this;
    }
    public ChiTietHoaDon setDonGiaBan(double donGiaBanMoi) {
        if(donGiaBanMoi < 0) return null;
        donGiaBan = donGiaBanMoi;
        thanhTien = donGiaBan * soLuong;
        return this;
    }
    public ChiTietHoaDon setThanhTien(double thanhTienMoi) {
        if(thanhTienMoi < 0) return null;
        thanhTien = thanhTienMoi;
        return this;
    }
    public ChiTietHoaDon setSoLuong(int soLuongMoi) {
        if(soLuongMoi <= 0) return null;
        soLuong = soLuongMoi;
        thanhTien = donGiaBan * soLuong;
        return this;
    }
}
