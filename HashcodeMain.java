import java.io.*;

import java.util.*;



public class HashcodeMain{

	public static void main(String[] args) throws FileNotFoundException{

		Scanner in = new Scanner(new File("inputs.txt"));

		int numOfVids = in.nextInt();

		int numOfEndpoints = in.nextInt();

		int numOfDescriptions = in.nextInt();

		int numOfCaches = in.nextInt();

		int cacheSizes = in.nextInt();

		int[] vidSizes = new int[numOfVids];

		Map<Integer, int[]> overallDict = new HashMap<Integer, int[]>();

		

		for(int i=0; i<NumOfVids; i++)

			VidSizes[i] = in.nextInt();

		ArrayList<Endpoint> endpoints = new ArrayList<Endpoint>();

		for(int i=0; i<NumOfEndpoints; i++){

			endpoints.add(new Endpoint(in.nextInt(), in.nextInt() )); //adding each endpoint to an arraylist called endpoints

			for (int j=0 ; j<endpoints.get(i).NumOfCaches; j++)

				endpoints.get(i).mapOfCaches.put(in.nextInt(), in.nextInt()); //fills a dictionary in the Endpoint class with cache number and its latency

		}			

		while(in.hasNextInt()){

			int video = in.nextInt();

			if(in.hasNextInt()){ //awkward way of doing this but avoids NoSuchElementException. Look to improve later. 

				int endpoint = in.nextInt();

				if(in.hasNextInt()){

					int requests = in.nextInt();

					endpoints.get(endpoint).mapOfRequests.put(video, requests); //fills a dictionary in the Endpoint class with video and amount of requests

				}

			}

		}

		for(int CacheNum = 0; CacheNum<NumOfCaches; CacheNum++){//go through each Cache

			Map<Integer, Integer> CacheDict = new HashMap<Integer, Integer>();

			int CacheSize = CacheSizes;

			int VidValues = 0; //the value each Vid has to the given Endpoint

			for (int i = 0; i < endpoints.size(); i++) { //for each endpoint

				Endpoint endpoint = endpoints.get(i); 

				if (endpoint.mapOfCaches.containsKey(CacheNum)){ //if endpoint is connected

					CacheDict = findValue(OverallDict,  endpoint.mapOfCaches.get(CacheNum), endpoint.mapOfRequests, CacheDict,endpoint.latency, endpoint.mapOfCaches);

					}

				}



			//System.out.println("Cache " + CacheNum + " " + CacheDict.toString());

			OverallDict = fillUpCache( CacheNum, CacheDict, OverallDict, CacheSizes, NumOfVids, VidSizes); 

			}

		printOutput(OverallDict);

		}	





	public static Map<Integer, Integer> findValue(Map<Integer, int[]> OverallDict, int CacheLat, Map<Integer, Integer> Requests, Map<Integer, Integer> CacheDict, int latency, Map<Integer, Integer> mapOfCaches){

		//System.out.println("\n\n\n\n\nnew something");  //order cache 0- endpoint 0, cache 1 - endpoint 0, cache 1 - end 1, cache 2 - end 0 

		for (int Vid : Requests.keySet()) {

			int currentValue = (Requests.get(Vid) * (latency - CacheLat))/10000;

			int otherValue = 0;

			int newValue = 0;

			for (int Cache : OverallDict.keySet()){	

				if (intInArray(OverallDict.get(Cache), Vid) && mapOfCaches.get(Cache) != null ){

					newValue = (Requests.get(Vid) * (latency - mapOfCaches.get(Cache)))/10000;

					if (newValue > otherValue)

						otherValue = newValue;

					}

				}

 		

			if (currentValue>=otherValue)

				CacheDict.put(Vid, currentValue);

		}

		return CacheDict;

	}



	public static Map<Integer, int[]> fillUpCache(int CacheNum,Map<Integer, Integer> CacheDict,Map<Integer, int[]> OverallDict, int CacheSizes, int NumOfVids, int[] VidSizes)throws NullPointerException{

	int thisCacheSize = CacheSizes;

	int vidsInCache = 0;

	if (CacheDict.size() == 0)

		return OverallDict;

	while (CacheDict.size() != 0){

		int mostPopVid = mostPop(CacheDict);

		if (VidSizes[mostPopVid] <= thisCacheSize){ //if the vid fits in the cache

			thisCacheSize -= VidSizes[mostPopVid]; //reduce cache size

			vidsInCache += 1;

			if (OverallDict.get(CacheNum) == null){

				OverallDict.put(CacheNum,new int[NumOfVids+1]); //first element is how many vids are actually in array

				}

			OverallDict.get(CacheNum)[vidsInCache] = mostPopVid; //add mostPopVid

			}

		CacheDict.remove(mostPopVid); //remove vid from map

		}

	if (OverallDict.get(CacheNum) != null)

		OverallDict.get(CacheNum)[0] = vidsInCache;

	return OverallDict;

	}



	public static int mostPop(Map<Integer, Integer> CacheDict){

	int biggest = -1;

	for (int Vid: CacheDict.keySet()){

		if (biggest == -1 || CacheDict.get(Vid) > CacheDict.get(biggest))

			biggest = Vid;

		}

	return biggest;

	}



	public static void printOutput(Map<Integer, int[]> OverallDict){

		System.out.println(OverallDict.size());

		for (Integer CacheNum : OverallDict.keySet()) {

			int[] cacheArray = OverallDict.get(CacheNum);

			System.out.print(CacheNum);

			for (int i = 0;i < cacheArray[0];i++)

				System.out.print(" " + cacheArray[i+1]);

			System.out.println("");

		System.out.print((((1500*900)+ (1000*900))/(4000))*1000);

		}

	}



	public static boolean intInArray(int[] intArray, int correctNum){

		for (int num : intArray)

			if (num == correctNum)

				return true;

		return false;

	}

}



class Endpoint

{

	int latency;

	int NumOfCaches;

	Map<Integer, Integer> mapOfCaches = new HashMap<Integer, Integer>();

	Map<Integer, Integer> mapOfRequests = new HashMap<Integer, Integer>();

	

	public Endpoint(int latency, int caches){

		this.latency = latency;

		NumOfCaches = caches;

	}		

}
