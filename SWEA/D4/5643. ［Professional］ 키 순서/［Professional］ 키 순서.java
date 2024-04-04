import java.util.*;
import java.io.*;

/**
 * SWEA5643
 * @author eunwoo.lee
 * 
 * 1. 입력
 * 	1-1. 전체 테스트 케이스 수를 입력받고 반복한다.
 * 	1-2. 학생들의 수(vertex), 키를 비교한 횟수(edge)를 입력받는다.
 * 	1-3. 키를 비교한 횟수만큼 그 결과를 입력받아 adj[][]에 저장한다.
 * 		: start end : adj[start][end]=1 -> start가 end 보다 키가 작다
 * 2. 모든 정점에 대해 탐색한다.
 * 	2-1. 나보다 키가 큰 사람이 몇 명인지 구한다. (진출차수에 대해 dfs)
 * 	2-2. 나보다 키가 작은 사람이 몇명인지 구한다. (진입차수에 대해 dfs)
 * 	2-3. (2-1)+(2-2)==(학생수-1) 이면 나의 순서를 알고 있으므로 knowCnt++
 * 3. knowCnt를 출력한다.
 */


public class Solution {
	
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static int vertexNum, edgeNum, cnt, knowCnt; // 학생들의 수, 키 비교횟수, 각 정점 앞/뒤 사람 수, 알고있는 학생수
	static int[][] adj; // 인접배열
	
	public static void setInit() throws IOException{
		knowCnt=0;
		
		// 1-2. 학생들의 수, 키를 비교한 횟수를 입력받는다.
		st = new StringTokenizer(br.readLine().trim());
		vertexNum = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine().trim());
		edgeNum = Integer.parseInt(st.nextToken());
		
		// 1-3. 키를 비교한 횟수만큼 그 결과를 입력받아 adj[][]에 저장한다.
		adj = new int[vertexNum+1][vertexNum+1];
		for (int edgeIdx=0; edgeIdx<edgeNum; edgeIdx++) {
			st = new StringTokenizer(br.readLine().trim());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			adj[start][end] = 1; // start가 end 보다 키가 작다
			
		}
	}
	
	public static void greaterDFS(int vertexIdx, boolean[] isVisited) {
		// 현재 노드 방문처리
		isVisited[vertexIdx] = true;
		// 현재 노드에서 나가는 모든 노드에 대해 탐색
		for (int endIdx=1; endIdx<=vertexNum; endIdx++) {
			if (adj[vertexIdx][endIdx]==1 && !isVisited[endIdx]) {
				cnt++;
				isVisited[endIdx] = true;
				greaterDFS(endIdx, isVisited);
			}
		}
	}
	
	public static void lessDFS(int vertexIdx, boolean[] isVisited) {
		// 현재 노드 방문처리
		isVisited[vertexIdx] = true;
		// 현재 노드에서 나가는 모든 노드에 대해 탐색
		for (int startIdx=1; startIdx<=vertexNum; startIdx++) {
			if (adj[startIdx][vertexIdx]==1 && !isVisited[startIdx]) {
				cnt++;
				isVisited[startIdx] = true;
				lessDFS(startIdx, isVisited);
			}
		}
	}

	public static void main(String[] args) throws Exception{

		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		// 1-1. 전체 테스트 케이스 수를 입력받고 반복한다.
		st = new StringTokenizer(br.readLine().trim());
		int testCase = Integer.parseInt(st.nextToken());
		for (int tc=1; tc<=testCase; tc++) {
			// 1. 입력
			setInit();
			// 2. 모든 정점에 대해 탐색한다.
			for (int vertexIdx=1; vertexIdx<=vertexNum; vertexIdx++) {
				cnt=0;
				// 2-1. 나보다 키가 큰 사람이 몇 명인지 구한다.
				greaterDFS(vertexIdx, new boolean[vertexNum+1]);
				// 2-2. 나보다 키가 작은 사람이 몇명인지 구한다.
				lessDFS(vertexIdx, new boolean[vertexNum+1]);
				// 2-3. (2-1)+(2-2)==(학생수-1) 이면 나의 순서를 알고 있으므로 knowCnt++
				if (cnt==vertexNum-1) knowCnt++;
			}
			// 3. knowCnt를 출력한다.
			sb.append("#"+tc+" "+knowCnt+"\n");
		}
		System.out.println(sb);
	}

}
