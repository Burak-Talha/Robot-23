package frc.robot.controlpanel;

public interface IDriverPanel {

    double speed();
    double rotation();
    void reverseDrive();
    boolean GETTING();
    boolean POSTING();
    boolean TURTLING();
    boolean BALANCING();
    boolean MANUAL();
}
