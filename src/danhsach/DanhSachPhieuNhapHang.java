package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.PhieuNhapHang;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;
import tienich.QuanLyDanhSach;

public class DanhSachPhieuNhapHang implements IDanhSach{
    private PhieuNhapHang[] DS_PhieuNhapHang;
    private int soLuong;

    public DanhSachPhieuNhapHang() {
        soLuong = 0;
        DS_PhieuNhapHang = new PhieuNhapHang[0];
    }
    public DanhSachPhieuNhapHang(DanhSachPhieuNhapHang ds_pnh) {
        soLuong = ds_pnh.soLuong;
        DS_PhieuNhapHang = new PhieuNhapHang[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_PhieuNhapHang[i] = ds_pnh.DS_PhieuNhapHang[i].saoChep();
    }

    @Override
    public DanhSachPhieuNhapHang saoChep() {return new DanhSachPhieuNhapHang(this);}
    @Override
    public int getSoLuong() {return soLuong;}
    @Override
    public void xuatDanhSach() {
        String[] tieuDeCot= new String[] {"Ma PNH", "Ma NV", "Ma NCC", "Ngay Tao Phieu", "Trang Thai", "Phuong Thuc Thanh Toan", "Tong Tien"};
        String[][] bangDuLieu = new String[soLuong + 1][];
        bangDuLieu[0] = tieuDeCot;
        for(int i = 0; i < soLuong; i++)
            bangDuLieu[i+1] = DS_PhieuNhapHang[i].xuat();
        FormatBang.inBangDuLieu("Phieu Nhap Hang",bangDuLieu);
        System.out.println("So luong: " + soLuong);
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSPNH));
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++) DS_PhieuNhapHang[i].ghiFile(dos);
        dos.close();
    }

    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSPNH));
            soLuong = dis.readInt();
            if(soLuong > DS_PhieuNhapHang.length) DS_PhieuNhapHang = new PhieuNhapHang[soLuong];
            for(int i = 0; i < soLuong; i++) {DS_PhieuNhapHang[i] = new PhieuNhapHang(); DS_PhieuNhapHang[i].docFile(dis);}
        dis.close();
    }

    public boolean kiemTraMa(String maPNH) {
        for(int i = 0; i < soLuong; i++)
            if(DS_PhieuNhapHang[i].getMaPNH().equals(maPNH)) return true;
        return false;
    }

    private boolean them(PhieuNhapHang pnh) {
        if(kiemTraMa(pnh.getMaPNH())) return false;
        if(soLuong == DS_PhieuNhapHang.length) DS_PhieuNhapHang = Arrays.copyOf(DS_PhieuNhapHang, ++soLuong);
        else ++soLuong;
        DS_PhieuNhapHang[soLuong - 1] = pnh.saoChep();
        return true;
    }

    private int timViTriPhieuNhapHang(String maPNH) {
        for(int i =0; i < soLuong; i++)
            if(DS_PhieuNhapHang[i].getMaPNH().equals(maPNH)) return i;
        return -1;
    }

    @Override 
    public void nhapTimKiem() {
        System.out.println("**** Tim kiem phieu nhap hang ****");
        String maPNH = NhapChuan.docChuoi("Ma phieu can tim");
        int viTriPhieu = timViTriPhieuNhapHang(maPNH); 
        if(viTriPhieu == -1)
            System.out.println("Khong tim thay phieu nhap hang nay");
        else {
            DanhSachPhieuNhapHang dsTam = new DanhSachPhieuNhapHang();
            dsTam.them((DS_PhieuNhapHang[viTriPhieu]));
            dsTam.xuatDanhSach();
        }
    }
    public PhieuNhapHang docPhanTu(int i) {return DS_PhieuNhapHang[i];}

    @Override 
    public void nhapThem() {
        System.out.println("==============================");
        int soLuongThem = NhapChuan.docSoNguyenKhongAm("So luong phieu nhap muon them");
        if(DS_PhieuNhapHang.length < soLuong + soLuongThem) DS_PhieuNhapHang = Arrays.copyOf(DS_PhieuNhapHang, soLuong + soLuongThem);
        for(int i = 0; i < soLuongThem; i++) {
            PhieuNhapHang pnhMoi = new PhieuNhapHang();
            while (kiemTraMa(pnhMoi.setMaPNH(NhapChuan.docChuoiKhongRong("Ma Phieu Nhap Hang")).getMaPNH()))
                System.out.println("Phieu Nhap Hang nay da ton tai");
            while (!QuanLyDanhSach.DS_NHANVIEN.kiemTraMa(pnhMoi.setMaNV(NhapChuan.docChuoiKhongRong("Ma Nhan vien")).getMaNV()))
                System.out.println("Khong tim thay nhan vien nay!!!");
            while (!QuanLyDanhSach.DS_NHACUNGCAP.kiemTraMa(pnhMoi.setMaNCC(NhapChuan.docChuoiKhongRong("Ma Nha Cung Cap")).getMaNCC()))
                System.out.println("khong tim thay nha cung cap nay!!!");
            DS_PhieuNhapHang[soLuong++] = pnhMoi;
            pnhMoi.nhap();
            pnhMoi.setTongTien(QuanLyDanhSach.DS_CHITIETPHIEUNHAPHANG.nhapChiTiet(pnhMoi.getMaPNH()));
            NhapChuan.docChuoi("Enter de quay lai");
        }
    }
    
    @Override 
    public void nhapSua() {
        do {
            int viTri = timViTriPhieuNhapHang(NhapChuan.docChuoiKhongRong("Nhap Ma Phieu Nhap Hang muon sua"));
            if(viTri == -1) {System.out.println("khong ton tai ma phieu nhap hang nay!!!"); continue;}
            DS_PhieuNhapHang[viTri].nhap();
        } while (NhapChuan.docYesNo("Co muon nhap tiep hay khong", false));
    }

    @Override
    public void nhapXoa() {
        do {
            int viTri = timViTriPhieuNhapHang(NhapChuan.docChuoiKhongRong("Nhap Ma Phieu Nhap Hang muon xoa"));
            if(viTri == -1) {System.out.println("khong ton tai ma phieu nhap hang nay!!!"); continue;}
            QuanLyDanhSach.DS_CHITIETPHIEUNHAPHANG.xoaPhieu(DS_PhieuNhapHang[viTri].getMaPNH());
            for(int i = viTri; i < soLuong - 1; i++)
                DS_PhieuNhapHang[viTri + 1] = DS_PhieuNhapHang[viTri];
            soLuong--;
        } while (NhapChuan.docYesNo("Co muon xoa tiep hay khong", false));
    }
}
