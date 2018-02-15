package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.team1294.firstpowerup.robot.Robot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Austin Jenchi (timtim17)
 */
public class SetArmHeightCommand extends InstantCommand {
    private static final int NO_SOFT_LIMIT = -1;

    private final double height;
    private final int reverseSoftLimit;
    private final int forwardSoftLimit;

    public SetArmHeightCommand(double height) {
        this(height, NO_SOFT_LIMIT, NO_SOFT_LIMIT);
    }

    public SetArmHeightCommand(double height, int reverseSoftLimit, int forwardSoftLimit) {
        super("Set Arm Height to " + height + "ft");
        this.height = height;
        this.reverseSoftLimit = reverseSoftLimit;
        this.forwardSoftLimit = forwardSoftLimit;

        requires(Robot.armSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.armSubsystem.setArmHeight(height);
        if (reverseSoftLimit == NO_SOFT_LIMIT && forwardSoftLimit == NO_SOFT_LIMIT) {
            Robot.armSubsystem.disableArmSoftLimits();
        } else {
            Robot.armSubsystem.setArmSoftLimits(reverseSoftLimit, forwardSoftLimit);
        }
    }

    public static List<SetArmHeightCommand> createPresetArmHeightCommands() {
        List<SetArmHeightCommand> result = new ArrayList<>();
        result.add(new SetArmHeightCommand(0));
        result.add(new SetArmHeightCommand(2, 1, 2));
        result.add(new SetArmHeightCommand(6, 4, 6));
        return result;
    }
}
