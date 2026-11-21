package mohinh;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;
public class KhachHang implements ITruyXuatMoHinh
{
    private String ma;
    private String ho;
    private String ten;
    private String soDienThoai;
    private LocalDate ngaySinh;
    private double tongTienDaMua;

    public KhachHang() {this("", "", "", "khong co", "01/01/2000",0);}
    protected KhachHang(String ma, String ho, String ten, String soDienThoai, LocalDate ngaySinh) {
        if(setMa(ma)==null) {this.ma = "khong hop le";}
        if(setHo(ho)==null) this.ho = "khong hop le";
        if(setTen(ten)==null) this.ten = "khong hop le";
        if(setSoDienThoai(soDienThoai)==null) this.soDienThoai = "khong hop le";
        if(setNgaySinh(ngaySinh)==null) this.ngaySinh = LocalDate.of(2000, 1, 1);
        if(thanhToan(tongTienDaMua)==null) this.tongTienDaMua = 0;
    }
    protected KhachHang(String ma, String ho, String ten, String soDienThoai, String ngaySinh, double tongTienDaMua) {
        this(ma, ho, ten, soDienThoai, LocalDate.parse(ngaySinh, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.tongTienDaMua = tongTienDaMua;
        
    }
    protected KhachHang(KhachHang khachHang) {
        this(khachHang.ma, khachHang.ho, khachHang.ten, khachHang.soDienThoai, khachHang.ngaySinh);
    }
    public KhachHang saoChep() {
        return new KhachHang(this);
    }
    public String[] xuat() {
        String ngaySinhStr = ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return new String[] {ma, ho, ten, soDienThoai, ngaySinhStr, String.valueOf(tongTienDaMua)};
    }
    public KhachHang nhap() {
        System.out.println("Nhap thong tin cho khach hang co ma: " + ma);
        setHo(NhapChuan.docChuoiKhongRong("Ho"));
        setTen(NhapChuan.docChuoiKhongRong("Ten"));
        setSoDienThoai(NhapChuan.docChuoiKhongRong("So dien thoai"));
        setNgaySinh(NhapChuan.docNgay("Ngay sinh"));
        return this;
    }
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(ma);
        dos.writeUTF(ho);
        dos.writeUTF(ten);
        dos.writeUTF(soDienThoai);
        dos.writeUTF(ngaySinh.toString());
        dos.writeDouble(tongTienDaMua);
    }

    public void docFile(DataInputStream dis) throws IOException {
        ma = dis.readUTF();
        ho = dis.readUTF();
        ten = dis.readUTF();
        soDienThoai = dis.readUTF();
        ngaySinh = LocalDate.parse(dis.readUTF());
        tongTienDaMua = dis.readDouble();
    }
    public String getMa(){return ma;}
    public String getTen(){return ten;}
    public String getHo(){return ho;}
    public String getSoDienThoai(){return soDienThoai;}
    public LocalDate getNgaySinh(){return ngaySinh;}
    public double getTongSoTienMua(){return tongTienDaMua;}

    public KhachHang setMa(String maMoi) {
        if(maMoi.isEmpty()) return null;
        ma = maMoi;
        return this;
    }
    public KhachHang setHo(String hoMoi) {
        if(hoMoi.isEmpty()) return null;
        ho = hoMoi;
        return this;
    }
    public KhachHang setTen(String tenMoi) {
        if(tenMoi.isEmpty()) return null;
        ten = tenMoi;
        return this;
    }
    public KhachHang setSoDienThoai(String soDienThoaiMoi) {
        if(soDienThoaiMoi.isEmpty()) return null;
        soDienThoai = soDienThoaiMoi;
        return this;
    }
    public KhachHang setNgaySinh(LocalDate ngaySinhMoi) {
        if(ngaySinhMoi == null) return null;
        ngaySinh = ngaySinhMoi;
        return this;
    }

    public KhachHang thanhToan(double soTien)
    {
        if(soTien <= 0) return null;
        tongTienDaMua += soTien;
        return this;
    }
}
