/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likwise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    private Node first;

    // The number of elements in this list
    private int size;
	
    /** Constructs an empty list. */
    public static void main(String[] args) {
      /**  CharData c1=new CharData('a');
        CharData c2=new CharData('b');
        CharData c3=new CharData('c');
        CharData c4=new CharData('d');
        CharData c5=new CharData('e');
        System.out.println(c1.toString());
        List l= new List();
        l.toString();
        int size = l.getSize();
        System.out.println(l.toString());
          l.addFirst('a');
        l.addFirst('b');
        System.out.println(l.toString());  
        */
        List l= new List();
        char c;
        String [] testWords = {"word","first","list","committee "};
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
       // l.getFirst().addC(l.getFirst(),0.2,0.3);
       // double cou=l.get(1).count;
       // cou=cou/l.size;
        
     //   l.get(1).addC(l.get(1),cou,1);
          System.out.println(l.toString());
          
        //System.out.println(solutions[0]);
    }
    public List() {
        first = null;
        size = 0;
    }

    /** Returns the number of elements in this list. */
    public int getSize() {
 	      return size;
    }

    /** Returns the first element in the list */
    public CharData getFirst() {
        return first.cp;
    }
    public Node getNode()
    {
        return first;
    }

    /** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) 
    {
            CharData c1=new CharData(chr);        
            Node a =new Node(c1);
            a.next=first;
            first=a;
            size++;
      }
      public int totalchar()
       {
        Node currentNode=first;
        int count=0;
        while (currentNode!=null)
        {
             count=count+currentNode.cp.count;
            currentNode=currentNode.next;
           

        }
        return count;
        
    }


      
    
    /** GIVE Textual representation of this list. */
    public String toString() {
        if (size==0)
        {
            return "()";

        }
        else 
        {
            String str="(";
            Node currentNode=first;
            Node nextNode=first.next;
            while(nextNode!=null)
            {
                str+= currentNode.toString()+" ";
                currentNode=currentNode.next;
                nextNode=nextNode.next;
            }
            str+= currentNode.toString()+")";
            return str;
        }
    }

    /** Returns the index of the first CharData object in this list
     *  that has the same chr value as the given char,
     *  or -1 if there is no such object in this list. */
    public int indexOf(char chr1) {
        Node currentNode=first;
        int count=0;
        while (currentNode!=null)
        {
            if (currentNode.cp.chr==chr1)
            {
                return count;
            }
            currentNode=currentNode.next;
            count++;

        }
        return -1;
        
    }

    /** If the given character exists in one of the CharData objects in this list,
     *  increments its counter. Otherwise, adds a new CharData object with the
     *  given chr to the beginning of this list. */
    public void update(char chr1) {
        Node currentNode=first;
        boolean changed=false;
        while (currentNode!=null)
        {
            if (currentNode.cp.chr==chr1)
            {
                currentNode.cp.count++;
                changed=true;
            }
            currentNode=currentNode.next;
        }

        if (changed==false)
        {
            addFirst(chr1);
        }
    }

    /** GIVE If the given character exists in one of the CharData objects
     *  in this list, removes this CharData object from the list and returns
     *  true. Otherwise, returns false. */
    public boolean remove(char chr1) 
    {
        Node prev=null;
        Node currentNode=first;
        while (currentNode!=null && currentNode.cp.chr!=chr1)
        {
            prev=currentNode;
            currentNode=currentNode.next;
        }
        if (currentNode==null) return false;
        if (prev==null)
        {
            first=first.next;
        }
        else 
        {
            prev.next=currentNode.next;
        }
        size --;
        return true ; 
    }

    /** Returns the CharData object at the specified index in this list. 
     *  If the index is negative or is greater than the size of this list, 
     *  throws an IndexOutOfBoundsException. */
    public CharData get(int index) {
        if (index<0||index>size)
        {
            return null;

        }
        else 
        {
            int count=0;
            Node currentNode=first;
           while (currentNode!=null && count!=index)
        {
           count++;
            currentNode=currentNode.next;
        }
        return currentNode.cp;

        }

    }

    /** Returns an array of CharData objects, containing all the CharData objects in this list. */
    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cp;
    	    current = current.next;
        }
        return arr;
    }

    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {
	    // If the list is empty, there is nothing to iterate   
	    if (size == 0) return null;
	    // Gets the element in position index of this list
	    Node current = first;
	    int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Returns an iterator that starts in that element
	    return new ListIterator(current);
    }
}
