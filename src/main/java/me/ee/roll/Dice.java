package me.ee.roll;

import java.util.Random;

public class Dice {
  private Random rng;
  public Dice(Random rng) {
    this.rng = rng;
  }
  public String roll(int n, int size, int adder, String name) {
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    int total = 0;
    for (int i = 0; i < n; i++) {
      int r = rng.nextInt(size) + 1;
      total += r;
      sb.append(r);
      sb.append(' ');
    }
    sb.append(']');
    if (adder > 0) {
      sb.append(" + ");
      sb.append(adder);
      total += adder;
    }
    if (adder < 0) {
      sb.append(" - ");
      sb.append(-adder);
      total += adder;
    }
    sb.append('\n');
    sb.append('*');
    sb.append(name);
    sb.append('*');
    sb.append(" rolls ");
    sb.append("**");
    sb.append(total);
    sb.append("**");
    return sb.toString();
  }
}
