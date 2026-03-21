package io.github.ashutosh_java_dev.terminal.table;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.MissingFormatWidthException;

public class TerminalTable {

    private ArrayList<String> header;
    private ArrayList<ArrayList<String>> data;
    private ArrayList<Integer> maxTableLength = new ArrayList<>();
    private int paddingLeft = 1;
    private int paddingRight = 1;
    public boolean nullable = false;
    private String nullData = "null";

    public TerminalTable() {
    }

    public TerminalTable(ArrayList<ArrayList<String>> data) {
	this.data = data;
    }

    public TerminalTable(ArrayList<String> header, ArrayList<ArrayList<String>> data) {
	this.header = header;
	this.data = data;
    }

    public void setPadding(int padding) {
	this.paddingLeft = padding;
	this.paddingRight = padding;
    }

    public void setPadding(int paddingLeft, int paddingRight) {
	this.paddingLeft = paddingLeft;
	this.paddingRight = paddingRight;
    }

    private void maxRowLength() {

	int columnCount = 0;

	if (header != null) {
	    columnCount = header.size();
	}

	for (ArrayList<String> list : data) {
	    if (columnCount < list.size()) {
		columnCount = list.size();
	    }
	}

	for (int i = 0; i < columnCount; i++) {
	    maxTableLength.add(0);
	}

	if (header != null) {
	    for (int i = 0; i < header.size(); i++) {
		maxTableLength.set(i, header.get(i).length() + (this.paddingLeft + this.paddingRight));
	    }
	}

	if (data != null) {
	    for (ArrayList<String> row : data) {
		for (int j = 0; j < row.size(); j++) {

		    int length = row.get(j).length() + (this.paddingLeft + this.paddingRight);

		    if (length > maxTableLength.get(j)) {
			maxTableLength.set(j, length);
		    }
		}
	    }
	}
    }

    private void printRow() {
	System.out.print("+");
	int sum = this.maxTableLength.stream().mapToInt(Integer::intValue).sum();
	System.out.print("-".repeat(sum + (this.maxTableLength.size() - 1)));
	System.out.print("+");
	System.out.println();
    }

    private void printHeader() {
	for (int i = 0; i < this.header.size(); i++) {
	    System.out.print("|");
	    System.out.print(" ".repeat(this.paddingLeft));
	    System.out.print(this.header.get(i));
	    System.out.print(" ".repeat(this.maxTableLength.get(i) - (this.paddingLeft + this.header.get(i).length())));
	}
	System.out.print("|");
	System.out.println();
    }

    private void printData() {
	for (ArrayList<String> list : data) {
	    for (int i = 0; i < list.size(); i++) {
		System.out.print("|");
		System.out.print(" ".repeat(this.paddingLeft));
		System.out.print(list.get(i));
		System.out.print(" ".repeat(this.maxTableLength.get(i) - (this.paddingLeft + list.get(i).length())));
	    }
	    System.out.print("|");
	    System.out.println();
	}
    }

    private void validate() {
	for (ArrayList<String> list : data) {
	    if (list.size() != this.maxTableLength.size()) {
		if (this.nullable) {
		    int itemsToAdd = this.maxTableLength.size() - list.size();
		    for (int i = 0; i < itemsToAdd; i++) {
			list.add(this.nullData);
		    }
		} else {
		    throw new MissingFormatWidthException(
			    "Data Provided is not Balanced. Whether Balance it or state nullable true.");
		}
	    }
	}

	if (this.header != null) {
	    if (this.header.size() != this.maxTableLength.size()) {
		if (this.nullable) {
		    int itemsToAdd = this.maxTableLength.size() - this.header.size();
		    for (int i = 0; i < itemsToAdd; i++) {
			this.header.add(this.nullData);
		    }
		} else {
		    throw new MissingFormatWidthException(
			    "Data Provided is not Balanced. Whether Balance it or state nullable true.");
		}
	    }
	}

    }

    public void mutate() {

    }

    public void build() {
	this.maxRowLength();
	this.validate();
	this.printRow();
	if (header != null) {
	    this.printHeader();
	    this.printRow();
	}
	this.printData();
	this.printRow();
    }

    public void setHeader(ArrayList<String> header) {
	this.header = header;
    }

    public void setData(ArrayList<ArrayList<String>> data) {
	this.data = data;
    }

    public ArrayList<Integer> getMaxTableLength() {
	return maxTableLength;
    }

    public void setMaxTableLength(ArrayList<Integer> maxTableLength) {
	this.maxTableLength = maxTableLength;
    }

    public void setNullData(String nullData) {
	if (nullData != null) {
	    this.nullData = nullData;
	} else {
	    System.out.println("Table refused 'null' input !!! ?");
	    throw new InputMismatchException("nullData can't be null itself, pls reframe from using it.");
	}
    }

}