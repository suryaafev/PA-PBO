package service;

import database.Database;
import model.Film;
import model.Tiket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Bioskop {
    private List<Film> daftarFilm;
    private List<Integer> kursiDipesan;
    private List<Tiket> tiketDipesan;

    public Bioskop() {
        daftarFilm = new ArrayList<>();
        kursiDipesan = new ArrayList<>();
        tiketDipesan = new ArrayList<>();
        loadFilmsFromDatabase();
    }

    private void loadFilmsFromDatabase() {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM film";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String judul = rs.getString("judul");
                String genre = rs.getString("genre");
                String sinopsis = rs.getString("sinopsis");
                daftarFilm.add(new Film(judul, genre, sinopsis) {});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tambahFilm(Film film) {
        daftarFilm.add(film);
    }

    public void hapusFilm(String judul) {
        daftarFilm.removeIf(film -> film.getJudul().equalsIgnoreCase(judul));
    }

    

    public void mulai() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean selesai = false;
            while (!selesai) {
                tampilkanMenu();
                System.out.print("Pilih menu: ");
                try {
                    int pilihan = scanner.nextInt();
                    switch (pilihan) {
                        case 1:
                            tampilkanDaftarFilm();
                            break;
                        case 2:
                            System.out.print("Masukkan nomor film: ");
                            int nomorFilm = scanner.nextInt();
                            scanner.nextLine(); // Membersihkan newline
                            tampilkanDetailFilm(nomorFilm);
                            break;
                        case 3:
                            System.out.print("Masukkan nomor film: ");
                            int nomorFilmPesan = scanner.nextInt();
                            System.out.print("Masukkan nomor kursi: ");
                            int nomorKursiPesan = scanner.nextInt();
                            scanner.nextLine(); // Membersihkan newline
                            pesanTiket(nomorFilmPesan, nomorKursiPesan);
                            break;
                        case 4:
                            System.out.print("Masukkan nomor kursi yang ingin dibatalkan: ");
                            int nomorKursiBatalkan = scanner.nextInt();
                            scanner.nextLine(); // Membersihkan newline
                            batalkanTiket(nomorKursiBatalkan);
                            break;
                        case 5:
                            System.out.print("Masukkan nomor film untuk memilih kursi: ");
                            int nomorFilmKursi = scanner.nextInt();
                            scanner.nextLine(); // Membersihkan newline
                            pilihKursi(nomorFilmKursi);
                            break;
                        case 6:
                            tampilkanTiket();
                            break;
                        case 7:
                            selesai = true;
                            break;
                        default:
                            System.out.println("Pilihan tidak valid.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input tidak valid. Harap masukkan angka.");
                    scanner.nextLine(); // Membersihkan input yang salah
                }
            }
        }
    }

    private void tampilkanMenu() {
        System.out.println("=== MENU BIOSKOP ===");
        System.out.println("1. Tampilkan Daftar Film");
        System.out.println("2. Tampilkan Detail Film");
        System.out.println("3. Pesan Tiket");
        System.out.println("4. Batalkan Tiket");
        System.out.println("5. Pilih Kursi");
        System.out.println("6. Tampilkan Tiket Saya");
        System.out.println("7. Keluar");
    }

    private void tampilkanDaftarFilm() {
        System.out.println("=== DAFTAR FILM ===");
        if (daftarFilm.isEmpty()) {
            System.out.println("Tidak ada film yang tersedia.");
        } else {
            for (int i = 0; i < daftarFilm.size(); i++) {
                Film film = daftarFilm.get(i);
                System.out.println((i + 1) + ". " + film.getJudul());
                System.out.println("   Genre: " + film.getGenre());
                System.out.println("   Sinopsis: " + film.getSinopsis());
                System.out.println("---------------------------------------");
            }
        }
    }

    private void tampilkanDetailFilm(int nomorFilm) {
        if (nomorFilm < 1 || nomorFilm > daftarFilm.size()) {
            System.out.println("Nomor film tidak valid.");
        } else {
            Film film = daftarFilm.get(nomorFilm - 1);
            System.out.println("=== DETAIL FILM ===");
            System.out.println("Judul: " + film.getJudul());
            System.out.println("Genre: " + film.getGenre());
            System.out.println("Sinopsis: " + film.getSinopsis());
        }
    }

    private void pesanTiket(int nomorFilm, int nomorKursi) {
        if (nomorFilm < 1 || nomorFilm > daftarFilm.size()) {
            System.out.println("Nomor film tidak valid.");
            return;
        }
        if (kursiDipesan.contains(nomorKursi)) {
            System.out.println("Kursi sudah dipesan.");
            return;
        }
        Film film = daftarFilm.get(nomorFilm - 1);
        kursiDipesan.add(nomorKursi);
        tiketDipesan.add(new Tiket(film, nomorKursi));
        System.out.println("Tiket berhasil dipesan untuk film \"" + film.getJudul() + "\" di kursi nomor " + nomorKursi + ".");
    }

    private void batalkanTiket(int nomorKursi) {
        boolean tiketDitemukan = false;
        for (int i = 0; i < tiketDipesan.size(); i++) {
            if (tiketDipesan.get(i).getNomorKursi() == nomorKursi) {
                tiketDipesan.remove(i);
                kursiDipesan.remove(Integer.valueOf(nomorKursi));
                System.out.println("Tiket untuk kursi nomor " + nomorKursi + " berhasil dibatalkan.");
                tiketDitemukan = true;
                break;
            }
        }
        if (!tiketDitemukan) {
            System.out.println("Tiket untuk kursi nomor " + nomorKursi + " tidak ditemukan.");
        }
    }

    private void pilihKursi(int nomorFilm) {
        if (nomorFilm < 1 || nomorFilm > daftarFilm.size()) {
            System.out.println("Nomor film tidak valid.");
            return;
        }
        Film film = daftarFilm.get(nomorFilm - 1);
        System.out.println("Memilih kursi untuk film \"" + film.getJudul() + "\":");
        for (int i = 1; i <= 30; i++) {
            if (kursiDipesan.contains(i)) {
                System.out.print("[X] ");
            } else {
                System.out.print("[" + i + "] ");
            }
            if (i % 10 == 0) {
                System.out.println();
            }
        }
    }

    private void tampilkanTiket() {
        if (tiketDipesan.isEmpty()) {
            System.out.println("Tidak ada tiket yang dipesan.");
            return;
        }
        for (Tiket tiket : tiketDipesan) {
            System.out.println("Film: " + tiket.getFilm().getJudul() + ", Kursi: " + tiket.getNomorKursi());
        }
    }
}
