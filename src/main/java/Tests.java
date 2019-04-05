/***
 * Testes para ver se está tudo a funcionar como deve ser
 */

public class Tests {

        public static void xmlTest(){
            // XML test
            xmlParser test= new xmlParser("command1.xml");
            OrderParser order1=new OrderParser(test);
            order1.printAll();
            System.out.println("\n ---------------------------");

            xmlParser test1= new xmlParser("command2.xml");
            OrderParser order2=new OrderParser(test1);
            order2.printAll();
            System.out.println("\n ---------------------------");
            xmlParser test2= new xmlParser("command3.xml");
            OrderParser order3=new OrderParser(test2);
            order3.printAll();

            System.out.println("\n ---------------------------");
            xmlParser test3= new xmlParser("command4.xml");
            OrderParser order4= new OrderParser(test3);
            order4.printAll();

            System.out.println("\n ---------------------------");
            xmlParser test4= new xmlParser("command5.xml");
            OrderParser order5=new OrderParser(test4);
            order5.printAll();
            System.out.println("\n ---------------------------");
        }

        public static void dbTest(){

        }
    }

