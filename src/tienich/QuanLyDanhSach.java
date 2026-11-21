package tienich;
import rangbuoc.IChiTietDanhSach;
import rangbuoc.IDanhSach;
import rangbuoc.ITruyVanDanhSach;

import java.io.IOException;

import danhsach.*;

public final class QuanLyDanhSach{
    public static DanhSachSanPham DS_SANPHAM = new DanhSachSanPham();
    public static DanhSachNhanVien DS_NHANVIEN = new DanhSachNhanVien();
    public static DanhSachKhachHang DS_KHACHHANG = new DanhSachKhachHang();
    public static DanhSachHoaDon DS_HOADON = new DanhSachHoaDon();
    public static DanhSachChiTietHoaDon DS_CHITIETHOADON = new DanhSachChiTietHoaDon();
    public static DanhSachNguyenLieu DS_NGUYENLIEU = new DanhSachNguyenLieu();
    public static DanhSachNhaCungCap DS_NHACUNGCAP = new DanhSachNhaCungCap();
    public static DanhSachPhieuNhapHang DS_PHIEUNHAPHANG = new DanhSachPhieuNhapHang();
    public static DanhSachChiTietPhieuNhapHang DS_CHITIETPHIEUNHAPHANG = new DanhSachChiTietPhieuNhapHang();
    private static ITruyVanDanhSach[] DANHSACHTRUYCAPFILE = new ITruyVanDanhSach[] {DS_SANPHAM, DS_NHANVIEN, DS_KHACHHANG, DS_HOADON,DS_CHITIETHOADON, DS_NGUYENLIEU, DS_NHACUNGCAP,DS_PHIEUNHAPHANG, DS_CHITIETPHIEUNHAPHANG};
    public static IDanhSach[] DANHSACH = new IDanhSach[] {DS_SANPHAM, DS_NHANVIEN, DS_KHACHHANG, DS_HOADON, DS_NGUYENLIEU, DS_NHACUNGCAP,DS_PHIEUNHAPHANG};
    public static IChiTietDanhSach[] DANHSACHCHITIET = new IChiTietDanhSach[] {DS_CHITIETHOADON, DS_CHITIETPHIEUNHAPHANG};

    private QuanLyDanhSach() {}
    public static void taiDuLieuDanhSach() {
        for(ITruyVanDanhSach ds: DANHSACHTRUYCAPFILE)
        try{ds.nhapDanhSachTuFile();}
        catch (IOException e ) {}
    }
    public static void ghiDuLieuDanhSach() {
        for(ITruyVanDanhSach ds: DANHSACHTRUYCAPFILE)
        try{ds.xuatDanhSachRaFile();}
        catch (IOException e ) {}
    }
}
