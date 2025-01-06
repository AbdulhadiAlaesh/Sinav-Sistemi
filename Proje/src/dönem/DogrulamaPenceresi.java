package dönem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

import VeriTabanı_Baglantısı.veribaglanti;

public class DogrulamaPenceresi extends JFrame {

    private JPanel contentPane;
    private JTextField dogrulamaKodTextField;
    private JButton dogrulamaBtn;
    private int dogrulamaKodu;

    public DogrulamaPenceresi(int dogrulamaKodu) {
        this.dogrulamaKodu = dogrulamaKodu; // Store the received code

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 200);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblEnterCode = new JLabel("Doğrulama Kodu:");
        lblEnterCode.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEnterCode.setBounds(50, 50, 150, 30);
        contentPane.add(lblEnterCode);

        dogrulamaKodTextField = new JTextField();
        dogrulamaKodTextField.setBounds(200, 50, 120, 30);
        contentPane.add(dogrulamaKodTextField);
        dogrulamaKodTextField.setColumns(10);

        dogrulamaBtn = new JButton("Doğrula");
        dogrulamaBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        dogrulamaBtn.setBounds(150, 100, 100, 30);
        contentPane.add(dogrulamaBtn);

        dogrulamaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int enteredCode;
                try {
                    enteredCode = Integer.parseInt(dogrulamaKodTextField.getText());
                    if (enteredCode == dogrulamaKodu) {
                        JOptionPane.showMessageDialog(null, "Doğrulama Başarılı!");
                        gecikmeliPencereAc(new girisclass());
                        dispose(); // Close the window
                    } else {
                        JOptionPane.showMessageDialog(null, "Doğrulama Kodu Hatalı!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Lütfen geçerli bir kod girin!");
                }
            }
        });
    }
    
    private void gecikmeliPencereAc(JFrame yeniPencere) {
        // Şu anki pencereyi kapat
        this.setVisible(false);

        // 2 saniye bekleyip yeni pencereyi aç
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yeniPencere.setVisible(true);
            }
        });
        timer.setRepeats(false); // Sadece bir kez çalışsın
        timer.start();
    }
    
}
