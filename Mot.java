public class Mot {

  private String mot;
  private int x;
  private int y;
  private int orientation;

  public Mot(String m, int i, int j, int o) {
    mot = m;
    x = i;
    y = j;
    orientation = o;
  }

  public String getMot() {
    return this.mot;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getOrientation() {
    return orientation;
  }

  public int getTaille() {
    return this.mot.toCharArray().length;
  }
}
