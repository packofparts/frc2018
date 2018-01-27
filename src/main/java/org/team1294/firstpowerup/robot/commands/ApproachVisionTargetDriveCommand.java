package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team1294.firstpowerup.robot.Robot;

public class ApproachVisionTargetDriveCommand extends Command {
  private ApproachVisionTargetCommand parent;

  public ApproachVisionTargetDriveCommand() {

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
  protected void execute() {
    if (parent != null) {
      Robot.driveSubsystem.arcadeDrive(parent.getForwardRate(),
          parent.getTurnRate());
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
