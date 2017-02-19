package kloranthy.github.io.modifier;

import java.util.UUID;

/**
 */
public abstract
class Modifier<T>
{
	private UUID modifierId;
	private String modifierName;

	public
	Modifier()
	{
	}

	public
	UUID getModifierId()
	{
		return modifierId;
	}

	public
	Modifier setModifierId( UUID modifierId )
	{
		this.modifierId = modifierId;
		return this;
	}

	public
	String getModifierName()
	{
		return modifierName;
	}

	public
	Modifier setModifierName( String modifierName )
	{
		this.modifierName = modifierName;
		return this;
	}

	public abstract void applyEffectsTo( T target );

	public abstract void removeEffectsFrom( T target );
}
