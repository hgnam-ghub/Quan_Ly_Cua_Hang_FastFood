package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import mohinh.HoaDon;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;
import rangbuoc.IDanhSach;

public class DanhSachHoaDon implements IDanhSach {
    private HoaDon[] DS_HoaDon;
    private int soLuong;
    private DanhSachNhanVien dsNhanVien;
    private DanhSachKhachHang dsKhachHang;

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
    public DanhSachHoaDon saoChep() {return new DanhSachHoaDon(this);}
    public int getSoLuong() { return soLuong; }

    @Override
    public void xuatDanhSach() {
       String[][] bangKhachHang = new String[soLuong + 1][];
        bangKhachHang[0] = new String[] {"Ma HD", "Ma NV", "Ma KH", "Ngay lap", "Tong tien", "Trang thai", "Phuong thuc thanh toan"};
        for (int i = 0; i < soLuong; i++) {
            bangKhachHang[i + 1] = DS_HoaDon[i].xuat();
        }
        FormatBang.inBangDuLieu("DANH SACH HOA DON", bangKhachHang);
        NhapChuan.docChuoi("Enter de quay lai");
    }
    @Override
    public void xuatDanhSachRaFile() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSHD))) {
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
        try (DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSHD))) {
            soLuong = dis.readInt();
            if(soLuong > DS_HoaDon.length) DS_HoaDon = new HoaDon[soLuong];
            for(int i = 0; i < soLuong; i++) {
                DS_HoaDon[i] = new HoaDon();
                DS_HoaDon[i].docFile(dis);
            }
        } catch (IOException e) {
            System.out.println(">> Co loi trong qua trinh nhap danh sach hoa don tu file");
            e.printStackTrace();
        }
    }
    private int timKiemViTriHoaDon(String maHD) {
        for(int i = 0; i < soLuong; i++)
            if(DS_HoaDon[i] != null && DS_HoaDon[i].getMaHoaDon().equals(maHD)) return i;
        return -1;
    }
    private HoaDon them(HoaDon hd) {
        if(kiemTraMa(hd.getMaHoaDon())) return null;
        if(soLuong == DS_HoaDon.length) DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong + 1);
        DS_HoaDon[soLuong++] = hd;
        return hd;
    }
    private HoaDon sua(String maHD) {
        int viTri = timKiemViTriHoaDon(maHD);
        if(viTri == -1) return null;
        return DS_HoaDon[viTri].nhap();
    }
    private boolean xoa(String maHD) {
        int viTri = timKiemViTriHoaDon(maHD);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++)
            DS_HoaDon[i] = DS_HoaDon[i + 1];
        DS_HoaDon[--soLuong] = null;
        return true;
    }
    public boolean kiemTraMa(String maHD) {
        return timKiemViTriHoaDon(maHD) != -1;
    }
    public HoaDon timKiemTheoMa(String maHD) {
        int viTri = timKiemViTriHoaDon(maHD);
        if (viTri == -1) return null;
        return DS_HoaDon[viTri].saoChep();
    }
    public HoaDon timKiemHoaDon(String maHD) {
        return timKiemTheoMa(maHD);
    }
    public boolean themVaoHoaDonDanhSach(HoaDon hd) {
        if (hd == null) return false;
        return them(hd.saoChep()) != null;
    }
    public boolean themHoaDon() {
        String maHD;
        while (true) {
            maHD = NhapChuan.docChuoiKhongRong("Ma San Pham");
            if (!kiemTraMa(maHD)) break;
            System.out.println(">> Ma hoa don va san pham nay da ton tai, nhap ma san pham khac!!");
        }
        HoaDon hd = new HoaDon();
        hd.nhap();
        return them(hd) != null;
    }
    private String nhapMaNVHopLe() {
        while (true) {
            String maNV = NhapChuan.docChuoiKhongRong("Nhap ma nhan vien");
            if (dsNhanVien == null || dsNhanVien.kiemTraMa(maNV)) return maNV;
            System.out.println(">> Ma nhan vien khong ton tai, nhap lai!");
        }
    }
    private String nhapMaKHHopLe() {
        while (true) {
            String maKH = NhapChuan.docChuoiKhongRong("Nhap ma khach hang");
            if (dsKhachHang == null || dsKhachHang.kiemTraMa(maKH)) return maKH;
            System.out.println(">> Ma khach hang khong ton tai, nhap lai!");
        }
    }
    @Override
    public void nhapTimKiem() {
        String maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don can tim");
        HoaDon kq = timKiemTheoMa(maHD);
        if (kq == null) {
            System.out.println(">> Khong tim thay hoa don!");
        } else {
            String[][] bang = new String[][] {
                {"Ma HD", "Ma NV", "Ma KH", "Ngay tao don", "Trang thai", "PT Thanh toan", "Tong tien"},
                kq.xuat()
            };
            FormatBang.inBangDuLieu("KET QUA TIM KIEM", bang);
        }
        NhapChuan.docChuoi("Enter de quay lai");
    }
    @Override
    public void nhapThem() {
        System.out.println("**** Bat dau nhap danh sach hoa don ****");
        int nhapThem= NhapChuan.docSoNguyenKhongAm("So luong hoa don can nhap them");
        DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong+nhapThem);
        for(int i=0; i<nhapThem; i++){
            System.out.println("--------------");
            System.out.printf("Nhap hoa don thu %d: \n", soLuong + 1);
            HoaDon hdMoi = new HoaDon();
            String maNV = nhapMaNVHopLe();
            String maKH = nhapMaKHHopLe();
            while (them(hdMoi.setMa(NhapChuan.docChuoiKhongRong("Nhap ma hoa don"), maNV, maKH)) == null) {
                System.out.println("Ma hoa don da ton tai, nhap lai ma hoa don khac!!!");
            }
            hdMoi.nhap();
        }
        System.out.println("**** Ket thuc nhap danh sach hoa don ****");
        NhapChuan.docChuoi("Enter de quay lai");
    }
     @Override
    public void nhapSua() {
        do {
            String maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
            if (sua(maHD) == null)
                System.out.println(">> Chi tiet hoa don nay khong ton tai trong danh sach");
            else
                System.out.println(">> Sua thanh cong");
            System.out.println("Co tiep tuc nhap sua them chi tiet hoa don khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }
    @Override
    public void nhapXoa() {
        do {
            String maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
            if (!xoa(maHD))
                System.out.println(">> Khong tim thay chi tiet hoa don can xoa");
            else
                System.out.println(">> Xoa thanh cong");
            System.out.println("Co tiep tuc xoa cac chi tiet hoa don khac khong?");
        } while (NhapChuan.docYesNo("", false));
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
    // tim theo nhieu ma cung luc: ma nao rong thi bo qua, cac ma con lai phai khop dong thoi
    // chi nhap 1 ma thi tra ve tat ca hoa don chua ma do
    public DanhSachHoaDon timKiemNhieuMa(String maHD, String maNV, String maKH) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++) {
            HoaDon hd = DS_HoaDon[i];
            if (hd == null) continue;
            if (!maHD.isEmpty() && !hd.getMaHoaDon().equals(maHD)) continue;
            if (!maNV.isEmpty() && !hd.getMaNhanVien().equals(maNV)) continue;
            if (!maKH.isEmpty() && !hd.getMaKhachHang().equals(maKH)) continue;
            dsTam.them(hd.saoChep());
        }
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    // tra ve ban sao da sap xep, danh sach goc giu nguyen thu tu
    public DanhSachHoaDon sapXepTheoNgayTaoDon() {
        return sapXepTheoNgayTaoDon(true);
    }
    // so sanh bang LocalDate (nam -> thang -> ngay), cung ngay thi xep theo ma hoa don de ket qua on dinh
    public DanhSachHoaDon sapXepTheoNgayTaoDon(boolean tangDan) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon(this);
        Comparator<HoaDon> soSanh = Comparator.comparing(HoaDon::getNgayTaoDon);
        if (!tangDan) soSanh = soSanh.reversed();
        soSanh = soSanh.thenComparing(HoaDon::getMaHoaDon);
        Arrays.sort(dsTam.DS_HoaDon, 0, dsTam.soLuong, soSanh);
        return dsTam;
    }
}
