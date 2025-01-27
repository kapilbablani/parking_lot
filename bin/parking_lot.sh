#!/usr/bin/env bash
# Add script to run program here.
# Complete bin/setup so that after it is
# run, bin/parking_lot can be used to launch
# it.

# This variable contains absolute path of this `parking_lot` script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

# Use DIR variable above to pinpoint your jar/executable/main class
# e.g.
# - if java
#   java -cp $DIR/../target/ParkingLot-1.0-SNAPSHOT.jar com.gojek.Main $1
# - if python
#   python3 $DIR/../main.py $1
# - if ruby
#   ruby $DIR/../main.rb $1
# 
# Important: Above commands is just examples, please modify to suit your requirement as necessary

  
#!/bin/sh
arg1=$1
##directory where jar file is located    
cd ../
dir=target
##jar file name
jar_name=parking_lot-1.0.0-RELEASE.jar

if [ -z "$1" ] ; then
        java -jar $dir/$jar_name './functional_spec/fixtures/file_input.txt'

else
	java -jar $dir/$jar_name $arg1

fi
