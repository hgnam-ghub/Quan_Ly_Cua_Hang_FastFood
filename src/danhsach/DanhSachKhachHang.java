package danhsach;

import java.util.Arrays;

import mohinh.KhachHang;
import quanly.NhapChuan;

public class DanhSachKhachHang {
    private KhachHang[] DS_KhachHang;
    private int soLuong;

    public int getSoLuong() {return soLuong;}
    public DanhSachKhachHang() {
        soLuong = 0;
        DS_KhachHang = new KhachHang[soLuong];
    }
    public DanhSachKhachHang(DanhSachKhachHang dsKhachHang) {
        
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
    public boolean themKhachHang() {
        String ma;
        while (true) {
            ma = NhapChuan.docChuoiKhongRong("Ma Khach Hang");
            if(!kiemTraMa(ma)) break;
            System.out.println(">> Ma khach hang nay da ton tai, nhap ma khac!!");
        }
        if(soLuong < DS_KhachHang.length)
            DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong);
        return true;
    }
    public boolean xoaKhachHang(String ma) {
        return true;
    }
    public KhachHang timKiemKhachHang(String ma) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_KhachHang[i].getMa().equals(ma)) 
                return DS_KhachHang[i];
        }
        return null;
    }
    public boolean kiemTraMa(String ma) {
        return timKiemKhachHang(ma) != null;
    }

    public void xoaTheoDanhSach(DanhSachKhachHang DS_KH) {
        
    }
    public void nhapTuDanhSach(DanhSachKhachHang DS_KH) {
        
    }
    public DanhSachKhachHang timkiemTheoMa(String ma) {
        return null;
    }
    public DanhSachKhachHang timkiemTheoHo(String ho) {
        return null;
    }
    public DanhSachKhachHang timkiemTheoTen(String ten) {
        return null;
    }
    public DanhSachKhachHang timkiemTheotongTienDaMua(double tongTienDaMua) {
        return null;
    }
}
