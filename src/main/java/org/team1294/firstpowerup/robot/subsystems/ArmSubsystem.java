package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team1294.firstpowerup.robot.RobotMap;

/**
 * @author Austin Jenchi (timtim17)
 */
public class ArmSubsystem extends Subsystem {
    private TalonSRX motor;
    private AnalogPotentiometer potentiometer;

    public ArmSubsystem() {
        super("Arm Subsystem");
        motor = new TalonSRX(RobotMap.TALON_ARM);
        potentiometer = new AnalogPotentiometer(RobotMap.SENSOR_POT);
    }

    public void driveTalon(double output) {
        motor.set(ControlMode.PercentOutput, output);
    }

    public void stop() {
        driveTalon(0);
    }

    public double getPotOutput() {
        return potentiometer.get();
    }

    @Override
    public void periodic() {
        // do nothing
    }

    @Override
    protected void initDefaultCommand() {
//        setDefaultCommand();
    }
}
