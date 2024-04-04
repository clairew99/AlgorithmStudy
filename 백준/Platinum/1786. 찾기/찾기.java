import java.util.*;
import java.io.*;

/**
 * BOJ
 * @author eunwoo.lee
 * 
 * 1. 본문 문자열과 패턴 문자열 을 입력받는다.
 *
 */


public class Main {
	
	static BufferedReader br;
	
	static String text, pattern;
	static int[] pi; // 주어진 패턴에 대한 부분일치 테이블
	static List<Integer> indices; // 일치하는 인덱스 저장
	
	/**
	 * makePi : 부분일치 테이블 만드는 메소드
	 * pi[i] : 0~인덱스 i까지의 부분 문자열에서 접두사=접미사 인 문자열의 최대 길이
	 * 
	 * 1. 패턴은 하나는 위치를 고정한 본문패턴, 하나는 위치를 변경할 비교패턴으로 취급한다.
	 * 2. 본문패턴의 인덱스(mainIdx)를 하나씩 증가시키며 비교패턴과 비교한다.
	 * 	2-1. 만약 본문패턴과 비교패턴이 다르다면
	 * 		2-1-1. 비교패턴[0]부터 다르다 -> 접두사와 접미사 일치 길이 0, 다음 인덱스 탐색
	 * 			pi[본문인덱스]=0, 본문인덱스++
	 * 		2-1-2. 비교패턴의 중간부터 다르다 -> pi[비교인덱스-1]까지는 동일하다. 비교패턴 shift
	 *			비교인덱스 = pi[비교인덱스-1]
	 * 	2-2. 만약 본문패턴과 비교패턴이 일치한다면 -> 비교인덱스의 길이만큼 일치한다. 다음 인덱스도 비교
	 * 		pi[본문인덱스] = 비교인덱스, 본문인덱스++. 비교인덱스++
	 * 		
	 * 		
	 */
	
	public static void makePi() {
		
		int patternLength = pattern.length();
		pi = new int[patternLength];
		
		// 2. 본문패턴의 인덱스(mainIdx)를 하나씩 증가시키며 비교패턴과 비교한다.
		// 자기 자신이 접두사이면서 접미사일때는 0으로 취급해야 하므로  mainIdx는 1부터 시작
		int mainIdx = 1, compareIdx=0;
		while (mainIdx<patternLength) {
			// 2-1. 만약 본문패턴과 비교패턴이 다르다면
			if (pattern.charAt(mainIdx)!=pattern.charAt(compareIdx)) {
				// 2-1-1. 비교패턴[0]부터 다르다
				if (compareIdx==0) {
					pi[mainIdx] = compareIdx; // 접두사접미사 일치 길이 0
					mainIdx++; // 비교패턴 shift
				}
				// 2-1-2. 비교패턴의 중간부터 다르다
				else {
					// pi[compareIdx-1]까지는 일치. 비교패턴 shift
					compareIdx = pi[compareIdx-1];
				}
			}
			// 2-2. 만약 본문패턴과 비교패턴이 일치한다면
			else {
				compareIdx++; // 0부터 길이니까 1 더해주기
				pi[mainIdx] = compareIdx;
				mainIdx++;
			}
		}
		
//		for (int p: pi) {
//			System.out.print(p+" ");
//		}
	}
	
	/**
	 * kmp() : kmp 알고리즘 실행 메소드
	 * 1. text를 차레로 탐색한다.
	 * 2-1. if text[textIdx]!=pattern[patternIdx]
	 * 	2-1-1. if patternIdx==0: 처음부터 일치X -> 패턴 한칸 shift
	 * 		-> textIdx++ 
	 * 	2-1-2. else : 중간부터 일치X -> patternIdx-1까지는 일치
	 * 		-> patternIdx=pi[patternIdx-1]
	 * 2-2. else: text[textIdx]==pattern[patternIdx] -> 다음 인덱스도 일치하는지 확인
	 * 		-> textIdx++, patternIdx++
	 * 	2-2-1. 만약 패턴의 끝까지 일치한다면: 동일한 문자열이다
	 * 		-> 일치하는 인덱스 (textIdx-patternIdx+1) 리스트에 추가. 패턴 shift
	 */
	
	public static void kmp() {
		int textIdx=0, patternIdx=0;
		while (textIdx<text.length()) {
			// 2-1. if text[textIdx]!=pattern[patternIdx]
			if (text.charAt(textIdx)!=pattern.charAt(patternIdx)) {
				// 2-1-1. 패턴의 처음부터 일치X -> 패턴 한칸 shift
				if (patternIdx==0) {
					textIdx++;
				}
				// 2-1-2. 패턴 중간부터 일치X
				else {
					// patternIdx-1까지는 일치
					patternIdx = pi[patternIdx-1];
				}
			}
			// 2-2. 현재 인덱스의 값이 일치하면 다음 인덱스도 일치하는지 확인
			else {
				textIdx++;
				// 2-2-1. 만약 패턴의 끝까지 일치한다면: 동일한 문자열
				if (patternIdx==pattern.length()-1) {
					indices.add(textIdx-pattern.length()+1); // 문제에서 문자열 인덱스 1부터 시작
					patternIdx = pi[patternIdx];
				}
				else {
					patternIdx++;					
				}
			}
		}
	}
	
	

	public static void main(String[] args) throws Exception {
		
		br = new BufferedReader(new InputStreamReader(System.in));
		indices = new ArrayList<>();
		
		text = br.readLine();
		pattern = br.readLine();
		
		// pi배열을 만든다 : 부분 일치 테이블
		makePi();
		
		// KMP 실행 : 본문 문자열과 패턴 문자열 비교
		kmp();
		
		// 일치하는 문자열 출력
		System.out.println(indices.size());
		for (int i: indices) {
			System.out.print(i+" ");
		}

	}

}
