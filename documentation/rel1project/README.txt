Group Members:
- Anthony Palumbo ajp1925@rit.edu
- Nicholas Feldman ncf1362@rit.edu
- Edward Wong exw4141@rit.edu
- Christopher Lim cxl2436@rit.edu
- Charles Barber crb7054@rit.edu

SWEN-262 Design Project R1: Library Book Management System

To run the console version of the project, run start.bat (can be run as a bash script if using a UNIX system.)
To run the basic API version run startAPI.bat (also can be run as a bash script.)

************************************************************************************************************************
A file named data.ser will be created after the initial startup of the system, in order to get a clean start with
the system simply delete this file as it contains the serialized data.
************************************************************************************************************************

File information:
    -buildlog.txt contains the output of gradle following a clean build from scratch
    -design.pdf is the design document
    -LBMS-R1.jar is the Java executable that will be run to start the program
    -start.bat is the script file to start the console mode
    -startAPI.bat is the script file to start the API mode of the program
    -vclog.txt is the git log for the project
    -presentation.pdf is the presentation for release 1
    -data.ser is the serialized java byte code for the system after the first startup

Known Bugs/Errors:
    -For the library report, requesting a report for a certain number of days does not display the correct information
    -Checking out a book sometimes works even though the library may not own any books