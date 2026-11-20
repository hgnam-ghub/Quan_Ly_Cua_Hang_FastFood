package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.ChiTietHoaDon;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public final class DanhSachChiTietHoaDon implements IDanhSach {
    private ChiTietHoaDon[] DS_ChiTietHoaDon;
    private int soLuong;

    public DanhSachChiTietHoaDon() {
        soLuong = 0;
        DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
    }

    public DanhSachChiTietHoaDon(DanhSachChiTietHoaDon ds_CTHD) {
        soLuong = ds_CTHD.soLuong;
        DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_ChiTietHoaDon[i] = ds_CTHD.DS_ChiTietHoaDon[i].saoChep();
    }

    public DanhSachChiTietHoaDon saoChep() { return new DanhSachChiTietHoaDon(this); }
    public int getSoLuong() { return soLuong; }

    @Override
    public void xuatDanhSach() {
        String[] header = {"MaHD", "MaSP", "Don Gia Ban", "Thanh Tien", "So Luong"};
        String[][] bangDuLieu = new String[soLuong + 1][5];
        bangDuLieu[0] = header;
        for (int i = 0; i < soLuong; i++)
            bangDuLieu[i + 1] = DS_ChiTietHoaDon[i].xuat();
        FormatBang.inBangDuLieu("DANH SACH CHI TIET HOA DON", bangDuLieu);
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSCTDH));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) {
            DS_ChiTietHoaDon[i].ghiFile(dos);
        }
        dos.close();
    }

    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSCTDH));
        soLuong = dis.readInt();
        if (DS_ChiTietHoaDon.length < soLuong) DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
        for (int i = 0; i < soLuong; i++) {
            DS_ChiTietHoaDon[i] = new ChiTietHoaDon();
            DS_ChiTietHoaDon[i].docFile(dis);
        }
        dis.close();
    }

    // --- Phuong thuc noi bo ---
    private int timKiemViTriChiTietHoaDon(String maHD, String maSP) {
        for (int i = 0; i < soLuong; i++)
            if (DS_ChiTietHoaDon[i] != null 
                && DS_ChiTietHoaDon[i].getMaHoaDon().equals(maHD) 
                && DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP))
                return i;
        return -1;
    }

    private ChiTietHoaDon them(ChiTietHoaDon cthd) {
        if (kiemTraMa(cthd.getMaHoaDon(), cthd.getMaSanPham())) return null;
        if (soLuong == DS_ChiTietHoaDon.length) DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong + 1);
        DS_ChiTietHoaDon[soLuong++] = cthd;
        return cthd;
    }

    private ChiTietHoaDon sua(String maHD, String maSP) {
        int viTri = timKiemViTriChiTietHoaDon(maHD, maSP);
        if (viTri == -1) return null;
        return DS_ChiTietHoaDon[viTri].nhap();
    }

    private boolean xoa(String maHD, String maSP) {
        int viTri = timKiemViTriChiTietHoaDon(maHD, maSP);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++)
            DS_ChiTietHoaDon[i] = DS_ChiTietHoaDon[i + 1];
        DS_ChiTietHoaDon[--soLuong] = null;
        return true;
    }

    // --- Cac phuong thuc giao tiep ra ben ngoai ---
    public boolean kiemTraMa(String maHD, String maSP) {
        return timKiemViTriChiTietHoaDon(maHD, maSP) != -1;
    }

    public ChiTietHoaDon timKiemTheoMa(String maHD, String maSP) {
        int viTri = timKiemViTriChiTietHoaDon(maHD, maSP);
        if (viTri == -1) return null;
        return DS_ChiTietHoaDon[viTri].saoChep();
    }

    public boolean themVaoChiTietHoaDonDanhSach(ChiTietHoaDon cthd) {
        if (cthd == null) return false;
        return them(cthd.saoChep()) != null;
    }

    public boolean themChiTietHoaDon(String maHD) {
        String maSP;
        while (true) {
            maSP = NhapChuan.docChuoiKhongRong("Ma San Pham");
            if (!kiemTraMa(maHD, maSP)) break;
            System.out.println(">> Ma hoa don va san pham nay da ton tai, nhap ma san pham khac!!");
        }
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        cthd.nhap();
        return them(cthd) != null;
    }

    @Override
    public void nhapTimKiem() {
        String maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don can tim");
        String maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham can tim");
        ChiTietHoaDon kq = timKiemTheoMa(maHD, maSP);
        if (kq == null) {
            System.out.println(">> Khong tim thay chi tiet hoa don!");
        } else {
            String[][] bang = new String[][] {
                {"MaHD", "MaSP", "Don Gia Ban", "Thanh Tien", "So Luong"},
                kq.xuat()
            };
            FormatBang.inBangDuLieu("KET QUA TIM KIEM", bang);
        }
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void nhapThem() {
        System.out.println("**** Bat dau nhap danh sach chi tiet hoa don ****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong chi tiet hoa don muon them");
        DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            System.out.println("Chi tiet hoa don thu " + (soLuong + 1)+": ");
            DS_ChiTietHoaDon[i] = new ChiTietHoaDon();
            DS_ChiTietHoaDon[i].setMa(NhapChuan.docChuoiKhongRong("Nhap ma hoa don"), NhapChuan.docChuoiKhongRong("Nhap ma san pham"));
            DS_ChiTietHoaDon[i].nhap();
            soLuong++;
        }
        System.out.println("**** Ket thuc nhap danh sach chi tiet hoa don ****");
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void nhapSua() {
        do {
            String maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
            String maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham");
            if (sua(maHD, maSP) == null)
                System.out.println(">> Chi tiet hoa don nay khong ton tai trong danh sach");
            else
                System.out.println(">> Sua thanh cong");
            System.out.println("Co tiep tuc nhap sua them chi tiet hoa don khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    @Override
    public void nhapXoa() {
        do {
            String maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
            String maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham");
            if (!xoa(maHD, maSP))
                System.out.println(">> Khong tim thay chi tiet hoa don can xoa");
            else
                System.out.println(">> Xoa thanh cong");
            System.out.println("Co tiep tuc xoa cac chi tiet hoa don khac khong?");
        } while (NhapChuan.docYesNo("", false));
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
                dsTam.them(DS_ChiTietHoaDon[i].saoChep());
        return (dsTam.soLuong == 0) ? null : dsTam;
    }

    public DanhSachChiTietHoaDon timKiemMaSP(String maSP) {
        DanhSachChiTietHoaDon dsTam = new DanhSachChiTietHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_ChiTietHoaDon[i] != null && DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP))
                dsTam.them(DS_ChiTietHoaDon[i].saoChep());
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
}
