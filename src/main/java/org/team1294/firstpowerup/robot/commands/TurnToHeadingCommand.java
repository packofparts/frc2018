package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;

public class TurnToHeadingCommand extends PIDCommand {

  private final int desiredHeading;
  private boolean hasRunPIDOnce = false;

  public TurnToHeadingCommand(final int desiredHeading) {
    super("Turn to heading " + desiredHeading + " degrees", 1.0, 0.0, 0.0);

    requires(Robot.driveSubsystem);

    setTimeout(15);

    this.desiredHeading = desiredHeading;

    double p = SmartDashboard.getNumber("TurnToHeadingCommand.p", 1.0);
    double i = SmartDashboard.getNumber("TurnToHeadingCommand.i", 0.0);
    double d = SmartDashboard.getNumber("TurnToHeadingCommand.d", 0.0);
    double tolerance = SmartDashboard.getNumber("TurnToHeadingCommand.tolerance", 5.0);
    double maxOutput = SmartDashboard.getNumber("TurnToHeadingCommand.maxOutput", 0.08);

    getPIDController().setP(p);
    getPIDController().setI(i);
    getPIDController().setD(d);
    getPIDController().setAbsoluteTolerance(tolerance);
    getPIDController().setOutputRange(-maxOutput, maxOutput);

    getPIDController().setAbsoluteTolerance(tolerance);
    getPIDController().setInputRange(0, 360);
    getPIDController().setContinuous(true);
    getPIDController().setOutputRange(-maxOutput, maxOutput);
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
