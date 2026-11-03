package danhsach;

import mohinh.HoaDon;

public class DanhSachHoaDon {
    private HoaDon[] DS_HoaDon;
    private int soLuong;

    public DanhSachHoaDon () {
        soLuong = 0;
        DS_HoaDon = new HoaDon[soLuong];
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

    public DanhSachHoaDon timKiemTheoMaHD(String maHD) {
        return null;
    }
    public DanhSachHoaDon timKiemTheoMaNV(String maNV) {
        return null;
    }
    public DanhSachHoaDon timKiemTheoMaKH(String maKH) {
        return null;
    }
    // lam them cac tim kiem theo cac thuoc tinh khac

    public void xoaTheoDanhSach(DanhSachHoaDon DS_HD) {
        
    }
    public void nhapTuDanhSach(DanhSachHoaDon DS_HD) {
        
    }
}
