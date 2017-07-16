package tap4fun.krow;

import robocode.ScannedRobotEvent;
import robocode.AdvancedRobot;

/*package*/ class Utilities {
	public static double regulize(double r){
		if (r > Math.PI)
			return r - 2.0 * Math.PI;
		if (r < -Math.PI)
			return r + 2.0 * Math.PI;
		return r;
	}
}

/*package*/ class Enemy {
	public double x, y;
	public String name = null;
	public double heading = 0.0;
	public double bearing = 0.0;
	public double distance = 0.0;
	public double direction = 0.0;
	public double velocity = 0.0;
	public double energy = 100.0;
	
	public Enemy() {	
	}
	
	public Enemy(ScannedRobotEvent e, AdvancedRobot me) {
		name = e.getName();
		heading = e.getHeadingRadians();
		bearing = e.getBearingRadians();
		distance = e.getDistance();
		direction = bearing + me.getHeadingRadians();
		velocity = e.getVelocity();
		energy = e.getEnergy();
		x = me.getX() + Math.sin(direction) * distance;
		y = me.getY() + Math.cos(direction) * distance;
	}
	
	public void update(ScannedRobotEvent e, AdvancedRobot me) {
		name = e.getName();
		heading = e.getHeadingRadians();
		bearing = e.getBearingRadians();
		distance = e.getDistance();
		direction = bearing + me.getHeadingRadians();
		velocity = e.getVelocity();
		energy = e.getEnergy();
		x = me.getX() + Math.sin(direction) * distance;
		y = me.getY() + Math.cos(direction) * distance;
	}
}
