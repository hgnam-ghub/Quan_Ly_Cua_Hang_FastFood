package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.KhachHang;
import tienich.NhapChuan;
import rangbuoc.ITruyXuatDanhSach;

public class DanhSachKhachHang implements ITruyXuatDanhSach {
    private KhachHang[] DS_KhachHang;
    private int soLuong;
    private static final String fileDanhSach = "data/DS_KhachHang.dat";

    public int getSoLuong() {return soLuong;}

    public DanhSachKhachHang() {
        soLuong = 0;
        DS_KhachHang = new KhachHang[soLuong];
    }
    public DanhSachKhachHang(DanhSachKhachHang DS_KH) {
        soLuong = DS_KH.soLuong;
        DS_KhachHang = new KhachHang[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_KhachHang[i] = DS_KH.DS_KhachHang[i].saoChep(); 
    }
    @Override
    public void nhapThem() {
        System.out.println("**** Bat dau nhap danh sach khach hang ****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong khach hang muon them");
        DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong + nhapThem);

        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maKH;
            while (kiemTraMa(maKH = NhapChuan.docChuoiKhongRong("Nhap ma khach hang")))
                System.out.println(">> Ma khach hang nay da ton tai, nhap ma khac!!");
            DS_KhachHang[soLuong] = new KhachHang(maKH);
            DS_KhachHang[soLuong++].nhap();
        }
        System.out.println("**** Ket thuc nhap danh sach khach hang ****");
    }
    @Override
    public void xuatDanhSach() {
        System.out.println("==== Danh Sach Cac Khach Hang ====");
        for(int i = 0; i < soLuong; i++) {
            System.out.println("------------");
            DS_KhachHang[i].xuat();
        }
        System.out.println("==== Het ====");
    }

    @Override
    public void xuatDanhSachRaFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileDanhSach))) {
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++) {
                DS_KhachHang[i].ghiFile(dos);
            }
        } catch (IOException e) {
            System.out.println(">> Loi trong qua trinh xuat danh sach khach hang ra file");
            e.printStackTrace();
        }
    }
    @Override
    public void nhapDanhSachTuFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileDanhSach))) {
            soLuong = dis.readInt();
            if(soLuong > DS_KhachHang.length) DS_KhachHang = new KhachHang[soLuong];
            for(int i = 0; i < soLuong; i++) {
                DS_KhachHang[i] = new KhachHang("");
                DS_KhachHang[i].docFile(dis);
            }
        } catch (IOException e) {
            System.out.println(">> Co loi trong qua trinh nhap danh sach khach hang tu file");
            e.printStackTrace();
        }
    }

    private boolean them(KhachHang kh) {
        if(kiemTraMa(kh.getMa())) return false;
        if(soLuong == DS_KhachHang.length) DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong + 1);
        DS_KhachHang[soLuong++] = kh;
        return true;
    }
    public boolean themKhachHang() {
        String ma = NhapChuan.docChuoiKhongRong("Ma Khach Hang");
        if(kiemTraMa(ma)) { 
            System.out.println(">> Ma nay da ton tai!"); 
            return false; 
        }
        KhachHang kh = new KhachHang(ma);
        kh.nhap();
        return them(kh);
    }

    private boolean sua(String ma) {
        int viTri = timKiemViTriKhachHang(ma);
        if(viTri == -1) return false;
        DS_KhachHang[viTri].nhap();
        return true;
    }
    @Override
    public void nhapSua() {
        if(!sua(NhapChuan.docChuoiKhongRong("Nhap ma khach hang can sua")))
            System.out.println(">> Khach hang khong ton tai trong danh sach!");
    }

    private boolean xoa(String ma) {
        int viTri = timKiemViTriKhachHang(ma);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++)
            DS_KhachHang[i] = DS_KhachHang[i + 1];
        DS_KhachHang[--soLuong] = null;
        return true;
    }
    @Override
    public void nhapXoa() {
        if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma khach hang can xoa")))
            System.out.println(">> Khong tim thay khach hang can xoa!");
    }

    private int timKiemViTriKhachHang(String ma) {
        for(int i = 0; i < soLuong; i++) 
            if(DS_KhachHang[i] != null && DS_KhachHang[i].getMa().equals(ma)) return i;
        return -1;
    }
    public boolean kiemTraMa(String ma) {
        return timKiemViTriKhachHang(ma) != -1;
    }
    public KhachHang timKiemKhachHang(String ma) {
        int viTri = timKiemViTriKhachHang(ma);
        return (viTri != -1) ? DS_KhachHang[viTri] : null;
    }

    public void xoaTheoDanhSach(DanhSachKhachHang DS_KH) {
        for (int i = 0; i < DS_KH.soLuong; i++) 
            xoa(DS_KH.DS_KhachHang[i].getMa());
    }
    public void nhapTuDanhSach(DanhSachKhachHang DS_KH) {
        for (int i = 0; i < DS_KH.soLuong; i++) 
            if (!kiemTraMa(DS_KH.DS_KhachHang[i].getMa())) 
                them(DS_KH.DS_KhachHang[i].saoChep());
    }

    public DanhSachKhachHang timkiemTheoMa(String ma) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++)
            if (DS_KhachHang[i] != null && DS_KhachHang[i].getMa().equals(ma))
                dsTam.them(DS_KhachHang[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachKhachHang timkiemTheoHo(String ho) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++) 
            if (DS_KhachHang[i] != null && DS_KhachHang[i].getHo().equalsIgnoreCase(ho)) 
                dsTam.them(DS_KhachHang[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachKhachHang timkiemTheoTen(String ten) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++) 
            if (DS_KhachHang[i] != null && DS_KhachHang[i].getTen().equalsIgnoreCase(ten)) 
                dsTam.them(DS_KhachHang[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachKhachHang timkiemTheotongTienDaMua(double tongTienDaMua) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++) 
            if (DS_KhachHang[i] != null && DS_KhachHang[i].getTongSoTienMua() == tongTienDaMua) 
                dsTam.them(DS_KhachHang[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
}
