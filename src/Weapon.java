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
	// modifier values for hit score
	private double additiveModifierForFixedHitScore;
	private double multiplicativeModifierForFixedHitScore;
	private double additiveModifierForVariableHitScore;
	private double multiplicativeModifierForVariableHitScore;
	private double additiveModifierForTotalHitScore;
	private double multiplicativeModifierForTotalHitScore;
	private double baseDamage;
	// modifier values for damage
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

	public double getAdditiveModifierForFixedHitScore()
	{
		return additiveModifierForFixedHitScore;
	}

	public double getMultiplicativeModifierForFixedHitScore()
	{
		return multiplicativeModifierForFixedHitScore;
	}

	public double getAdditiveModifierForVariableHitScore()
	{
		return additiveModifierForVariableHitScore;
	}

	public double getMultiplicativeModifierForVariableHitScore()
	{
		return multiplicativeModifierForVariableHitScore;
	}

	public double getAdditiveModifierForTotalHitScore()
	{
		return additiveModifierForTotalHitScore;
	}

	public double getMultiplicativeModifierForTotalHitScore()
	{
		return multiplicativeModifierForTotalHitScore;
	}

	public double getAdditiveModifierForFixedDamage()
	{
		return additiveModifierForFixedDamage;
	}

	public double getMultiplicativeModifierForFixedDamage()
	{
		return multiplicativeModifierForFixedDamage;
	}

	public double getAdditiveModifierForVariableDamage()
	{
		return additiveModifierForVariableDamage;
	}

	public double getMultiplicativeModifierForVariableDamage()
	{
		return multiplicativeModifierForVariableDamage;
	}

	public double getAdditiveModifierForTotalDamage()
	{
		return additiveModifierForTotalDamage;
	}

	public double getMultiplicativeModifierForTotalDamage()
	{
		return multiplicativeModifierForTotalDamage;
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
