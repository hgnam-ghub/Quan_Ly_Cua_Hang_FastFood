package rangbuoc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ITruyXuatMoHinh {
    Object nhap();
    String[] xuat();
    void ghiFile(DataOutputStream dos) throws IOException;
    void docFile(DataInputStream dis) throws IOException;
}
