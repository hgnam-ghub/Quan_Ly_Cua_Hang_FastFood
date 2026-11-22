package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NguyenLieu;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNguyenLieu implements IDanhSach {
    private NguyenLieu[] DS_NguyenLieu;
    private int soLuong;

    public DanhSachNguyenLieu() {
        soLuong = 0;
        DS_NguyenLieu = new NguyenLieu[soLuong];
    }

    public DanhSachNguyenLieu(DanhSachNguyenLieu DS_NL) {
        soLuong = DS_NL.soLuong;
        DS_NguyenLieu = new NguyenLieu[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_NguyenLieu[i] = DS_NL.DS_NguyenLieu[i].saoChep();
    }

    // sao chep 1 ra danh sach moi
    @Override
    public DanhSachNguyenLieu saoChep() { return new DanhSachNguyenLieu(this); }

    // doc so luong cua danh sach
    @Override
    public int getSoLuong() { return soLuong; }

    // ======================================================
    // ghi du lieu danh sach xuong file
    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSNL));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) {
            DS_NguyenLieu[i].ghiFile(dos);
        }
        dos.close();
    }

    // doc du lieu danh sach vao chuong trinh
    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSNL));
        soLuong = dis.readInt();
        if (DS_NguyenLieu.length < soLuong) DS_NguyenLieu = new NguyenLieu[soLuong];
        for (int i = 0; i < soLuong; i++) {
            NguyenLieu nl = new NguyenLieu("");
            nl.docFile(dis);
            DS_NguyenLieu[i] = nl;
        }
        dis.close();
    }

    // ======================================================
    // phuong thuc noi bo, dung de tao chuoi xu ly cho gon, dau vao phai duoc kiem tra truoc

    // tim vi tri nguyen lieu
    private int timKiemViTriNguyenLieu(String maNL) {
        for (int i = 0; i < soLuong; i++)
            if (DS_NguyenLieu[i] != null && DS_NguyenLieu[i].getMa().equals(maNL)) return i;
        return -1;
    }

    // them, sua, xoa 1 nguyen lieu
    private NguyenLieu them(NguyenLieu nl) {
        if (kiemTraMa(nl.getMa())) return null;
        if (soLuong == DS_NguyenLieu.length) DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, soLuong + 1);
        DS_NguyenLieu[soLuong++] = nl;
        return nl;
    }

    private NguyenLieu sua(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if (viTri == -1) return null;
        DS_NguyenLieu[viTri].nhap();
        return DS_NguyenLieu[viTri];
    }

    private boolean xoa(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++)
            DS_NguyenLieu[i] = DS_NguyenLieu[i + 1];
        DS_NguyenLieu[--soLuong] = null;
        return true;
    }

    // ======================================================
    // cac phuong thuc giao tiep ra ben ngoai

    // kiem tra ma nay da ton tai trong danh sach chua, co roi thi tra ve true, chua thi false
    public boolean kiemTraMa(String maNL) {
        if (maNL == null || maNL.isEmpty()) return false;
        return (timKiemViTriNguyenLieu(maNL) != -1);
    }

    // tra ra ban sao cua nguyen lieu
    public NguyenLieu timKiemMa(String maNL) {
        if (maNL == null || maNL.isEmpty()) return null;
        int viTriNL = timKiemViTriNguyenLieu(maNL);
        if (viTriNL == -1) return null;
        return DS_NguyenLieu[viTriNL].saoChep();
    }

    // them ban sao cua nguyen lieu ben ngoai vo danh sach
    public boolean themVaoDanhSach(NguyenLieu nl) {
        if (nl == null || nl.getMa().isEmpty()) return false;
        return them(nl.saoChep()) != null;
    }

    public NguyenLieu docPhanTu(int index) { return DS_NguyenLieu[index].saoChep(); }

    public DanhSachNguyenLieu timKiemTheoTen(String tenNguyenLieu) {
        DanhSachNguyenLieu dsTam = new DanhSachNguyenLieu();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NguyenLieu[i] != null && DS_NguyenLieu[i].getTen().equalsIgnoreCase(tenNguyenLieu))
                dsTam.them(DS_NguyenLieu[i].saoChep());
        }
        if (dsTam.soLuong == 0) return null;
        return dsTam;
    }
    public boolean capNhatSoLuong(String maNL, int soLuongMoi){
        if (maNL == null || maNL.trim().isEmpty()) return false;
        int viTri = timKiemViTriNguyenLieu(maNL);
        if (viTri == -1 || DS_NguyenLieu[viTri] == null) return false;
        return DS_NguyenLieu[viTri].setSoLuong(soLuongMoi);
    }
    // ------------------------------------
    // cac phuong thuc giao dien
    @Override
    public void nhapTimKiem() {
        System.out.println("**** Tim kiem nguyen lieu ****");
        String maNL = NhapChuan.docChuoi("Ma nguyen lieu can tim");
        int viTriPhieu = timKiemViTriNguyenLieu(maNL); 
        if(viTriPhieu == -1)
            System.out.println("Khong tim thay nguyen lieu nay");
        else {
            DanhSachNguyenLieu dsTam = new DanhSachNguyenLieu();
            dsTam.them((DS_NguyenLieu[viTriPhieu]));
            dsTam.xuatDanhSach();
        }
    }

    // them, sua, xoa
    @Override
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nguyen lieu muon them");
        DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, soLuong + nhapThem);
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNL;
            while (kiemTraMa(maNL = NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu")))
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            NguyenLieu nlMoi = new NguyenLieu(maNL);
            nlMoi.nhap();
            them(nlMoi);
        }
        System.out.println("****Ket thuc nhap danh sach****");
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void nhapSua() {
        do {
            if (sua(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu")) == null)
                System.out.println(">> Nguyen lieu nay khong ton tai trong danh sach");
            else System.out.println(">> Sua thanh cong");
            System.out.println("Co tiep tuc nhap sua them nguyen lieu khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    @Override
    public void nhapXoa() {
        do {
            if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu")))
                System.out.println(">> Khong tim thay nguyen lieu can xoa");
            else System.out.println(">> Xoa thanh cong");
            System.out.println("Co tiep tuc xoa cac nguyen lieu khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    // xuat danh sach voi cac dong du lieu duoc fomat bang
    @Override
    public void xuatDanhSach() {
        String[][] bangNguyenLieu = new String[soLuong + 1][];
        bangNguyenLieu[0] = new String[] { "Ma", "Ten", "Don vi", "Don gia", "So luong" };
        for (int i = 0; i < soLuong; i++) {
            bangNguyenLieu[i + 1] = new String[] {
                DS_NguyenLieu[i].getMa(),
                DS_NguyenLieu[i].getTen(),
                DS_NguyenLieu[i].getDonVi(),
                String.format("%.1f",DS_NguyenLieu[i].getDonGia()),
                String.valueOf(DS_NguyenLieu[i].getSoLuong())
            };
        }
        FormatBang.inBangDuLieu("DANH SACH NGUYEN LIEU", bangNguyenLieu);
        System.out.println("So luong: " + soLuong);
        NhapChuan.docChuoi("Enter de quay lai");
    }
}
