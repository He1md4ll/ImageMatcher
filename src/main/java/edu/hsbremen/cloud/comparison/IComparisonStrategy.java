package edu.hsbremen.cloud.comparison;

public interface IComparisonStrategy {
    Double compare(byte[] imageReference, byte[] imageCompared);
}