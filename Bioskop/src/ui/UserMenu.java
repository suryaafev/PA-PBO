package ui;

import service.Bioskop;

public class UserMenu {
    private Bioskop bioskop;

    public UserMenu(Bioskop bioskop) {
        this.bioskop = bioskop;
    }

    public void mulai() {
        bioskop.mulai();
    }
}
