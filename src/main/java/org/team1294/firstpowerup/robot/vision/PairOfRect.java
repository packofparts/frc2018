package org.team1294.firstpowerup.robot.vision;

import org.opencv.core.Point;
import org.opencv.core.Rect;

public class PairOfRect {
  private final Rect a;
  private final Rect b;

  public PairOfRect(final Rect a, final Rect b) {
    this.a = a;
    this.b = b;
  }

  public double centerX() {
    if (a.x < b.x) {
      return a.x + ((double) (b.x + b.width - a.x)) / 2;
    } else {
      return b.x + ((double) (a.x + a.width - b.x)) / 2;
    }
  }

  public Point topLeft() {
    if (a.x < b.x) {
      return new Point(a.x, a.y);
    } else {
      return new Point(b.x, b.y);
    }
  }

  public Point bottomRight() {
    if (a.x < b.x) {
      return new Point(b.x + b.width, b.y + b.height);
    } else {
      return new Point(a.x + a.width, a.y + a.height);
    }
  }

  public Rect getA() {
    return a;
  }

  public Rect getB() {
    return b;
  }
}
