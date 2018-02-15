package org.team1294.firstpowerup.robot.vision;

import org.opencv.core.Rect;

public class VisionProcessingResult {

  private static final VisionProcessingResult EMPTY_RESULT = new VisionProcessingResult();

  private boolean targetAcquired;
  private double degreesOffCenter;
  private double headingWhenImageTaken;
  private Rect targetRect;

  public VisionProcessingResult() {
    targetAcquired = false;
  }

  public boolean isTargetAcquired() {
    return targetAcquired;
  }

  public void setTargetAcquired(boolean targetAcquired) {
    this.targetAcquired = targetAcquired;
  }

  public double getDegreesOffCenter() {
    return degreesOffCenter;
  }

  public void setDegreesOffCenter(double degreesOffCenter) {
    this.degreesOffCenter = degreesOffCenter;
  }

  public double getHeadingWhenImageTaken() {
    return headingWhenImageTaken;
  }

  public void setHeadingWhenImageTaken(double headingWhenImageTaken) {
    this.headingWhenImageTaken = headingWhenImageTaken;
  }

  public Rect getTargetRect() {
    return targetRect;
  }

  public void setTargetRect(Rect targetRect) {
    this.targetRect = targetRect;
  }

  public double getHeadingToTurn() {
    return headingWhenImageTaken + degreesOffCenter;
  }

  public static VisionProcessingResult getEmptyResult() {
    return EMPTY_RESULT;
  }
}
