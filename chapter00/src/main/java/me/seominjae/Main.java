package me.seominjae;

/*
    스프링 부트 3 프로젝트 만들기

        01 단계 - 현재까지 여러분들이 프로젝트를 만든 것은 스프링 부트 3 프로젝트가 아니라
             '그레이들 프로젝트'를 생성한 예시입니다.
             해당 그레이들 프로젝트를 스프링 부트 3 프로젝트로 만들 예정입니다

             그레이들 설정 파일로 이동 합니다 -> build.gradle
 */
public class Main {
    public static void main(String[] args) {
        //TIP 캐럿을 강조 표시된 텍스트에 놓고 <shortcut actionId="ShowIntentionActions"/>을(를) 누르면
        // IntelliJ IDEA이(가) 수정을 제안하는 것을 확인할 수 있습니다.
        System.out.print("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP <shortcut actionId="Debug"/>을(를) 눌러 코드 디버그를 시작하세요. 1개의 <icon src="AllIcons.Debugger.Db_set_breakpoint"/> 중단점을 설정해 드렸습니다
            // 언제든 <shortcut actionId="ToggleLineBreakpoint"/>을(를) 눌러 중단점을 더 추가할 수 있습니다.
            System.out.println("i = " + i);

        }
    }
}