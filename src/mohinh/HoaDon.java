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
    public HoaDon(String maHD, String maNV, String maKH, LocalDate ngayTaoDon, String trangThai, String phuongThucThanhToan, double tongTien) {
        this.maHD=maHD;
        this.maNV=maNV;
        this.maKH=maKH;
        this.ngayTaoDon=ngayTaoDon;
        this.trangThai=trangThai;
        this.phuongThucThanhToan=phuongThucThanhToan;
        this.tongTien=tongTien;
    }
    public HoaDon( HoaDon hd) {this(hd.maHD, hd.maNV, hd.maKH, hd.ngayTaoDon, hd.trangThai, hd.phuongThucThanhToan, hd.tongTien);}
    public HoaDon saoChep() {return new HoaDon(this){};}
    @Override 
    public String[] xuat() {
        return new String[] {maHD, maNV, maKH, ngayTaoDon.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), trangThai, phuongThucThanhToan, String.valueOf(tongTien)};
    }
    @Override 
    public HoaDon nhap() {
        System.out.println("Nhap thong tin cho hoa don co ma: " + maHD);
        setNgayTaoDon(NhapChuan.docNgay("Ngay tao don"));
        setTrangThai(NhapChuan.docChuoiKhongRong("Trang thai"));
        setPhuongThucThanhToan(NhapChuan.docChuoiKhongRong("Phuong thuc thanh toan"));
        setTongTien(NhapChuan.docSoThucKhongAm("Tong tien"));
        return this;
    }
    @Override 
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maHD);
        dos.writeUTF(maNV);
        dos.writeUTF(maKH);
        dos.writeUTF(ngayTaoDon.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dos.writeUTF(trangThai);
        dos.writeUTF(phuongThucThanhToan);
        dos.writeDouble(tongTien);
    }
    @Override 
    public void docFile(DataInputStream dis) throws IOException {
        maHD = dis.readUTF();
        maNV = dis.readUTF();
        maKH = dis.readUTF();
        ngayTaoDon = LocalDate.parse(dis.readUTF(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        trangThai = dis.readUTF();
        phuongThucThanhToan = dis.readUTF();
        tongTien = dis.readDouble();
    }

    public String getMaHD() {return maHD;}
    public String getMaNV() {return maNV;}
    public String getMaKH() {return maKH;}
    public LocalDate getNgayTaoDon() {return ngayTaoDon;}
    public String getTrangThai() {return trangThai;}
    public String getPhuongThucThanhToan() {return phuongThucThanhToan;}
    public double getTongTien() {return tongTien;}

    public HoaDon setMaHD(String maHD) {this.maHD = maHD; return this;}
    public HoaDon setMaNV(String maNV) {this.maNV=maNV; return this;}
    public HoaDon setMaKH(String maKH) {this.maKH=maKH; return this;}
    public HoaDon setNgayTaoDon(LocalDate ngayTaoDon) {this.ngayTaoDon = ngayTaoDon;return this;}
    public HoaDon setTrangThai(String trangThai) {this.trangThai = trangThai; return this;}
    public HoaDon setPhuongThucThanhToan(String phuongThucThanhToan) {this.phuongThucThanhToan = phuongThucThanhToan;return this;}
    public HoaDon setTongTien(double tongTien) {this.tongTien = tongTien;return this;}
}
