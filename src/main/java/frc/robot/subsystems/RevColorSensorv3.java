package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;
import frc.robot.lib.frc7682.TargetFinder.ObjectType;

public class RevColorSensorv3 extends Subsystem{

    private final ColorSensorV3 colorSensor = new ColorSensorV3(edu.wpi.first.wpilibj.I2C.Port.kOnboard);

    // All of the colors that the color sensor can detect : TESTED
    private final Color purpleTarget = new Color(0.22, 0.33, 0.445);
    private final Color yellowTarget = new Color(0.40, 0.55, 0.057);
    private final Color unknownTarget = new Color(0.0, 0.0, 0.0);
    private ObjectType currentColor = ObjectType.UNKNOWN;
    private ColorMatch colorMatcher;

    private static RevColorSensorv3 instance = new RevColorSensorv3();

    RevColorSensorv3(){
        colorMatcher = new ColorMatch();
        colorMatcher.addColorMatch(purpleTarget);
        colorMatcher.addColorMatch(yellowTarget);
        colorMatcher.addColorMatch(unknownTarget);
    }

    public static RevColorSensorv3 getInstance() {
        if(instance == null){
            instance = new RevColorSensorv3();
        }
        return instance;
    }
    
    public ObjectType currentObjectType(){
        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        if (match.color == purpleTarget && confidence() > Constants.IntakeConstants.INTAKE_CONFIDANCE_DOWN_LIMIT) {
            currentColor = ObjectType.CUBE;
        } else if (match.color == yellowTarget && confidence() > Constants.IntakeConstants.INTAKE_CONFIDANCE_DOWN_LIMIT) {
            currentColor = ObjectType.CONE;
        } else {
            currentColor = ObjectType.UNKNOWN;
        }
        return currentColor;
    }

    public double confidence(){
        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        return match.confidence;
    }

    @Override
    public void stop() {
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
