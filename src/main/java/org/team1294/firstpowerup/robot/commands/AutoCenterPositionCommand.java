package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoCenterPositionCommand extends CommandGroup {

    private final AutoDriveCommand turnTowardsCorrectSide;
    private final AutoDriveCommand driveTowardsCorrectSide;
    private final AutoVisionTargetCommand driveTowardsVisionTargetCommand;

    public AutoCenterPositionCommand() {


        // drive straight ahead far enough to make it easy to turn
        addSequential(new AutoDriveCommand(0.1, 0, 0.5, 0.25));

        // turn towards the desired side (heading gets set in initialize)
        turnTowardsCorrectSide = new AutoDriveCommand(0, 0, 0.5, 0.5);
        addSequential(turnTowardsCorrectSide);

        // drive towards the desired side (heading and distance gets set in initialize)
        driveTowardsCorrectSide = new AutoDriveCommand(0, 0, 0.5, 0.25);
        addSequential(driveTowardsCorrectSide);

        // turn towards the vision target
        addSequential(new AutoDriveCommand(0, 0, 0.5, 0.5));

        // engage the vision system approach (distance set in initialize)
        driveTowardsVisionTargetCommand = new AutoVisionTargetCommand(0);
        addSequential(driveTowardsVisionTargetCommand);

        // deliver the crate
        addSequential(new AutoDeliverCrateCommand());
    }

    @Override
    protected void initialize() {
        final String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.charAt(0) == 'L') {
            turnTowardsCorrectSide.setHeading(315);
            driveTowardsCorrectSide.setHeading(315);
        } else {
            turnTowardsCorrectSide.setHeading(45);
            driveTowardsCorrectSide.setHeading(45);
        }

        driveTowardsCorrectSide.setDistance(SmartDashboard
            .getNumber("AutoCenterPositionCommand.DriveTowardsCorrectSideDistance", 1.0)); // todo tune this distance

        driveTowardsVisionTargetCommand.setDistance(SmartDashboard
            .getNumber("AutoCenterPositionCommand.DriveTowardsVisionTargetDistance", 1.0)); // todo tune this distance
    }
}
