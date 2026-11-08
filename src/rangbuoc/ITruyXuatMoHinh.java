package rangbuoc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ITruyXuatMoHinh {
    void nhap();
    void xuat();
    void ghiFile(DataOutputStream dos) throws IOException;
    void docFile(DataInputStream dis) throws IOException;
}
