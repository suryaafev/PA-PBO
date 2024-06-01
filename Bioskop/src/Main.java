import java.util.Scanner;

import service.Bioskop;
import ui.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bioskop bioskop = new Bioskop();

        while (true) {
            System.out.println("=== MENU UTAMA ===");
            System.out.println("1. Menu Admin");
            System.out.println("2. Menu User");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan newline

            switch (pilihan) {
                case 1:
                    AdminMenu adminMenu = new AdminMenu(bioskop);
                    adminMenu.mulai();
                    break;
                case 2:
                    UserMenu userMenu = new UserMenu(bioskop);
                    userMenu.mulai();
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan program. Sampai jumpa!");
                    scanner.close();
                    return; // Keluar dari program
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih menu yang tersedia.");
            }
        }
    }
}
