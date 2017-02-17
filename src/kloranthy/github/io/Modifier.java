package kloranthy.github.io;

import java.util.UUID;

/**
 */
public abstract class Modifier
{
	private UUID modifierId;
	private String modifierName;

	public Modifier()
	{
	}

	public UUID getModifierId()
	{
		return modifierId;
	}

	public Modifier setModifierId( UUID modifierId )
	{
		this.modifierId = modifierId;
		return this;
	}

	public String getModifierName()
	{
		return modifierName;
	}

	public Modifier setModifierName( String modifierName )
	{
		this.modifierName = modifierName;
		return this;
	}
}
