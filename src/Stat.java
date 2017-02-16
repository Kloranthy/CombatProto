import java.util.HashMap;
import java.util.UUID;

import modifier.Modifier;

/**
 * possible solution
 * although a one size fits all stat model might not
 * work for all cases
 * ex: health doesn't take in rolls > doesn't need variable mods
 * ex: range has to return values for hit score and damage based on
 * 	the range that is passed in
 */
public class Stat
{
	private UUID statId;
	private String statName;
	private String statDescription;
	private double baseValue;
	private double additiveModifierForFixedValue;
	private double multiplicativeModifierForFixedValue;
	// todo not sure if all stats need variable part
	private double additiveModifierForVariableValue;
	private double multiplicativeModifierForVariableValue;
	private double additiveModifierForTotalValue;
	private double multiplicativeModifierForTotalValue;
	private HashMap<UUID, Modifier> modifiersById;

	public Stat()
	{}
}
