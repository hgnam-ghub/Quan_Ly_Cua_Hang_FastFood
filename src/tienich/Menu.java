package tienich;

public class Menu {
    public static String[] THONGBAOMENU= new String[] {
        "[THONG BAO] Tinh nang chua update !!!\n",
        "[THONG BAO] Lua chon khong hop le !!!\n"
    };
    private static String thongBaoMenu = null;
    private Menu(){}
    public  static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
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
        // chay menu voi man hinh thay the, dung ma ANSI de ra lenh cho terminal
        System.out.print("\033[?1049h");
        QuanLyDanhSach.taiDuLieuDanhSach();
        menuLoop:
        while(true) {
            String [] menuList = new String[]{"1. Quan ly danh sach san pham", "0. Thoat"};
            switch (menuLuaChon("MENU", menuList)) {
                case 0 -> {break menuLoop;}
                case 1 -> menuQLSP();
                default -> thongBaoMenu = THONGBAOMENU[1];
            };
        }
        // ra lenh cho termunal tat man hinh thay the va quay lai man hinh chinh
        System.out.print("\033[?1049l");
    }
    public static void menuQLSP() {
        menuLoop:
        while(true) {
            String [] menuList = new String[]{"1. Xem Danh Sach", "2. Nhap Them San Pham", "3. Sua San Pham", "4. Xoa San Pham", "0. Thoat"};
            int luaChon = menuLuaChon("QUAN LY SAN PHAM", menuList);
            switch (luaChon) {
                case 0 -> {break menuLoop;}
                case 1 -> QuanLyDanhSach.DS_SANPHAM.xuatDanhSach();
                case 2 -> QuanLyDanhSach.DS_SANPHAM.nhapThem();
                case 3 -> QuanLyDanhSach.DS_SANPHAM.nhapSua();
                case 4 -> QuanLyDanhSach.DS_SANPHAM.nhapXoa();
                default -> thongBaoMenu = THONGBAOMENU[1];
            };
            clearScreen();
        }
    }

}

