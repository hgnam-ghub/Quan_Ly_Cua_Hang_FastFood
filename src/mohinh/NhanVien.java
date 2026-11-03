package mohinh;

public final class NhanVien {
    String ma;
    String ho;
    String ten;
    String chucVu;
    String soDienThoai;
    String gioiTinh;
    double luongCoBan;
    double tongTienDaBan;

    public NhanVien(String ma, String ho, String ten, String chucVu, String soDienThoai, String gioiTinh, double luongCoBan, double tongCongBan) {
        this.ma = ma;
        this.ho = ho;
        this.ten = ten;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
        this.luongCoBan = luongCoBan;
        this.tongTienDaBan = tongCongBan;
    }
    public NhanVien(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co", "nam", 0, 0);
    }
    public NhanVien(String ma, NhanVien nv) {
        this(ma, nv.ho, nv.ten, nv.chucVu, nv.soDienThoai, nv.gioiTinh, nv.luongCoBan, nv.tongTienDaBan);
    }
    public NhanVien(NhanVien nv) {this(nv.ma, nv);}
    public NhanVien saoChep() {
        return new NhanVien(this);
    }
    public NhanVien saoChepVoiMaMoi(String maMoi)
    {
        return new NhanVien(maMoi, this);
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
    public String getHo() {return ho;}
    public String getTen() {return ten;}
    public String getChucVu() {return chucVu;}
    public String getSoDienThoai() {return soDienThoai;}
    public String getGioiTinh() {return gioiTinh;}
    public double getLuongCoBan() {return luongCoBan;}
    public double getTongTienDaBan() {return tongTienDaBan;}

    public boolean setHo(String hoMoi) {return true;}
    public boolean setTen(String tenMoi) {return true;}
    public boolean setChucVu(String chucVuMoi) {return true;}
    public boolean setSoDienThoai(String soDienThoaiMoi) {return true;}
    public boolean setGioiTinh(String gioiTinhMoi) {return true;}
    public boolean setLuongCoBan(double luongCoBanMoi) {return true;}
}
