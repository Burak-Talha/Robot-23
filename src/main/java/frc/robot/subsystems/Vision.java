package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;

public class Vision extends Subsystem{

    private PhotonCamera photonCamera;

    public static Vision vision = null;

    Vision(){
        photonCamera = new PhotonCamera(Constants.VisionConstants.CAM_NAME);
    }

    public static Vision getInstance(){
        if(vision == null){
            vision = new Vision();
        }
        return vision;
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
    
}
