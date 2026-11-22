package rangbuoc;

import java.io.IOException;

public interface ITruyVanDanhSach {
    ITruyVanDanhSach saoChep();
    int getSoLuong();
    void nhapTimKiem();
    void xuatDanhSach();
    void xuatDanhSachRaFile() throws IOException;
    void nhapDanhSachTuFile() throws IOException;
}
