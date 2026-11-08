package danhsach;
import java.util.Arrays;
import mohinh.ChiTietHoaDon;
import quanly.NhapChuan;
public final class DanhSachChiTietHoaDon {
    private ChiTietHoaDon[] DS_ChiTietHoaDon;
    private int soLuong;
    public int getSoLuong() {return soLuong;}
    public DanhSachChiTietHoaDon() {
        soLuong = 0;
        DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
    }
    public DanhSachChiTietHoaDon(DanhSachChiTietHoaDon ds_CTHD) {
        soLuong = ds_CTHD.soLuong;
        DS_ChiTietHoaDon = new ChiTietHoaDon[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_ChiTietHoaDon[i] = ds_CTHD.DS_ChiTietHoaDon[i].saoChep(); 
    }
    public void nhap() {
        System.out.println("**********************");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong chi tiet hoa don muon them");
        DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("-------------");
            String maHD, maSP;
            while (true) {
                maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
                maSP = NhapChuan.docChuoiKhongRong("Nhap ma san pham");
                if(!kiemTraMa(maHD, maSP)) break;
                System.out.println(">> Ma hoa don va san pham nay da ton tai, nhap lai!!");
            }
            DS_ChiTietHoaDon[soLuong] = new ChiTietHoaDon(maHD, maSP);
            DS_ChiTietHoaDon[soLuong].nhap();
            soLuong++;
        }
    }
    public void xuat() {
        System.out.println("---Danh Sach Cac Chi Tiet Hoa Don---");
        for(int i = 0; i < soLuong; i++) {
            System.out.println("-------------");
            DS_ChiTietHoaDon[i].xuat();
        }
    }
    public boolean ghiFile() {
        return true;
    }
    public boolean docFile() {
        return true;
    }
    public boolean themChiTietHoaDon(String maHD) {
        String maSP;
        while (true) {
            maSP = NhapChuan.docChuoiKhongRong("Ma San Pham");
            if(kiemTraMa(maHD, maSP)) break;
            System.out.println(">> Ma hoa don va san pham nay da ton tai, nhap ma san pham khac!!");
        }
        if (soLuong == DS_ChiTietHoaDon.length)
            DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong + 1);
        DS_ChiTietHoaDon[soLuong] = new ChiTietHoaDon(maHD, maSP);
        soLuong++;
        return true;
    }
    public boolean xoaChiTietHoaDon(String maHD, String maSP) {
        int viTri = -1;
        for (int i = 0; i < soLuong; i++) {
            if (DS_ChiTietHoaDon[i].getMaHoaDon().equals(maHD) && DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP)) {
                viTri = i;
                break;
            }
        }
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++) 
            DS_ChiTietHoaDon[i] = DS_ChiTietHoaDon[i + 1];
        DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong - 1);
        soLuong--;
        return true;
    }
    public ChiTietHoaDon timKiemChiTietHoaDon(String maHD, String maSP) {
        for(int i = 0; i < soLuong; i++) 
            if(DS_ChiTietHoaDon[i].getMaHoaDon().equals(maHD) && DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP)) 
                return DS_ChiTietHoaDon[i];
        return null;
    }
    public boolean kiemTraMa(String maHD, String maSP) {
        return timKiemChiTietHoaDon(maHD, maSP) != null;
    }
    private boolean nhapThamChieu(ChiTietHoaDon cthdThamChieu) {
        if (kiemTraMa(cthdThamChieu.getMaHoaDon(), cthdThamChieu.getMaSanPham())) return false;
        if (soLuong == DS_ChiTietHoaDon.length) DS_ChiTietHoaDon = Arrays.copyOf(DS_ChiTietHoaDon, soLuong + 1);
        DS_ChiTietHoaDon[soLuong++] = cthdThamChieu;
        return true;
    }
    public void xoaTheoDanhSach(DanhSachChiTietHoaDon DS_HD) {
        for (int i = 0; i < DS_HD.soLuong; i++) 
            xoaChiTietHoaDon(DS_HD.DS_ChiTietHoaDon[i].getMaHoaDon(), DS_HD.DS_ChiTietHoaDon[i].getMaSanPham());
    }
    public void nhapTuDanhSach(DanhSachChiTietHoaDon DS_HD) {
        for (int i = 0; i < DS_HD.soLuong; i++) 
            if (!kiemTraMa(DS_HD.DS_ChiTietHoaDon[i].getMaHoaDon(), DS_HD.DS_ChiTietHoaDon[i].getMaSanPham())) 
                nhapThamChieu(DS_HD.DS_ChiTietHoaDon[i]);
    }
    public DanhSachChiTietHoaDon timKiemMaHD(String maHD) {
        DanhSachChiTietHoaDon dsTam = new DanhSachChiTietHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_ChiTietHoaDon[i].getMaHoaDon().equals(maHD)) 
                dsTam.nhapThamChieu(DS_ChiTietHoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachChiTietHoaDon timKiemMaSP(String maSP) {
        DanhSachChiTietHoaDon dsTam = new DanhSachChiTietHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_ChiTietHoaDon[i].getMaSanPham().equals(maSP)) 
                dsTam.nhapThamChieu(DS_ChiTietHoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
}
