package Afvink6;

import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

public class Blok5Afvink6 extends JFrame implements ActionListener {
    public JTextField inputtextfield;
    public JTextArea outputtextarea;
    public JButton blader, analyseer;
    public JPanel panel;
    public JLabel polairlabel, apolairlabel;
    public JPanel polairpanel, apolairpanel;
    public JFrame frame;

    public static void main(String[] args) {
        Blok5Afvink6 frame = new Blok5Afvink6();
        frame.createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Afvink 6 Blok 5");
        panel = new JPanel();
        frame.getContentPane();

        //Maken van een label
        JLabel bestandlabel = new JLabel("Bestand");
        bestandlabel.setBounds(20, 20, 80, 25);
        //Einde label

        //Plaatsen van een een textfield voor de input
        inputtextfield = new JTextField("");  // initalisatie (instantiering)
        inputtextfield.setBounds(120, 20, 150, 25);
        //Einde input textfield

        //Maken van een button en actionlistener koppelen
        blader = new JButton("Blader"); //instantie
        blader.setBounds(280, 20, 100, 25);
        blader.addActionListener(this); //koppel actionlistener
        //Einde button

        //Maken van een button en actionlistener koppelen
        analyseer = new JButton("Analyseer"); //instantie
        analyseer.setBounds(390, 20, 100, 25);
        analyseer.addActionListener(this); //koppel actionlistener
        //Einde button

        //Plaatsen van een label
        JLabel informatielabel = new JLabel("Informatie");
        informatielabel.setBounds(20, 55, 80, 25);
        //Einde label

        //Plaatsen van een een textarea voor de input (voor meerdere regels te krijgen als output)
        outputtextarea = new JTextArea("");  // initalisatie (instantiering)
        outputtextarea.setBounds(120, 55, 370, 250);
        outputtextarea.setLineWrap(true);
        //Einde output textarea

        //Plaatsen van een label
        JLabel percentagelabel = new JLabel("Percentage");
        percentagelabel.setBounds(20, 320, 80, 25);
        //Einde labelx

        //Plaatsen polair en apolair panel
        polairpanel = new JPanel();
        apolairpanel = new JPanel();
        polairpanel.setBounds(130, 330, 0, 45);
        polairpanel.setBackground(Color.cyan);
        apolairpanel.setBounds(130, 385, 0, 45);
        apolairpanel.setBackground(Color.red);
        //Einde panel

        //Plaatsen polair en apolair labels
        polairlabel = new JLabel("");
        polairlabel.setBounds(140, 330, 350, 45);
        apolairlabel = new JLabel("");
        apolairlabel.setBounds(140, 385, 350, 45);
        //Einde labels

        //Toevoegen alle gegevens aan de jframe
        panel.setLayout(null);
        panel.add(bestandlabel);
        panel.add(inputtextfield);
        panel.add(blader);
        panel.add(analyseer);
        panel.add(informatielabel);
        panel.add(outputtextarea);
        panel.add(percentagelabel);
        panel.add(polairlabel);
        panel.add(apolairlabel);
        panel.add(polairpanel);
        panel.add(apolairpanel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(530, 500);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == blader) {
            JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = fc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                String bestandslocatie = selectedFile.getAbsolutePath();

                inputtextfield.setText(bestandslocatie);
            }
        }
        if (event.getSource() == analyseer) {
            StringBuilder sequentie = new StringBuilder();
            String bestandslocatie = inputtextfield.getText();

            File bestand = new File(bestandslocatie);
            Scanner bestandlezer = null;
            try {
                bestandlezer = new Scanner(bestand);
            } catch (FileNotFoundException e) {
                outputtextarea.setText("Bestand is niet gevonden. Voer een nieuw bestand in.");
            }

            while (true) {
                assert bestandlezer != null;
                if (!bestandlezer.hasNextLine()) break;
                String data = bestandlezer.nextLine();
                sequentie.append(data);
            }

            sequentie = new StringBuilder(sequentie.toString().toUpperCase());

            String[] pol = {"R", "N", "D", "C", "Q", "E", "G", "H", "K", "S", "T", "Y"};
            String[] apol = {"A", "F", "I", "L", "M", "P", "W", "V"};

            int polaircounter = 0;
            int apolaircounter = 0;

            for (int i = 0; i < sequentie.length(); i++) {
                String letter = String.valueOf(sequentie.charAt(i));
                if (Arrays.asList(pol).contains(letter)) {
                    polaircounter++;
                }
                if (Arrays.asList(apol).contains(letter)) {
                    apolaircounter++;
                }
            }

            //Breedte polair en apolair
            double totaal = sequentie.length();
            double polairtot = polaircounter;
            double apolairtot = apolaircounter;
            double polair = ((float) polairtot / totaal) * 350;
            double apolair = ((float) apolairtot / totaal) * 350;
            double polairbreedte = (polairtot / totaal) * 100;
            double apolairbreedte = (apolairtot / totaal) * 100;

            //Percentage voor label in gekleurde balk
            String polairstring = String.valueOf((int) polairbreedte);
            String apolairstring = String.valueOf((int) apolairbreedte);

            String vertaal = "";
            int error = 0;
            for (int i = 0; i < sequentie.length(); i++) {
                char temp = sequentie.charAt(i);
                String toTranslate = String.valueOf(temp);

                try {
                    vertaal = vertaal.concat("-" + Afvink5.Translator.one2three(toTranslate));

                    if (i == 0) {
                        vertaal = vertaal.substring(1);
                    }
                } catch (Exception e) {
                    vertaal = e.getMessage();
                    error = 1;
                    break;
                }
            }
            if (error == 0) {
                String outputtext = "Alle aminozuren zijn juist. Het totaal aantal aminozuren: " + sequentie.length() + ". "
                        + polairstring + "% van de aminozuren is polair en " + apolairstring + "% is apolair.";
                outputtextarea.setText(outputtext);
            } else {
                outputtextarea.setText(vertaal);
            }
            //Plaatsen polair en apolair panel
            polairpanel.setBounds(130, 330, (int) polair, 45);
            apolairpanel.setBounds(130, 385, (int) apolair, 45);
            //Einde panel

            //Plaatsen polair en apolair labels
            polairlabel.setText("Polair " + polairstring + "%");
            apolairlabel.setText("Apolair " + apolairstring + "%");
            //Einde labels
        }
    }
}