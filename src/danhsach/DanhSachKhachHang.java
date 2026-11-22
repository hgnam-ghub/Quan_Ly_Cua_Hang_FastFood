package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.KhachHang;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;
public class DanhSachKhachHang implements IDanhSach {
    private KhachHang[] DS_KhachHang;
    private int soLuong;
    public DanhSachKhachHang() {
        soLuong = 0;
        DS_KhachHang = new KhachHang[soLuong];
    }
    public DanhSachKhachHang(DanhSachKhachHang DS_KH) {
        soLuong = DS_KH.soLuong;
        DS_KhachHang = new KhachHang[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_KhachHang[i] = DS_KH.DS_KhachHang[i].saoChep();
    }
    @Override 
    public DanhSachKhachHang saoChep() { return new DanhSachKhachHang(this); }
    @Override 
    public int getSoLuong() { return soLuong; }
    public KhachHang docPhanTu(int index) { return DS_KhachHang[index].saoChep(); }
    @Override
    public void xuatDanhSach() {
        String[] tieuDeCot= new String[] {"Ma KH", "Ho", "Ten", "So dien thoai", "Ngay sinh", "Tong tien da mua"};
        String[][] bangDuLieu = new String[soLuong + 1][];
        bangDuLieu[0] = tieuDeCot;
        for(int i = 0; i < soLuong; i++)
            bangDuLieu[i+1] = DS_KhachHang[i].xuat();
        FormatBang.inBangDuLieu("Danh sach khach hang",bangDuLieu);
        NhapChuan.docChuoi("Enter de quay lai");
    }
    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSKH));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) 
            DS_KhachHang[i].ghiFile(dos);
        dos.close();
    }
    @Override
    public void nhapDanhSachTuFile() throws IOException {
        int i = 0;
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSKH));
        soLuong = dis.readInt();
        if (DS_KhachHang.length < soLuong) DS_KhachHang = new KhachHang[soLuong];
        for (i = 0; i < soLuong; i++) {
            DS_KhachHang[i] = new KhachHang();
            DS_KhachHang[i].docFile(dis);
        }
        dis.close();
    }
    // phuong thuc noi bo, dung de tao chuoi xu ly cho gon
    private int timKiemViTriKhachHang(String ma) {
        for (int i = 0; i < soLuong; i++)
            if (DS_KhachHang[i].getMa().equals(ma)) return i;
        return -1;
    }
    private boolean them(KhachHang kh) {
        if (kiemTraMa(kh.getMa())) return false;
        if (soLuong == DS_KhachHang.length) DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong+1);
        DS_KhachHang[soLuong++] = kh.saoChep();
        return true;
    }
    // cac phuong thuc giao tiep ra ben ngoai
    public boolean kiemTraMa(String ma) { return timKiemViTriKhachHang(ma) != -1; }
    @Override
    public void nhapTimKiem() {
        System.out.println("**** Tim kiem khach hang ****");
        String maHD = NhapChuan.docChuoi("Ma khach hang can tim");
        int viTriHD = timKiemViTriKhachHang(maHD); 
        if(viTriHD == -1)
            System.out.println("Khong tim thay phieu nhap hang nay");
        else {
            DanhSachKhachHang dsTam = new DanhSachKhachHang();
            dsTam.them((DS_KhachHang[viTriHD]));
            dsTam.xuatDanhSach();
        }
    }
    @Override 
    // them, sua, xoa
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach khach hang****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong khach hang muon them");
        if(DS_KhachHang.length < soLuong + nhapThem)    DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong + nhapThem);
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            System.out.printf("Nhap thong tin khach hang thu %d:\n", soLuong + 1);
            KhachHang khMoi = new KhachHang();
            while (kiemTraMa(khMoi.setMa(NhapChuan.docChuoiKhongRong("Ma Khach hang")).getMa()))
                System.out.println("Khach hang nay da ton tai");
            khMoi.nhap();
            DS_KhachHang[soLuong++] = khMoi;
        }
        System.out.println("****Ket thuc nhap danh sach khach hang****");
        NhapChuan.docChuoi("Enter de quay lai");
    }
    @Override 
    public void nhapSua() {
        do {
            int viTri = timKiemViTriKhachHang(NhapChuan.docChuoiKhongRong("Nhap ma KH muon sua"));
            if(viTri == -1) {System.out.println("khong ton tai ma kh nay!!!"); continue;}
            DS_KhachHang[viTri].nhap();
        } while (NhapChuan.docYesNo("Co muon nhap tiep hay khong", false));
    }
    @Override 
    public void nhapXoa() {
        do {
            int viTri = timKiemViTriKhachHang(NhapChuan.docChuoiKhongRong("Nhap ma KH muon xoa"));
            if(viTri == -1) {System.out.println("khong ton tai ma kh nay!!!"); continue;}
            for(int i = viTri; i < soLuong - 1; i++)
                DS_KhachHang[i] = DS_KhachHang[i+1];
            DS_KhachHang[soLuong - 1] = null;
            soLuong--;
        } while (NhapChuan.docYesNo("Co muon xoa tiep hay khong", false));
    }
}
