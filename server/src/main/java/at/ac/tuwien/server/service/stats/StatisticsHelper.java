package at.ac.tuwien.server.service.stats;

import java.util.List;
import java.util.Set;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;


public class StatisticsHelper {
	
	public static final double d2r = (Math.PI / 180.0);
	

	public static Race updateRaceStats(Race race, Set<Location> updatedLocations, Location lastDBlocation, Location firstDBlocation){
		
		if(updatedLocations.isEmpty()) return race;
		//init if null
		if(race.getDistance() == null) race.setDistance(new Double(0));
		if(race.getOverallElevation() == null) race.setOverallElevation(new Double(0));
		if(race.getAvgSpeed() == null) race.setAvgSpeed(new Double(0));
		
		Location lastElem = lastDBlocation; 
		double distance = 0;
		double overallElevation = 0;
		for(Location l : updatedLocations){
			if(lastElem == null){
				lastElem = l;
				continue;
			}else{
				distance = distance + calculateDistanceBetweenPoints(lastElem, l);
				overallElevation = overallElevation + calculateElevationBetweenTwoPoints(lastElem, l);
				lastElem = l;
			}
		}
		
		race.setDistance(race.getDistance()+distance);
		race.setOverallElevation(race.getOverallElevation() + overallElevation);
		
		//timeitervall
		long timeIntervallInMillis = 0;
		Location[] locs = updatedLocations.toArray(new Location[updatedLocations.size()]);
		//ordered backwards
		if(firstDBlocation == null){
			timeIntervallInMillis = locs[0].getTimestamp().getTime() - locs[locs.length-1].getTimestamp().getTime();
		}else{
			timeIntervallInMillis = locs[0].getTimestamp().getTime()-firstDBlocation.getTimestamp().getTime();
		}
		//km/h
		double temp = new Double(timeIntervallInMillis)/new Double(1000*60*60);
		race.setAvgSpeed(new Double(race.getDistance()/temp));
		
		
		return race;
	}

	//calculate how much we climbed (only add positiv changes)
	private static double calculateElevationBetweenTwoPoints(Location lastElem,	Location l) {
		double a = lastElem.getAltitude();
		double b = l.getAltitude();
		if(a > b){
			return 0;
		}else{
			return b-a;
		}
	}

	public static double calculateDistanceBetweenPoints(Location lastElem,	Location l) {
		if(lastElem == null || l == null){
			return 0;
		}else{
			return haversine_km(lastElem.getLatitude(), lastElem.getLongitude(), l.getLatitude(), l.getLongitude());
		}
	}
	
	//calculate distance in kilometer
	private static double haversine_km(double lat1, double long1, double lat2, double long2)
	{
	    double dlong = (long2 - long1) * d2r;
	    double dlat = (lat2 - lat1) * d2r;
	    double a = Math.pow(Math.sin(dlat/2.0), 2) + Math.cos(lat1*d2r) * Math.cos(lat2*d2r) * Math.pow(Math.sin(dlong/2.0), 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double d = 6367 * c;

	    return d;
	}

	//calculate distance in mile
	private static double haversine_mi(double lat1, double long1, double lat2, double long2)
	{
	    double dlong = (long2 - long1) * d2r;
	    double dlat = (lat2 - lat1) * d2r;
	    double a = Math.pow(Math.sin(dlat/2.0), 2) + Math.cos(lat1*d2r) * Math.cos(lat2*d2r) * Math.pow(Math.sin(dlong/2.0), 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double d = 3956 * c; 

	    return d;
	}

	public static Double getDistanceInRaces(Set<Race> races) {
		Double sum = new Double(0);
		
		for(Race r : races){
			if(!r.getRaceName().equals(Constants.defaultRace))
			sum += r.getDistance();
		}
		
		return sum;
		
	}

	public static double calculateDistanceBetweenListOfPoints(List<Location> raceLocations) {
		
		double sum = 0;
		Location temp = null;
		
		for (Location location : raceLocations) {
			if(temp == null){
				temp = location;
			}else{
				sum += haversine_km(temp.getLatitude(), temp.getLongitude(), location.getLatitude(), location.getLongitude());
			}
		}
		
		return sum;
	}


	
}
