package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants;
import frc.robot.FieldConstants;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
 
 public class Vision {
     public PhotonCamera photonCamera;
     private PhotonPoseEstimator photonPoseEstimator;
     private Pose2d pose2d = new Pose2d();
 
     public Vision() {
         // Change the name of your camera here to whatever it is in the PhotonVision UI.
         photonCamera = new PhotonCamera(Constants.VisionConstants.CAM_NAME);
 
             photonPoseEstimator =
                     new PhotonPoseEstimator(
                             FieldConstants.fieldLayout(), PoseStrategy.LOWEST_AMBIGUITY, photonCamera, Constants.VisionConstants.CAMERA_TO_ROBOT);
     }

     public static Vision vision;

        public static Vision getInstance() {
            if (vision == null) {
                vision = new Vision();
            }
            return vision;
        }
 
     /**
      * @param estimatedRobotPose The current best guess at robot pose
      * @return an EstimatedRobotPose with an estimated pose, the timestamp, and targets used to create
      *     the estimate
      */
     public Optional<EstimatedRobotPose> getEstimatedGlobalPose() {
         if (photonPoseEstimator == null) {
             // The field layout failed to load, so we cannot estimate poses.
             return Optional.empty();
         }
         photonPoseEstimator.setReferencePose(pose2d);
         return photonPoseEstimator.update();
    }
}