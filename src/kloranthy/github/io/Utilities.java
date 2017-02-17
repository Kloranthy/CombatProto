package kloranthy.github.io;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 */
public class Utilities
{
	public static double convertToPercentileFormat( double probability )
	{
		probability = round( probability * 100,
									2 );
		return probability;
	}

	public static double round(
										  double value,
										  int places
									  )
	{
		if ( places < 0 )
		{
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal( value );
		bd = bd.setScale(
			places,
			RoundingMode.HALF_UP
							 );
		return bd.doubleValue();
	}
}
