package mohinh;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public class HoaDon implements ITruyXuatMoHinh {
    private String maHD;
    private String maNV;
    private String maKH;
    private LocalDate ngayTaoDon;
    private String trangThai;
    private String phuongThucThanhToan;
    private double tongTien;

    public HoaDon() {this("", "", "", LocalDate.of(2000, 1, 1), "khong co", "khong co", 0);}
    protected HoaDon(String maHD, String maNV, String maKH, LocalDate ngayTaoDon, String trangThai, String phuongThucThanhToan, double tongTien) {
        if(setMa(maHD, maNV, maKH)==null) {this.maHD = "khong hop le"; this.maNV = "khong hop le"; this.maKH = "khong hop le";}
        if(setNgayTaoDon(ngayTaoDon)==null) this.ngayTaoDon = LocalDate.of(2000, 1, 1);
        if(setTrangThai(trangThai)==null) this.trangThai = "khong co";
        if(setPhuongThucThanhToan(phuongThucThanhToan)==null) this.phuongThucThanhToan = "khong co";
        if(setTongTien(tongTien)==null) this.tongTien = 0;
    }
    protected HoaDon( HoaDon hd) {
        this(hd.maHD, hd.maNV, hd.maKH, hd.ngayTaoDon, hd.trangThai, hd.phuongThucThanhToan, hd.tongTien);
    }
    public HoaDon saoChep() {return new HoaDon(this){};}

    public String[] xuat() {
        String NgayStr = (ngayTaoDon != null) ? ngayTaoDon.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "01/01/2000";
        return new String[] {maHD, maNV, maKH, NgayStr, trangThai, phuongThucThanhToan, String.valueOf(tongTien)};
    }

    public HoaDon nhap() {
        System.out.println("Nhap thong tin cho hoa don co ma: " + maHD);
        setNgayTaoDon(NhapChuan.docNgay("Ngay tao don (dd/MM/yyyy)"));
        setTrangThai(NhapChuan.docChuoiKhongRong("Trang thai"));
        setPhuongThucThanhToan(NhapChuan.docChuoiKhongRong("Phuong thuc thanh toan"));
        setTongTien(NhapChuan.docSoThucKhongAm("Tong tien"));
        return this;
    }

    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maHD);
        dos.writeUTF(maNV);
        dos.writeUTF(maKH);
        dos.writeUTF(ngayTaoDon != null ? ngayTaoDon.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "01/01/2000");
        dos.writeUTF(trangThai);
        dos.writeUTF(phuongThucThanhToan);
        dos.writeDouble(tongTien);
    }

    public void docFile(DataInputStream dis) throws IOException {
        maHD = dis.readUTF();
        maNV = dis.readUTF();
        maKH = dis.readUTF();
        ngayTaoDon = LocalDate.parse(dis.readUTF(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        trangThai = dis.readUTF();
        phuongThucThanhToan = dis.readUTF();
        tongTien = dis.readDouble();
    }

    public String getMaHoaDon() {return maHD;}
    public String getMaNhanVien() {return maNV;}
    public String getMaKhachHang() {return maKH;}
    public LocalDate getNgayTaoDon() {return ngayTaoDon;}
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
    public HoaDon setNgayTaoDon(LocalDate ngayTaoDonMoi) {
        if(ngayTaoDonMoi == null) return null;
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
