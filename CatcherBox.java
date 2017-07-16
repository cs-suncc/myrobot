package tap4fun.krow;

import robocode.*;
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
		double offset = Utilities.regulize(current_enemy.direction - getRadarHeadingRadians());
		if(Math.abs(offset) < 1e-6)
			setTurnRadarRightRadians(0.05);
		else
			setTurnRadarRightRadians(offset * 1.2);
		System.out.println("Catched and turn " + offset);
	}
	
}
