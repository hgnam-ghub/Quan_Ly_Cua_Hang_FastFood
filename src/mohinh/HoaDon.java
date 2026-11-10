package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import tienich.NhapChuan;
public final class HoaDon {
    private String maHD;
    private String maNV;
    private String maKH;
    private String ngayTaoDon;
    private String trangThai;
    private String phuongThucThanhToan;
    private double tongTien;
    private static int tongSoHoaDon = 0; // Thuộc tính static
    
    public HoaDon(String maHD, String maNV, String maKH, String ngayTaoDon, String trangThai, String phuongThucThanhToan, double tongTien) {
        if(!setMa(maHD, maNV, maKH)) {this.maHD = "khong hop le"; this.maNV = "khong hop le"; this.maKH = "khong hop le";}
        if(!setNgayTaoDon(ngayTaoDon)) this.ngayTaoDon = "01/01/2000";
        if(!setTrangThai(trangThai)) this.trangThai = "khong co";
        if(!setPhuongThucThanhToan(phuongThucThanhToan)) this.phuongThucThanhToan = "khong co";
        if(!setTongTien(tongTien)) this.tongTien = 0;
        tongSoHoaDon++;
    }
    public HoaDon(String maHD, String maNV, String maKH) {
        this(maHD, maNV, maKH, "01/01/2000", "dang xu ly", "N/A", 0);
    }
    public HoaDon(String maHD, String maNV, String maKH, HoaDon hd) {
        this(maHD, maNV, maKH, hd.ngayTaoDon, hd.trangThai, hd.phuongThucThanhToan, hd.tongTien);
    }
    public HoaDon(HoaDon hd) {this(hd.maHD, hd.maNV, hd.maKH, hd);}
    public HoaDon saoChep() {return new HoaDon(maHD, maNV, maKH, this);}
    public HoaDon saoChepVoiMaMoi(String maHoaDon, String maNhanVien, String maKhachHang) {
        return new HoaDon(maHoaDon, maNhanVien, maKhachHang, this);
    }
    public static int getTongSoHoaDon() {return tongSoHoaDon;}

    protected final void xuatChuoi(String prompt, Object giaTri) {System.out.println(prompt + ": " + giaTri);}
    protected final void xuatChuoi(String prompt, Object giaTri, String donVi) {System.out.println(prompt + ": " + giaTri + ' ' + donVi);}
    public void nhap() {
        System.out.println("Nhap thong tin cho hoa don co ma: " + maHD);
        setNgayTaoDon(NhapChuan.docChuoiKhongRong("Ngay tao don (dd/MM/yyyy)"));
        setTrangThai(NhapChuan.docChuoiKhongRong("Trang thai"));
        setPhuongThucThanhToan(NhapChuan.docChuoiKhongRong("Phuong thuc thanh toan"));
        setTongTien(NhapChuan.docSoThucKhongAm("Tong tien"));
    }
    public void xuat() {
        xuatChuoi("Ma hoa don", maHD);
        xuatChuoi("Ma nhan vien", maNV);
        xuatChuoi("Ma khach hang", maKH);
        xuatChuoi("Ngay tao don", ngayTaoDon);
        xuatChuoi("Trang thai", trangThai);
        xuatChuoi("Phuong thuc thanh toan", phuongThucThanhToan);
        xuatChuoi("Tong tien", tongTien, "$");
    }
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maHD);
        dos.writeUTF(maNV);
        dos.writeUTF(maKH);
        dos.writeUTF(ngayTaoDon);
        dos.writeUTF(trangThai);
        dos.writeUTF(phuongThucThanhToan);
        dos.writeDouble(tongTien);
    }
    public void docFile(DataInputStream dis) throws IOException {
        maHD = dis.readUTF();
        maNV = dis.readUTF();
        maKH = dis.readUTF();
        ngayTaoDon = dis.readUTF();
        trangThai = dis.readUTF();
        phuongThucThanhToan = dis.readUTF();
        tongTien = dis.readDouble();
    }

    public String getMaHoaDon() {return maHD;}
    public String getMaNhanVien() {return maNV;}
    public String getMaKhachHang() {return maKH;}
    public String getNgayTaoDon() {return ngayTaoDon;}
    public String getTrangThai() {return trangThai;}
    public String getPhuongThucThanhToan() {return phuongThucThanhToan;}
    public double getTongTien() {return tongTien;}

    private boolean setMa(String maHDMoi, String maNVMoi, String maKHMoi) {
        if(maHDMoi.isEmpty() || maNVMoi.isEmpty() || maKHMoi.isEmpty()) return false;
        maHD = maHDMoi;
        maNV = maNVMoi;
        maKH = maKHMoi;
        return true;
    }
    public boolean setNgayTaoDon(String ngayTaoDonMoi) {
        if(ngayTaoDonMoi.isEmpty()) return false;
        ngayTaoDon = ngayTaoDonMoi;
        return true;
    }
    public boolean setTrangThai(String trangThaiMoi) {
        if(trangThaiMoi.isEmpty()) return false;
        trangThai = trangThaiMoi;
        return true;
    }
    public boolean setPhuongThucThanhToan(String phuongThucThanhToanMoi) {
        if(phuongThucThanhToanMoi.isEmpty()) return false;
        phuongThucThanhToan = phuongThucThanhToanMoi;
        return true;
    }
    public boolean setTongTien(double tongTienMoi) {
        if(tongTienMoi < 0) return false;
        tongTien = tongTienMoi;
        return true;
    }
}
