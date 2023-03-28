package frc.robot.controlpanel;

import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;

public interface IOperatorPanel {

    DesiredPosition desiredTargetPosition();

    double shoulderDegrees();
    double xAxis();
    double yAxis();
    boolean getIn();
    boolean getOut();

}
