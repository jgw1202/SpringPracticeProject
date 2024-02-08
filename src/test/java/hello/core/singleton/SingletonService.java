package hello.core.singleton;

public class SingletonService {

    // static 영역에 객체를 딱 1개만 생성
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        // public으로 열어서 객체가 필요하면 이 static 메서드를 통해서만 조회
        return instance;
    }

    private SingletonService() {
        // 생성자를 private으로 선언하여 외부에서 new 키워드를 사용한 객체 생성을 못하게 막음
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
