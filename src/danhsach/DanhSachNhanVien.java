package danhsach;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import mohinh.NhanVien;
import rangbuoc.IDanhSach;
import tienich.FormatBang;
import tienich.NhapChuan;

public class DanhSachNhanVien implements IDanhSach {
    private NhanVien[] DS_NhanVien;
    private int soLuong;
    private static final String FILE_DANH_SACH = "data/DS_NhanVien.dat";

    public DanhSachNhanVien() {
        soLuong = 0;
        DS_NhanVien = new NhanVien[soLuong];
    }

    public DanhSachNhanVien(DanhSachNhanVien DS_NV) {
        soLuong = DS_NV.soLuong;
        DS_NhanVien = new NhanVien[soLuong];
        for (int i = 0; i < soLuong; i++) {
            DS_NhanVien[i] = DS_NV.DS_NhanVien[i].saoChep();
        }
    }

    public DanhSachNhanVien saoChep() {
        return new DanhSachNhanVien(this);
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void xuatDanhSach() {
        String[][] bangNhanVien = new String[soLuong + 1][];
        bangNhanVien[0] = new String[]{"Ma", "Ho", "Ten", "Chuc vu", "So dien thoai", "Gioi tinh", "Luong co ban", "Tong tien da ban"};
        for (int i = 0; i < soLuong; i++) {
            bangNhanVien[i + 1] = Arrays.copyOf(DS_NhanVien[i].xuat(), bangNhanVien[0].length);
        }
        FormatBang.inBangDuLieu("DANH SACH NHAN VIEN", bangNhanVien);
        NhapChuan.docChuoi("Enter de quay lai");
    }

    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_DANH_SACH));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) {
            DS_NhanVien[i].ghiFile(dos);
        }
        dos.close();
    }

    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(FILE_DANH_SACH));
        soLuong = dis.readInt();
        if (DS_NhanVien.length < soLuong) {
            DS_NhanVien = new NhanVien[soLuong];
        }
        for (int i = 0; i < soLuong; i++) {
            NhanVien nv = new NhanVien();
            nv.docFile(dis);
            DS_NhanVien[i] = nv;
        }
        dis.close();
    }

    private int timKiemViTriNhanVien(String maNV) {
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhanVien[i] != null && DS_NhanVien[i].getMa().equalsIgnoreCase(maNV)) {
                return i;
            }
        }
        return -1;
    }

    private NhanVien them(NhanVien nv) {
        if (nv == null || kiemTraMa(nv.getMa())) {
            return null;
        }
        if (soLuong == DS_NhanVien.length) {
            DS_NhanVien = Arrays.copyOf(DS_NhanVien, soLuong + 1);
        }
        DS_NhanVien[soLuong++] = nv;
        return nv;
    }

    private NhanVien sua(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if (viTri == -1) {
            return null;
        }
        return DS_NhanVien[viTri].nhap();
    }

    private boolean xoa(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if (viTri == -1) {
            return false;
        }
        for (int i = viTri; i < soLuong - 1; i++) {
            DS_NhanVien[i] = DS_NhanVien[i + 1];
        }
        DS_NhanVien[--soLuong] = null;
        return true;
    }

    public boolean kiemTraMa(String maNV) {
        return timKiemViTriNhanVien(maNV) != -1;
    }

    public NhanVien timKiemTheoMa(String maNV) {
        int viTri = timKiemViTriNhanVien(maNV);
        if (viTri == -1) {
            return null;
        }
        return DS_NhanVien[viTri].saoChep();
    }

    public boolean themVaoDanhSach(NhanVien nv) {
        if (nv == null) {
            return false;
        }
        return them(nv.saoChep()) != null;
    }

    public void timKiemMa() {
    }

    public DanhSachNhanVien timKiemTheoTen(String tenNhanVien) {
        DanhSachNhanVien dsTam = new DanhSachNhanVien();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhanVien[i] != null && DS_NhanVien[i].getTen().equalsIgnoreCase(tenNhanVien)) {
                dsTam.them(DS_NhanVien[i].saoChep());
            }
        }
        if (dsTam.soLuong == 0) {
            return null;
        }
        return dsTam;
    }

    public void nhapThem() {
        System.out.println("****Bat dau nhap danh sach nhan vien****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nhan vien muon them");
        DS_NhanVien = Arrays.copyOf(DS_NhanVien, soLuong + nhapThem);
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            NhanVien nvMoi = new NhanVien();
            while (them(nvMoi.setMa(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien"))) == null) {
                System.out.println(">> Ma nay da ton tai, vui long nhap ma khac !!!");
            }
            nvMoi.nhap();
        }
        System.out.println("****Ket thuc nhap danh sach nhan vien****");
        NhapChuan.docChuoi("Enter de quay lai");
    }

    public void nhapSua() {
        do {
            if (sua(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien")) == null) {
                System.out.println(">> Nhan vien nay khong ton tai trong danh sach");
            } else {
                System.out.println(">> Sua thanh cong");
            }
            System.out.println("Co tiep tuc nhap sua them nhan vien khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }

    public void nhapXoa() {
        do {
            if (!xoa(NhapChuan.docChuoiKhongRong("Nhap ma nhan vien"))) {
                System.out.println(">> Khong tim thay nhan vien can xoa");
            } else {
                System.out.println(">> Xoa thanh cong");
            }
            System.out.println("Co tiep tuc xoa cac nhan vien khac khong?");
        } while (NhapChuan.docYesNo("", false));
    }
}