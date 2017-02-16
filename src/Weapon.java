import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import modifier.WeaponModifier;

/**
 */
public class Weapon
{
	private WeaponType weaponType;
	private DamageType damageType;
	private double baseHitScore;
	private double additiveModifierForFixedHitScore;
	private double multiplicativeModifierForFixedHitScore;
	private double additiveModifierForVariableHitScore;
	private double multiplicativeModifierForVariableHitScore;
	private double additiveModifierForTotalHitScore;
	private double multiplicativeModifierForTotalHitScore;
	private double baseDamage;
	private double additiveModifierForFixedDamage;
	private double multiplicativeModifierForFixedDamage;
	private double additiveModifierForVariableDamage;
	private double multiplicativeModifierForVariableDamage;
	private double additiveModifierForTotalDamage;
	private double multiplicativeModifierForTotalDamage;
	private HashMap<UUID, WeaponModifier> weaponModifiersById;

	public Weapon()
	{
		weaponModifiersById = new HashMap<UUID, WeaponModifier>();
		initModifierValues();
	}

	public static void main( String[] args )
	{
		Weapon shotgun = new Weapon();
		shotgun.setBaseHitScore( 10 );
		System.out.println( "hit score for roll of 4: " + shotgun.calculateHitScore( 4 ));
	}

	public WeaponType getWeaponType()
	{
		return weaponType;
	}

	public Weapon setWeaponType( WeaponType weaponType )
	{
		this.weaponType = weaponType;
		return this;
	}

	public DamageType getDamageType()
	{
		return damageType;
	}

	public Weapon setDamageType( DamageType damageType )
	{
		this.damageType = damageType;
		return this;
	}

	public double getBaseHitScore()
	{
		return baseHitScore;
	}

	public Weapon setBaseHitScore( double baseHitScore )
	{
		this.baseHitScore = baseHitScore;
		return this;
	}

	public double getBaseDamage()
	{
		return baseDamage;
	}

	public Weapon setBaseDamage( double baseDamage )
	{
		this.baseDamage = baseDamage;
		return this;
	}

	public Weapon addModifier( WeaponModifier weaponModifier )
	{
		weaponModifiersById.put( weaponModifier.getModifierId(), weaponModifier );
		applyModifierEffects( weaponModifier );
		return this;
	}

	public List<WeaponModifier> getModifiers()
	{
		List<WeaponModifier> weaponModifiers = new LinkedList<WeaponModifier>();
		weaponModifiers.addAll( weaponModifiersById.values() );
		return weaponModifiers;
	}

	public void removeModifier( UUID weaponModifierId )
	{
		if ( weaponModifiersById.containsKey( weaponModifierId ) )
		{
			WeaponModifier weaponModifier = weaponModifiersById.remove( weaponModifierId );
			removeModifierEffects( weaponModifier );
		}
	}

	public double calculateFixedHitScore()
	{
		double fixedHitScore = baseHitScore;
		fixedHitScore += additiveModifierForFixedHitScore;
		fixedHitScore *= multiplicativeModifierForFixedHitScore;
		return fixedHitScore;
	}

	public double calculateVariableHitScore( double roll )
	{
		double variableHitScore = roll;
		variableHitScore += additiveModifierForVariableHitScore;
		variableHitScore *= multiplicativeModifierForVariableHitScore;
		return variableHitScore;
	}

	public double calculateHitScore( double roll )
	{
		double hitScore = calculateFixedHitScore() + calculateVariableHitScore( roll );
		hitScore += additiveModifierForTotalHitScore;
		hitScore *= multiplicativeModifierForTotalHitScore;
		return hitScore;
	}

	public double calculateFixedDamage()
	{
		double fixedDamage = baseDamage;
		fixedDamage += additiveModifierForFixedDamage;
		fixedDamage *= multiplicativeModifierForFixedDamage;
		return fixedDamage;
	}

