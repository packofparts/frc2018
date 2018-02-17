package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.DriveIntakeWithJoystickCommand;

/**
 * @author Austin Jenchi (timtim17)
 */
public class IntakeSubsystem extends Subsystem {
    private TalonSRX leftTalon;
    private TalonSRX rightTalon;
    private DigitalInput beamBreak;

    public IntakeSubsystem() {
        super("Intake Subsystem");
        leftTalon = new TalonSRX(RobotMap.TALON_INTAKE_LEFT);
        rightTalon = new TalonSRX(RobotMap.TALON_INTAKE_RIGHT);
        beamBreak = new DigitalInput(RobotMap.SENSOR_INTAKE_BEAMBREAK);
    }

    public void driveIntake(double output) {
        if (output < 0 && hasCube()) {
            // assuming negative is in, and we already have a cube, stop the motor
            stop();
        } else {
            leftTalon.set(ControlMode.PercentOutput, output);
            rightTalon.set(ControlMode.PercentOutput, output);
        }
    }

    public void stop() {
        driveIntake(0);
    }

    public boolean hasCube() {
        return beamBreak.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Has Cube", hasCube());
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveIntakeWithJoystickCommand());
    }
}
