package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Austin Jenchi (timtim17)
 */
public class ArmHeightMotionProfileCommand extends Command {
    private static final int NO_SOFT_LIMIT = -1;
    private static final double TOLLERANCE = 2.5;

    private final double height;
    private final int reverseSoftLimit;
    private final int forwardSoftLimit;

    public ArmHeightMotionProfileCommand(double height) {
        this(height, NO_SOFT_LIMIT, NO_SOFT_LIMIT);
    }

    public ArmHeightMotionProfileCommand(double height, int reverseSoftLimit, int forwardSoftLimit) {
        super("Set Arm Height to " + height + "units");
        this.height = height;
        this.reverseSoftLimit = reverseSoftLimit;
        this.forwardSoftLimit = forwardSoftLimit;
        Robot.armSubsystem.setHeightMotionProfile(100, 300);
        requires(Robot.armSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.armSubsystem.setArmMotionMagic(height);
//        Robot.armSubsystem.setArmSoftLimits(reverseSoftLimit, forwardSoftLimit);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.armSubsystem.getArmHeight() - height) < TOLLERANCE;
    }

    public static List<ArmHeightMotionProfileCommand> createPresetArmHeightCommands() {
        List<ArmHeightMotionProfileCommand> result = new ArrayList<>();
        result.add(new ArmHeightMotionProfileCommand(0));
        result.add(new ArmHeightMotionProfileCommand(2, 1, 2));
        result.add(new ArmHeightMotionProfileCommand(6, 4, 6));
        return result;
    }
}
