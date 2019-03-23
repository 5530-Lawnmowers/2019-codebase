/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.*;

public class LiftRobot extends Command {

  double startDownavatorPosition;
  double startElevatorPosition;
  double elevatorVelocity;
  double downavatorVelocity;
  int state;
  int counter;

  private static final double k_DOWNAVATOR_HEIGHT = 29;
  private static final double k_ELEVATOR_HEIGHT = 31;
  //Stage 1:
  private final double BASE_DOWNAVATOR_SPEED = 0.45;
  private final double BASE_ELEVATOR_SPEED = 0.6;
  //Stage 2:
  private final double DRIVE_FORWARD_TIME_S2 = 120;

  public LiftRobot() {
    requires(Robot.downavator);
    requires(Robot.elevator);
    requires(Robot.drivetrain);
    requires(Robot.arm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    state = 0;
    counter = 0;
    Downavator.downavatorSpark2.follow(Downavator.downavatorSpark1);
    Elevator.elevatorSpark2.follow(Elevator.elevatorSpark1);
    Elevator.elevatorSpark1.set(BASE_ELEVATOR_SPEED);
    Downavator.downavatorSpark1.set(BASE_DOWNAVATOR_SPEED);

    
    startDownavatorPosition = Downavator.downavatorSpark1.getEncoder().getPosition(); 
    startElevatorPosition = Elevator.elevatorSpark1.getEncoder().getPosition(); 
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    System.out.println("Downavator: " + Downavator.downavatorSpark1.get() + "Elevator: " + Elevator.elevatorSpark1.get());
    Robot.lights.setLightStage(state);
    switch(state){
      case 0:
        Downavator.downavatorDrive.set(.15);
			  Arm.armTRSX1.set(-.5);
        Elevator.elevatorSpark1.set(0.07 * ((Downavator.downavatorSpark1.getEncoder().getPosition() - startDownavatorPosition) - (Elevator.elevatorSpark1.getEncoder().getPosition() - startElevatorPosition)) + BASE_ELEVATOR_SPEED);
        if (Downavator.downavatorSpark1.getEncoder().getPosition() >= startDownavatorPosition + k_DOWNAVATOR_HEIGHT) {
          Downavator.downavatorSpark1.set(0.05);
        }
        if (Elevator.elevatorSpark1.getEncoder().getPosition() >= startElevatorPosition + k_ELEVATOR_HEIGHT){
          Elevator.elevatorSpark1.set(0.0875);
        }
		    if(Arm.armPot.get() >= Arm.MIN_ARM_HEIGHT) {
          Arm.armTRSX1.stopMotor();
        }
        if ((Downavator.downavatorSpark1.getEncoder().getPosition() >= startDownavatorPosition + k_DOWNAVATOR_HEIGHT) && (Elevator.elevatorSpark1.getEncoder().getPosition() >= startElevatorPosition + k_ELEVATOR_HEIGHT)) {
          Arm.armTRSX1.stopMotor();          
          Downavator.downavatorDrive.set(.7);
          state = 1;
        }
        break;
      case 1:
        counter ++;
        Elevator.elevatorSpark1.set(0.0875 - (0.05 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (k_ELEVATOR_HEIGHT + startElevatorPosition))));
        Downavator.downavatorSpark1.set(.05 - (0.05 * (Downavator.downavatorSpark1.getEncoder().getPosition() - (k_DOWNAVATOR_HEIGHT + startDownavatorPosition))));
        if(counter >= DRIVE_FORWARD_TIME_S2){
          Downavator.downavatorDrive.stopMotor();
          state = 2;
        }
        break;
      case 2:
        Downavator.downavatorSpark1.set(.05 - (0.05 * (Downavator.downavatorSpark1.getEncoder().getPosition() - (k_DOWNAVATOR_HEIGHT + startDownavatorPosition))));
        if( Elevator.elevatorSpark1.getEncoder().getPosition() > (startElevatorPosition - 1)){
          Elevator.elevatorSpark1.set(-0.3);
        } else {
          Elevator.elevatorSpark1.set(-0.03 - (0.05 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (startElevatorPosition - 1))));
          counter = 0;
          state = 3;
        }
        break;
      case 3:
        Elevator.elevatorSpark1.set(-0.03 - (0.05 * (Elevator.elevatorSpark1.getEncoder().getPosition() - (startElevatorPosition - 1))));
        Downavator.downavatorDrive.set(0.5);
        Drivetrain.frontLeftTSRX.set(0.3);
        Drivetrain.frontRightTSRX.set(0.3);
        Downavator.downavatorSpark1.set(.07 - (0.05 * (Downavator.downavatorSpark1.getEncoder().getPosition() - (k_DOWNAVATOR_HEIGHT + startDownavatorPosition))));
			  Arm.armTRSX1.set(55 * (Arm.armPot.get() - Arm.TARGET_HOLD_HEIGHT) + 0.03);
        counter ++;
        if (counter >= 100){
          Downavator.downavatorDrive.stopMotor();
          Drivetrain.frontRightTSRX.stopMotor();
          Drivetrain.frontLeftTSRX.stopMotor();
          state = 4;
        }
        break;
      case 4:
        Drivetrain.frontLeftTSRX.set(0.1);
        Drivetrain.frontRightTSRX.set(0.1);
        Downavator.downavatorSpark1.set(-0.25);
		  	Arm.armTRSX1.set(55 * (Arm.armPot.get() - Arm.TARGET_HOLD_HEIGHT) + 0.03);
        if(!Downavator.downavatorTopSwitch.get()){
          Downavator.downavatorSpark1.stopMotor();
          Drivetrain.frontLeftTSRX.stopMotor();
          Drivetrain.frontRightTSRX.stopMotor();
          counter = 0;
          state = 5;
          }
        break;
      case 5:
        Drivetrain.frontRightTSRX.set(0.2);
        Drivetrain.frontLeftTSRX.set(0.2);
			  Arm.armTRSX1.set(55 * (Arm.armPot.get() - Arm.TARGET_HOLD_HEIGHT) + 0.03);
        counter ++;
        if( counter >= 25){
          Drivetrain.frontLeftTSRX.stopMotor();
          Drivetrain.frontRightTSRX.stopMotor();
          state = 6;
        }
        break;
      default:
        break;        
    }
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return state == 6;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("FINISHED LIFT");
    Elevator.elevatorSpark1.stopMotor();
    Downavator.downavatorSpark1.stopMotor();
    Downavator.downavatorDrive.stopMotor();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Elevator.elevatorSpark1.stopMotor();
    Downavator.downavatorSpark1.stopMotor();
    Downavator.downavatorDrive.stopMotor();
  }
}