	public double calculateVariableDamage( double roll )
	{
		double variableDamage = roll;
		variableDamage += additiveModifierForVariableDamage;
		variableDamage *= multiplicativeModifierForVariableDamage;
		return variableDamage;
	}

	public double calculateDamage( double roll )
	{
		double damage = calculateFixedDamage() + calculateVariableDamage( roll );
		damage += additiveModifierForTotalDamage;
		damage *= multiplicativeModifierForTotalDamage;
		return damage;
	}

	private void initModifierValues()
	{
		additiveModifierForFixedHitScore = 0;
		multiplicativeModifierForFixedHitScore = 1;
		additiveModifierForVariableHitScore = 0;
		multiplicativeModifierForVariableHitScore = 1;
		additiveModifierForTotalHitScore = 0;
		multiplicativeModifierForTotalHitScore = 1;
		additiveModifierForFixedDamage = 0;
		multiplicativeModifierForFixedDamage = 1;
		additiveModifierForVariableDamage = 0;
		multiplicativeModifierForVariableDamage = 1;
		additiveModifierForTotalDamage = 0;
		multiplicativeModifierForTotalDamage = 1;
	}

	private void applyModifierEffects( WeaponModifier weaponModifier )
	{
		additiveModifierForFixedHitScore += weaponModifier.getAdditiveModifierForFixedHitScore();
		multiplicativeModifierForFixedHitScore += weaponModifier.getMultiplicativeModifierForFixedHitScore();
		additiveModifierForVariableHitScore += weaponModifier.getAdditiveModifierForVariableHitScore();
		multiplicativeModifierForVariableHitScore += weaponModifier.getMultiplicativeModifierForVariableHitScore();
		additiveModifierForTotalHitScore += weaponModifier.getAdditiveModifierForTotalHitScore();
		multiplicativeModifierForTotalHitScore += weaponModifier.getMultiplicativeModifierForTotalHitScore();
		additiveModifierForFixedDamage += weaponModifier.getAdditiveModifierForFixedDamage();
		multiplicativeModifierForFixedDamage += weaponModifier.getMultiplicativeModifierForFixedDamage();
		additiveModifierForVariableDamage += weaponModifier.getAdditiveModifierForVariableDamage();
		multiplicativeModifierForVariableDamage += weaponModifier.getMultiplicativeModifierForVariableDamage();
		additiveModifierForTotalDamage += weaponModifier.getAdditiveModifierForTotalDamage();
		multiplicativeModifierForTotalDamage += weaponModifier.getMultiplicativeModifierForTotalDamage();
	}

	private void removeModifierEffects( WeaponModifier weaponModifier )
	{
		additiveModifierForFixedHitScore -= weaponModifier.getAdditiveModifierForFixedHitScore();
		multiplicativeModifierForFixedHitScore -= weaponModifier.getMultiplicativeModifierForFixedHitScore();
		additiveModifierForVariableHitScore -= weaponModifier.getAdditiveModifierForVariableHitScore();
		multiplicativeModifierForVariableHitScore -= weaponModifier.getMultiplicativeModifierForVariableHitScore();
		additiveModifierForTotalHitScore -= weaponModifier.getAdditiveModifierForTotalHitScore();
		multiplicativeModifierForTotalHitScore -= weaponModifier.getMultiplicativeModifierForTotalHitScore();
		additiveModifierForFixedDamage -= weaponModifier.getAdditiveModifierForFixedDamage();
		multiplicativeModifierForFixedDamage -= weaponModifier.getMultiplicativeModifierForFixedDamage();
		additiveModifierForVariableDamage -= weaponModifier.getAdditiveModifierForVariableDamage();
		multiplicativeModifierForVariableDamage -= weaponModifier.getMultiplicativeModifierForVariableDamage();
		additiveModifierForTotalDamage -= weaponModifier.getAdditiveModifierForTotalDamage();
		multiplicativeModifierForTotalDamage -= weaponModifier.getMultiplicativeModifierForTotalDamage();
	}
}
