package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import mohinh.NhaCungCap;
import mohinh.NhanVien;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNhaCungCap implements IDanhSach {
    private NhaCungCap[] DS_NhaCungCap;
    private int soLuong;

    public DanhSachNhaCungCap() {
        soLuong = 0;
        DS_NhaCungCap = new NhaCungCap[0];
    }

    public DanhSachNhaCungCap(DanhSachNhaCungCap DS_NCC) {
        soLuong = DS_NCC.soLuong;
        DS_NhaCungCap = new NhaCungCap[soLuong];
        for (int i = 0; i < soLuong; i++) {
            DS_NhaCungCap[i] = (DS_NCC.DS_NhaCungCap[i] != null) ? DS_NCC.DS_NhaCungCap[i].saoChep() : null;
        }
    }

    @Override
    public DanhSachNhaCungCap saoChep() { return new DanhSachNhaCungCap(this); }

    @Override
    public int getSoLuong() { return soLuong; }

    @Override
    public void xuatDanhSachRaFile() throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSNCC))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                DS_NhaCungCap[i].ghiFile(dos);
            }
        }
    }

    @Override
    public void nhapDanhSachTuFile() throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSNCC))) {
            soLuong = dis.readInt();
            if (DS_NhaCungCap.length < soLuong) {
                DS_NhaCungCap = new NhaCungCap[soLuong];
            }
            for (int i = 0; i < soLuong; i++) {
                NhaCungCap ncc = new NhaCungCap("");
                ncc.docFile(dis);
                DS_NhaCungCap[i] = ncc;
            }
        }
    }

    private int timKiemViTriNhaCungCap(String maNCC) {
        if (maNCC == null) return -1;
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhaCungCap[i] != null && maNCC.equals(DS_NhaCungCap[i].getMa())) return i;
        }
        return -1;
    }

    private NhaCungCap them(NhaCungCap ncc) {
        if (ncc == null || ncc.getMa() == null || ncc.getMa().isEmpty() || kiemTraMa(ncc.getMa())) return null;
        if (soLuong == DS_NhaCungCap.length) {
            int newSize = (DS_NhaCungCap.length == 0) ? 4 : DS_NhaCungCap.length * 2;
            DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, newSize);
        }
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
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NhaCungCap[i] = DS_NhaCungCap[i + 1];
        }
        DS_NhaCungCap[--soLuong] = null;
        return true;
    }

    // ======================================================
    // Sap xep tang dan / giam dan
    public void sapXepTheoMa(boolean tangDan) {
        Comparator<String> strComp = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);
        Comparator<NhaCungCap> comp = Comparator.comparing(NhaCungCap::getMa, strComp);
        Arrays.sort(DS_NhaCungCap, 0, soLuong, tangDan ? comp : comp.reversed());
    }

    public void sapXepTheoTen(boolean tangDan) {
        Comparator<String> strComp = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);
        Comparator<NhaCungCap> comp = Comparator.comparing(NhaCungCap::getTen, strComp);
        Arrays.sort(DS_NhaCungCap, 0, soLuong, tangDan ? comp : comp.reversed());
    }

    public void sapXepTheoNgayHopTac(boolean tangDan) {
        Comparator<LocalDate> dateComp = tangDan
            ? Comparator.nullsLast(Comparator.naturalOrder())
            : Comparator.nullsLast(Comparator.reverseOrder());
        Comparator<NhaCungCap> comp = Comparator.comparing(NhaCungCap::getNgayHopTac, dateComp);
        Arrays.sort(DS_NhaCungCap, 0, soLuong, comp);
    }

    public void sapXepTheoNgay(boolean tangDan) {
        sapXepTheoNgayHopTac(tangDan);
    }

    public void nhapSapXep() {
        if (soLuong <= 1) {
            System.out.println(">> Danh sach khong du phan tu de sap xep.");
            return;
        }
        System.out.println("Chon tieu chi sap xep: 1-Ma | 2-Ten | 3-Ngay hop tac");
        int tieuChi = NhapChuan.docSoNguyen("Nhap lua chon");
        System.out.println("Chon chieu sap xep: 1-Tang dan | 2-Giam dan");
        boolean tangDan = NhapChuan.docSoNguyen("Nhap lua chon") == 1;

        switch (tieuChi) {
            case 1 -> sapXepTheoMa(tangDan);
            case 2 -> sapXepTheoTen(tangDan);
            case 3 -> sapXepTheoNgayHopTac(tangDan);
            default -> {
                System.out.println(">> Lua chon khong hop le!");
                return;
            }
        }
        System.out.println(">> Sap xep thanh cong!");
    }

    // ======================================================
    public boolean kiemTraMa(String maNCC) {
        if (maNCC == null || maNCC.isEmpty()) return false;
        return timKiemViTriNhaCungCap(maNCC) != -1;
    }

    public NhaCungCap timKiemMa(String maNCC) {
        if (maNCC == null || maNCC.isEmpty()) return null;
        int viTriNCC = timKiemViTriNhaCungCap(maNCC);
        if (viTriNCC == -1) return null;
        return DS_NhaCungCap[viTriNCC].saoChep();
    }

    public boolean themVaoDanhSach(NhaCungCap ncc) {
        if (ncc == null || ncc.getMa() == null || ncc.getMa().isEmpty()) return false;
        return them(ncc.saoChep()) != null;
    }

    public NhaCungCap docPhanTu(int index) {
        if (index < 0 || index >= soLuong || DS_NhaCungCap[index] == null) return null;
        return DS_NhaCungCap[index].saoChep();
    }

    public DanhSachNhaCungCap timKiemTheoTen(String tenNhaCungCap) {
        if (tenNhaCungCap == null) return null;
        DanhSachNhaCungCap dsTam = new DanhSachNhaCungCap();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhaCungCap[i] != null && tenNhaCungCap.equalsIgnoreCase(DS_NhaCungCap[i].getTen())) {
                dsTam.them(DS_NhaCungCap[i].saoChep());
            }
        }
        return (dsTam.soLuong == 0) ? null : dsTam;
    }

    @Override
    public void nhapTimKiem() {
        String tenCanTim = NhapChuan.docChuoiKhongRong("Nhap ten nha cung cap can tim");
        DanhSachNhaCungCap ketQua = timKiemTheoTen(tenCanTim);
        if (ketQua == null) {
            System.out.println(">> Khong tim thay nha cung cap nao phu hop.");
        } else {
            ketQua.xuatDanhSach();
        }
    }

    @Override
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nha cung cap muon them");
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNCC;
            while (kiemTraMa(maNCC = NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap"))) {
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
            }
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

    @Override
    public void xuatDanhSach() {
        String[][] bangNhaCungCap = new String[soLuong + 1][];
        bangNhaCungCap[0] = new String[] { "Ma", "Ho", "Ten", "Dia chi", "So dien thoai", "Email", "Ngay hop tac" };
        for (int i = 0; i < soLuong; i++) {
            bangNhaCungCap[i + 1] = new String[] {
                DS_NhaCungCap[i].getMa(),
                DS_NhaCungCap[i].getHo(),
                DS_NhaCungCap[i].getTen(),
                DS_NhaCungCap[i].getDiaChi(),
                DS_NhaCungCap[i].getSoDienThoai(),
                DS_NhaCungCap[i].getEmail(),
                NhanVien.dinhDangNgay(DS_NhaCungCap[i].getNgayHopTac())
            };
        }
        FormatBang.inBangDuLieu("DANH SACH NHA CUNG CAP", bangNhaCungCap);
        NhapChuan.docChuoi("Enter de quay lai");
    }
}