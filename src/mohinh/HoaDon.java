package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import tienich.NhapChuan;
import rangbuoc.ITruyXuatMoHinh;

public final class HoaDon implements ITruyXuatMoHinh {
    private String maHD;
    private String maNV;
    private String maKH;
    private String ngayTaoDon;
    private String trangThai;
    private String phuongThucThanhToan;
    private double tongTien;

    public HoaDon(String maHD, String maNV, String maKH, String ngayTaoDon, String trangThai, String phuongThucThanhToan, double tongTien) {
        if(setMa(maHD, maNV, maKH)==null) {this.maHD = "khong hop le"; this.maNV = "khong hop le"; this.maKH = "khong hop le";}
        if(setNgayTaoDon(ngayTaoDon)==null) this.ngayTaoDon = "01/01/2000";
        if(setTrangThai(trangThai)==null) this.trangThai = "khong co";
        if(setPhuongThucThanhToan(phuongThucThanhToan)==null) this.phuongThucThanhToan = "khong co";
        if(setTongTien(tongTien)==null) this.tongTien = 0;
    }
    public HoaDon() {
        this("khong hop le", "khong hop le", "khong hop le", "01/01/2000", "dang xu ly", "N/A", 0);
    }
    public HoaDon(HoaDon hd) {this(hd.maHD, hd.maNV, hd.maKH, hd.ngayTaoDon, hd.trangThai, hd.phuongThucThanhToan, hd.tongTien);}

    public HoaDon saoChep() {
        return new HoaDon(this);
    }

    protected final void xuatChuoi(String prompt, Object giaTri) {System.out.println(prompt + ": " + giaTri);}
    protected final void xuatChuoi(String prompt, Object giaTri, String donVi) {System.out.println(prompt + ": " + giaTri + ' ' + donVi);}

    public String[] xuat() {
        String[] thongTin = new String[7];
        thongTin[0] = maHD;
        thongTin[1] = maNV;
        thongTin[2] = maKH;
        thongTin[3] = ngayTaoDon;
        thongTin[4] = trangThai;
        thongTin[5] = phuongThucThanhToan;
        thongTin[6] = String.valueOf(tongTien);
        return thongTin;
    }
    public HoaDon nhap() {
        System.out.println("Nhap thong tin cho hoa don co ma: " + maHD);
        setNgayTaoDon(NhapChuan.docChuoiKhongRong("Ngay tao don (dd/MM/yyyy)"));
        setTrangThai(NhapChuan.docChuoiKhongRong("Trang thai"));
        setPhuongThucThanhToan(NhapChuan.docChuoiKhongRong("Phuong thuc thanh toan"));
        setTongTien(NhapChuan.docSoThucKhongAm("Tong tien"));
        return this;
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maHD);
        dos.writeUTF(maNV);
        dos.writeUTF(maKH);
        dos.writeUTF(ngayTaoDon);
        dos.writeUTF(trangThai);
        dos.writeUTF(phuongThucThanhToan);
        dos.writeDouble(tongTien);
    }

    public void docFile(DataInputStream dis) throws IOException {
        maHD = dis.readUTF();
        maNV = dis.readUTF();
        maKH = dis.readUTF();
        ngayTaoDon = dis.readUTF();
        trangThai = dis.readUTF();
        phuongThucThanhToan = dis.readUTF();
        tongTien = dis.readDouble();
    }

    public String getMaHoaDon() {return maHD;}
    public String getMaNhanVien() {return maNV;}
    public String getMaKhachHang() {return maKH;}
    public String getNgayTaoDon() {return ngayTaoDon;}
    public String getTrangThai() {return trangThai;}
    public String getPhuongThucThanhToan() {return phuongThucThanhToan;}
    public double getTongTien() {return tongTien;}

    public HoaDon setMa(String maHDMoi, String maNVMoi, String maKHMoi) {
        if(maHDMoi.isEmpty() || maNVMoi.isEmpty() || maKHMoi.isEmpty()) return null;
        maHD = maHDMoi;
        maNV = maNVMoi;
        maKH = maKHMoi;
        return this;
    }
    public HoaDon setNgayTaoDon(String ngayTaoDonMoi) {
        if(ngayTaoDonMoi.isEmpty()) return null;
        ngayTaoDon = ngayTaoDonMoi;
        return this;
    }
    public HoaDon setTrangThai(String trangThaiMoi) {
        if(trangThaiMoi.isEmpty()) return null;
        trangThai = trangThaiMoi;
        return this;
    }
    public HoaDon setPhuongThucThanhToan(String phuongThucThanhToanMoi) {
        if(phuongThucThanhToanMoi.isEmpty()) return null;
        phuongThucThanhToan = phuongThucThanhToanMoi;
        return this;
    }
    public HoaDon setTongTien(double tongTienMoi) {
        if(tongTienMoi < 0) return null;
        tongTien = tongTienMoi;
        return this;
    }
}
