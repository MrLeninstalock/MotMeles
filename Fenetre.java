import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.util.*;

public class Fenetre {

  private int longueur;
  private int largeur;
  private int nbCases;
  private char[][] plateau;
  private JTextField[] tabTextField;
  private JTextField[] tabMot;
  private LinkedList<Mot> liste;


  public Fenetre(int longe, int larg, char[][] tab, LinkedList<Mot> listeMot) {
    this.longueur=longe-1;
    this.largeur=larg-1;
    this.nbCases=this.longueur * this.largeur;
    tabTextField = new JTextField[nbCases];
    plateau = new char[longueur][largeur];
    plateau = tab;
    this.liste = new LinkedList<Mot>();
    liste = listeMot;
  }

  public void initialiserFenetre() {
    JFrame t = new JFrame();
    JPanel panel = new JPanel(new BorderLayout());
    JPanel pan = new JPanel (new GridLayout (longueur,largeur));

    JPanel legende = new JPanel();
    panel.add(legende, BorderLayout.SOUTH);

    tabMot = new JTextField[liste.size()];

    insererMotLegende(legende);
    insererMotGrille(pan);

    panel.add(pan, BorderLayout.CENTER);
    t.add(panel);
    t.setSize(500,400);
    t.setVisible(true);
  }

  public void insererMotLegende(JPanel legende) {
    for(int i = 0; i < liste.size(); i++) {
      tabMot[i] = new JTextField(liste.get(i).getMot());
      legende.add(tabMot[i]);
    }
  }

  public void insererMotGrille(JPanel pan) {
    int ligne, colonne;
    LineBorder line = new LineBorder(Color.BLACK);
    for(int index=0; index<nbCases; index++) {
      tabTextField[index] = new JTextField();
      tabTextField[index].setBorder(line);
      tabTextField[index].setFont(new Font("SansSerif", Font.BOLD, 20));
      tabTextField[index].setBackground(new Color (128, 0, 0));
      tabTextField[index].setHorizontalAlignment(JTextField.CENTER);
      ligne = index/longueur;
      colonne = index%longueur;
      tabTextField[index].setText(Character.toString(plateau[ligne][colonne]));
      pan.add(tabTextField[index]);
    }
  }
}
