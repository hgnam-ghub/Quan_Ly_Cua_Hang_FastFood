package mohinh;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import quanly.NhapChuan;
public class DoAn extends SanPham {
    private int mucCay;
    public DoAn(String ma, String ten, String donVi, String phuongThucThanhToan, double giaBan, int soLuong, int mucCay) {
        super(ma, ten, donVi, phuongThucThanhToan, giaBan, soLuong);
        if(!setMucCay(mucCay)) this.mucCay = 0;
    }
    public DoAn(String ma) {
        super(ma);
        mucCay = 0;
    }

    public DoAn(String maMoi, DoAn doAn) {
        super(maMoi, doAn);
        mucCay = doAn.mucCay;
    }

    public DoAn(DoAn doAn) {
        super(doAn);
        mucCay = doAn.mucCay;
    }
    @Override 
    public DoAn saoChep() {return new DoAn(this.getMa(),this);}

    @Override 
    public DoAn saoChepVoiMaMoi(String maMoi){return new DoAn(maMoi, this);}

    @Override 
    public void xuat() {
        super.xuat();
        xuatChuoi("Muc do cay", mucCay);
    }
    @Override 
    public void nhap() {
        super.nhap();
        setMucCay(NhapChuan.docSoNguyen("Muc do cay"));
    }
    @Override
    public void ghiFile(DataOutputStream dos) throws Exception {
        super.ghiFile(dos);
        dos.writeInt(mucCay);
    }
    @Override 
    public void docFile(DataInputStream dis) throws Exception {
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
