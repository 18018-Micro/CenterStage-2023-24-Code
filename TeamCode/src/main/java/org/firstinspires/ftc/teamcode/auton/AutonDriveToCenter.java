package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutonDriveToCenter extends LinearOpMode{


    @Override
    public void runOpMode() {

        DcMotor cm1 = hardwareMap.dcMotor.get("chm1");
        DcMotor cm2 = hardwareMap.dcMotor.get("chm2");
        DcMotor cm3 = hardwareMap.dcMotor.get("chm3");
        DcMotor cm4 = hardwareMap.dcMotor.get("chm4");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intakeMotor");

        cm1.setDirection(DcMotorSimple.Direction.FORWARD);
        cm2.setDirection(DcMotorSimple.Direction.REVERSE);
        cm3.setDirection(DcMotorSimple.Direction.REVERSE);
        cm4.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (opModeIsActive()) {

           cm1.setPower(00.35);
           cm2.setPower(00.35);
           cm3.setPower(00.35);
           cm4.setPower(00.35);
           sleep(700);
           cm1.setPower(0);
           cm2.setPower(0);
           cm3.setPower(0);
           cm4.setPower(0);

//            cm1.setPower(-00.35);
//            cm2.setPower(-00.35);
//            cm3.setPower(-00.35);
//            cm4.setPower(-00.35);
//            sleep(750);
//            cm1.setPower(0);
//            cm2.setPower(0);
//            cm3.setPower(0);
//            cm4.setPower(0);

            intakeMotor.setPower(0.2);
            sleep(3000);
            intakeMotor.setPower(0);

            while (opModeIsActive()) {


            }

        }

    }
}
