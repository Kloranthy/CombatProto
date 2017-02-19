package kloranthy.github.io.equipment.weapon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import kloranthy.github.io.damage.DamageType;
import kloranthy.github.io.modifier.WeaponModifier;
import kloranthy.github.io.stat.ScalingStat;

/**
 */
public
class Weapon
{
	private WeaponType weaponType;
	private DamageType damageType;
	private ScalingStat hitScore;
	private ScalingStat damage;
	private HashMap<UUID, WeaponModifier> weaponModifiersById;

	public
	Weapon()
	{
		hitScore = new ScalingStat();
		damage = new ScalingStat();
		weaponModifiersById = new HashMap<UUID, WeaponModifier>();
	}

	public
	WeaponType getWeaponType()
	{
		return weaponType;
	}

	public
	Weapon setWeaponType( WeaponType weaponType )
	{
		this.weaponType = weaponType;
		return this;
	}

	public
	DamageType getDamageType()
	{
		return damageType;
	}

	public
	Weapon setDamageType( DamageType damageType )
	{
		this.damageType = damageType;
		return this;
	}

	public
	Weapon setBaseHitScore( double baseHitScore )
	{
		hitScore.setBaseValue( baseHitScore );
		return this;
	}

	public
	Weapon setBaseDamage( double baseDamage )
	{
		damage.setBaseValue( baseDamage );
		return this;
	}

	public
	ScalingStat getHitScore()
	{
		return hitScore;
	}

	public
	ScalingStat getDamage()
	{
		return damage;
	}

	public
	Weapon addModifier( WeaponModifier weaponModifier )
	{
		weaponModifiersById.put(
			weaponModifier.getModifierId(),
			weaponModifier
									  );
		applyModifierEffects( weaponModifier );
		return this;
	}

	private
	void applyModifierEffects( WeaponModifier weaponModifier )
	{
		weaponModifier.applyEffectsTo( this );
	}

	public
	List<WeaponModifier> getModifiers()
	{
		List<WeaponModifier> weaponModifiers = new LinkedList<WeaponModifier>();
		weaponModifiers.addAll( weaponModifiersById.values() );
		return weaponModifiers;
	}

	public
	void removeModifier( UUID weaponModifierId )
	{
		if ( weaponModifiersById.containsKey( weaponModifierId ) )
		{
			WeaponModifier weaponModifier = weaponModifiersById.remove( weaponModifierId );
			removeModifierEffects( weaponModifier );
		}
	}

	private
	void removeModifierEffects( WeaponModifier weaponModifier )
	{
		weaponModifier.removeEffectsFrom( this );
	}
}
