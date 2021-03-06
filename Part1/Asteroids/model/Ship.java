package asteroids.model;

import asteroids.*;
import be.kuleuven.cs.som.annotate.*;
import static asteroids.Util.*;
/**
 * A class of ships for the game asteroids.
 * 
 * @invar	The angle of each ship must be a valid angle for a ship.
 *			| isValidAngle(getAngle())
 * 
 * @version  2.5
 * @author   Frederik Van Eeghem (1st master Mathematical engineering), 
			 Pieter Lietaert (1st master Mathematical engineering)
 */
// Link to dropbox folder with files: https://www.dropbox.com/sh/tp0rjutudne3vji/hyFRz4TEUn

// GENERAL REMARK:
// The setters setPosition, setVelocity and setAngle have been made private because 
// changing the position, velocity or angle of the ship directly in other places makes little sense.
// The methods move, thrust and turn can be used to indirectly change these attributes.

public class Ship implements IShip{
	/**
	 * Initialize this new ship with given position, angle, radius, velocity and maximum speed.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new ship.
	 * @param 	angle
	 * 			The angle for this new ship.
	 * @param 	radius
	 * 			The radius for this new ship.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new ship.
	 * @param 	maxSpeed
	 * 			The maximum allowed speed for this new ship.
	 * @post	The radius of this new ship is equal to the given radius.
	 * 			| (new this).getRadius() == radius
	 * @post	If the maximum speed is a number that is positive and smaller than or equal to the speed of light,
	 * 			the maximum speed of this new ship is equal to the given maximum speed.
	 * 			|if ((!Double.isNaN(maxSpeed)) &&  (maxSpeed  >=0) && (maxSpeed <= 300000))
	 * 			|	then (new this).getMaxSpeed() == maxSpeed
	 * @post	If the maximum speed is NaN, a negative number or larger than the speed of light,
	 * 			the maximum speed of this new ship is equal to the speed of light.
	 * 			|if (Double.isNaN(maxSpeed) || (maxSpeed  < 0) || (maxSpeed > 300000))
	 * 			|	then (new this).getMaxSpeed() == 300000;
	 * @effect	The given position is set as the position of this new ship.
	 *       	| this.setPostion(position)
	 * @effect 	The given angle is set as the angle of this new ship.
	 * 			| this.setAngle(angle)
	 * @effect	The given velocity is set as the velocity of this new ship.
	 * 			| this.setVelocity(velocity)
	 * @throws	IllegalArgumentException
	 * 			Check if the given radius is valid.
	 * 			| ! this.isValidRadius(radius)
	 */
	@Raw
	public Ship(Vector2D position, double angle, double radius, Vector2D velocity, double maxSpeed)
			throws IllegalArgumentException, NullPointerException{
		if (!isValidRadius(radius)){
			throw new IllegalArgumentException("Given radius was invalid while constructing new ship.");
		}
		setPosition(position);
		setAngle(angle);
		this.radius = radius;
		
		if ((!Double.isNaN(maxSpeed)) && (maxSpeed  >=0) && (maxSpeed <= 300000)){
			this.maxSpeed = maxSpeed;
		}
		else{
			this.maxSpeed = 300000;
		}
		setVelocity(velocity);			
	}
		
	/**
	 * Initialize this new ship with given position, angle, radius, velocity and maximum speed set as the speed of light.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new ship.
	 * @param 	angle
	 * 			The angle for this new ship.
	 * @param 	radius
	 * 			The radius for this new ship.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new ship.
	 * @param 	maxSpeed
	 * 			The maximum allowed speed for this new ship.
	 * @effect	The new ship is initialized with position equal to the given position, 
	 * 			angle equal to the given angle, radius equal to the given 
	 * 			radius, velocity equal to given velocity and maximum speed 
	 * 			equal to the speed of light.
	 * 			| this(position, angle, radius, velocity, 300000)
	 */
	@Raw
	public Ship(Vector2D position, double angle, double radius, Vector2D velocity)
			throws IllegalArgumentException, NullPointerException{
		this(position, angle, radius, velocity, 300000);
	}
	
