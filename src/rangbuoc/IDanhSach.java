package rangbuoc;
import tienich.NhapChuan;
import java.io.IOException;

public interface IDanhSach {
    IDanhSach saoChep();
    int getSoLuong();
    void xuatDanhSachRaFile() throws IOException;
    void nhapDanhSachTuFile() throws IOException;
    void nhapTimKiem();
    // mac dinh: danh sach nao chua ho tro sap xep thi bao chua update
    default void nhapSapXep() {
        System.out.println("[THONG BAO] Tinh nang sap xep chua update !!!");
        NhapChuan.docChuoi("Enter de quay lai");
    }
    void nhapThem();
    void nhapSua();
    void nhapXoa();
    void xuatDanhSach();
}
