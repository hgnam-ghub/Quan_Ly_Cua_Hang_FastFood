package mohinh;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import tienich.NhapChuan;
public class DoUong extends SanPham {
    private double theTich;
    private boolean coGas;
    public DoUong() {theTich = 0;coGas = false;}
    public DoUong(String ma, String ten, String donVi, String phuongThucThanhToan, double giaBan, int soLuong, double theTich, boolean coGas) {
        super(ma, ten, donVi, phuongThucThanhToan, giaBan, soLuong);
        if(!setTheTich(theTich)) this.theTich = 1;
        if(!setGas(coGas)) this.coGas = false;
    }
    public DoUong(DoUong doUong) {
        super(doUong);
        theTich = doUong.theTich;
        coGas = doUong.coGas;
    }

    @Override 
    public DoUong saoChep() {return new DoUong(this);}


    @Override 
    public String[] xuat() {
        String[] duLieuGoc = super.xuat();
        int doDaiGoc = duLieuGoc.length;

        String[] truongDuLieu = Arrays.copyOf(duLieuGoc, doDaiGoc + 2);
        truongDuLieu[doDaiGoc] = String.valueOf(theTich);
        truongDuLieu[doDaiGoc+1] = coGas?"Co":"Khong";
        return truongDuLieu;
    }
    @Override 
    public DoUong nhap() {
        super.nhap();
        setTheTich(NhapChuan.docSoThucKhongAm("The tich"));
        setGas(NhapChuan.docYesNo("Co gas", false));
        return this;
    }

    @Override
    public void ghiFile(DataOutputStream dos) throws IOException {
        super.ghiFile(dos);
        dos.writeDouble(theTich);
        dos.writeBoolean(coGas);
    }
    @Override 
    public void docFile(DataInputStream dis) throws IOException {
        super.docFile(dis);
        theTich = dis.readDouble();
        coGas = dis.readBoolean();
    }

    public final double getTheTich(){return theTich;}
    public final boolean getGas(){return coGas;}

    public final boolean setTheTich(double theTichMoi) {
        if(theTichMoi <= 0) return false;
        theTich = theTichMoi;
        return true;
    }
    public final boolean setGas(boolean coGas) {this.coGas = coGas; return true;}
}
