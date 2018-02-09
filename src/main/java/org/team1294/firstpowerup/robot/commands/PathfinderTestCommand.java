package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import org.team1294.firstpowerup.robot.Robot;

import java.io.File;

/**
 * @author Austin Jenchi (timtim17)
 */
public class PathfinderTestCommand extends Command {
    private static final double kP = 1.0;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kV = 900;
    private static final double kA = 100; // todo: tune
    private static final int TICKS_PER_REVOLUTION = 40; // todo: tune
    public static final double kInchToMeter = 0.0254;
    private static final double WHEEL_DIAMETER = 6 * kInchToMeter; // diameter of 6" wheel in meters
    private static final String PATH_FILE_PREFIX = "/home/lvuser/TestScaleMotionPath_";

    private EncoderFollower leftFollow;
    private EncoderFollower rightFollow;

    public PathfinderTestCommand() {
        super("Pathfinder Test Command");
        requires(Robot.driveSubsystem);
        File left = new File(PATH_FILE_PREFIX + "left_detailed.csv");
        File right = new File(PATH_FILE_PREFIX + "right_detailed.csv");
        Trajectory leftTraj = Pathfinder.readFromCSV(left);
        Trajectory rightTraj = Pathfinder.readFromCSV(right);
        leftFollow = new EncoderFollower(leftTraj);
        rightFollow = new EncoderFollower(rightTraj);

        leftFollow.configurePIDVA(kP, kI, kD, kV, kA);
        rightFollow.configurePIDVA(kP, kI, kD, kV, kA);
    }

    @Override
    protected void initialize() {
        leftFollow.reset();
        rightFollow.reset();

        leftFollow.configureEncoder(Robot.driveSubsystem.getEncoderPositionLeftRaw(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);
        rightFollow.configureEncoder(Robot.driveSubsystem.getEncoderPositionRightRaw(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);
    }

    @Override
    protected void execute() {
        int encoderTick = 10;   // todo: do something
        double desiredHeading = leftFollow.getHeading();    // todo: compensate for this
        Robot.driveSubsystem.tankDrive(leftFollow.calculate(encoderTick), rightFollow.calculate(encoderTick));
    }

    @Override
    protected void end() {
        super.end();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
