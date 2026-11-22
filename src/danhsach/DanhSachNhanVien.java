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
import tienich.LuuTru;
import tienich.NhapChuan;

public class DanhSachNhanVien implements IDanhSach {
    private NhanVien[] DS_NhanVien;
    private int soLuong;

    public DanhSachNhanVien() {
        soLuong = 0;
        DS_NhanVien = new NhanVien[soLuong];
    }

    public DanhSachNhanVien(DanhSachNhanVien DS_NV) {
        soLuong = DS_NV.soLuong;
        DS_NhanVien = new NhanVien[soLuong];
        for (int i = 0; i < soLuong; i++)
            DS_NhanVien[i] = DS_NV.DS_NhanVien[i].saoChep();
    }

    // sao chep 1 ra danh sach moi
    @Override
    public DanhSachNhanVien saoChep() { return new DanhSachNhanVien(this); }

    // doc so luong cua danh sach
    @Override
    public int getSoLuong() { return soLuong; }

    // ======================================================
    // ghi du lieu danh sach xuong file
    @Override
    public void xuatDanhSachRaFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(LuuTru.FILEDSNV));
        dos.writeInt(soLuong);
        for (int i = 0; i < soLuong; i++) {
            DS_NhanVien[i].ghiFile(dos);
        }
        dos.close();
    }

    // doc du lieu danh sach vao chuong trinh
    @Override
    public void nhapDanhSachTuFile() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(LuuTru.FILEDSNV));
        soLuong = dis.readInt();
        if (DS_NhanVien.length < soLuong) DS_NhanVien = new NhanVien[soLuong];
        for (int i = 0; i < soLuong; i++) {
            NhanVien nv = new NhanVien("");
            nv.docFile(dis);
            DS_NhanVien[i] = nv;
        }
        dis.close();
    }

    // ======================================================
    // phuong thuc noi bo, dung de tao chuoi xu ly cho gon, dau vao phai duoc kiem tra truoc

    // tim vi tri nhan vien
    private int timKiemViTriNhanVien(String maNV) {
        for (int i = 0; i < soLuong; i++)
            if (DS_NhanVien[i] != null && DS_NhanVien[i].getMa().equals(maNV)) return i;
        return -1;
    }

    // them, sua, xoa 1 nhan vien
    private NhanVien them(NhanVien nv) {
        if (kiemTraMa(nv.getMa())) return null;
        if (soLuong == DS_NhanVien.length) DS_NhanVien = Arrays.copyOf(DS_NhanVien, soLuong + 1);
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
        for (int i = viTri; i < soLuong - 1; i++)
            DS_NhanVien[i] = DS_NhanVien[i + 1];
        DS_NhanVien[--soLuong] = null;
        return true;
    }

    // ======================================================
    // cac phuong thuc giao tiep ra ben ngoai

    // kiem tra ma nay da ton tai trong danh sach chua, co roi thi tra ve true, chua thi false
    public boolean kiemTraMa(String maNV) {
        if (maNV == null || maNV.isEmpty()) return false;
        return (timKiemViTriNhanVien(maNV) != -1);
    }

    // tra ra ban sao cua nhan vien
    public NhanVien timKiemMa(String maNV) {
        if (maNV == null || maNV.isEmpty()) return null;
        int viTriNV = timKiemViTriNhanVien(maNV);
        if (viTriNV == -1) return null;
        return DS_NhanVien[viTriNV].saoChep();
    }

    // them ban sao cua nhan vien ben ngoai vo danh sach
    public boolean themVaoDanhSach(NhanVien nv) {
        if (nv == null || nv.getMa().isEmpty()) return false;
        return them(nv.saoChep()) != null;
    }

    public NhanVien docPhanTu(int index) { return DS_NhanVien[index].saoChep(); }

    public DanhSachNhanVien timKiemTheoTen(String tenNhanVien) {
        DanhSachNhanVien dsTam = new DanhSachNhanVien();
        for (int i = 0; i < soLuong; i++) {
            if (DS_NhanVien[i] != null && DS_NhanVien[i].getTen().equalsIgnoreCase(tenNhanVien))
                dsTam.them(DS_NhanVien[i].saoChep());
        }
        if (dsTam.soLuong == 0) return null;
        return dsTam;
    }

    // ------------------------------------
    // cac phuong thuc giao dien
    @Override
    public void nhapTimKiem() {
        System.out.println("**** Tim kiem phieu nhap hang ****");
        String maNV = NhapChuan.docChuoi("Ma phieu can tim");
        int viTriPhieu = timKiemViTriNhanVien(maNV); 
        if(viTriPhieu == -1)
            System.out.println("Khong tim thay phieu nhap hang nay");
        else {
            DanhSachNhanVien dsTam = new DanhSachNhanVien();
            dsTam.them((DS_NhanVien[viTriPhieu]));
            dsTam.xuatDanhSach();
        }
    }

    // them, sua, xoa
    @Override
    public void nhapThem() {
        System.out.println("****bat dau nhap danh sach****");
        int nhapThem = NhapChuan.docSoNguyenKhongAm("So luong nhan vien muon them");
        DS_NhanVien = Arrays.copyOf(DS_NhanVien, soLuong + nhapThem);
        for (int i = 0; i < nhapThem; i++) {
            System.out.println("---------------------");
            String maNV;
            while (kiemTraMa(maNV = NhapChuan.docChuoiKhongRong("Nhap ma nhan vien")))
                System.out.println(">> ma nay da ton tai, vui long nhap ma khac !!!");
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

    // xuat danh sach voi cac dong du lieu duoc fomat bang
    @Override
    public void xuatDanhSach() {
        String[][] bangNhanVien = new String[soLuong + 1][];
        bangNhanVien[0] = new String[] { "Ma", "Ho", "Ten", "Chuc vu", "So dien thoai", "Gioi tinh", "Luong CB", "Tong ban" };
        for (int i = 0; i < soLuong; i++) {
            bangNhanVien[i + 1] = new String[] {
                DS_NhanVien[i].getMa(),
                DS_NhanVien[i].getHo(),
                DS_NhanVien[i].getTen(),
                DS_NhanVien[i].getChucVu(),
                DS_NhanVien[i].getSoDienThoai(),
                DS_NhanVien[i].getGioiTinh(),
                String.valueOf(DS_NhanVien[i].getLuongCoBan()),
                String.valueOf(DS_NhanVien[i].getTongTienDaBan())
            };
        }
        FormatBang.inBangDuLieu("DANH SACH NHAN VIEN", bangNhanVien);
        NhapChuan.docChuoi("Enter de quay lai");
    }
}
