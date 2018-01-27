package org.team1294.firstpowerup.robot.vision;

public class VisionProcessingResult {
  private boolean targetAcquired;
  private double degreesOffCenter;
  private double headingWhenImageTaken;

  public VisionProcessingResult() {

  }

  public VisionProcessingResult(boolean targetAcquired, double degreesOffCenter,
      double headingWhenImageTaken) {
    this.targetAcquired = targetAcquired;
    this.degreesOffCenter = degreesOffCenter;
    this.headingWhenImageTaken = headingWhenImageTaken;
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
}
