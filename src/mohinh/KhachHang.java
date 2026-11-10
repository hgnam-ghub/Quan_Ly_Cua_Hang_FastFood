package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import tienich.NhapChuan;
public final class KhachHang 
{
    private String ma;
    private String ho;
    private String ten;
    private String soDienThoai;
    private String ngaySinh;
    private double tongTienDaMua;
    private static int demKhachHang = 0;

    public KhachHang(String ma, String ho, String ten, String soDienThoai, String ngaySinh, double tongTienDaMua) {
        if(!setMa(ma)) this.ma = "KH00";
        if(!setHo(ho)) this.ho = "khong co";
        if(!setTen(ten)) this.ten = "khong co";
        if(!setSoDienThoai(soDienThoai)) this.soDienThoai = "khong co";
        if(!setNgaySinh(ngaySinh)) this.ngaySinh = "khong co";
        this.tongTienDaMua = (tongTienDaMua < 0) ? 0 : tongTienDaMua;
        demKhachHang++;
    }
    public KhachHang(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co", 0);
    }
    public KhachHang(String ma, String ho, String ten, String soDienThoai, String ngaySinh) {
        this(ma, ho, ten, soDienThoai, ngaySinh, 0);
    }
    public KhachHang(String maMoi, KhachHang khachHang) {
        this(maMoi, khachHang.ho, khachHang.ten, khachHang.soDienThoai, khachHang.ngaySinh, khachHang.tongTienDaMua);
    }
    public KhachHang(KhachHang khachHang) {this(khachHang.ma, khachHang);}
    public KhachHang saoChep() {return new KhachHang(this);}
    public KhachHang saoChepVoiMaMoi(String maMoi) {return new KhachHang(maMoi, this);}
    public static int getDemKhachHang() {return demKhachHang;}

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

    private boolean setMa(String maMoi) {
        if(maMoi == null || maMoi.isEmpty()) return false;
        ma = maMoi;
        return true;
    }
    public boolean setHo(String hoMoi) {
        if(hoMoi == null || hoMoi.isEmpty()) return false;
        ho = hoMoi;
        return true;
    }
    public boolean setTen(String tenMoi) {
        if(tenMoi == null || tenMoi.isEmpty()) return false;
        ten = tenMoi;
        return true;
    }
    public boolean setSoDienThoai(String newPhoneNumber) {
        if(newPhoneNumber == null || newPhoneNumber.isEmpty()) return false;
        soDienThoai = newPhoneNumber;
        return true;
    }
    public boolean setNgaySinh(String newDate) {
        if(newDate == null || newDate.isEmpty()) return false;
        ngaySinh = newDate;
        return true;
    }
    public boolean thanhToan(double soTien) {
        if(soTien <= 0) return false;
        tongTienDaMua += soTien;
        return true;
    }
}
