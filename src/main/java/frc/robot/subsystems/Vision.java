package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;

public class Vision extends Subsystem{

    public PhotonCamera photonCamera;
    private PhotonTrackedTarget currentTarget;
    private PhotonTrackedTarget lastTarget = new PhotonTrackedTarget();

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

    public PhotonTrackedTarget bestAprilTarget(){
        try{
            currentTarget = photonCamera.getLatestResult().getBestTarget();
        }catch(NullPointerException exception){
            exception.printStackTrace();
        }

        if(currentTarget != null){
            lastTarget = photonCamera.getLatestResult().getBestTarget();
            return currentTarget;
        }
       return lastTarget;
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
