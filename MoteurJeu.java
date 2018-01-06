import java.lang.Math;
import java.io.*;
import java.util.*;

public class MoteurJeu {

  char[][] plateau;
  int longueur;
  int largeur;
  int nombreMotPossible;

  int lower = 97;
  int higher = 122;

  private LinkedList<Mot> liste;

  String motFinal;

  public MoteurJeu(int longueur, int largeur) throws IOException{
    nombreMotPossible = 0;
    String line;
    this.longueur = longueur+1;
    this.largeur = largeur+1;
    this.plateau = new char[this.longueur][this.largeur];
    File f = new File("dico.txt");
    BufferedReader br = new BufferedReader(new FileReader(f));
    liste = new LinkedList<Mot>();
    while ((line = br.readLine()) != null) {
      nombreMotPossible++;
    }
    br.close();
    motFinal = "1234";
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
        if(verifierEtEcrireMot(mot, orientation, i, j)) {
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
    while(!verifierEtEcrireMot(mot, orientation, i, j)) {
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

  public boolean verifierEtEcrireMot(String mot, int orientation, int x, int y) {
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
      mettreMot(1, tab, x, y);
      return true;
    } else if (orientation == 2) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x+i][y] == tab[i] || plateau[x+i][y] == '-') {
        } else {
          return false;
        }
      }
      mettreMot(2, tab, x, y);
      return true;
    } else if (orientation == 3) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x+i][y+i] == tab[i] || plateau[x+i][y+i] == '-') {
        } else {
          return false;
        }
      }
      mettreMot(3, tab, x, y);
      return true;
    }
    return false;
  }

  public boolean leMotPeutEtreEcritSansEcrire(String mot, int orientation, int x, int y) {
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
      return true;
    } else if (orientation == 2) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x+i][y] == tab[i] || plateau[x+i][y] == '-') {
        } else {
          return false;
        }
      }
      return true;
    } else if (orientation == 3) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x+i][y+i] == tab[i] || plateau[x+i][y+i] == '-') {
        } else {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  public void remplirDeMots(int nombreDeMots) throws IOException{
    if(nombreDeMots==0) {
      nombreDeMots=999;
    }
    while(grilleNonRemplie()) {
      if(placerMotHasard(lireMot())) {
        nombreDeMots--;
        if(nombreDeMots==0) {
          break;
        }
      }
    }
  }

  public void mettreMot(int orientation, char[] tab, int x, int y) {
    System.out.println("On a mis le mot " + new String(tab));
    liste.add(new Mot(new String(tab), x, y, orientation));
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

  public int[] placerMotOrdonneReturnCoord(String mot) {
    int[] res = new int[4];
    int place;
    res[3] = 4;
    for(int i =0; i<this.longueur-1; i++) {
      for(int j = 0; j<this.largeur-1; j++) {
        if(leMotPeutEtreEcritSansEcrire(mot, 1, i, j)) {
          res[0]=compterPlacePrise(1, mot.toCharArray(), i, j);
          res[1]=i;
          res[2]=j;
          res[3]=1;
          return res;
        }
        if(leMotPeutEtreEcritSansEcrire(mot, 2, i, j)) {
          res[0]=compterPlacePrise(2, mot.toCharArray(), i, j);
          res[1]=i;
          res[2]=j;
          res[3]=2;
          return res;
        }
        if(leMotPeutEtreEcritSansEcrire(mot, 3, i, j)) {
          res[0]=compterPlacePrise(3, mot.toCharArray(), i, j);
          res[1]=i;
          res[2]=j;
          res[3]=3;
          return res;
        }
      }
    }
    return res;
  }



  public int compterPlacePrise(int orientation, char[] tab, int x, int y) {
    int place=0;
    if(orientation == 1) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x][y+i]!=tab[i]) {
          place++;
        }
      }
      return place;
    } else if(orientation == 2) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x+i][y]!=tab[i]) {
          place++;
        }
      }
      return place;
    } else if(orientation == 3) {
      for(int i=0; i<tab.length; i++) {
        if(plateau[x+i][y+i]!=tab[i]) {
          place++;
        }
      }
      return place;
    } else {
      System.out.println("MAUVAISE ORIENTATION");
    }
    return -1;
  }

  public void remplirJusquaMotFinal()  throws IOException{
    String mot ="";
    int[] tab = new int[4];
    boolean first = true;
    while(nombrePlaceRestante() != motFinal.toCharArray().length) {
      mot = lireMot();
      tab = placerMotOrdonneReturnCoord(mot);
      while(tab[3]==4) {
        mot = lireMot();
        tab = placerMotOrdonneReturnCoord(mot);
      }
      if(tab[0] <= nombrePlaceRestante() - motFinal.toCharArray().length) {
        mettreMot(tab[3], mot.toCharArray(), tab[1], tab[2]);
      }
    }
  }

  public boolean ilResteLaPlacePourLeMot() {
    int tailleMot = motFinal.toCharArray().length;
    if(nombrePlaceRestante()==tailleMot) {
      return true;
    }
    return false;
  }

  public int nombrePlaceRestante() {
    int espaceLibre =0;
    for(int i =0; i<this.longueur-1; i++) {
      for(int j = 0; j<this.largeur-1; j++) {
        if(plateau[i][j]=='-') {
          espaceLibre++;
        }
      }
    }
    return espaceLibre;
  }

  public void insererMotFinal() {
    char[] tab = new char[motFinal.toCharArray().length];
    tab=motFinal.toCharArray();
    int k=0;
    for(int i =0; i<this.longueur-1; i++) {
      for(int j = 0; j<this.largeur-1; j++) {
        if(plateau[i][j]=='-') {
          plateau[i][j]=tab[k];
          k++;
        }
      }
    }
  }

  public void afficherIG() {
    Fenetre fen = new Fenetre(longueur, largeur, plateau, liste);
    fen.initialiserFenetre();
  }
}
