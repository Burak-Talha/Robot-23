package frc.robot.controlpanel;

import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;
import frc.robot.subsystems.SuperStructure.SystemState;

public interface IOperatorPanel {

    DesiredPosition desiredTargetPosition();
    SystemState GETTING();
    SystemState POSTING();
    SystemState TURTLING();
    SystemState BALANCING();
    SystemState MANUAL();

    double shoulderDegrees();
    double turretDegrees();

}
