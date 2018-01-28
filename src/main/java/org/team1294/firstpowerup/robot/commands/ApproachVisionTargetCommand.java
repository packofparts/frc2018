package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.vision.VisionProcessingResult;

public class ApproachVisionTargetCommand extends CommandGroup {

  private final ForwardPIDCommand forwardPIDCommand;
  private final TurnPIDCommand turnPIDCommand;
  private final DriveCommand driveCommand;

  private double forwardRate;
  private double turnRate;


  public ApproachVisionTargetCommand(double distance) {
    super("Drive towards vision target " + distance + "m");

    forwardPIDCommand = new ForwardPIDCommand(distance);
    turnPIDCommand = new TurnPIDCommand();
    driveCommand = new DriveCommand();

    addParallel(turnPIDCommand);
    addParallel(forwardPIDCommand);
    addParallel(driveCommand);

    setTimeout(15);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut() || (forwardPIDCommand.isOnTarget() && turnPIDCommand.isOnTarget());
  }

  private class DriveCommand extends Command {
    public DriveCommand() {
      requires(Robot.driveSubsystem);
    }

    @Override
    protected void execute() {
      Robot.driveSubsystem.arcadeDrive(forwardRate, turnRate);
    }

    @Override
    protected boolean isFinished() {
      return false;
    }
  }

  private class ForwardPIDCommand extends PIDCommand {

    private final double distance;
    private boolean hasRunPIDOnce = false;

    public ForwardPIDCommand(double distance) {
      super(1.0, 0.1, 0);
      this.distance = distance;
    }

    @Override
    protected void initialize() {
      double p = SmartDashboard.getNumber("ApproachVisionTargetCommand.ForwardPID.p", 1.0);
      double i = SmartDashboard.getNumber("ApproachVisionTargetCommand.ForwardPID.i", 0.1);
      double d = SmartDashboard.getNumber("ApproachVisionTargetCommand.ForwardPID.d", 0.0);
      double tolerance = SmartDashboard
          .getNumber("ApproachVisionTargetCommand.ForwardPID.tolerance", 0.01);
      double maxOutput = SmartDashboard
          .getNumber("ApproachVisionTargetCommand.ForwardPID.maxOutput", 1.0);

      getPIDController().setP(p);
      getPIDController().setI(i);
      getPIDController().setD(d);
      getPIDController().setAbsoluteTolerance(tolerance);
      getPIDController().setOutputRange(-maxOutput, maxOutput);
      getPIDController().setSetpoint(Robot.driveSubsystem.getEncoderPositionAverage() + distance);
    }

    @Override
    protected double returnPIDInput() {
      hasRunPIDOnce = true;
      return Robot.driveSubsystem.getEncoderPositionAverage();
    }

    @Override
    protected void usePIDOutput(double output) {
      forwardRate = output;
    }

    @Override
    protected boolean isFinished() {
      return false;
    }

    public boolean isOnTarget() {
      return hasRunPIDOnce && getPIDController().onTarget();
    }
  }

  private class TurnPIDCommand extends PIDCommand {

    private final Timer timer;
    private boolean hasRunPIDOnce = false;

    public TurnPIDCommand() {
      super(1.0, 0, 0);
      requires(Robot.visionSubsystem);

      timer = new Timer();

      double p = SmartDashboard.getNumber("ApproachVisionTargetCommand.TurnPID.p", 1.0);
      double i = SmartDashboard.getNumber("ApproachVisionTargetCommand.TurnPID.i", 0.0);
      double d = SmartDashboard.getNumber("ApproachVisionTargetCommand.TurnPID.d", 0.0);
      double tolerance = SmartDashboard
          .getNumber("ApproachVisionTargetCommand.TurnPID.tolerance", 5.0);
      double maxOutput = SmartDashboard
          .getNumber("ApproachVisionTargetCommand.TurnPID.maxOutput", 0.08);

      getPIDController().setP(p);
      getPIDController().setI(i);
      getPIDController().setD(d);
      getPIDController().setAbsoluteTolerance(tolerance);
      getPIDController().setInputRange(0, 360);
      getPIDController().setContinuous(true);
      getPIDController().setOutputRange(-maxOutput, maxOutput);
    }

    @Override
    protected void initialize() {
      timer.start();
      getPIDController().setSetpoint(Robot.driveSubsystem.getHeading());
    }

    @Override
    protected void execute() {
      // do vision processing
      VisionProcessingResult visionProcessingResult = Robot.visionSubsystem.detectSwitch();

      // if the target was acquired, adjust the setpoint
      if (visionProcessingResult.isTargetAcquired()) {
        getPIDController().setSetpoint(visionProcessingResult.getHeadingToTurn());
      }

      // periodically save an image
      if (timer.hasPeriodPassed(.25)) {
        Robot.visionSubsystem.saveLastImage();
        timer.reset();
      }
    }

    @Override
    protected double returnPIDInput() {
      hasRunPIDOnce = true;
      return Robot.driveSubsystem.getHeading();
    }

    @Override
    protected void usePIDOutput(double output) {
      turnRate = output;
    }

    @Override
    protected boolean isFinished() {
      return false;
    }

    public boolean isOnTarget() {
      return hasRunPIDOnce && getPIDController().onTarget();
    }
  }
}
