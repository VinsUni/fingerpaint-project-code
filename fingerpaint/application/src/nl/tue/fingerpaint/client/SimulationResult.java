package nl.tue.fingerpaint.client;

import java.io.Serializable;

/**
 * Represents a the results of a {@link Simulation}.
 * 
 * @author Group Fingerpaint
 */
public class SimulationResult implements Serializable {
	
	/** Randomly generated serial version UID */
	private static final long serialVersionUID = -3437263386499901097L;
	/** The resulting concentration vector(s) */
	private double[][] concentrationVectors;
	/** The array with segregation values */
	private double[] segregationPoints;
	
	/**
	 * Default constructor.
	 */
	public SimulationResult() {
		// Needed to implement Serializable
	}

	/**
	 * Constructs a new SimulationResults of a {@link Simulation}.
	 * 
	 * @param simulation The simulation that has been run
	 * @param concentrationVectors The resulting concentration vectors
	 * @param segregationPoints The resulting segregation values
	 */
	public SimulationResult(final double[][] concentrationVectors,
						    final double[] segregationPoints) {
		if (concentrationVectors == null) {
			throw new NullPointerException(
					"Argument concentrationVectors cannot be null");
		}
		if (segregationPoints == null) {
			throw new NullPointerException(
					"Argument segragationPoints cannot be null");
		}
		
		this.concentrationVectors = concentrationVectors;
		this.segregationPoints = segregationPoints;
	}

	/**
	 * @return the concentrationVectors
	 */
	public double[][] getConcentrationVectors() {
		return concentrationVectors;
	}

	/**
	 * @return the segregationPoints
	 */
	public double[] getSegregationPoints() {
		return segregationPoints;
	}
}
