import java.util.*;
import java.io.*;
/**
 * SWEA
 * @author eunwoo.lee
 * 
 * 1. 테스트케이스의 수를 입력받고 반복한다.
 * 	1-1. 점의 좌표를 입력받은 후 원점으로부터의 맨하탄 거리를 배열에 저장한다.
 * 	(시간을 줄이기 위해 맨하탄거리가 모두 짝수이거나 홀수가 아니면 -1 리턴해도 될 것 같다)
 * 2. 점을 이동시킨다 
 * 	2-1. 매 시간마다 모든 점의 맨하탄거리를 탐색한다.
 * 		if) 모든 거리가 1이하 이면
 * 		2-1-1. 모든 거리가 0이면 -> 그때 시각 return
 * 		2-1-2. 거리에 0과 1이 섞여있으면 -> 한점에 모을 수 없음 -> return -1 		
 *  2-2. 점을 이동시킨다.(맨하탄거리를 줄인다)
 *  	 모든 거리를 탐색하며 조건에 맞게 이동시킨다.
 *  	2-2-1. if (거리-time)>=0 라면 그대로 진행
 *  	2-2-2. else
 *  			abs(거리-time)이 짝수면 0으로 저장
 *  			abs(거리-time)이 홀수면 1로 저장
 *  2-3. time을 증가시킨다
 * 	3. 답을 출력한다.
 */


public class Solution {
	
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	
	static int pointNum; // 점의 개수
	static int[] manDist; // 각 점의 원점으로부터 맨하탄거리 저장
	
	public static void setInit() throws IOException{
		// 점의 개수를 입력받는다.
		st = new StringTokenizer(br.readLine().trim());
		pointNum = Integer.parseInt(st.nextToken());
		manDist = new int[pointNum];
		
		for (int pointIdx=0; pointIdx<pointNum; pointIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			// 1-1. 각 점의 원점으로 부터의 맨하탄 거리 저장
			manDist[pointIdx] = Math.abs(x) + Math.abs(y);
		}
		
	}
	
	public static void movePoints() {
		
		// 2-0. 모두 홀수이거나 모두 짝수가 아니면 return -1;
		int oddNum=0, evenNum=0;
		for (int pointIdx=0; pointIdx<pointNum; pointIdx++) {
			if (manDist[pointIdx]%2==0) { // 거리가 0인 것의 개수
				evenNum++;
			}else { // 거리가 1인 것의 개수
				oddNum++;
			}
		}
		if (oddNum!=pointNum && evenNum!=pointNum) {
			sb.append("-1\n");
			return;
		}
		
		int time=0;
		
		while (true) {
			// 2-1. 매 시간마다 모든 점의 맨하탄거리를 탐색한다.
			int zeroNum=0, oneNum=0; // 거리 0과 1의 개수
			for (int pointIdx=0; pointIdx<pointNum; pointIdx++) {
				if (manDist[pointIdx]==0) { // 거리가 0인 것의 개수
					zeroNum++;
				}else if (manDist[pointIdx]==1) { // 거리가 1인 것의 개수
					oneNum++;
				}else if (manDist[pointIdx]>1) { // 1보다 거리가 남았으면 계산하지 않고 점 이동 진행
					break;
				}
			}
			
			// 2-1-1. 모든 거리가 0이면 -> 그때 시각 return
			if (zeroNum==pointNum) {
				if (time>0) {
					time-=1;
				}
				sb.append(time+"\n");
				return;
			}
			// 2-1-2. 거리에 0과 1이 섞여있으면 -> 원점에 모을 수 없음 -> return -1 	
			if ((zeroNum+oneNum)==pointNum && zeroNum>0) {
				sb.append("-1\n");
				return;
			}
			// 2-2. 점을 이동시킨다 (맨하탄거리를 줄인다)
			// 모든 거리를 탐색하며 조건에 맞게 이동시킨다.
			for (int pointIdx=0; pointIdx<pointNum; pointIdx++) {
				// 2-2-1. if (거리-time)>=0 라면 그대로 진행
				if ((manDist[pointIdx]-time)>=0) {
					manDist[pointIdx] = manDist[pointIdx]-time;
				}				
				// 2-2-2. else: (거리-time)<0 
				else {
					// abs(거리-time)이 짝수면 0으로 저장
					if (Math.abs(manDist[pointIdx]-time)%2==0) {
						manDist[pointIdx] = 0;
					}
					// abs(거리-time)이 홀수면 1로 저장
					else {
						manDist[pointIdx] = 1;
					}
				}
			}			
			
			// 2-3. time을 증가시킨다
			time++;
		}
	}

	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		// 1. 테스트케이스의 수를 입력받고 반복한다.
		st = new StringTokenizer(br.readLine().trim());
		int testCase = Integer.parseInt(st.nextToken());
		
		for (int tc=1; tc<=testCase; tc++) {
			sb.append("#"+tc+" ");
			// 입력
			setInit();
			
			// 이동
			movePoints();
			
		}
		System.out.println(sb);
	}

}
