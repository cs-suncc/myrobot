package tap4fun.krow;

import robocode.*;
import robocode.util.Utils;
import tap4fun.krow.Enemy;
import tap4fun.krow.Utilities;

public class CatcherBox extends AdvancedRobot {
	private Enemy current_enemy;
	
	@Override
	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		current_enemy = new Enemy();
		
		while (true) {
			if (getGunTurnRemainingRadians() < 1e-2)
				setFire(3);
			if (current_enemy.name == null) {
				setTurnRadarRight(360);
				execute();
			} else {
				execute();
			}
		}
	}
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		current_enemy.update(e, this);
		double offset = Utils.normalRelativeAngle(current_enemy.direction - getRadarHeadingRadians());
		double gun_offset = Utils.normalRelativeAngle(current_enemy.direction - getGunHeadingRadians());
		//boolean rdo = true, go = true;
		if(Math.abs(offset) < 1e-6) {
			setTurnRadarRightRadians(0.05);
		} else {
			setTurnRadarRightRadians(offset * 1.2);
		}
		if(Math.abs(gun_offset) < 0.01) {
			setFire(3);
		} else {
			/*double absoluteBearing = current_enemy.bearing + getHeadingRadians();
			setTurnGunRightRadians(
					Utils.normalRelativeAngle(
							absoluteBearing - 
							getGunHeadingRadians() + 
							(current_enemy.velocity * Math.sin(current_enemy.heading - absoluteBearing) / 13.0)
					)
			);*/
			final double FIREPOWER = 2;
		    final double ROBOT_WIDTH = 16,ROBOT_HEIGHT = 16;
		    // Variables prefixed with e- refer to enemy, b- refer to bullet and r- refer to robot
		    final double eAbsBearing = getHeadingRadians() + current_enemy.bearing;
		    final double rX = getX(), rY = getY(),
		        bV = Rules.getBulletSpeed(FIREPOWER);
		    final double eX = rX + current_enemy.distance * Math.sin(eAbsBearing),
		        eY = rY + current_enemy.distance * Math.cos(eAbsBearing),
		        eV = current_enemy.velocity,
		        eHd = current_enemy.heading;
		    // These constants make calculating the quadratic coefficients below easier
		    final double A = (eX - rX)/bV;
		    final double B = eV/bV*Math.sin(eHd);
		    final double C = (eY - rY)/bV;
		    final double D = eV/bV*Math.cos(eHd);
		    // Quadratic coefficients: a*(1/t)^2 + b*(1/t) + c = 0
		    final double a = A*A + C*C;
		    final double b = 2*(A*B + C*D);
		    final double c = (B*B + D*D - 1);
		    final double discrim = b*b - 4*a*c;
		    if (discrim >= 0) {
		        // Reciprocal of quadratic formula
		        final double t1 = 2*a/(-b - Math.sqrt(discrim));
		        final double t2 = 2*a/(-b + Math.sqrt(discrim));
		        final double t = Math.min(t1, t2) >= 0 ? Math.min(t1, t2) : Math.max(t1, t2);
		        // Assume enemy stops at walls
		        final double endX = Utilities.limit(
		            eX + eV*t*Math.sin(eHd),
		            ROBOT_WIDTH/2, getBattleFieldWidth() - ROBOT_WIDTH/2);
		        final double endY = Utilities.limit(
		            eY + eV*t*Math.cos(eHd),
		            ROBOT_HEIGHT/2, getBattleFieldHeight() - ROBOT_HEIGHT/2);
		        setTurnGunRightRadians(robocode.util.Utils.normalRelativeAngle(
		            Math.atan2(endX - rX, endY - rY)
		            - getGunHeadingRadians()));
		        setFire(FIREPOWER);
		    }
		//System.out.println("Catch and turn " + offset);
		}
	}
}
