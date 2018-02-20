package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.DriveArmWithJoystickCommand;

/**
 * @author Abhinav Diddee (heatblast016)
 */
public class ArmSubsystem extends Subsystem {
    private TalonSRX armMotor;
    private TalonSRX wristMotor;
    private TalonSRX extendMotor;
    private Wrist currentStatus;
    private AnalogPotentiometer armPot;

    private enum Wrist {
        IN(0), OUT(10);

        private final double angle;

        Wrist(double angle) {
            this.angle = angle;
        }


    }
    public ArmSubsystem() {
        super("Arm Subsystem");
        armMotor = new TalonSRX(RobotMap.TALON_ARM);
        wristMotor = new TalonSRX(RobotMap.TALON_WRIST);
        extendMotor = new TalonSRX(RobotMap.TALON_ARM_EXTENSION);
        currentStatus = Wrist.IN;

        wristMotor.setNeutralMode(NeutralMode.Brake);
        extendMotor.setNeutralMode(NeutralMode.Brake);
        armMotor.setNeutralMode(NeutralMode.Brake);

        armPot = new AnalogPotentiometer(RobotMap.SENSOR_POT);
    }
    public void toggleWristDeploy() {
        if (currentStatus == Wrist.OUT) {
            currentStatus = Wrist.IN;
        } else {
            currentStatus = Wrist.OUT;
        }
        wristMotor.set(ControlMode.Position, currentStatus.angle);
    }

    public void setArmHeight(double height) {
        armMotor.set(ControlMode.Position, height);
    }

    public void driveArmPercentOut(double percent) {
        armMotor.set(ControlMode.PercentOutput, percent);
    }

    public void driveExtendPercentOut(double percent) { extendMotor.set(ControlMode.PercentOutput, percent);}

    public void driveWristPercentOut(double percent) {
        wristMotor.set(ControlMode.PercentOutput, percent);
    }
    public double getPotOutputAngle() {
        return 72.0*armPot.get();   // todo fix
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveArmWithJoystickCommand());
    }

    public void setArmSoftLimits(int reverseSoftLimit, int forwardSoftLimit) {
        armMotor.configReverseSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT_PERIODIC);
        armMotor.configReverseSoftLimitThreshold(reverseSoftLimit, RobotMap.CTRE_TIMEOUT_PERIODIC);

        armMotor.configForwardSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT_PERIODIC);
        armMotor.configForwardSoftLimitThreshold(forwardSoftLimit, RobotMap.CTRE_TIMEOUT_PERIODIC);
    }

    @Override
    public void periodic() {

    }

    public double getExtensionLength() {
        return 0.0; // todo make this work
    }

    public double getWristAngle() {
        return 0.0; // todo
    }
}