package org.team1294.firstpowerup.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team1294.firstpowerup.robot.subsystems.ExampleSubsystem;

/**
 * The main class for the Robot. This handles the creation of the various
 * subsystems of the robot, and the timing of when events happen. Each robot
 * mode has an "init" and a "periodic" method. The "init" event happens one
 * time when the drivers' station (DS) starts an op-mode, and the "periodic"
 * event happens every time a packet is received from the DS (~20 ms).
 */
public class Robot extends IterativeRobot {
    public static ExampleSubsystem exampleSubsystem;

    public static OI oi;

    /**
     * Constructor for Robot(). This is where
     * {@link edu.wpi.first.wpilibj.command.Subsystem Subsystems} and the
     * {@link OI} should be initialized.
     */
    public Robot() {
        exampleSubsystem = new ExampleSubsystem();

        // OI has to be initialized AFTER the Subsystems, because the OI has
        // Buttons which refernce Commands which use the Subsystems
        oi = new OI();
    }

    @Override
    public void robotInit() {
        // TODO: Method stub
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
