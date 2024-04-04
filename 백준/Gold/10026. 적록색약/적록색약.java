import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BOJ
 * @author eunwoo.lee
 * 
 * 1. 테스트케이스를 입력받는다.
 * 2. 적록색약이 아닌 사람이 본 구역의 수를 구한다.
 * 	2-1. 배열의 모든 칸에 대해 탐색한다.
 * 	2-2. 만약 아직 방문하지 않았다면 dfs 실행
 * 		2-3. 현재 위치 방문처리
 * 		2-4. 다음 위치 계산
 * 		2-5. 다음 위치가 범위 밖이라면 continue
 * 		2-6. 다음 위치가 방문하지 않았고 탐색을 시작한 칸과 같은 색이면 dfs
 * 	2-7. dfs 끝날때마다 덩어리 세기
 * 	
 * 3. 적록색약인 사람이 본 구역의 수를 구한다.
 * 	3-1. 배열의 G를 R로 바꾼다.
 * 	3-2. dfs 이용해 덩어리세기
 * 
 * 4. 정답을 출력한다.
 *
 */

public class Main {
	
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static final int[] dr = {-1, 1, 0, 0};
	static final int[] dc = {0, 0, -1, 1};
	
	static boolean[][] isVisited;
	static int normalSectionCnt, abnormalSectionCnt;
	
	static int paintingSize;
	static char[][] painting;
	
	public static void inputTestCase() throws IOException{
		// 그림의 크기를 입력받는다.
		st = new StringTokenizer(br.readLine().trim());
		paintingSize = Integer.parseInt(st.nextToken());
		
		// 그림의 정보를 입력받는다.
		painting = new char[paintingSize][paintingSize];
		for (int pRowIdx=0; pRowIdx<paintingSize; pRowIdx++) {
			String line = br.readLine().trim();
			for (int pColIdx=0; pColIdx<paintingSize; pColIdx++) {
				painting[pRowIdx][pColIdx] = line.charAt(pColIdx);
			}
		}
	}
	
	public static void searchSection(int currentRowIdx, int currentColIdx) {
		// 2-3. 현재 위치 방문처리
		isVisited[currentRowIdx][currentColIdx] = true;
		char currentColor = painting[currentRowIdx][currentColIdx];
		
		// 2-4. 다음위치 구하기
		for (int d=0; d<4; d++) {
			int nextRowIdx = currentRowIdx + dr[d];
			int nextColIdx = currentColIdx + dc[d];
			
			// 2-5. 다음 위치가 범위 밖이라면 continue
			if (nextRowIdx<0 || nextRowIdx>=paintingSize || nextColIdx<0 || nextColIdx>=paintingSize)
				continue;
			
			// 2-6. 다음 위치가 방문하지 않았고 탐색을 시작한 칸과 같은 색이면 dfs
			if (!isVisited[nextRowIdx][nextColIdx] && painting[nextRowIdx][nextColIdx]==currentColor) {
				searchSection(nextRowIdx,nextColIdx);
			}
		}
	}

	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		// 1. 테스트케이스를 입력받는다.
		inputTestCase();
		
		// 2. 적록색약이 아닌 사람이 본 구역의 수를 구한다.
		// 2-1. 배열의 모든 칸에 대해 탐색을 시작한다.
		isVisited = new boolean[paintingSize][paintingSize];
		normalSectionCnt = 0;
		for (int paintingRowIdx=0; paintingRowIdx<paintingSize; paintingRowIdx++) {
			for (int paintingColIdx=0; paintingColIdx<paintingSize; paintingColIdx++) {
				// 2-2. 만약 아직 방문하지 않았다면 dfs 실행
				if (!isVisited[paintingRowIdx][paintingColIdx]) {
					searchSection(paintingRowIdx, paintingColIdx);
					normalSectionCnt++; // 2-7. dfs 끝날때마다 덩어리 세기
				}
			}
		}
		
		// 3. 적록색약인 사람이 본 구역의 수를 구한다.
		// 3-1. 배열의 G를 R로 바꾼다.
		for (int rowIdx=0; rowIdx<paintingSize; rowIdx++) {
			for (int colIdx=0; colIdx<paintingSize; colIdx++) {
				if (painting[rowIdx][colIdx]=='G')
					painting[rowIdx][colIdx]='R';
			}
		}
		// 3-2. dfs 이용해 덩어리세기
		isVisited = new boolean[paintingSize][paintingSize];
		abnormalSectionCnt = 0;
		for (int paintingRowIdx=0; paintingRowIdx<paintingSize; paintingRowIdx++) {
			for (int paintingColIdx=0; paintingColIdx<paintingSize; paintingColIdx++) {
				// 2-2. 만약 아직 방문하지 않았다면 dfs 실행
				if (!isVisited[paintingRowIdx][paintingColIdx]) {
					searchSection(paintingRowIdx, paintingColIdx);
					abnormalSectionCnt++; // 2-7. dfs 끝날때마다 덩어리 세기
				}
			}
		}
		
		// 4. 정답을 출력한다.
		sb.append(normalSectionCnt+" "+abnormalSectionCnt);
		System.out.println(sb);

	}

}
