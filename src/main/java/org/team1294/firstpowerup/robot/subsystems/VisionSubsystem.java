package org.team1294.firstpowerup.robot.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.vision.CratePipeline;
import org.team1294.firstpowerup.robot.vision.PairOfRect;
import org.team1294.firstpowerup.robot.vision.SwitchTargetPipeline;
import org.team1294.firstpowerup.robot.vision.VisionProcessingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VisionSubsystem extends Subsystem {

  private final CvSink cvSink;
  private final UsbCamera usbCamera;
  private final CameraServer cameraServer;
  private final Mat frame = new Mat();

  private final SwitchTargetPipeline switchTargetPipeline;
  private final CratePipeline cratePipeline;

  private static final int IMG_WIDTH = 320;
  private static final int IMG_HEIGHT = 240;

  public VisionSubsystem() {
    cameraServer = CameraServer.getInstance();
    usbCamera = cameraServer.startAutomaticCapture(0);
    usbCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    usbCamera.setFPS(30);
    usbCamera.setBrightness(7);
    usbCamera.setExposureManual(30);
    cvSink = cameraServer.getVideo(usbCamera);

    switchTargetPipeline = new SwitchTargetPipeline();
    cratePipeline = new CratePipeline();
  }


  @Override
  protected void initDefaultCommand() {

  }

  public VisionProcessingResult detectSwitch() {
    VisionProcessingResult result = new VisionProcessingResult();
    try {
      double heading = Robot.driveSubsystem.getHeading();

      // grab a frame
      cvSink.grabFrame(frame);

      // run the grip pipeline
      switchTargetPipeline.process(frame);

      // get the bounding rect for each contour
      List<Rect> rects = switchTargetPipeline
          .filterContoursOutput()
          .stream()
          .map(contour -> Imgproc.boundingRect(contour))
          .collect(Collectors.toList());

      // get every combination of pairs
      Stream<PairOfRect> pairs = rects.stream()
          .flatMap(i -> rects.stream().filter(j -> !i.equals(j)).map(j -> new PairOfRect(i, j)));

      // get the best (lowest) scoring pair
      Optional<PairOfRect> bestPair = pairs.min((a, b) -> scoreSwitchContourPair(b).compareTo(scoreSwitchContourPair(a)));

      if (bestPair.isPresent()) {
        result.setTargetAcquired(true);

        // calculate how many degrees off center
        double halfWidth = frame.width() / 2;
        double pixelsOffCenter = bestPair.get().centerX() - halfWidth;
        double percentOffCenter = pixelsOffCenter  / halfWidth;
        double degreesOffCenter = percentOffCenter * 30; // todo: validate this years field of view for the camera
        result.setDegreesOffCenter(degreesOffCenter);
        result.setHeadingWhenImageTaken(heading);
      } else {
        result.setTargetAcquired(false);
      }

      SmartDashboard.putBoolean("VisionSubsystem.SwitchTargetAcquired", result.isTargetAcquired());
      SmartDashboard.putNumber("VisionSubsystem.SwitchTargetDegreesOffCenter", result.getDegreesOffCenter());
    } catch (Exception ex) {
      System.out.println("Failed to do vision processing.");
      ex.printStackTrace();
      result.setTargetAcquired(false);
    }

    return result;
  }

  public VisionProcessingResult detectCrate() {
    VisionProcessingResult result = new VisionProcessingResult();
    try {
      double heading = Robot.driveSubsystem.getHeading();

      // grab a frame
      cvSink.grabFrame(frame);

      // run the grip pipeline
      cratePipeline.process(frame);

      // todo bunch of stuff here
      result.setTargetAcquired(false);
      result.setDegreesOffCenter(0);
      result.setHeadingWhenImageTaken(heading);

      SmartDashboard.putBoolean("VisionSubsystem.CrateTargetAcquired", result.isTargetAcquired());
      SmartDashboard.putNumber("VisionSubsystem.CrateTargetDegreesOffCenter", result.getDegreesOffCenter());
    } catch (Exception ex) {
      System.out.println("Failed to do vision processing.");
      ex.printStackTrace();
      result.setTargetAcquired(false);
    }

    return result;
  }


  public void saveLastImage() {

  }




  public Double scoreSwitchContourPair(PairOfRect pair) {
    double score = 0;

    // difference in height
    score += Math.abs(pair.getA().height - pair.getB().height);

    // difference in width
    score += Math.abs(pair.getA().width - pair.getB().width);

    // difference in center Y
    double rect1CenterY = pair.getA().y + pair.getA().height / 2;
    double rect2CenterY = pair.getB().y + pair.getB().height / 2;
    score += Math.abs(rect1CenterY - rect2CenterY);

    // difference between combined rect width:height ratio and the ideal of 2.05
    // todo figure out ideal ratio for this years vision target
    double combinedWidth;
    double combinedHeight;
    if (pair.getA().x < pair.getB().x) {
      combinedWidth = pair.getA().x + pair.getB().x + pair.getB().width;
    } else {
      combinedWidth = pair.getB().x + pair.getA().x + pair.getA().width;
    }
    if (pair.getA().y < pair.getB().y) {
      combinedHeight = pair.getA().y + pair.getB().y + pair.getB().height;
    } else {
      combinedHeight = pair.getB().y + pair.getA().y + pair.getA().height;
    }

    double combinedRatio = combinedWidth / combinedHeight;
    score += Math.abs(combinedRatio - 2.05);

    return score;
  }
}
