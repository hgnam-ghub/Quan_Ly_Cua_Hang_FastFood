package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.ChiTietPhieuNhapHang;
import mohinh.NguyenLieu;
import rangbuoc.IChiTietDanhSach;
import rangbuoc.ITruyVanDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;
import tienich.QuanLyDanhSach;

public class DanhSachChiTietPhieuNhapHang implements IChiTietDanhSach{
    private ChiTietPhieuNhapHang[] DS_ChiTietPhieuNhapHang;
    private int soLuong;

    public DanhSachChiTietPhieuNhapHang() {
        soLuong = 0; 
        DS_ChiTietPhieuNhapHang = new ChiTietPhieuNhapHang[0];
    }

    public DanhSachChiTietPhieuNhapHang(DanhSachChiTietPhieuNhapHang ds_ctpnh) {
        soLuong = ds_ctpnh.soLuong;
        if(soLuong > DS_ChiTietPhieuNhapHang.length) DS_ChiTietPhieuNhapHang = new ChiTietPhieuNhapHang[soLuong];
        for(int i = 0; i < soLuong; i++) 
            DS_ChiTietPhieuNhapHang[i] = ds_ctpnh.DS_ChiTietPhieuNhapHang[i];
    }

    @Override
    public ITruyVanDanhSach saoChep() {return new DanhSachChiTietPhieuNhapHang(this);}

    @Override
    public int getSoLuong() {return soLuong;}

    @Override
    public boolean nhapChiTiet(String maPNH){
        if(!QuanLyDanhSach.DS_PHIEUNHAPHANG.kiemTraMa(maPNH)) return false;
        if(DS_ChiTietPhieuNhapHang.length < this.soLuong + soLuong) DS_ChiTietPhieuNhapHang = Arrays.copyOf(DS_ChiTietPhieuNhapHang, this.soLuong + soLuong);
        for(int i = 0; i < soLuong; i++) {
            ChiTietPhieuNhapHang ctpnhMoi = new ChiTietPhieuNhapHang();
            ctpnhMoi.setMaPNH(maPNH);
            while(true) {
                while (!QuanLyDanhSach.DS_NGUYENLIEU.kiemTraMa(ctpnhMoi.setMaNL(NhapChuan.docChuoiKhongRong("Ma nguyen Lieu")).getMaNL()))
                    System.out.println("Ma Nguyen Lieu khong ton tai");
                if(kiemTraMa(ctpnhMoi.getMaPNH(), ctpnhMoi.getMaNL())) System.out.println("Da Ton Tai Chi Tiet Phieu Nhap Hang Nay!!!");
                else break;
            }
            DS_ChiTietPhieuNhapHang[soLuong++] = ctpnhMoi;
            NguyenLieu nl = QuanLyDanhSach.DS_NGUYENLIEU.timKiemMa(ctpnhMoi.getMaNL());
            while (ctpnhMoi.nhap().getSoLuong() > nl.getSoLuong())
                System.out.println("So nguyen lieu con lai trong kho trong du");
            ctpnhMoi.setDonGiaNhap(nl.getDonGia());
            ctpnhMoi.setThanhTien(ctpnhMoi.getDonGiaNhap() * ctpnhMoi.getSoLuong());
            QuanLyDanhSach.DS_NGUYENLIEU.capNhatSoLuong(maPNH, ctpnhMoi.getSoLuong());
        }
        return true;
    }
    
    @Override
    public void nhapTimKiem() {
        
    }
    @Override
    public void xuatDanhSachRaFile() throws IOException{
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSCTPNH));
        dos.writeInt(soLuong);
        for(int i = 0; i < soLuong; i++) DS_ChiTietPhieuNhapHang[i].ghiFile(dos);
        dos.close();
    }
    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSCTPNH));
        soLuong = dis.readInt();
        if(soLuong > DS_ChiTietPhieuNhapHang.length) DS_ChiTietPhieuNhapHang = new ChiTietPhieuNhapHang[soLuong];
        for(int i = 0; i < soLuong; i++) DS_ChiTietPhieuNhapHang[i].docFile(dis);
        dis.close();
    }

    public DanhSachChiTietPhieuNhapHang timKiemMaPNH(String maPNH) {
        return null;
    }
    public DanhSachChiTietPhieuNhapHang timKiemMaNL(String maNL) {
        return null;
    }
    public boolean kiemTraMa(String maPNH, String maNL) {
        for(int i = 0; i < soLuong; i++) 
            if(DS_ChiTietPhieuNhapHang[i].getMaPNH().equals(maPNH) && DS_ChiTietPhieuNhapHang[i].getMaNL().equals(maNL)) return true;
        return false;
    }

    @Override 
    public void xuatDanhSach() {
        String[] tieuDeCot = new String[] {"Ma PNH", "Ma NL", "Don Gia Nhap", "Thanh Tien", "So Luong"};
        String[][] bangDuLieu = new String[soLuong + 1][];
        bangDuLieu[0] = tieuDeCot;
        for(int i = 0; i < soLuong; i++) bangDuLieu[i+1] = DS_ChiTietPhieuNhapHang[i].xuat();
        FormatBang.inBangDuLieu("Chi Tiet Phieu Nhap Hang",bangDuLieu);
    }
}
