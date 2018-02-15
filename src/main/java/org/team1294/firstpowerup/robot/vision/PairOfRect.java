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

    public Rect getCombined() {
        return new Rect(topLeft(), bottomRight());
    }

    public Double fitnessScore() {
        double score = 0;

        // difference in height
        score += Math.abs(a.height - b.height);

        // difference in width
        score += Math.abs(a.width - b.width);

        // difference in center Y
        double rect1CenterY = a.y + a.height / 2;
        double rect2CenterY = b.y + b.height / 2;
        score += Math.abs(rect1CenterY - rect2CenterY);

        // difference between combined rect width:height ratio and the ideal of 15.3 :  8 = 1.9
        double combinedRatio = getCombined().width / getCombined().height;
        score += Math.abs(combinedRatio - 1.9);

        return score;
    }
}
