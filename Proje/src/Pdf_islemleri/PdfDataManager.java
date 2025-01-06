// PdfDataManager sınıfını düzenleyin
package Pdf_islemleri;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PdfDataManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<PdfData> pdfList;

    public PdfDataManager() {
        this.pdfList = new ArrayList<>();
        loadPdfData(); // Başlatıldığında veriyi yükler
    }

    // Yeni bir PDF ekler
    public void addPdf(PdfData pdf) {
        pdfList.add(pdf);
        savePdfData(); // Her PDF eklendiğinde veriyi dosyaya kaydeder
    }

    // PDF'nin listede olup olmadığını kontrol eder
    public boolean containsPdf(String pdfName) {
        return pdfList.stream().anyMatch(pdf -> pdf.getPdfName().equals(pdfName));
    }

    // PDF verisini dosyaya kaydeder
    private void savePdfData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/pdf_ds/pdfData.ser"))) {
            oos.writeObject(pdfList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // PDF verisini dosyadan okur
    private void loadPdfData() {
        File file = new File("src/pdf_ds/pdfData.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                pdfList = (List<PdfData>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // PDF listesini döndürür
    public List<PdfData> getPdfList() {
        return pdfList;
    }
}
