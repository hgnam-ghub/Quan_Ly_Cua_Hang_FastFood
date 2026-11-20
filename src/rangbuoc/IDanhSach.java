package rangbuoc;

import java.io.IOException;

public interface IDanhSach {
    IDanhSach saoChep();
    int getSoLuong();
    void xuatDanhSachRaFile() throws IOException;
    void nhapDanhSachTuFile() throws IOException;
    void nhapTimKiem();
    void nhapThem();
    void nhapSua();
    void nhapXoa();
    void xuatDanhSach();
}
