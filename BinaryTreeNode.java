//Lucille Njoo

package tree.binary;
  
  public class BinaryTreeNode {
      
      private String data;
      private BinaryTreeNode left, right;
      
      BinaryTreeNode (String s) {
          data = s;
          left = null;
          right = null;
      }
      
      public void add (String s, String child) {
          if (child.equals("L")) {
              left = new BinaryTreeNode(s);
          } else if (child.equals("R")) {
              right = new BinaryTreeNode(s);
          } else {
              throw new IllegalArgumentException();
          }
      }
      
      public BinaryTreeNode getChild (String child) {
          return (child.equals("L")) ? left : right;
      }
      
      public String getString () {
          return data;
      }
      
      public static void preOrderPrint (BinaryTreeNode n) {
          //Base Case: done recursing, stop
          if (n == null) { return;}
          //Visit the node
          System.out.print(n.data + " ");
          //Recursive cases:
          preOrderPrint(n.left);
          preOrderPrint(n.right);
      }
      
      public void doubleTree () {
          makeDoubleTree(this);
      }
      
      public static boolean sameTree(BinaryTreeNode n1, BinaryTreeNode n2) {
          if (n1 == null && n2 == null) {return true;}
          if (!n1.data.equals(n2.data)) {return false;}
          return (sameTree(n1.left, n2.left) && sameTree (n1.right, n2.right));
      }
      
      private void makeDoubleTree (BinaryTreeNode root) {
          //Base case: stop recursing if root is null
          if (root == null) {return;}

          //Recursive case:
          BinaryTreeNode duplicate = new BinaryTreeNode(root.data);
          duplicate.left = root.left;
          root.left = duplicate;
          makeDoubleTree(duplicate.left);
          makeDoubleTree(root.right);
      }
      
      public static void main (String[] args) {
          BinaryTreeNode root1 = new BinaryTreeNode("A");
          root1.add("B", "L");
          root1.add("C", "R");
          BinaryTreeNode lefty1 = root1.getChild("L");
          lefty1.add("D",  "L");
          lefty1.add("E", "R");
          
          root1.doubleTree();
          System.out.println("root1 doubleTree: ");
          preOrderPrint(root1);
          System.out.println();
          
          BinaryTreeNode root2 = new BinaryTreeNode("A");
          root2.add("B", "L");
          root2.add("C", "R");
          BinaryTreeNode lefty2 = root2.getChild("L");
          lefty2.add("D",  "L");
          lefty2.add("E", "R");
          
          System.out.println("sameTree, root1 and root2: " + sameTree(root1, root2));
          
          BinaryTreeNode root3 = new BinaryTreeNode("A");
          root3.add("B", "L");
          root3.add("C", "R");
          BinaryTreeNode lefty3 = root3.getChild("L");
          lefty3.add("D",  "L");
          lefty3.add("E", "R");
          BinaryTreeNode righty3 = root3.getChild("R");
          righty3.add("F", "L");
          righty3.add("G", "R");
          
          root3.doubleTree();
          System.out.println("root3 doubleTree: ");
          preOrderPrint(root3);
          System.out.println();
          
          BinaryTreeNode root4 = new BinaryTreeNode("A");
          root4.add("B", "L");
          root4.add("C", "R");
          BinaryTreeNode lefty4A = root4.getChild("L");
          lefty4A.add("D",  "L");
          lefty4A.add("E", "R");
          BinaryTreeNode righty4A = root4.getChild("R");
          righty4A.add("F", "L");
          righty4A.add("G", "R");
          BinaryTreeNode lefty4B = lefty4A.getChild("L");
          lefty4B.add("H", "L");
          lefty4B.add("I", "R");
          
          System.out.println("sameTree, root3 and root4: " + sameTree(root3, root4));
          root4.doubleTree();
          System.out.println("root4 doubleTree: ");
          preOrderPrint(root4);
          System.out.println();

          
          BinaryTreeNode root5 = new BinaryTreeNode("A");
          root5.add("B", "L");
          root5.add("C", "R");
          BinaryTreeNode lefty5 = root1.getChild("L");
          lefty5.add("D",  "L");
          lefty5.add("E", "R");
          BinaryTreeNode righty5 = root1.getChild("R");
          righty5.add("F", "L");
          righty5.add("G", "R");
         
          System.out.println("sameTree, root3 and root5: " + sameTree(root3, root5));

      }
      
  }