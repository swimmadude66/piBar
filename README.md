piBar
=====

This software is specifically made to be loaded on to a Raspberry pi, with pi4J and Google Gson libraries. 

The src files in the rpi folder will not compile on a non Raspberry pi, but should function on a pi.
The nonpi folder should compile and allow examination of the UI on other systems.

To debug the classes can be compiled as is, but to run on a pi (and control full machine) the rpi needs the 3 cfg files
placed in /root/pi/Bartenderbot/data.

the "path" variable in ReadWriter.java needs to be changed to point to the folder which hold the cfg files.
