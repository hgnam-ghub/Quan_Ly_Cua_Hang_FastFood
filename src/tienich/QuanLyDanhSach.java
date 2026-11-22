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
     // Muc 4: tong tien hoa don theo khach hang tu nam x den nam y (dong = khach hang, cot = tung nam)
    public static void thongKeKhachHangTheoNam() {
        // 1. Nhap x, y (toi da 10 nam)
        int x, y;
        do x = NhapChuan.docSoNguyen("Tu nam x"); while (x < 1);
        do y = NhapChuan.docSoNguyen("Den nam y (toi da " + (x + 9) + ")"); while (y < x || y > x + 9);
        int soNam = y - x + 1, soKH = DS_KHACHHANG.getSoLuong();

        // 2. Tieu de, roi moi khach hang 1 dong: cong tien hoa don vao cot cua nam do
        String[][] bang = new String[soKH + 1][soNam + 2];
        bang[0][0] = "Khach hang";
        bang[0][soNam + 1] = "Tong Cong";
        for (int j = 0; j < soNam; j++) bang[0][j + 1] = String.valueOf(x + j);
        for (int k = 0; k < soKH; k++) {
            KhachHang kh = DS_KHACHHANG.layKhachHang(k);
            double[] tien = new double[soNam + 1];   // tien[soNam] = tong dong
            for (int i = 0; i < DS_HOADON.getSoLuong(); i++) {
                HoaDon hd = DS_HOADON.layHoaDon(i);
                int cot = hd.getNgayTaoDon().getYear() - x;
                if (cot >= 0 && cot < soNam && hd.getMaKH().equals(kh.getMa())) {
                    tien[cot] += hd.getTongTien();
                    tien[soNam] += hd.getTongTien();
                }
            }
            bang[k + 1][0] = kh.getMa() + " - " + kh.getHo() + " " + kh.getTen();
            for (int j = 0; j <= soNam; j++) bang[k + 1][j + 1] = String.format("%,.0f", tien[j]);
        }
        FormatBang.inBangDuLieu("Tong tien hoa don theo khach hang", bang);
        NhapChuan.docChuoi("Enter de quay lai");
    }
}
