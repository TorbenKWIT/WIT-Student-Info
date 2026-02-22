package transit;

public class Stop {
    String id;
    String name;

    Stop(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }
}
