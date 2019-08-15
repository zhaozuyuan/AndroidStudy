package com.example.sjms.wrapper;

/**
 * 很贵的穿着装饰
 */
public class ExpensiveCloth extends PersonCloth {
    public ExpensiveCloth(Person person) {
        super(person);
    }

    @Override
    public void dressed() {
        super.dressed();
        System.out.println("穿一件2万的外套、一双5千的鞋子");
    }

    /**
     * 实现
     */
    public static void main(String[] args) {
        Person person = new PersonImpl();
        PersonCloth cloth = new ExpensiveCloth(person);
        cloth.dressed();
    }
}
