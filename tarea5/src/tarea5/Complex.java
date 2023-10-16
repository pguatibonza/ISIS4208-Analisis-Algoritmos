package tarea5;

public  class Complex {
        double real;
        double imag;

        Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        Complex add(Complex other) {
            return new Complex(real + other.real, imag + other.imag);
        }

        Complex subtract(Complex other) {
            return new Complex(real - other.real, imag - other.imag);
        }

        Complex multiply(Complex other) {
            return new Complex(real * other.real - imag * other.imag, real * other.imag + imag * other.real);
        }

        Complex divide(double divisor) {
            return new Complex(real / divisor, imag / divisor);
        }
        Complex conjugate(){
            return new Complex(real,-imag);
        }

        @Override
        public String toString() {
            return "(" + real + ", " + imag + "i)";
        }
    
}
