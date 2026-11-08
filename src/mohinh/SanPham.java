package mohinh;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import quanly.NhapChuan;
import rangbuoc.ITruyXuatMoHinh;

public abstract class SanPham implements ITruyXuatMoHinh{
    private String ma;
    private String ten;
    private String donVi;
    private String phuongThucCheBien;
    private double giaBan;
    private int soLuong;
    
    protected SanPham(String ma, String ten, String donVi, String phuongThucCheBien, double giaBan, int soLuong) {
        if(!setMa(ma)) this.ma = "khong hop le";
        if(!setTen(ten)) this.ten = "khong co";
        if(!setDonVi(donVi)) this.donVi = "khong co";
        if(!setPhuongThucCheBien(phuongThucCheBien)) this.phuongThucCheBien = "khong co";
        if(!setGiaBan(giaBan)) this.giaBan = 0;
        if(!setSoLuong(soLuong)) this.soLuong = 1;
    }
    protected SanPham(String ma) {
        this(ma, "", "", "", 0, 1);
    }

    protected SanPham(String ma, SanPham sanPham) {
        this(ma, sanPham.ten, sanPham.donVi, sanPham.phuongThucCheBien, sanPham.giaBan, sanPham.soLuong);
    }
    protected SanPham(SanPham sanPham) {
        this(sanPham.ma, sanPham);
    }
    
    protected final void xuatChuoi(String prompt, Object giaTri) {System.out.println(prompt + ": " + giaTri);}
    protected final void xuatChuoi(String prompt, Object giaTri, String donVi) {System.out.println(prompt + ": " + giaTri + ' ' + donVi);}

    public abstract SanPham saoChep();
    public abstract SanPham saoChepVoiMaMoi(String newID);

    public void xuat() {
        xuatChuoi("Ma", ma);
        xuatChuoi("Ten", ten);
        xuatChuoi("Don vi", donVi);
        xuatChuoi("Phuong thuc thanh toan", phuongThucCheBien);
        xuatChuoi("Gia ban", giaBan, "$");
        xuatChuoi("So luong", soLuong, donVi);
    }
    public void nhap() {
        System.out.println("nhap thong tin cho san pham co ma: " + ma);
        setTen(NhapChuan.docChuoiKhongRong("Ten"));
        setDonVi(NhapChuan.docChuoiKhongRong("Don vi"));
        setPhuongThucCheBien(NhapChuan.docChuoiKhongRong("Phuong thuc che bien"));
        setGiaBan(NhapChuan.docSoNguyen("Gia ban"));
    }

    @Override
    public void ghiFile(DataOutputStream dos) throws IOException{
        dos.writeUTF(ma);
        dos.writeUTF(ten);
        dos.writeUTF(donVi);
        dos.writeUTF(phuongThucCheBien);
        dos.writeDouble(giaBan);
        dos.writeInt(soLuong);
    }
    @Override
    public void docFile(DataInputStream dis) throws IOException {
        ten = dis.readUTF();
        donVi = dis.readUTF();
        phuongThucCheBien = dis.readUTF();
        giaBan = dis.readDouble();
        soLuong = dis.readInt();
    }

    public final String getMa(){return ma;}
    public final String getTen(){return ten;}
    public final String getDonVi(){return donVi;}
    public final String getPhuongThucCheBien(){return phuongThucCheBien;}
    public final double getGia(){return giaBan;}
    public final int getSoLuong(){return soLuong;}

    private final boolean setMa(String maMoi) {
        if(maMoi.isEmpty()) return false;
        ma = maMoi;
        return true;
    }
    public final boolean setTen(String tenMoi) {
        if(tenMoi.isEmpty()) return false;
        ten = tenMoi;
        return true;
    }
    public final boolean setDonVi(String donViMoi) {
        if(donViMoi.isEmpty()) return false;
        donVi = donViMoi;
        return true;
    }
    public final boolean setPhuongThucCheBien(String phuongThucCheBienMoi){
        if(phuongThucCheBienMoi.isEmpty()) return false;
        phuongThucCheBien = phuongThucCheBienMoi;
        return true;
    }
    public final boolean setGiaBan(double giaBanMoi) {
        if(giaBanMoi < 0) return false;
        giaBan = giaBanMoi;
        return true;
    }
    private final boolean setSoLuong(int soLuongMoi) {
        if(soLuongMoi < 0) return false;
        soLuong = soLuongMoi;
        return true;
    }
    public final boolean thayDoiSoLuong(int luongThayDoi) {
        return setSoLuong(soLuong + luongThayDoi);
    }
}
