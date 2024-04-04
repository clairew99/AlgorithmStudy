import java.util.*;
import java.io.*;

/**
 * SWEA
 * @author eunwoo.lee
 * 
 * 1. 입력
 * 	1-1. 자석을 회전시키는 횟수를 입력받는다.
 * 	1-2. 1~4번 자석의 8개 날의 자성정보를 입력받는다
 * 2. 자석을 회전시키는 횟수만큼 회전 정보를 입력받는다. (자석의 번호, 회전방향)
 * 3. 자석을 회전시킨다.
 * 	3-1. 각 맞닿은 부분을 비교해 대상 자석의 회전 방향에 따른 다른 자석의 회전 방향을 배열에 저장한다.
 * 		이 때 대상 자석 기준 한칸씩 왼쪽으로 탐색, 한칸씩 오른쪽으로 탐색을 진행한다.
 * 	3-2. 배열에 저장된 탐색 방향에 따라 회전을 진행한다.
 * 4. 회전이 끝나면 빨간 화살표가 가리키는 극에 따라 점수를 계산한다.
 */


public class Solution {
	
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static int rotateNum, totalScore; // 회전횟수, 총 점수
	static int[][] magnet; // 자성정보
	static final int[][] SCORE = {{0,1}, {0,2}, {0,4}, {0,8}};
	static int[] rotateDirection; // 회전 방향 저장할 배열
	
	static final int N = 0;
	static final int S = 1;
	static final int CLOCKWISE = 1;
	static final int ANTICLOCKWISE = -1;
	
	public static void setInit() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		rotateNum = Integer.parseInt(st.nextToken());
		
		// 자성정보 저장 (0-빨간 화살표, 2-n+1과 닿아있는 부분, 6-n-1과 닿아있는 부분)
		magnet = new int[4][8];
		for (int magnetIdx=0; magnetIdx<4; magnetIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int idx=0; idx<8; idx++) {
				magnet[magnetIdx][idx] = Integer.parseInt(st.nextToken());
			}		
		}		
	}
	
	public static void rotate(int targetMagnet, int rotateWise) {
		// 1. rotateDirection 배열을 만든다
		// rotateDirection : 회전 하려는 대상 자석에 대한 다른 자석의 회전 방향 저장
		rotateDirection = new int[4];
		// 1-1. 회전 대상 자석은 rotateWise 값 그대로
		rotateDirection[targetMagnet] = rotateWise;
		// 1-2. 대상 자석에서 한칸씩 왼쪽으로 탐색한다.
		for (int magnetIdx=targetMagnet; magnetIdx>0; magnetIdx--) {
			// 맞닿아있는 부분이 다르면 비교 대상과 다른 방향 저장
			if (magnet[magnetIdx][6]!=magnet[magnetIdx-1][2]) {
				rotateDirection[magnetIdx-1] = rotateDirection[magnetIdx]*(-1);
			}else { // 만약 같다면 더이상 뒤에 것들까지 회전하지 않으므로 break
				break;
			}
		}
		// 1-3. 대상 자석에서 한칸씩 오른쪽으로 탐색한다.
		for (int magnetIdx=targetMagnet; magnetIdx<3; magnetIdx++) {
			// 맞닿아있는 부분이 다르면 비교 대상과 다른 방향 저장
			if (magnet[magnetIdx][2]!=magnet[magnetIdx+1][6]) {
				rotateDirection[magnetIdx+1] = rotateDirection[magnetIdx]*(-1);
			}else { // 만약 같다면 더이상 뒤에 것들까지 회전하지 않으므로 break
				break;
			}
		}
		
		// 2. 배열의 값에 따라 회전한다.
		for (int idx=0; idx<4; idx++) {
			if (rotateDirection[idx]==CLOCKWISE) {
				rotateClock(idx);
			}else if (rotateDirection[idx]==ANTICLOCKWISE) {
				rotateAnticlock(idx);
			}
		}
	}
	
	// 톱니바퀴 시계방향으로 회전시키는 메소드
	public static void rotateClock(int magnetIdx) {
		int value = magnet[magnetIdx][7];
		for (int idx=7; idx>=1; idx--) {
			magnet[magnetIdx][idx] = magnet[magnetIdx][idx-1];
		}
		magnet[magnetIdx][0] = value;
	}
	// 톱니바퀴 반시계방향으로 회전시키는 메소드
	public static void rotateAnticlock (int magnetIdx) {
		int value = magnet[magnetIdx][0];
		for (int idx=0; idx<=6; idx++) {
			magnet[magnetIdx][idx] = magnet[magnetIdx][idx+1];
		}
		magnet[magnetIdx][7] = value;
	}
	
	public static int calScore() {
		// 빨간 화살표 위치의 값에 따라 점수 계산
		totalScore = 0;
		for (int magnetIdx=0; magnetIdx<4; magnetIdx++) {
			totalScore += SCORE[magnetIdx][magnet[magnetIdx][0]];		
		}	
		return totalScore;
	}

	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine().trim());
		int testCase = Integer.parseInt(st.nextToken());
		for (int tc=1; tc<=testCase; tc++) {		
			// 1. 입력
			setInit();			
			
			// 2. rotateNum 만큼 회전정보 입력받는다
			for (int rotateIdx=0; rotateIdx<rotateNum; rotateIdx++) {
				st = new StringTokenizer(br.readLine().trim());
				int magnetIdx = Integer.parseInt(st.nextToken());
				int rotateWise = Integer.parseInt(st.nextToken());
				
				// 3. 자석을 회전시킨다
				rotate(magnetIdx-1, rotateWise);
			}
			
			// 점수를 계산한다.
			sb.append("#"+tc+" "+calScore()+"\n");
		}
		
		System.out.println(sb);

	}

}
