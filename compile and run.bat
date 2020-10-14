cd class
del /S *.class
cd ../src
javac -d ../class ConsolePanel.java DFSMPanel.java DFSMSimulator.java MainFrame.java TextPanel.java
cd ../class
java DFSMSimulator