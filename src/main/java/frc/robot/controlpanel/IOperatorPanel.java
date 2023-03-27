package frc.robot.controlpanel;

import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;

public interface IOperatorPanel {

    DesiredPosition desiredTargetPosition();
    boolean GETTING();
    boolean POSTING();
    boolean TURTLING();
    boolean BALANCING();
    boolean MANUAL();

    double shoulderDegrees();
    double xAxis();
    double yAxis();

}
