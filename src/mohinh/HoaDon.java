package mohinh;

public final class HoaDon {
    private String maHD;
    private String maNV;
    private String maKH;
    private String ngayTaoDon;
    private String trangThai;
    private String phuongThucThanhToan;
    private double tongTien;
    public HoaDon(String maHD, String maNV, String maKH, String ngayTaoDon, String trangThai, String phuongThucThanhToan, double tongTien) {
        if(!setMa(maHD, maNV, maKH)) {maHD = "khong hop le"; maNV = "khong hop le"; maKH = "khong hop le";}
        if(!setNgayTaoDon(ngayTaoDon)) ngayTaoDon = "01/01/2000";
        if(!setTrangThai(trangThai)) trangThai = "khong co";
        if(!setPhuongThucThanhToan(phuongThucThanhToan)) phuongThucThanhToan = "khong co";
        if(!setTongTien(tongTien)) tongTien = 0;
    }
    public HoaDon(String maHD, String maNV, String maKH) {
        this(maHD, maNV, maKH, "01/01/2000", "dang xu ly", "N/A", 0);
    }
    public HoaDon(String maHD, String maNV, String maKH, HoaDon hd) {
        this(maHD, maNV, maKH, hd.ngayTaoDon, hd.trangThai, hd. phuongThucThanhToan, hd.tongTien);
    }
    public HoaDon(HoaDon hd) {this(hd.maHD, hd.maNV, hd.maKH, hd);}

    public HoaDon saoChep() {
        return new HoaDon(maHD, maNV, maKH, this);
    }
    public HoaDon saoChepVoiMaMoi(String maHoaDon, String maNhanVien, String maKhachHang) {
        return new HoaDon(maHoaDon, maNhanVien, maKhachHang, this);
    }

    public void nhap() {

    }
    public void xuat() {

    }
    public void ghiFile() {

    }
    public void docFile() {

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
    private boolean setNgayTaoDon(String ngayTaoDonMoi) {
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
    private boolean setTongTien(double tongTienMoi) {
        if(tongTienMoi < 0) return false;
        tongTien = tongTienMoi;
        return true;
    }
}
