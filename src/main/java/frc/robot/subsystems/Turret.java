package frc.robot.subsystems;

import frc.robot.lib.frc254.Subsystem;


// NOTE : Also should be setted velocity
public class Turret extends Subsystem {

    public static Turret turret;

    public static Turret getInstance(){
        if(turret == null){
            turret = new Turret();
        }
        return turret;
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean checkSystem() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void outputTelemetry() {
        // TODO Auto-generated method stub
        
    }

    public double getAngle(){
        return 0;
    }
    
}
