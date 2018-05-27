import java.util.ArrayList;

public class FileComparator {
	private int[][] C;
	
	/** Computing the length of the LCS
	 * ���̰� m�� text1�� ���̰� n�� text2�� �Է¹޾� ��� 1 <= i <= m �� 1 <= j <= n�� ����
	 * text1[1..i] �� text2[1..j] ������ ���� ���� LCS�� �����ϰ�,
	 * C[i, j]�� �����Ѵ�. C[m, n]�� text1�� text2�� ���� LCS ���� ������ �ȴ�.
	 */
	public int LCSLength(ArrayList<String> text1, ArrayList<String> text2) {
		int m = text1.size();
		int n = text2.size();
		C = new int[m + 1][n + 1];
		
		/* ���̳� ���� index�� 0�� element�� 0���� �ʱ�ȭ�Ѵ� */
		for (int i = 0; i <= m; i++)
			C[i][0] = 0;
		for (int j = 0; j <= n; j++)
			C[0][j] = 0;
	
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (text1.get(i - 1).equals(text2.get(j - 1)))
					C[i][j] = C[i-1][j-1] + 1;
				else
					C[i][j] = (C[i][j-1] > C[i-1][j])? C[i][j-1] : C[i-1][j];
			}
		}
		
		return C[m][n];
	}
	
	/**
	 * print diff
	 */
	public void printDiff(int[][] C, ArrayList<String> text1, ArrayList<String> text2, int i, int j) {
		if (i > 0 && j > 0 && text1.get(i - 1).equals(text2.get(j-1))) {
			printDiff(C, text1, text2, i-1, j-1);
			System.out.println(i-1+":  " + text1.get(i-1));
		}
		else if (j > 0 && (i == 0 || C[i][j-1] >= C[i-1][j])) {
			printDiff(C, text1, text2, i, j - 1);
			System.out.println(i-1+":+ "+ text2.get(j-1));
		}
		else if (i > 0 && (j == 0 || C[i][j-1] < C[i-1][j])) {
			printDiff(C, text1, text2, i-1, j);
			System.out.println(i-1+":- " + text1.get(i-1));
		}
		else
			System.out.println("");
	}
	
	
	
	/**
	 * Reading out a LCS
	 *	backtracking�� �̿��Ͽ� C�� �迭�� �����Ѵ�. 
	 *	���λ��� ������ ���ڵ��� ������, ������ LCS�� �� ���ڸ� ���� String�� return
	 *	���λ��� ������ ���ڵ��� �ٸ���, ���� ū LCS�� return
	 */
	public String backtrack(int[][] C, ArrayList<String> text1, ArrayList<String> text2, int i, int j) {
		if (i == 0 || j == 0)
			return "";
		else if (text1.get(i).equals(text2.get(j)))
			return backtrack(C, text1, text2, i - 1, j - 1) + text1.get(i);
		else {
			if (C[i][j - 1] > C[i - 1][j])
				return backtrack(C, text1, text2, i, j-1);
			else
				return backtrack(C, text1, text2, i-1, j);
		}
	}
	
	/**
	 * Reading out all LCSs
	 * 	text1�� text2�� ���� ������ ����� return �Ѵٸ�,
	 *	����� ������ ��� subsequences�� �о���δ�
	 * 
	 */
	public String backtrackAll(int[][] C, ArrayList<String> text1, ArrayList<String> text2, int i , int j) {
		// TODO
		return "";
	}
	
	/**
	 * @return C
	 */
	public int[][] getC() {
		return this.C;
	}

	
	
	public static void main(String[] args) {
		FileComparator fc = new FileComparator();
		ArrayList<String> s1 = new ArrayList<String>();
		ArrayList<String> s2 = new ArrayList<String>();
		s1.add("abcdef");
		s2.add("abcdef");
		s1.add("aaa");
		s2.add("bbb");
		s1.add("hihihi");
		s2.add("hihihi");
		
		fc.LCSLength(s1, s2);
		fc.printDiff(fc.getC(), s1, s2, s1.size(), s2.size());
		
		
	}
}















