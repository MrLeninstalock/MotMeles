import java.lang.Math;
import java.io.*;

public class MoteurJeu {

  char[][] plateau;
  int longueur;
  int largeur;
  int nombreMotPossible;

  int lower = 97;
  int higher = 122;

  public MoteurJeu(int longueur, int largeur) throws IOException{
    nombreMotPossible = 0;
    String line;
    this.longueur = longueur;
    this.largeur = largeur;
    this.plateau = new char[longueur][largeur];
    File f = new File("dico.txt");
    BufferedReader br = new BufferedReader(new FileReader(f));
    while ((line = br.readLine()) != null) {
      nombreMotPossible++;
    }
    br.close();
  }

  public String lireMot() throws IOException {
    String line;
    int i = 0;
    int positionMot = (int)(Math.random() * (nombreMotPossible-0)) + 0;
    File f = new File("dico.txt");
    BufferedReader br = new BufferedReader(new FileReader(f));
    while ((line = br.readLine()) != null && i<=positionMot) {
      i++;
    }
    br.close();
    return line;
  }

  public void initialiserGrilleRandom() {
    int tmp;
    for(int i =0; i<this.longueur-1; i++) {
      for(int j = 0; j<this.largeur-1; j++) {
        tmp = (int)(Math.random() * (higher-lower)) + lower;
        plateau[i][j]=(char)tmp;
      }
    }
  }

  public void initialiserGrille() {
    for(int i =0; i<this.longueur-1; i++) {
      for(int j = 0; j<this.largeur-1; j++) {
        plateau[i][j]='-';
      }
    }
  }

  public void afficherGrille() {
    for(int i =0; i<this.longueur-1; i++) {
      System.out.println("");
      for(int j = 0; j<this.largeur-1; j++) {
        System.out.print(plateau[i][j]);
      }
    }
    System.out.println("-----");
  }

  public boolean placerMotOrdonnes(String mot) {
    if(mot==null) {
      return false;
    }
    char[] tab = mot.toCharArray();
    int orientation = (int)(Math.random() * 3)+1;
    for(int i =0; i<this.longueur-1; i++) {
      for(int j = 0; j<this.largeur-1; j++) {
        if(leMotPeutEtreEcrit(mot, orientation, i, j)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean placerMotHasard(String mot) {
    if(mot==null) {
      return false;
    }
    char[] tab = mot.toCharArray();
    int orientation = (int)(Math.random() * 3)+1;
    int i = (int)(Math.random() *longueur);
    int j = (int)(Math.random() *largeur);
    int nombreTentative = 0;
    int nombreTentativeMax = longueur*largeur;
    while(!leMotPeutEtreEcrit(mot, orientation, i, j)) {
      i = (int)(Math.random() *longueur);
      j = (int)(Math.random() *largeur);
      nombreTentative++;
      if(nombreTentative == nombreTentativeMax) {
        return false;
      }
    }
    return true;
  }

  public boolean motNeDepassePas(char[] tab, int orientation, int x, int y) {
    if(orientation == 1) {
      if(y+tab.length >= this.largeur) {
        return false;
      }
    } else if(orientation == 2) {
      if(x+tab.length >= this.longueur) {
        return false;
      }
    } else if(orientation == 3) {
      if(x+tab.length >= this.longueur || y+tab.length >= this.largeur) {
        return false;
      }
    }
    return true;
  }

  public boolean leMotPeutEtreEcrit(String mot, int orientation, int x, int y) {
    char[] tab = mot.toCharArray();
    if(!motNeDepassePas(tab, orientation, x, y)) {
      return false;
    }
    if(orientation == 1) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x][y+i] == tab[i] || plateau[x][y+i] == '-') {
        } else {
          return false;
        }
      }
      ecrireMot(1, tab, x, y);
      return true;
    } else if (orientation == 2) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x+i][y] == tab[i] || plateau[x+i][y] == '-') {
        } else {
          return false;
        }
      }
      ecrireMot(2, tab, x, y);
      return true;
    } else if (orientation == 3) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x+i][y+i] == tab[i] || plateau[x+i][y+i] == '-') {
        } else {
          return false;
        }
      }
      ecrireMot(3, tab, x, y);
      return true;
    }
    return false;
  }

  public void remplirDeMots(int nombreDeMots) throws IOException{
    while(grilleNonRemplie()) {
      if(placerMotHasard(lireMot())) {
        nombreDeMots--;
        if(nombreDeMots==0) {
          break;
        }
      }
    }
  }

  public void ecrireMot(int orientation, char[] tab, int x, int y) {
    System.out.println("On a mis le mot " + new String(tab));
    if(orientation == 1) {
      for(int i=0; i<tab.length; i++) {
        plateau[x][y+i]=tab[i];
      }
    } else if(orientation == 2) {
      for(int i=0; i<tab.length; i++) {
        plateau[x+i][y]=tab[i];
      }
    } else if(orientation == 3) {
      for(int i=0; i<tab.length; i++) {
        plateau[x+i][y+i]=tab[i];
      }
    } else {
      System.out.println("MAUVAISE ORIENTATION");
    }
  }

  public boolean grilleNonRemplie() {
    for(int i =0; i<this.longueur-1; i++) {
      for(int j = 0; j<this.largeur-1; j++) {
        if(plateau[i][j] == '-') {
          return true;
        }
      }
    }
    return true;
  }

  public void finirRemplissage() {
    int tmp;
    for(int i =0; i<this.longueur-1; i++) {
      for(int j = 0; j<this.largeur-1; j++) {
        if(plateau[i][j]=='-') {
          tmp = (int)(Math.random() * (higher-lower)) + lower;
          plateau[i][j]=(char)tmp;
        }
      }
    }
  }
}
