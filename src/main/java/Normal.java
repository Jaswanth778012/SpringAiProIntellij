//class Address {
//    int id;
//    String sname;
//
//    Address(int id, String sname)  {
//        this.id = id;
//        this.sname = sname;
//    }
//
//    Address(Address add) {
//        this.id = add.id;
//        this.sname = add.sname;
//    }
//}
//
//class Person {
//    int id;
//    String name;
//    Address address;
//
//    Person(int id, String name, Address address) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//    }
//
//    Person(Person p) {
//        this.id = p.id;
//        this.name = p.name;
//        this.address = new Address(p.address);
//    }
//}

public class Normal {
    public static  void main(String[] args) {
//        Address add = new Address(1, "Johnmerg");
//
//        Person p1= new Person(101, "James", add);
//        Person p2 = new Person(p1);
//
//        System.out.println(p2.address.id);
//        System.out.println(p1.address.id);
//
//        p2.address.id = 2;
//
//        System.out.println(p2.address.id);
//        System.out.println(p1.address.id);

        int arr[] = {1,2,3,4};

        System.out.println(arr[0]);
    }
}
