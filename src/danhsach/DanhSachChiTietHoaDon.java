package danhsach;

import java.util.Arrays;

import mohinh.ChiTietHoaDon;
import quanly.NhapChuan;

public final class DanhSachChiTietHoaDon {
    private ChiTietHoaDon[] DS_ChiTietHoaDon;
    private int soLuong;

    public DanhSachChiTietHoaDon() {
        soLuong = 0;
        DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
    }
    public DanhSachChiTietHoaDon(DanhSachChiTietHoaDon dsChiTietHoaDon) {
        
    }

    public void nhap() {

    }
    public void xuat() {

    }
    public boolean ghiFile() {
        return true;
    }
    public boolean docFile() {
        return true;
    }
    public boolean themChiTietHoaDon(String maHD) {
        String maSP;
        while (true) {
            maSP = NhapChuan.docChuoiKhongRong("Ma San Pham");
            if(kiemTraMa(maHD, maSP)) break;
            System.out.println(">> Ma hoa don va san pham nay da ton tai, nhap ma san pham khac!!");
        }
        if(soLuong < DS_ChiTietHoaDon.length)
            DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong);
        return true;
    }

    public boolean xoaChiTietHoaDon(String maHD, String maSP) {
        return true;
    }

    public ChiTietHoaDon timKiemChiTietHoaDon(String maHD, String maSP) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_ChiTietHoaDon[i].getMaHoaDon().equals(maHD) && DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP)) 
                return DS_ChiTietHoaDon[i];
        }
        return null;
    }

    public boolean kiemTraMa(String maHD, String maSP) {
        return timKiemChiTietHoaDon(maHD, maSP) != null;
    }

    public void xoaTheoDanhSach(DanhSachChiTietHoaDon DS_HD) {
        
    }
    public void nhapTuDanhSach(DanhSachChiTietHoaDon DS_HD) {
        
    }
    public DanhSachChiTietHoaDon timKiemMaHD(String maHD) {
        return null;
    }
    public DanhSachChiTietHoaDon timKiemMaSP(String maSP) {
        return null;
    }
}
