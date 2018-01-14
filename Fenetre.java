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
  private int AbscisseOrigine;
  private int AbscisseDestination;
  private int OrdonneOrigine;
  private int OrdonneDestination;

  private double tempsDebut;
  private double tempsFin;


  public Fenetre(int longe, int larg, char[][] tab, LinkedList<Mot> listeMot)  {
    tempsDebut = System.currentTimeMillis();
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
   int abscisse, ordonne, lacase, locase;
   if(premierClick) {

     lacase = pan.getWidth()/longueur;
     locase = pan.getHeight()/largeur;
     abscisse =(e.getX()+tmp.getX())/lacase;
     ordonne =(e.getY()+tmp.getY())/locase;
     AbscisseOrigine = abscisse;
     OrdonneOrigine = ordonne;
     premierClick = false;
   } else {
     lacase = pan.getWidth()/longueur;
     locase = pan.getHeight()/largeur;
     abscisse =(e.getX()+tmp.getX())/lacase;
     ordonne =(e.getY()+tmp.getY())/locase;
     AbscisseDestination = abscisse;
     OrdonneDestination = ordonne;
     if(verifierClick()) {
       verifierMot();
     }
   }
  }

  public boolean verifierClick() {
   if(AbscisseOrigine!=AbscisseDestination && OrdonneOrigine!=OrdonneDestination) {
     if((AbscisseOrigine-AbscisseDestination)==(OrdonneDestination-OrdonneOrigine)) {
       return true;
     } else {
       return false;
     }
   }
   return true;
  }

  public boolean motVertical() {
    if(AbscisseOrigine==AbscisseDestination && OrdonneOrigine!=OrdonneDestination) {
      System.out.println("Mot vertical");
      return true;
    } else {
      return false;
    }
  }

  public boolean motHorizontal() {
    if(AbscisseOrigine!=AbscisseDestination && OrdonneOrigine==OrdonneDestination) {
      System.out.println("Mot horizontal");
      return true;
    } else {
      return false;
    }
  }

  public boolean motExiste(String mot) {
   int i;
   for(i =0; i<liste.size(); i++) {
     if(mot.equals(liste.get(i).getMot())) {
       System.out.println("On a trouvé un mot !");
       premierClick=true;
       validerMot(i);
       return true;
     }
   }
   System.out.println("Non ce n'est pas un mot valide !");
   premierClick=true;
   return false;
  }

  public String collecterMotHorizontal() {
   int i;
   int longueurMot = AbscisseDestination-AbscisseOrigine+1;
   String mot = "";
   for(i=0; i<longueurMot; i++) {
     mot+=plateau[OrdonneOrigine][AbscisseOrigine+i];
   }
   return mot;
  }

  public String collecterMotVertical() {
   String mot = "";
   int i;
   int longueurMot = OrdonneDestination-OrdonneOrigine+1;
   for(i=0; i<longueurMot; i++) {
     mot+=plateau[OrdonneOrigine+i][AbscisseOrigine];
   }
   return mot;
  }

  public String collecterMotDiagonal() {
   String mot = "";
   int i;
   int longueurMot = OrdonneDestination-OrdonneOrigine+1;
   for(i=0; i<longueurMot; i++) {
     mot+=plateau[OrdonneOrigine+i][AbscisseOrigine+i];
   }
   return mot;
  }

  public boolean verifierMot() {
   String mot ="";
   if(motHorizontal()) {
     mot = collecterMotHorizontal();
     if(motExiste(mot)) {
       return true;
     } else {
       return false;
     }
   } else if(motVertical()) {
     mot = collecterMotVertical();
     if(motExiste(mot)) {
       return true;
     } else {
       return false;
     }
   } else {
     mot = collecterMotDiagonal();
     if(motExiste(mot)) {
       return true;
     } else {
       return false;
     }
   }
  }

  public void validerMot(int index) {
     Mot mot = liste.get(index);
     if(mot.getOrientation() == 1){
       for(int i=0; i<mot.getTaille(); i++) {
         tabTextField[mot.getX() * largeur + mot.getY()+i].setBackground(new Color (128, 128, 0));
       }
     } else if(mot.getOrientation() == 2) {
       for(int i=0; i<mot.getTaille(); i++) {
         tabTextField[(mot.getX()+i) * largeur + mot.getY()].setBackground(new Color (128, 128, 0));
       }
     } else {
       for(int i=0; i<mot.getTaille(); i++) {
         tabTextField[(mot.getX()+i) * largeur + mot.getY()+i].setBackground(new Color (128, 128, 0));
       }
     }
     retirerMotListe(index);
   }

   public void retirerMotListe(int index) {
     liste.remove(index);
     if(liste.size()==0) {
       gagner();
     }
   }

   public void gagner() {
     System.out.println("GG mon ptit pote !");
     tempsFin = System.currentTimeMillis();
     System.out.println("Tu as mis :"+ (tempsFin - tempsDebut)/1000+"s");
   }

}
