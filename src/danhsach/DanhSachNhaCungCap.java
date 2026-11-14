package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NhaCungCap;
import rangbuoc.ITruyXuatDanhSach;
import tienich.BangDuLieu;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNhaCungCap implements ITruyXuatDanhSach {
    private NhaCungCap[] DS_NhaCungCap;
    private int soLuong;

    public DanhSachNhaCungCap() {
        soLuong = 0;
        DS_NhaCungCap = new NhaCungCap[soLuong];
    }
    public DanhSachNhaCungCap(DanhSachNhaCungCap DS_NCC) {
        soLuong = DS_NCC.soLuong;
        DS_NhaCungCap = new NhaCungCap[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_NhaCungCap[i] = DS_NCC.DS_NhaCungCap[i].saoChep();
    }

    public void xuatDanhSach() {
        String[] header = {"Ma NCC", "Ten Nha Cung Cap", "Dia Chi", "So Dien Thoai"};
        BangDuLieu.khoiTaoBang(header, soLuong);
        for(int i = 0; i < soLuong; i++)
            BangDuLieu.nhapDong(i, DS_NhaCungCap[i].xuat());
        BangDuLieu.xuatBang();
    }

    public void xuatDanhSachRaFile(){
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.DANHSACH))){
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++){
                DS_NhaCungCap[i].ghiFile(dos);
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
            if(DS_NhaCungCap.length < soLuong) DS_NhaCungCap = new NhaCungCap[soLuong];
            for(i = 0; i < soLuong; i++) {
                DS_NhaCungCap[i] = new NhaCungCap();
                DS_NhaCungCap[i].docFile(dis);
            }
        }
        catch(IOException e) {
            System.out.println(">> [Loi doc file]: " + e.getMessage());
            soLuong = i;
        }
    }

    private int timKiemViTriNhaCungCap(String maNCC) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_NhaCungCap[i] != null && DS_NhaCungCap[i].getMaNhaCungCap().equals(maNCC)) return i;
        }
        return -1;
    }

    private NhaCungCap them(NhaCungCap ncc) {
        if(kiemTraMa(ncc.getMaNhaCungCap())) return null;
        if(soLuong == DS_NhaCungCap.length) DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, soLuong + 1);
        DS_NhaCungCap[soLuong++] = ncc;
        return ncc;
    }

    private NhaCungCap sua(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if(viTri == -1) return null;
        return DS_NhaCungCap[viTri].nhap();
    }

    private boolean xoa(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if(viTri == -1) return false;
        for(int i = viTri; i < soLuong - 1; i++) {
            DS_NhaCungCap[i] = DS_NhaCungCap[i+1];
        }
        DS_NhaCungCap[--soLuong] = null;
        return true;
    }

    public boolean kiemTraMa(String maNCC) {
        return timKiemViTriNhaCungCap(maNCC) != -1;
    }

    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach nha cung cap****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nha cung cap muon them");
        DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            NhaCungCap nccMoi = new NhaCungCap();
            while(them(nccMoi.setMaNhaCungCap(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap"))) == null)
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            nccMoi.nhap();
        }
        System.out.println("****Ket thuc nhap danh sach nha cung cap****");
    }

    public void nhapSua() {
        String ma = NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap");
        if(sua(ma) == null)
            System.out.println(">> Nha cung cap nay khong ton tai trong danh sach");
    }

    public void nhapXoa() {
        if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap")))
            System.out.println(">> Khong tim thay nha cung cap can xoa");
    }
}