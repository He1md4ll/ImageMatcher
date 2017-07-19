package edu.hsbremen.cloud.comparison.calc;

public interface Calculator<T> {
    // TODO: Caching would be faster if image name would be used to refer to image instead of bytes
    // TODO: -- Comparison of bytes is slow
    T calculate(byte[] imageBytes);
}