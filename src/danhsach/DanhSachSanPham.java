package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import mohinh.DoAn;
import mohinh.DoUong;
import mohinh.SanPham;
import quanly.NhapChuan;

public class DanhSachSanPham {
    private SanPham[] DS_SanPham;
    private int soLuong;
    private static final String fileDanhSach = "data/DS_SanPham.dat";

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

    // nhap, xuat, doc ghi danh sach
    public void nhap() {
        System.out.println("****bat dau nhap danh sach****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong san pham muon them");
        DS_SanPham = Arrays.copyOf(DS_SanPham, soLuong + nhapThem);

        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            System.out.printf("Chon loai san pham muon them:\n1: do an\n2: do uong\n");
            int luaChon;
            while(!((luaChon = NhapChuan.docSoNguyenKhongAm("Nhap lua chon")) >= 1 && luaChon <= 2))
                System.out.println(">> khong ton tai lua chon nay, vui long chon lai !!!");
            String maSP;
            while(kiemTraMa(maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham")))
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");

            switch (luaChon) {
                case 1:
                    DS_SanPham[soLuong] = new DoAn(maSP);
                    break;
                case 2:
                    DS_SanPham[soLuong] = new DoUong(maSP);
                    break;
            }
            DS_SanPham[soLuong++].nhap();
            System.out.println("****Ket thuc nhap danh sach****");
        }
    }
    public void xuat() {
        System.out.println("====Danh Sach Cac San Pham====");
        for(int i = 0; i < soLuong; i++) {
            System.out.println("------------");
            DS_SanPham[i].xuat();
        }
        System.out.println("====Het====");
    }
    public void ghiDSXuongFile() throws Exception {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileDanhSach));
        dos.writeInt(soLuong);
        for(int i = 0; i < soLuong; i++){
            if(DS_SanPham[i] instanceof DoAn) dos.writeInt(1);
            else if(DS_SanPham[i] instanceof DoUong) dos.writeInt(2);
            DS_SanPham[i].ghiFile(dos);
        }
        dos.close();
    }
    public void docDSTuFile() throws Exception{
        DataInputStream dis = new DataInputStream(new FileInputStream(fileDanhSach));
        soLuong = dis.readInt();
        if(soLuong > DS_SanPham.length) DS_SanPham = new SanPham[soLuong];
        for(int i = 0; i < soLuong; i++) {
            int loaiSP = dis.readInt();
            switch (loaiSP) {
                case 1:
                    DS_SanPham[i] = new DoAn(dis.readUTF());
                    break;
                case 2:
                    DS_SanPham[i] = new DoUong(dis.readUTF());
                    break;
            }
            DS_SanPham[i].docFile(dis);
        }
        dis.close();
    }

    // them mot san pham
    private boolean them(SanPham sp) {
        if(kiemTraMa(sp.getMa()))
            return false;
        if(soLuong == DS_SanPham.length) DS_SanPham = Arrays.copyOf(DS_SanPham, soLuong + 1);
        DS_SanPham[soLuong++] = sp;
        return true;
    }
    // sua mot san pham
    private boolean sua(String maSP) {
        int viTri = timKiemViTriSanPham(maSP);
        if(viTri == -1) return false;
        DS_SanPham[viTri].nhap();
        return true;
    }
    public void sua() {
        if(!sua(NhapChuan.docChuoiKhongRong("Nhap ma san pham")))
            System.out.println(">> San pham nay khong ton tai trong danh sach");
    }
    // xoa mot san pham
    private boolean xoa(String maSP) {
        int viTri = timKiemViTriSanPham(maSP);
        if(viTri == -1) return false;
        for(int i = viTri; i < soLuong - 1; i++) {
            DS_SanPham[i] = DS_SanPham[i+1];
        }
        DS_SanPham[--soLuong] = null;
        return true;
    }
    public void xoa() {
        if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma san pham")))
            System.out.println(">> Khong tim thay san pham can xoa");
    }
    // tim kiem doi tuong voi ma duy nhat trong danh sach
    private int timKiemViTriSanPham(String maSP) {
        for(int i = 0; i < soLuong; i++) {
            if(DS_SanPham[i] != null && DS_SanPham[i].getMa().equals(maSP)) return i;
        }
        return -1;
    }
    public boolean kiemTraMa(String maSP) {return timKiemViTriSanPham(maSP) != -1;}

    // tim kiem nhieu doi tuong co thuoc tinh chung giong nhau
    public DanhSachSanPham timKiemTheoTen(String tenSanPham) {
        DanhSachSanPham dsTam = new DanhSachSanPham();
        for(int i = 0; i < soLuong; i++)
            if(DS_SanPham[i] != null && DS_SanPham[i].getTen().equals(tenSanPham))
                dsTam.them(DS_SanPham[i]);
        if(dsTam.soLuong == 0) return null;
        return dsTam;
    }
}
