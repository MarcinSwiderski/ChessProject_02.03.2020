package pwr.ChessProject.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *  Frame
 */

public class  Frame extends JFrame {
    /**
     *
     */
    public Frame()  {
        super("Moja apka");
        int heightOfScreen = Toolkit.getDefaultToolkit().getScreenSize().height; // pobiera wysokość ekranu
        int widthOfScreen = Toolkit.getDefaultToolkit().getScreenSize().width;  // pobiera szerokość ekranu
        this.setBounds(300, 300, 600, 400); // lokalizacja i wielkość okienka
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("pawelek.jpg")); // ikonka w lewym górnym rogu
        this.setDefaultCloseOperation(3); // Dla głównego okienka najlepiej 3, Dla komponentów 2
        this.setTitle("ChessProject");
        initComponentsLayout(); // funkcja z ułożonymi guzikami
    }

    public void initComponentsLayout() {

        GroupLayout layout = new GroupLayout(getContentPane()); //tworzenie obiektu klasy GroupLayout ( ładne układanie buttonów )

        this.getContentPane().setLayout(layout); // przypisanie var layout do contentu

        // układanie guzików
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        // układanie guzików, muszą być dwie części, horyzontalnie i wertykalnie
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup().addComponent(button1, 10 , GroupLayout.DEFAULT_SIZE, 300).addComponent(colorRed, 10 , GroupLayout.DEFAULT_SIZE, 300) // dodawanie guzika (nazwa, minimalna szerokość, szerokość podczas stworzenia, maksymalna wielkość)

                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(button2, 10 , GroupLayout.DEFAULT_SIZE, 300).addComponent(button4, 10 , GroupLayout.DEFAULT_SIZE, 300)
                        )
                        .addComponent(button3, 10 , GroupLayout.DEFAULT_SIZE, 300)
                        .addContainerGap(5, Short.MAX_VALUE)
                        .addComponent(bDecline)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup().addComponent(button1, 10 , GroupLayout.DEFAULT_SIZE, 200).addComponent(button2, 10 , GroupLayout.DEFAULT_SIZE, 200).addComponent(button3, 10 , GroupLayout.DEFAULT_SIZE, 200)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(button4, 10 , GroupLayout.DEFAULT_SIZE, 200).addComponent(colorRed, 10 , GroupLayout.DEFAULT_SIZE, 300)
                        )
                        .addContainerGap(5, Short.MAX_VALUE)
                        .addComponent(bDecline)
        );
        //pack(); //ustala wielkość okienka co do domyślnych rozmiarów buttonów

    }
    // Tworzenie panelu
    JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.TRAILING));

    // DEKLAROWANIE (TWORZENIE GUZIKÓW)
    JButton button1 = new JButton("button1");
    JButton button2 = new JButton("button2");
    JButton button3 = new JButton("button3");
    JButton button4 = new JButton("button4");
    JButton bDecline = new JButton("Decline");
    JButton colorRed = new JButton("Red");
    // JTextField

}
