package danhsach;

import mohinh.PhieuNhapHang;
import rangbuoc.IDanhSach;

public class DanhSachPhieuNhapHang implements IDanhSach{
    private PhieuNhapHang[] DS_PhieuNhapHang;
    private int soLuong;

    public DanhSachPhieuNhapHang() {
        soLuong = 0;
        DS_PhieuNhapHang = new PhieuNhapHang[0];
    }
    public DanhSachPhieuNhapHang(DanhSachPhieuNhapHang ds_pnh) {
        soLuong = ds_pnh.soLuong;
        DS_PhieuNhapHang = new PhieuNhapHang[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_PhieuNhapHang[i] = ds_pnh.DS_PhieuNhapHang[i].saoChep();
    }

    @Override
    public DanhSachPhieuNhapHang saoChep() {return new DanhSachPhieuNhapHang(this);}

    @Override
    public int getSoLuong() {return soLuong;}

    @Override
    public void xuatDanhSach() {
        String[] tieuDeCot= new String[] {"Ma PNH", "Ma NV", "Ma NCC", "Ngay Tao Phieu", "Trang Thai", "Phuong Thuc Thanh Toan", "Tong Tien"};
        String[][] bangDuLieu = new String[soLuong + 1][];
        bangDuLieu[0] = tieuDeCot;
        for(int i = 0; i < 0; i++) {
            bangDuLieu[i+1] = DS_PhieuNhapHang[i].xuat();
        }
    }

}
