package frc.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoModeSelector {

    private SendableChooser<Command> autoModes;

    public AutoModeSelector(){
        autoModes = new SendableChooser<Command>();
    }

    private static AutoModeSelector autoModeSelector = null;
    public static AutoModeSelector getInstance() {
        if (autoModeSelector == null) {
            autoModeSelector = new AutoModeSelector();
        }
        return autoModeSelector;
    }

    public void addDefaultOption(String modeName, Command command) {
        autoModes.setDefaultOption(modeName, command);
    }

    public void addOption(String modeName, Command command) {
        autoModes.addOption(modeName, command);
    }

    public Command getAutoMode() {
        return autoModes.getSelected();
    }
    
}
