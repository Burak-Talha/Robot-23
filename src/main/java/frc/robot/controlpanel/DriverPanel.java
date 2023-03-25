package frc.robot.controlpanel;

import frc.robot.Constants;
import frc.robot.lib.frc7682.FusionJoystick;

public class DriverPanel implements IDriverPanel{

    private FusionJoystick driverStick;

    DriverPanel(FusionJoystick driverStick){
        this.driverStick = new FusionJoystick(Constants.JoystickConstants.DRIVER_CONTROLLER_PORT);
    }

    public void configureJoystick(){
        driverStick.setDeadBand(0.1);
        driverStick.setSlewRate(0.5);
    }

    @Override
    public double speed() {
        return driverStick.getRawAxis(1);
    }

    @Override
    public double rotation() {
        return driverStick.getRawAxis(4);
    }

}