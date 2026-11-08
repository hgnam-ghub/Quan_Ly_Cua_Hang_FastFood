package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.ChiTietHoaDon;
import quanly.NhapChuan;
import rangbuoc.ITruyXuatDanhSach;

public final class DanhSachChiTietHoaDon implements ITruyXuatDanhSach {
    private ChiTietHoaDon[] DS_ChiTietHoaDon;
    private int soLuong;
    private static final String fileDanhSach = "data/DS_ChiTietHoaDon.dat";

    public int getSoLuong() { return soLuong; }

    public DanhSachChiTietHoaDon() {
        soLuong = 0;
        DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
    }
    public DanhSachChiTietHoaDon(DanhSachChiTietHoaDon ds_CTHD) {
        soLuong = ds_CTHD.soLuong;
        DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_ChiTietHoaDon[i] = ds_CTHD.DS_ChiTietHoaDon[i].saoChep(); 
    }

    // --- Triển khai các phương thức từ ITruyXuatDanhSach ---
    @Override
    public void nhapThem() {
        System.out.println("**** Bat dau nhap danh sach chi tiet hoa don ****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong chi tiet hoa don muon them");
        DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maHD, maSP;
            while (true) {
                maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
                maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham");
                if(!kiemTraMa(maHD, maSP)) break;
                System.out.println(">> Ma hoa don va san pham nay da ton tai, nhap lai!!");
            }
            DS_ChiTietHoaDon[soLuong] = new ChiTietHoaDon(maHD, maSP);
            DS_ChiTietHoaDon[soLuong++].nhap();
        }
        System.out.println("**** Ket thuc nhap danh sach chi tiet hoa don ****");
    }
    @Override
    public void xuatDanhSach() {
        System.out.println("==== Danh Sach Cac Chi Tiet Hoa Don ====");
        for(int i = 0; i < soLuong; i++) {
            System.out.println("------------");
            DS_ChiTietHoaDon[i].xuat();
        }
        System.out.println("==== Het ====");
    }

    @Override
    public void xuatDanhSachRaFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileDanhSach))) {
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++) {
                DS_ChiTietHoaDon[i].ghiFile(dos);
            }
        } catch (IOException e) {
            System.out.println(">> Loi trong qua trinh xuat danh sach chi tiet hoa don ra file");
            e.printStackTrace();
        }
    }
    @Override
    public void nhapDanhSachTuFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileDanhSach))) {
            soLuong = dis.readInt();
            if(soLuong > DS_ChiTietHoaDon.length) DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
            for(int i = 0; i < soLuong; i++) {
                DS_ChiTietHoaDon[i] = new ChiTietHoaDon("", "");
                DS_ChiTietHoaDon[i].docFile(dis);
            }
        } catch (IOException e) {
            System.out.println(">> Co loi trong qua trinh nhap danh sach chi tiet hoa don tu file");
            e.printStackTrace();
        }
    }
    // --- Các phương thức nội bộ bổ trợ ---
    private boolean them(ChiTietHoaDon cthd) {
        if(kiemTraMa(cthd.getMaHoaDon(), cthd.getMaSanPham())) return false;
        if(soLuong == DS_ChiTietHoaDon.length) DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong + 1);
        DS_ChiTietHoaDon[soLuong++] = cthd;
        return true;
    }
    public boolean themChiTietHoaDon(String maHD) {
        String maSP;
        while (true) {
            maSP = NhapChuan.docChuoiKhongRong("Ma San Pham");
            if(!kiemTraMa(maHD, maSP)) break;
            System.out.println(">> Ma hoa don va san pham nay da ton tai, nhap ma san pham khac!!");
        }
        ChiTietHoaDon cthd = new ChiTietHoaDon(maHD, maSP);
        cthd.nhap();
        return them(cthd);
    }

    private boolean sua(String maHD, String maSP) {
        int viTri = timKiemViTriChiTietHoaDon(maHD, maSP);
        if(viTri == -1) return false;
        DS_ChiTietHoaDon[viTri].nhap();
        return true;
    }
    @Override
    public void nhapSua() {
        String maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
        String maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham");
        if(!sua(maHD, maSP))
            System.out.println(">> Chi tiet hoa don khong ton tai trong danh sach!");
    }

    private boolean xoa(String maHD, String maSP) {
        int viTri = timKiemViTriChiTietHoaDon(maHD, maSP);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++) 
            DS_ChiTietHoaDon[i] = DS_ChiTietHoaDon[i + 1];
        DS_ChiTietHoaDon[--soLuong] = null;
        return true;
    }
    @Override
    public void nhapXoa() {
        String maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
        String maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham");
        if(!xoa(maHD, maSP))
            System.out.println(">> Khong tim thay chi tiet hoa don can xoa!");
    }

    private int timKiemViTriChiTietHoaDon(String maHD, String maSP) {
        for(int i = 0; i < soLuong; i++) 
            if(DS_ChiTietHoaDon[i] != null && DS_ChiTietHoaDon[i].getMaHoaDon().equals(maHD) && DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP)) 
                return i;
        return -1;
    }
    public boolean kiemTraMa(String maHD, String maSP) {
        return timKiemViTriChiTietHoaDon(maHD, maSP) != -1;
    }
    public ChiTietHoaDon timKiemChiTietHoaDon(String maHD, String maSP) {
        int viTri = timKiemViTriChiTietHoaDon(maHD, maSP);
        return (viTri != -1) ? DS_ChiTietHoaDon[viTri] : null;
    }

    public void xoaTheoDanhSach(DanhSachChiTietHoaDon DS_HD) {
        for (int i = 0; i < DS_HD.soLuong; i++) 
            xoa(DS_HD.DS_ChiTietHoaDon[i].getMaHoaDon(), DS_HD.DS_ChiTietHoaDon[i].getMaSanPham());
    }
    public void nhapTuDanhSach(DanhSachChiTietHoaDon DS_HD) {
        for (int i = 0; i < DS_HD.soLuong; i++) 
            if (!kiemTraMa(DS_HD.DS_ChiTietHoaDon[i].getMaHoaDon(), DS_HD.DS_ChiTietHoaDon[i].getMaSanPham())) 
                them(DS_HD.DS_ChiTietHoaDon[i].saoChep());
    }

    public DanhSachChiTietHoaDon timKiemMaHD(String maHD) {
        DanhSachChiTietHoaDon dsTam = new DanhSachChiTietHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_ChiTietHoaDon[i] != null && DS_ChiTietHoaDon[i].getMaHoaDon().equals(maHD)) 
                dsTam.them(DS_ChiTietHoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachChiTietHoaDon timKiemMaSP(String maSP) {
        DanhSachChiTietHoaDon dsTam = new DanhSachChiTietHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_ChiTietHoaDon[i] != null && DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP)) 
                dsTam.them(DS_ChiTietHoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
}
