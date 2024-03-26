import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    
    

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }
    
    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {
        String window = "";
        char c;
        In in = new In(fileName);
        // Reads just enough characters to form the first window
        for (int i=0;i<windowLength;i++)
        { 
            window=window+in.readChar();
        }
       boolean ex;
        // Processes the entire text, one character at a time
        while (!in.isEmpty())
         {
           // Gets the next character
           c  = in.readChar();
           // Checks if the window is already in the map
            ex=CharDataMap.containsKey(window);
           
            if (ex==false)
            {
                      List probs=new List();
                      probs.update(c);
                     CharDataMap.put(window, probs);
                     
            }
            else 
            {
                CharDataMap.get(window).update(c);
                
            }
           

            window=window+c;
            window=window.substring(1);
         
     }
          
        

        // The entire file has been processed, and all the characters have been counted.
        // Proceeds to compute and set the p and cp fields of all the CharData objects
        // in each linked list in the map.
        for (List probs : CharDataMap.values())
           calculateProbabilities(probs);
     
	}

    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	public void calculateProbabilities(List probs) {	
        
         if (probs.getSize()==0)
         {

         }
         else 
         {
        int q=probs.totalchar();
        double p ;
        double cpi;
        double cpiminus1;
        p=probs.get(0).count;
        //p=p/(probs.getSize()+1);
        p=p/q;
        cpi=p;
        cpiminus1=cpi;

        probs.get(0).addC( probs.get(0),p, cpi);   
      //  l.getFirst().addC(l.getFirst(),0.2,0.3);
     //   double cou=l.get(1).count;
     //   cou=cou/l.size;
        
       // l.get(1).addC(l.get(1),cou,1);   
      for (int i=1;i<probs.getSize();i++)
      {
        p= probs.get(i).count;
        p=p/q;
        cpi=cpiminus1+p;
        probs.get(i).addC(probs.get(i),p, cpi);
        cpiminus1=cpi;

      }
        
	}
}

      public boolean isThere(String s)
{
   return CharDataMap.containsKey(s);

}

    // Returns a random character from the given probabilities list.
	public char getRandomChar(List probs) {
		double ran= randomGenerator.nextDouble();
        //System.out.println(ran);
        Node currentNode=probs.getNode();
        Node lastNode=null;
        while (currentNode!=null)
        {
             if (ran<currentNode.cp.cp)
             {
                return currentNode.cp.chr;
               
             }
             else 
             {
                lastNode=currentNode;
                currentNode=currentNode.next;

             }
             
        }
        return ' ';
	}

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
        if (initialText.length()<windowLength)
        {
            return initialText;
        }
        String workString=initialText;
        String end=workString;
        char add;
        for (int i=0 ; i<textLength;i++)
        {
            if (CharDataMap.containsKey(workString))
            {
                add= getRandomChar( CharDataMap.get(workString));
                end=end+add;
                workString+=add;
                workString=workString.substring(1);
            }
            else 
            {
                return end;
            }
        }

        return end ;
        
	}

    /** Returns a string representing the map of this language model. */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}
    public int Howmuch() {
		int a=0;
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			a++;
		}
		return a;
	}


    public static void main(String[] args) {
       // int windowLength = Integer.parseInt(args[0]);
        int windowLength =6;
        //String initialText = args[1];
        String initialText="market";
        //int generatedTextLength = Integer.parseInt(args[2]);
        int generatedTextLength = 200;
        //boolean randomGeneration = args[3].equals("random");
        boolean randomGeneration =true ;
        //String fileName = args[4];
        String fileName = "shakespeareinlove.txt";

        // Create the LanguageModel object
        LanguageModel lm;
        if (randomGeneration)
        lm = new LanguageModel(windowLength);
         else
          lm = new LanguageModel(windowLength, 20);
          // Trains the model, creating the map.
         lm.train(fileName);
         int a=lm.Howmuch();

         //System.out.println(lm.toString());
         //System.out.println(a);
         // Generates text, and prints it.
         System.out.println();
          System.out.println(lm.generate(initialText, generatedTextLength));
          System.out.println();
}
}




/** 
        LanguageModel model = new LanguageModel(2);
        String c ;
        model.train("T1.txt");
        c=model.toString();
        boolean b=model.isThere("qq");
       c=model.generate("hi", 5);
        System.out.println(c);
        */


       /**  List l= new List();
        char c;
        String [] testWords = {"word","first","list","commitmteeeeee "};
        String [] solutions = {
            "((w 1 0.0 0.0) (o 1 0.0 0.0) (r 1 0.0 0.0) (d 1 0.0 0.0))",
            "((f 1 0.0 0.0) (i 1 0.0 0.0) (r 1 0.0 0.0) (s 1 0.0 0.0) (t 1 0.0 0.0))",
            "((l 1 0.0 0.0) (i 1 0.0 0.0) (s 1 0.0 0.0) (t 1 0.0 0.0))"
        };
        for (int i = testWords[3].length()-1; i >=0; i--) {
            c=testWords[3].charAt(i);
            l.update(c);
            System.out.println(c);
        }
        model.calculateProbabilities(l);
        System.out.println(l.toString());
        char c7=model.getRandomChar(l);
        System.err.println(c7);
        int count=0;
        int countC=0 ;
        int countO=0 ;
        int countM=0 ;
        int countI=0 ;
        int counT=0 ;
        int countE=0 ;
        int countREV=0 ;

        while (count<10000000)
        {
            c7=model.getRandomChar(l);
            switch (c7) {
                case 'c':
                   countC++;
                    break;
                case 'o':
                    countO++;
                    break;
                case 'm':
                    countM++;
                    break;
                case 'i':
                   countI++;
                    break;

                case 't' :
                counT++;   
                break;

                case 'e':
                countE++;
                break;
                
                default:
                countREV++;
                break;
        }
        count++;
    }
    System.out.println("c-"+ countC + "  o-"+countO+"  m-"+countM+"  i-"+countI+"  t"+ counT+ "  e-"+countE+ "   space-"+countREV);
        */
		
    