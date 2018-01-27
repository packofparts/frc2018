package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;

public class ApproachVisionTargetForwardCommand extends PIDCommand {

  private static final double p = 1.0;
  private static final double i = 0.0;
  private static final double d = 0.0;

  private final double distance;

  private ApproachVisionTargetCommand parent;
  private boolean hasRunPIDOnce = false;

  public ApproachVisionTargetForwardCommand(double distance) {
    super(p,i,d);
    this.distance = distance;
  }

  @Override
  protected void initialize() {
    CommandGroup group = getGroup();
    if (group instanceof ApproachVisionTargetCommand) {
      this.parent = (ApproachVisionTargetCommand) group;
    } else {
      this.parent = null;
    }
  }

  @Override
  protected double returnPIDInput() {
    hasRunPIDOnce = true;
    return Robot.driveSubsystem.getEncoderPositionAverage();
  }

  @Override
  protected void usePIDOutput(double output) {
    if(parent != null){
      parent.setForwardRate(output);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  public boolean onTarget() {
    return hasRunPIDOnce && getPIDController().onTarget();
  }
}
