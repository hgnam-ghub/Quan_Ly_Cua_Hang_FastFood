package danhsach;

import java.util.Arrays;

import mohinh.HoaDon;
import quanly.NhapChuan;

public class DanhSachHoaDon {
    private HoaDon[] DS_HoaDon;
    private int soLuong;

    public int getSoLuong() {return soLuong;}
    public DanhSachHoaDon () {
        soLuong = 0;
        DS_HoaDon = new HoaDon[soLuong];
    }
    public DanhSachHoaDon(DanhSachHoaDon dsHoaDon) {
        
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

    public boolean themHoaDon() {
        String maHD;
        while (true) {
            maHD = NhapChuan.docChuoiKhongRong("Ma Hoa Don");
            if(!kiemTraMa(maHD)) break;
            System.out.println(">> Ma hoa don nay da ton tai, nhap ma khac!!");
        }
        if(soLuong < DS_HoaDon.length)
            DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong);
        return true;
    }

    public boolean xoaHoaDon(String maHD) {
        return true;
    }

    public HoaDon timKiemHoaDon(String maHD) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_HoaDon[i].getMaHoaDon().equals(maHD)) 
                return DS_HoaDon[i];
        }
        return null;
    }

    public boolean kiemTraMa(String maHD) {
        return timKiemHoaDon(maHD) != null;
    }

    public void xoaTheoDanhSach(DanhSachHoaDon DS_HD) {
        
    }
    public void nhapTuDanhSach(DanhSachHoaDon DS_HD) {
        
    }
    public DanhSachHoaDon timKiemTheoMaHD(String maHD) {
        return null;
    }
    public DanhSachHoaDon timKiemTheoMaNV(String maNV) {
        return null;
    }
    public DanhSachHoaDon timKiemTheoMaKH(String maKH) {
        return null;
    }
    public DanhSachHoaDon timKiemTheotongTien(String tongTien) {
        return null;
    }
    public DanhSachHoaDon timKiemTheoNgayTaoDon(String ngayTaoDon) {
        return null;
    }
    public DanhSachHoaDon timKiemTheoTrangThai(String trangThai) {
        return null;
    }
    public DanhSachHoaDon timKiemTheoPhuongThucThanhToan(String phuongThucThanhToan) {
        return null;
    }
}
