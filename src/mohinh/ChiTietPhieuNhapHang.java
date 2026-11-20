package mohinh;

public final class ChiTietPhieuNhapHang{
    private String maPNH; 
    private String maNL; 
    private double donGiaNhap;
    private double thanhTien;
    private int soLuong;

    public ChiTietPhieuNhapHang (String maPNH, String maNL, double donGiaNhap, double thanhTien, int soLuong) {
        if(!setMa(maPNH, maNL)) {this.maPNH = "khong hop le"; this.maNL = "khong hop le";}
        if(!setDonGiaNhap(donGiaNhap)) this.donGiaNhap = 0;
        if(!setThanhTien(thanhTien)) this.thanhTien = 0;
        if(!setSoLuong(soLuong)) this.soLuong = 1;
    }
    public ChiTietPhieuNhapHang (String maPNHMoi, String maNLMoi) {
        this(maPNHMoi, maNLMoi, 0, 0, 1);
    }
    public ChiTietPhieuNhapHang (String maPNHMoi, String MaNLMoi, ChiTietPhieuNhapHang CTPNH) {
        this(maPNHMoi, MaNLMoi, CTPNH.donGiaNhap, CTPNH.thanhTien, CTPNH.soLuong);
    }
    public ChiTietPhieuNhapHang (ChiTietPhieuNhapHang CTPNH) {
        this(CTPNH.maPNH, CTPNH.maNL, CTPNH);
    }
    public ChiTietPhieuNhapHang saoChep() {
        return new ChiTietPhieuNhapHang(this);
    }
    public ChiTietPhieuNhapHang saoChepVoiMaMoi(String maPNHMoi, String maNLMoi) {
        return new ChiTietPhieuNhapHang(maPNHMoi, maNLMoi, this);
    }

    public void xuat() {

    }
    public boolean ghiFile() {
        return true;
    }
    public boolean docFile() {
        return true;
    }

    public String getMaPNH() {return maPNH;}
    public String getMaNL() {return maNL;}
    public double getDonGiaNhap() {return donGiaNhap;}
    public double getThanhTien() {return thanhTien;}
    public int getSoLuong() {return soLuong;}

    private boolean setMa(String maPNHMoi, String maNLMoi) {
        if(maPNHMoi.isEmpty() || maNLMoi.isEmpty()) return false;
        maPNH = maPNHMoi;
        maNL = maNLMoi;
        return true;
    }
    private boolean setDonGiaNhap(double donGiaNhapMoi) {
        if(donGiaNhapMoi < 0 ) return false;
        donGiaNhap = donGiaNhapMoi;
        return true;
    }
    private boolean setThanhTien(double thanhTienMoi) {
        if(thanhTienMoi < 0 ) return false;
        thanhTien = thanhTienMoi;
        return true;
    }
    private boolean setSoLuong(int soLuongMoi) {
        if(soLuongMoi <= 1) return false;
        soLuong = soLuongMoi;
        return true;
    }
}
