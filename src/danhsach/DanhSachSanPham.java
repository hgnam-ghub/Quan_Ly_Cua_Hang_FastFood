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
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachSanPham implements IDanhSach {
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

    public DanhSachSanPham saoChep() {return new DanhSachSanPham(this);}
    public int getSoLuong() {return soLuong;}
    private SanPham khoiTaoSanPham(int loaiSanPham) {
        switch (loaiSanPham) { 
            case 1 -> {return new DoAn();}
            case 2 -> {return new DoUong();}
            default -> {return null;}
        }
    }

    public void xuatDanhSach() {
        String[][] bangSanPham = new String[soLuong + 1][];
        bangSanPham[0] = new String[] {"Ma", "Ten", "Don Vi", "Phuong thuc che bien", "Gia ban", "So luong", "Muc cay", "The tich", "Gas"};
        for(int i = 0; i < soLuong; i++)
        {
            bangSanPham[i+1] = Arrays.copyOf(DS_SanPham[i].xuat(), bangSanPham[0].length);
            if(DS_SanPham[i] instanceof DoUong){
                bangSanPham[i+1][8] = bangSanPham[i+1][7]; 
                bangSanPham[i+1][7] = bangSanPham[i+1][6]; 
                bangSanPham[i+1][6] = null;
            }
        }
        FormatBang.inBangDuLieu("DANH SACH SAN PHAM",bangSanPham);
        NhapChuan.docChuoi("Enter de quay lai");
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

    // phuong thuc noi bo, dung de tao chuoi xu ly cho gon
    // tim vi tri san pham
    private int timKiemViTriSanPham(String maSP) {
        for(int i = 0; i < soLuong; i++)
            if(DS_SanPham[i] != null && DS_SanPham[i].getMa().equals(maSP)) return i;
        return -1;
    }
    
    // them, sua, xoa
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
    
    // cac phuong thuc giao tiep ra ben ngoai

    //kiem tra ma duy nhat
    public boolean kiemTraMa(String maSP) {return timKiemViTriSanPham(maSP) != -1;}
    
    // tra ra San Pham sao chep, ben ngoai xem duoc nhung khong sua duoc danh sach
    public SanPham timKiemTheoMa(String maSP) {
        int viTriSP = timKiemViTriSanPham(maSP);
        if(viTriSP == -1) return null;
        return DS_SanPham[viTriSP].saoChep();
    }
    public void timKiemTheoMa() {
        if(timKiemViTriSanPham(NhapChuan.docChuoiKhongRong("Nhap Ma San Pham Can Tim")) == -1)
            System.out.println(">> Khong tim thay san pham nay");
        else
            System.out.println(">> Da tim thay san pham can tim, bay gio muon lam gi voi san pham nay");
    }
    // nhan san pham va tu dong sao chep ra, chan tham chieu
    public boolean themVaoSanPhamDanhSach(SanPham sp) {
        if(sp == null) return false;
        return them(sp.saoChep()) != null;
    }

    //them, sua, xoa
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
        NhapChuan.docChuoi("Enter de quay lai");
    }
    public void nhapSua() {
        if(sua(NhapChuan.docChuoiKhongRong("Nhap ma san pham")) == null)
            System.out.println(">> San pham nay khong ton tai trong danh sach");
        else System.out.println(">> Sua thanh cong");
        NhapChuan.docChuoi("Enter de thoat");
    }
    public void nhapXoa() {
        if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma san pham")))
            System.out.println(">> Khong tim thay san pham can xoa");
        else System.out.println(">> Xoa thanh cong");
        NhapChuan.docChuoi("Enter de thoat");
    }
}
