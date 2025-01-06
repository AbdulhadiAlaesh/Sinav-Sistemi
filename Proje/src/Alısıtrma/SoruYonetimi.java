package Alısıtrma;

import java.util.ArrayList;

public class SoruYonetimi {
    private ArrayList<Soru> sorular;

    public SoruYonetimi() {
        sorular = new ArrayList<>();
    }

    public void soruEkle(Soru soru) {
        sorular.add(soru);
    }

    public ArrayList<Soru> getSorular() {
        return sorular;
    }

    public void temizle() {
        sorular.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Soru soru : sorular) {
            sb.append(soru.toString()).append("\n");
        }
        return sb.toString();
    }
}
