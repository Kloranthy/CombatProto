package kloranthy.github.io.damage;

import java.util.UUID;

/**
 */
public
class Damage
{
	private DamageType damageType;
	private UUID sourceId;
	private double amount;
	// todo use this class to specify how the damage is applied

	public Damage()
	{}

	public
	DamageType getDamageType()
	{
		return damageType;
	}

	public
	Damage setDamageType( DamageType damageType )
	{
		this.damageType = damageType;
		return this;
	}

	public
	UUID getSourceId()
	{
		return sourceId;
	}

	public
	Damage setSourceId( UUID sourceId )
	{
		this.sourceId = sourceId;
		return this;
	}

	public
	double getAmount()
	{
		return amount;
	}

	public
	Damage setAmount( double amount )
	{
		this.amount = amount;
		return this;
	}
}
