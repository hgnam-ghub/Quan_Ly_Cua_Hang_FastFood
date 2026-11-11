package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import tienich.NhapChuan;
import rangbuoc.ITruyXuatMoHinh;
public final class KhachHang implements ITruyXuatMoHinh {
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
    public KhachHang() {
        this("khong co", "khong co", "khong co", "khong co", "khong co");
    }
    public KhachHang(KhachHang khachHang) {
        this(khachHang.ma, khachHang.ho, khachHang.ten, khachHang.soDienThoai, khachHang.ngaySinh);
    }
    public KhachHang saoChep() {
        return new KhachHang(this);
    }
    
    protected final void xuatChuoi(String prompt, Object giaTri) {System.out.println(prompt + ": " + giaTri);}
    protected final void xuatChuoi(String prompt, Object giaTri, String donVi) {System.out.println(prompt + ": " + giaTri + ' ' + donVi);}
    public String[] xuat() {
        String[] thongTin = new String[5];
        thongTin[0] = ma;
        thongTin[1] = ho + " " + ten;
        thongTin[2] = soDienThoai;
        thongTin[3] = ngaySinh;
        thongTin[4] = String.valueOf(tongTienDaMua);
        return thongTin;
    }
    public KhachHang nhap() {
        System.out.println("Nhap thong tin cho khach hang co ma: " + ma);
        setHo(NhapChuan.docChuoiKhongRong("Ho"));
        setTen(NhapChuan.docChuoiKhongRong("Ten"));
        setSoDienThoai(NhapChuan.docChuoiKhongRong("So dien thoai"));
        setNgaySinh(NhapChuan.docChuoiKhongRong("Ngay sinh"));
        return this;
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

    public KhachHang setMa(String maMoi){
        this.ma = maMoi;
        return this;}
    public KhachHang setTen(String tenMoi){
        this.ten = tenMoi;
        return this;}
    public KhachHang setHo(String hoMoi){
        this.ho = hoMoi;
        return this;}
    public KhachHang setSoDienThoai(String newPhoneNumber){
        this.soDienThoai = newPhoneNumber;
        return this;}
    public KhachHang setNgaySinh(String newDate){
        this.ngaySinh = newDate;
        return this;}
    public boolean thanhToan(double soTien)
    {
        if(soTien <= 0) return false;
        tongTienDaMua += soTien;
        return true;
    }
}
