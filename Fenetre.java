import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Fenetre {

  private int longueur;
  private int largeur;
  private int nbCases;
  private char[][] plateau;
  private JTextField[] tabTextField;


  public Fenetre(int longe, int larg, char[][] tab) {
    this.longueur=longe-1;
    this.largeur=larg-1;
    this.nbCases=this.longueur * this.largeur;
    tabTextField = new JTextField[nbCases];
    plateau = new char[longueur][largeur];
    plateau = tab;
  }

  public void initialiserFenetre() {
    JFrame t = new JFrame();
    JPanel pan = new JPanel (new GridLayout (longueur,largeur));
    LineBorder line = new LineBorder(Color.BLACK);
    Font font1 = new Font("SansSerif", Font.BOLD, 20);
    char tmp;
    int ligne;
    int colonne;
    for(int i = 0; i<nbCases;i++) {
      tabTextField[i] = new JTextField();
      tabTextField[i].setBorder(line);
        tabTextField[i].setFont(font1);
      tabTextField[i].setHorizontalAlignment(JTextField.CENTER);
      ligne = i/longueur;
      colonne = i%longueur;
      tabTextField[i].setText(Character.toString(plateau[ligne][colonne]));
      pan.add(tabTextField[i]);
    }
    pan.setBorder(line);
    t.add(pan);
    t.setSize(500,400);
    t.setVisible(true);
  }
}
