package tienich;
import rangbuoc.IChiTietDanhSach;
import rangbuoc.IDanhSach;
import rangbuoc.ITruyVanDanhSach;
import java.io.IOException;
import java.time.LocalDate;

import mohinh.HoaDon;
import mohinh.NhanVien;
import mohinh.SanPham;
import mohinh.ChiTietHoaDon;
import mohinh.KhachHang;

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
        String[] tieuDeCot = new String[soNam + 2];
        for (int i = 1; i <= soNam; i++) tieuDeCot[i] = String.valueOf(namBatDau + i); 
        tieuDeCot[0] = "Nhan Vien";
        tieuDeCot[soNam + 1] = "tong cong";
        FormatBang.inBangThongKe(tieuDeDong, tieuDeCot, tong);
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
        String[] tieuDeCot = new String[soNam + 2];
        for (int i = 0; i < soNam; i++) tieuDeCot[i] = String.valueOf(namBatDau + i); 
        tieuDeCot[0] = "San Pham";
        tieuDeCot[soNam + 1] = "tong cong";
        FormatBang.inBangThongKe(tieuDeDong, tieuDeCot, tong);
    }
    private static void tongTienKhachHangTheoNam(int namBatDau, int namKetThuc) {
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
        // tao tieu de cot
        String[] tieuDeDong = new String[DS_KHACHHANG.getSoLuong()];
        for(int i = 0; i < DS_KHACHHANG.getSoLuong(); i++) {
            KhachHang nv = DS_KHACHHANG.docPhanTu(i);
            tieuDeDong[i] = nv.getMa() + "-" + nv.getHo() + " " + nv.getTen();
        }
        // tao tieu de dong
        String[] tieuDeCot = new String[soNam + 2];
        for (int i = 0; i < soNam; i++) tieuDeCot[i] = String.valueOf(namBatDau + i); 
        tieuDeCot[0] = "Khach Hang";
        tieuDeCot[soNam + 1] = "tong cong";
        FormatBang.inBangThongKe(tieuDeDong, tieuDeCot, tong);
    }
    public static double tongTienHoaDonTheoNgay(LocalDate dateDau, LocalDate dateCuoi) {
        int soHD = DS_HOADON.getSoLuong();
        double tong = 0;
        for(int i = 0; i < soHD; i++) {
            LocalDate ngayHD = DS_HOADON.docPhanTu(i).getNgayTaoDon();
            if(ngayHD.isBefore(dateDau) || ngayHD.isAfter(dateCuoi)) continue;
            tong += DS_HOADON.docPhanTu(i).getTongTien();
        }
        return tong;
    }
    public static double tongTienPhieuNhapHangTheoNgay(LocalDate dateDau, LocalDate dateCuoi) {
        int soPNH = DS_PHIEUNHAPHANG.getSoLuong();
        double tong = 0;
        for(int i = 0; i < soPNH; i++) {
            LocalDate ngayPNH = DS_PHIEUNHAPHANG.docPhanTu(i).getNgayTaoPhieu();
            if(ngayPNH.isBefore(dateDau) || ngayPNH.isAfter(dateCuoi)) continue;
            tong += DS_PHIEUNHAPHANG.docPhanTu(i).getTongTien();
        }
        return tong;
    } 
    private static void tongTienHoaDonTheoQuy(int nam) {
        double[][] tong = new double[3][4];
        LocalDate[][] gioiHanQuy = new LocalDate[4][2]; 
        gioiHanQuy[0] = new LocalDate[] {LocalDate.of(nam, 1, 1), LocalDate.of(nam, 3, 31)};
        gioiHanQuy[1] = new LocalDate[] {LocalDate.of(nam, 4, 1), LocalDate.of(nam, 6, 30)};
        gioiHanQuy[2] = new LocalDate[] {LocalDate.of(nam, 7, 1), LocalDate.of(nam, 9, 30)};
        gioiHanQuy[3] = new LocalDate[] {LocalDate.of(nam, 10, 1), LocalDate.of(nam, 12, 31)};
        for(int i = 0; i < 4; i++) {
            tong[0][i] = tongTienHoaDonTheoNgay(gioiHanQuy[i][0], gioiHanQuy[i][1]);
            tong[1][i] = tongTienPhieuNhapHangTheoNgay(gioiHanQuy[i][0], gioiHanQuy[i][1]);
            tong[2][i] = tong[0][i] - tong[1][i];
        }
        String[] tieuDeDong = new String[] {"tong thu", "tong chi", "loi nhuan"};
        String[] tieuDeCot = new String[] {"Hoa Don","Quy 1", "Quy 2", "Quy 3", "Quy 4", "Tong Cong"};
        FormatBang.inBangThongKe(tieuDeDong, tieuDeCot, tong);
    }
    public static void tongTienHoaDonTheoNgay() {
        LocalDate ngayBatDau = NhapChuan.docNgay("Nhap ngay bat dau");
        LocalDate ngayKetThuc;
        while (ngayBatDau.isAfter(ngayKetThuc = NhapChuan.docNgay("Nhap ngay ket thuc")))
            System.out.println("Ngay ket thuc khong duoc dung truoc ngay bat dau, nhap lai");
        System.out.println("Tong Gia Tri Hoa Don: " + tongTienHoaDonTheoNgay(ngayBatDau, ngayKetThuc));
        NhapChuan.docChuoi("Enter de quay lai");
    }

    public static void thongKeTienHoaDonTheoQuy() {
        int namXem = NhapChuan.docNam("Nam muon thong ke");
        Menu.clearScreen();
        tongTienHoaDonTheoQuy(namXem);
        NhapChuan.docChuoi("Enter de quay lai");
    }
    // 0: in thong ke nhan vien 1: in thong ke theo khach hang 2: in thong ke theo san pham 
    public static void inBangThongKeTienHoaDonTheoNam(int luaChon) {
        int namBatDau = NhapChuan.docNam("Tu nam (yyyy)");
        int namKetThuc;
        while ((namKetThuc = NhapChuan.docNam("Den nam (yyyy)")) < namBatDau)
            System.out.println(">> Nam ket thuc phai lon hon hoac bang nam bat dau!");
        Menu.clearScreen();
        switch (luaChon) {
            case 0 -> tongTienNhanVienTheoNam(namBatDau, namKetThuc);
            case 1 -> tongTienKhachHangTheoNam(namBatDau, namKetThuc);
            case 2 -> tongTienSanPhamTheoNam(namBatDau, namKetThuc);
        }
        NhapChuan.docChuoi("Enter de quay lai");
    }
}
