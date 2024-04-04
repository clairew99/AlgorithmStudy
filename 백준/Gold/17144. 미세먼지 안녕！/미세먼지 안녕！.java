import java.util.*;
import java.io.*;

/**
 * BOJ
 * @author eunwoo.lee
 * 
 * 1. 입력
 * 	1-1. mapRowSize, mapColSize, totalTime 을 입력받는다.
 * 	1-2. map의 상태를 입력받는다.
 * 2. t초간 상황이 진행된다.
 * 	2-1. 미세먼지가 확산된다.
 * 		2-1-1. 모든 미세먼지의 좌표와 먼지량을 큐에 넣어 저장한다.
 * 			순차적이 아니라 동시에 확산이므로 큐에 넣어 먼지량을 저장 후 확산해야 한다.
 * 		2-1-2. 하나씩 꺼내어 먼지를 확산시킨다.
 * 	2-2. 공기청정기가 작동한다.
 * 		2-2-1. 윗부분을 작동시킨다.
 * 			문제에서 안내된 순서를 1,2,3,4라고 하면 4->3->2->1 순으로 진행한다.
 * 		2-2-2. 아래부분을 작동시킨다.
 * 			문제에서 안내된 순서를 1,2,3,4라고 하면 4->3->2->1 순으로 진행한다.
 * 3. 방에 남아있는 미세먼지의 양을 출력한다.
 */

public class Main {
	
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static int mapRowSize, mapColSize, totalTime;
	static int[][] map;
	static Queue<Point> dustQ = new ArrayDeque<>();
	static int cleanerRow; // 공기청정기 밑부분 저장
	
	// delta 배열
	static final int[] dr = {-1, 1, 0, 0};
	static final int[] dc = {0, 0, -1, 1};
	
	static class Point {
		int rowIdx, colIdx, dustNum;

		public Point(int rowIdx, int colIdx, int dustNum) {
			super();
			this.rowIdx = rowIdx;
			this.colIdx = colIdx;
			this.dustNum = dustNum;
		}		
	}
	
	public static void setInit() throws IOException {
		// 1-1. mapRowSize, mapColSize, totalTime 을 입력받는다.
		st = new StringTokenizer(br.readLine().trim());
		mapRowSize = Integer.parseInt(st.nextToken());
		mapColSize = Integer.parseInt(st.nextToken());
		totalTime = Integer.parseInt(st.nextToken());
		
		// 1-2. map의 상태를 입력받는다.
		map = new int[mapRowSize][mapColSize];
		for (int rowIdx=0; rowIdx<mapRowSize; rowIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int colIdx=0; colIdx<mapColSize; colIdx++) {
				map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
				if (map[rowIdx][colIdx]==-1) {
					cleanerRow = rowIdx;
				}
			}
		}
		
	}
	// 미세먼지를 확산시키는 메소드
	public static void spread() {
		// 맵의 모든 먼지가 있는 곳을 큐에 저장한다.
		for (int rowIdx=0; rowIdx<mapRowSize; rowIdx++) {
			for (int colIdx=0; colIdx<mapColSize; colIdx++) {
				if (map[rowIdx][colIdx]>0) {
					dustQ.offer(new Point(rowIdx, colIdx, map[rowIdx][colIdx]));
				}
			}
		}
		
		while (!dustQ.isEmpty()) {
			Point current = dustQ.poll();
			
			for (int d=0; d<4; d++) { // 4방향 탐색
				int nextRowIdx = current.rowIdx+dr[d];
				int nextColIdx = current.colIdx+dc[d];
				
				// 범위를 벗어난다면 pass
				if (nextRowIdx<0 || nextRowIdx>=mapRowSize || nextColIdx<0 || nextColIdx>=mapColSize)
					continue;
				// 공기청정기로는 먼지가 퍼지지 않는다.
				if (map[nextRowIdx][nextColIdx]==-1)
					continue;
				// 먼지확산
				map[nextRowIdx][nextColIdx] += current.dustNum/5;
				map[current.rowIdx][current.colIdx] -= current.dustNum/5;
			}
			
			
		}
	}
	
	public static void cleanAir() {

		// 1. 윗부분 공기 청소
		// 1-4. 위->아래
		for (int rowIdx=cleanerRow-2; rowIdx>0; rowIdx--) {
			map[rowIdx][0] = map[rowIdx-1][0];
		}
		// 1-3. 오른쪽->왼쪽
		for (int colIdx=0; colIdx<mapColSize-1; colIdx++) {
			map[0][colIdx] = map[0][colIdx+1];
		}
		// 1-2. 아래->위
		for (int rowIdx=0; rowIdx<cleanerRow-1; rowIdx++) {
			map[rowIdx][mapColSize-1] = map[rowIdx+1][mapColSize-1];
		}
		// 1-1. 왼쪽->오른쪽
		for (int colIdx=mapColSize-1; colIdx>1; colIdx--) {
			map[cleanerRow-1][colIdx] = map[cleanerRow-1][colIdx-1];
		}
		map[cleanerRow-1][1] = 0;
		
		// 2. 아래부분 공기 청소
		// 2-4. 아래->위
		for (int rowIdx=cleanerRow+1; rowIdx<mapRowSize-1; rowIdx++) {
			map[rowIdx][0] = map[rowIdx+1][0];
		}
		// 2-3. 오른쪽->왼쪽
		for (int colIdx=0; colIdx<mapColSize-1; colIdx++) {
			map[mapRowSize-1][colIdx] = map[mapRowSize-1][colIdx+1];
		}
		// 2-2. 위->아래
		for (int rowIdx=mapRowSize-1; rowIdx>cleanerRow; rowIdx--) {
			map[rowIdx][mapColSize-1] = map[rowIdx-1][mapColSize-1];
		}
		// 2-1. 왼쪽->오른쪽
		for (int colIdx=mapColSize-1; colIdx>1; colIdx--) {
			map[cleanerRow][colIdx] = map[cleanerRow][colIdx-1];
		}
		map[cleanerRow][1] = 0;
	}

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		// 1. 입력
		setInit();
		// 2. t초간 상황이 진행된다.
		int time = 0;		
		while (time<totalTime) {
			// 2-1. 미세먼지가 확산된다.
			spread();
			// 2-2. 공기청정기가 작동한다.
			cleanAir();
			
			time++;
		}
		// 3. 방에 남아있는 미세먼지의 양을 출력한다.
		int dustSum=0;
		for (int rowIdx=0; rowIdx<mapRowSize; rowIdx++) {
			for (int colIdx=0; colIdx<mapColSize; colIdx++) {
				if (map[rowIdx][colIdx]>0) dustSum += map[rowIdx][colIdx];
			}
		}
		System.out.println(dustSum);
	}

}
