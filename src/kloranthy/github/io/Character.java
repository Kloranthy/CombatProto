package kloranthy.github.io;

import java.util.EnumMap;
import java.util.UUID;

import kloranthy.github.io.equipment.Equipment;
import kloranthy.github.io.equipment.EquipmentSlot;

/**
 */
public
class Character
{
	private
	UUID characterId;
	private
	String characterName;
	private
	EnumMap<EquipmentSlot, Equipment> equippedEquipment;
	// todo add proficiencies
}
