package frc.robot.lib.frc7682;

import java.util.List;
import java.util.stream.Collectors;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.FieldConstants;

public class TargetFinder {


    private Pose3d armPose;
    private Target lastTarget;
    private List<Target> targets;
    private TargetType currnetTargetType = TargetType.NONE;

    public enum TargetType {
        GET,
        POST,
        NONE
    }

    public enum ObjectType{
        CONE,
        CUBE
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

    public void changeTargetType(TargetType targetType){
        currnetTargetType = targetType;
    }

    public Target bestTarget(ObjectType objectType, DesiredPosition desiredPosition){
        // This methods will return best target by pose
        //armPose.getTranslation().getDistance()
        Target target = null;

        // Choosing alliance specific targets
        targets = FieldConstants.LoadingZone.BLUE_TARGETS;
        if(DriverStation.getAlliance() == DriverStation.Alliance.Red){
            targets = FieldConstants.LoadingZone.RED_TARGETS;
        }

        // Find best target based on criteria and distance
        List<Target> filteredTarget = targets.stream().filter(x -> x.objectType == objectType && x.isCompleted == false && x.targetType == currnetTargetType && x.desiredPosition == desiredPosition).collect(Collectors.toList());
        // Get minimum distance target
        double minDistance = Double.MAX_VALUE;
        for(Target t : filteredTarget){
            double distance = armPose.getTranslation().getDistance(t.target.getTranslation());
            if(distance < minDistance){
                target = t;
            }
        }

        // Uyari mesaji : SmartDashboard
        if(target != null){
        lastTarget = target;
        }

        if(target==null){
            System.out.println("No target found!!! : Using last target");
            return lastTarget;
        }

        return target;
    }

    public void completeTarget(){
        if(lastTarget != null){
            lastTarget.isCompleted = true;
        }
    }
}
