package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NguyenLieu;
import rangbuoc.ITruyXuatDanhSach;
import tienich.BangDuLieu;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNguyenLieu implements ITruyXuatDanhSach {
    private NguyenLieu[] DS_NguyenLieu;
    private int soLuong;

    public DanhSachNguyenLieu() {
        soLuong = 0;
        DS_NguyenLieu = new NguyenLieu[soLuong];
    }
    public DanhSachNguyenLieu(DanhSachNguyenLieu DS_NL) {
        soLuong = DS_NL.soLuong;
        DS_NguyenLieu = new NguyenLieu[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_NguyenLieu[i] = DS_NL.DS_NguyenLieu[i].saoChep();
    }

    public void xuatDanhSach() {
        String[] header = {"Ma NL", "Ten Nguyen Lieu", "Don Vi Tinh", "So Luong Ton", "Gia Tien", "Ma NCC"};
        BangDuLieu.khoiTaoBang(header, soLuong);
        for(int i = 0; i < soLuong; i++)
            BangDuLieu.nhapDong(i, DS_NguyenLieu[i].xuat());
        BangDuLieu.xuatBang();
    }

    public void xuatDanhSachRaFile(){
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.DANHSACH))){
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++){
                DS_NguyenLieu[i].ghiFile(dos);
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
            if(DS_NguyenLieu.length < soLuong) DS_NguyenLieu = new NguyenLieu[soLuong];
            for(i = 0; i < soLuong; i++) {
                DS_NguyenLieu[i] = new NguyenLieu();
                DS_NguyenLieu[i].setMaNguyenLieu(dis.readUTF());
                DS_NguyenLieu[i].docFile(dis);
            }
        }
        catch(IOException e) {
            System.out.println(">> [Loi doc file]: " + e.getMessage());
            soLuong = i;
        }
    }

    private int timKiemViTriNguyenLieu(String maNL) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_NguyenLieu[i] != null && DS_NguyenLieu[i].getMaNguyenLieu().equals(maNL)) return i;
        }
        return -1;
    }

    private NguyenLieu them(NguyenLieu nl) {
        if(kiemTraMa(nl.getMaNguyenLieu())) return null;
        if(soLuong == DS_NguyenLieu.length) DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, soLuong + 1);
        DS_NguyenLieu[soLuong++] = nl;
        return nl;
    }

    private NguyenLieu sua(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if(viTri == -1) return null;
        return DS_NguyenLieu[viTri].nhap();
    }

    private boolean xoa(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if(viTri == -1) return false;
        for(int i = viTri; i < soLuong - 1; i++) {
            DS_NguyenLieu[i] = DS_NguyenLieu[i+1];
        }
        DS_NguyenLieu[--soLuong] = null;
        return true;
    }

    public boolean kiemTraMa(String maNL) {
        return timKiemViTriNguyenLieu(maNL) != -1;
    }

    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach nguyen lieu****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nguyen lieu muon them");
        DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            NguyenLieu nlMoi = new NguyenLieu();
            while(them(nlMoi.setMaNguyenLieu(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu"))) == null)
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            nlMoi.nhap();
        }
        System.out.println("****Ket thuc nhap danh sach nguyen lieu****");
    }

    public void nhapSua() {
        String ma = NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu");
        if(sua(ma) == null)
            System.out.println(">> Nguyen lieu nay khong ton tai trong danh sach");
    }

    public void nhapXoa() {
        if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu")))
            System.out.println(">> Khong tim thay nguyen lieu can xoa");
    }
}