package pwr.chessproject.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
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
        // this.setIconImage(Toolkit.getDefaultToolkit().getImage("pawelek.jpg")); // ikonka w lewym górnym rogu
        this.setDefaultCloseOperation(3); // Dla głównego okienka najlepiej 3, Dla komponentów 2
        this.setTitle("ChessProject");
        initComponentsLayout(); // funkcja z ułożonymi guzikamiw
        bDecline.addActionListener(e -> {
            this.hide();
        });


        ImagePanel img = new ImagePanel();
        img.setVisible(true);
        JLabel imageLabel = new JLabel(new ImageIcon(String.valueOf(img)));
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
                                layout.createParallelGroup().addComponent(labelFromXAxis, 10 , 200, 300).addComponent(xAxisFromTextField, 10 , 15, 40).addComponent(labelToXAxis, 10 , 200, 300).addComponent(xAxisToTextField, 10 , 15, 40) // dodawanie guzika (nazwa, minimalna szerokość, szerokość podczas stworzenia, maksymalna wielkość)

                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(labelFromYAxis, 10 , 200, 300).addComponent(yAxisFromTextField, 10 , 15, 40).addComponent(labelToYAxis, 10 , 200, 300).addComponent(yAxisToTextField, 10 , 15, 40)// dodawanie guzika (nazwa, minimalna szerokość, szerokość podczas stworzenia, maksymalna wielkość)

                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(bAcceptMove)
                        )
                        .addContainerGap(30, Short.MAX_VALUE)
                        .addComponent(bDecline)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup().addComponent(labelFromXAxis, 10 , 15, 40).addComponent(labelFromYAxis, 10 , 15, 40)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(xAxisFromTextField, 10 , 15, 40).addComponent(yAxisFromTextField, 10 , 15, 40)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(labelToXAxis, 10 , 15, 40).addComponent(labelToYAxis, 10 , 15, 40)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(xAxisToTextField, 10 , 15, 40).addComponent(yAxisToTextField, 10 , 15, 40).addComponent(bAcceptMove)
                        )
                        .addContainerGap(30, Short.MAX_VALUE)
                        .addComponent(bDecline)
        );
        pack(); //ustala wielkość okienka co do domyślnych rozmiarów buttonów

    }

    public class ImagePanel extends JPanel{

        public ImagePanel() {
            try {
                image = ImageIO.read(new File("szachownica.jpg"));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
        }

    }

    // Tworzenie panelu
    JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.TRAILING));

    // DEKLAROWANIE
    JButton bAcceptMove = new JButton("Accept!");
    JButton bDecline = new JButton("Decline");
    BufferedImage image;
    // "From" Labels and TextFields

    JTextField xAxisFromTextField = new JTextField("A");
    JTextField yAxisFromTextField = new JTextField("A");
     //JTextField chooseFromFigureTextField = new JTextField("A");
     //JLabel labelFromFigureName = new JLabel("Type below figure short-name!");
    JLabel labelFromXAxis = new JLabel("Type below XAxis of position");
    JLabel labelFromYAxis = new JLabel("Type below YAxis of position");

    // "To" Labels and TextFields

    JTextField xAxisToTextField = new JTextField("B");
    JTextField yAxisToTextField = new JTextField("B");
     //JTextField chooseToFigureTextField = new JTextField("B");
     //JLabel labelToFigureName = new JLabel("TO below figure short-name!");
    JLabel labelToXAxis = new JLabel("TO below XAxis of position");
    JLabel labelToYAxis = new JLabel("TO below YAxis of position");
}


