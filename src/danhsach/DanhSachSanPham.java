package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.DoAn;
import mohinh.DoUong;
import mohinh.SanPham;
import rangbuoc.ITruyXuatDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachSanPham implements ITruyXuatDanhSach{
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

    public int getSoLuong() {
        return soLuong;
    }
    private SanPham khoiTaoSanPham(int loaiSanPham) {
        switch (loaiSanPham) { 
            case 1: return new DoAn();
            case 2: return new DoUong();
            default: return null;
        }
    }

    public void xuatDanhSach() {
        String tieuDe = "DANH SACH";
        String[][] bangDuLieu = new String[soLuong + 1][6];
        bangDuLieu[0] = new String[] {"Ma", "Ten", "Don Vi", "Phuong thuc che bien", "Gia ban", "So luong"};
        for(int i = 0; i < soLuong; i++)
            bangDuLieu[i+1] = DS_SanPham[i].xuat();
        FormatBang.inBangDuLieu(tieuDe ,bangDuLieu, true);
    }
    public void xuatDanhSachRaFile(){
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.DANHSACH))){
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++){
                if(DS_SanPham[i] instanceof DoAn) dos.writeInt(1);
                else if(DS_SanPham[i] instanceof DoUong) dos.writeInt(2);
                DS_SanPham[i].ghiFile(dos);
            }
        }
        catch (IOException e) {
            System.out.println(">> [Loi ghi file]: " + e.getMessage());
        }
    }
    public void nhapDanhSachTuFile(){
        int i = 0;
        try(DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.DANHSACH))) {
            soLuong = dis.readInt();
            if(DS_SanPham.length < soLuong) DS_SanPham = new SanPham[soLuong];
            for(i = 0; i < soLuong; i++) {
                DS_SanPham[i] = khoiTaoSanPham(dis.readInt());
                if((DS_SanPham[i]) == null) 
                    throw new IOException("kieu doi tuong khong hop le, khong the khoi tao doi tuong " + i);
                DS_SanPham[i].setMa(dis.readUTF());
                DS_SanPham[i].docFile(dis);
            }
        }
        catch(IOException e) {
            System.out.println(">> [Loi doc file]: " + e.getMessage());
            soLuong = i;
        }
    }

    // them, sua, xoa. tim vi tri cua maSP trong danh sach, neu khong ton tai tra ve -1 (cac phuong thuc su dung noi bo)
    private int timKiemViTriSanPham(String maSP) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_SanPham[i] != null && DS_SanPham[i].getMa().equals(maSP)) return i;
        }
        return -1;
    }
    private SanPham them(SanPham sp) {
        if(kiemTraMa(sp.getMa())) return null;
        if(soLuong == DS_SanPham.length) DS_SanPham = Arrays.copyOf(DS_SanPham, soLuong + 1);
        DS_SanPham[soLuong++] = sp;
        return sp;
    }
    private SanPham sua(String maSP) {
        int viTri = timKiemViTriSanPham(maSP);
        if(viTri == -1) return null;
        return DS_SanPham[viTri].nhap();
    }
    private boolean xoa(String maSP) {
        int viTri = timKiemViTriSanPham(maSP);
        if(viTri == -1) return false;
        for(int i = viTri; i < soLuong - 1; i++) {
            DS_SanPham[i] = DS_SanPham[i+1];
        }
        DS_SanPham[--soLuong] = null;
        return true;
    }
    // them, sua, xoa, kiem tra ma duy nhat voi dau vao nhap tu ban phim
    public boolean kiemTraMa(String maSP) {return timKiemViTriSanPham(maSP) != -1;}
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong san pham muon them");
        DS_SanPham = Arrays.copyOf(DS_SanPham, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            System.out.printf("Chon loai san pham muon them:\n1: do an\n2: do uong\n");
            SanPham spMoi = null;
            while((spMoi = khoiTaoSanPham(NhapChuan.docSoNguyen("Nhap so nguyen"))) == null) 
                System.out.println(">> lua chon khong ton tai, vui long chon lua chon khac !!!");
            while(them(spMoi.setMa(NhapChuan.docChuoiKhongRong("Nhap ma san pham"))) == null)
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            spMoi.nhap();
        }
        System.out.println("****Ket thuc nhap danh sach****");
    }
    public void nhapSua() {
        if(sua(NhapChuan.docChuoiKhongRong("Nhap ma san pham")) != null)
            System.out.println(">> San pham nay khong ton tai trong danh sach");
    }
    public void nhapXoa() {
        if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma san pham")))
            System.out.println(">> Khong tim thay san pham can xoa");
    }
}
