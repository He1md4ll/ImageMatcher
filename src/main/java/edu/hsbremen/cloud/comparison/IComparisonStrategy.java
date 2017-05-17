package edu.hsbremen.cloud.comparison;

public interface IComparisonStrategy {
    Integer compare(byte[] imageReference, byte[] imageCompared);
}