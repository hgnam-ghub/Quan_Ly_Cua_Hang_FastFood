package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import mohinh.KhachHang;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;
public class DanhSachKhachHang implements IDanhSach {
    private KhachHang[] DS_KhachHang;
    private int soLuong;
    public DanhSachKhachHang() {
        soLuong = 0;
        DS_KhachHang = new KhachHang[soLuong];
    }
    public DanhSachKhachHang(DanhSachKhachHang DS_KH) {
        soLuong = DS_KH.soLuong;
        DS_KhachHang = new KhachHang[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_KhachHang[i] = DS_KH.DS_KhachHang[i].saoChep();
    }
    public DanhSachKhachHang saoChep() { return new DanhSachKhachHang(this); }
    public int getSoLuong() { return soLuong; }
    @Override
    public void xuatDanhSach() {
        String[][] bangKhachHang = new String[soLuong + 1][];
        bangKhachHang[0] = new String[] {"Ma KH", "Ho", "Ten", "So dien thoai", "Dia chi", "Tong tien da mua"};
        for (int i = 0; i < soLuong; i++) {
            bangKhachHang[i + 1] = DS_KhachHang[i].xuat();
        }
        FormatBang.inBangDuLieu("DANH SACH KHACH HANG", bangKhachHang);
        NhapChuan.docChuoi("Enter de quay lai");
    }
    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSKH));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) {
            DS_KhachHang[i].ghiFile(dos);
        }
        dos.close();
    }
    @Override
    public void nhapDanhSachTuFile() throws IOException {
        int i = 0;
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSKH));
        soLuong = dis.readInt();
        if (DS_KhachHang.length < soLuong) DS_KhachHang = new KhachHang[soLuong];
        for (i = 0; i < soLuong; i++) {
            DS_KhachHang[i] = new KhachHang();
            DS_KhachHang[i].docFile(dis);
        }
        dis.close();
    }
    // phuong thuc noi bo, dung de tao chuoi xu ly cho gon
    private int timKiemViTriKhachHang(String ma) {
        for (int i = 0; i < soLuong; i++)
            if (DS_KhachHang[i] != null && DS_KhachHang[i].getMa().equals(ma)) return i;
        return -1;
    }
    // them, sua, xoa
    private KhachHang them(KhachHang kh) {
        if (kiemTraMa(kh.getMa())) return null;
        if (soLuong == DS_KhachHang.length) DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong + 1);
        DS_KhachHang[soLuong++] = kh;
        return kh;
    }
    private KhachHang sua(String ma) {
        int viTri = timKiemViTriKhachHang(ma);
        if (viTri == -1) return null;
        return DS_KhachHang[viTri].nhap();
    }
    private boolean xoa(String ma) {
        int viTri = timKiemViTriKhachHang(ma);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_KhachHang[i] = DS_KhachHang[i + 1];
        }
        DS_KhachHang[--soLuong] = null;
        return true;
    }
    // cac phuong thuc giao tiep ra ben ngoai
    public boolean kiemTraMa(String ma) { return timKiemViTriKhachHang(ma) != -1; }
    // tra ra KhachHang sao chep, ben ngoai xem duoc nhung khong sua duoc danh sach
    public KhachHang timKiemTheoMa(String ma) {
        int viTriKH = timKiemViTriKhachHang(ma);
        if (viTriKH == -1) return null;
        return DS_KhachHang[viTriKH].saoChep();
    }
    public KhachHang timKiemKhachHang(String ma) {
        return timKiemTheoMa(ma);
    }
    // nhan KhachHang va tu dong sao chep ra, chan tham chieu
    public boolean themVaoKhachHangDanhSach(KhachHang kh) {
        if (kh == null) return false;
        return them(kh.saoChep()) != null;
    }
    public boolean themKhachHang() {
        String ma = NhapChuan.docChuoiKhongRong("Ma Khach Hang");
        if (kiemTraMa(ma)) {
            System.out.println(">> Ma nay da ton tai!");
            return false;
        }
        KhachHang kh = new KhachHang();
        kh.nhap();
        return them(kh) != null;
    }
    @Override
    public void nhapTimKiem() {
        System.out.println("**** Tim kiem khach hang theo ma ****");
        String[] ma = NhapChuan.docNhieuMa("Ma khach hang");
        DanhSachKhachHang kq = timkiemTheoMa(ma[0]);
        if (kq == null) {
            System.out.println(">> Khong tim thay khach hang!");
        } else {
            String[][] bang = new String[kq.soLuong + 1][];
            bang[0] = new String[] {"Ma KH", "Ho", "Ten", "So dien thoai", "Ngay sinh", "Tong tien da mua"};
            for (int i = 0; i < kq.soLuong; i++)
                bang[i + 1] = kq.DS_KhachHang[i].xuat();
            FormatBang.inBangDuLieu("KET QUA TIM KIEM KHACH HANG", bang);
        }
        NhapChuan.docChuoi("Enter de quay lai");
    }
    public void nhapSapXep() {
        System.out.println("**** Sap xep khach hang theo ngay sinh ****");
        DanhSachKhachHang dsDaSapXep = sapXepTheoNgaySinh(NhapChuan.docThuTuSapXep());
        dsDaSapXep.xuatDanhSach();
    }
    // them, sua, xoa
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach khach hang****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong khach hang muon them");
        DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong + nhapThem);
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            System.out.printf("Nhap thong tin khach hang thu %d:\n", soLuong + 1);
            KhachHang khMoi = new KhachHang();
            while(them(khMoi.setMa(NhapChuan.docChuoiKhongRong("Nhap ma khach hang"))) == null)
                System.out.println("Ma nay da ton tai trong danh sach, nhap lai!!!");
            khMoi.nhap();
        }
        System.out.println("****Ket thuc nhap danh sach khach hang****");
        NhapChuan.docChuoi("Enter de quay lai");
    }
    public void nhapSua() {
        do {
            if (sua(NhapChuan.docChuoiKhongRong("Nhap ma khach hang")) == null)
                System.out.println(">> Khach hang nay khong ton tai trong danh sach");
            else System.out.println(">> Sua thanh cong");
            System.out.println("Co tiep tuc nhap sua them khach hang khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }
    public void nhapXoa() {
        do {
            if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma khach hang")))
                System.out.println(">> Khong tim thay khach hang can xoa");
            else System.out.println(">> Xoa thanh cong");
            System.out.println("Co tiep tuc xoa cac khach hang khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }
    public void xoaTheoDanhSach(DanhSachKhachHang DS_KH) {
        for (int i = 0; i < DS_KH.soLuong; i++)
            xoa(DS_KH.DS_KhachHang[i].getMa());
    }
    public void nhapTuDanhSach(DanhSachKhachHang DS_KH) {
        for (int i = 0; i < DS_KH.soLuong; i++)
            if (!kiemTraMa(DS_KH.DS_KhachHang[i].getMa()))
                them(DS_KH.DS_KhachHang[i].saoChep());
    }
    public DanhSachKhachHang timkiemTheoMa(String ma) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++)
            if (DS_KhachHang[i] != null && DS_KhachHang[i].getMa().equals(ma))
                dsTam.them(DS_KhachHang[i].saoChep());
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    // tra ve ban sao da sap xep, danh sach goc giu nguyen thu tu
    public DanhSachKhachHang sapXepTheoNgaySinh() {
        return sapXepTheoNgaySinh(true);
    }
    // so sanh bang LocalDate (nam -> thang -> ngay), cung ngay sinh thi xep theo ma khach hang de ket qua on dinh
    public DanhSachKhachHang sapXepTheoNgaySinh(boolean tangDan) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang(this);
        Comparator<KhachHang> soSanh = Comparator.comparing(KhachHang::getNgaySinh);
        if (!tangDan) soSanh = soSanh.reversed();
        soSanh = soSanh.thenComparing(KhachHang::getMa);
        Arrays.sort(dsTam.DS_KhachHang, 0, dsTam.soLuong, soSanh);
        return dsTam;
    }
}
