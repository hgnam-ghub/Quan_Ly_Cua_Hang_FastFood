package quanly;

import java.util.Scanner;

public final class NhapChuan 
{
    private static Scanner SCANNER = new Scanner(System.in);
    private NhapChuan() {};

    public static String docChuoi(String prompt) {
        System.out.print(prompt + ": ");
        return SCANNER.nextLine().trim();
    }

    // đọc 1 chuỗi và bắt buộc chuỗi đó không rỗng
    public static String docChuoiKhongRong(String prompt) {
        while (true) 
        {
            String input = docChuoi(prompt);
            if (!input.isEmpty())
                return input;
        }
    }

    public static int docSoNguyen(String prompt) {
        while (true) 
        {
            System.out.print(prompt + ": ");
            try 
            {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } 
            catch (NumberFormatException e) 
            {
                System.out.println(">> Please enter an integer");
            }
        }
    }

    public static int docSoNguyenKhongAm(String prompt) {
        while (true) 
        {
            int val = docSoNguyen(prompt);
            if (val >= 0) return val;
                System.out.println(">> Please enter a non-negative integer");
        }
    }

    public static double docSoThuc(String prompt) {
        while (true) 
        {
            System.out.print(prompt + ": ");
            try 
            {
                return Double.parseDouble(SCANNER.nextLine().trim());
            } 
            catch (NumberFormatException e) 
            {
                System.out.println(">> Please enter an decimal number");
            }
        }
    }

    public static double docSoThucKhongAm(String prompt) {
        while (true) 
        {
            double val = docSoThuc(prompt);
            if (val >= 0) return val;
                System.out.println(">> Please enter a non-negative decimal number");
        }
    }

    // đọc và trả vè đúng hoặc sai, người dùng nhập yes/no, y/n
    // defaultValue sẽ quyết định khi người dùng không lựa chọn thì mặc định sẽ chọn giá trị nào
    public static boolean docYesNo(String prompt, boolean defaultValue) {
        String hint = defaultValue ? " [Y/n]: " : " [y/N]: ";
        while (true) 
        {
            System.out.print(prompt + hint);
            String input = SCANNER.nextLine().trim().toLowerCase();

            if (input.isEmpty())
                return defaultValue;

            if (input.equals("y") || input.equals("yes"))
                return true;
            if (input.equals("n") || input.equals("no")) 
                return false;

            System.out.println(">> Please enter (Yes/No) or (y/n)!");
        }
    }
}
