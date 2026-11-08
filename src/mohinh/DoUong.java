package mohinh;
import quanly.NhapChuan;
public class DoUong extends SanPham {
    private double theTich;
    private boolean coGas;
    public DoUong(String id) {
        super(id);
        theTich = 0;
        coGas = false;
    }
    public DoUong(String ma, String ten, String donVi, String phuongThucThanhToan, double giaBan, int soLuong, double theTich, boolean coGas) {
        super(ma, ten, donVi, phuongThucThanhToan, giaBan, soLuong);
        if(!setTheTich(theTich)) this.theTich = 1;
        if(!setGas(coGas)) this.coGas = false;
    }
    public DoUong(String maMoi, DoUong doUong) {
        super(maMoi ,doUong);
        theTich = doUong.theTich;
        coGas = doUong.coGas;
    }
    public DoUong(DoUong doUong) {
        super(doUong);
        theTich = doUong.theTich;
        coGas = doUong.coGas;
    }

    @Override 
    public DoUong saoChep() {return new DoUong(this.getMa(),this);}

    @Override 
    public DoUong saoChepVoiMaMoi(String newID){return new DoUong(newID, this);}

    @Override 
    public void xuat() {
        super.xuat();
        xuatChuoi("The tich", theTich, "ml");
        xuatChuoi("Co gas", coGas?"co":"khong");
    }
    @Override 
    public void nhap() {
        super.nhap();
        setTheTich(NhapChuan.docSoThucKhongAm("The tich: "));
        setGas(NhapChuan.docYesNo("Co gas", false));
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
