package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.HoaDon;
import tienich.NhapChuan;
import rangbuoc.ITruyXuatDanhSach;

public class DanhSachHoaDon implements ITruyXuatDanhSach {
    private HoaDon[] DS_HoaDon;
    private int soLuong;
    private static final String fileDanhSach = "data/DS_HoaDon.dat";

    public int getSoLuong() { return soLuong; }

    public DanhSachHoaDon () {
        soLuong = 0;
        DS_HoaDon = new HoaDon[soLuong];
    }
    public DanhSachHoaDon(DanhSachHoaDon ds_HD) {
        soLuong = ds_HD.soLuong;
        DS_HoaDon = new HoaDon[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_HoaDon[i] = ds_HD.DS_HoaDon[i].saoChep();  
    }

    @Override
    public void nhapThem() {
        System.out.println("**** Bat dau nhap danh sach hoa don ****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong hoa don muon them");
        DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maHD;
            while (kiemTraMa(maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don")))
                System.out.println(">> Ma hoa don nay da ton tai, nhap ma khac!!");
            String maNV = NhapChuan.docChuoiKhongRong("Nhap ma nhan vien");
            String maKH = NhapChuan.docChuoiKhongRong("Nhap ma khach hang");

            DS_HoaDon[soLuong] = new HoaDon(maHD, maNV, maKH);
            DS_HoaDon[soLuong++].nhap();
        }
        System.out.println("**** Ket thuc nhap danh sach hoa don ****");
    }
    @Override
    public void xuatDanhSach() {
        System.out.println("==== Danh Sach Cac Hoa Don ====");
        for(int i = 0; i < soLuong; i++) {
            System.out.println("------------");
            DS_HoaDon[i].xuat();
        }
        System.out.println("==== Het ====");
    }

    @Override
    public void xuatDanhSachRaFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileDanhSach))) {
            dos.writeInt(soLuong);
            for(int i = 0; i < soLuong; i++) {
                DS_HoaDon[i].ghiFile(dos);
            }
        } catch (IOException e) {
            System.out.println(">> Loi trong qua trinh xuat danh sach hoa don ra file");
            e.printStackTrace();
        }
    }
    @Override
    public void nhapDanhSachTuFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileDanhSach))) {
            soLuong = dis.readInt();
            if(soLuong > DS_HoaDon.length) DS_HoaDon = new HoaDon[soLuong];
            for(int i = 0; i < soLuong; i++) {
                DS_HoaDon[i] = new HoaDon("");
                DS_HoaDon[i].docFile(dis);
            }
        } catch (IOException e) {
            System.out.println(">> Co loi trong qua trinh nhap danh sach hoa don tu file");
            e.printStackTrace();
        }
    }

    private boolean them(HoaDon hd) {
        if(kiemTraMa(hd.getMaHoaDon())) return false;
        if(soLuong == DS_HoaDon.length) DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong + 1);
        DS_HoaDon[soLuong++] = hd;
        return true;
    }
    public boolean themHoaDon() {
        String maHD = NhapChuan.docChuoiKhongRong("Ma Hoa Don");
        if (kiemTraMa(maHD)) { 
            System.out.println(">> Ma hoa don da ton tai!"); 
            return false; 
        }
        String maNV = NhapChuan.docChuoiKhongRong("Ma nhan vien");
        String maKH = NhapChuan.docChuoiKhongRong("Ma khach hang");
        HoaDon hd = new HoaDon(maHD, maNV, maKH);
        hd.nhap();
        return them(hd);
    }

    private boolean sua(String maHD) {
        int viTri = timKiemViTriHoaDon(maHD);
        if(viTri == -1) return false;
        DS_HoaDon[viTri].nhap();
        return true;
    }
    @Override
    public void nhapSua() {
        if(!sua(NhapChuan.docChuoiKhongRong("Nhap ma hoa don can sua")))
            System.out.println(">> Hoa don khong ton tai trong danh sach!");
    }

    private boolean xoa(String maHD) {
        int viTri = timKiemViTriHoaDon(maHD);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++)
            DS_HoaDon[i] = DS_HoaDon[i + 1];
        DS_HoaDon[--soLuong] = null;
        return true;
    }
    @Override
    public void nhapXoa() {
        if(!xoa(NhapChuan.docChuoiKhongRong("Nhap ma hoa don can xoa")))
            System.out.println(">> Hoa don khong ton tai trong danh sach!");
    }

    private int timKiemViTriHoaDon(String maHD) {
        for(int i = 0; i < soLuong; i++)
            if(DS_HoaDon[i] != null && DS_HoaDon[i].getMaHoaDon().equals(maHD)) return i;
        return -1;
    }
    public boolean kiemTraMa(String maHD) {
        return timKiemViTriHoaDon(maHD) != -1;
    }
    public HoaDon timKiemHoaDon(String maHD) {
        int viTri = timKiemViTriHoaDon(maHD);
        return (viTri != -1) ? DS_HoaDon[viTri] : null;
    }

    public void xoaTheoDanhSach(DanhSachHoaDon DS_HD) {
        for (int i = 0; i < DS_HD.soLuong; i++)
            xoa(DS_HD.DS_HoaDon[i].getMaHoaDon());
    }
    public void nhapTuDanhSach(DanhSachHoaDon DS_HD) {
        for (int i = 0; i < DS_HD.soLuong; i++) 
            if (!kiemTraMa(DS_HD.DS_HoaDon[i].getMaHoaDon())) 
                them(DS_HD.DS_HoaDon[i].saoChep());
    }
    
    public DanhSachHoaDon timKiemTheoMaHD(String maHD) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_HoaDon[i] != null && DS_HoaDon[i].getMaHoaDon().equals(maHD)) 
                dsTam.them(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoMaNV(String maNV) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_HoaDon[i] != null && DS_HoaDon[i].getMaNhanVien().equals(maNV)) 
                dsTam.them(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoMaKH(String maKH) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_HoaDon[i] != null && DS_HoaDon[i].getMaKhachHang().equals(maKH)) 
                dsTam.them(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheotongTien(double tongTien) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_HoaDon[i] != null && DS_HoaDon[i].getTongTien() == tongTien)
                dsTam.them(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoNgayTaoDon(String ngayTaoDon) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_HoaDon[i] != null && DS_HoaDon[i].getNgayTaoDon().equals(ngayTaoDon)) 
                dsTam.them(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoTrangThai(String trangThai) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_HoaDon[i] != null && DS_HoaDon[i].getTrangThai().equalsIgnoreCase(trangThai)) 
                dsTam.them(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoPhuongThucThanhToan(String phuongThucThanhToan) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_HoaDon[i] != null && DS_HoaDon[i].getPhuongThucThanhToan().equalsIgnoreCase(phuongThucThanhToan)) 
                dsTam.them(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
}
