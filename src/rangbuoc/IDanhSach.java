package rangbuoc;

import java.io.IOException;

public interface IDanhSach {
    IDanhSach saoChep();
    int getSoLuong();
    void timKiemMa();
    void xuatDanhSach();
    void xuatDanhSachRaFile() throws IOException;
    void nhapDanhSachTuFile() throws IOException;
    void nhapThem();
    void nhapSua();
    void nhapXoa();
}
