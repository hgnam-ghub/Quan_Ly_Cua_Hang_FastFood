package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NhanVien;
import quanly.NhapChuan;
import rangbuoc.ITruyXuatDanhSach;

public class DanhSachNhanVien implements ITruyXuatDanhSach {
    private NhanVien[] DS_NhanVien;
    private int soLuong;
    private static final String fileDanhSach = "data/DS_NhanVien.dat";

    public DanhSachNhanVien() {
        soLuong = 0;
        DS_NhanVien = new NhanVien[soLuong];
    }

    public DanhSachNhanVien(DanhSachNhanVien DS_NV) {
        soLuong = DS_NV.soLuong;
        DS_NhanVien = new NhanVien[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_NhanVien[i] = DS_NV.DS_NhanVien[i].saoChep();
    }

    public void nhapThem() {
        System.out.println("****Bat dau nhap danh sach nhan vien****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nhan vien muon them");
        DS_NhanVien = Arrays.copyOf(DS_NhanVien, soLuong + nhapThem);

        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNV;
            while (kiemTraMa(maNV = NhapChuan.docChuoiKhongRong("Nhap ma nhan vien")))
                System.out.println(">> Ma nay da ton tai, vui long nhap ma khac !!!");

            NhanVien nv = new NhanVien(maNV);
            nv.nhap();
            DS_NhanVien[soLuong++] = nv;
        }
        System.out.println("****Ket thuc nhap danh sach nhan vien****");
    }

    public void xuatDanhSach() {
        System.out.println("====Danh Sach Nhan Vien====");
        for (int i = 0; i < soLuong; i++) {
            System.out.println("------------");
            DS_NhanVien[i].xuat();
        }
        System.out.println("====Het====");
    }

    public void xuatDanhSachRaFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileDanhSach))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                DS_NhanVien[i].ghiFile(dos);
            }
        } catch (IOException e) {
            System.out.println(">> Loi trong qua trinh xuat danh sach nhan vien ra file");
            e.printStackTrace();
        }
    }

    public void nhapDanhSachTuFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileDanhSach))) {
            soLuong = dis.readInt();
            if (soLuong > DS_NhanVien.length)
                DS_NhanVien = new NhanVien[soLuong];
            for (int i = 0; i < soLuong; i++) {
                NhanVien nv = new NhanVien("");
                nv.docFile(dis);
                DS_NhanVien[i] = nv;
            }
        } catch (IOException e) {
            System.out.println(">> Co loi trong qua trinh nhap danh sach nhan vien tu file");
            e.printStackTrace();
        }
    }

    private boolean them(NhanVien nv) {
        if (kiemTraMa(nv.getMa()))
            return false;
        if (soLuong == DS_NhanVien.length)
            DS_NhanVien = Arrays.copyOf(DS_NhanVien, soLuong + 1);
        DS_NhanVien[soLuong++] = nv;
        return true;
    }

    private boolean sua(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if (viTri == -1)
            return false;
        DS_NhanVien[viTri].nhap();
        return true;
    }

    public void nhapSua() {
        if (!sua(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien")))
            System.out.println(">> Nhan vien nay khong ton tai trong danh sach");
    }

    private boolean xoa(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if (viTri == -1)
            return false;
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NhanVien[i] = DS_NhanVien[i + 1];
        }
        DS_NhanVien[--soLuong] = null;
        return true;
    }

    public void nhapXoa() {
        if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien")))
            System.out.println(">> Khong tim thay nhan vien can xoa");
    }

    private int timKiemViTriNhanVien(String maNV) {
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhanVien[i] != null && DS_NhanVien[i].getMa().equalsIgnoreCase(maNV))
                return i;
        }
        return -1;
    }

    public boolean kiemTraMa(String maNV) {
        return timKiemViTriNhanVien(maNV) != -1;
    }

    public DanhSachNhanVien timKiemTheoTen(String tenNhanVien) {
        DanhSachNhanVien dsTam = new DanhSachNhanVien();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhanVien[i] != null && DS_NhanVien[i].getTen().equalsIgnoreCase(tenNhanVien))
                dsTam.them(DS_NhanVien[i]);
        }
        if (dsTam.soLuong == 0)
            return null;
        return dsTam;
    }
}