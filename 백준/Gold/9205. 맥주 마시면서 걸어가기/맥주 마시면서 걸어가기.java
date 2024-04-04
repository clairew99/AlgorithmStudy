import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BOJ
 * @author eunwoo.lee
 * 
 * 1. 입력
 * 	1-1. 테스트케이스의 수를 입력받고 반복한다.
 * 	1-2. 맥주를 파는 편의점의 개수를 입력받는다.
 * 	1-3. 상근이네 집, 편의점, 락페스티벌 좌표를 입력받는다.
 * 2. 입력받은 좌표들을 하나의  node로 그래프를 만들어 연결한다.
 * 	2-1. 두 좌표의 거리가 1000m(맥주 20개) 이하면 연결
 * 3. 그래프에 대해 플로이드워샬을 실행한다.
 * 	[출][경]과 [경][도]의 값이 모두 1이면 [출][도]=1
 * 4. 그래프의 [집][페스티벌] 이 연결되어 있으면 happy, 아니면 sad 출력
 * cf) 집에서 bfs 실행 후 페스티벌에 도달하면 happy, 아니면 sad를 출력해도 된다
 *
 */

class Point {
	int x;
	int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	
	static final int INF = Integer.MAX_VALUE;
	
	static int storeNum; // 편의점 개수
	static Point[] points; // 집, 편의점 n개, 락페스티벌 좌표 담을 배열
	static int[][] adjArray; // 좌표로 만들  그래프
	
	
	public static void setInit() throws IOException{
		// 1-2. 맥주를 파는 편의점의 개수를 입력받는다.
		st = new StringTokenizer(br.readLine().trim());
		storeNum = Integer.parseInt(st.nextToken());
		
		// 1-3. 상근이네 집, 편의점, 락페스티벌 좌표를 입력받는다.
		points = new Point[storeNum+2];
		for (int inputIdx=0; inputIdx<storeNum+2; inputIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			points[inputIdx] = new Point(x, y);
		}
	}
	
	public static void makeAdjArray() {
		adjArray = new int[storeNum+2][storeNum+2];
		
		for (int start=0; start<storeNum+1; start++) {
			for (int end=start+1; end<storeNum+2; end++) {
				// 맨하탄 거리
				int distance = Math.abs(points[start].x-points[end].x)
						+Math.abs(points[start].y-points[end].y);
				// 2-1. 두 좌표의 거리가 1000m(맥주 20개) 이하면 연결
				if (distance<=1000) {
					adjArray[start][end] = adjArray[end][start] = 1;
				}
			}
		}
	}
	
	public static void floydWarshall() {
		for (int midIdx=0; midIdx<storeNum+2; midIdx++) {
			for (int startIdx=0; startIdx<storeNum+2; startIdx++) {
				
				if (startIdx==midIdx || adjArray[startIdx][midIdx]==0) {
					continue;
				}
				
				for (int endIdx=0; endIdx<storeNum+2; endIdx++) {
					
					if (endIdx==startIdx || endIdx==midIdx || adjArray[midIdx][endIdx]==0) {
						continue;
					}
					
					if (adjArray[startIdx][midIdx]==1 && adjArray[midIdx][endIdx]==1) {
						adjArray[startIdx][endIdx] = 1;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		// 1. 입력
		// 1-1. 테스트케이스의 수를 입력받고 반복한다.
		st = new StringTokenizer(br.readLine().trim());
		int testCase = Integer.parseInt(st.nextToken());
		
		for (int tc=1; tc<=testCase; tc++) {
			// 1. 입력
			setInit();
			
			// 2. 입력받은 좌표들을 하나의  node로 그래프를 만들어 연결한다.
			makeAdjArray();
			
			// 3. 그래프에 대해 플로이드워샬을 실행한다.
			floydWarshall();
			
			// 4. 그래프의 [집][페스티벌] 이 연결되어 있으면 happy, 아니면 sad 출력
			if (adjArray[0][storeNum+1]!=0) {
				sb.append("happy\n");
			}else {
				sb.append("sad\n");
			}
		}
		System.out.println(sb);

	}

}
