package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;

public class TurnToHeadingCommand extends PIDCommand {

  private static final double p = 0.1;
  private static final double i = 0.0;
  private static final double d = 0.0;
  private static final double TOLERANCE = 0.1;
  private static final double MAX_RATE = 0.5;
  private final int desiredHeading;

  private boolean hasRunPIDOnce = false;

  public TurnToHeadingCommand(final int desiredHeading) {
    super(p, i, d);

    requires(Robot.driveSubsystem);

    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setInputRange(0,360);
    getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
    getPIDController().setContinuous(true);

    setTimeout(15);

    this.desiredHeading = desiredHeading;
  }

  @Override
  protected void initialize() {
    getPIDController().setSetpoint(this.desiredHeading);
  }

  @Override
  protected double returnPIDInput() {
    hasRunPIDOnce = true;
    return Robot.driveSubsystem.getHeading();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.driveSubsystem.arcadeDrive(0, output);
  }

  @Override
  protected boolean isFinished() {
    return getPIDController().onTarget() && hasRunPIDOnce;
  }
}
