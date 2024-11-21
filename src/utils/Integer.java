package utils;

public class Integer implements Additionable{
    int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public void addition(Additionable a) {
        try {
            this.value = this.value + ((Rational) a).value;
        }catch (Exception e){
            this.value = this.value + ((Integer) a).value;
        }
        switch(a){
            case Integer x:
                break;
            case Rational x:
        }
    }
}
