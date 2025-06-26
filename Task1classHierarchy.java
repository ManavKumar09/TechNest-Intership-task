class Animal {
    protected String name;          // visible inside subclasses

    Animal(String name) {
        this.name = name;
    }

    /** Generic behaviour that most animals share. */
    void speak() {
        System.out.println("Some animal sound");
    }
}

/**
 * Dog “is-an” Animal → inherits name + speak.
 * We override speak() and add a dog-specific method fetch().
 */
class Dog extends Animal {

    Dog(String name) {
        super(name);                // call Animal constructor
    }

    @Override                      // compile-time safety check
    void speak() {
        System.out.println(name + " says: Woof!");
    }

    void fetch() {
        System.out.println(name + " is fetching the ball!");
    }
}

/**
 * Cat “is-an” Animal too. We override speak() and add scratch().
 */
class Cat extends Animal {

    Cat(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + " says: Meow!");
    }

    void scratch() {
        System.out.println(name + " is scratching the post!");
    }
}


public class Task1classHierarchy {

    public static void main(String[] args) {

        
        Animal d = new Dog("Buddy");
        Animal c = new Cat("Luna");

       
        d.speak();   // Buddy says: Woof!
        c.speak();   // Luna says: Meow!

        
        ((Dog) d).fetch();
        ((Cat) c).scratch();
    }
}