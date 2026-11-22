package tienich;

import rangbuoc.IDanhSach;

public class Menu {
    private Menu(){}
    public static String[] THONGBAOMENU= new String[] {
        "[THONG BAO] Tinh nang chua update !!!\n",
        "[THONG BAO] Lua chon khong hop le !!!\n"
    };
    private static String thongBaoMenu = null;
    public  static void clearScreen() {System.out.print("\033[H\033[2J\033[3J");}

    // hien thi giao dien cac lua chon
    private static int menuLuaChon(String tieuDeMenu,String[] menuListNgang) {
        String[][] menuListDoc = new String[menuListNgang.length][];
        for(int i = 0; i < menuListDoc.length; i++) {(menuListDoc[i] = new String[1])[0] = menuListNgang[i];}
        FormatBang.inBangDuLieu(tieuDeMenu,menuListDoc, false);

        if(thongBaoMenu != null){System.out.print(thongBaoMenu);thongBaoMenu = null;}

        int luaChon = NhapChuan.nhapLuaChonMenu();
        clearScreen();
        return luaChon;
    }

    public static void run() {
        // chay man hinh ung dung
        clearScreen();
        QuanLyDanhSach.taiDuLieuDanhSach();
        while(true) {
            String [] menuList = new String[]{"1. San Pham","2. Nhan Vien","3. Khach Hang","4. Hoa Don","5. Nguyen Lieu","6. Nha Cung Cap", "7. Phieu Nhap Hang","8. Thong Ke Danh Sach", "9. Xem chi tiet hoa don", "10. xem chi tiet phieu nhap hang","0. Thoat"};
            int luaChon = menuLuaChon("MENU", menuList);
            if(luaChon == 0) break;
            if(luaChon >= 1 && luaChon <= 7)
                menuDanhSach(QuanLyDanhSach.DANHSACH[luaChon-1]);
            else if(luaChon == 8) menuThongKe();
            else if(luaChon == 9) {QuanLyDanhSach.DS_CHITIETHOADON.xuatDanhSach();clearScreen();}
            else if(luaChon == 10) {QuanLyDanhSach.DS_CHITIETPHIEUNHAPHANG.xuatDanhSach();clearScreen();}
            else thongBaoMenu = THONGBAOMENU[1];
        }
        // dong man hinh ung dung
        QuanLyDanhSach.ghiDuLieuDanhSach();
    }
    public static void menuDanhSach(IDanhSach DSLamViec) {
        menuLoop:
        while(true) {
            String[] menuList = new String[]{"1. Xem Danh Sach", "2. Them", "3. Sua", "4. Xoa", "5. Tim Kiem", "0. Thoat"};
            switch (menuLuaChon("Danh Sach", menuList)) {
                case 0 -> {break menuLoop;}
                case 1 -> DSLamViec.xuatDanhSach();
                case 2 -> DSLamViec.nhapThem();
                case 3 -> DSLamViec.nhapSua();
                case 4 -> DSLamViec.nhapXoa();
                case 5 -> DSLamViec.nhapTimKiem();
                default -> {thongBaoMenu = THONGBAOMENU[1]; continue menuLoop;}
            };
            clearScreen();
        }
    }
    public static void menuThongKe() {
        menuLoop:
        while (true) {
            String[] menuList = new String[] {
                "1. Tong Tien Hoa Don (ngay/Thang)",
                "2. Tong Tien Hoa Don (Quy|Thang/Nam)",
                "3. Tong Tien Hoa Don Cua Nhan Vien (Quy|Nam)",
                "4. Tong Tien Hoa Don Cua Khach Hang (Quy|Nam)",
                "5. Tong Tien Hoa Don Theo San Pham (Quy|Nam)",
                "0. Thoat"
            };
            switch (menuLuaChon("Thong Ke", menuList)) {
                case 0 -> {break menuLoop;}
                case 3 -> QuanLyDanhSach.thongKeHoaDonTheoNam(0);
                case 4 -> QuanLyDanhSach.thongKeHoaDonTheoNam(1);
                case 5 -> QuanLyDanhSach.thongKeHoaDonTheoNam(2);
                default -> {thongBaoMenu = THONGBAOMENU[0];}
            }
            clearScreen();
        }
    }
}

