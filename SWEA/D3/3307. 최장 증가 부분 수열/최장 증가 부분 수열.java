import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA
 * @author eunwoo.lee
 * 
 * 1. 테스트 케이스 수를 입력받고 그만큼 반복한다.
 * 2. 수열을 입력받는다.
 * 3. DP: lis배열을 채운다
 * 	lis[i]: i번째 요소를 lis구성의 마지막으로 하는 최장 길이
 * 	3-0. lis[0] = 0
 * 	3-1. 모든 수열의 인덱스에 대해 탐색한다.(seqIdx)
 * 		numIdx가 lis 구성의 마지막이 되는 최소 길이인 1로 초기화한다.
 * 		3-2. 탐색 대상의 전에 있는 원소들을 차례로 탐색한다. (searchIdx)
 * 		if searchIdx의 수열 값이 numIdx의 수열 값보다 작다면 (오름차순 만족)
 * 		-> 직전 최적해 vs lis[searchIdx]뒤에 내가 붙는 경우 중 더 긴 것
 * 			lis[seqIdx] = max(lis[seqIdx], lis[searchIdx]+1) 
 * 4. lis 배열의 최대값을 출력한다.
 */

public class Solution {
	
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static int seqLength; // 수열의 길이
	static int[] sequence, lis; // 수열 저장
	
	
	public static void inputTestcase() throws IOException {
		// 수열의 길이 입력받기
		st = new StringTokenizer(br.readLine().trim());
		seqLength = Integer.parseInt(st.nextToken());
		
		// 수열 입력받기
		sequence = new int[seqLength+1];
		st = new StringTokenizer(br.readLine().trim());
		for (int idx=1; idx<=seqLength; idx++) {
			sequence[idx] = Integer.parseInt(st.nextToken());
		}
		
	}
	
	public static void makeLIS() {
		// lis[i]: i번째 요소를 lis구성의 마지막으로 하는 최장 길이
		lis = new int[seqLength+1];
		
		// 3-0. lis[0]=0
		lis[0] = 0;
		// 3-1. 수열의 모든 수를 차례로 탐색한다.
		for (int seqIdx=1; seqIdx<=seqLength; seqIdx++) {
			lis[seqIdx] = 1;
			// 3-2. 탐색 대상의 전에 있는 원소들을 차례로 탐색한다.
			for (int searchIdx=1; searchIdx<seqIdx; searchIdx++) {
				// 오름차순을 만족한다면
				if (sequence[searchIdx]<sequence[seqIdx]) {
					lis[seqIdx] = Math.max(lis[searchIdx]+1, // searchIdx의 최적해에 내가 붙기
										lis[seqIdx]); // 나의 직전 최적해
				}
			}
		}
	}
	

	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		// 1. 테스트 케이스 수를 입력받고 그만큼 반복한다.
		st = new StringTokenizer(br.readLine().trim());
		int testCase = Integer.parseInt(st.nextToken());
		
		for (int tc=1; tc<=testCase; tc++) {
			sb.append("#"+tc+" ");
			// 2. 수열을 입력받는다.
			inputTestcase();
			// 3. DP: lis 배열을 채운다.
			makeLIS();
			// 4. lis 배열의 최대값을 출력한다.
			Arrays.sort(lis);
			sb.append(lis[seqLength]+"\n");
			
		}
		System.out.println(sb);

	}

}
