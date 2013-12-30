/*
  Copyright 2010 Sean Dague

  This file is part of Where is Io

  Where is Io is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
  Where is Io is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  General Public License for more details.
  
  You should have received a copy of the GNU General Public License
  along with Where is Io.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.dague.astro.sim;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import net.dague.astro.data.JupiterData;
import net.dague.astro.jupiter.JovianMoonSet;
import net.dague.astro.jupiter.JovianMoons;
import net.dague.astro.util.TimeUtil;
import net.dague.astro.util.Vector3;
import static android.provider.BaseColumns._ID;
import static net.dague.astro.data.Constants.*;

public class SolarSim {
	
	public static final int SUN = 0;
	public static final int MERCURY = 1;
	public static final int VENUS = 2;
	public static final int EARTH = 3;
	public static final int MARS = 4;
	public static final int PHOBOS = 401;
	public static final int DEIMOS = 402;
	public static final int VESTA = 10;
	public static final int CERES = 11;
	public static final int JUPITER = 5;
	public static final int IO = 501;
	public static final int EUROPA = 502;
	public static final int GANYMEDE = 503;
	public static final int CALLISTO = 504;
	public static final int SATURN = 6;
	public static final int MIMAS = 601;
	public static final int ENCELADUS = 602;
	public static final int TETHYS = 603;
	public static final int DIONE = 604;
	public static final int RHEA = 605;
	public static final int TITAN = 606;
	public static final int HYPERION = 607;
	public static final int IAPETUS = 608;
	public static final int URANUS = 7;
	public static final int MIRANDA = 701;
	public static final int ARIEL = 702;
	public static final int UMBRIEL = 703;
	public static final int TITANIA = 704;
	public static final int OBERON = 705;
	public static final int NEPTUNE = 8;
	public static final int GALATEA = 801;
	public static final int LARISSA = 802;
	public static final int PROTEUS = 803;
	public static final int TRITON = 804;
	public static final int NEREID = 805;
	public static final int PLUTO = 9;
	public static final int CHARON = 901;
	public static final int S2012_1= 902;
	public static final int NIX = 903;
	public static final int S2011_1 = 904;
	public static final int HYDRA = 905;
	public static final int ERIS = 12;
	public static final int DYSNOMIA = 120;
	public static final int HAUMEA = 13;
	public static final int NAMAKA = 1301;
	public static final int HI_IAKA = 1302;

	// native double[] returnJD(double jd);
	native double[] mercuryCoords(double jd);
	native double[] venusCoords(double jd);
	native double[] earthCoords(double jd);
	native double[] marsCoords(double jd);
	native double[] vestaCoords(double jd);
	native double[] ceresCoords(double jd);
	native double[] jupiterCoords(double jd);
	native double[] saturnCoords(double jd);
	native double[] uranusCoords(double jd);
	native double[] neptuneCoords(double jd);
	native double[] plutoCoords(double jd);
	native double[] erisCoords(double jd);
	native double[] haumeaCoords(double jd);

	// mars's moons
	native double[] phobosCoords(double jd);
	native double[] deimosCoords(double jd);
	
	// jupiter's moons
	native double[] ioCoords(double jd);
	native double[] europaCoords(double jd);
	native double[] ganymedeCoords(double jd);
	native double[] callistoCoords(double jd);
	
	// saturn's moons
	native double[] mimasCoords(double jd);
	native double[] enceladusCoords(double jd);
	native double[] tethysCoords(double jd);
	native double[] dioneCoords(double jd);
	native double[] rheaCoords(double jd);
	native double[] titanCoords(double jd);
	native double[] hyperionCoords(double jd);
	native double[] iapetusCoords(double jd);
	
	// uranus's moons
	native double[] mirandaCoords(double jd);
	native double[] arielCoords(double jd);
	native double[] umbrielCoords(double jd);
	native double[] titaniaCoords(double jd);
	native double[] oberonCoords(double jd);

	// neptune's moons
	native double[] galateaCoords(double jd);
	native double[] larissaCoords(double jd);
	native double[] proteusCoords(double jd);
	native double[] tritonCoords(double jd);
	native double[] nereidCoords(double jd);

	// pluto's moons
	native double[] charonCoords(double jd);
	native double[] s2012_1Coords(double jd);
	native double[] nixCoords(double jd);
	native double[] s2011_1Coords(double jd);
	native double[] hydraCoords(double jd);
	
	// eris's moons
	native double[] dysnomiaCoords(double jd);
	
	// haumea's moons
	native double[] namakaCoords(double jd);
	native double[] hi_iakaCoords(double jd);

	
	public SolarSim() {}

/////RAH STOP HERE - Need to add new solar bodies and moons to this switch statement.	
/////RAH Many of the smaller bodies don't have actual functions to display their locations unfortunately.
/////RAH They return the null vector for now.
	
	public Vector3 calcPosition(int body, double jd)
	{
		switch(body) { 
		case SUN:
			// the sun is cannonically 0
			return new Vector3();
		case MERCURY:
			return new Vector3(mercuryCoords(jd));
		case VENUS:
			return new Vector3(venusCoords(jd));		
		case EARTH:
			return new Vector3(earthCoords(jd));
		case MARS:
			return new Vector3(marsCoords(jd));		
		case JUPITER:
			return new Vector3(jupiterCoords(jd));
		case SATURN:
			return new Vector3(saturnCoords(jd));		
		case URANUS:
			return new Vector3(uranusCoords(jd));		
		case NEPTUNE:
			return new Vector3(neptuneCoords(jd));
		case PLUTO:
			return new Vector3(plutoCoords(jd));
		// Jupiter's moons
		case IO:
			return new Vector3(ioCoords(jd));
		case EUROPA:
			return new Vector3(europaCoords(jd));
		case GANYMEDE:
			return new Vector3(ganymedeCoords(jd));
		case CALLISTO:
			return new Vector3(callistoCoords(jd));
		// Saturn's moons
		case MIMAS:
			return new Vector3(mimasCoords(jd));
		case ENCELADUS:
			return new Vector3(enceladusCoords(jd));
		case TETHYS:
			return new Vector3(tethysCoords(jd));
		case DIONE:
			return new Vector3(dioneCoords(jd));
		case RHEA:
			return new Vector3(rheaCoords(jd));
		case TITAN:
			return new Vector3(titanCoords(jd));
		case HYPERION:
			return new Vector3(hyperionCoords(jd));
		case IAPETUS:
			return new Vector3(iapetusCoords(jd));	
			
		default:
			return new Vector3();
		}
	}	

	static {
		System.loadLibrary("solarsym");
	}
}
