package frc.robot.lib.frc7682;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;

public class TargetFinder {

    private HashMap<String, Target> targets = new HashMap<>();

    private Pose2d robotPose;

    private String currentTargetName;
    private Target currentTarget;
    public static TargetFinder targetFinder = null;

    public TargetFinder(Pose2d initialPose){
        robotPose = initialPose;
    }
    public TargetFinder(){
        robotPose = new Pose2d();
    }


    public static TargetFinder getInstance(){
        if(targetFinder == null){
            targetFinder = new TargetFinder();
        }
        return targetFinder;
    }

    // Should work periodically
    public void update(Pose2d estimatedPose2d){
        robotPose = estimatedPose2d;
    }

    public Target bestTarget(){
        // This methods will return best target by pose
        return null;
    }

    public void resetTargets(){
        targets.clear();
    }

    public void CompleteCurrentTarget(){
        if(targets.remove(currentTargetName, currentTarget)){
            currentTarget.isCompleted = true;
            targets.put(currentTargetName, currentTarget);
        }
    }

    public void addTarget(String targetName ,Target target){
        targets.put(targetName, target);
    }

    public void addTargets(Pose3d[] poses){
        for(int i=0;i<poses.length; i++){
            targets.put(Boolean.FALSE, poses[i]);
        }
        System.out.println("Targets added!!");
    }
}
