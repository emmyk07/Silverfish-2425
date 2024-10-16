package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp Test V1")
public class TeleOp_testV1 extends LinearOpMode {

    Servo Thing1;

    Servo Elbow;

    Servo Thing2;

    DcMotor sideSlide;

    DcMotor FL;
    DcMotor BL;
    DcMotor FR;
    DcMotor BR;

    DcMotor upSlide;
    double tgt = 0;
    double tgtslide;

    //Slides Pos Variables
    int sideSlidePos;
    double upSlidePos;

    @Override
    public void runOpMode() throws InterruptedException {

        //HardwareMap

        Thing1 = hardwareMap.get(Servo.class, "Thing1");

        Elbow = hardwareMap.get(Servo.class, "Elbow");

        Thing2 = hardwareMap.get(Servo.class, "Thing2");

        sideSlide = hardwareMap.get(DcMotor.class, "sideSlide");

        upSlide = hardwareMap.get(DcMotor.class, "upSlide");

        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Declare motor position variables

        sideSlidePos = sideSlide.getCurrentPosition();
        upSlidePos = upSlide.getCurrentPosition();


        // Motor Configuration

        Motor_Config();

        waitForStart();

        sideSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        upSlide.setPower(1);
        Thing2.setPosition(0);
        sideSlide.setTargetPosition(0);
        upSlide.setTargetPosition(150);

        while(opModeIsActive()) {
            Move();


            //Thing1 and Elbow Button Control

            if (gamepad1.a) {
                Thing1.setPosition(0);
            } else if (gamepad1.y) {
                Thing1.setPosition(1);
            } else {
                Thing1.setPosition(0.5);
            }
            if (gamepad1.x) {
                Elbow.setPosition(0.9);

            } else if (gamepad1.b) {
                Elbow.setPosition(0.24);
            }


            telemetry.addData("Elbow", Elbow.getPosition());
            telemetry.addData("Thing1", Thing1.getPosition());
            telemetry.addData("Thing2", Thing2.getPosition());
            telemetry.addData("SideSlide", sideSlidePos);
            telemetry.addData("UpSlide", upSlidePos);
            telemetry.update();

            //Servo Test code
//            tgt = -this.gamepad1.left_stick_y;
//            Thing2.setPosition(tgt);

            if (gamepad2.x) {
                sideSlidePos += 100;

            } else if (gamepad2.b) {
                sideSlidePos -= 100;
            }

            if (gamepad1.dpad_right) {
                Thing2.setPosition(0);
            } else if (gamepad1.dpad_left) {
                Thing2.setPosition(0.8);
            }


            sideSlidePos = Math.max(Math.min(sideSlidePos, 0), -2100);
            upSlidePos = Math.max(Math.min(upSlidePos, 0), -3000);

            if (gamepad1.dpad_up) {
                upSlide.setTargetPosition(3000);
            } else if (gamepad1.dpad_down) {
                upSlide.setTargetPosition(150);
            }

            sideSlide.setTargetPosition(sideSlidePos);
            sideSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            upSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            sideSlide.setPower(1);

            while (upSlide.isBusy() && opModeIsActive()) {
                idle();
            }
        }


    }

    public void Motor_Config()
    {
        sideSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        upSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        upSlide.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void Move() {
        // The Y axis of a joystick ranges from -1 in its topmost position to +1 in its bottommost position.
        // We negate this value so that the topmost position corresponds to maximum forward power.
        BR.setPower(0.5 * (1 * -gamepad1.left_stick_y + 1 * (1 * gamepad1.left_stick_x - gamepad1.right_stick_x)));
        BL.setPower(0.5 * (1 * gamepad1.left_stick_y + 1 * (1 * gamepad1.left_stick_x - gamepad1.right_stick_x)));
        // The Y axis of a joystick ranges from -1 in its topmost position to +1 in its bottommost position.
        // We negate this value so that the topmost position corresponds to maximum forward power.
        FR.setPower(0.5 * (1 * -gamepad1.left_stick_y + 1 * (1 * -gamepad1.left_stick_x - gamepad1.right_stick_x)));
        FL.setPower(0.5 * (1 * gamepad1.left_stick_y + 1 * -(1 * gamepad1.left_stick_x + gamepad1.right_stick_x)));
    }


}
