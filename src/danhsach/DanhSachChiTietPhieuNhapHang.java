package danhsach;

import mohinh.ChiTietPhieuNhapHang;

public class DanhSachChiTietPhieuNhapHang {
    private ChiTietPhieuNhapHang[] DS_ChiTietPhieuNhapHang;
    private int soLuong;

    public DanhSachChiTietPhieuNhapHang() {
        soLuong = 0; 
        DS_ChiTietPhieuNhapHang = new ChiTietPhieuNhapHang[soLuong];
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

    public DanhSachChiTietPhieuNhapHang timKiemMaPNH(String maPNH) {
        return null;
    }
    public DanhSachChiTietPhieuNhapHang timKiemMaNL(String maNL) {
        return null;
    }
    public boolean kiemTraMa(String maPNH, String maNL) {
        DanhSachChiTietPhieuNhapHang dsTam = timKiemMaNL(maNL);
        return (dsTam != null && dsTam.timKiemMaPNH(maPNH) != null);
    }
    public void xoaTheoDanhSach(DanhSachChiTietPhieuNhapHang DS_CTPNH) {
        
    }
}
