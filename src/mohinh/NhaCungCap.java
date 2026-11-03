package mohinh;

public final class NhaCungCap {
    String ma;
    String ho;
    String ten;
    String diaChi;
    String soDienThoai;
    String email;

    public NhaCungCap(String ma, String ho, String ten, String diaChi, String soDienThoai, String email) {
        this.ma = ma;
        this.ho = ho;
        this.ten = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }
    public NhaCungCap(String ma) {
        this(ma, "khong co", "khong co", "khong co", "khong co", "khong co");
    }
    public NhaCungCap(String ma, NhaCungCap nhaCungCap) {
        this(ma, nhaCungCap.ho, nhaCungCap.ten, nhaCungCap.diaChi, nhaCungCap.soDienThoai, nhaCungCap.email);
    }
    public NhaCungCap(NhaCungCap nhaCungCap) {
        this(nhaCungCap.ma, nhaCungCap);
    }
    public NhaCungCap saoChep() {
        return new NhaCungCap(this);
    }
    public NhaCungCap saoChepVoiMaMoi(String maMoi) {
        return new NhaCungCap(maMoi, this);
    }


    // viết logic hàm từ đây
    public void nhap() {

    }
    public void xuat() {

    }
    public void ghiFile() {

    }
    public void docFile() {

    }

    public String getMa() {return ma;}
    public String getHo() {return ho;}
    public String getTen() {return ten;}
    public String getDiaChi() {return diaChi;}
    public String getSoDienThoai() {return soDienThoai;}
    public String getEmail() {return email;}

    public boolean setMa() {return true;}
    public boolean setHo() {return true;}
    public boolean setTen() {return true;}
    public boolean setDiaChi() {return true;}
    public boolean setSoDienThoai() {return true;}
    public boolean setEmail() {return true;}
}
