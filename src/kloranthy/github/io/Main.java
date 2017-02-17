package kloranthy.github.io;

import java.util.Scanner;

/**
 */
public class Main
{
	private Scanner scanner;
	private boolean running;

	private Attacker attacker;
	private Defender defender;

	public Main()
	{
		scanner = new Scanner( System.in );
	}

	public static void main( String[] args )
	{
		Main main = new Main();
		main.init();
		main.start();
	}

	private void init()
	{
		attacker = new Attacker();
		defender = new Defender();
		Weapon weapon = new Weapon();
		weapon.setWeaponType( WeaponType.MELEE )
				.setDamageType( DamageType.KINETIC )
				.setBaseHitScore( 6 )
				.setBaseDamage( 6 );
		attacker.setWeaponTypeTrainingLevel(
			WeaponType.MELEE,
			TrainingLevel.BASIC
													  )
				  .setWeaponTypeExperienceLevel(
					  WeaponType.MELEE,
					  ExperienceLevel.INEXPERIENCED
														 )
				  .setDamageTypeTrainingLevel(
					  DamageType.KINETIC,
					  TrainingLevel.BASIC
													  )
				  .setDamageTypeExperienceLevel(
					  DamageType.KINETIC,
					  ExperienceLevel.NOVICE
														 )
				  .setAimBonusPerStack( 2 )
				  .setMaxAimStacks( 3 )
				  .setWeapon( weapon )
				  .setTarget( defender );
		defender.setEvasionTrainingLevel( TrainingLevel.INTERMEDIATE )
				  .setEvasionExperienceLevel( ExperienceLevel.NOVICE )
				  .setBaseDifficulty( 6 )
				  .setBaseDamageNegation( 2 )
				  .setBaseDamageResistance( 0.3 )
				  .setDefenderStance( Stance.STANDING );
	}

	public void start()
	{
		running = true;
		while ( running )
		{
			print( "enter command" );
			String input = scanner.nextLine();
			if ( input.length() != 0 )
			{
				handleInput( input );
			}
		}
	}

	private void print( String output )
	{
		System.out.println( output );
	}

	private void handleInput( String input )
	{
		input = input.toLowerCase();
		switch ( input )
		{
			case "a":
				aim();
				break;
			case "s":
				shoot();
				break;
			case "x":
				exit();
				break;
			default:
				print( "invalid input" );
		}
	}


	private void aim()
	{
		attacker.aim();
	}

	private void shoot()
	{
		attacker.attack();
	}

	private void exit()
	{
		running = false;
	}
}
