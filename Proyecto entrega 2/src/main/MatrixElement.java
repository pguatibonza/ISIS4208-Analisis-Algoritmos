package main;

public class MatrixElement implements Comparable<MatrixElement> {
    float usageProbability;
	int i;
	int j;

	public MatrixElement(float usageProbability, int i, int j) {
        this.usageProbability = usageProbability;
        this.i = i;
        this.j = j;
    }

    @Override
    public int compareTo(MatrixElement other) {
        // Compare based on usage probability
        return Float.compare(this.usageProbability, other.usageProbability);
    }
}
