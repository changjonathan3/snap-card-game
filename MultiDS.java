/**CS 0445 Fall 17
 * @author Jonathan Chang
 * Assignment 1
 * Khattab
 */
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class MultiDS<T> implements PrimQ<T>, Reorder{
    /** Constructors */
    private T [] theBag;
    private int count;
    private boolean initialized=false;
    private static final int DEFAULT_CAPACITY = 52;

    /** Creates an empty bag whose initial capacity is 52. */
    public MultiDS(){
        this(DEFAULT_CAPACITY);
    }
    /** Creates an bag whose capacity is entered. */
    public MultiDS(int desiredCapacity)
    {
        if (desiredCapacity <= DEFAULT_CAPACITY)
        {
            @SuppressWarnings("unchecked")
            T[] tempBag = (T[])new Object[desiredCapacity]; // Unchecked cast
            theBag = tempBag;
            count = 0;
            initialized = true;
        }
        else
            throw new IllegalStateException("Attempt to create a bag " +
                    "whose capacity exceeds " +
                    "allowed maximum.");
    }

    /** Adds a new entry to this bag.
     @param item  The object to be added to PrimQ<T> in next available location.
     @return  True if the addition is successful, or false if not (no room). */
    public boolean addItem(T item){
        checkInitialization();
        boolean result = true;
        if (full())
        {
            result = false;
        }
        else
        {
            theBag[count] = item;
            count++;
        }
        return result;
    }

    /** Removes "oldest" item in PrimQ, if possible.
     @return  Either the removed entry, if the removal
     was successful, or null if empty. */
    public T removeItem(){
        checkInitialization();
        if(empty()){
            return null;
        }
        else{
            T result = top();
            for (int i=0; i<theBag.length-1;i++)
                theBag[i]=theBag[i+1];
            theBag[count-1] = null;             // Remove reference to last entry
            count--;
            return result;
        }
    }

    /** Returns the "oldest" item in the PrimQ.  If the PrimQ
     is empty, return null.*/
    public T top(){
        if(empty()){
            return null;
        }
        else{
            T oldest=theBag[0];
            return oldest;
        }
    }

    /** Return true if the PrimQ is full, and false otherwise*/
    public boolean full(){
        return count >= theBag.length;
    }

    /** Sees whether this bag is empty.
     @return  True if this bag is empty, or false if not. */
    public boolean empty(){
        return count == 0;
    }

    /** Gets the current number of entries in this bag.
     @return  The integer number of entries currently in this bag. */
    public int size(){
        return count;
    }

    /** Removes all entries from this bag. */
    public void clear(){
        while (!empty())
            removeItem();
    }

    /** Retrieves all entries that are in this bag.
     @return  A newly allocated array of all the entries in this bag. */
    public String toString()
    {
        checkInitialization();
        StringBuilder sb = new StringBuilder();
            for(int i = 0; i < count-1; i++){
                sb.append(theBag[i]+" ");
            }
        return "Contents:\n"+sb.toString();
    }
    /** Logically reverse the data in the Reorder object so that the item
     that was logically first will now be logically last and vice
     versa. */
    public void reverse(){
        for(int i = 0; i < count / 2; i++) //switch spots until reach midpoint
        {
            T temp = theBag[i];
            theBag[i] = theBag[count - i - 1];
            theBag[count - i - 1] = temp;
        }
    }

    /** Remove the logical last item of the DS and put it at the front. */
    public void shiftRight(){
        T temp = theBag[count - 1];
        theBag[count - 1] = null;
        for (int i = count-1; i >= 1; i--) {
            theBag[i] = theBag[i - 1];
        }
        theBag[0] = temp;
    }

    /** Remove the logical first item of the DS and put it at the end. */
    public void shiftLeft(){
        T temp=theBag[0];
        theBag[0]=null;
        for(int i=0;i<count-1;i++){
            theBag[i]=theBag[i+1];
        }
        theBag[count-1]=temp;
    }

    /** Reorganize the items in the object in a pseudo-random way. */
    public void shuffle() {
        Random rnd = ThreadLocalRandom.current();
        for (int i = count - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // swap spots
            T a = theBag[index];
            theBag[index] = theBag[i];
            theBag[i] = a;
        }
    }

    /** Throws exception if object not initialized*/
    private void checkInitialization()
    {
        if (!initialized)
            throw new SecurityException("ArrayBag object is not initialized properly.");
    }
}