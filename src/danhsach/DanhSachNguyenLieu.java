package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NguyenLieu;
import rangbuoc.ITruyXuatDanhSach;
import tienich.NhapChuan;

public class DanhSachNguyenLieu implements ITruyXuatDanhSach {
    private NguyenLieu[] DS_NguyenLieu;
    private int soLuong;
    private static final String fileDanhSach = "data/DS_NguyenLieu.dat";

    public DanhSachNguyenLieu() {
        soLuong = 0;
        DS_NguyenLieu = new NguyenLieu[soLuong];
    }

    public DanhSachNguyenLieu(DanhSachNguyenLieu DS_NL) {
        soLuong = DS_NL.soLuong;
        DS_NguyenLieu = new NguyenLieu[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_NguyenLieu[i] = DS_NL.DS_NguyenLieu[i].saoChep();
    }

    public void nhapThem() {
        System.out.println("****Bat dau nhap danh sach nguyen lieu****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nguyen lieu muon them");
        DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, soLuong + nhapThem);

        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNL;
            while (kiemTraMa(maNL = NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu")))
                System.out.println(">> Ma nay da ton tai, vui long nhap ma khac !!!");

            NguyenLieu nl = new NguyenLieu(maNL);
            nl.nhap();
            DS_NguyenLieu[soLuong++] = nl;
        }
        System.out.println("****Ket thuc nhap danh sach nguyen lieu****");
    }

    public void xuatDanhSach() {
        System.out.println("====Danh Sach Nguyen Lieu====");
        for (int i = 0; i < soLuong; i++) {
            System.out.println("------------");
            DS_NguyenLieu[i].xuat();
        }
        System.out.println("====Het====");
    }

    public void xuatDanhSachRaFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileDanhSach))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                DS_NguyenLieu[i].ghiFile(dos);
            }
        } catch (IOException e) {
            System.out.println(">> Loi trong qua trinh xuat danh sach nguyen lieu ra file");
            e.printStackTrace();
        }
    }

    public void nhapDanhSachTuFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileDanhSach))) {
            soLuong = dis.readInt();
            if (soLuong > DS_NguyenLieu.length)
                DS_NguyenLieu = new NguyenLieu[soLuong];
            for (int i = 0; i < soLuong; i++) {
                NguyenLieu nl = new NguyenLieu("");
                nl.docFile(dis);
                DS_NguyenLieu[i] = nl;
            }
        } catch (IOException e) {
            System.out.println(">> Co loi trong qua trinh nhap danh sach nguyen lieu tu file");
            e.printStackTrace();
        }
    }

    private boolean them(NguyenLieu nl) {
        if (kiemTraMa(nl.getMa()))
            return false;
        if (soLuong == DS_NguyenLieu.length)
            DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, soLuong + 1);
        DS_NguyenLieu[soLuong++] = nl;
        return true;
    }

    private boolean sua(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if (viTri == -1)
            return false;
        DS_NguyenLieu[viTri].nhap();
        return true;
    }

    public void nhapSua() {
        if (!sua(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu")))
            System.out.println(">> Nguyen lieu nay khong ton tai trong danh sach");
    }

    private boolean xoa(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if (viTri == -1)
            return false;
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NguyenLieu[i] = DS_NguyenLieu[i + 1];
        }
        DS_NguyenLieu[--soLuong] = null;
        return true;
    }

    public void nhapXoa() {
        if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu")))
            System.out.println(">> Khong tim thay nguyen lieu can xoa");
    }

    private int timKiemViTriNguyenLieu(String maNL) {
        for (int i = 0; i < soLuong; i++) {
            if (DS_NguyenLieu[i] != null && DS_NguyenLieu[i].getMa().equalsIgnoreCase(maNL))
                return i;
        }
        return -1;
    }

    public boolean kiemTraMa(String maNL) {
        return timKiemViTriNguyenLieu(maNL) != -1;
    }

    public DanhSachNguyenLieu timKiemTheoTen(String tenNguyenLieu) {
        DanhSachNguyenLieu dsTam = new DanhSachNguyenLieu();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NguyenLieu[i] != null && DS_NguyenLieu[i].getTen().equalsIgnoreCase(tenNguyenLieu))
                dsTam.them(DS_NguyenLieu[i]);
        }
        if (dsTam.soLuong == 0)
            return null;
        return dsTam;
    }
}
