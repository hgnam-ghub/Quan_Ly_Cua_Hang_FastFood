package tienich;
import rangbuoc.IChiTietDanhSach;
import rangbuoc.IDanhSach;
import rangbuoc.ITruyVanDanhSach;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.ToIntFunction;

import mohinh.HoaDon;
import mohinh.NhanVien;

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
private static String[][] lapBangThongKeNhanVien(String[] tieuDeCot, ToIntFunction<LocalDate> chiSoCot) {
        int soCot = tieuDeCot.length;
        int soNV = DS_NHANVIEN.getSoLuong();
        double[][] tong = new double[soNV][soCot];

        for (int i = 0; i < DS_HOADON.getSoLuong(); i++) {
            HoaDon hd = DS_HOADON.docPhanTu(i);
            int cot = chiSoCot.applyAsInt(hd.getNgayTaoDon());
            if (cot < 0 || cot >= soCot) continue;
            for (int j = 0; j < soNV; j++)
                if (DS_NHANVIEN.docPhanTu(j).getMa().equals(hd.getMaNV())) {
                    tong[j][cot] += hd.getTongTien();
                    break;
                }
        }

        String[][] bang = new String[soNV + 1][soCot + 2];
        bang[0][0] = "Nhan vien";
        for (int c = 0; c < soCot; c++) bang[0][c + 1] = tieuDeCot[c];
        bang[0][soCot + 1] = "Tong Cong";
        for (int j = 0; j < soNV; j++) {
            NhanVien nv = DS_NHANVIEN.docPhanTu(j);
            double tongNV = 0;
            bang[j + 1][0] = nv.getMa() + " - " + nv.getHo() + " " + nv.getTen();
            for (int c = 0; c < soCot; c++) {
                bang[j + 1][c + 1] = String.format("%,.0f", tong[j][c]);
                tongNV += tong[j][c];
            }
            bang[j + 1][soCot + 1] = String.format("%,.0f", tongNV);
        }
        return bang;
    }

     public static String[][] thongKeTongTienNhanVienTheoNam(int namBatDau, int namKetThuc) {
        String[] tieuDe = new String[namKetThuc - namBatDau + 1];
        for (int i = 0; i < tieuDe.length; i++) tieuDe[i] = String.valueOf(namBatDau + i);
        return lapBangThongKeNhanVien(tieuDe, ngay -> ngay.getYear() - namBatDau);
    }
}
