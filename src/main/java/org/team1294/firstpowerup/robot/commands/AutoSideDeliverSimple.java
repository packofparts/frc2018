package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Austin Jenchi (timtim17)
 */
public class AutoSideDeliverSimple extends CommandGroup {
    private final char side;

    public AutoSideDeliverSimple(char side) {
        super("Auto deliver cube to " + side);
        this.side = side;
        int heading = (side == 'L') ? 90 : 270;
        addSequential(new AutoDriveCommand(3, 0, 0.9, 0.5));
        addSequential(new SetArmHeightCommand(622));
        addSequential(new MoveWristUpFor1SecondInAutoCommand());
        addSequential(new AutoDriveCommand(0, heading, 0.5, 0.75));
        addSequential(new AutoDeliverCrateCommand());
    }

    public boolean shouldDeliverCube() {
        return DriverStation.getInstance().getGameSpecificMessage().charAt(0) == side;
    }
}
