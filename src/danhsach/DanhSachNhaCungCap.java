package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NhaCungCap;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNhaCungCap implements IDanhSach {
    private NhaCungCap[] DS_NhaCungCap;
    private int soLuong;

    public DanhSachNhaCungCap() {
        soLuong = 0;
        DS_NhaCungCap = new NhaCungCap[soLuong];
    }

    public DanhSachNhaCungCap(DanhSachNhaCungCap DS_NCC) {
        soLuong = DS_NCC.soLuong;
        DS_NhaCungCap = new NhaCungCap[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_NhaCungCap[i] = DS_NCC.DS_NhaCungCap[i].saoChep();
    }

    // sao chep 1 ra danh sach moi
    @Override
    public DanhSachNhaCungCap saoChep() { return new DanhSachNhaCungCap(this); }

    // doc so luong cua danh sach
    @Override
    public int getSoLuong() { return soLuong; }

    // ======================================================
    // ghi du lieu danh sach xuong file
    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSNCC));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) {
            DS_NhaCungCap[i].ghiFile(dos);
        }
        dos.close();
    }

    // doc du lieu danh sach vao chuong trinh
    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSNCC));
        soLuong = dis.readInt();
        if (DS_NhaCungCap.length < soLuong) DS_NhaCungCap = new NhaCungCap[soLuong];
        for (int i = 0; i < soLuong; i++) {
            NhaCungCap ncc = new NhaCungCap("");
            ncc.docFile(dis);
            DS_NhaCungCap[i] = ncc;
        }
        dis.close();
    }

    // ======================================================
    // phuong thuc noi bo, dung de tao chuoi xu ly cho gon, dau vao phai duoc kiem tra truoc

    // tim vi tri nha cung cap
    private int timKiemViTriNhaCungCap(String maNCC) {
        for (int i = 0; i < soLuong; i++)
            if (DS_NhaCungCap[i] != null && DS_NhaCungCap[i].getMa().equals(maNCC)) return i;
        return -1;
    }

    // them, sua, xoa 1 nha cung cap
    private NhaCungCap them(NhaCungCap ncc) {
        if (kiemTraMa(ncc.getMa())) return null;
        if (soLuong == DS_NhaCungCap.length) DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, soLuong + 1);
        DS_NhaCungCap[soLuong++] = ncc;
        return ncc;
    }

    private NhaCungCap sua(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if (viTri == -1) return null;
        DS_NhaCungCap[viTri].nhap();
        return DS_NhaCungCap[viTri];
    }

    private boolean xoa(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++)
            DS_NhaCungCap[i] = DS_NhaCungCap[i + 1];
        DS_NhaCungCap[--soLuong] = null;
        return true;
    }

    // ======================================================
    // cac phuong thuc giao tiep ra ben ngoai

    // kiem tra ma nay da ton tai trong danh sach chua, co roi thi tra ve true, chua thi false
    public boolean kiemTraMa(String maNCC) {
        if (maNCC == null || maNCC.isEmpty()) return false;
        return (timKiemViTriNhaCungCap(maNCC) != -1);
    }

    // tra ra ban sao cua nha cung cap
    public NhaCungCap timKiemMa(String maNCC) {
        if (maNCC == null || maNCC.isEmpty()) return null;
        int viTriNCC = timKiemViTriNhaCungCap(maNCC);
        if (viTriNCC == -1) return null;
        return DS_NhaCungCap[viTriNCC].saoChep();
    }

    // them ban sao cua nha cung cap ben ngoai vo danh sach
    public boolean themVaoDanhSach(NhaCungCap ncc) {
        if (ncc == null || ncc.getMa().isEmpty()) return false;
        return them(ncc.saoChep()) != null;
    }

    public NhaCungCap docPhanTu(int index) { return DS_NhaCungCap[index].saoChep(); }

    public DanhSachNhaCungCap timKiemTheoTen(String tenNhaCungCap) {
        DanhSachNhaCungCap dsTam = new DanhSachNhaCungCap();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhaCungCap[i] != null && DS_NhaCungCap[i].getTen().equalsIgnoreCase(tenNhaCungCap))
                dsTam.them(DS_NhaCungCap[i].saoChep());
        }
        if (dsTam.soLuong == 0) return null;
        return dsTam;
    }

    // ------------------------------------
    // cac phuong thuc giao dien
    @Override
    public void nhapTimKiem() {

    }

    // them, sua, xoa
    @Override
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nha cung cap muon them");
        DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, soLuong + nhapThem);
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNCC;
            while (kiemTraMa(maNCC = NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap")))
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            NhaCungCap nccMoi = new NhaCungCap(maNCC);
            nccMoi.nhap();
            them(nccMoi);
        }
        System.out.println("****Ket thuc nhap danh sach****");
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void nhapSua() {
        do {
            if (sua(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap")) == null)
                System.out.println(">> Nha cung cap nay khong ton tai trong danh sach");
            else System.out.println(">> Sua thanh cong");
            System.out.println("Co tiep tuc nhap sua them nha cung cap khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    @Override
    public void nhapXoa() {
        do {
            if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap")))
                System.out.println(">> Khong tim thay nha cung cap can xoa");
            else System.out.println(">> Xoa thanh cong");
            System.out.println("Co tiep tuc xoa cac nha cung cap khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    // xuat danh sach voi cac dong du lieu duoc fomat bang
    @Override
    public void xuatDanhSach() {
        String[][] bangNhaCungCap = new String[soLuong + 1][];
        bangNhaCungCap[0] = new String[] { "Ma", "Ho", "Ten", "Dia chi", "So dien thoai", "Email" };
        for (int i = 0; i < soLuong; i++) {
            bangNhaCungCap[i + 1] = new String[] {
                DS_NhaCungCap[i].getMa(),
                DS_NhaCungCap[i].getHo(),
                DS_NhaCungCap[i].getTen(),
                DS_NhaCungCap[i].getDiaChi(),
                DS_NhaCungCap[i].getSoDienThoai(),
                DS_NhaCungCap[i].getEmail()
            };
        }
        FormatBang.inBangDuLieu("DANH SACH NHA CUNG CAP", bangNhaCungCap);
        NhapChuan.docChuoi("Enter de quay lai");
    }
}
