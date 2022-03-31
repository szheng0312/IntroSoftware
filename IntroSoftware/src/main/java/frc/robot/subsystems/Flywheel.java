package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;

public class Flywheel extends Subsystem{
    public static Flywheel mFlywheel;
    public PeriodicIO mPeriodicIO;
    TalonFX master, slave;
    
    public class PeriodicIO{
        double velocityDemand;
        double currentSpeed;
    }

    public Flywheel() {
        master = new TalonFX(Constants.master);
        slave = new TalonFX(Constants.slave);
        slave.set(ControlMode.Follower, Constants.master);
        master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
    }

    public synchronized static Flywheel getInstance() {
        if (mFlywheel == null){
            mFlywheel = new Flywheel();
        }
        return mFlywheel;
    }

    public void setRPM(double rpmDemand) {
        mPeriodicIO.velocityDemand = rpmDemand / 60 / 10 * Constants.kFlywheelTicksPerRevolution;
    }
    @Override
    public void readPeriodicInputs() {
        mPeriodicIO.currentSpeed = master.getSelectedSensorPosition(0);
    }

    @Override
    public void writePeriodicOutputs() {
        master.set(ControlMode.Velocity, mPeriodicIO.velocityDemand);
    }
     
    
    @Override
    public void stop() {
        master.set(ControlMode.PercentOutput, 0);
        
    }

    @Override
    public boolean checkSystem() {
        return false;
    }

    @Override
    public void outputTelemetry() {
        // TODO Auto-generated method stub
        
    }
    
}
