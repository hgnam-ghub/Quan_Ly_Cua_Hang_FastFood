package mohinh;
import quanly.NhapChuan;
public final class KhachHang 
{
    private String ma;
    private String ten;
    private String ho;
    private String soDienThoai;
    private String ngaySinh;
    private double tongTienDaMua;

    public KhachHang(String ma, String ten, String ho, String soDienThoai, String ngaySinh) {
        this.ma = ma;
        this.ten = ten;
        this.ho = ho;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
    }
    public KhachHang(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co");
    }
    public KhachHang(String maMoi, KhachHang khachHang) {
        this(maMoi, khachHang.ten, khachHang.ho, khachHang.soDienThoai, khachHang.ngaySinh);
        tongTienDaMua = khachHang.tongTienDaMua;
    }
    public KhachHang(KhachHang khachHang) {
        this(khachHang.ma, khachHang);
    }
    public KhachHang saoChep() {
        return new KhachHang(this);
    }
    public KhachHang saoChepVoiMaMoi(String maMoi) {
        return new KhachHang(maMoi, this);
    }

    public void xuat()
    {
        System.out.printf("id: %s%n", ma);
        System.out.printf("name: %s%n", ho + ten);
        System.out.printf("phone number: %s%n", soDienThoai);
        System.out.printf("date of birth: %s%n", ngaySinh);
        System.out.printf("totol spending: %f%n", tongTienDaMua);
    }
    public void nhap()
    {
        ten = NhapChuan.docChuoiKhongRong("ten");
        ho = NhapChuan.docChuoiKhongRong("ho");
        soDienThoai = NhapChuan.docChuoiKhongRong("So dien thoai");
        ngaySinh = NhapChuan.docChuoiKhongRong("Ngay sinh nhat");
    }
    public String getMa(){return ma;}
    public String getTen(){return ten;}
    public String getHo(){return ho;}
    public String getSoDienThoai(){return soDienThoai;}
    public String getNgaySinh(){return ngaySinh;}
    public double getTongSoTienMua(){return tongTienDaMua;}

    public boolean setTen(String tenMoi){return true;}
    public boolean setHo(String hoMoi){return true;}
    public boolean setSoDienThoai(String newPhoneNumber){return true;}
    public boolean setNgaySinh(String newDate){return true;}

    public boolean thanhToan(double soTien)
    {
        if(soTien <= 0) return false;
        tongTienDaMua += soTien;
        return true;
    }
}
