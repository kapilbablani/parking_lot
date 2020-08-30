## Parking Lot Service
This system is designed to control a parking system.

####Feature Offered
1. Create Parking Lot
2. Park a car
3. Leave a car
4. Parking status check

####Code Highlights
******** App.java is the program start up file ******
1. The system has the complete core business logic in service layer.
2. The test packages covers all the test cases for core business logic.
3. The parking lot can be created with minimum of 5 parking lot space.
4. The default properties and messages has been kept in the PropertyUtil and MessageUtil files in the code.
5. The nearest free parking lot is alloted every time to the new car.
6. Optimized collection has been used to perform the operation.
7. The program is shell script enabled. 


### The steps to execute the project is as follows -

1. Execute ./setup.sh from the bin directory of the project root. This will compile the code, run tests and build jar file with name parking_lot-1.0.0-RELEASE.jar in the target directory
2. Execute ./parking_lot.sh <file_name>, which will execute the program. Example command - 
	./parking_lot.sh C:\\Users\\test\\Desktop\\input.txt
3. In case, the file is not provided to the above command, the default file from the location /functional_spec/fixtures/file_input.txt will be used.

######Contact
Email: test@mallparking.in

Call: 1800 XXX 2XXX

