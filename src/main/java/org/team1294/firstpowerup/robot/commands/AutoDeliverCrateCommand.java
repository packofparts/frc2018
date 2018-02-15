package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.PrintCommand;

public class AutoDeliverCrateCommand extends ConditionalCommand {
    public AutoDeliverCrateCommand() {
        super("AutoDeliverCrateCommand", new PrintCommand("This is where we would deliver a cube"));
    }

    @Override
    protected boolean condition() {
        CommandGroup parent = getGroup();
        return parent instanceof AutoSidePositionCommand && ((AutoSidePositionCommand) parent).getShouldDeliverCube();
    }
}
