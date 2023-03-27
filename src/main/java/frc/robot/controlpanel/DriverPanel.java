package frc.robot.controlpanel;

import frc.robot.Constants;
import frc.robot.lib.frc7682.FusionJoystick;

public class DriverPanel implements IDriverPanel{

    private FusionJoystick driverStick;
    private boolean isReversed = false;

    DriverPanel(FusionJoystick driverStick){
        this.driverStick = new FusionJoystick(Constants.JoystickConstants.DRIVER_CONTROLLER_PORT);
    }

    public void configureJoystick(){
        driverStick.setDeadBand(0.1);
        driverStick.setSlewRate(0.5);
    }


    @Override
    public double speed() {
        return driverStick.getRawAxis(1) * (isReversed ? -1 : 1);
    }

    @Override
    public double rotation() {
        return driverStick.getRawAxis(4) * (isReversed ? -1 : 1);
    }

    @Override
    public void reverseDrive() {
        setReversed(!isReversed);
    }
    
    private void setReversed(boolean isReversed){
        this.isReversed = isReversed;
    }

}