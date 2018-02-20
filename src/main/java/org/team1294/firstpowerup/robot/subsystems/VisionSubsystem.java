package org.team1294.firstpowerup.robot.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.commands.DefaultVisionCommand;
import org.team1294.firstpowerup.robot.vision.CratePipeline;
import org.team1294.firstpowerup.robot.vision.PairOfRect;
import org.team1294.firstpowerup.robot.vision.SwitchTargetPipeline;
import org.team1294.firstpowerup.robot.vision.VisionProcessingResult;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VisionSubsystem extends Subsystem {

    private static final int IMG_WIDTH = 320;
    private static final int IMG_HEIGHT = 240;
    private static final int FPS = 30;
    private final CvSink cvSink;
    private final CvSource cvSource;
    private final UsbCamera usbCamera;
    private final CameraServer cameraServer;
    private final Mat frame = new Mat();
    private final SwitchTargetPipeline switchTargetPipeline;
    private final CratePipeline cratePipeline;

    private VisionProcessingResult visionProcessingResult;

    private enum CameraMode {NORMAL, VISION}
    private CameraMode currentMode;

    public VisionSubsystem() {
        switchTargetPipeline = new SwitchTargetPipeline();
        cratePipeline = new CratePipeline();

        cameraServer = CameraServer.getInstance();

        usbCamera = cameraServer.startAutomaticCapture(0);
        usbCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
        usbCamera.setFPS(FPS);

        cvSink = cameraServer.getVideo(usbCamera);
        cvSource = cameraServer.putVideo("VisionSystem", IMG_WIDTH, IMG_HEIGHT);

        // grab a frame from the camera
        // draw on the frame if we have a vision target
        // output the frame to the stream
        new Thread(() -> {
            while (!Thread.interrupted()) {
                // grab a frame from the camera
                cvSink.grabFrame(frame);

                // draw on the frame if we have a vision target
                if (visionProcessingResult != null && visionProcessingResult.isTargetAcquired()) {
                    final Rect targetRect = visionProcessingResult.getTargetRect();
                    Imgproc
                            .rectangle(frame, targetRect.tl(), targetRect.br(), new Scalar(0, 0, 255), 2);
                }

                // output the frame to the stream
                cvSource.putFrame(frame);
            }
        }).start();

        setCameraNormal();
        currentMode = CameraMode.NORMAL;
    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DefaultVisionCommand());
    }

    public void detectNothing() {
        // set the camera to take normal images
        setCameraNormal();
        visionProcessingResult = VisionProcessingResult.getEmptyResult();
    }

    public void detectSwitch() {
        VisionProcessingResult result = new VisionProcessingResult();
        try {
            // set the camera to take vision target images
            setCameraForVisionTarget();

            // remember the heading before we do lengthy processing
            double heading = Robot.driveSubsystem.getHeading();

            // run the grip pipeline
            cvSink.grabFrame(frame);
            switchTargetPipeline.process(frame);

            // get the bounding rect for each contour
            List<Rect> rects = switchTargetPipeline.filterContoursOutput().stream()
                .map(Imgproc::boundingRect).collect(Collectors.toList());

            // get every combination of pairs
            Stream<PairOfRect> pairs = rects.stream().flatMap(
                i -> rects.stream().filter(j -> !i.equals(j)).map(j -> new PairOfRect(i, j)));

            // get the best (lowest) scoring pair
            Optional<PairOfRect> bestPair = pairs
                .min((a, b) -> b.fitnessScore().compareTo(a.fitnessScore()));

            if (bestPair.isPresent()) {
                result.setTargetAcquired(true);

                // calculate how many degrees off center
                double halfWidth = frame.width() / 2;
                double pixelsOffCenter = bestPair.get().centerX() - halfWidth;
                double percentOffCenter = pixelsOffCenter / halfWidth;
                double degreesOffCenter =
                    percentOffCenter * 30; // todo: validate this years field of view for the camera
                result.setDegreesOffCenter(degreesOffCenter);
                result.setHeadingWhenImageTaken(heading);
                result.setTargetRect(bestPair.get().getCombined());
            } else {
                result.setTargetAcquired(false);
            }

            SmartDashboard.putBoolean("VisionSubsystem/SwitchTargetAcquired", result.isTargetAcquired());
            SmartDashboard.putNumber("VisionSubsystem/SwitchTargetDegreesOffCenter", result.getDegreesOffCenter());
        } catch (Exception ex) {
            System.out.println("Failed to do vision processing.");
            ex.printStackTrace();
            result.setTargetAcquired(false);
        }

        visionProcessingResult = result;
    }

    public void detectCrate() {
        VisionProcessingResult result = new VisionProcessingResult();
        try {
            // set the camera to take normal images
            setCameraNormal();

            // remember the heading before we do lengthy processing
            double heading = Robot.driveSubsystem.getHeading();

            // run the grip pipeline
            cratePipeline.process(frame);

//            // todo bunch of stuff here
//            result.setTargetAcquired(false);
//            result.setDegreesOffCenter(0);
//            result.setHeadingWhenImageTaken(heading);

            // get the bounding rect for each contour
            List<Rect> rects = cratePipeline.filterContoursOutput().stream()
                    .map(Imgproc::boundingRect).collect(Collectors.toList());
            Optional<Rect> bestRect = rects.stream().max(Comparator.comparingInt(a -> a.width));

            if (bestRect.isPresent()) {
                result.setTargetAcquired(true);

                // calculate how many degrees off center
                double halfWidth = frame.width() / 2;
                double pixelsOffCenter = bestRect.get().x - halfWidth;
                double percentOffCenter = pixelsOffCenter / halfWidth;
                double degreesOffCenter =
                        percentOffCenter * 30; // todo: validate this years field of view for the camera
                result.setDegreesOffCenter(degreesOffCenter);
                result.setHeadingWhenImageTaken(heading);
                result.setTargetRect(bestRect.get());
            } else {
                result.setTargetAcquired(false);
            }

            SmartDashboard
                .putBoolean("VisionSubsystem.CrateTargetAcquired", result.isTargetAcquired());
            SmartDashboard.putNumber("VisionSubsystem.CrateTargetDegreesOffCenter",
                result.getDegreesOffCenter());
        } catch (Exception ex) {
            System.out.println("Failed to do vision processing.");
            ex.printStackTrace();
            result.setTargetAcquired(false);
        }

        this.visionProcessingResult = result;
    }

    public VisionProcessingResult getResult() {
        return visionProcessingResult;
    }

    private void setCameraNormal() {
        // todo turn the green led ring OFF

        if (currentMode != CameraMode.NORMAL) {
            // set the brightness and exposure to bright auto
            //usbCamera.setBrightness(100);
            usbCamera.setExposureAuto();
            currentMode = CameraMode.NORMAL;
        }
    }

    private void setCameraForVisionTarget() {
        // todo turn the green led ring ON

        if (currentMode != CameraMode.VISION) {
            // set the brightness and exposure to dark manual
            //usbCamera.setBrightness(7);
            usbCamera.setExposureManual(30);
            currentMode = CameraMode.VISION;
        }
    }

    public void saveLastImage() {
        // todo save an image somehow
    }
}
