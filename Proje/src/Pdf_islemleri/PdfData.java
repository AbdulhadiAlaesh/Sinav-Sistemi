package Pdf_islemleri;

public class PdfData {
    private String dersAdi;
    private String konuAdi;
    private String pdfName;

    // Constructor
    public PdfData(String dersAdi, String konuAdi, String pdfName) {
        this.dersAdi = dersAdi;
        this.konuAdi = konuAdi;
        this.pdfName = pdfName;
    }

    // Getter ve Setter metodları
    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public String getKonuAdi() {
        return konuAdi;
    }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }
}
