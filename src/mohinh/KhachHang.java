package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import quanly.NhapChuan;
public final class KhachHang 
{
    private String ma;
    private String ho;
    private String ten;
    private String soDienThoai;
    private String ngaySinh;
    private double tongTienDaMua;

    public KhachHang(String ma, String ho, String ten, String soDienThoai, String ngaySinh) {
        this.ma = ma;
        this.ho = ho;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
    }
    public KhachHang(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co");
    }
    public KhachHang(String maMoi, KhachHang khachHang) {
        this(maMoi, khachHang.ho, khachHang.ten, khachHang.soDienThoai, khachHang.ngaySinh);
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
    protected final void xuatChuoi(String prompt, Object giaTri) {System.out.println(prompt + ": " + giaTri);}
    protected final void xuatChuoi(String prompt, Object giaTri, String donVi) {System.out.println(prompt + ": " + giaTri + ' ' + donVi);}
    public void xuat() {
        xuatChuoi("Ma khach hang", ma);
        xuatChuoi("Ho va ten", ho + " " + ten);
        xuatChuoi("So dien thoai", soDienThoai);
        xuatChuoi("Ngay sinh", ngaySinh);
        xuatChuoi("Tong tien da mua", tongTienDaMua, "$");
    }

    public void nhap() {
        System.out.println("Nhap thong tin cho khach hang co ma: " + ma);
        setHo(NhapChuan.docChuoiKhongRong("Ho"));
        setTen(NhapChuan.docChuoiKhongRong("Ten"));
        setSoDienThoai(NhapChuan.docChuoiKhongRong("So dien thoai"));
        setNgaySinh(NhapChuan.docChuoiKhongRong("Ngay sinh"));
    }
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma);
        dos.writeUTF(ho);
        dos.writeUTF(ten);
        dos.writeUTF(soDienThoai);
        dos.writeUTF(ngaySinh);
        dos.writeDouble(tongTienDaMua);
    }

    public void docFile(DataInputStream dis) throws IOException {
        ma = dis.readUTF();
        ho = dis.readUTF();
        ten = dis.readUTF();
        soDienThoai = dis.readUTF();
        ngaySinh = dis.readUTF();
        tongTienDaMua = dis.readDouble();
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
