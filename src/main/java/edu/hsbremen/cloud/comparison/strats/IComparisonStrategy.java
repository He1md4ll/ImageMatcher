package edu.hsbremen.cloud.comparison.strats;

public interface IComparisonStrategy {
    Double compare(byte[] imageReference, byte[] imageCompared);
}