package com.parking.ticketing.parking_lot;

public class App 
{
    public static void main(String[] args) {
       SystemInput systemInput = new SystemInput();
       systemInput.parseFileInput(args[0]);
    }
}
