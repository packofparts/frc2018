package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team1294.firstpowerup.robot.Robot;

public class ApproachVisionTargetCommand extends CommandGroup {

  private double forwardRate;
  private double turnRate;
  private boolean visionTargetAcquired;

  public ApproachVisionTargetCommand() {
    requires(Robot.driveSubsystem);

    addParallel(new ApproachVisionTargetDriveCommand());
    addParallel(new ApproachVisionTargetTurnCommand(this));
    addParallel(new ApproachVisionTargetForwardCommand(1.0));
  }

  public double getForwardRate() {
    return forwardRate;
  }

  public void setForwardRate(double forwardRate) {
    this.forwardRate = forwardRate;
  }

  public double getTurnRate() {
    return turnRate;
  }

  public void setTurnRate(double turnRate) {
    this.turnRate = turnRate;
  }

  public void setVisionTargetAcquired(boolean visionTargetAcquired) {
    this.visionTargetAcquired = visionTargetAcquired;
  }
}
