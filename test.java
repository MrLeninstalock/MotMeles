import java.io.*;

public class test {

  public static void main(String[] args) throws IOException{
    MoteurJeu m = new MoteurJeu(5,5);
    m.initialiserGrille();
    m.remplirJusquaMotFinal();
    m.insererMotFinal();
    m.afficherIG();
  }
}
