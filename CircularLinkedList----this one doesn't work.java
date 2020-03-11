package com.company;

public class CircularLinkedList<E> {
    public int size;
    private Node<E> head;
    private Node<E> tail;

    public CircularLinkedList() {
        size = 0;
        this.head = null;
        this.tail = null;
    }

    public int size() {
        return size;
    }

    public boolean add(E item) {
        this.add(size, item);
        return true;
    }

    public boolean add(int index, E item) {
        // index out of bounds, throw error
        if (index < 0) {
            throw new IndexOutOfBoundsException("no");
        } else if (index > size) {
            index = index % this.size;
        }
        // valid index, create a new node
        Node<E> adding = new Node<E>(item);
        // adding a new head
        if (index == 0) {
            adding.next = head;
            head = adding;
        } else { // adding anywhere else
            Node<E> before = getNode(index - 1);
            adding.next = before.next;
            before.next = adding;
            if (index == size) {
                tail = adding;
                tail.next = head;
            }
        }
        this.size++;
        return true;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(":(");
        }
        E toReturn = null;
        if (index == 0) {
            toReturn = head.data;
            head = head.next;
        } else {
            Node<E> before = getNode(index - 1);
            toReturn = before.next.data;
            before.next = before.next.next;
        }
        size--;
        return toReturn;
    }

    public int getIndex(E item) {
        int index = 0;
        Node<E> current = head;
        while (current != tail) {
            if (current.data == item) {
                return index;
            }
            index++;
            current = current.next;
        }
        if (current.data == item) {
            return index;
        }
        return -1;
    }

    public int swap(int index, int numAfter) {
        Node<E> current = getNode(index);
        // get first card
        Node<E> cardA = current;
        // get second card
        for (int i = 0; i < numAfter; i++) {
            current = current.next;
        }
        Node<E> cardB = current;
        // swap places
        E temp = cardA.data;
        cardA.data = cardB.data;
        cardB.data = temp;

        return index + numAfter;
    }

    private <E> Node getNode(int index) {
        Node<E> current = (Node<E>) head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public String toString() {
        String out = "";
        Node<E> current = head;
        for (int i = 0; i < size - 1; i++) {
            out += current.data;
            out += " -> ";
            current = current.next;
        }
        out += tail.data;
        return out;
    }

    public static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E data) {
            this.data = data;
        }
    }

    public static void tests(String args[]) {
        CircularLinkedList<Integer> MyCLL = new CircularLinkedList<Integer>();
        MyCLL.add(1);
        MyCLL.add(2);
        MyCLL.add(100);
        MyCLL.add(4);
        MyCLL.add(5);

        System.out.println(MyCLL.toString());
        System.out.println(MyCLL.tail.next.data);
        System.out.println(MyCLL.getIndex(5));
        MyCLL.swap(MyCLL.getIndex(100), 1);
        System.out.println(MyCLL.toString());
    }
    public static int getKeystreamValue (CircularLinkedList<Integer> deck) {
        // get the first joker
        int firstJokerIndex = deck.getIndex(27);
        // swap 27 with the next value
        deck.remove(firstJokerIndex);
        deck.add(firstJokerIndex + 1, 27);
        firstJokerIndex += 1;
        // find second joker
        int secondJokerIndex = deck.getIndex(28);
        deck.remove(secondJokerIndex);
        deck.add(secondJokerIndex + 2, 28);
        // move 28 2 places down
        secondJokerIndex += 2;

        // do triple cut
        // everything above the first joker (28 or 27) gets swapped with
        // everything below the second joker
        if (firstJokerIndex > secondJokerIndex) {
            int temp = firstJokerIndex;
            firstJokerIndex = secondJokerIndex;
            secondJokerIndex = temp;
        }
        // get everything before first joker
        CircularLinkedList<Integer> cardsBeforeFirstJoker = new CircularLinkedList<>();
        for (int i = 0; i < firstJokerIndex; i++) {
            cardsBeforeFirstJoker.add((Integer) deck.getNode(i).data);
        }
        // get everything after second joker
        CircularLinkedList<Integer> cardsAfterSecondJoker = new CircularLinkedList<>();
        for (int i = secondJokerIndex + 1; i < deck.size; i++) {
            cardsAfterSecondJoker.add((Integer) deck.getNode(i).data);
        }
        // get everything between (including jokers)
        CircularLinkedList<Integer> cardsBetweenJokers = new CircularLinkedList<>();
        for (int i = firstJokerIndex; i < secondJokerIndex + 1; i++) {  // this needs to include the joker cards
            cardsBetweenJokers.add((Integer) deck.getNode(i).data);
        }
        // swap the befores and the afters
        // newDeck = cardsAfterSecondJoker + cardsBetweenJokers + cardsBeforeFirstJoker;
        CircularLinkedList<Integer> newDeck = new CircularLinkedList<>();
        for (int i = 0; i < cardsAfterSecondJoker.size; i++) {
            newDeck.add((Integer) cardsAfterSecondJoker.getNode(i).data);
        }
        for (int i = 0; i < cardsBetweenJokers.size; i++) {
            newDeck.add((Integer) cardsBetweenJokers.getNode(i).data);
        }
        for (int i = 0; i < cardsBeforeFirstJoker.size; i++) {
            newDeck.add((Integer) cardsBeforeFirstJoker.getNode(i).data);
        }
        // check what the last card is, remove, get the value number from the top
        int lastCard = newDeck.tail.data;
        // if it is a joker, count it as 27
        if (lastCard == 28) {
            lastCard = 27;
        }
        newDeck.remove(newDeck.size - 1);
        CircularLinkedList<Integer> lastCardNumFromTop = new CircularLinkedList<>();
        for (int i = 0; i < lastCard; i++) {
            lastCardNumFromTop.add((Integer) newDeck.getNode(i).data);
        }
        // remove those cards from the top
        for (int i = 0; i < lastCardNumFromTop.size; i++) {
            newDeck.remove(0);
        }
        // add to the bottom
        for (int i = 0; i < lastCardNumFromTop.size; i++) {
            newDeck.add((Integer) lastCardNumFromTop.getNode(i).data);
        }
        // add the last card back
        newDeck.add(lastCard);

        // check what the top card is
        int topCard = (int) newDeck.getNode(0).data;
        // if joker, make it 27
        if (topCard == 28) {
            topCard = 27;
        }
        // get the topCard+1th value in the deck
        int keystreamValue = (int) newDeck.getNode(topCard +1).data;
        return keystreamValue;
    }
    public static CircularLinkedList<Integer> getKeystreamValues(CircularLinkedList<Integer> deck, int numValues) {
        CircularLinkedList<Integer> tempDeck = new CircularLinkedList<>();
        int[] cardsArray = {1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 3, 6, 9, 12, 15, 18, 21, 24, 27, 2, 5, 8, 11, 14, 17, 20, 23, 26};
        for (int i = 0; i < cardsArray.length; i++) {
            tempDeck.add(cardsArray[i]);
        }
        deck = tempDeck;
        CircularLinkedList<Integer> returnDeck = new CircularLinkedList<>();
        for (int i = 0; i < numValues; i++ ) {
            int keystreamValue = getKeystreamValue(deck);
            // if a joker, do it again
            while (keystreamValue == 27 || keystreamValue == 28) {
                keystreamValue = getKeystreamValue(deck);
            }
            returnDeck.add(keystreamValue);
        }
        return returnDeck;
    }
    public static String removeSpacesAndUpper(String string) {
        String returnString = "";
        // takes out spaces
        for (int i = 0; i < string.length(); i++) {
            String current = string.substring(i, i+1);
            if (!current.contains(" ")) {
                returnString += current;
            }
        }
        // makes uppercase
        return returnString.toUpperCase();
    }
    public static int[] toNumbers(String string) {
        string = string.toUpperCase();
        int[] returnList = new int[string.length()];
        for (int i = 0; i < string.length(); i++) {
            char current = string.charAt(i);
            returnList[i] = current;
        }
        return returnList;
    }
    public static String toLetters(int[] numbers) {
        String returnString = "";
        for (int i = 0; i < numbers.length; i++) {
            int current = numbers[i];
            String letter = String.valueOf((char)current);
            returnString += letter;
        }
        return returnString;
    }
    public static int[] addXs(int[] list) {
        if (list.length % 5 == 0) {
            return list;
        } else {
            int newLength = (list.length / 5); // integer-ify this
            newLength = (newLength +1) *5; // add one because it got int-ified, *5 to be the next mult of 5
            int[] returnList = new int[newLength];
            // add the values from original list until runs out. after run out, add Xs for the rest
            for (int i = 0; i < returnList.length; i++) {
                if (i < list.length) {
                    returnList[i] = list[i];
                } else {
                    returnList[i] = 'X';
                }
            }
            return returnList;
        }
    }
    public static String encrypt(String string, CircularLinkedList<Integer> deck) {
        String encryptedString = "";

        int[] numbers = toNumbers(string);

        // get Keystream values using the deck configuration, and make it same number of chars as input
        CircularLinkedList<Integer> keystreamValues = getKeystreamValues(deck, string.length());

        for (int i = 0; i < string.length(); i++) {
            int currentLetter = Character.toUpperCase(string.charAt(i));
            int currentKey = (int) keystreamValues.getNode(i).data;
            int cipherText = currentLetter + currentKey;
            if (cipherText > 26) {
                cipherText -= 26;
            }
            cipherText = cipherText-'A'+1;
            numbers[i] = cipherText;
            System.out.println(cipherText);
        }
        encryptedString = toLetters(numbers);
        return encryptedString;
    };
    public static String decrypt(String string, CircularLinkedList<Integer> deck) {
        String decryptedString = "";
        // get Keystream values using the deck configuration, and make it same number of chars as input
        CircularLinkedList<Integer> keystreamValues = getKeystreamValues(deck, string.length());

        int[] stringAsNums = addXs(toNumbers(string));
        int[] decryptedNums = new int[string.length()];
        for (int i = 0;  i < string.length(); i++) {
            int currentLetter = stringAsNums[i];
            int currentKey = (int) keystreamValues.getNode(i).data;
            int decryptedText = currentLetter - currentKey;
            if (decryptedText <= 0) {
                decryptedText +=26;
            }
            decryptedText = decryptedText-1+'A';
            decryptedNums[i] = decryptedText;
        }
        decryptedString = toLetters(decryptedNums);

        return decryptedString;
    }
    public static void main(String[] args) {
        CircularLinkedList<Integer> deck = new CircularLinkedList<>();
        int[] cardsArray = {1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 3, 6, 9, 12, 15, 18, 21, 24, 27, 2, 5, 8, 11, 14, 17, 20, 23, 26};
        for (int i = 0; i < cardsArray.length; i++) {
            deck.add(cardsArray[i]);
        }
        encrypt("gnarly", deck);

        decrypt("UOYYBDXXXX", deck);

    }
}
