package tienich;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Scanner;

public final class NhapChuan 
{
    private static Scanner SCANNER = new Scanner(System.in);
    private NhapChuan() {};

    public static String docChuoi(String prompt) {
        System.out.print(prompt + ": ");
        return SCANNER.nextLine().trim();
    }
    public static LocalDate docNgay(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = SCANNER.nextLine().trim();
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println(">> Ngay khong hop le. Vui long nhap theo dinh dang dd/MM/yyyy.");
            }
        }
    }

    public static int nhapLuaChonMenu() {
        System.out.print(">> ");
        try
        {
            return Integer.parseInt(SCANNER.nextLine().trim());
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
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
                System.out.println(">> Phai nhap 1 so nguyen");
            }
        }
    }

    public static int docSoNguyenKhongAm(String prompt) {
        while (true) 
        {
            int val = docSoNguyen(prompt);
            if (val >= 0) return val;
                System.out.println(">> Phai nhap so nguyen khong am");
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
                System.out.println(">> phai nhap 1 so thuc");
            }
        }
    }

    public static double docSoThucKhongAm(String prompt) {
        while (true) 
        {
            double val = docSoThuc(prompt);
            if (val >= 0) return val;
                System.out.println(">> Phai nhap 1 so thuc khong am");
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

            System.out.println(">> Phai nhap voi dang (Yes/No) hoac (y/n)!");
        }
    }
}
