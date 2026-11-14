package mohinh;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import tienich.NhapChuan;
public class DoAn extends SanPham {
    private int mucCay;
    public DoAn() {mucCay = 0;}

    public DoAn(String ma, String ten, String donVi, String phuongThucThanhToan, double giaBan, int soLuong, int mucCay) {
        super(ma, ten, donVi, phuongThucThanhToan, giaBan, soLuong);
        if(!setMucCay(mucCay)) this.mucCay = 0;
    }
    public DoAn(DoAn doAn) {
        super(doAn);
        mucCay = doAn.mucCay;
    }
    @Override 
    public DoAn saoChep() {return new DoAn(this);}

    @Override 
    public String[] xuat() {
        String[] duLieuGoc = super.xuat();
        int doDaiGoc = duLieuGoc.length;

        String[] truongDuLieu = Arrays.copyOf(duLieuGoc, doDaiGoc + 1);
        truongDuLieu[doDaiGoc] = String.valueOf(mucCay);
        return truongDuLieu;
    }
    @Override 
    public DoAn nhap() {
        super.nhap();
        setMucCay(NhapChuan.docSoNguyen("Muc do cay"));
        return this;
    }
    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        super.ghiFile(dos);
        dos.writeInt(mucCay);
    }
    @Override 
    public void docFile(DataInputStream dis) throws IOException {
        super.docFile(dis);
        mucCay = dis.readInt();
    }
    public int getMucCay(){return mucCay;}
    public boolean setMucCay(int mucCayMoi) {
        if(mucCayMoi < 0 || mucCayMoi > 7) return false;
        mucCay = mucCayMoi;
        return true;
    }
}
