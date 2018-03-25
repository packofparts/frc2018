package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.subsystems.ArmSubsystem;

/**
 * @author Austin Jenchi (timtim17)
 */
public class PresetCommand extends CommandGroup {
    private final int height;

    @Override
    protected void initialize() {
        super.initialize();
        Robot.armSubsystem.setWristIn();
    }

    public PresetCommand(int height) {
        super("Preset command");
        this.height = height;
        addSequential(new ExtendoArmInOutCommand(ArmSubsystem.Telescope.IN));
        addSequential(new SetArmHeightCommand(height));
        addSequential(new ConditionalCommand(new ExtendoArmInOutCommand(ArmSubsystem.Telescope.OUT)) {
            @Override
            protected boolean condition() {
                return height == ArmSubsystem.ArmHeight.FLOOR.height;
            }
        });
        addSequential(new InstantCommand() {
            @Override
            protected void initialize() {
                Robot.armSubsystem.setWristOut();
            }
        });
        requires(Robot.armSubsystem);
    }
}
