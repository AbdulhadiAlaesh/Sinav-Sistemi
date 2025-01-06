package Alısıtrma;
public class Soru {
    private String icerik;
    private String[] secenekler;
    private String dogruSik;

    public Soru(String icerik, String[] secenekler, String dogruSik) {
        this.icerik = icerik;
        this.secenekler = secenekler;
        this.dogruSik = dogruSik;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String[] getSecenekler() {
        return secenekler;
    }

    public void setSecenekler(String[] secenekler) {
        this.secenekler = secenekler;
    }

    public String getDogruSik() {
        return dogruSik;
    }

    public void setDogruSik(String dogruSik) {
        this.dogruSik = dogruSik;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Soru: ").append(icerik).append("\n");
        for (int i = 0; i < secenekler.length; i++) {
            sb.append((char) ('A' + i)).append(") ").append(secenekler[i]).append("\n");
        }
        sb.append("Doğru Şık: ").append(dogruSik).append("\n");
        return sb.toString();
    }
}
