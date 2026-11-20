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


    // sao chep 1 ra danh sach moi
    @Override
    public DanhSachSanPham saoChep() {return new DanhSachSanPham(this);}
    // doc so luong cua danh sach
    @Override 
    public int getSoLuong() {return soLuong;}
    // ======================================================
    // ghi du lieu danh sach xuong file
    @Override
    public void xuatDanhSachRaFile() throws IOException  {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSSP));
        dos.writeInt(soLuong);
        for(int i = 0; i < soLuong; i++){
            if(DS_SanPham[i] instanceof DoAn) dos.writeInt(1);
            else if(DS_SanPham[i] instanceof DoUong) dos.writeInt(2);
            DS_SanPham[i].ghiFile(dos);
        }
        dos.close();
    }
    // doc du lieu danh sach vao chuong trinh
    @Override 
    public void nhapDanhSachTuFile() throws IOException{
        int i = 0;
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSSP));
        soLuong = dis.readInt();
        if(DS_SanPham.length < soLuong) DS_SanPham = new SanPham[soLuong];
        for(i = 0; i < soLuong; i++) {
            DS_SanPham[i] = khoiTaoSanPham(dis.readInt());
            if((DS_SanPham[i]) == null) 
                throw new IOException("kieu doi tuong khong hop le, khong the khoi tao doi tuong " + i);
            DS_SanPham[i].setMa(dis.readUTF());
            DS_SanPham[i].docFile(dis);
        }
        dis.close();
    }

    // ======================================================
    // phuong thuc noi bo, dung de tao chuoi xu ly cho gon, dau vao phai duoc kiem tra truoc

    // tim vi tri san pham
    private int timKiemViTriSanPham(String maSP) {
        for(int i = 0; i < soLuong; i++)
            if(DS_SanPham[i] != null && DS_SanPham[i].getMa().equals(maSP)) return i;
        return -1;
    }
    // them, sua, xoa 1 san pham
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
        for(int i = viTri; i < soLuong - 1; i++)
            DS_SanPham[i] = DS_SanPham[i+1];
        DS_SanPham[--soLuong] = null;
        return true;
    }
    
    //  khoi tao san pham dua tren loai nhap vao 1 la do an, 2 do la uong
    private SanPham khoiTaoSanPham(int loaiSanPham) {
        switch (loaiSanPham) { 
            case 1 -> {return new DoAn();}
            case 2 -> {return new DoUong();}
            default -> {return null;}
        }
    }
    // ======================================================
    // cac phuong thuc giao tiep ra ben ngoai

    //kiem tra ma nay da ton tai trong danh sach chua, co roi thi tra ve true, chua thi false
    public boolean kiemTraMa(String maSP) {
        if(maSP == null || maSP.isEmpty()) return false;
        return (timKiemViTriSanPham(maSP) != -1);
    }
    
    // tra ra ban sao cua san pham
    public SanPham timKiemMa(String maSP) {
        if(maSP == null || maSP.isEmpty()) return null;
        int viTriSP = timKiemViTriSanPham(maSP);
        if(viTriSP == -1) return null;
        return DS_SanPham[viTriSP].saoChep();
    }
    // them ban sao cua san pham ben ngoai vo danh sach
    public boolean themVaoDanhSach(SanPham sp) {
        if(sp == null || sp.getMa().isEmpty()) return false;
        return them(sp.saoChep()) != null;
    }

    public SanPham docPhanTu(int index) {return DS_SanPham[index].saoChep();}
    //------------------------------------
    // cac phuong thuc giao dien
    @Override
    public void nhapTimKiem() {
        
    }
    //them, sua, xoa
    @Override
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
    @Override
    public void nhapSua() {
        do{
            if(sua(NhapChuan.docChuoiKhongRong("Nhap ma san pham")) == null)
                System.out.println(">> San pham nay khong ton tai trong danh sach");
            else System.out.println(">> Sua thanh cong");
            System.out.println("Co tiep tuc nhap sua them san pham khac khong?");
        }
        while(NhapChuan.docYesNo("", false));
    }
    @Override
    public void nhapXoa() {
        do
        {
            if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma san pham")))
                System.out.println(">> Khong tim thay san pham can xoa");
            else System.out.println(">> Xoa thanh cong");
            System.out.println("Co tiep tuc xoa cac san pham khac khong?");
        }
        while(NhapChuan.docYesNo("", false));
        
    }
    // xuat danh sach voi cac dong du lieu duoc fomat bang
    @Override
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
}
