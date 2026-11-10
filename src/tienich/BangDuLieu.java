package tienich;

public class BangDuLieu {
    private BangDuLieu(){};
    private static String[][] bangDuLieu;
    private static int[] chieuRongCot;
    private static int soDong, soCot;
    private static int demLe = 1;
    private static final char TOP_LEFT     = '\u250C';
    private static final char TOP_RIGHT    = '\u2510';
    private static final char BOT_LEFT     = '\u2514';
    private static final char BOT_RIGHT    = '\u2518';
    private static final String HORIZONTAL   = "\u2500";
    private static final char VERTICAL     = '\u2502';
    private static final char T_SHAPE_TOP  = '\u252C';
    private static final char T_SHAPE_BOT  = '\u2534';
    private static final char CROSS        = '\u253C';
    private static final char T_SHAPE_LEFT = '\u251C';
    private static final char T_SHAPE_RIGHT= '\u2524';

    public static void khoiTaoBang(String[] tieuDe, int soDong) {
        BangDuLieu.soDong = soDong + 1;
        BangDuLieu.soCot = tieuDe.length;
        bangDuLieu = new String[BangDuLieu.soDong][BangDuLieu.soCot];
        bangDuLieu[0] = tieuDe;
    }
    public static void nhapDong(int dong, String[] duLieu) {
        bangDuLieu[dong + 1] = duLieu;
    }
    public static void xuatBang(){
        chieuRongCot = new int[soCot];
        for(int i = 0; i < soCot; i++)
        for(int j = 0; j < soDong; j++) {
            int doDaiODuLieu = bangDuLieu[j][i].length();
            if(chieuRongCot[i] < doDaiODuLieu) chieuRongCot[i] = doDaiODuLieu;
        }
        for(int i = 0; i < soDong; i++)
        for(int j = 0; j < soCot; j++) {
            if(bangDuLieu[i][j].isEmpty()) bangDuLieu[i][j] = HORIZONTAL.repeat(chieuRongCot[j]);
        }
        String dongKe = taoCanh(T_SHAPE_LEFT, CROSS, T_SHAPE_RIGHT);
        String dinhDangDong = taoDinhDang();
        System.out.print(taoCanh(TOP_LEFT, T_SHAPE_TOP, TOP_RIGHT));
        for(int i = 0; i < soDong - 1; i++) {
            System.out.printf(dinhDangDong, (Object[])bangDuLieu[i]);
            System.out.print(dongKe);
        }
        System.out.printf(dinhDangDong, (Object[])bangDuLieu[soDong-1]);
        System.out.print(taoCanh(BOT_LEFT, T_SHAPE_BOT, BOT_RIGHT)); 
    }
    // tao canh ngang kieu nhu <ki tu o dau>----------------<ki tu o giua>---------------<ki tu o cuoi>
    private static String taoCanh(char dau, char giua, char cuoi) {
        String dongKe = String.valueOf(dau);
        for(int i = 0; i < soCot - 1; i ++)
            dongKe += HORIZONTAL.repeat(chieuRongCot[i] + demLe * 2) + giua ;
        dongKe += HORIZONTAL.repeat(chieuRongCot[soCot - 1] + demLe * 2) + cuoi;
        return dongKe + '\n';
    }
    // tao format cot du lieu, dua vao so luong cot va cac tham so truyen vao, chinh format theo kich thuoc luu trong chieuRongCot[]
    private static String taoDinhDang() {
        String padding = " ".repeat(demLe);
        String dinhDangDongDuLieu = String.valueOf(VERTICAL);
        for(int i = 0; i < soCot; i++)
        dinhDangDongDuLieu += padding +"%" + (chieuRongCot[i] ) + "s" + padding + VERTICAL;
        return dinhDangDongDuLieu + '\n';
    }
}
 