package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.vision.VisionProcessingResult;

public class ApproachVisionTargetTurnCommand extends PIDCommand {

  private static final double p = 1.0;
  private static final double i = 0.0;
  private static final double d = 0.0;
  private static final double TOLERANCE = 5.0f;
  private static final double MAX_RATE = 0.08;

  private final ApproachVisionTargetCommand parent;
  private final Timer timer;

  public ApproachVisionTargetTurnCommand(ApproachVisionTargetCommand parent) {
    super(p,i,d);
    this.parent = parent;
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
    getPIDController().setSetpoint(0);

    timer = new Timer();
  }

  @Override
  protected void initialize() {
    timer.start();
  }

  @Override
  protected void execute() {
    // do vision processing
    VisionProcessingResult visionProcessingResult = Robot.visionSubsystem.grabFrameAndDetectVisionTarget();

    // if the target was acquired, adjust the setpoint
    if (visionProcessingResult.isTargetAcquired()) {
      getPIDController().setSetpoint(visionProcessingResult.getHeadingWhenImageTaken() + visionProcessingResult.getDegreesOffCenter());
    }

    // periodically save an image
    if (timer.hasPeriodPassed(.25)) {
      Robot.visionSubsystem.saveLastImage();
      timer.reset();
    }

    parent.setVisionTargetAcquired(visionProcessingResult.isTargetAcquired());
  }

  @Override
  protected double returnPIDInput() {
    return Robot.driveSubsystem.getHeading();
  }

  @Override
  protected void usePIDOutput(double output) {
    parent.setTurnRate(output);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  public boolean onTarget() {
    return getPIDController().onTarget();
  }
}