	/**
	 * Initialize this new ship.
	 * 
	 * @effect	The new ship is initialized with the components of position equal to 0, 
	 * 			the angle equal to 0, the radius equal to the minimum radius,
	 * 			the velocity components equal to 0 and the maximum speed 
	 * 			equal to the speed of light.
	 * 			| this(new Vector(0,0), 0, getMinRadius(), new Vector(0,0))
	 */
	@Raw
	public Ship() 
			throws IllegalArgumentException, NullPointerException{
		this(new Vector2D(0,0), 0, minRadius, new Vector2D(0,0));
	}
		
	
	/**
	 * Return the position of this ship.
	 * The position is a 2D vector that contains the coordinates of this ship.
	 */
	@Basic @Raw
	public Vector2D getPosition() {
		return this.position;
	}
	
	/**
	 * Check whether the given position is a valid position for a ship.
	 * 
	 * @param	position
	 * 			The position to check.
	 * @return	True if and only if the given 2D position vector is effective and does not contain NaN.
	 *			| result == (position != null && !position.containsNaN())
	 * @note	This checker checks for effectiveness and NaN at the same time. This means
	 * 			that this function is not useful when different exceptions are 
	 * 			desired for the different cases (e.g. NullPointerException 
	 * 			and IllegalArgumentException), it is however simpler towards the user.
	 */
	public boolean isValidPosition(Vector2D position) {
		return (position != null && !position.containsNaN());
	}
	
	/**
	 * Set the position of this ship as a 2D vector containing the coordinates of this ship.
	 * 
	 * @param	position
	 * 			The new position for this ship.
	 * @post	The position of this ship is equal to the given position.
	 * 			| (new this).getPosition == position
	 * @throws	IllegalArgumentException
	 * 			The given position is not a valid position for a ship.
	 * 			| ! isValidPosition(position)
	 */
	private void setPosition(Vector2D position) throws IllegalArgumentException {
		if (!isValidPosition(position))
			throw new IllegalArgumentException("Invalid position.");
		 this.position = position;
	}
	
	/**
	 * Variable registering the position of this ship.
	 * The coordinates are measured in km.
	 */
	private Vector2D position;
		
	/**
	 * Check whether the given time period is a valid time period.
	 * 
	 * @param 	deltaT
	 * 			The time period to check.
	 * @return	True if and only if the given time period is a number that is positive.
	 * 			| result == (!Double.isNaN(deltaT)) && (deltaT >= 0)
	 * @note	Checking for NaN is actually redundant since the boolean (deltaT >= 0) returns false in case 
	 * 			deltaT is NaN. It is however excluded explicitly for clarity towards the user.
	 */
	public static boolean isValidTime(double deltaT){
		return (!Double.isNaN(deltaT)) && (deltaT >= 0);
	}
	
	/**
	 * Calculate new position of this ship after a time period of deltaT based on current position and velocity.
	 * 
	 * @param 	deltaT
	 * 			Time period over which this ship moves.
	 * @effect	The resulting position of this ship is the sum of on the one hand the position of this ship,
	 * 			and on the other hand the product of the given time with the velocity of this ship.
	 * 			| (new this).getPosition == this.getPosition().add(this.getVelocity().multiply(deltaT))) 
	 * @throws	IllegalArgumentException
	 * 			The given time period is not a valid time period.
	 * 			| !isValidTime(deltaT)
	 */
	// Note that we do not check explicitly for an infinite time. This would either lead to an infinite position or a NaN position,
	// which are both cases that can be handled within setPosition (either without problems or by throwing an exception).
	public void move(double deltaT) throws IllegalArgumentException {
		if (!isValidTime(deltaT))
			throw new IllegalArgumentException("Invalid time step while attempting to move the ship.");
		this.setPosition(this.getPosition().add(this.getVelocity().multiply(deltaT)));
	}
		
		
	/**
	 * Return the angle of this ship.
	 */
	@Basic @Raw
	public double getAngle(){
		return this.angle;
	}
	
	/**
	 * Check whether the given angle is a valid angle for a ship.
	 * 
	 * @param	angle
	 * 			The angle to check.
	 * @return	True if and only if the angle is a number between 0 and 2 pi
	 * 			| result == (angle != Double.NaN) && (angle >= 0) && (angle < 2* Math.PI)
	 */
	public boolean isValidAngle(double angle){
		return (!Double.isNaN(angle)) && (angle >= 0) && (angle < 2* Math.PI);
	}
	
