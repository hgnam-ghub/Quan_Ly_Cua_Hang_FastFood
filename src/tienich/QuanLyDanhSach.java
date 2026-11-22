package tienich;
import rangbuoc.IChiTietDanhSach;
import rangbuoc.IDanhSach;
import rangbuoc.ITruyVanDanhSach;
import java.io.IOException;

import mohinh.HoaDon;
import mohinh.NhanVien;
import mohinh.SanPham;
import mohinh.ChiTietHoaDon;

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
    private static void tongTienNhanVienTheoNam(int namBatDau, int namKetThuc) {
        int soNam = namKetThuc - namBatDau + 1;
        int soNV = DS_NHANVIEN.getSoLuong();
        double[][] tong = new double[soNV][soNam];

        for (int i = 0; i < DS_HOADON.getSoLuong(); i++) {
            HoaDon hd = DS_HOADON.docPhanTu(i);
            int cot = hd.getNgayTaoDon().getYear() - namBatDau;
            if (cot < 0 || cot >= soNam) continue;
            for (int j = 0; j < soNV; j++)
                if (DS_NHANVIEN.docPhanTu(j).getMa().equals(hd.getMaNV())) {
                    tong[j][cot] += hd.getTongTien();
                    break;
                }
        }
        String[] tieuDeDong = new String[DS_NHANVIEN.getSoLuong()];
        for(int i = 0; i < DS_NHANVIEN.getSoLuong(); i++) {
            NhanVien nv = DS_NHANVIEN.docPhanTu(i);
            tieuDeDong[i] = nv.getMa() + "-" + nv.getHo() + " " + nv.getTen();
        }
        FormatBang.inBangThongKeTheoNam("Nhan Vien", tieuDeDong, namBatDau, tong);
    }
    private static void tongTienSanPhamTheoNam(int namBatDau, int namKetThuc) {
        int soNam = namKetThuc - namBatDau + 1;
        int soSP = DS_SANPHAM.getSoLuong();
        double[][] tong = new double[soSP][soNam];

        for(int i = 0; i < DS_CHITIETHOADON.getSoLuong(); i++) {
            ChiTietHoaDon cthd = DS_CHITIETHOADON.docPhanTu(i);
            HoaDon hd = DS_HOADON.timKiemMa(cthd.getMaHoaDon());
            int cot = hd.getNgayTaoDon().getYear() - namBatDau;
            if (cot < 0 || cot >= soNam) continue;
            for(int dong = 0; dong < soSP; dong++)
                if(DS_SANPHAM.docPhanTu(dong).getMa().equals(cthd.getMaSanPham())) {
                    tong[dong][cot] += cthd.getThanhTien();
                    break;
                }
        }
        String[] tieuDeDong = new String[DS_SANPHAM.getSoLuong()];
        for(int i = 0; i < DS_SANPHAM.getSoLuong(); i++) {
            SanPham sp = DS_SANPHAM.docPhanTu(i);
            tieuDeDong[i] = sp.getMa() + "-" + sp.getTen();
        }
        FormatBang.inBangThongKeTheoNam("San Pham", tieuDeDong, namBatDau, tong);
    }


    // 0: in thong ke nhan vien 1: in thong ke theo khach hang 2: in thong ke theo san pham 
    public static void thongKeHoaDonTheoNam(int luaChon) {
        int namBatDau = NhapChuan.docNam("Tu nam (yyyy)");
        int namKetThuc;
        while ((namKetThuc = NhapChuan.docNam("Den nam (yyyy)")) < namBatDau)
            System.out.println(">> Nam ket thuc phai lon hon hoac bang nam bat dau!");
        Menu.clearScreen();
        switch (luaChon) {
            case 0 -> tongTienNhanVienTheoNam(namBatDau, namKetThuc);
            case 1 -> {}
            case 2 -> tongTienSanPhamTheoNam(namBatDau, namKetThuc);
        }
        NhapChuan.docChuoi("Enter de quay lai");
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
