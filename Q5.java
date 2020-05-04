public class Q5{
    public static void main(String[] args) {
        //int n=2;
        int[][] x={{1,0},{1,2}};
        for (int i = 0; i < x.length; i ++){
            for (int j = 0; j < x.length; j ++) {
                if (x[i][j] != 0){
                  continue; //continue to traverse array with non zero row
                }
                else{
                    System.out.println("First all-zero row: "+ i);
                    break;//break out of the loop when you discover your first all-zero row
                }            
            }//inner loop ends 
        }//out loop ends
    }
}
    