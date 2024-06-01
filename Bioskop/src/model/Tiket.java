package model;

public class Tiket {
    private Film film;
    private int nomorKursi;

    public Tiket(Film film, int nomorKursi) {
        this.film = film;
        this.nomorKursi = nomorKursi;
    }

    public Film getFilm() {
        return film;
    }

    public int getNomorKursi() {
        return nomorKursi;
    }
}
