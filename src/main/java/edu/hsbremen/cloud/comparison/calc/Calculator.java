package edu.hsbremen.cloud.comparison.calc;

public interface Calculator<T> {
    T calculate(byte[] imageBytes);
}