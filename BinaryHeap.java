//Lucille Njoo

package tree.heap;
  
  import java.util.ArrayList;
  
  class BinaryHeap {
      
      ArrayList<Integer> heap;
      
      BinaryHeap () {
          heap = new ArrayList<Integer>();
      }
  
      /*
       * Continues to bubble values up the tree until we
       * find a node that is greater than it
       */
      public void bubbleUp (int index) {
          if (index == 0) {return;}
  
          int parent = getParent(index);
  
          if (heap.get(parent) < heap.get(index)) {
              Integer temp = heap.get(index);
              heap.set(index, heap.get(parent));
              heap.set(parent, temp);
              bubbleUp(parent);
          }
  
      }
      
      public void bubbleDown (int index, int heapSize) {
          //base case: this node of the tree is a leaf and has no more children. 
          if (!indexIsValid(getChild(index, 'L'), heapSize)) {
              return;
          }
              
          //recursive cases:
          int leftChildIndex = getChild(index, 'L');
          int rightChildIndex = getChild(index, 'R'); 
          int greatestChildIndex = (heap.get(leftChildIndex) < heap.get(rightChildIndex)) ? rightChildIndex : leftChildIndex;
          //case: this node has two children. Must check both children and swap with the greatest one. 
          if (indexIsValid(rightChildIndex, heapSize)) {
              if (heap.get(index) < heap.get(greatestChildIndex) && greatestChildIndex < heapSize) {
                  swapNodes(index, greatestChildIndex);
                  bubbleDown(greatestChildIndex, heapSize);
              } 
          } 
          //case: this node does not have a right child, only a left child. 
          else if (heap.get(index) < heap.get(leftChildIndex)) {
              swapNodes(index, leftChildIndex);
              bubbleDown(leftChildIndex, heapSize);
          }

      }
      
      public void insert (Integer toInsert) {
          heap.add(toInsert);
          bubbleUp(heap.size() - 1);
      }
  
      // Traversal helpers
      public int getParent (int index) {
          return (index - 1) / 2;
      }
      
      public int getChild (int index, char child) {
          int result = (index * 2) + 1;
          if (child == 'R') {
              result++;
          }
          return result;
      }
      
      public void print () {
          for (int i = 0; i < heap.size(); i++) {
              System.out.println(heap.get(i));
          }
      }
      
      public ArrayList<Integer> getSortedElements () {
          //Create a deep copy of the heap
          BinaryHeap sorted = new BinaryHeap();
          for (int index = 0; index < heap.size(); index ++) {
              sorted.heap.add(heap.get(index));
          }
          
          int count = sorted.heap.size();
          
          while (count > 1) {
              sorted.swapNodes(0, count - 1);
              count--;
              sorted.bubbleDown(0, count);
          }
          return sorted.heap;
      }
      
      private void swapNodes (int index1, int index2) {
          Integer placeholder = heap.get(index1);
          heap.set(index1, heap.get(index2));
          heap.set(index2, placeholder);
      }
      
      private boolean indexIsValid (int index, int heapSize) {
          return index < heapSize;
      }
      
      public static void main (String[] args) {
          BinaryHeap bb = new BinaryHeap();
          bb.insert(new Integer(15));
          bb.insert(new Integer(20));
          bb.insert(new Integer(3));
          bb.insert(new Integer(5));
          bb.insert(new Integer(10));
          bb.insert(new Integer(9));
          bb.insert(new Integer(8));
          
          ArrayList<Integer> sortedbb = bb.getSortedElements();
          System.out.println("unsorted: ");
          bb.print();
          System.out.println("sorted: " + sortedbb);
          
          BinaryHeap bb2 = new BinaryHeap();
          bb2.insert(new Integer(15));
          bb2.insert(new Integer(20));
          bb2.insert(new Integer(3));
          bb2.insert(new Integer(5));
          bb2.insert(new Integer(10));
          bb2.insert(new Integer(9));
          bb2.insert(new Integer(8));
          bb2.insert(new Integer(30));
          bb2.insert(new Integer(4));
          bb2.insert(new Integer(27));
          bb2.insert(new Integer(6));
          
          System.out.println("unsorted: ");
          bb2.print();
          System.out.println(bb2.getSortedElements());
          
      }
      
  }