package com.huffzip;

import com.huffzip.cli.CommandLineInterface;

public class Main {

    public static void main(String[] args) {

        CommandLineInterface cli =
                new CommandLineInterface();

        cli.run(args);
    }
}