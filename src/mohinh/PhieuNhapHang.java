package mohinh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import rangbuoc.ITruyXuatMoHinh;
import tienich.NhapChuan;

public final class PhieuNhapHang implements ITruyXuatMoHinh{
    private String maPNH;
    private String maNV;
    private String maNCC;
    private LocalDate ngayTaoPhieu;
    private String trangThai;
    private String phuongThucThanhToan;
    private double tongTien;
    
    public PhieuNhapHang() {
        this("maPNH", "maNV", "maNCC", LocalDate.of(2000, 1, 1), "N/A", "N/A", 0);
    }
    public PhieuNhapHang(String maPNH, String maNV, String maNCC, LocalDate ngayTaoPhieu, String trangThai, String phuongThucThanhToan, double tongTien) {
        this.maPNH = maPNH;
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.ngayTaoPhieu = ngayTaoPhieu;
        this.trangThai = trangThai;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tongTien = tongTien;
    }
    public PhieuNhapHang(PhieuNhapHang pnh) {this(pnh.maPNH, pnh.maNV, pnh.maNCC, pnh.ngayTaoPhieu, pnh.trangThai, pnh.phuongThucThanhToan, pnh.tongTien);}
    public PhieuNhapHang saoChep() {return new PhieuNhapHang(this);}

    @Override
    public PhieuNhapHang nhap() {
        ngayTaoPhieu = NhapChuan.docNgay("Ngay tao phieu");
        trangThai = NhapChuan.docChuoi("Trang Thai");
        phuongThucThanhToan = NhapChuan.docChuoi("Phuong Thuc Thanh Toan");
        return this;
    }
    @Override 
    public String[] xuat() {
        return new String[] {maPNH, maNV, maNCC, ngayTaoPhieu.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), trangThai, phuongThucThanhToan,String.format("%.1f", tongTien)};
    }
    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        dos.writeUTF(maPNH);
        dos.writeUTF(maNV);
        dos.writeUTF(maNCC);
        dos.writeUTF(ngayTaoPhieu.toString());
        dos.writeUTF(trangThai);
        dos.writeUTF(phuongThucThanhToan);
        dos.writeDouble(tongTien);
    }
    @Override
    public void docFile(DataInputStream dis) throws IOException {
        maPNH = dis.readUTF();
        maNV = dis.readUTF();
        maNCC = dis.readUTF();
        ngayTaoPhieu = LocalDate.parse(dis.readUTF());
        trangThai = dis.readUTF();
        phuongThucThanhToan = dis.readUTF();
        tongTien = dis.readDouble();
    }

    public PhieuNhapHang setMaPNH(String maPNH) {this.maPNH = maPNH; return this;}
    public PhieuNhapHang setMaNV(String maNV) {this.maNV = maNV; return this;}
    public PhieuNhapHang setMaNCC(String maNCC) {this.maNCC = maNCC; return this;}
    public PhieuNhapHang setNgayTaoPhieu(LocalDate ngayTao) {this.ngayTaoPhieu = ngayTao; return this;}
    public PhieuNhapHang setTrangThai(String trangThai) {this.trangThai = trangThai; return this;}
    public PhieuNhapHang setPhuongThucThanhToan(String PTTT) {this.phuongThucThanhToan = PTTT; return this;}
    public PhieuNhapHang setTongTien(double tongTien) {this.tongTien = tongTien; return this;}
    public String getMaPNH() {return maPNH;}
    public String getMaNV() {return maNV;}
    public String getMaNCC() {return maNCC;}
    public LocalDate getNgayTaoPhieu() {return ngayTaoPhieu;}
    public String getTrangThai() {return trangThai;}
    public String getPhuongThucThanhToan() {return phuongThucThanhToan;}
    public double getTongTien() {return tongTien;}
}
