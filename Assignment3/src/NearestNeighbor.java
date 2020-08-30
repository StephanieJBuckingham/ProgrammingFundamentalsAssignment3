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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NearestNeighbor {

	public static void main(String[] args) throws FileNotFoundException {

		// Method to print program header
		printHeader();

		// Create scanner to import name of file
		Scanner inputScan = new Scanner(System.in);

		// Prompt user for name of training file
		System.out.print("Enter the name of the training file: ");
		File trainFile = new File(inputScan.next());

		// Prompt user for name of testing file
		System.out.print("Enter the name of the testing file: ");
		File testFile = new File(inputScan.next());

		// Create 2d double array for values
		double[][] trainingValues = new double[75][4];
		double[][] testingValues = new double[75][4];

		// Create string array for classes
		String[] trainingClasses = new String[75];
		String[] testingClasses = new String[75];
		
		// Create the predictions array to hold the predicted values
		String[] predictions = new String[75];

		// Make scanners to scan in test values and classes
		Scanner testClassScan = new Scanner(testFile);
		Scanner testValuesScan = new Scanner(testFile);

		// Make scanners to scan in train values and classes
		Scanner trainClassScan = new Scanner(trainFile);
		Scanner trainValuesScan = new Scanner(trainFile);

		// Invoke createClassesArray method to copy classes into the arrays
		testingClasses = createClassesArray(testClassScan, testingClasses);
		trainingClasses = createClassesArray(trainClassScan, trainingClasses);

		// Invoke createValuesArray method to the copy the doubles into the arrays
		testingValues = createValuesArray(testValuesScan, testingValues);
		trainingValues = createValuesArray(trainValuesScan, trainingValues);

		// Iterates through each row and invokes the getNearestNeighbor method to find
		// the class label of the closest training example and then inputs these
		// predictions into an array
		int nearestneighbor = 0;
		for (int i = 0; i < 75; i++) {
			nearestneighbor = getNearestNeighbor(i, testingValues, trainingValues);
			predictions[i] = trainingClasses[nearestneighbor];
		}

		// Invoke printResults method to print the examples true label, predicted label,
		// and overall accuracy of model
		printResults(predictions, testingClasses);

		// Close Scanners to prevent resource leaks
		inputScan.close();
		testClassScan.close();
		trainClassScan.close();
		testValuesScan.close();
		trainValuesScan.close();
	}

	// Method that prints program header
	static void printHeader() {
		System.out.println("Programming Fundamentals");
		System.out.println("NAME: Stephanie Buckingham");
		System.out.println("PROGRAMMING ASSIGNMENT 3\n");
	}

	// Method to take classes from scanner and input them into a string array
	static String[] createClassesArray(Scanner scanClass, String[] classes) {

		int i = 0;

		// Loop to go through each row and split by commas and then select 4th index
		while (scanClass.hasNextLine()) {
			String[] splitClass = scanClass.nextLine().split(",");
			classes[i] = splitClass[4];
			i++;
		}
		return classes;
	}

	// Method to take values from scanner and input them into 2d double array
	private static double[][] createValuesArray(Scanner valuesScan, double[][] values) {

		int row = 0;
		String[][] splitValues = new String[75][4];

		// Loop to split each value by the comma
		while (valuesScan.hasNextLine()) {
			String[] splitLine = valuesScan.nextLine().split(",");

			// Loop to separate values into rows and parse into double
			for (int i = 0; i < 4; i++) {
				splitValues[row][i] = splitLine[i];
				values[row][i] = Double.parseDouble(splitValues[row][i]);
			}
			row++;
		}
		return values;
	}

	// Loops through table and uses computeDistance method to find distance and gets
	// the smallest distance because that will determine the class label it will be
	// given
	static int getNearestNeighbor(int testRow, double[][] test, double[][] train) {

		int nearestneighbor = 0;

		double nnDistance = computeDistance(testRow, 0, test, train);

		for (int i = 1; i < 75; i++) {
			if (computeDistance(testRow, i, train, test) < nnDistance) {
				nnDistance = computeDistance(testRow, i, train, test);
				nearestneighbor = i;
			}
		}
		return nearestneighbor;
	}

	// Uses distance formula to calculate distance the distance between
	// testing and training values
	static double computeDistance(int testRow, int trainRow, double[][] test, double[][] train) {

		double distance = Math.sqrt(Math.pow((test[testRow][0] - train[trainRow][0]), 2)
				+ Math.pow((test[testRow][1] - train[trainRow][1]), 2)
				+ Math.pow((test[testRow][2] - train[trainRow][2]), 2)
				+ Math.pow((test[testRow][3] - train[trainRow][3]), 2));

		return distance;
	}
	// Method that prints out an iteration of the testingClass and predictions
	// Adds up correct classes and calculates and prints out accuracy
	private static void printResults(String[] testingClasses, String[] predictions) {

		double correct = 0;
		int ex = 1;

		System.out.println("\n EX#: TRUE LABEL, PREDICTED LABEL");

		for (int i = 0; i < 75; i++) {
			System.out.println(ex + ": " + testingClasses[i] + " " + predictions[i]);
			if (testingClasses[i].equals(predictions[i])) {
				correct++;
			}
			ex++;
		}
		double accuracy = correct/ predictions.length;
		System.out.println("ACCURACY: " + accuracy);
	}
}