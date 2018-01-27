package org.team1294.firstpowerup.robot.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.vision.VisionProcessing;
import org.team1294.firstpowerup.robot.vision.VisionProcessingResult;

public class VisionSubsystem extends Subsystem {

  private final VisionProcessing visionProcessing;
  private final CvSink cvSink;
  private final UsbCamera usbCamera;
  private final CameraServer cameraServer;

  private final Mat frame = new Mat();

  private static final int IMG_WIDTH = 320;
  private static final int IMG_HEIGHT = 240;

  public VisionSubsystem() {
    visionProcessing = new VisionProcessing();

    cameraServer = CameraServer.getInstance();
    usbCamera = cameraServer.startAutomaticCapture(0);
    usbCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    usbCamera.setFPS(30);
    usbCamera.setBrightness(7);
    usbCamera.setExposureManual(30);
    cvSink = cameraServer.getVideo(usbCamera);
  }


  @Override
  protected void initDefaultCommand() {

  }

  public VisionProcessingResult grabFrameAndDetectVisionTarget() {
    VisionProcessingResult result;
    try {
      double heading = Robot.driveSubsystem.getHeading();

      // grab a frame
      cvSink.grabFrame(frame);

      // do the processing
      result = visionProcessing
          .processSwitchTargetFrame(frame);
      result.setHeadingWhenImageTaken(heading);

      SmartDashboard.putBoolean("VisionSubsystem.SwitchTargetAcquired", result.isTargetAcquired());
      SmartDashboard.putNumber("VisionSubsystem.SwitchTargetDegreesOffCenter", result.getDegreesOffCenter());
    } catch (Exception ex) {
      System.out.println("Failed to do vision processing.");
      ex.printStackTrace();
      result = new VisionProcessingResult();
      result.setTargetAcquired(false);
    }

    return result;
  }

  public VisionProcessingResult grabFrameAndDetectCrate() {
    return null;
  }

  public void saveLastImage() {

  }
}
