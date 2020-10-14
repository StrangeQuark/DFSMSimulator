import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DFSMSimulator
{
    private BufferedReader bufferedReader;
    private BufferedReader testReader;
    private int numberOfStates;
    private int statesCounter;
    private int[] alphabets;
    private int[] states;
    private int[][] functions;
    private int currentState;

    public DFSMSimulator(File dFile, File sFile) throws Exception
    {
        bufferedReader = new BufferedReader(new FileReader(dFile));
        String s = bufferedReader.readLine();
        recognizeAlphabet(s);
        s = bufferedReader.readLine();
        numberOfStates = recognizeStates(s);
        s = bufferedReader.readLine();
        recognizeAcceptingStates(s);
        s = bufferedReader.readLine();
        functions = new int[(integerCounter(s) / 3) * numberOfStates][3];
        while(s != null)
        {
            recognizeFunctions(s);
            s = bufferedReader.readLine();
        }
        simulateDFSM(sFile);
    }

    private String trimStrings(String s)
    {
        s = s.replaceAll("[^\\w]", " ");

        s = s.trim();

        s = s.replaceAll(" +", " ");

        return s;
    }

    private int integerCounter(String s)
    {
        s = trimStrings(s);
        int counter = 1;

        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) == ' ')
            {
                counter++;
            }
        }
        return counter;
    }

    private String alphaToNum(String s)
    {
        String str = "";

        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) == 'a' || s.charAt(i) == '0')
                str += "0";
            else if(s.charAt(i) == 'b' || s.charAt(i) == '1')
                str += "1";
            else if(s.charAt(i) == 'c' || s.charAt(i) == '2')
                str += "2";
            else if(s.charAt(i) == 'd' || s.charAt(i) == '3')
                str += "3";
            else if(s.charAt(i) == 'e' || s.charAt(i) == '4')
                str += "4";
            else if(s.charAt(i) == 'f' || s.charAt(i) == '5')
                str += "5";
            else if(s.charAt(i) == 'g' || s.charAt(i) == '6')
                str += "6";
            else if(s.charAt(i) == 'h' || s.charAt(i) == '7')
                str += "7";
            else if(s.charAt(i) == 'i' || s.charAt(i) == '8')
                str += "8";
            else if(s.charAt(i) == 'j' || s.charAt(i) == '9')
                str += "9";
            else if(s.charAt(i) == ' ')
                str += " ";
        }
        return str;
    }

    private void recognizeAlphabet(String s)
    {
        String digit = "";

        s = alphaToNum(s);

        s = trimStrings(s);

        alphabets = new int[integerCounter(s)];

        int counter = 0;

        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) != ' ')
            {
                digit += s.charAt(i);
            }
            else
            {
                alphabets[counter] = Integer.parseInt(digit);
                digit = "";
                counter++;
            }
            if(i == s.length() - 1)
            {
                alphabets[counter] = Integer.parseInt(digit);
            }
        }
    }

    private int recognizeStates(String s)
    {
        int temp = Integer.parseInt(s);
        states = new int[temp];
        return temp;
    }

    private void recognizeAcceptingStates(String s)
    {
        String digit = "";

        s = trimStrings(s);

        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) != ' ')
            {
                digit += s.charAt(i);
            }
            else
            {
                states[Integer.parseInt(digit)] = 1;
                digit = "";
            }
            if(i == s.length() - 1)
            {
                states[Integer.parseInt(digit)] = 1;
            }
        }
    }

    private void recognizeFunctions(String s)
    {
        String digit = "";
        int counter1 = 0;
        int counter2 = 0;

        s = trimStrings(s);

        s = alphaToNum(s);
        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) != ' ')
            {
                digit += s.charAt(i);
            }
            else
            {
                functions[counter1 + (alphabets.length*statesCounter)][counter2] = Integer.parseInt(digit);
                counter2++;
                if(counter2 == 3)
                {
                    counter1++;
                    counter2 = 0;
                }
                digit = "";
            }
            if(i == s.length() - 1)
            {
                functions[counter1 + (alphabets.length*statesCounter)][counter2] = Integer.parseInt(digit);
            }
        }
        statesCounter++;
    }

    private void simulateDFSM(File inputFile) throws Exception
    {
        currentState = 0;
        boolean b;
        testReader = new BufferedReader(new FileReader(inputFile));

        String s = testReader.readLine();

        s = alphaToNum(s);

        for(int i = 0; i < s.length(); i++)
        {
            b = true;
            for(int j = 0; j < functions.length; j++)
            {
                if(b)
                {
                    if (functions[j][0] == currentState && functions[j][1] == Character.getNumericValue(s.charAt(i)))
                    {
                        currentState = functions[j][2];
                        b = false;
                    }
                }
            }
        }

        if(states[currentState] == 1)
            System.out.println("Accept\n");
        else
            System.out.println("Reject\n");
    }

    public int getNumberOfStates(){return numberOfStates;}
    public int[] getAlphabets(){return alphabets;}
    public int[] getStates(){return states;}
    public int[][] getFunctions(){return functions;}
    public int getNumberOfFunctions(){return functions.length;}
    public int getCurrentState(){return currentState;}

    public static void main(String[] args) throws Exception
    {
        new MainFrame();
    }
}
