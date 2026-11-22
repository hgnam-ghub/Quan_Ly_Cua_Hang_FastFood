package tienich;
import rangbuoc.IChiTietDanhSach;
import rangbuoc.IDanhSach;
import rangbuoc.ITruyVanDanhSach;
import java.io.IOException;

import danhsach.*;
import mohinh.HoaDon;
import mohinh.KhachHang;

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
     private static String[][] thongKeTongTienKhachHangTheoNam(int namBatDau, int namKetThuc) {
        int soNam = namKetThuc - namBatDau + 1;
        int soKH = DS_KHACHHANG.getSoLuong();
        double[][] tong = new double[soKH][soNam];

        for (int i = 0; i < DS_HOADON.getSoLuong(); i++) {
            HoaDon hd = DS_HOADON.docPhanTu(i);
            int cot = hd.getNgayTaoDon().getYear() - namBatDau;
            if (cot < 0 || cot >= soNam) continue;
            for (int j = 0; j < soKH; j++)
                if (DS_KHACHHANG.docPhanTu(j).getMa().equals(hd.getMaKH())) {
                    tong[j][cot] += hd.getTongTien();
                    break;
                }
        }
        String[][] bang = new String[soKH + 1][soNam + 2];
        bang[0][0] = "Khach hang";
        for (int c = 0; c < soNam; c++) bang[0][c + 1] = String.valueOf(namBatDau + c);
        bang[0][soNam + 1] = "Tong Cong";

        for (int j = 0; j < soKH; j++) {
            KhachHang kh = DS_KHACHHANG.docPhanTu(j);
            double tongKH = 0;
            bang[j + 1][0] = kh.getMa() + " - " + kh.getHo() + " " + kh.getTen();
            for (int c = 0; c < soNam; c++) {
                bang[j + 1][c + 1] = String.format("%,.0f", tong[j][c]);
                tongKH += tong[j][c];
            }
            bang[j + 1][soNam + 1] = String.format("%,.0f", tongKH);
        }
        return bang;
    }

    public static void thongKeTongTienKhachHang() {
        int namBatDau = NhapChuan.docNam("Tu nam (yyyy)");
        int namKetThuc;
        while ((namKetThuc = NhapChuan.docNam("Den nam (yyyy)")) < namBatDau)
            System.out.println(">> Nam ket thuc phai lon hon hoac bang nam bat dau!");
        Menu.clearScreen();
        String[][] bang = thongKeTongTienKhachHangTheoNam(namBatDau, namKetThuc);
        FormatBang.inBangDuLieu("TONG TIEN HOA DON CUA KHACH HANG TU NAM " + namBatDau + " DEN NAM " + namKetThuc, bang);
        if (bang.length == 1) System.out.println(">> Chua co khach hang nao trong danh sach");
        NhapChuan.docChuoi("Enter de quay lai");
    }
}
