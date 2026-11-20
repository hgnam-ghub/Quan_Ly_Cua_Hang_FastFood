package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import mohinh.NhanVien;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNhanVien implements IDanhSach {
    private NhanVien[] DS_NhanVien;
    private int soLuong;

    public DanhSachNhanVien() {
        soLuong = 0;
        DS_NhanVien = new NhanVien[0];
    }

    public DanhSachNhanVien(DanhSachNhanVien DS_NV) {
        soLuong = DS_NV.soLuong;
        DS_NhanVien = new NhanVien[soLuong];
        for (int i = 0; i < soLuong; i++) {
            DS_NhanVien[i] = (DS_NV.DS_NhanVien[i] != null) ? DS_NV.DS_NhanVien[i].saoChep() : null;
        }
    }

    @Override
    public DanhSachNhanVien saoChep() { return new DanhSachNhanVien(this); }

    @Override
    public int getSoLuong() { return soLuong; }

    @Override
    public void xuatDanhSachRaFile() throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSNV))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                DS_NhanVien[i].ghiFile(dos);
            }
        }
    }

    @Override
    public void nhapDanhSachTuFile() throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSNV))) {
            soLuong = dis.readInt();
            if (DS_NhanVien.length < soLuong) {
                DS_NhanVien = new NhanVien[soLuong];
            }
            for (int i = 0; i < soLuong; i++) {
                NhanVien nv = new NhanVien("");
                nv.docFile(dis);
                DS_NhanVien[i] = nv;
            }
        }
    }

    private int timKiemViTriNhanVien(String maNV) {
        if (maNV == null) return -1;
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhanVien[i] != null && maNV.equals(DS_NhanVien[i].getMa())) return i;
        }
        return -1;
    }

    private NhanVien them(NhanVien nv) {
        if (nv == null || nv.getMa() == null || nv.getMa().isEmpty() || kiemTraMa(nv.getMa())) return null;
        if (soLuong == DS_NhanVien.length) {
            int newSize = (DS_NhanVien.length == 0) ? 4 : DS_NhanVien.length * 2;
            DS_NhanVien = Arrays.copyOf(DS_NhanVien, newSize);
        }
        DS_NhanVien[soLuong++] = nv;
        return nv;
    }

    private NhanVien sua(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if (viTri == -1) return null;
        DS_NhanVien[viTri].nhap();
        return DS_NhanVien[viTri];
    }

    private boolean xoa(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if (viTri == -1) return false;
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NhanVien[i] = DS_NhanVien[i + 1];
        }
        DS_NhanVien[--soLuong] = null;
        return true;
    }

    // ======================================================
    // Sap xep tang dan / giam dan
    public void sapXepTheoMa(boolean tangDan) {
        Comparator<String> strComp = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);
        Comparator<NhanVien> comp = Comparator.comparing(NhanVien::getMa, strComp);
        Arrays.sort(DS_NhanVien, 0, soLuong, tangDan ? comp : comp.reversed());
    }

    public void sapXepTheoTen(boolean tangDan) {
        Comparator<String> strComp = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);
        Comparator<NhanVien> comp = Comparator.comparing(NhanVien::getTen, strComp);
        Arrays.sort(DS_NhanVien, 0, soLuong, tangDan ? comp : comp.reversed());
    }

    public void sapXepTheoLuongCoBan(boolean tangDan) {
        Comparator<NhanVien> comp = Comparator.comparingDouble(NhanVien::getLuongCoBan);
        Arrays.sort(DS_NhanVien, 0, soLuong, tangDan ? comp : comp.reversed());
    }

    public void sapXepTheoNgayVaoLam(boolean tangDan) {
        Comparator<LocalDate> dateComp = tangDan
            ? Comparator.nullsLast(Comparator.naturalOrder())
            : Comparator.nullsLast(Comparator.reverseOrder());
        Comparator<NhanVien> comp = Comparator.comparing(NhanVien::getNgayVaoLam, dateComp);
        Arrays.sort(DS_NhanVien, 0, soLuong, comp);
    }

    public void sapXepTheoNgay(boolean tangDan) {
        sapXepTheoNgayVaoLam(tangDan);
    }

    public void nhapSapXep() {
        if (soLuong <= 1) {
            System.out.println(">> Danh sach khong du phan tu de sap xep.");
            return;
        }
        System.out.println("Chon tieu chi sap xep: 1-Ma | 2-Ten | 3-Luong co ban | 4-Ngay vao lam");
        int tieuChi = NhapChuan.docSoNguyen("Nhap lua chon");
        System.out.println("Chon chieu sap xep: 1-Tang dan | 2-Giam dan");
        boolean tangDan = NhapChuan.docSoNguyen("Nhap lua chon") == 1;

        switch (tieuChi) {
            case 1 -> sapXepTheoMa(tangDan);
            case 2 -> sapXepTheoTen(tangDan);
            case 3 -> sapXepTheoLuongCoBan(tangDan);
            case 4 -> sapXepTheoNgayVaoLam(tangDan);
            default -> {
                System.out.println(">> Lua chon khong hop le!");
                return;
            }
        }
        System.out.println(">> Sap xep thanh cong!");
    }

    // ======================================================
    public boolean kiemTraMa(String maNV) {
        if (maNV == null || maNV.isEmpty()) return false;
        return timKiemViTriNhanVien(maNV) != -1;
    }

    public NhanVien timKiemMa(String maNV) {
        if (maNV == null || maNV.isEmpty()) return null;
        int viTriNV = timKiemViTriNhanVien(maNV);
        if (viTriNV == -1) return null;
        return DS_NhanVien[viTriNV].saoChep();
    }

    public boolean themVaoDanhSach(NhanVien nv) {
        if (nv == null || nv.getMa() == null || nv.getMa().isEmpty()) return false;
        return them(nv.saoChep()) != null;
    }

    public NhanVien docPhanTu(int index) {
        if (index < 0 || index >= soLuong || DS_NhanVien[index] == null) return null;
        return DS_NhanVien[index].saoChep();
    }

    public DanhSachNhanVien timKiemTheoTen(String tenNhanVien) {
        if (tenNhanVien == null) return null;
        DanhSachNhanVien dsTam = new DanhSachNhanVien();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhanVien[i] != null && tenNhanVien.equalsIgnoreCase(DS_NhanVien[i].getTen())) {
                dsTam.them(DS_NhanVien[i].saoChep());
            }
        }
        return (dsTam.soLuong == 0) ? null : dsTam;
    }

    @Override
    public void nhapTimKiem() {
        String tenCanTim = NhapChuan.docChuoiKhongRong("Nhap ten nhan vien can tim");
        DanhSachNhanVien ketQua = timKiemTheoTen(tenCanTim);
        if (ketQua == null) {
            System.out.println(">> Khong tim thay nhan vien nao phu hop.");
        } else {
            ketQua.xuatDanhSach();
        }
    }

    @Override
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nhan vien muon them");
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNV;
            while (kiemTraMa(maNV = NhapChuan.docChuoiKhongRong("Nhap ma nhan vien"))) {
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            }
            NhanVien nvMoi = new NhanVien(maNV);
            nvMoi.nhap();
            them(nvMoi);
        }
        System.out.println("****Ket thuc nhap danh sach****");
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void nhapSua() {
        do {
            if (sua(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien")) == null)
                System.out.println(">> Nhan vien nay khong ton tai trong danh sach");
            else System.out.println(">> Sua thanh cong");
            System.out.println("Co tiep tuc nhap sua them nhan vien khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    @Override
    public void nhapXoa() {
        do {
            if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien")))
                System.out.println(">> Khong tim thay nhan vien can xoa");
            else System.out.println(">> Xoa thanh cong");
            System.out.println("Co tiep tuc xoa cac nhan vien khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    @Override
    public void xuatDanhSach() {
        String[][] bangNhanVien = new String[soLuong + 1][];
        bangNhanVien[0] = new String[] { "Ma", "Ho", "Ten", "Chuc vu", "So dien thoai", "Gioi tinh", "Luong CB", "Tong ban", "Ngay vao lam" };
        for (int i = 0; i < soLuong; i++) {
            bangNhanVien[i + 1] = new String[] {
                DS_NhanVien[i].getMa(),
                DS_NhanVien[i].getHo(),
                DS_NhanVien[i].getTen(),
                DS_NhanVien[i].getChucVu(),
                DS_NhanVien[i].getSoDienThoai(),
                DS_NhanVien[i].getGioiTinh(),
                String.valueOf(DS_NhanVien[i].getLuongCoBan()),
                String.valueOf(DS_NhanVien[i].getTongTienDaBan()),
                NhanVien.dinhDangNgay(DS_NhanVien[i].getNgayVaoLam())
            };
        }
        FormatBang.inBangDuLieu("DANH SACH NHAN VIEN", bangNhanVien);
        NhapChuan.docChuoi("Enter de quay lai");
    }
}