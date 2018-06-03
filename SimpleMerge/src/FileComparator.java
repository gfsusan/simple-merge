import java.util.ArrayList;

public class FileComparator {
   /**
    * LCS length를 저장하는 배열 sequence alignment 알고리즘이나 LCS 알고리즘 참고
    */
   private int[][] C;
   /**
    * 각 Panel의 difference index를 저장하는 배열 같은 내용을 가진 줄은 서로의 index를 저장하고 다른 내용을 가진 줄에는
    * -1을 저장한다. ex) <Left> <leftDiffIndex> <Right> <rightDiffIndex> abc 0 abc 0 de
    * -1 fgh 2 fgh 1
    */
   private ArrayList<Integer> leftDiffIndex;
   private ArrayList<Integer> rightDiffIndex;
   private ArrayList<int[]> blocks;

   FileComparator(ArrayList<String> left, ArrayList<String> right) {
      leftDiffIndex = new ArrayList<Integer>();
      rightDiffIndex = new ArrayList<Integer>();
      blocks = new ArrayList<int[]>();

      LCSLength(left, right);
      compare();
      arrange();
      computeBlocks();
   }

   /**
    * Computing the length of the LCS sequence alignment와 같은 알고리즘 길이가 m인 text1과 길이가
    * n인 text2를 입력받아 모든 1 <= i <= m 와 1 <= j <= n에 대해 text1[1..i] 와 text2[1..j] 사이의
    * 값에 대해 LCS를 연산하고, C[i, j]에 저장한다. C[m, n]은 text1과 text2에 대한 LCS 값을 가지게 된다.
    */
   int LCSLength(ArrayList<String> left, ArrayList<String> right) {
      int m = left.size();
      int n = right.size();
      C = new int[m + 1][n + 1];

      /* 행이나 열의 index가 0인 element는 0으로 초기화한다 */
      for (int i = 0; i <= m; i++)
         C[i][0] = 0;
      for (int j = 0; j <= n; j++)
         C[0][j] = 0;

      for (int i = 1; i <= m; i++) {
         for (int j = 1; j <= n; j++) {
            if (left.get(i - 1).equals(right.get(j - 1)))
               C[i][j] = C[i - 1][j - 1] + 1;
            else
               C[i][j] = (C[i][j - 1] > C[i - 1][j]) ? C[i][j - 1] : C[i - 1][j];
         }
      }
      return C[m][n];
   }

   /**
    * @param C:
    *            matrix of LCS length between text 1 and text2
    * @param left
    * @param right
    * @param i:
    *            length(size) of text1
    * @param j:
    *            length(size) of text2
    */
   void compare() {
      /* if two strings have same line, store mutual index */
      int i = C.length - 1;
      int j = C[0].length - 1;

      while (C[i][j] != 0) {
         if (C[i][j] == C[i - 1][j]) {
            leftDiffIndex.add(0, -1);
            i--;
         }

         else if (C[i][j] == C[i][j - 1]) {
            rightDiffIndex.add(0, -1);
            j--;
         }

         else {
            leftDiffIndex.add(0, i);
            rightDiffIndex.add(0, j);
            i--;
            j--;

         }
      }

      while (i != 0) {

         leftDiffIndex.add(0, -1);
         i--;
      }

      while (j != 0) {

         rightDiffIndex.add(0, -1);
         j--;
      }

      leftDiffIndex.add(0, 0);
      rightDiffIndex.add(0, 0);
   }

