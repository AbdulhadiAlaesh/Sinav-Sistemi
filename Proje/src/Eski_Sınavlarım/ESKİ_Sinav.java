package Eski_Sınavlarım;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import VeriTabanı_Baglantısı.veribaglanti;
import dönem.HocaPanel;

public class ESKİ_Sinav extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JTextField ARAtextField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ESKİ_Sinav frame = new ESKİ_Sinav();
                    frame.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Uygulama başlatılırken bir hata oluştu: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public ESKİ_Sinav() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 831, 658);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(51, 204, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Ders Adı", "Dosya Yolu", "Dosya Tipi"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Tabloda hücre düzenlemesini kapatıyoruz
            }
        });
        table.setBounds(20, 149, 738, 462);
        contentPane.add(table);

        ARAtextField = new JTextField();
        ARAtextField.setBounds(61, 56, 283, 33);
        contentPane.add(ARAtextField);
        ARAtextField.setColumns(10);

        JButton ArabtnNewButton = new JButton("Ara");
        ArabtnNewButton.setBounds(375, 62, 85, 21);
        contentPane.add(ArabtnNewButton);

        ArabtnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String aramaMetni = ARAtextField.getText();
                    if (!aramaMetni.isEmpty()) {
                        searchAndLoadData(aramaMetni);
                    } else {
                        getSinavlarData();
                    }
                } catch (Exception ex) {
                    System.err.println("Arama sırasında bir hata oluştu: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        JButton btnNewButton_2 = new JButton("SİL");
        btnNewButton_2.setBounds(511, 62, 85, 21);
        contentPane.add(btnNewButton_2);
        
        JButton GeribtnNewButton_2_1 = new JButton("Geri");
        GeribtnNewButton_2_1.setBounds(643, 62, 85, 21);
        contentPane.add(GeribtnNewButton_2_1);
        
        GeribtnNewButton_2_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HocaPanel hocaPanel = new HocaPanel();
			    hocaPanel.setVisible(true);
			    setVisible(false);
				
			}
		});
        
        
        

        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Tablodaki seçili satırdaki verileri alıyoruz
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        String dersAdi = (String) model.getValueAt(selectedRow, 0); // Ders Adı

                        // Veritabanından bu ders adını kullanarak silme işlemi yapıyoruz
                        deleteFromDatabase(dersAdi);

                        // Tabloyu güncelliyoruz
                        model.removeRow(selectedRow);

                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Lütfen bir satır seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    System.err.println("Silme sırasında bir hata oluştu: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        try {
            getSinavlarData();
        } catch (Exception e) {
            System.err.println("Tabloyu yüklerken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getSinavlarData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection connection = veribaglanti.getConnection()) {
            String query = "SELECT ders_adi, dosya_yolu_1, dosya_tipi FROM Sinavlar";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String dersAdi = rs.getString("ders_adi");
                String dosyaYolu = rs.getString("dosya_yolu_1");
                String dosyaTipi = rs.getString("dosya_tipi");
                model.addRow(new Object[]{dersAdi, dosyaYolu, dosyaTipi});
            }
        } catch (SQLException e) {
            System.err.println("Veritabanından veri çekerken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void searchAndLoadData(String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection conn = veribaglanti.getConnection()) {
            String sql = "SELECT ders_adi, dosya_yolu_1, dosya_tipi FROM Sinavlar WHERE ders_adi LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + searchText + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String dersAdi = rs.getString("ders_adi");
                String dosyaYolu = rs.getString("dosya_yolu_1");
                String dosyaTipi = rs.getString("dosya_tipi");
                model.addRow(new Object[]{dersAdi, dosyaYolu, dosyaTipi});
            }
        } catch (SQLException e) {
            System.err.println("Arama sırasında bir SQL hatası oluştu: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteFromDatabase(String dersAdi) {
        try (Connection conn = veribaglanti.getConnection()) {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Veritabanı bağlantısı sağlanamadı.");
            }

            // Silme sorgusu
            String sql = "DELETE FROM Sinavlar WHERE LOWER(ders_adi) = LOWER(?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, dersAdi); // Silinecek ders adı

                int rowsDeleted = stmt.executeUpdate(); // Sorguyu çalıştırma
                System.out.println("Silinen Satır Sayısı: " + rowsDeleted);

                if (rowsDeleted > 0) {
                    System.out.println("Veritabanı başarıyla güncellendi!");
                } else {
                    JOptionPane.showMessageDialog(contentPane, 
                        "Ders Adı bulunamadı. Lütfen seçim yaptığınızdan emin olun.", 
                        "Hata", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL hatası: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
