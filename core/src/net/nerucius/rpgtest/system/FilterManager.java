package net.nerucius.rpgtest.system;

import com.badlogic.gdx.physics.box2d.Filter;

import box2dLight.Light;

/**
 * Completely static class containing all game contact filters for Box2d.
 */
public class FilterManager {

	public static final short CAT_LIGHT = 0x0001;
	public static final short CAT_OCCLUSION = 0x0002;
	public static final short CAT_COLLISION = 0x0004;
	public static final short CAT_PLAYER = 0x0008;
	public static final short CAT_EVENT = 0x0010;
	public static final short CAT_SENSOR = 0x0020;


	public static final Filter NO_CLIP;
	public static final Filter LIGHT_FILTER;
	public static final Filter OCCLUSION_FILTER;
	public static final Filter COLLISION_FILTER;
	public static final Filter PLAYER_FILTER;
	public static final Filter EVENT_FILTER;
	public static final Filter SENSOR_FILTER;

	static {
		// NoClip filter
		NO_CLIP = new Filter();
		NO_CLIP.categoryBits = 0x0;
		NO_CLIP.maskBits = 0x0;
		NO_CLIP.groupIndex = -1;

		// Filter for light itself, should only collide with shadow casters
		LIGHT_FILTER = new Filter();
		LIGHT_FILTER.categoryBits = CAT_LIGHT;
		LIGHT_FILTER.maskBits = CAT_OCCLUSION;

		// Filter for fixtures that cast a shadow
		OCCLUSION_FILTER = new Filter();
		OCCLUSION_FILTER.categoryBits = CAT_OCCLUSION;
		OCCLUSION_FILTER.maskBits = CAT_LIGHT;

		// Filter for fixtures that collide with stuff but dont cast a shadow
		COLLISION_FILTER = new Filter();
		COLLISION_FILTER.categoryBits = CAT_COLLISION;
		COLLISION_FILTER.maskBits = CAT_PLAYER;

		// Filter for players
		PLAYER_FILTER = new Filter();
		PLAYER_FILTER.categoryBits = CAT_PLAYER;
		PLAYER_FILTER.maskBits = CAT_COLLISION;

		// Filter for Events and sensors
		EVENT_FILTER = new Filter();
		EVENT_FILTER.categoryBits = CAT_EVENT;
		EVENT_FILTER.maskBits = CAT_SENSOR;

		// Filter for the player Sensor
		SENSOR_FILTER = new Filter();
		SENSOR_FILTER.categoryBits = CAT_SENSOR;
		SENSOR_FILTER.maskBits = CAT_EVENT;


		Light.setGlobalContactFilter(LIGHT_FILTER);
	}
}
