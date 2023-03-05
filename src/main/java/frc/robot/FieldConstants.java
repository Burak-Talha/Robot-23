package frc.robot;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import frc.robot.lib.frc7682.Target;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;
import frc.robot.lib.frc7682.TargetFinder.ObjectType;
import frc.robot.lib.frc7682.TargetFinder.TargetType;

public class FieldConstants {

    public static class LoadingZone{
        
    public Rotation3d loadingZoneRotation = new Rotation3d(0, 0, 0);

    // Target rows are designed by scoring table opposite
        // Contains red alliance target positions
    public static final List<Target> RED_TARGETS = Arrays.asList(
                                                            // Cone targets
                                                            // 1st row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 3nd row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 4th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 6th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 7th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 8th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),

                                                            // Cube Targets
                                                            // 2nd row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            // 5th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            // 9th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),

                                                            new Target(aprilTags.get(5), ObjectType.BOTH, TargetType.GET, DesiredPosition.UP)
                                                            )
                                                            ;
    public static final List<Target> BLUE_TARGETS = Arrays.asList(
                                                            // Cone targets
                                                            // 1st row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 3nd row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 4th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 6th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 7th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            // 8th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),

                                                            // Cube Targets
                                                            // 2nd row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            // 5th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            // 9th row
                                                            new Target(new Pose3d(), ObjectType.BOTH, TargetType.POST, DesiredPosition.DOWN),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),

                                                            new Target(aprilTags.get(6), ObjectType.BOTH, TargetType.GET, DesiredPosition.UP)
                                                            );

    }
    // FMwill constants
    public static final Map<Integer, Pose3d> aprilTags =
      Map.of(
          1,
          new Pose3d(
              Units.inchesToMeters(610.77),
              Units.inchesToMeters(42.19),
              Units.inchesToMeters(18.22),
              new Rotation3d(0.0, 0.0, Math.PI)),
          2,
          new Pose3d(
              Units.inchesToMeters(610.77),
              Units.inchesToMeters(108.19),
              Units.inchesToMeters(18.22),
              new Rotation3d(0.0, 0.0, Math.PI)),
          3,
          new Pose3d(
              Units.inchesToMeters(610.77),
              Units.inchesToMeters(174.19), // FIRST's diagram has a typo (it says 147.19)
              Units.inchesToMeters(18.22),
              new Rotation3d(0.0, 0.0, Math.PI)),
          4,
          new Pose3d(
              Units.inchesToMeters(636.96),
              Units.inchesToMeters(265.74),
              Units.inchesToMeters(27.38),
              new Rotation3d(0.0, 0.0, Math.PI)),
          5,
          new Pose3d(
              Units.inchesToMeters(14.25),
              Units.inchesToMeters(265.74),
              Units.inchesToMeters(27.38),
              new Rotation3d()),
          6,
          new Pose3d(
              Units.inchesToMeters(40.45),
              Units.inchesToMeters(174.19), // FIRST's diagram has a typo (it says 147.19)
              Units.inchesToMeters(18.22),
              new Rotation3d()),
          7,
          new Pose3d(
              Units.inchesToMeters(40.45),
              Units.inchesToMeters(108.19),
              Units.inchesToMeters(18.22),
              new Rotation3d()),
          8,
          new Pose3d(
              Units.inchesToMeters(40.45),
              Units.inchesToMeters(42.19),
              Units.inchesToMeters(18.22),
              new Rotation3d()));
    
}
