package mohinh;

public final class ChiTietHoaDon{
    private String maHD;
    private String maSP;
    private double donGiaBan;
    private double thanhTien;
    private int soLuong;

    public ChiTietHoaDon (String maHD, String maSP, double donGiaBan, double thanhTien, int soLuong) {
        if(!setMa(maHD, maSP)) {this.maHD = "khong hop le"; this.maSP = "khong hop le";}
        if(!setDonGiaBan(donGiaBan)) this.donGiaBan = 0;
        if(!setThanhTien(thanhTien)) this.thanhTien = 0;
        if(!setSoLuong(soLuong)) this.soLuong = 0;
    }
    public ChiTietHoaDon (String maHD, String maSP) {
        this(maHD, maSP, 0, 0, 1);
    }
    public ChiTietHoaDon(String maHD, String maSP, ChiTietHoaDon cthd) {
        this(maHD, maSP, cthd.donGiaBan, cthd.thanhTien,  cthd.soLuong);
    }
    public ChiTietHoaDon(ChiTietHoaDon cthd) {
        this(cthd.maHD, cthd.maSP, cthd);
    }
    public ChiTietHoaDon saoChep() {
        return new ChiTietHoaDon(this);
    }
    public ChiTietHoaDon saoChepVoiMaMoi(String maHDMoi, String maSPMoi) {
        return new ChiTietHoaDon(maHDMoi, maSPMoi, this);
    }

    public void xuat() {
        
    }
    public boolean ghiFile() {
        return true;
    }
    public boolean docFile() {
        return true;
    }

    public String getMaHoaDon() {return maHD;}
    public String getMaSanPham() {return maSP;}
    public double getDonGiaBan() {return donGiaBan;}
    public double getThanhTien() {return thanhTien;}
    public int getSoLuong() {return soLuong;}

    private boolean setMa(String maHDMoi, String maSPMoi) {
        if(maHDMoi.isEmpty() || maSPMoi.isEmpty()) return false;
        maHD = maHDMoi;
        maSP = maSPMoi;
        return true;
    }
    private boolean setDonGiaBan(double donGiaBanMoi) {
        if(donGiaBanMoi < 0) return false;
        donGiaBan = donGiaBanMoi;
        return true;
    }
    private boolean setThanhTien(double thanhTien) {
        if(thanhTien < 0) return false;
        this.thanhTien = thanhTien;
        return true;
    }
    private boolean setSoLuong(int soLuongMoi) {
        if(soLuongMoi <= 0) return false;
        soLuong = soLuongMoi;
        return true;
    }
}