	/**
	 * Set the angle of this ship to the given angle.
	 * 
	 * @pre		The given angle must be a valid angle for a ship.
	 * 			| isValidAngle(angle)
	 * @post	The angle of this ship is equal to the given angle.
	 * 			| (new this).getAngle() == angle.
	 */
	@Raw
	private void setAngle(double angle){
		assert isValidAngle(angle);
		this.angle = angle;
	}
	
	/**
	 * Variable registering the angle of the ship.
	 * The angle is measured in Radians. The positive direction is taken to be counterclockwise. 
	 * Zero angle coincides with the positive x-direction.
	 */
	private double angle;
	
	/** 
	 * Turn this ship over the given angle.
	 * 
	 * @param 	angle
	 * 		  	The angle to turn this ship in the counterclockwise direction.
	 * @effect	The sum of the angle of this ship and the given angle is set as the angle of this ship.
	 * 			| this.setAngle(this.getAngle() + angle)
	 * @note	The sum of the current angle and the given angle should be a valid angle according to the precondition.
	 * 			This means, before using the method turn, the user should calculate the resulting angle and if this angle
	 * 			does not lie within the 0-2pi range (because it surpasses 2 pi, see isValidAngle), 
	 *			recalculate the angle so that the resulting	angle lies within the range. 			
	 */
	// The method is implemented in a nominal way as the assignment specifies. It would however be more intuitive to 
	// implement it in a total way by correcting the angle within the method.
	public void turn(double angle){
		this.setAngle(this.getAngle() + angle);
	}
	
	
	/**
	 * Return the velocity of this ship. 
	 * The velocity is a 2D vector that contains the velocity in the x-direction and the y-direction.
	 */
	@Basic @Raw
	public Vector2D getVelocity(){
		return this.velocity;
	}
	
	/**
	 * Set the velocity of this ship as a 2D vector containing the velocity in the x-direction and the y-direction.
	 * 
	 * @param	velocity
	 * 			The new velocity for this ship.
	 * @post	If the given velocity is effective, does not contain any NaN entries and 
	 * 			if the norm of the given velocity is smaller than or equal to the maximum allowed speed,
	 * 			the velocity of this ship is equal to the given velocity.
	 * 			| if ((velocity != null) && (!velocity.containsNaN()) && (fuzzyLessThanOrEqualTo(velocity.getNorm(),this.getMaxSpeed())))
	 * 			|		then (new this).getVelocity().equals(velocity)
	 * @post	If the given velocity is effective, does not contain any NaN entries and 
	 * 			if the norm of the given velocity exceeds the maximum allowed speed, the velocity 
	 * 			of this ship has the same direction as the given velocity and a norm equal 
	 * 			to the maximum allowed speed.
	 * 			| if ((velocity != null) &&  (!velocity.containsNaN()) && (!fuzzyLessThanOrEqualTo(velocity.getNorm(),this.getMaxSpeed())))
	 * 			|	then (new this).getVelocity.getNorm() == this.getMaxSpeed()
	 * 			|		 (new this).getVelocity.getDirection().equals(velocity.getDirection())
	 * @post 	If the given velocity is non-effective or contains a NaN entry, 
	 * 			the velocity of this ship is set to zero.
	 * 			|if ((velocity == null) || (velocity.containsNaN())
	 * 			|	then (new this).getVelocity == new Vector2D(0,0)
	 */
	private void setVelocity(Vector2D velocity){
		if (velocity == null || velocity.containsNaN()){
			this.velocity = new Vector2D(0,0);
		} else {
			// Use of fuzzyLessThanOrEqualTo to save on calculation time when
			// the given velocity is only slightly higher than the maximum speed.
			// The effect will be the same as when the speed is reset to the maximum speed.
			if (fuzzyLessThanOrEqualTo(velocity.getNorm(),this.getMaxSpeed())){
				this.velocity = velocity;
			} else {
				this.velocity = velocity.getDirection().multiply(this.getMaxSpeed());
				}
		}
	}
	
	/**
	 * Variable registering the velocity of this ship.
	 * The velocity is a 2D vector, containing the velocity in x-direction and y-direction, expressed in km/s.
	 */
	private Vector2D velocity;
	
