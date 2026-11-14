package tienich;

public class FormatBang {
    private FormatBang(){};
    private static int padding = 1;
    private static String[][] bangDuLieu;
    private static int[] chieuRongCot;
    private static int soDong, soCot;
    private static final char L_TOP_LEFT = '\u250C', L_TOP_RIGHT = '\u2510', L_BOT_LEFT = '\u2514', L_BOT_RIGHT = '\u2518', HORIZONTAL = '\u2500', VERTICAL = '\u2502', T_SHAPE_TOP = '\u252C', T_SHAPE_BOT = '\u2534', CROSS = '\u253C', T_SHAPE_LEFT = '\u251C', T_SHAPE_RIGHT= '\u2524';

    // cac ham in bang du lieu voi tham so ngan gon
    public static void inBangDuLieu(String[][] bangDuLieu) {inBangDuLieu(null, bangDuLieu, true);}
    public static void inBangDuLieu(String[][] bangDuLieu, boolean midLine) {inBangDuLieu(null, bangDuLieu, midLine);}
    public static void inBangDuLieu(String tieuDeBang,String[][] bangDuLieu) {inBangDuLieu(tieuDeBang, bangDuLieu, true);}

    // ham in bang du lieu tu bang dau vao
    public static void inBangDuLieu(String tieuDeBang,String[][] bangDuLieu, boolean midLine) {
        FormatBang.bangDuLieu = bangDuLieu;
        cauHinhVaKiemTraBang();
        xuLyDuLieuBang();
        
        String lineTop;
        String lineBottom = makeLine(L_BOT_LEFT, T_SHAPE_BOT, L_BOT_RIGHT);
        String format = taoFormatDuLieu();

        // neu co tieu de bang thi ve tieu de bang len dau bang
        if(tieuDeBang != null && !tieuDeBang.isEmpty()) {
            int doRongHangNgang = soCot - 1;
            for(int cot = 0; cot < soCot; cot++) doRongHangNgang += chieuRongCot[cot];
            String padCanGiua = " ".repeat(doRongHangNgang/2 - tieuDeBang.length()/2);
            System.out.print(makeLine(L_TOP_LEFT, HORIZONTAL, L_TOP_RIGHT));
            System.out.printf("%c%-" + doRongHangNgang +  "s%c\n", VERTICAL, padCanGiua + tieuDeBang, VERTICAL);
            lineTop = makeLine(T_SHAPE_LEFT, T_SHAPE_TOP, T_SHAPE_RIGHT);
        }
        else lineTop = makeLine(L_TOP_LEFT, T_SHAPE_TOP, L_TOP_RIGHT);
        System.out.print(lineTop); // dong ke dau tien cua bang
        if(midLine) {
            String dongKeMiddle = makeLine(T_SHAPE_LEFT, CROSS, T_SHAPE_RIGHT);
            for(int i = 0; i < soDong - 1; i++) {
                System.out.printf(format, (Object[]) bangDuLieu[i]);
                if(midLine) System.out.print(dongKeMiddle);
            }
            System.out.printf(format, (Object[]) bangDuLieu[soDong - 1]);

        }
        else for(int i = 0; i < soDong; i++) System.out.printf(format ,(Object[]) bangDuLieu[i]);
        System.out.print(lineBottom); // dong ke cuoi cung cua bang
    }

    // tim chieu rong toi thieu cho cot dua theo truong du lieu tren cot, tu dong thay the truong du lieu rong thanh dang ----- tuy vao do dai cot
    private static void xuLyDuLieuBang() {
        chieuRongCot = new int[soCot];
        for(int cot = 0; cot < soCot; cot++)
        for(int dong = 0; dong < soDong; dong++) {
            int doDaiODuLieu = bangDuLieu[dong][cot].length() + 2 * padding;
            if(chieuRongCot[cot] < doDaiODuLieu) chieuRongCot[cot] = doDaiODuLieu;
        }
        for(int dong = 0; dong < soDong; dong++)
        for(int cot = 0; cot < soCot; cot++)
            if(bangDuLieu[dong][cot].isEmpty()) bangDuLieu[dong][cot] = String.valueOf(HORIZONTAL).repeat(chieuRongCot[cot]);
    }

    // tao chuoi canh ngang cua bang, chuoi tai su dung thay vi moi lan in ra phai tao chuoi lai
    private static String makeLine(char kiTuTrai, char kiTuGiua, char kiTuPhai) {
        String dongKe = String.valueOf(kiTuTrai);
        for(int i = 0; i < soCot; i++)
            dongKe+= String.valueOf(HORIZONTAL).repeat(chieuRongCot[i]) + ((i < soCot - 1)?String.valueOf(kiTuGiua):String.valueOf(kiTuPhai));
        return dongKe + '\n';
    }

    // tao format dong du lieu voi dang nhu %-xs voi x la do rong cua cot va cac ki tu ngan cach o du lieu
    private static String taoFormatDuLieu() {
        String pad = " ".repeat(padding);
        String chuoiFormat = String.valueOf(VERTICAL);
        for(int i = 0; i < soCot; i++)
            chuoiFormat += pad + "%-" + (chieuRongCot[i] - 2*padding) + 's' + pad + String.valueOf(VERTICAL);
        return chuoiFormat + '\n';
    }
    
    // kiem tra bang co hop le hay khong
    private static boolean cauHinhVaKiemTraBang() {
        if(bangDuLieu == null) return false;
        soDong = bangDuLieu.length; soCot = bangDuLieu[0].length;
        for(int i = 0; i < soDong; i++) if(bangDuLieu[i].length < soCot) return false;
        if(soDong == 0) return false;
        return true;
    }
}
 