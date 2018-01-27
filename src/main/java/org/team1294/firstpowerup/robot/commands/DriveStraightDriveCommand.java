package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;

public class DriveStraightDriveCommand extends Command{

    private DriveStraightCommand group;

    public DriveStraightDriveCommand(){
        super("Drive Straight Drive Command");
    }

    @Override
    protected void initialize() {
        CommandGroup group = getGroup();
        if (group instanceof DriveStraightCommand) {
            this.group = (DriveStraightCommand) group;
        } else {
            this.group = null;
        }
    }

  @Override
  protected void execute() {
    if (group != null) {
      Robot.driveSubsystem.arcadeDrive(group.getForwardRate(),
          group.getTurnRate());
    }
  }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
