package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NhaCungCap;
import quanly.NhapChuan;
import rangbuoc.ITruyXuatDanhSach;

public class DanhSachNhaCungCap implements ITruyXuatDanhSach {
    private NhaCungCap[] DS_NhaCungCap;
    private int soLuong;
    private static final String fileDanhSach = "data/DS_NhaCungCap.dat";

    public DanhSachNhaCungCap() {
        soLuong = 0;
        DS_NhaCungCap = new NhaCungCap[soLuong];
    }

    public DanhSachNhaCungCap(DanhSachNhaCungCap DS_NCC) {
        soLuong = DS_NCC.soLuong;
        DS_NhaCungCap = new NhaCungCap[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_NhaCungCap[i] = DS_NCC.DS_NhaCungCap[i].saoChep();
    }

    public void nhapThem() {
        System.out.println("****Bat dau nhap danh sach nha cung cap****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nha cung cap muon them");
        DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, soLuong + nhapThem);

        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNCC;
            while (kiemTraMa(maNCC = NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap")))
                System.out.println(">> Ma nay da ton tai, vui long nhap ma khac !!!");

            NhaCungCap ncc = new NhaCungCap(maNCC);
            ncc.nhap();
            DS_NhaCungCap[soLuong++] = ncc;
        }
        System.out.println("****Ket thuc nhap danh sach nha cung cap****");
    }

    public void xuatDanhSach() {
        System.out.println("====Danh Sach Nha Cung Cap====");
        for (int i = 0; i < soLuong; i++) {
            System.out.println("------------");
            DS_NhaCungCap[i].xuat();
        }
        System.out.println("====Het====");
    }

    public void xuatDanhSachRaFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileDanhSach))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                DS_NhaCungCap[i].ghiFile(dos);
            }
        } catch (IOException e) {
            System.out.println(">> Loi trong qua trinh xuat danh sach nha cung cap ra file");
            e.printStackTrace();
        }
    }

    public void nhapDanhSachTuFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileDanhSach))) {
            soLuong = dis.readInt();
            if (soLuong > DS_NhaCungCap.length)
                DS_NhaCungCap = new NhaCungCap[soLuong];
            for (int i = 0; i < soLuong; i++) {
                NhaCungCap ncc = new NhaCungCap("");
                ncc.docFile(dis);
                DS_NhaCungCap[i] = ncc;
            }
        } catch (IOException e) {
            System.out.println(">> Co loi trong qua trinh nhap danh sach nha cung cap tu file");
            e.printStackTrace();
        }
    }

    private boolean them(NhaCungCap ncc) {
        if (kiemTraMa(ncc.getMa()))
            return false;
        if (soLuong == DS_NhaCungCap.length)
            DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, soLuong + 1);
        DS_NhaCungCap[soLuong++] = ncc;
        return true;
    }

    private boolean sua(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if (viTri == -1)
            return false;
        DS_NhaCungCap[viTri].nhap();
        return true;
    }

    public void nhapSua() {
        if (!sua(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap")))
            System.out.println(">> Nha cung cap nay khong ton tai trong danh sach");
    }

    private boolean xoa(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if (viTri == -1)
            return false;
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NhaCungCap[i] = DS_NhaCungCap[i + 1];
        }
        DS_NhaCungCap[--soLuong] = null;
        return true;
    }

    public void nhapXoa() {
        if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap")))
            System.out.println(">> Khong tim thay nha cung cap can xoa");
    }

    private int timKiemViTriNhaCungCap(String maNCC) {
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhaCungCap[i] != null && DS_NhaCungCap[i].getMa().equalsIgnoreCase(maNCC))
                return i;
        }
        return -1;
    }

    public boolean kiemTraMa(String maNCC) {
        return timKiemViTriNhaCungCap(maNCC) != -1;
    }

    public DanhSachNhaCungCap timKiemTheoTen(String tenNhaCungCap) {
        DanhSachNhaCungCap dsTam = new DanhSachNhaCungCap();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhaCungCap[i] != null && DS_NhaCungCap[i].getTen().equalsIgnoreCase(tenNhaCungCap))
                dsTam.them(DS_NhaCungCap[i]);
        }
        if (dsTam.soLuong == 0)
            return null;
        return dsTam;
    }
}