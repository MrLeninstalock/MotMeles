import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.util.*;

public class Fenetre implements MouseListener {

  private int longueur;
  private int largeur;
  private int nbCases;
  private char[][] plateau;
  private JTextField[] tabTextField;
  private JTextField[] tabMot;
  private LinkedList<Mot> liste;

  private JPanel pan;

  private boolean premierClick;
  private int Ox;
  private int Oy;
  private int Dx;
  private int Dy;



  public Fenetre(int longe, int larg, char[][] tab, LinkedList<Mot> listeMot)  {
    this.longueur=longe-1;
    this.largeur=larg-1;
    premierClick = true;
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
    pan = new JPanel (new GridLayout (longueur,largeur));

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
      tabTextField[index].addMouseListener(this);
      ligne = index/longueur;
      colonne = index%longueur;
      tabTextField[index].setText(Character.toString(plateau[ligne][colonne]));
      pan.add(tabTextField[index]);
    }
  }

  public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseClicked(MouseEvent e) {
     JTextField tmp = (JTextField)e.getSource();
     if(premierClick) {
       int x, y, lacase, locase;
       lacase = pan.getWidth()/longueur;
       locase = pan.getHeight()/largeur;
       x = (e.getX()+tmp.getX())/lacase;
       y = (e.getY()+tmp.getY())/locase;
       Ox = x;
       Oy = y;
       System.out.println("Case : "+x+" "+y);
       premierClick = false;
     } else {
       int dx, dy, lacase, locase;
       lacase = pan.getWidth()/longueur;
       locase = pan.getHeight()/largeur;
       x = (e.getX()+tmp.getX())/lacase;
       y = (e.getY()+tmp.getY())/locase;
       Dx = x;
       Dy = y;
       if(verifierClick()) {
         verifierMot();
       }
     }
   }

   public boolean verifierClick() {
     if(Ox!=Dx && Oy!=Dy) {
       if((Dx-Ox)==(Dy-Do)) {
         return true;
       }
     }
   }

   public boolean verifierMot() {
     String mot ="";
     if(Ox==Dx && Oy!=Dy) {
       for(int i=0; i<Dx-Ox; i++) {
         mot+=plateau[Ox+i][Oy];
       }
     }
   }
}
