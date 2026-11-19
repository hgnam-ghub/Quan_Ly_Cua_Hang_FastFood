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
import tienich.NhapChuan;

public class DanhSachNhaCungCap implements IDanhSach {
    private NhaCungCap[] DS_NhaCungCap;
    private int soLuong;
    private static final String FILE_DANH_SACH = "data/DS_NhaCungCap.dat";

    public DanhSachNhaCungCap() {
        soLuong = 0;
        DS_NhaCungCap = new NhaCungCap[soLuong];
    }

    public DanhSachNhaCungCap(DanhSachNhaCungCap DS_NCC) {
        soLuong = DS_NCC.soLuong;
        DS_NhaCungCap = new NhaCungCap[soLuong];
        for (int i = 0; i < soLuong; i++) {
            DS_NhaCungCap[i] = DS_NCC.DS_NhaCungCap[i].saoChep();
        }
    }

    public DanhSachNhaCungCap saoChep() {
        return new DanhSachNhaCungCap(this);
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void xuatDanhSach() {
        String[][] bangNhaCungCap = new String[soLuong + 1][];
        bangNhaCungCap[0] = new String[]{"Ma", "Ho", "Ten", "Dia chi", "So dien thoai", "Email"};
        for (int i = 0; i < soLuong; i++) {
            bangNhaCungCap[i + 1] = Arrays.copyOf(DS_NhaCungCap[i].xuat(), bangNhaCungCap[0].length);
        }
        FormatBang.inBangDuLieu("DANH SACH NHA CUNG CAP", bangNhaCungCap);
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_DANH_SACH));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) {
            DS_NhaCungCap[i].ghiFile(dos);
        }
        dos.close();
    }

    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(FILE_DANH_SACH));
        soLuong = dis.readInt();
        if (DS_NhaCungCap.length < soLuong) {
            DS_NhaCungCap = new NhaCungCap[soLuong];
        }
        for (int i = 0; i < soLuong; i++) {
            NhaCungCap ncc = new NhaCungCap();
            ncc.docFile(dis);
            DS_NhaCungCap[i] = ncc;
        }
        dis.close();
    }

    private int timKiemViTriNhaCungCap(String maNCC) {
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhaCungCap[i] != null && DS_NhaCungCap[i].getMa().equalsIgnoreCase(maNCC)) {
                return i;
            }
        }
        return -1;
    }

    private NhaCungCap them(NhaCungCap ncc) {
        if (ncc == null || kiemTraMa(ncc.getMa())) {
            return null;
        }
        if (soLuong == DS_NhaCungCap.length) {
            DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, soLuong + 1);
        }
        DS_NhaCungCap[soLuong++] = ncc;
        return ncc;
    }

    private NhaCungCap sua(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if (viTri == -1) {
            return null;
        }
        return DS_NhaCungCap[viTri].nhap();
    }

    private boolean xoa(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if (viTri == -1) {
            return false;
        }
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NhaCungCap[i] = DS_NhaCungCap[i + 1];
        }
        DS_NhaCungCap[--soLuong] = null;
        return true;
    }

    public boolean kiemTraMa(String maNCC) {
        return timKiemViTriNhaCungCap(maNCC) != -1;
    }

    public NhaCungCap timKiemTheoMa(String maNCC) {
        int viTri = timKiemViTriNhaCungCap(maNCC);
        if (viTri == -1) {
            return null;
        }
        return DS_NhaCungCap[viTri].saoChep();
    }

    public boolean themVaoDanhSach(NhaCungCap ncc) {
        if (ncc == null) {
            return false;
        }
        return them(ncc.saoChep()) != null;
    }

    public void timKiemMa() {
    }

    public DanhSachNhaCungCap timKiemTheoTen(String tenNhaCungCap) {
        DanhSachNhaCungCap dsTam = new DanhSachNhaCungCap();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhaCungCap[i] != null && DS_NhaCungCap[i].getTen().equalsIgnoreCase(tenNhaCungCap)) {
                dsTam.them(DS_NhaCungCap[i].saoChep());
            }
        }
        if (dsTam.soLuong == 0) {
            return null;
        }
        return dsTam;
    }

    public void nhapThem() {
        System.out.println("****Bat dau nhap danh sach nha cung cap****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nha cung cap muon them");
        DS_NhaCungCap = Arrays.copyOf(DS_NhaCungCap, soLuong + nhapThem);
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            NhaCungCap nccMoi = new NhaCungCap();
            while (them(nccMoi.setMa(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap"))) == null) {
                System.out.println(">> Ma nay da ton tai, vui long nhap ma khac !!!");
            }
            nccMoi.nhap();
        }
        System.out.println("****Ket thuc nhap danh sach nha cung cap****");
        NhapChuan.docChuoi("Enter de quay lai");
    }

    public void nhapSua() {
        do {
            if (sua(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap")) == null) {
                System.out.println(">> Nha cung cap nay khong ton tai trong danh sach");
            } else {
                System.out.println(">> Sua thanh cong");
            }
            System.out.println("Co tiep tuc nhap sua them nha cung cap khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    public void nhapXoa() {
        do {
            if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nha cung cap"))) {
                System.out.println(">> Khong tim thay nha cung cap can xoa");
            } else {
                System.out.println(">> Xoa thanh cong");
            }
            System.out.println("Co tiep tuc xoa cac nha cung cap khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }
}