import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFunctions {
    public static void main(String[] args) {
        // many test sets
        ArrayList<Integer> randNums = new ArrayList<Integer>(Arrays.asList(1,2,3,4,3,2,1));
        ArrayList<Integer> randNums2 = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8));
        ArrayList<Integer> randNums3 = new ArrayList<Integer>(Arrays.asList(1, 4, 5, 6, 5, 5, 2));
        ArrayList<String> randWords = new ArrayList<String>(Arrays.asList("I", "like", "to", "eat", "eat", "eat", "apples", "and", "bananas"));
        ArrayList<Integer> permTest = new ArrayList<Integer>(Arrays.asList(1,2,4));
        ArrayList<Integer> permTest2 = new ArrayList<Integer>(Arrays.asList(2,1,4));
        ArrayList<String> permTest3 = new ArrayList<String>(Arrays.asList("bag", "of", "fish"));
        ArrayList<String> permTest4 = new ArrayList<String>(Arrays.asList("of", "fish", "bag"));
        ArrayList<Integer> permTest5 = new ArrayList<Integer>(Arrays.asList(2,1,3));
        ArrayList<String> permTest6 = new ArrayList<String>(Arrays.asList("I", "like", "fish"));

        // unique test
        System.out.println(unique(randNums)); // false
        System.out.println(unique(randNums2)); // true
        // all multiples test
        System.out.println(allMultiples(randNums, 2));
        System.out.println(allMultiples(randNums2, 4));
        // all strings of size test
        System.out.println(allStringsOfSize(randWords, 3));
        // is permutation test
        System.out.println(isPermutation(permTest, permTest2)); // true
        System.out.println(isPermutation(permTest, permTest5)); // false
        System.out.println(isPermutation(permTest2, permTest5)); // false
        System.out.println(isPermutation(permTest3, permTest4)); // true
        System.out.println(isPermutation(permTest3, permTest6)); // false
        System.out.println(isPermutation(permTest4, permTest6)); // false
        // string to list of words test
        System.out.println(stringToListOfWords("banana"));
        System.out.println(stringToListOfWords("banana bread I like cheese"));
        System.out.println(stringToListOfWords("banana hi hellllllooooo"));
        // EXTRA CREDIT
        System.out.println(stringToListOfWords("I! like! cheese& fish food:;"));
        // remove all instances test (doesn't print anything because it returns void)
        removeAllInstances(randNums3, 5);

    }
    public static <E> boolean unique(List<E> list) {
        // check if there are ever repeats
        // if an item repeats, the items are not unique
        List<E> checkItems = new ArrayList<E>();
        for (int i = 0; i < list.size(); i++) {
            // add the key to the checkItems if the key isn't already there
            if (!checkItems.contains(list.get(i))) {
                checkItems.add(list.get(i));
            } else { // if the value is already there, the items are not unique
                return false;
            }
        }
        // if the item is never repeated, all values are unique
        return true;
    }
    public static List<Integer> allMultiples(List<Integer> list, int num) {
        List<Integer> multiples = new ArrayList<>();
        // go through the list
        for (int i = 0; i < list.size(); i++) {
            // add the numbers that are divisible by the number to the return list
            if (list.get(i) % num == 0) {
                multiples.add(list.get(i));
            }
        }
        return multiples;
    }
    public static List<String> allStringsOfSize(List<String> list, int length) {
        List<String> listOfStringsOfSpecLength = new ArrayList<String>();
        // go through the list and filter the words that have the length
        for (int i = 0; i < list.size(); i++) {
            // if the word is of the right length, add it to the return list
            if (list.get(i).length() == length) {
                listOfStringsOfSpecLength.add(list.get(i));
            }
        }
        return listOfStringsOfSpecLength;
    }
    public static <E> boolean isPermutation(List<E> listA, List<E> listB) {
        // if sizes are not the same, false
        if (listA.size() != listB.size()) {
            return false;
        }
        // for each item in A, count each time each item occurs
        // this is fine b/c the lists are the same length
        for (E item : listA) {
            int countA = 0;
            int countB = 0;
            for (E itemInA : listA) {
                // count the number of times item in A is equal to the item
                if (item == itemInA || item.equals(itemInA)) {
                    countA++;
                }
            }
            for (E itemInB : listB) {
                // count the number of times item in B is equal to the item
                if (item == itemInB || item.equals(itemInB)) {
                    countB++;
                }
            }
            // if the count in each is every different, it is false
            if (countA != countB) {
                return false;
            }
        }
        // if the two lists pass, they are permutations of each other
        return true;
    }
    public static List<String> stringToListOfWords(String string) {
        List listOfWords = new ArrayList<String>();
        String punctuation = ",./?;:~!@#$%^&*()";
        String stringWithoutPunc = "";

        // EXTRA CREDIT
        // strips the punctuation
        // checks each character in the string
        for (int i = 0; i < string.length(); i++) {
            // if the string of punctuations contains the character in the string, skip
            String current = string.substring(i, i+1);
            // if the current character is not punctuation, add it to the string
            if (!punctuation.contains(current)) {
                stringWithoutPunc += current;
            }
        }
        // for each word in the string, separate it by the whitespace
        for (String word : stringWithoutPunc.split("\\s+")) {
            // add to the return array
            listOfWords.add(word);
        }




        return listOfWords;
    }
    public static <E> void removeAllInstances(List<E> list, E item) {
        int countOfItem = 0;
        // go through the list and count how many times the item pops up
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == item || list.get(i).equals(item)) {
                countOfItem++;
            }
        }
        // for the number of times the item pops up, remove from list
        while (countOfItem > 0) {
            list.remove(item);
            countOfItem--;
        }
    }
}
