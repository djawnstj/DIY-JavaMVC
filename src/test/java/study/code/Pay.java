package study.code;

public class Pay {

    private long id;
    private long balance;

    public Pay(long id, long balance) {
        this.id = id;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void add(long money) {
        this.balance += money;
    }

    public void afterBuying(long money){
        this.balance -= money;
    }
}
