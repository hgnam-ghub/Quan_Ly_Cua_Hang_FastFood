package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.HoaDon;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;
import tienich.QuanLyDanhSach;
import rangbuoc.IDanhSach;

public class DanhSachHoaDon implements IDanhSach {
    private HoaDon[] DS_HoaDon;
    private int soLuong;

    public DanhSachHoaDon () {
        soLuong = 0;
        DS_HoaDon = new HoaDon[soLuong];
    }
    public DanhSachHoaDon(DanhSachHoaDon ds_HD) {
        soLuong = ds_HD.soLuong;
        DS_HoaDon = new HoaDon[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_HoaDon[i] = ds_HD.DS_HoaDon[i].saoChep();  
    }
    @Override 
    public DanhSachHoaDon saoChep() {return new DanhSachHoaDon(this);}
    @Override 
    public int getSoLuong() { return soLuong; }

    @Override
    public void xuatDanhSach() {
        String[] tieuDeCot= new String[] {"Ma HD", "Ma NV", "Ma KH", "Ngay lap", "Phuong Thuc Thanh Toan", "Trang Thai", "Tong Tien"};
        String[][] bangDuLieu = new String[soLuong + 1][];
        bangDuLieu[0] = tieuDeCot;
        for(int i = 0; i < soLuong; i++)
            bangDuLieu[i+1] = DS_HoaDon[i].xuat();
        FormatBang.inBangDuLieu("Danh sach hoa don",bangDuLieu);
        NhapChuan.docChuoi("Enter de quay lai");
    }
    @Override
    public void xuatDanhSachRaFile() throws IOException{
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSHD));
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++) DS_HoaDon[i].ghiFile(dos);
        dos.close();
    }
    @Override
    public void nhapDanhSachTuFile() throws IOException{
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSHD));
            soLuong = dis.readInt();
            if(soLuong > DS_HoaDon.length) DS_HoaDon = new HoaDon[soLuong];
            for(int i = 0; i < soLuong; i++) { DS_HoaDon[i] = new HoaDon(); DS_HoaDon[i].docFile(dis);}
        dis.close();
    }
    private int timKiemViTriHoaDon(String maHD) {
        for(int i = 0; i < soLuong; i++)
            if(DS_HoaDon[i].getMaHD().equals(maHD)) return i;
        return -1;
    }
    private boolean them(HoaDon hd) {
        if(kiemTraMa(hd.getMaHD())) return false;
        if(soLuong == DS_HoaDon.length) DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong+1);
        DS_HoaDon[soLuong++] = hd.saoChep();
        return true;
    }
    public boolean kiemTraMa(String maHD) {
        for(int i = 0; i < soLuong; i++)
            if(DS_HoaDon[i].getMaHD().equals(maHD)) return true;
        return false;
    }
    @Override
    public void nhapTimKiem() {
        System.out.println("**** Tim kiem hoa don ****");
        String maHD = NhapChuan.docChuoi("Ma hoa don can tim");
        int viTriHD = timKiemViTriHoaDon(maHD); 
        if(viTriHD == -1)
            System.out.println("Khong tim thay phieu nhap hang nay");
        else {
            DanhSachHoaDon dsTam = new DanhSachHoaDon();
            dsTam.them((DS_HoaDon[viTriHD]));
            dsTam.xuatDanhSach();
        }
    }
    @Override
    public void nhapThem() {
        System.out.println("==============================");
        int soLuongThem = NhapChuan.docSoNguyenKhongAm("So luong hoa don muon them");
        if(DS_HoaDon.length < soLuong + soLuongThem) DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong + soLuongThem);
        for(int i = 0; i < soLuongThem; i++) {
            HoaDon hdMoi = new HoaDon();
            while (kiemTraMa(hdMoi.setMaHD(NhapChuan.docChuoiKhongRong("Ma Hoa Don")).getMaHD()))
                System.out.println("Hoa don nay da ton tai");
            while (!QuanLyDanhSach.DS_NHANVIEN.kiemTraMa(hdMoi.setMaNV(NhapChuan.docChuoiKhongRong("Ma Nhan vien")).getMaNV()))
                System.out.println("Khong tim thay nhan vien nay!!!");
            while (!QuanLyDanhSach.DS_KHACHHANG.kiemTraMa(hdMoi.setMaKH(NhapChuan.docChuoiKhongRong("Ma Khach hang")).getMaKH()))
                System.out.println("khong tim thay khach hang nay!!!");
            DS_HoaDon[soLuong++] = hdMoi;
            hdMoi.nhap();
            hdMoi.setTongTien(QuanLyDanhSach.DS_CHITIETHOADON.nhapChiTiet(hdMoi.getMaHD()));
            NhapChuan.docChuoi("Enter de quay lai");
        }
    }
     @Override
    public void nhapSua() {
        do {
            int viTri = timKiemViTriHoaDon(NhapChuan.docChuoiKhongRong("Nhap Ma hoa don muon sua"));
            if(viTri == -1) {System.out.println("khong ton tai ma hoa don nay!!!"); continue;}
            DS_HoaDon[viTri].nhap();
        } while (NhapChuan.docYesNo("Co muon nhap tiep hay khong", false));
    }
    @Override
    public void nhapXoa() {
        do {
            int viTri = timKiemViTriHoaDon(NhapChuan.docChuoiKhongRong("Nhap Ma hoa don muon xoa"));
            if(viTri == -1) {System.out.println("khong ton tai ma hoa don nay!!!"); continue;}
            for(int i = viTri; i < soLuong - 1; i++)
                DS_HoaDon[viTri + 1] = DS_HoaDon[viTri];
            soLuong--;
        } while (NhapChuan.docYesNo("Co muon xoa tiep hay khong", false));
    }
}
