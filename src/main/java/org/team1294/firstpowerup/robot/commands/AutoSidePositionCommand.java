package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSidePositionCommand extends CommandGroup {
    private final String side;
    private final AutoDriveCommand turnTowardsWall;
    private final AutoDriveCommand driveTowardsWall;
    private final AutoDriveCommand driveDownfield;
    private final AutoDriveCommand turnTowardsDelivery;
    private final AutoDriveCommand driveTowardsDelivery;
    private final AutoDeliverCrateCommand deliverCrateCommand;

    public AutoSidePositionCommand(String side) {
        this.side = side;

        // drive straight ahead far enough to make it easy to turn
        addSequential(new AutoDriveCommand(0.1, 0, 0.5, 0.25));

        // turn towards the wall (heading gets set in initialize)
        turnTowardsWall = new AutoDriveCommand(0, 0, 0.5, 0.5);
        addSequential(turnTowardsWall);

        // drive towards the wall (heading gets set in initialize)
        driveTowardsWall = new AutoDriveCommand(1, 0, 0.5, 0.25);
        addSequential(driveTowardsWall);

        // turn downfield
        addSequential(new AutoDriveCommand(0, 0, 0.5, 0.5));

        // drive downfield until abeam the crate delivery location (distance gets set in initialize)
        driveDownfield = new AutoDriveCommand(0, 0, 0.5, 0.25);
        addSequential(driveDownfield);

        // turn towards the crate delivery location (heading gets set in initialize)
        turnTowardsDelivery = new AutoDriveCommand(0, 0, 0.5, 0.5);
        addSequential(turnTowardsDelivery);

        // drive towards the crate delivery location ((heading and distance gets set in initialize)
        driveTowardsDelivery = new AutoDriveCommand(0, 0, 0.5, 0.25);
        addSequential(driveTowardsDelivery);

        // deliver the crate
        deliverCrateCommand = new AutoDeliverCrateCommand();
        addSequential(deliverCrateCommand);
    }

    @Override
    protected void initialize() {
        final String gameData = DriverStation.getInstance().getGameSpecificMessage();
        final String scaleSide = gameData.substring(1, 1);
        final String switchSide = gameData.substring(0, 0);

        if (side.equals("L")) {
            turnTowardsWall.setHeading(315);
            driveTowardsWall.setHeading(315);
            turnTowardsDelivery.setHeading(90);
            driveTowardsDelivery.setHeading(90);
        } else {
            turnTowardsWall.setHeading(45);
            driveTowardsWall.setHeading(45);
            turnTowardsDelivery.setHeading(270);
            driveTowardsDelivery.setHeading(270);
        }

        if (scaleSide.equals(side)) {
            driveDownfield.setDistance(2.0); // todo tune this distance
            driveTowardsDelivery.setDistance(0.5); // todo tune this distance
        } else if (switchSide.equals(side)) {
            driveDownfield.setDistance(1); // todo tune this distance
            driveTowardsDelivery.setDistance(0.5); // todo tune this distance
        } else {
            driveDownfield.setDistance(0.5); // todo tune this distance
            turnTowardsDelivery.setHeading(0);
            driveTowardsDelivery.setDistance(0);
            deliverCrateCommand.setEnabled(false);
        }
    }
}
