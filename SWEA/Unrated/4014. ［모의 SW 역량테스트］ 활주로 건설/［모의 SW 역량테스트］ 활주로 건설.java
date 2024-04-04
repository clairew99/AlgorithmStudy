import java.util.*;
import java.io.*;

/**
 * SWEA
 * @author eunwoo.lee
 * 
 * 1. 입력
 * 	1-1. 테스트케이스의 수를 입력받고 반복한다.
 * 	1-2. 지도의 한변의 길이와 경사로의 길이를 입력받는다.
 * 	1-3. 지도의 지형 정보를 입력받는다.
 * 2. 지도를 가로,세로 한줄씩 검사후 조건을 만족하면 활주로 개수를 증가시킨다.
 * 	2-1. 입력받은 줄을 한칸씩 검사한다.
 * 	2-2. 이전 칸과 높이 차이가 2이상 난다 -> return
 * 	2-3. 이전 칸보다 다음칸이 1 높아졌다 -> 오르막 경사로 설치 검사
 * 		만약 경사로 설치가 불가하면  return
 * 		가능하면 경사로 설치 (isUsed true로 설정)
 * 	2-4. 이전칸보다 다음칸이 1 낮아졌다 -> 내리막 경사로 설치 검사
 *	 	만약 경사로 설치가 불가하면  return
 * 		가능하면 경사로 설치 (isUsed true로 설정)
 * 	2-5. 함수의 끝까지 도달했다면 runwayCnt++
 * 3. 활주로의 개수를 출력한다.
 * 
 * <오르막 경사로 설치 검사 메소드> - mapRow, startIdx
 *  startIdx ~(startIdx-rampSize+1) 까지 검사한다.
 * 	1. 만약 범위 밖이다-> return false
 *	2. 이미 경사로가 설치되었거나 높이가 달라지면 -> return false
 * 
 * 
 * <내리막 경사로 설치 검사 메소드> - mapRow, startIdx
 * startIdx ~(startIdx+rampSize-1) 까지 검사한다.
 * 	1. 만약 범위 밖이다-> return false
 *	2. 이미 경사로가 설치되었거나 높이가 달라지면 -> return false
 *
 */

public class Solution {
	
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static int mapSize, rampSize, runwayCnt; // 지도의 한변의 길이, 경사로의 길이, 활주로 길이
	static int[][] map; // 지도
	static boolean[] isUsed; // 경사로 설치 여부 저장 배열
	
	
	public static void setInit() throws IOException{
		// 1-0. 전역변수 초기화
		runwayCnt = 0;
		
		// 1-2. 지도의 한변의 길이와 경사로의 길이를 입력받는다.
		st = new StringTokenizer(br.readLine().trim());
		mapSize = Integer.parseInt(st.nextToken());
		rampSize = Integer.parseInt(st.nextToken());
		// 1-3. 지도의 지형 정보를 입력받는다.
		map = new int[mapSize][mapSize];
		for (int rowIdx=0; rowIdx<mapSize; rowIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int colIdx=0; colIdx<mapSize; colIdx++) {
				map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	public static void checkRow(int[] mapRow) {
		
		isUsed = new boolean[mapSize];
		
		// 2-1. 입력받은 줄을 한칸씩 검사한다.
		for (int colIdx=0; colIdx<mapSize-1; colIdx++) {
			// 2-2. 이전 칸과 높이 차이가 2이상 난다 -> return
			if (Math.abs(mapRow[colIdx]-mapRow[colIdx+1])>1)
				return;
			// 2-3. 이전 칸보다 다음칸이 1칸 높아졌다.-> 오르막 경사로 설치 검사
			if (mapRow[colIdx]-mapRow[colIdx+1]==-1) {
				// 경사로 설치가 불가하면  return
				if (!upAvailable(mapRow, colIdx)) return;
				// 오르막 설치 가능하면 설치해준다
				for (int mapRowIdx=colIdx; mapRowIdx>colIdx-rampSize; mapRowIdx--) {
					isUsed[mapRowIdx] = true;
				}
			}
			// 2-4. 이전칸보다 다음칸이 1 낮아졌다 -> 내리막 경사로 설치 검사
			if (mapRow[colIdx]-mapRow[colIdx+1]==1) {
				// 경사로 설치가 불가하면  return
				if (!downAvailable(mapRow, colIdx+1)) return;
				// 내리막 설치 가능하면 설치해준다
				for (int mapRowIdx=colIdx+1; mapRowIdx<colIdx+1+rampSize; mapRowIdx++) {
					isUsed[mapRowIdx] = true;
				}
			}
		}
		runwayCnt++;
	}
	
	public static boolean upAvailable(int[] mapRow, int startIdx) {
		// startIdx ~(startIdx-rampSize+1)까지 탐색
		int currentHeight = mapRow[startIdx]; // 현재 높이 저장
		for (int mapRowIdx=startIdx; mapRowIdx>startIdx-rampSize; mapRowIdx--) {
			// 1. 경사로가 맵 밖을 벗어나면
			if (mapRowIdx<0) return false;
			// 2. 이미 경사로가 설치되었거나 높이가 달라지면
			if (isUsed[mapRowIdx] || mapRow[mapRowIdx]!=currentHeight) return false;
		}		
		return true;
	}
	
	public static boolean downAvailable(int[] mapRow, int startIdx) {
		// startIdx~(startIdx+rampSize-1) 까지 탐색
		int currentHeight = mapRow[startIdx]; // 현재 높이 저장
		for (int mapRowIdx=startIdx; mapRowIdx<startIdx+rampSize; mapRowIdx++) {
			// 1. 경사로가 맵 밖을 벗어나면
			if (mapRowIdx>=mapSize) return false;
			// 2. 이미 경사로가 설치되었거나 높이가 달라지면
			if (isUsed[mapRowIdx] || mapRow[mapRowIdx]!=currentHeight) return false;
		}		
		return true;
	}

	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		// 1-1. 테스트케이스의 수를 입력받고 반복한다.
		st = new StringTokenizer(br.readLine().trim());
		int testCase = Integer.parseInt(st.nextToken());
		
		for (int tc=1; tc<=testCase; tc++) {
			// 1. 입력
			setInit();
			// 2. 지도를 한줄씩 검사후 조건을 만족하면 활주로 개수를 증가시킨다.
			for (int mapIdx=0; mapIdx<mapSize; mapIdx++) {
				// 가로 검사
				checkRow(map[mapIdx]);
				// 세로 검사
				// 똑같은 메소드를 사용하기 위해 col을 row로 transpose
				int[] colArray = new int[mapSize];
				for (int rowIdx=0; rowIdx<mapSize; rowIdx++) {
					colArray[rowIdx] = map[rowIdx][mapIdx];
				}
				checkRow(colArray);
			}
			// 3. 활주로의 개수를 출력한다.
			sb.append("#"+tc+" "+runwayCnt+"\n");
		}
		System.out.println(sb);
	}

}
