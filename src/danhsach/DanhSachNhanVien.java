package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NhanVien;
import rangbuoc.ITruyXuatDanhSach;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNhanVien implements ITruyXuatDanhSach {
    private NhanVien[] DS_NhanVien;
    private int soLuong;

    public DanhSachNhanVien() {
        soLuong = 0;
        DS_NhanVien = new NhanVien[soLuong];
    }
    public DanhSachNhanVien(DanhSachNhanVien DS_NV) {
        soLuong = DS_NV.soLuong;
        DS_NhanVien = new NhanVien[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_NhanVien[i] = DS_NV.DS_NhanVien[i].saoChep();
    }

    public void xuatDanhSach() {
        for(int i = 0; i < soLuong; i++)
            DS_NhanVien[i].xuat();
    }

    public void xuatDanhSachRaFile(){
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.DANHSACH))){
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++){
                DS_NhanVien[i].ghiFile(dos);
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
            if(DS_NhanVien.length < soLuong) DS_NhanVien = new NhanVien[soLuong];
            for(i = 0; i < soLuong; i++) {
                DS_NhanVien[i] = new NhanVien("");
                DS_NhanVien[i].docFile(dis);
            }
        }
        catch(IOException e) {
            System.out.println(">> [Loi doc file]: " + e.getMessage());
            soLuong = i;
        }
    }

    private int timKiemViTriNhanVien(String maNV) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_NhanVien[i] != null && DS_NhanVien[i].getMa().equals(maNV)) return i;
        }
        return -1;
    }

    private NhanVien them(NhanVien nv) {
        if(kiemTraMa(nv.getMa())) return null;
        if(soLuong == DS_NhanVien.length) DS_NhanVien = Arrays.copyOf(DS_NhanVien, soLuong + 1);
        DS_NhanVien[soLuong++] = nv;
        return nv;
    }

    private NhanVien sua(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if(viTri == -1) return null;
        DS_NhanVien[viTri].nhap();
        return DS_NhanVien[viTri];
    }

    private boolean xoa(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if(viTri == -1) return false;
        for(int i = viTri; i < soLuong - 1; i++) {
            DS_NhanVien[i] = DS_NhanVien[i+1];
        }
        DS_NhanVien[--soLuong] = null;
        return true;
    }

    public boolean kiemTraMa(String maNV) {
        return timKiemViTriNhanVien(maNV) != -1;
    }

    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach nhan vien****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nhan vien muon them");
        DS_NhanVien = Arrays.copyOf(DS_NhanVien, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            NhanVien nvMoi = new NhanVien("");
            while(them(nvMoi.saoChepVoiMaMoi(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien"))) == null)
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            nvMoi.nhap();
        }
        System.out.println("****Ket thuc nhap danh sach nhan vien****");
    }

    public void nhapSua() {
        String ma = NhapChuan.docChuoiKhongRong("Nhap ma nhan vien");
        if(sua(ma) == null)
            System.out.println(">> Nhan vien nay khong ton tai trong danh sach");
    }

    public void nhapXoa() {
        if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien")))
            System.out.println(">> Khong tim thay nhan vien can xoa");
    }
}