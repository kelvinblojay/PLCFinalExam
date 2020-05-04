public class Q8PartC{

public static void main(String args[]){
    int j = -3;
    for( int i = 0 ; i < 3 ; i++ ){
        if( j + 2 == 2 || j + 2 == 3 )
            j--;
        else if( j + 2 == 0 )
            j += 2;
        else
            j = 0;
        if( j > 0 )
            break;
        j = 3 - i;
    }
    System.out.println("Content %d\n"+j);
}
}