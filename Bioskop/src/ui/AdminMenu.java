package ui;

import service.Admin;
import service.Bioskop;

import java.util.Scanner;

public class AdminMenu {
    private Bioskop bioskop;

    public AdminMenu(Bioskop bioskop) {
        this.bioskop = bioskop;
    }

    public void mulai() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (Admin.cekLogin(username, password)) {
            System.out.println("Login berhasil.");
            while (true) {
                System.out.println("=== MENU ADMIN ===");
                System.out.println("1. Tambah Film");
                System.out.println("2. Hapus Film");
                System.out.println("3. Lihat Film");
                System.out.println("4. Update Film");
                System.out.println("0. Keluar");
                System.out.print("Pilih menu: ");
                int pilihan = scanner.nextInt();
                scanner.nextLine(); // Membersihkan newline
                switch (pilihan) {
                    case 1:
                        System.out.print("Judul film: ");
                        String judul = scanner.nextLine();
                        System.out.print("Genre film: ");
                        String genre = scanner.nextLine();
                        System.out.print("Sinopsis film: ");
                        String sinopsis = scanner.nextLine();
                        Admin admin = new Admin(username, password);
                        admin.tambahFilm(bioskop, judul, genre, sinopsis);
                        break;
                    case 2:
                    System.out.print("Masukkan judul film yang ingin dihapus: ");
                    String judulHapus = scanner.nextLine();
                    admin = new Admin("admin", "admin"); // Sesuaikan username dan password jika diperlukan
                    admin.hapusFilm(bioskop, judulHapus);
                    break;
                    case 3:
                    admin = new Admin(username, password);
                    admin.lihatFilm();
                    break;
                    case 4:
                    System.out.print("Masukkan judul film yang ingin diubah: ");
                    String judulUbah = scanner.nextLine();
                    System.out.print("Masukkan judul baru: ");
                    String judulBaru = scanner.nextLine();
                    System.out.print("Masukkan genre baru: ");
                    String genreBaru = scanner.nextLine();
                    System.out.print("Masukkan sinopsis baru: ");
                    String sinopsisBaru = scanner.nextLine();
                    admin = new Admin("admin", "admin"); // Sesuaikan username dan password jika diperlukan
                    admin.ubahFilm(judulUbah, judulBaru, genreBaru, sinopsisBaru);
                    break;
                    case 0:
                        return; // Keluar dari menu admin
                    default:
                        System.out.println("Pilihan tidak valid. Silakan pilih menu yang tersedia.");
                }
            }
        } else {
            System.out.println("Login gagal. Username atau password salah.");
        }
    }
}
