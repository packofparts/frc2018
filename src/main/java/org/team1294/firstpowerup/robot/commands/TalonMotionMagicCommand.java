package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class TalonMotionMagicCommand extends Command {

    public static final double DISTANCE = 1.0;
    public static final double TOLERANCE = 0.01;

    public TalonMotionMagicCommand() {
        super("Talon Motion Magic Command");
        requires(Robot.driveSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.driveSubsystem.resetEncoders();
    }

    @Override
    protected void execute() {
        Robot.driveSubsystem.talonMotionMagic(DISTANCE, DISTANCE);
    }

    @Override
    protected void end() {
        Robot.driveSubsystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.driveSubsystem.getEncoderPositionAverage() - DISTANCE) < TOLERANCE;
    }
}
