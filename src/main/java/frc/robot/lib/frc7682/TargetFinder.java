package frc.robot.lib.frc7682;

import java.util.List;
import java.util.stream.Collectors;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.FieldConstants;

public class TargetFinder {


    private Pose3d armPose;
    private String currentTargetName;
    private Target lastTarget;
    private List<Target> targets;

    public enum TargetType {
        GET,
        POST
    }

    public enum ObjectType{
        CONE,
        CUBE,
        BOTH
    }

    public enum DesiredPosition{
        UP,
        MID,
        DOWN
    }

    public static TargetFinder targetFinder = null;

    public TargetFinder(){
        armPose = new Pose3d();
    }

    public static TargetFinder getInstance(){
        if(targetFinder == null){
            targetFinder = new TargetFinder();
        }
        return targetFinder;
    }

    // Should work periodically
    public void update(Pose3d estimatedArmPose){
        armPose = estimatedArmPose;
    }

    public Target bestTarget(TargetType targetType, ObjectType objectType, DesiredPosition desiredPosition){
        // This methods will return best target by pose
        //armPose.getTranslation().getDistance()
        Target target = null;

        // Choosing alliance specific targets
        targets = FieldConstants.LoadingZone.BLUE_TARGETS;
        if(DriverStation.getAlliance() == DriverStation.Alliance.Red){
            targets = FieldConstants.LoadingZone.RED_TARGETS;
        }

        // Find best target based on criteria and distance
        List<Target> filteredTarget = targets.stream().filter(x -> x.objectType == objectType && x.isCompleted == false && x.targetType == targetType).collect(Collectors.toList());
        // Get minimum distance target
        double minDistance = Double.MAX_VALUE;
        for(Target t : filteredTarget){
            double distance = armPose.getTranslation().getDistance(t.target.getTranslation());
            if(distance < minDistance){
                target = t;
            }
        }
        if(target != null){
        lastTarget = target;
        }
        return lastTarget;
    }

    public void completeTarget(){
        if(lastTarget != null){
            lastTarget.isCompleted = true;
        }
    }
}
