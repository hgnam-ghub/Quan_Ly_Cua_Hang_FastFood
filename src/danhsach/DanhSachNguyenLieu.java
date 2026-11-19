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
import tienich.NhapChuan;

public class DanhSachNguyenLieu implements IDanhSach {
    private NguyenLieu[] DS_NguyenLieu;
    private int soLuong;
    private static final String FILE_DANH_SACH = "data/DS_NguyenLieu.dat";

    public DanhSachNguyenLieu() {
        soLuong = 0;
        DS_NguyenLieu = new NguyenLieu[soLuong];
    }

    public DanhSachNguyenLieu(DanhSachNguyenLieu DS_NL) {
        soLuong = DS_NL.soLuong;
        DS_NguyenLieu = new NguyenLieu[soLuong];
        for (int i = 0; i < soLuong; i++) {
            DS_NguyenLieu[i] = DS_NL.DS_NguyenLieu[i].saoChep();
        }
    }

    public DanhSachNguyenLieu saoChep() {
        return new DanhSachNguyenLieu(this);
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void xuatDanhSach() {
        String[][] bangNguyenLieu = new String[soLuong + 1][];
        bangNguyenLieu[0] = new String[]{"Ma", "Ten", "Don vi", "Don gia", "So luong"};
        for (int i = 0; i < soLuong; i++) {
            bangNguyenLieu[i + 1] = Arrays.copyOf(DS_NguyenLieu[i].xuat(), bangNguyenLieu[0].length);
        }
        FormatBang.inBangDuLieu("DANH SACH NGUYEN LIEU", bangNguyenLieu);
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_DANH_SACH));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) {
            DS_NguyenLieu[i].ghiFile(dos);
        }
        dos.close();
    }

    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(FILE_DANH_SACH));
        soLuong = dis.readInt();
        if (DS_NguyenLieu.length < soLuong) {
            DS_NguyenLieu = new NguyenLieu[soLuong];
        }
        for (int i = 0; i < soLuong; i++) {
            NguyenLieu nl = new NguyenLieu();
            nl.docFile(dis);
            DS_NguyenLieu[i] = nl;
        }
        dis.close();
    }

    private int timKiemViTriNguyenLieu(String maNL) {
        for (int i = 0; i < soLuong; i++) {
            if (DS_NguyenLieu[i] != null && DS_NguyenLieu[i].getMa().equalsIgnoreCase(maNL)) {
                return i;
            }
        }
        return -1;
    }

    private NguyenLieu them(NguyenLieu nl) {
        if (nl == null || kiemTraMa(nl.getMa())) {
            return null;
        }
        if (soLuong == DS_NguyenLieu.length) {
            DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, soLuong + 1);
        }
        DS_NguyenLieu[soLuong++] = nl;
        return nl;
    }

    private NguyenLieu sua(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if (viTri == -1) {
            return null;
        }
        return DS_NguyenLieu[viTri].nhap();
    }

    private boolean xoa(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if (viTri == -1) {
            return false;
        }
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NguyenLieu[i] = DS_NguyenLieu[i + 1];
        }
        DS_NguyenLieu[--soLuong] = null;
        return true;
    }

    public boolean kiemTraMa(String maNL) {
        return timKiemViTriNguyenLieu(maNL) != -1;
    }

    public NguyenLieu timKiemTheoMa(String maNL) {
        int viTri = timKiemViTriNguyenLieu(maNL);
        if (viTri == -1) {
            return null;
        }
        return DS_NguyenLieu[viTri].saoChep();
    }

    public boolean themVaoDanhSach(NguyenLieu nl) {
        if (nl == null) {
            return false;
        }
        return them(nl.saoChep()) != null;
    }

    public void timKiemMa() {
    }

    public DanhSachNguyenLieu timKiemTheoTen(String tenNguyenLieu) {
        DanhSachNguyenLieu dsTam = new DanhSachNguyenLieu();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NguyenLieu[i] != null && DS_NguyenLieu[i].getTen().equalsIgnoreCase(tenNguyenLieu)) {
                dsTam.them(DS_NguyenLieu[i].saoChep());
            }
        }
        if (dsTam.soLuong == 0) {
            return null;
        }
        return dsTam;
    }

    public void nhapThem() {
        System.out.println("****Bat dau nhap danh sach nguyen lieu****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nguyen lieu muon them");
        DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, soLuong + nhapThem);
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            NguyenLieu nlMoi = new NguyenLieu();
            while (them(nlMoi.setMa(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu"))) == null) {
                System.out.println(">> Ma nay da ton tai, vui long nhap ma khac !!!");
            }
            nlMoi.nhap();
        }
        System.out.println("****Ket thuc nhap danh sach nguyen lieu****");
        NhapChuan.docChuoi("Enter de quay lai");
    }

    public void nhapSua() {
        do {
            if (sua(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu")) == null) {
                System.out.println(">> Nguyen lieu nay khong ton tai trong danh sach");
            } else {
                System.out.println(">> Sua thanh cong");
            }
            System.out.println("Co tiep tuc nhap sua them nguyen lieu khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    public void nhapXoa() {
        do {
            if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu"))) {
                System.out.println(">> Khong tim thay nguyen lieu can xoa");
            } else {
                System.out.println(">> Xoa thanh cong");
            }
            System.out.println("Co tiep tuc xoa cac nguyen lieu khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }
}