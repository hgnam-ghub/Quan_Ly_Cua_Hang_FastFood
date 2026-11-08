package danhsach;
import java.util.Arrays;
import mohinh.KhachHang;
import quanly.NhapChuan;
public class DanhSachKhachHang {
    private KhachHang[] DS_KhachHang;
    private int soLuong;
    public int getSoLuong() {return soLuong;}
    public DanhSachKhachHang() {
        soLuong = 0;
        DS_KhachHang = new KhachHang[soLuong];
    }
    public DanhSachKhachHang(DanhSachKhachHang DS_KH) {
        soLuong = DS_KH.soLuong;
        DS_KhachHang = new KhachHang[soLuong];
        for(int i = 0; i < soLuong; i++)
            DS_KhachHang[i] = DS_KH.DS_KhachHang[i].saoChep(); 
    }
    public void nhap() {
        System.out.println("**********************");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong khach hang muon them");
        DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong + nhapThem);
        for(int i = 0; i < nhapThem; i++) {
            System.out.println("-------------");
            String maKH;
            while (true) {
                maKH = NhapChuan.docChuoiKhongRong("Nhap ma khach hang");
                if(!kiemTraMa(maKH)) break;
                System.out.println(">> Ma khach hang nay da ton tai, nhap ma khac!!");
            }
            DS_KhachHang[soLuong] = new KhachHang(maKH);
            DS_KhachHang[soLuong].nhap();
            soLuong++;
        }
    }
    public void xuat() {
        System.out.println("---Danh Sach Cac Khach Hang---");
        for(int i = 0; i < soLuong; i++) {
            System.out.println("-------------");
            DS_KhachHang[i].xuat();
        }
    }
    public boolean ghiFile() {
        return true;
    }
    public boolean docFile() {
        return true;
    }
    public boolean themKhachHang() {
        String ma;
        while (true) {
            ma = NhapChuan.docChuoiKhongRong("Ma Khach Hang");
            if(!kiemTraMa(ma)) break;
            System.out.println(">> Ma khach hang nay da ton tai, nhap ma khac!!");
        }
        if(soLuong == DS_KhachHang.length)
            DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong+1);
        DS_KhachHang[soLuong] = new KhachHang(ma);
        DS_KhachHang[soLuong].nhap();
        soLuong++;
        return true;
    }
    public boolean xoaKhachHang(String ma) {
        int viTri = -1;
        for (int i = 0; i < soLuong; i++) {
            if (DS_KhachHang[i].getMa().equals(ma)) {
                viTri = i;
                break;
            }
        }
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++)
            DS_KhachHang[i] = DS_KhachHang[i + 1];
        DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong - 1);
        soLuong--;
        return true;
    }
    public KhachHang timKiemKhachHang(String ma) {
        for(int i = 0; i < soLuong; i++) 
            if(DS_KhachHang[i].getMa().equals(ma)) 
                return DS_KhachHang[i];
        return null;
    }
    public boolean kiemTraMa(String ma) {
        return timKiemKhachHang(ma) != null;
    }
    private boolean nhapThamChieu(KhachHang khThamChieu) {
        if (kiemTraMa(khThamChieu.getMa())) return false;
        if (soLuong == DS_KhachHang.length) DS_KhachHang = Arrays.copyOf(DS_KhachHang, soLuong + 1);
        DS_KhachHang[soLuong++] = khThamChieu;
        return true;
    }
    public void xoaTheoDanhSach(DanhSachKhachHang DS_KH) {
        for (int i = 0; i < DS_KH.soLuong; i++) 
            xoaKhachHang(DS_KH.DS_KhachHang[i].getMa());
    }
    public void nhapTuDanhSach(DanhSachKhachHang DS_KH) {
        for (int i = 0; i < DS_KH.soLuong; i++) 
            if (!kiemTraMa(DS_KH.DS_KhachHang[i].getMa())) 
                nhapThamChieu(DS_KH.DS_KhachHang[i]);
    }
    public DanhSachKhachHang timkiemTheoMa(String ma) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++)
            if (DS_KhachHang[i].getMa().equals(ma))
                dsTam.nhapThamChieu(DS_KhachHang[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachKhachHang timkiemTheoHo(String ho) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++) 
            if (DS_KhachHang[i].getHo().equalsIgnoreCase(ho)) 
                dsTam.nhapThamChieu(DS_KhachHang[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachKhachHang timkiemTheoTen(String ten) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++) 
            if (DS_KhachHang[i].getTen().equalsIgnoreCase(ten)) 
                dsTam.nhapThamChieu(DS_KhachHang[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
    public DanhSachKhachHang timkiemTheotongTienDaMua(double tongTienDaMua) {
        DanhSachKhachHang dsTam = new DanhSachKhachHang();
        for (int i = 0; i < soLuong; i++) 
            if (DS_KhachHang[i].getTongSoTienMua() == tongTienDaMua) 
                dsTam.nhapThamChieu(DS_KhachHang[i]);
        return (dsTam.soLuong == 0) ? null : dsTam;
    }
}
