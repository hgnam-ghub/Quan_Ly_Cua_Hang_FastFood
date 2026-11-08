package danhsach;

import java.util.Arrays;

import mohinh.DoAn;
import mohinh.DoUong;
import mohinh.SanPham;
import quanly.NhapChuan;

public class DanhSachSanPham {
    private SanPham[] DS_SanPham;
    private int soLuong;

    public DanhSachSanPham() {
        soLuong = 0;
        DS_SanPham = new SanPham[soLuong];
    }
    public DanhSachSanPham(DanhSachSanPham DS_SP) {
        soLuong = DS_SP.soLuong;
        DS_SanPham = new SanPham[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_SanPham[i] = DS_SP.DS_SanPham[i].saoChep();
    }

    public void nhap() {
        System.out.println("**********************");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong san pham muon them");
        DS_SanPham = Arrays.copyOf(DS_SanPham, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("-------------");
            System.out.println("Chon loai san pham muon them:");
            System.out.println("1: do an");
            System.out.println("2: do uong");
            String maSP;
            int luaChon = NhapChuan.docSoNguyenKhongAm("Nhap lua chon");
            if(luaChon >= 1 && luaChon <= 2) {
                while (true) {
                    maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham");
                    if(kiemTraMa(maSP)) break;
                    System.out.println("Ma san pham nay da ton tai, nhap ma khac !!!");
                }
                switch (luaChon) {
                    case 1:
                        DS_SanPham[soLuong] = new DoAn(maSP);
                        break;
                    case 2:
                        DS_SanPham[soLuong] = new DoUong(maSP);
                        break;
                }
            }
            else {
                System.out.println("khong co lua chon nay, vui long nhap lai!!!");
                continue;
            }
            DS_SanPham[soLuong].nhap();
            soLuong++;
        }
    }
    public void xuat() {
        System.out.println("---Danh Sach Cac San Pham---");
        for(int i = 0; i < soLuong; i++) {
            System.out.println("------------");
            DS_SanPham[i].xuat();
        }
        System.out.println("---END---");
    }


    // tim kiem vi tri san pham theo ma
    private int timKiemViTriSanPham(String maSP) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_SanPham[i] != null && DS_SanPham[i].getMa().equals(maSP)) return i;
        }
        return -1;
    }
    public boolean kiemTraMa(String maSP) {return timKiemViTriSanPham(maSP) != -1;}
    public SanPham timKiemSanPham(String maSP) {
        int viTri = timKiemViTriSanPham(maSP);
        return (viTri != -1)?DS_SanPham[viTri]:null;
    }

    // nhan them 1 san pham (sao chep va tham chieu)
    public boolean nhapSanPham(SanPham spThemVo) {
        if(kiemTraMa(spThemVo.getMa())) return false;
        if(soLuong == DS_SanPham.length) DS_SanPham = Arrays.copyOf(DS_SanPham, soLuong + 1);
        DS_SanPham[soLuong++] = spThemVo.saoChep();
        return true;
    }
    private boolean nhapThamChieu(SanPham spThamChieu) {
        if(kiemTraMa(spThamChieu.getMa())) return false;
        if(soLuong == DS_SanPham.length) DS_SanPham = Arrays.copyOf(DS_SanPham, soLuong + 1);
        DS_SanPham[soLuong++] = spThamChieu;
        return true;
    }

    public DanhSachSanPham timKiemTheoTen(String tenSanPham) {
        DanhSachSanPham dsTam = new DanhSachSanPham();
        for(int i = 0; i < soLuong; i++)
            if(DS_SanPham[i] != null && DS_SanPham[i].getTen().equals(tenSanPham))
                dsTam.nhapThamChieu(DS_SanPham[i]);
        if(dsTam.soLuong == 0) return null;
        return dsTam;
    }

}
