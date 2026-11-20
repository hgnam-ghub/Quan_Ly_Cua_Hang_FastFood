package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import mohinh.NguyenLieu;
import mohinh.NhanVien;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNguyenLieu implements IDanhSach {
    private NguyenLieu[] DS_NguyenLieu;
    private int soLuong;

    public DanhSachNguyenLieu() {
        soLuong = 0;
        DS_NguyenLieu = new NguyenLieu[0];
    }

    public DanhSachNguyenLieu(DanhSachNguyenLieu DS_NL) {
        soLuong = DS_NL.soLuong;
        DS_NguyenLieu = new NguyenLieu[soLuong];
        for (int i = 0; i < soLuong; i++) {
            DS_NguyenLieu[i] = (DS_NL.DS_NguyenLieu[i] != null) ? DS_NL.DS_NguyenLieu[i].saoChep() : null;
        }
    }

    @Override
    public DanhSachNguyenLieu saoChep() { return new DanhSachNguyenLieu(this); }

    @Override
    public int getSoLuong() { return soLuong; }

    @Override
    public void xuatDanhSachRaFile() throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSNL))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                DS_NguyenLieu[i].ghiFile(dos);
            }
        }
    }

    @Override
    public void nhapDanhSachTuFile() throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSNL))) {
            soLuong = dis.readInt();
            if (DS_NguyenLieu.length < soLuong) {
                DS_NguyenLieu = new NguyenLieu[soLuong];
            }
            for (int i = 0; i < soLuong; i++) {
                NguyenLieu nl = new NguyenLieu("");
                nl.docFile(dis);
                DS_NguyenLieu[i] = nl;
            }
        }
    }

    private int timKiemViTriNguyenLieu(String maNL) {
        if (maNL == null) return -1;
        for (int i = 0; i < soLuong; i++) {
            if (DS_NguyenLieu[i] != null && maNL.equals(DS_NguyenLieu[i].getMa())) return i;
        }
        return -1;
    }

    private NguyenLieu them(NguyenLieu nl) {
        if (nl == null || nl.getMa() == null || nl.getMa().isEmpty() || kiemTraMa(nl.getMa())) return null;
        if (soLuong == DS_NguyenLieu.length) {
            int newSize = (DS_NguyenLieu.length == 0) ? 4 : DS_NguyenLieu.length * 2;
            DS_NguyenLieu = Arrays.copyOf(DS_NguyenLieu, newSize);
        }
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
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NguyenLieu[i] = DS_NguyenLieu[i + 1];
        }
        DS_NguyenLieu[--soLuong] = null;
        return true;
    }

    // ======================================================
    // Sap xep tang dan / giam dan
    public void sapXepTheoMa(boolean tangDan) {
        Comparator<String> strComp = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);
        Comparator<NguyenLieu> comp = Comparator.comparing(NguyenLieu::getMa, strComp);
        Arrays.sort(DS_NguyenLieu, 0, soLuong, tangDan ? comp : comp.reversed());
    }

    public void sapXepTheoTen(boolean tangDan) {
        Comparator<String> strComp = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);
        Comparator<NguyenLieu> comp = Comparator.comparing(NguyenLieu::getTen, strComp);
        Arrays.sort(DS_NguyenLieu, 0, soLuong, tangDan ? comp : comp.reversed());
    }

    public void sapXepTheoDonGia(boolean tangDan) {
        Comparator<NguyenLieu> comp = Comparator.comparingDouble(NguyenLieu::getDonGia);
        Arrays.sort(DS_NguyenLieu, 0, soLuong, tangDan ? comp : comp.reversed());
    }

    public void sapXepTheoNgayNhap(boolean tangDan) {
        Comparator<LocalDate> dateComp = tangDan
            ? Comparator.nullsLast(Comparator.naturalOrder())
            : Comparator.nullsLast(Comparator.reverseOrder());
        Comparator<NguyenLieu> comp = Comparator.comparing(NguyenLieu::getNgayNhap, dateComp);
        Arrays.sort(DS_NguyenLieu, 0, soLuong, comp);
    }

    public void sapXepTheoNgay(boolean tangDan) {
        sapXepTheoNgayNhap(tangDan);
    }

    public void nhapSapXep() {
        if (soLuong <= 1) {
            System.out.println(">> Danh sach khong du phan tu de sap xep.");
            return;
        }
        System.out.println("Chon tieu chi sap xep: 1-Ma | 2-Ten | 3-Don gia | 4-Ngay nhap");
        int tieuChi = NhapChuan.docSoNguyen("Nhap lua chon");
        System.out.println("Chon chieu sap xep: 1-Tang dan | 2-Giam dan");
        boolean tangDan = NhapChuan.docSoNguyen("Nhap lua chon") == 1;

        switch (tieuChi) {
            case 1 -> sapXepTheoMa(tangDan);
            case 2 -> sapXepTheoTen(tangDan);
            case 3 -> sapXepTheoDonGia(tangDan);
            case 4 -> sapXepTheoNgayNhap(tangDan);
            default -> {
                System.out.println(">> Lua chon khong hop le!");
                return;
            }
        }
        System.out.println(">> Sap xep thanh cong!");
    }

    // ======================================================
    public boolean kiemTraMa(String maNL) {
        if (maNL == null || maNL.isEmpty()) return false;
        return timKiemViTriNguyenLieu(maNL) != -1;
    }

    public NguyenLieu timKiemMa(String maNL) {
        if (maNL == null || maNL.isEmpty()) return null;
        int viTriNL = timKiemViTriNguyenLieu(maNL);
        if (viTriNL == -1) return null;
        return DS_NguyenLieu[viTriNL].saoChep();
    }

    public boolean themVaoDanhSach(NguyenLieu nl) {
        if (nl == null || nl.getMa() == null || nl.getMa().isEmpty()) return false;
        return them(nl.saoChep()) != null;
    }

    public NguyenLieu docPhanTu(int index) {
        if (index < 0 || index >= soLuong || DS_NguyenLieu[index] == null) return null;
        return DS_NguyenLieu[index].saoChep();
    }

    public DanhSachNguyenLieu timKiemTheoTen(String tenNguyenLieu) {
        if (tenNguyenLieu == null) return null;
        DanhSachNguyenLieu dsTam = new DanhSachNguyenLieu();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NguyenLieu[i] != null && tenNguyenLieu.equalsIgnoreCase(DS_NguyenLieu[i].getTen())) {
                dsTam.them(DS_NguyenLieu[i].saoChep());
            }
        }
        return (dsTam.soLuong == 0) ? null : dsTam;
    }

    @Override
    public void nhapTimKiem() {
        String tenCanTim = NhapChuan.docChuoiKhongRong("Nhap ten nguyen lieu can tim");
        DanhSachNguyenLieu ketQua = timKiemTheoTen(tenCanTim);
        if (ketQua == null) {
            System.out.println(">> Khong tim thay nguyen lieu nao phu hop.");
        } else {
            ketQua.xuatDanhSach();
        }
    }

    @Override
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nguyen lieu muon them");
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNL;
            while (kiemTraMa(maNL = NhapChuan.docChuoiKhongRong("Nhap ma nguyen lieu"))) {
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            }
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

    @Override
    public void xuatDanhSach() {
        String[][] bangNguyenLieu = new String[soLuong + 1][];
        bangNguyenLieu[0] = new String[] { "Ma", "Ten", "Don vi", "Don gia", "So luong", "Ngay nhap" };
        for (int i = 0; i < soLuong; i++) {
            bangNguyenLieu[i + 1] = new String[] {
                DS_NguyenLieu[i].getMa(),
                DS_NguyenLieu[i].getTen(),
                DS_NguyenLieu[i].getDonVi(),
                String.valueOf(DS_NguyenLieu[i].getDonGia()),
                String.valueOf(DS_NguyenLieu[i].getSoLuong()),
                NhanVien.dinhDangNgay(DS_NguyenLieu[i].getNgayNhap())
            };
        }
        FormatBang.inBangDuLieu("DANH SACH NGUYEN LIEU", bangNguyenLieu);
        NhapChuan.docChuoi("Enter de quay lai");
    }
}