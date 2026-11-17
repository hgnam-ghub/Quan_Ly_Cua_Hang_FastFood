package tienich;

import rangbuoc.IDanhSach;

public class Menu {
    private Menu(){}
    private static IDanhSach DanhSachDangLamViec = null;
    public static String[] THONGBAOMENU= new String[] {
        "[THONG BAO] Tinh nang chua update !!!\n",
        "[THONG BAO] Lua chon khong hop le !!!\n"
    };
    private static String thongBaoMenu = null;
    public  static void clearScreen() {System.out.print("\033[H\033[2J");}

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
        System.out.print("\033[?1049h");
        QuanLyDanhSach.taiDuLieuDanhSach();
        menuLoop:
        while(true) {
            String [] menuList = new String[]{
                "1. Quan ly danh sach San Pham",
                "2. Quan ly danh sach khach Hang",
                "3. Quan ly danh sach Nhan Vien",
                "4. Quan ly danh sach Nha Cung Cap",
                "5. Quan ly danh sach Nguyen Lieu",
                "6. Quan ly danh sach Hoa Don",
                "7. Quan ly danh sach Danh Sach Phieu Nhap Hang",
                "8. Thong Ke Danh Sach",
                "0. Thoat"
            };
            switch (menuLuaChon("MENU", menuList)) {
                case 0 -> {break menuLoop;}
                case 1 -> DanhSachDangLamViec = QuanLyDanhSach.DS_SANPHAM;
                case 2 -> {thongBaoMenu = THONGBAOMENU[0]; continue menuLoop;}
                case 3 -> {thongBaoMenu = THONGBAOMENU[0]; continue menuLoop;}
                case 4 -> {thongBaoMenu = THONGBAOMENU[0]; continue menuLoop;}
                case 5 -> {thongBaoMenu = THONGBAOMENU[0]; continue menuLoop;}
                case 6 -> {thongBaoMenu = THONGBAOMENU[0]; continue menuLoop;}
                case 7 -> {thongBaoMenu = THONGBAOMENU[0]; continue menuLoop;}
                default -> {thongBaoMenu = THONGBAOMENU[1]; continue menuLoop;}
            };
            menuDanhSach();
        }
        // dong man hinh ung dung
        System.out.print("\033[?1049l");
    }
    public static void menuDanhSach() {
        menuLoop:
        while(true) {
            String [] menuList = new String[]{"1. Xem Danh Sach", "2. Nhap Them", "3. Sua", "4. Xoa", "0. Thoat"};
            switch (menuLuaChon("Danh Sach", menuList)) {
                case 0 -> {break menuLoop;}
                case 1 -> DanhSachDangLamViec.xuatDanhSach();
                case 2 -> DanhSachDangLamViec.nhapThem();
                case 3 -> DanhSachDangLamViec.nhapSua();
                case 4 -> DanhSachDangLamViec.nhapXoa();
                default -> {thongBaoMenu = THONGBAOMENU[1]; continue menuLoop;}
            };
            clearScreen();
        }
    }
}

