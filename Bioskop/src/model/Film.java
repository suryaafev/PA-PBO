package model;

public abstract class Film {
    private String judul;
    private String genre;
    private String sinopsis;

    public Film(String judul, String genre, String sinopsis) {
        this.judul = judul;
        this.genre = genre;
        this.sinopsis = sinopsis;
    }

    public String getJudul() {
        return judul;
    }

    public String getGenre() {
        return genre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void tampilkanInformasi() {
        System.out.println("Judul: " + judul);
        System.out.println("Genre: " + genre);
        System.out.println("Sinopsis: " + sinopsis);
    }
}
