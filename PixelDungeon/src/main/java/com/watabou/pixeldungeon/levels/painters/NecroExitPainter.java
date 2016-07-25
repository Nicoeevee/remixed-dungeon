package com.watabou.pixeldungeon.levels.painters;

import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Room;
import com.watabou.pixeldungeon.levels.Terrain;

public class NecroExitPainter extends ExitPainter {

	public static void paint( Level level, Room room ) {
		//fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.EMPTY );
		
		for (Room.Door door : room.connected.values()) {
			door.set( Room.Door.Type.REGULAR );
		}
		
		level.setExit((room.top+room.bottom) * level.getWidth()/2 + (room.left + room.right) / 2,1);
		set( level, level.getExit(1), Terrain.LOCKED_EXIT );
	}
}
