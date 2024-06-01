package service;

import database.Database;
import model.Film;
import model.FilmAksi;
import model.FilmDrama;
import model.FilmKomedi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static boolean cekLogin(String username, String password) {
        // For simplicity, just check if the username and password are "admin"
        return "admin".equals(username) && "admin".equals(password);
    }

    public void tambahFilm(Bioskop bioskop, String judul, String genre, String sinopsis) {
        Film film;
        switch (genre.toLowerCase()) {
            case "aksi":
                film = new FilmAksi(judul, sinopsis);
                break;
            case "komedi":
                film = new FilmKomedi(judul, sinopsis);
                break;
            case "drama":
                film = new FilmDrama(judul, sinopsis);
                break;
            default:
                film = new Film(judul, genre, sinopsis) {};
                break;
        }
        bioskop.tambahFilm(film);

        // Simpan film ke database
        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO film (judul, genre, sinopsis) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, judul);
            stmt.setString(2, genre);
            stmt.setString(3, sinopsis);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Film \"" + judul + "\" berhasil ditambahkan.");
    }

    public void hapusFilm(Bioskop bioskop, String judul) {
        // Hapus film dari database
        try (Connection conn = Database.getConnection()) {
            String query = "DELETE FROM film WHERE judul = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, judul);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Hapus film dari daftar film di bioskop
                bioskop.hapusFilm(judul);
                System.out.println("Film \"" + judul + "\" berhasil dihapus.");
            } else {
                System.out.println("Film \"" + judul + "\" tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void lihatFilm() {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM film";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            System.out.println("=== DAFTAR FILM ===");
            while (rs.next()) {
                String judul = rs.getString("judul");
                String genre = rs.getString("genre");
                String sinopsis = rs.getString("sinopsis");
                System.out.println("Judul: " + judul);
                System.out.println("Genre: " + genre);
                System.out.println("Sinopsis: " + sinopsis);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ubahFilm(String judulFilm, String judulBaru, String genreBaru, String sinopsisBaru) {
        try (Connection conn = Database.getConnection()) {
            // Periksa apakah film dengan judul yang diberikan ada dalam database
            String cekQuery = "SELECT COUNT(*) AS count FROM film WHERE judul = ?";
            PreparedStatement cekStmt = conn.prepareStatement(cekQuery);
            cekStmt.setString(1, judulFilm);
            ResultSet cekResult = cekStmt.executeQuery();
            cekResult.next();
            int count = cekResult.getInt("count");
            if (count == 0) {
                System.out.println("Film dengan judul '" + judulFilm + "' tidak ditemukan.");
                return;
            }
    
            // Jika film ditemukan, lakukan pembaruan detail film
            String updateQuery = "UPDATE film SET judul = ?, genre = ?, sinopsis = ? WHERE judul = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setString(1, judulBaru);
            updateStmt.setString(2, genreBaru);
            updateStmt.setString(3, sinopsisBaru);
            updateStmt.setString(4, judulFilm);
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Film dengan judul '" + judulFilm + "' berhasil diperbarui.");
            } else {
                System.out.println("Gagal memperbarui film dengan judul '" + judulFilm + "'.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}

