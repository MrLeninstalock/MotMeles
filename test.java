import java.io.*;

public class test {

  public static void main(String[] args) throws IOException{
    MoteurJeu m = new MoteurJeu(10,10);
    m.initialiserGrille();
    m.remplirDeMots(5);
    m.finirRemplissage();
    m.afficherGrille();
  }
}
