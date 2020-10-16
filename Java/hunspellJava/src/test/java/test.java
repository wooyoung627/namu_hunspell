import dk.dren.hunspell.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.lang.*;

public class test {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // hunspell을 사용하기 위해 hunspell 한국어 사전을 등록
        Hunspell.Dictionary dic = Hunspell.getInstance().getDictionary("/usr/share/myspell/dicts/ko_KR");
        String str, change, swt, strTxt, strChange;
        int flag=1;
        Scanner sc = new Scanner(System.in);
        System.out.println("맞춤법 검사 프로그램입니다.");

        while(true){
            System.out.println("검사할 문장을 입력해주세요(exit 입력시 종료): ");
            str = sc.nextLine();

            // exit 입력시 while문 종료
            if(str.equals("exit"))
                break;

            flag=1;
            // 공백을 기준으로 문장을 단어로 쪼갬
            String[] strList = str.split(" ");

            // 각 단어들을 for문 돌면서 hunspell을 이용해 맞춤법 검사
            for(int i=0; i<strList.length; i++){
                strTxt = strList[i].replaceAll("[\\p{S}\\p{P}º]+", "");

                // 각 단어에서 특수기호를 빼고 검사 , 오류가 있다면
                if(dic.misspelled(strTxt)){
                    System.out.println(strList[i]+" 대신 아래 단어를 사용해보세요!");
                    // 유사한 단어로 추천
                    System.out.println(dic.suggest(strTxt));
                    System.out.println();
                    // 1을 입력하면 단어 변경, else 그대로 유지
                    System.out.println("1. " + strList[i] +" 단어를 변경합니다.");
                    System.out.println("else. " + strList[i] +" 단어를 그대로 유지합니다.");

                    swt = sc.nextLine();
                    System.out.println();

                    if(swt.equals("1")){
                        flag = 0;
                        System.out.println("변경할 단어를 입력해주세요.");
                        change = sc.nextLine();
                        // 입력한 단어로 기존의 단어를 교체
                        strList[i] = change;
                    }
                }
            }
            strChange = String.join(" ", strList);
            // 변경한 단어가 없으면 출력
            if(flag==1)
                System.out.println("문장이 그대로 유지됩니다.");
            else{
                System.out.println("문장이 변경되었습니다.");
                System.out.println("==> " + strChange);
            }
            System.out.println();
        }
        System.out.println("프로그램이 종료되었습니다.");
    }
}