// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.AutoModeSelector;
import frc.robot.controlpanel.DriverPanel;
import frc.robot.controlpanel.IDriverPanel;
import frc.robot.controlpanel.IOperatorPanel;
import frc.robot.controlpanel.OperatorPanel;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.SuperStructure;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.SuperStructure.SystemState;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  private AutoModeSelector autoModeSelector = AutoModeSelector.getInstance();
  private Command m_autonomousCommand;

  IOperatorPanel operatorPanel = new OperatorPanel();
  IDriverPanel driverPanel = new DriverPanel();

  SuperStructure superStructure = SuperStructure.getInstance();
  Drive drive = Drive.getInstance();
  Arm arm = Arm.getInstance();
  Turret turret = Turret.getInstance();
  Intake intake = Intake.getInstance();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    drive.openBrakeMode();
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = autoModeSelector.getAutoMode();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // Mode Chain
    if(driverPanel.POSTING()){  
      superStructure.setTargetFinderDesiredPosition(operatorPanel.desiredTargetPosition());
      superStructure.setSystemState(SystemState.POSTING);
    } else if(driverPanel.GETTING()){
      superStructure.setSystemState(SystemState.GETTING);
    } else if(driverPanel.BALANCING()){
      superStructure.setSystemState(SystemState.BALANCING);
    } else if(driverPanel.MANUAL()){
      superStructure.setSystemState(SystemState.MANUAL);
    }

    // Gripper chain
    if(operatorPanel.getIn()){
      intake.manualIn();
    }
    else if(operatorPanel.getOut()){
      intake.manualOut();
    }
    else{
      intake.stop();
    }

    // Set Target Position
    superStructure.setTargetFinderDesiredPosition(operatorPanel.desiredTargetPosition());

    // Manual PID Control
    drive.cleverDrive(driverPanel.speed(), driverPanel.rotation());
    arm.controlArm(operatorPanel.shoulderDegrees(), operatorPanel.yAxis());
    //turret.controlTurret(operatorPanel.yAxis(), operatorPanel.xAxis());
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
