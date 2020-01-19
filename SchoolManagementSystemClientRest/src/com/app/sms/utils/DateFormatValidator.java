package com.app.sms.utils;

import com.app.sms.exceptions.BadFormatDataException;
import com.app.sms.exceptions.DateOutOfBoundsException;
import com.app.sms.exceptions.NotAvailableDateFormatException;

public class DateFormatValidator {
	public enum DateFormat {
		JJ_MM_AAAA ("JJ-MM-AAAA"),
		AAAA_MM_JJ ("AAAA-MM-JJ");
		
		private DateFormat(String name) {}
	}
	
	public static boolean validate ( String date, DateFormat format) throws NumberFormatException, DateOutOfBoundsException, NotAvailableDateFormatException, BadFormatDataException {
		
		switch (format) {
			case JJ_MM_AAAA :
				String [] items = date.split("-");
				if (items.length != 3) throw new BadFormatDataException("Invalid Format Date !");
				else {
					int jj = Integer.parseInt(items[0]);					
					int mm = Integer.parseInt(items[1]);
					int aaaa = Integer.parseInt(items[2]);
					
					if (aaaa >= 1970 && aaaa <= 2018) {
						switch (mm ) {
							case  1:
							case  3:
							case  5:
							case  7:
							case  8:
							case 10:
							case 12:
								if (jj < 1 || jj > 31) throw new DateOutOfBoundsException ("Mois de 31 jours 1 <= mois <= 31");
							case  4:
							case  6:									
							case  9:
							case 11:
								if (jj < 1 || jj > 30) throw new DateOutOfBoundsException ("Mois de 30 jours 1 <= mois <= 30");
								break;
							case  2:
								if ( ! ( ( (aaaa % 4 == 0) && (aaaa % 100 != 0) ) || ( aaaa % 400 == 0) ) ) {
									// Année non bissextile
									if (jj < 1 || jj > 28) throw new DateOutOfBoundsException ("Mois de 28 jours 1 <= mois <= 28");
									// Année bissextile
								} else if (jj < 1 || jj > 29) throw new DateOutOfBoundsException ("Mois de 29 jours - Année bissextile");
							default :;
						}
					} else throw new DateOutOfBoundsException ("1970 <= Annee <= 2018");				
				}
				break;
			case AAAA_MM_JJ : 
				// Format non encore pris en charge
				throw new NotAvailableDateFormatException ( "Format de date non encore disponible !" );
				/*
				 * les autres formats ici ...
				 */
		}		
		return true;
	}
}
