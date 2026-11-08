package danhsach;
import java.util.Arrays;
import mohinh.HoaDon;
import quanly.NhapChuan;
public class DanhSachHoaDon {
    private HoaDon[] DS_HoaDon;
    private int soLuong;
    public int getSoLuong() {return soLuong;}
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
    public void nhap() {
        System.out.println("**********************");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong hoa don muon them");
        DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("-------------");
            String maHD;
            while (true) {
                maHD = NhapChuan.docChuoiKhongRong("Nhap ma hoa don");
                if(!kiemTraMa(maHD)) break;
                System.out.println(">> Ma hoa don nay da ton tai, nhap ma khac!!");
            }
            DS_HoaDon[soLuong] = new HoaDon(maHD);
            DS_HoaDon[soLuong].nhap();
            soLuong++;
        }
    }
    public void xuat() {
        System.out.println("---Danh Sach Cac Hoa Don---");
        for(int i = 0; i < soLuong; i++) {
            System.out.println("-------------");
            DS_HoaDon[i].xuat();
        }
    }
    public boolean ghiFile() {
        return true;
    }
    public boolean docFile() {
        return true;
    }
    public boolean themHoaDon() {
        String maHD;
        while (true) {
            maHD = NhapChuan.docChuoiKhongRong("Ma Hoa Don");
            if (!kiemTraMa(maHD)) break;
            System.out.println(">> Ma hoa don nay da ton tai, nhap ma khac!!");
        }
        String maNV = NhapChuan.docChuoiKhongRong("Ma nhan vien");
        String maKH = NhapChuan.docChuoiKhongRong("Ma khach hang");
        if (soLuong == DS_HoaDon.length)
            DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong + 1);
        DS_HoaDon[soLuong] = new HoaDon(maHD, maNV, maKH);
        DS_HoaDon[soLuong].nhap();
        soLuong++;
        return true;
    }
    public boolean xoaHoaDon(String maHD) {
        int viTri = -1;
        for (int i = 0; i < soLuong; i++) {
            if (DS_HoaDon[i].getMaHoaDon().equals(maHD)) {
                viTri = i;
                break;
            }
        }
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++)
            DS_HoaDon[i] = DS_HoaDon[i + 1];
        DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong - 1);
        soLuong--;
        return true;
    }
    public HoaDon timKiemHoaDon(String maHD) {
        for(int i = 0; i < soLuong; i++)
            if(DS_HoaDon[i].getMaHoaDon().equals(maHD)) 
                return DS_HoaDon[i];
        return null;
    }
    public boolean kiemTraMa(String maHD) {
        return timKiemHoaDon(maHD) != null;
    }
    private boolean nhapThamChieu(HoaDon hdThamChieu) {
        if (kiemTraMa(hdThamChieu.getMaHoaDon())) return false;
        if (soLuong == DS_HoaDon.length) DS_HoaDon = Arrays.copyOf(DS_HoaDon, soLuong + 1);
        DS_HoaDon[soLuong++] = hdThamChieu;
        return true;
    }
    public void xoaTheoDanhSach(DanhSachHoaDon DS_HD) {
        for (int i = 0; i < DS_HD.soLuong; i++)
            xoaHoaDon(DS_HD.DS_HoaDon[i].getMaHoaDon());
    }
    public void nhapTuDanhSach(DanhSachHoaDon DS_HD) {
        for (int i = 0; i < DS_HD.soLuong; i++) 
            if (!kiemTraMa(DS_HD.DS_HoaDon[i].getMaHoaDon())) 
                nhapThamChieu(DS_HD.DS_HoaDon[i]);
    }
    public DanhSachHoaDon timKiemTheoMaHD(String maHD) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_HoaDon[i].getMaHoaDon().equals(maHD)) 
                dsTam.nhapThamChieu(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoMaNV(String maNV) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_HoaDon[i].getMaNhanVien().equals(maNV)) 
                dsTam.nhapThamChieu(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoMaKH(String maKH) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_HoaDon[i].getMaKhachHang().equals(maKH)) 
                dsTam.nhapThamChieu(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheotongTien(double tongTien) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_HoaDon[i].getTongTien() == tongTien)
                dsTam.nhapThamChieu(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoNgayTaoDon(String ngayTaoDon) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++)
            if (DS_HoaDon[i].getNgayTaoDon().equals(ngayTaoDon)) 
                dsTam.nhapThamChieu(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoTrangThai(String trangThai) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_HoaDon[i].getTrangThai().equalsIgnoreCase(trangThai)) 
                dsTam.nhapThamChieu(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachHoaDon timKiemTheoPhuongThucThanhToan(String phuongThucThanhToan) {
        DanhSachHoaDon dsTam = new DanhSachHoaDon();
        for (int i = 0; i < soLuong; i++) 
            if (DS_HoaDon[i].getPhuongThucThanhToan().equalsIgnoreCase(phuongThucThanhToan)) 
                dsTam.nhapThamChieu(DS_HoaDon[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
}
