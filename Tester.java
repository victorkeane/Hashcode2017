import java.io.*;

import java.util.*;



public class Tester{

	public static void main(String[] args)throws FileNotFoundException {

		Scanner in = new Scanner(System.in);

		//System.out.println("Enter the input file name (probably inputs.txt)");

		String[] input = new String[1];

		//input[0] = in.next();

		input[0] = "a.txt";

		try {

			if (isInputValid(input[0]) == true){

				System.out.print("Valid File");

			}

			//NewCode.main(input);

		}

		catch (FileNotFoundException ex){

			System.out.print("File not found");

		}

	}



	

	public static boolean isInputValid(String filename) throws FileNotFoundException {

		Scanner in = new Scanner(new File(filename));

		int numOfVids;

		int numOfEnds;

		int numOfRequests;

		if (in.hasNextInt() && in.hasNextInt() && in.hasNextInt()){

			numOfVids = in.nextInt();

			numOfEnds = in.nextInt();

			numOfRequests = in.nextInt();

		}

		else{

			System.out.println("First line does not conatin exactly 5 integers.");

			return false;

		}

		if (doesLineContainXInts(in, 2) == false){

			System.out.println("Error with Line 1 of file");

			return false;

		}

		if (doesLineContainXInts(in, numOfVids) == false){

			System.out.println("Error with Line 2 of file");

			return false;

		}

		for (int i = 0; i<numOfEnds; i++){

			if (!(in.hasNextInt()&&in.hasNextInt())){

				System.out.println("Invalid information about Endpoints");

				return false;

			}

			in.nextInt();

			int numOfCaches = in.nextInt();

			for (int j = 0; j< numOfCaches;j++){

				if(doesLineContainXInts(in,2)== false){

					System.out.println("Invalid information about Cache");

					return false;

			}

			}

		}

		for (int i = 0; i<numOfRequests; i++){

			if(doesLineContainXInts(in,3)== false){

				System.out.println("Invalid information about Requests");

				return false;

			}

		}

		return true;

				

	}



	public static boolean doesLineContainXInts(Scanner in, int numOfInts){

		for (int i = 0; i<numOfInts; i++){ //the first line should contain 5 ints

			if (!(in.hasNextInt())){

				return false;

			}

			in.nextInt();

		}

		return true;

	}

}
