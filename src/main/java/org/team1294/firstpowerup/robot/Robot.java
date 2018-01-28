package org.team1294.firstpowerup.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.commands.AutoCenterPositionCommand;
import org.team1294.firstpowerup.robot.commands.DriveStraightCommand;
import org.team1294.firstpowerup.robot.commands.ResetEncoderCommand;
import org.team1294.firstpowerup.robot.commands.ResetGyroCommand;
import org.team1294.firstpowerup.robot.commands.TurnToHeadingCommand;
import org.team1294.firstpowerup.robot.subsystems.DriveSubsystem;
import org.team1294.firstpowerup.robot.subsystems.VisionSubsystem;

/**
 * The main class for the Robot. This handles the creation of the various
 * subsystems of the robot, and the timing of when events happen. Each robot
 * mode has an "init" and a "periodic" method. The "init" event happens one
 * time when the drivers' station (DS) starts an op-mode, and the "periodic"
 * event happens every time a packet is received from the DS (~20 ms).
 */
public class Robot extends IterativeRobot {
    public static DriveSubsystem driveSubsystem;
    public static VisionSubsystem visionSubsystem;

    public static OI oi;

    /**
     * Constructor for Robot(). This is where
     * {@link edu.wpi.first.wpilibj.command.Subsystem Subsystems} and the
     * {@link OI} should be initialized.
     */
    public Robot() {
        driveSubsystem = new DriveSubsystem();
        visionSubsystem = new VisionSubsystem();

        // OI has to be initialized AFTER the Subsystems, because the OI has
        // Buttons which reference Commands which use the Subsystems
        oi = new OI();
    }

    @Override
    public void robotInit() {
        SmartDashboard.putData(driveSubsystem);

        SmartDashboard.putData(new ResetEncoderCommand());
        SmartDashboard.putData(new ResetGyroCommand());

        SmartDashboard.putData(new DriveStraightCommand(1.0));

        SmartDashboard.putData(new TurnToHeadingCommand(0));
        SmartDashboard.putData(new TurnToHeadingCommand(90));
        SmartDashboard.putData(new TurnToHeadingCommand(180));
        SmartDashboard.putData(new TurnToHeadingCommand(270));

        SmartDashboard.putData(new AutoCenterPositionCommand());

        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    public void disabledInit() {
        // TODO: Method stub
    }

    @Override
    public void autonomousInit() {
        // TODO: Method stub
    }

    @Override
    public void teleopInit() {
        // TODO: Method stub
    }

    @Override
    public void testInit() {
        // TODO: Method stub
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putData(Scheduler.getInstance());
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledPeriodic() {
        // TODO: Method stub
    }

    @Override
    public void autonomousPeriodic() {
        // TODO: Method stub
    }

    @Override
    public void teleopPeriodic() {
        // TODO: Method stub
    }

    @Override
    public void testPeriodic() {
        // TODO: Method stub
    }
}
