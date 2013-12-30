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

package net.dague.astro.jupiter;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import android.util.Log;

import net.dague.astro.sim.SolarSim;
import net.dague.astro.util.TimeUtil;
import net.dague.astro.util.Vector3;

public class JovianMoons {
	//RAH Try to stay consistent with SolarSim enums.
	public static final int JUPITER = 5;
	public static final int IO = 501;
	public static final int EUROPA = 502;
	public static final int GANYMEDE = 503;
	public static final int CALLISTO = 504;
	
	public double jd;
	public Vector3 callisto;
	public Vector3 io;
	public Vector3 ganymede;
	public Vector3 europa;
	
	public JovianMoons()
	{
		jd = TimeUtil.mils2JD(System.currentTimeMillis());
		callisto = new Vector3();
		io = new Vector3();
		ganymede = new Vector3();
		europa = new Vector3();
	}
	
	public JovianMoons(double t)
	{
		jd = t;
		callisto = new Vector3();
		io = new Vector3();
		ganymede = new Vector3();
		europa = new Vector3();
	}
	
	public int[] getZOrder()
	{
		int[] values = new int[CALLISTO - IO + 2];  //RAH +1 for the math to get 4 objects, then +1 for the stealth Jupiter
		
		TreeMap<Double, Integer> map = new TreeMap<Double, Integer>();
		map.put(new Double(getZ(JUPITER)), new Integer(JUPITER));  //Manually add in a Jupiter distance
		for (int i = IO; i <= CALLISTO; i++) {
			map.put(new Double(getZ(i)), new Integer(i));
		}
		
//		Log.i("IO","Io Z distance " + getZ(IO));
		
		int i = CALLISTO - IO + 1;  //+1 for Jupiter
		for(Entry<Double, Integer> entry : map.entrySet()) {
			  Integer value = entry.getValue();
			  values[i] = value.intValue();
			  i--;
		}
		return values;
	}
	

	public double getZ(int i)
	{
		switch(i) {
		case IO:
			return io.Z;
		case EUROPA:
			return europa.Z;
		case CALLISTO:
			return callisto.Z;
		case GANYMEDE:
			return ganymede.Z;
		default:
			return 0;
		}
	}
	
	public double getX(int i)
	{
		switch(i) {
		case IO:
			return io.X;
		case EUROPA:
			return europa.X;
		case CALLISTO:
			return callisto.X;
		case GANYMEDE:
			return ganymede.X;
		default:
			return 0;
		}
	}
	
	public void setX(int i, double v)
	{
		switch(i) {
		case IO:
			io.X = v;
			break;
		case EUROPA:
			europa.X = v;
			break;
		case CALLISTO:
			callisto.X = v;
			break;
		case GANYMEDE:
			ganymede.X = v;
			break;
		}
	}
	
	// x(n) = x(1) + y(n) * (deltax / deltay)
	
	public JovianMoons interpolate(JovianMoons next, long time)
	{
		double newjd = TimeUtil.mils2JD(time);
		JovianMoons newjm = new JovianMoons(newjd);
		if (this.jd < newjd && next.jd > newjd) {
			double slope = (newjd - jd) / (next.jd - jd);
			newjm.callisto.X = callisto.X + slope * (next.callisto.X - callisto.X);
			newjm.io.X = io.X + slope * (next.io.X - io.X);
			newjm.ganymede.X = ganymede.X + slope * (next.ganymede.X - ganymede.X);
			newjm.europa.X = europa.X + slope * (next.europa.X - europa.X);			
		}
		return newjm;
	}
	
	public String toString()
	{
		return "jd: " + jd + " - c: " + callisto + ", i: " + io + ", g: " + ganymede + ", e: " + europa;
	}
}
