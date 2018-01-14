public class Mot {

  private String mot;
  private int x;
  private int y;
  private int orientation;
  private boolean check;
  private int sens; //if 0, normal. If 1, reverse.

  public Mot(String m, int i, int j, int o, int s) {
    mot = m;
    x = i;
    y = j;
    orientation = o;
    check = false;
    sens = s;
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

  public void check() {
    check = true;
  }
}
