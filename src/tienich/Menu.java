package tienich;

public class Menu {
    private Menu(){}
    public static void run() {
        QuanLyDanhSach.taiDuLieuDanhSach();
        menuLoop:
        while(true) {
            System.out.println("-----MENU-----");
            System.out.println("1. Quan ly san pham");
            System.out.println("2. Quan ly nhan vien");
            System.out.println("3. Quan ly khach hang");
            System.out.println("4. Quan ly hoa don");
            System.out.println("5. Quan ly nguyen lieu");
            System.out.println("6. Quan ly nha cung cap");
            System.out.println("7. Quan ly phieu nhap hang");
            System.out.println("0. Dong va luu file");
            System.out.println("--------------");

            choseLoop:
            while (true) {
                int luaChon = NhapChuan.docSoNguyenKhongAm("Lua chon");
                switch (luaChon) {
                    case 0:break menuLoop;
                    case 1:menuQLSP();;break choseLoop;
                    case 2:chuaUpdate();break choseLoop;
                    case 3:chuaUpdate();break choseLoop;
                    case 4:chuaUpdate();break choseLoop;
                    case 5:chuaUpdate();break choseLoop;
                    case 6:chuaUpdate();break choseLoop;
                    case 7:chuaUpdate();break choseLoop;
                    default:chuaUpdate();break;
                }   
            }
        }
    }
    private static void chuaUpdate() {System.out.println(">> Chua cap nhat!!!");}
    public static void menuQLSP() {
        menuLoop:
        while(true) {
            System.out.println("-----MENU-----");
            System.out.println("1. Xem danh sach san pham");
            System.out.println("2. Sua mot san pham");
            System.out.println("3. Xoa mot san pham");
            System.out.println("4. Them san pham");
            System.out.println("5. Quan ly nguyen lieu");
            System.out.println("6. Quan ly nha cung cap");
            System.out.println("7. Quan ly phieu nhap hang");
            System.out.println("0. Dong va luu file");
            System.out.println("--------------");

            choseLoop:
            while (true) {
                int luaChon = NhapChuan.docSoNguyenKhongAm("Lua chon");
                switch (luaChon) {
                    case 0:break menuLoop;
                    case 1:QuanLyDanhSach.DS_SANPHAM.xuatDanhSach(); ;break choseLoop;
                    case 2:QuanLyDanhSach.DS_SANPHAM.nhapSua();break choseLoop;
                    case 3:QuanLyDanhSach.DS_SANPHAM.nhapXoa();break choseLoop;
                    case 4:QuanLyDanhSach.DS_SANPHAM.nhapThem();break choseLoop;
                    case 5:chuaUpdate();break choseLoop;
                    case 6:chuaUpdate();break choseLoop;
                    case 7:chuaUpdate();break choseLoop;
                    default:chuaUpdate();break;
                }   
            }
        }
    }
}

