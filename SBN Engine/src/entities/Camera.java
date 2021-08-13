package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

public class Camera
{
	
	public Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 20; // y
	private float yaw 	= 40; // x
 	private float roll  = 0;  // y/x
 	
 	private float rotationSpeed = 10.0f;
 	private float speed = 5.0f;
 	
 	private boolean gravity = true;
 	private boolean isInAir = false;
 	
 	public void move(Terrain terrain) {
 		
 		yaw += Mouse.getDX() / rotationSpeed;
 		pitch -= Mouse.getDY() / rotationSpeed;
 		
 		if(pitch <= -90) {
 			pitch = -90;
 		}
 		
 		if(pitch >= 90) {
 			pitch = 90;
 		}
 		
 		float terrainHeight = terrain.getHeightOfTerrain(position.x, position.z) + 35.0f;
		if(position.y < terrainHeight) {
			position.y = terrainHeight;
		}
 		
 		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
 	        position.z += -(float)Math.cos(Math.toRadians(yaw)) * speed;
 	        position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;
 	    }
 	    else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
 	        position.z -= -(float)Math.cos(Math.toRadians(yaw)) * speed;
 	        position.x -= (float)Math.sin(Math.toRadians(yaw)) * speed;
 	    }
 	    if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
 	        position.z += (float)Math.sin(Math.toRadians(yaw)) * speed;
 	        position.x += (float)Math.cos(Math.toRadians(yaw)) * speed;
 	    }
 	    else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
 	        position.z -= (float)Math.sin(Math.toRadians(yaw)) * speed;
 	        position.x -= (float)Math.cos(Math.toRadians(yaw)) * speed;
 	    }
 	    
 	    if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) 
 	    {
 	    	if(gravity) 
 	    	{
 	    		if(position.y > (terrainHeight + 20.0f)) 
 	    		{
 	    			isInAir = true;
 	    		}
 	    		else 
 	    		{
 	    			isInAir = false;
 	    		}
 	    		
 	    		if(!isInAir) 
 	    		{ 	    			
 	    			position.y += speed * 1.5f;
 	    		}
 	    	}
 	    	else 
 	    	{
 	    		position.y += speed;
 	    	}
 	    }
 	    
 	    if(Keyboard.isKeyDown(Keyboard.KEY_G)) { 
 	    	gravity = !gravity; 	    	
 	    }
 	    
 	    if(gravity)  {
 	    	position.y -= speed * 0.02f + 3;
 	    }
 	    else {
 	    	position.y = getPosition().y;
 	    	if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
 	    		position.y -= speed * 1.5f;
 	    	}
 	    }
 	}
 	
	public Vector3f getPosition() 
	{
		return position;
	}

	public float getPitch() 
	{
		return pitch;
	}

	public float getYaw() 
	{
		return yaw;
	}

	public float getRoll() 
	{
		return roll;
	}
}