   void arrange() {

      ArrayList<Integer> leftViewIndex = new ArrayList<Integer>();
      ArrayList<Integer> rightViewIndex = new ArrayList<Integer>();

      int L = 0, R = 0;
      int L_Max = leftDiffIndex.size();
      int R_Max = rightDiffIndex.size();
      while (L < L_Max && R < R_Max) {
         /* 같은 string인 경우 해당 string의 index를 저장 */
         if (this.leftDiffIndex.get(L) != -1 && this.rightDiffIndex.get(R) != -1) {
            leftViewIndex.add(L++);
            rightViewIndex.add(R++);
         }
         /* 다른 string이지만 같은 line에 있는 경우 해당 string의 index*(-1)을 저장 */
         else if (this.leftDiffIndex.get(L) == -1 && this.rightDiffIndex.get(R) == -1) {
            leftViewIndex.add(L * (-1));
            L++;
            rightViewIndex.add(R * (-1));
            R++;
         }
         /* 한쪽 패널에만 내용이 존재하는 경우 내용을 해당 패널에 저장하고 다른 패널에는 공백(0)을 저장 */
         else {
            while (this.rightDiffIndex.get(R) == -1) {
               leftViewIndex.add(0);
               rightViewIndex.add(R++);
            }
            while (this.leftDiffIndex.get(L) == -1) {
               leftViewIndex.add(L++);
               rightViewIndex.add(0);
            }
         }
      }

      leftDiffIndex = leftViewIndex;
      rightDiffIndex = rightViewIndex;

   }

   
   /**
    * block의 start index와 end index를 저장하는 메소드
    * blocks arraylist에 (start, end)를 저장
    */
   void computeBlocks() {
      int start = 0;
      int end = 0;
      int i = 1;
      int size = leftDiffIndex.size();

      // System.out.println("size: "+size);

      while (i < size) {
         /* 같은 내용인 경우 */
         while ((i < size && leftDiffIndex.get(i) > 0) && (rightDiffIndex.get(i) > 0)) {
            i++;
         }

         /* 왼쪽이 공백인 경우 */
         if (i < size && leftDiffIndex.get(i) == 0) {
            start = i++;
            while (i < size && leftDiffIndex.get(i) == 0) {
               i++;
            }
            end = i - 1;

            blocks.add(new int[] { start, end });
         }
         /* 오른쪽이 공백인 경우 */
         else if (i < size && rightDiffIndex.get(i) == 0) {
            start = i++;
            while (i < size && rightDiffIndex.get(i) == 0) {
               i++;
            }
            end = i - 1;

            blocks.add(new int[] { start, end });
         }
         /* 다른 string이 같은 line에 있는 경우 */
         else if (i < size && leftDiffIndex.get(i) < 0 && rightDiffIndex.get(i) < 0) {
            start = i++;
            while (i < size && leftDiffIndex.get(i) < 0 && rightDiffIndex.get(i) < 0) {
               i++;
            }
            end = i - 1;

            blocks.add(new int[] { start, end });
         }
      }
   }

   /**
    * leftDiffIndex의 element를 모두 0 이상의 정수로 바꾸어서 return 함
    * @return diffLeft
    */
   public ArrayList<Integer> getDiffLeft() {
      for (int i = 1; i < leftDiffIndex.size(); i++) {
         int num = leftDiffIndex.get(i);
         if (num > 0) {
            leftDiffIndex.set(i, num*(-1));
         }
      }      
      return this.leftDiffIndex;
   }

   /**
    * rightDiffIndex의 element를 모두 0 이상의 정수로 바꾸어서 return 함
    * @return diffRight
    */
   ArrayList<Integer> getDiffRight() {
      for (int i = 1; i < rightDiffIndex.size(); i++) {
         int num = rightDiffIndex.get(i);
         if (num < 0) {
            rightDiffIndex.set(i, num * (-1));
         }
      }
      
      return this.rightDiffIndex;
   }

   ArrayList<int[]> getBlocks() {
      return this.blocks;
   }

   public static void main(String[] args) {
      /*
       * This main is a test for this class. You can delete this if you don't need.
       */
      ArrayList<String> s1 = new ArrayList<String>();
      ArrayList<String> s2 = new ArrayList<String>();
      s1.add("Your job is to work on these two files:");
      s1.add(" - Board.JAVA");
      s1.add(" - Solver.JAVA");
      s1.add("");
      s1.add("Nothing to do in these three files");
      s1.add(" - MinPQ.JAVA");
      s1.add(" - Queue.JAVA");
      s1.add(" - Stack.JAVA");
      s1.add("");
      s1.add("Hello!");
      s1.add("Kim Soyeon test :)");
      s1.add("ischanged");
      
      s2.add("1");
      s2.add("2");
      s2.add("3");
      s2.add("4");
      s2.add("5");
      s2.add("6");
      s2.add("7");
      s2.add("8");
      s2.add("9");
      s2.add("0");
      s2.add("939331");
      s2.add("111");
      s2.add("11");
      
      FileComparator fc = new FileComparator(s1, s2);

      System.out.println("Left Panel=========");
      for (int i = 1; i < fc.getDiffLeft().size(); i++) {
         System.out.println("[" + i + "](=" + fc.getDiffLeft().get(i) + ")\t");
      }

      System.out.println("\nRight Panel=========");
      for (int i = 1; i < fc.getDiffRight().size(); i++) {
         System.out.println("[" + i + "](=" + fc.getDiffRight().get(i) + ")\t");
      }

      System.out.println("\nDiff blocks=========");
      for (int i = 0; i < fc.blocks.size(); i++) {
         System.out.println("block[" + i + "]: (" + fc.blocks.get(i)[0] + ", " + fc.blocks.get(i)[1] + ")");
      }

   }
}