	/**
	 * Return the maximum speed of this ship.
	 */
	@Basic @Immutable @Raw
	public double getMaxSpeed(){
		return this.maxSpeed;
	}
	
	/**
	 * Variable registering the maximum allowed total speed of this ship.
	 * This variable is expressed in km/s.
	 */
	private final double maxSpeed;
	
	/**
	 * Accelerate this ship according to the given acceleration.
	 * 
	 * @param	acceleration
	 * 			The acceleration of this ship.
	 * @post	If the given acceleration is negative, the velocity of this ship remains unchanged.
	 * 			| if(acceleration < 0)
	 * 			|	then (new this).getVelocity() == this.getVelocity
	 * @effect	If the given acceleration is positive, the given acceleration in the direction 
	 * 			of angle is added to the velocity of this ship and set as the velocity of this ship.
	 * 			| if(acceleration >= 0)
	 * 			|	then let Vector2D deltaVelocity == new Vector2D(Math.cos(this.getAngle())*acceleration,
	 * 			|													Math.sin(this.getAngle())*acceleration)
	 * 			|		 in this.setVelocity(this.getVelocity().add(deltaVelocity));
	 */
	public void thrust(double acceleration){
		if(acceleration >= 0){
			Vector2D deltaVelocity = new Vector2D(Math.cos(this.getAngle())*acceleration,Math.sin(this.getAngle())*acceleration);
			this.setVelocity(this.getVelocity().add(deltaVelocity));
		}
	}
	
	
	
	/**
	 * Calculate the distance between this ship and the given ship.
	 * 
	 * @param 	otherShip
	 * 			The ship to which the distance is calculated.
	 * @return	The distance between this ship and the given ship if the given ship is effective, 
	 * 			zero if this ship and the given ship are the same.
	 * 			| if(this != otherShip)
	 * 			|		result == this.getPosition().subtract(otherShip.getPosition()).getNorm() - 
	 * 			|            (this.getRadius()+otherShip.getRadius())
	 * @return	Zero if this ship and the given ship are the same.
	 * 			| if(this == otherShip)
	 * 			|		result == 0
	 * @throws	NullPointerException
	 * 			The otherShip is non existent.
	 * 			| otherShip == null
	 */
	public double getDistanceBetween(Ship otherShip) throws NullPointerException{
		if(otherShip == null)
			throw new NullPointerException("The other ship is non existent.");
		if(this == otherShip)
			return 0;
		else 
			return this.getPosition().subtract(otherShip.getPosition()).getNorm() - 
					(this.getRadius()+otherShip.getRadius());
	}
	
	/**
	 * Check whether this ship and the given ship overlap.
	 * 
	 * @param 	otherShip
	 * 			The ship for which the overlap with this ship is checked.
	 * @return	True if this ship and the given ship are the same or
	 * 			if this ship and the given ship overlap.
	 *			| result == ((this == otherShip) || (this.getDistanceBetween(otherShip) < 0))
	 */
	public boolean overlap(Ship otherShip) throws NullPointerException{
		return ((this == otherShip) || (this.getDistanceBetween(otherShip) < 0));
	}
	
	/**
	 * The time to collision between this ship and the given ship.
	 * 
	 * @param 	otherShip
	 * 			The ship for which the time to collision is calculated.
	 * @return	Double.POSITIVE_INFINITY if this ship and the given ship are the same.
	 * 			| if (this == otherShip)
	 * 			|	result == Double.POSITIVE_INFINITY
	 * @return	The time until collision according to current position and velocity
	 * 			if this ship and the given ship (different from this ship), 
	 * 			both moving in their current direction,	will ever collide, 
	 * 			else Double.POSITIVE_INFINITY.
	 * 			| if (this != otherShip)
	 * 			|	let
	 * 			| 		allOverlapTimes = {collisionTime in Double | (when (this.move(collisionTime)  
	 * 			|								&& otherShip.move(collisionTime)) then this.overlap(otherShip))
	 * 			|								&& (collisionTime != Double.POSITIVE_INFINITY)}
	 * 			|	 in
	 * 			|	 if (!isEmpty(allOverlapTimes))
	 * 			|		fuzzyEquals(result, min(allOverlapTimes)) == true
	 * 			|	 else
	 * 			|		result == Double.POSITIVE_INFINITY
	 * @throws	NullPointerException
	 * 			The otherShip is non existent
	 * 			| otherShip == null
	 */

