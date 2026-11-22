package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.ChiTietHoaDon;
import mohinh.SanPham;
import rangbuoc.IChiTietDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;
import tienich.QuanLyDanhSach;

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
        String[] tieuDeCot = new String[] {"Ma HD", "Ma SP", "Don Gia Ban", "Thanh Tien", "So Luong"};
        String[][] bangDuLieu = new String[soLuong + 1][];
        bangDuLieu[0] = tieuDeCot;
        for(int i = 0; i < soLuong; i++) bangDuLieu[i+1] = DS_ChiTietHoaDon[i].xuat();
        FormatBang.inBangDuLieu("Chi Tiet Hoa Don",bangDuLieu);
        NhapChuan.docChuoi("Enter de thoat");
    }
    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSCTDH));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) 
            DS_ChiTietHoaDon[i].ghiFile(dos);
        dos.close();
    }
    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSCTDH));
        soLuong = dis.readInt();
        if (DS_ChiTietHoaDon.length < soLuong) DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
        for (int i = 0; i < soLuong; i++) {
            DS_ChiTietHoaDon[i]= new ChiTietHoaDon();
            DS_ChiTietHoaDon[i].docFile(dis);
        }
        dis.close();
    }
    private ChiTietHoaDon them(ChiTietHoaDon cthd) {
        if(kiemTraMa(cthd.getMaHoaDon(), cthd.getMaSanPham())) return null;
        if(soLuong == DS_ChiTietHoaDon.length) DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, ++soLuong);
        else soLuong++;
        return DS_ChiTietHoaDon[soLuong-1] = cthd;
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
    public boolean kiemTraMa(String maHD, String maSP) {
        for(int i = 0; i < soLuong; i++) 
            if(DS_ChiTietHoaDon[i].getMaHoaDon().equals(maHD) && DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP)) return true;
        return false;
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
        if(!QuanLyDanhSach.DS_HOADON.kiemTraMa(maHD)) return -1;
        double tongTien = 0;
        int soLuongThem = NhapChuan.docSoNguyenKhongAm("Nhap so luong chi tiet hoa don muon them");
        if(DS_ChiTietHoaDon.length < soLuong + soLuongThem) DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong + soLuongThem);
        for(int i = 0; i < soLuongThem; i++) {
            ChiTietHoaDon cthdMoi = new ChiTietHoaDon();
            cthdMoi.setMaHoaDon(maHD);
            while(true) {
                while (!QuanLyDanhSach.DS_SANPHAM.kiemTraMa(cthdMoi.setMaSanPham(NhapChuan.docChuoiKhongRong("Ma san pham")).getMaSanPham()))
                    System.out.println("Ma san pham khong ton tai");
                if(kiemTraMa(cthdMoi.getMaHoaDon(), cthdMoi.getMaSanPham())) System.out.println("Da Ton Tai Chi Tiet Hoa Don Nay!!!");
                else break;
            }
            DS_ChiTietHoaDon[soLuong++] = cthdMoi;
            SanPham sp = QuanLyDanhSach.DS_SANPHAM.timKiemMa(cthdMoi.getMaSanPham());
            cthdMoi.nhap();
            cthdMoi.setDonGiaBan(sp.getGia());
            tongTien += cthdMoi.setThanhTien(cthdMoi.getDonGiaBan() * cthdMoi.getSoLuong()).getThanhTien();
            QuanLyDanhSach.DS_NGUYENLIEU.capNhatSoLuong(maHD, sp.getSoLuong() + cthdMoi.getSoLuong());
        }
        return tongTien;
    }

    public ChiTietHoaDon docPhanTu(int index) {return DS_ChiTietHoaDon[index].saoChep();}

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
