import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/* Stephanie Buckingham
 * Programming Fundamentals
 * Summer Session II
 * Programming Assignment 3
 * 8/1/2020
 * 
 * Nearest Neighbor algorithm is used to classify iris plants to correct species based on
 * sepal and petal length and width.  Two files are loaded into the program, one for training, the
 * "iris-training-data.csv" and one for testing, the "iris-testing-data.csv".  The testing data is  
 * classified and the accuracy is computed.  
 */

public class NearestNeighbor {

	public static void main(String[] args) throws FileNotFoundException {

		// method to print program header
		printHeader();

		// create scanner to import name of file
		Scanner inputScan = new Scanner(System.in);

		// prompt user for name of training file
		System.out.print("Enter the name of the training file: ");
		File trainFile = new File("./src/" + inputScan.next());
		Scanner trainScan = new Scanner(trainFile);

		// prompt user for name of testing file
		System.out.print("Enter the name of the testing file: ");
		File testFile = new File("./src/" + inputScan.next());
		Scanner testScan = new Scanner(testFile);
		inputScan.close();

		// create 2d double array for values
		double[][] trainingValues = new double[75][4];
		double[][] testingValues = new double[75][4];

		// create string array for classes
		String[] trainingClasses = new String[75];
		String[] testingClasses = new String[75];

		// create array for the prediction classes
		String[] predictions = new String[75];

		// call method to copy the measurements in files to array
		trainingValues = createValuesArray(trainScan, trainingValues);
		testingValues = createValuesArray(testScan, testingValues);

		// call method to copy species names in files to array
		trainingClasses = createSpeciesArray(trainScan, trainingClasses);
		testingClasses = createSpeciesArray(testScan, testingClasses);

		// print space between
		System.out.println();

		// loop that goes through each row and uses nearest neighbor method to predict
		// classes and then records these predictions in new array
		for (int i = 0; i < 75; i++) {
			int nearestNeighbor = shortestDistance(trainingValues[i][0], trainingValues[i][1], trainingValues[i][2],
					trainingValues[i][3], trainingValues);

			predictions[i] = trainingClasses[nearestNeighbor];
		}
		// call method to print results
		printResults(predictions,testingClasses);
		
	}

	// method to print program header
	public static void printHeader() {
		// print program label
		System.out.println("Programming Fundamentals");
		System.out.println("NAME: Stephanie Buckingham");
		System.out.println("PROGRAMMING ASSIGNMENT 3\n");
	}

	// method to take classes from scanner and input them into a string array
	static String[] createSpeciesArray(Scanner scanClass, String[] classes) {

		int row = 0;

		while (scanClass.hasNextLine()) {

			String[] splitString = scanClass.nextLine().split(",");
			classes[row] = splitString[4];
			row++;
			scanClass.close();

		}

		return classes;
	}

	// method to take values from scanner and input them into a 2d double array
	static double[][] createValuesArray(Scanner scanValues, double[][] values) {

		while (scanValues.hasNextLine()) {
			String[][] tempArray = new String[75][4];

			String lines = scanValues.nextLine();
			String[] splitLines = lines.split(",", 5);
			int row = 0;
			for (int col = 0; col <= 3; col++) {
				for (int j = 0; j < 4; j++) {
					values[row][col] = Double.parseDouble(splitLines[j]);
				}
			}
			scanValues.close();
		}
		return values;
	}

	// method that calculates the shortest distance between the training value and the test value
	public static int shortestDistance(double sepalLength, double sepalWidth, double petalLength, double petalWidth,
			double[][] trainingValues) {

		int row = 0;
		double slTotal = Math.pow((trainingValues[0][0] - sepalLength), 2);
		double swTotal = Math.pow((trainingValues[0][1] - sepalWidth), 2);
		double plTotal = Math.pow((trainingValues[0][2] - petalLength), 2);
		double pwTotal = Math.pow((trainingValues[0][3] - petalWidth), 2);
		double distance = Math.sqrt(slTotal + swTotal + plTotal + pwTotal);
		for (int i = 0; i < 75; i++) {
			double slTest = Math.pow((trainingValues[i][0] - sepalLength), 2);
			double swTest = Math.pow((trainingValues[i][1] - sepalWidth), 2);
			double plTest = Math.pow((trainingValues[i][2] - petalLength), 2);
			double pwTest = Math.pow((trainingValues[i][3] - petalWidth), 2);
			double testDistance = Math.sqrt(slTest + swTest + plTest + pwTest);
			if (testDistance < distance) {
				row = i;
				distance = testDistance;
			}
			
		}

		return row;
	}

	// method that prints out an iteration of the testingClass and predictions
	// adds up correct classes and calculates and prints out accuracy 
	private static void printResults(String[] predictions, String[]testingClasses) {

	int correct = 0;

	for (int i = 0; i < 75; i++) {
		System.out.println((i+1) + ": " + testingClasses[i] + " " + predictions[i]);
		if (testingClasses[i].equals(predictions[i])) {
			correct++;
		}
		System.out.println("ACCURACY: " + (correct/predictions.length));
	}
	}
}
