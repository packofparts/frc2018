package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCenterPositionCommand extends CommandGroup {

  public AutoCenterPositionCommand() {

    // drive straight ahead far enough to make it easy to turn
    addSequential(new DriveStraightCommand(0.1));

    // turn towards the desired side
    final String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.charAt(0) == 'L')
    {
      addSequential(new TurnToHeadingCommand(315));
    } else {
      addSequential(new TurnToHeadingCommand(45));
    }

    // drive straight ahead to line up with vision target
    addSequential(new DriveStraightCommand(1.0));

    // turn towards the vision target
    addSequential(new TurnToHeadingCommand(0));

    // approach the vision target
    addSequential(new DriveStraightCommand(0.5));

    // engage the vision system approach
    addSequential(new ApproachVisionTargetCommand(1.0));

    // deliver the crate
    // todo
  }

}
