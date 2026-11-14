package tienich;
import rangbuoc.ITruyXuatDanhSach;
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
    private static ITruyXuatDanhSach[] DANHSACH = new ITruyXuatDanhSach[] {DS_SANPHAM, DS_NHANVIEN, DS_KHACHHANG, DS_HOADON, DS_CHITIETHOADON, DS_NGUYENLIEU, DS_NHACUNGCAP};

    private QuanLyDanhSach() {}
    public static void taiDuLieuDanhSach() {
        // for(ITruyXuatDanhSach ds: DANHSACH) {
        //     ds.nhapDanhSachTuFile();
        // }
        DS_SANPHAM.nhapDanhSachTuFile();
    }
    public static void ghiDuLieuDanhSach() {
        DS_SANPHAM.xuatDanhSachRaFile();
    }
}
