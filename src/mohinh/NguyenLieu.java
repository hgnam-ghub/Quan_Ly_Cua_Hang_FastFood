package mohinh;

public final class NguyenLieu {
    private String ma;
    private String ten;
    private String donVi;
    private double donGia;
    private int soLuong;

    public NguyenLieu(String ma, String ten, String donVi, double donGia, int soLuong) {
        this.ma = ma;
        this.ten = ten;
        this.donVi = donVi;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }
    public NguyenLieu(String ma) {
        this(ma, "Khong co", "N/A", 0, 0);
    }
    public NguyenLieu(String ma, NguyenLieu nguyenLieu) {
        this(ma, nguyenLieu.ten, nguyenLieu.donVi, nguyenLieu.donGia, nguyenLieu.soLuong);
    }
    public NguyenLieu(NguyenLieu nguyenLieu) {
        this(nguyenLieu.ma, nguyenLieu);
    }
    public NguyenLieu saoChep() {
        return new NguyenLieu(this);
    }
    public NguyenLieu saoChepVoiMaMoi(String maMoi) {
        return new NguyenLieu(maMoi, this);
    }

    public void nhap() {

    }
    public void xuat() {

    }
    public void ghiFile() {

    }
    public void docFile() {

    }

    public String getMa() {return ma;}
    public String getTen() {return ten;}
    public String getDonVi() {return donVi;}
    public double getDonGia() {return donGia;}
    public int getSoLuong() {return soLuong;}

    public boolean setTen() {return true;}
    public boolean setDonVi() {return true;}
    public boolean setDonGia() {return true;}
    public boolean setSoLuong(int soLuongMoi) {
        if(soLuongMoi < 0) return false;
        soLuong = soLuongMoi;
        return true;
    }
    public boolean them(int luongThem) {
        return setSoLuong(soLuong + luongThem);
    }
    public boolean giam(int luongGiam) {
        return setSoLuong(soLuong - luongGiam);
    }
}
