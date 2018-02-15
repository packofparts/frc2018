package org.team1294.firstpowerup.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.commands.*;
import org.team1294.firstpowerup.robot.subsystems.ClimbSubsystem;
import org.team1294.firstpowerup.robot.subsystems.DriveSubsystem;
import org.team1294.firstpowerup.robot.subsystems.IntakeSubsystem;
import org.team1294.firstpowerup.robot.subsystems.VisionSubsystem;

/**
 * The main class for the Robot. This handles the creation of the various subsystems of the robot,
 * and the timing of when events happen. Each robot mode has an "init" and a "periodic" method. The
 * "init" event happens one time when the drivers' station (DS) starts an op-mode, and the
 * "periodic" event happens every time a packet is received from the DS (~20 ms).
 */
public class Robot extends IterativeRobot {

    public static DriveSubsystem driveSubsystem;
    public static VisionSubsystem visionSubsystem;
    public static ClimbSubsystem climbSubsystem;
//    public static ArmSubsystem armSubsystem;
    public static IntakeSubsystem intakeSubsystem;

    public static OI oi;

    private SendableChooser<Command> chooser = new SendableChooser<>();

    /**
     * Constructor for Robot(). This is where {@link edu.wpi.first.wpilibj.command.Subsystem
     * Subsystems} and the {@link OI} should be initialized.
     */
    public Robot() {
        visionSubsystem = new VisionSubsystem();
        driveSubsystem = new DriveSubsystem();
        climbSubsystem = new ClimbSubsystem();
//        armSubsystem = new ArmSubsystem();
        intakeSubsystem = new IntakeSubsystem();

        // OI has to be initialized AFTER the Subsystems, because the OI has
        // Buttons which reference Commands which use the Subsystems
        oi = new OI();
    }

    @Override
    public void robotInit() {
        SmartDashboard.putData(new ResetEncoderCommand());
        SmartDashboard.putData(new ResetGyroCommand());

//        chooser.addDefault("Left", new AutoSidePositionCommand("L"));
//        chooser.addObject("Center", new AutoCenterPositionCommand());
//        chooser.addObject("Right", new AutoSidePositionCommand("R"));
//        SmartDashboard.putData("Auto mode", chooser);

        SmartDashboard.putData(new AutoDriveCommand(1.0, 0, 0.5, 0.25));
        SmartDashboard.putData(new AutoDriveCommand(0, 0, 0.5, 0.75));
        SmartDashboard.putData(new AutoDriveCommand(0, 90, 0.5, 0.75));
        SmartDashboard.putData(new AutoDriveCommand(0, 180, 0.5, 0.75));
        SmartDashboard.putData(new AutoDriveCommand(0, 270, 0.5, 0.75));

//        SmartDashboard.putData(new AutoCenterPositionCommand());
//        SmartDashboard.putData(new AutoSidePositionCommand("L"));
//        SmartDashboard.putData(new AutoSidePositionCommand("R"));

//        SmartDashboard.putData(new AutoVisionTargetCommand(1.0));

        Robot.driveSubsystem.resetGyro();
        Robot.driveSubsystem.resetEncoders();
//        Robot.intakeSubsystem.resetEncoders();

        SmartDashboard.putData(Scheduler.getInstance());
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        // TODO: Method stub
    }

    @Override
    public void disabledPeriodic() {
        // TODO: Method stub
    }

    @Override
    public void autonomousInit() {
        chooser.getSelected().start();
    }

    @Override
    public void autonomousPeriodic() {
        // TODO: Method stub
    }

    @Override
    public void teleopInit() {
        // TODO: Method stub
    }

    @Override
    public void teleopPeriodic() {
        // TODO: Method stub
    }

    @Override
    public void testInit() {
        // TODO: Method stub
    }

    @Override
    public void testPeriodic() {
        // TODO: Method stub
    }
}
