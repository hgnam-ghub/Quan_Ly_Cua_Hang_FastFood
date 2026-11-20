package mohinh;

public final class PhieuNhapHang {
    private String maPNH;
    private String maNV;
    private String maNCC;
    private String ngayTaoPhieu;
    private String trangThai;
    private String phuongThucThanhToan;
    private double tongTien;
    
    public PhieuNhapHang(String maPNH, String maNV, String maNCC, String ngayTaoPhieu, String trangThai, String phuongThucThanhToan, double tongTien) {
        this.maPNH = maPNH;
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.ngayTaoPhieu = ngayTaoPhieu;
        this.trangThai = trangThai;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tongTien = tongTien;
    }
    public PhieuNhapHang(String maPNH, String maNV, String maNCC) {
        this(maPNH, maNV, maNCC, "01/01/2000", "N/A", "N/A", 0);
    }
    private PhieuNhapHang(String maPNH, String maNV, String maNCC, PhieuNhapHang pnh) {
        this(maPNH, maNV, maNCC, pnh.ngayTaoPhieu, pnh.trangThai, pnh.phuongThucThanhToan, pnh.tongTien);
    }
    public PhieuNhapHang saoChep() {
        return new PhieuNhapHang(maPNH, maNV, maNCC, this);
    }
    public PhieuNhapHang saoChepVoiMaMoi(String maPNHMoi, String maNVMoi, String maNCCMoi) {
        return new PhieuNhapHang(maPNHMoi, maNVMoi, maNCCMoi, this);
    }

    public void nhap() {
        
    }
    public void xuat() {

    }
    public void ghiFile() {

    }
    public void docFile() {

    }
    public String getMaPNH() {return maPNH;}
    public String getMaNV() {return maNV;}
    public String getMaNCC() {return maNCC;}
    public String getNgayTaoPhieu() {return ngayTaoPhieu;}
    public String getTrangThai() {return trangThai;}
    public String getPhuongThucThanhToan() {return phuongThucThanhToan;}
    public double getTongTien() {return tongTien;}
}