	public double getTimeToCollision(Ship otherShip)throws NullPointerException{
		if (otherShip == null)
			throw new NullPointerException("The other ship is non existent.");
		if (this != otherShip) {
			Vector2D dr = this.getPosition().subtract(otherShip.getPosition());
			Vector2D dv = this.getVelocity().subtract(otherShip.getVelocity());
			double drdr = dr.getDotProduct(dr);
			double dvdv = dv.getDotProduct(dv);
			double dvdr = dr.getDotProduct(dv);
			double d = dvdr * dvdr - dvdv * (drdr - 
					Math.pow((this.getRadius() + otherShip.getRadius()), 2));
			if (dvdr >= 0 || d <= 0)
				return Double.POSITIVE_INFINITY;
			else
				return -1 * (dvdr + Math.sqrt(d)) / dvdv;
		} 
		else {
			return Double.POSITIVE_INFINITY;
		}
	}
	
	/**
	 * The collision point of this ship and the other ship.
	 * 
	 * @param 	otherShip
	 * 			The ship on which the collision point of this ship is calculated.
	 * @return	If this ship and the other ship will collide, the collision position is returned
	 * 			as the middle of the connecting line on the time of collision.
	 *  		| let collisionTime = this.getTimeToCollision(otherShip)
	 *  		| in
	 *  		|	if(collisionTime != Double.POSITIVE_INFINITY)
	 *  		|		when(otherShip.move(collisionTime) && this.move(collisionTime))
	 *  		|		then
	 *  		|			(otherShip.getPosition().subtract(result)).getNorm() <= otherShip.getRadius()
	 *  		|			(this.getPosition().subtract(result)).getNorm() <= this.getRadius()
	 * @return	If this ship and the other ship never collide, null is returned.
	 * 			| if(this.getTimeToCollision(otherShip) == Double.POSITIVE_INFINITY)
	 * 			|	then result == null
	 * @throws	NullPointerException
	 * 			The otherShip is non existent
	 * 			| otherShip == null
	 */
	public Vector2D getCollisionPosition(Ship otherShip) throws NullPointerException{
		double timeToCollision = this.getTimeToCollision(otherShip);
		if(timeToCollision != Double.POSITIVE_INFINITY){
			Ship thisClone = new Ship(this.getPosition(), this.getAngle(), this.getRadius(), this.getVelocity(), this.getMaxSpeed());
			Ship otherShipClone = new Ship(otherShip.getPosition(), otherShip.getAngle(), otherShip.getRadius(), otherShip.getVelocity(), otherShip.getMaxSpeed());

			thisClone.move(timeToCollision);
			otherShipClone.move(timeToCollision);
			Vector2D newPositionThis = thisClone.getPosition();
			Vector2D newPositionOtherShip = otherShipClone.getPosition();
			
			Vector2D collisionPosition = newPositionThis.add(newPositionOtherShip.subtract(newPositionThis).getDirection().multiply(this.getRadius()));
			return collisionPosition;

		} else{
			return null;
		}
	}
	
	
	
	/**
	 * Return the radius of this ship.
	 */
	@Basic @Immutable @Raw
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * Check whether the given radius is a valid radius for a ship.
	 * 
	 * @param	radius
	 * 			Radius to check.
	 * @return	True if and only if the given radius is a number 
	 * 			larger than the minimum radius and smaller than or equal to the maximum double value.
	 * 			| result == (!Double.isNaN(radius)) && ((radius >= this.getMinRadius()) && (radius <= Double.MAX_VALUE))
	 */
	public boolean isValidRadius(double radius){
		return (!Double.isNaN(radius)) && (fuzzyLessThanOrEqualTo(this.getMinRadius(),radius) 
				&& (radius <= Double.MAX_VALUE));
	}
	
	/**
	 * Return the minimum radius of this ship.
	 */
	@Basic @Immutable @Raw
	public double getMinRadius(){
		return minRadius;
	}
	
	/**
	 * Variable registering radius of this ship.
	 * The radius is expressed in km.
	 */
	private final double radius;
	
	/**
	 * Variable registering the minimum allowed radius of this ship.
	 * The minimum radius is expressed in km.
	 */
	private static final double minRadius = 10;
}
