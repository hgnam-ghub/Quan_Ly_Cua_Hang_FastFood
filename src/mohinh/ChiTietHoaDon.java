package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import quanly.NhapChuan;

public final class ChiTietHoaDon {
    private String maHD;
    private String maSP;
    private double donGiaBan;
    private double thanhTien;
    private int soLuong;

    public ChiTietHoaDon (String maHD, String maSP, double donGiaBan, double thanhTien, int soLuong) {
        if(!setMa(maHD, maSP)) {this.maHD = "khong hop le"; this.maSP = "khong hop le";}
        if(!setDonGiaBan(donGiaBan)) this.donGiaBan = 0;
        if(!setThanhTien(thanhTien)) this.thanhTien = 0;
        if(!setSoLuong(soLuong)) this.soLuong = 0;
    }
    public ChiTietHoaDon (String maHD, String maSP) {
        this(maHD, maSP, 0, 0, 1);
    }
    public ChiTietHoaDon(String maHD, String maSP, ChiTietHoaDon cthd) {
        this(maHD, maSP, cthd.donGiaBan, cthd.thanhTien, cthd.soLuong);
    }
    public ChiTietHoaDon(ChiTietHoaDon cthd) {
        this(cthd.maHD, cthd.maSP, cthd);
    }
    public ChiTietHoaDon saoChep() {
        return new ChiTietHoaDon(this);
    }
    public ChiTietHoaDon saoChepVoiMaMoi(String maHDMoi, String maSPMoi) {
        return new ChiTietHoaDon(maHDMoi, maSPMoi, this);
    }

    protected final void xuatChuoi(String prompt, Object giaTri) {System.out.println(prompt + ": " + giaTri);}
    protected final void xuatChuoi(String prompt, Object giaTri, String donVi) {System.out.println(prompt + ": " + giaTri + ' ' + donVi);}

    public void xuat() {
        xuatChuoi("Ma hoa don", maHD);
        xuatChuoi("Ma san pham", maSP);
        xuatChuoi("Don gia ban", donGiaBan, "$");
        xuatChuoi("So luong", soLuong);
        xuatChuoi("Thanh tien", thanhTien, "$");
    }

    public void nhap() {
        System.out.println("Nhap thong tin chi tiet cho hoa don: " + maHD + " va san pham: " + maSP);
        setDonGiaBan(NhapChuan.docSoThucKhongAm("Don gia ban"));
        setSoLuong(NhapChuan.docSoNguyenKhongAm("So luong"));
        thanhTien = donGiaBan * soLuong; // Tự động tính thành tiền
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maHD);
        dos.writeUTF(maSP);
        dos.writeDouble(donGiaBan);
        dos.writeDouble(thanhTien);
        dos.writeInt(soLuong);
    }

    public void docFile(DataInputStream dis) throws IOException {
        maHD = dis.readUTF();
        maSP = dis.readUTF();
        donGiaBan = dis.readDouble();
        thanhTien = dis.readDouble();
        soLuong = dis.readInt();
    }

    public String getMaHoaDon() {return maHD;}
    public String getMaSanPham() {return maSP;}
    public double getDonGiaBan() {return donGiaBan;}
    public double getThanhTien() {return thanhTien;}
    public int getSoLuong() {return soLuong;}

    private boolean setMa(String maHDMoi, String maSPMoi) {
        if(maHDMoi.isEmpty() || maSPMoi.isEmpty()) return false;
        maHD = maHDMoi;
        maSP = maSPMoi;
        return true;
    }
    public boolean setDonGiaBan(double donGiaBanMoi) {
        if(donGiaBanMoi < 0) return false;
        donGiaBan = donGiaBanMoi;
        thanhTien = donGiaBan * soLuong;
        return true;
    }
    public boolean setThanhTien(double thanhTienMoi) {
        if(thanhTienMoi < 0) return false;
        thanhTien = thanhTienMoi;
        return true;
    }
    public boolean setSoLuong(int soLuongMoi) {
        if(soLuongMoi <= 0) return false;
        soLuong = soLuongMoi;
        thanhTien = donGiaBan * soLuong;
        return true;
    }
}
