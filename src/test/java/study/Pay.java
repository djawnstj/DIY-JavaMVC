package study;

class Pay {
    private long id;
    private long balance;

    Pay(long id, long balance) {
        this.id = id;
        this.balance = balance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getId() {
        return id;
    }
    
    public long getBalance() {
        return balance;
    }
}