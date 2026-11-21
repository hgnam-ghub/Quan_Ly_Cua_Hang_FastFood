package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.ChiTietHoaDon;
import rangbuoc.IChiTietDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public final class DanhSachChiTietHoaDon implements IChiTietDanhSach {
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

    @Override 
    public DanhSachChiTietHoaDon saoChep() { return new DanhSachChiTietHoaDon(this); }
    @Override
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
    public void xoaHoaDon(String maHD){
        int doLech = 0;
        for(int i = 0; i < soLuong - doLech;i++){
            while(i + doLech < soLuong && DS_ChiTietHoaDon[i + doLech].getMaHoaDon().equals(maHD)) doLech++;
            DS_ChiTietHoaDon[i] =  (i+doLech<soLuong)?DS_ChiTietHoaDon[i + doLech]:null;
        }
        soLuong-=doLech;
    }

    // --- Cac phuong thuc giao tiep ra ben ngoai ---
    public boolean kiemTraMa(String maHD, String maSP) {return timKiemViTriChiTietHoaDon(maHD, maSP) != -1;}

    public ChiTietHoaDon timKiemTheoMa(String maHD, String maSP) {
        int viTri = timKiemViTriChiTietHoaDon(maHD, maSP);
        if (viTri == -1) return null;
        return DS_ChiTietHoaDon[viTri].saoChep();
    }

    public boolean themVaoChiTietHoaDonDanhSach(ChiTietHoaDon cthd) {
        if (cthd == null) return false;
        return them(cthd.saoChep()) != null;
    }
    @Override
    public void nhapTimKiem() {
        System.out.println("**** Tim kiem chi tiet hoa don (Nhap it nhat 1 ma de tim) ****");
        DanhSachChiTietHoaDon dsKq = timKiemNhieuMa(NhapChuan.docChuoi("Ma hoa don"), NhapChuan.docChuoi("Ma san pham"));
        if(dsKq == null) System.out.println(">> khong tim thay");
        else dsKq.xuatDanhSach();
        NhapChuan.docChuoi("Enter de quay lai");
    }
    @Override
    public double nhapChiTiet(String maHD) {
        return 0;
    }

    public DanhSachChiTietHoaDon timKiemMaHD(String maHD) {return timKiemNhieuMa(maHD, "");}
    public DanhSachChiTietHoaDon timKiemMaSP(String maSP) {return timKiemNhieuMa("", maSP);}
    public DanhSachChiTietHoaDon timKiemNhieuMa(String maHD, String maSP) {
        if(maHD.isEmpty() && maSP.isEmpty()) return null;
        DanhSachChiTietHoaDon dsTam = new DanhSachChiTietHoaDon();
        for (int i = 0; i < soLuong; i++) {
            ChiTietHoaDon cthd = DS_ChiTietHoaDon[i];
            if(!maHD.isEmpty() && !cthd.getMaHoaDon().equals(maHD)) continue;
            if(!maSP.isEmpty() && !cthd.getMaSanPham().equals(maSP)) continue;
            dsTam.them(cthd.saoChep());
        }
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
}
