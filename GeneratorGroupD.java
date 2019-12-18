package groupD;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.stream.Stream;


public class GeneratorGroupD {
    static ArrayList<char[][]> global_arr = new ArrayList<>();
    public static ArrayList<char[]>  textToArrayAL(String pathName) {
        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(pathName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
            String strLine;
            //Read File Line By Line
            //char[][] twoDarray = new char[150][16];
            ArrayList<char[]> twoDarray = new ArrayList<>();
            int i = 0;
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
//                System.out.println (strLine);
                char[] temp = strLine.toCharArray();
                twoDarray.add(temp);


                i++;
            }
            return twoDarray;

        }catch (IOException e){//Catch exception if any
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<char[][]> getChunky(ArrayList<char[]> fullLevel, int len) {

        int noOfChunks = 15;
        int c =0;
        int g=0;

        ArrayList<char[][]> output = new ArrayList<>();
        for(int i = 0; i < 150; i += noOfChunks) {
            char[][] temp = new char[16][noOfChunks];
            for (int j = 0; j < 16; j++) {
                c = 0;
                for (int k = i; k < i + noOfChunks; k++) {
//                    System.out.println(i + " " + c +  " " + j + " " + k );
                    temp[j][c] = fullLevel.get(j)[k];
//                    System.out.print(temp[j][c]);
                    c++;

                }
//                System.out.println();
            }
            output.add(temp);
        }
        return output;
    }

    public static char[][] get_fittest(char[][] chunk1, char[][] chunk2){
        // fitness out of 100
        // fitness defines as
            // optimum enemy - 3
            // more blocks in pattern
            // power ups
            // coins
            // boin box
        int fitness_chunk_1 = 0;
        int fitness_chunk_2 = 0;

//        System.out.println(chunk1.length);
        for (int i = 0; i < chunk1.length; i++){
            for(int j = 0; j < 15; j++){

                // CHUNK 1 FITNESS
                if(chunk1[i][j] != '-'){
                    if(chunk1[i][j] == 'o' || chunk1[i][j] == '@' || chunk1[i][j] == '!' ){
                        fitness_chunk_1 += 3;
                    }else {
                        fitness_chunk_1++;
                    }
                }

                // CHUNK 2 FITNESS
                if(chunk2[i][j] != '-'){
                    if(chunk2[i][j] == 'o' || chunk1[i][j] == '@' || chunk1[i][j] == '!' ){
                        fitness_chunk_1 += 3;
                    }else {
                        fitness_chunk_2++;
                    }
                }
            }
        }

//        System.out.println(fitness_chunk_1);
//        System.out.println(fitness_chunk_2);

        if (fitness_chunk_1 > fitness_chunk_2){
            return chunk1;
        }else if(fitness_chunk_1 == fitness_chunk_2){
            double r = Math.random();
            if(r < 0.5){

                return chunk2;
            }else{

                return chunk1;
            }
        }else{

            return chunk2;
        }
    }

    //pattern evaluation
    // find pattern in level keep pattern
        // findPattern()
        // give fitness to chunky column
    //
    //take fitter model
        // append fitter model to the final map

    public static char[][] crossover(char[][] chunk1, char[][] chunk2){
//        int mid = new Random().nextInt(chunk1.length);
        int mid = chunk1.length;
        //int temp = geneA[i];
        //geneA[i] = geneB[i];
        //geneB[i] = temp;

        char[][] output = chunk2;

        for(int i = 0; i < chunk1.length; i++){
            for(int j = 0; j < 15; j++){
                if(i > mid){
//                    System.out.println(chunk2[i][j]);
                    output[i][j] = chunk2[i][j];
//                    System.out.println(output[i][j]);

                }else{
                    output[i][j] = chunk1[i][j];
                }
            }
            mid --;
        }
//        System.out.println(chunk1[0].length);
//        System.out.println(output[0].length);
        return output;
    }

    public static char[][] add_flag (char[][] final_block){
        int flag = 0;
        for(int i = 15; i >= 0; i--){
            for(int j = 14; j >= 0; j--){

                if(flag == 0) {

                    if (final_block[i][j] == '%' || final_block[i][j] == 'X') {
                        if (final_block[i - 2][j] == '-') {
                            final_block[i - 1][j] = 'F';
//                            System.out.println("Flag added");
                            flag++;
                        }
                    }
                }
            }
        }
        if (flag==0){
            final_block[12][2]='F';
            final_block[11][2]='-';
            final_block[12][1]='X';
            final_block[12][3]='X';
            final_block[13][2]='X';
            flag++;
        }

        return final_block;
    }

    public static void print2D(char mat[][]){
        for (int i = 0; i < mat.length; i++) {
            System.out.println();
            for (int j = 0; j < mat[i].length; j++)
                System.out.print(mat[i][j]);
        }
    }

    public static void write_file(String output_name, ArrayList<char[][]> generated_level) throws IOException{
        String line1 = "";String line2 = "";String line3 = "";String line4 = "";
        String line5 = "";String line6 = "";String line7 = "";String line8 = "";
        String line9 = "";String line10 = "";String line11 = "";String line12 = "";
        String line13 = "";String line14 = "";String line15 = "";String line16 = "";

        File fout = new File(output_name);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (int i = 0; i < generated_level.size(); i++) {
                line1 = line1 + new String(generated_level.get(i)[0]);
                line2 = line2 + new String(generated_level.get(i)[1]);
                line3 = line3 + new String(generated_level.get(i)[2]);
                line4 = line4 + new String(generated_level.get(i)[3]);
                line5 = line5 + new String(generated_level.get(i)[4]);
                line6 = line6 + new String(generated_level.get(i)[5]);
                line7 = line7 + new String(generated_level.get(i)[6]);
                line8 = line8 + new String(generated_level.get(i)[7]);
                line9 = line9 + new String(generated_level.get(i)[8]);
                line10 = line10 + new String(generated_level.get(i)[9]);
                line11 = line11 + new String(generated_level.get(i)[10]);
                line12 = line12 + new String(generated_level.get(i)[11]);
                line13 = line13 + new String(generated_level.get(i)[12]);
                line14 = line14 + new String(generated_level.get(i)[13]);
                line15 = line15 + new String(generated_level.get(i)[14]);
                line16 = line16 + new String(generated_level.get(i)[15]);
            }

        bw.write(line1);
        bw.newLine();
        bw.write(line2);
        bw.newLine();
        bw.write(line3);
        bw.newLine();
        bw.write(line4);
        bw.newLine();
        bw.write(line5);
        bw.newLine();
        bw.write(line6);
        bw.newLine();
        bw.write(line7);
        bw.newLine();
        bw.write(line8);
        bw.newLine();
        bw.write(line9);
        bw.newLine();
        bw.write(line10);
        bw.newLine();
        bw.write(line11);
        bw.newLine();
        bw.write(line12);
        bw.newLine();
        bw.write(line13);
        bw.newLine();
        bw.write(line14);
        bw.newLine();
        bw.write(line15);
        bw.newLine();
        bw.write(line16);
        bw.close();

    }

    public static void runRandomGenerator(String output_name, String newlevel1, String newlevel2, String newlevel3) throws IOException{
        ArrayList<char[]>  a = textToArrayAL(newlevel1);
        ArrayList<char[][]> chunks_of_a = getChunky(a, a.get(0).length);

        ArrayList<char[]>  b = textToArrayAL(newlevel2);
        ArrayList<char[][]> chunks_of_b = getChunky(b, b.get(0).length);

        ArrayList<char[]>  c = textToArrayAL(newlevel3);
        ArrayList<char[][]> chunks_of_c = getChunky(c, c.get(0).length);

        ArrayList<char[][]> generate_level = new ArrayList<>();
        //get fitness in array
        //generate our final arraylist

        for(int i = 0; i < chunks_of_a.size(); i ++){
            char[][] temp = get_fittest(chunks_of_a.get(i), chunks_of_b.get(i));
            char[][] new_temp = crossover(temp, chunks_of_c.get(i));
            generate_level.add(new_temp);
        }

        generate_level.set(9, add_flag(generate_level.get(9)));

        write_file(output_name, generate_level);
    }

    public static void runGenerator(String output_name) throws IOException{
        int r = new Random().nextInt(1000) + 1;
        int r1 = new Random().nextInt(1000) + 1;
        int r2 = new Random().nextInt(1000) + 1;

        String level = "levels/ore/lvl-";
        String txtBit = ".txt";

        String newlevel1 = level + r + txtBit;
        String newlevel2 = level + r1 + txtBit;
        String newlevel3 = level + r2 + txtBit;

        //2D array of all the lines of characters/level y axis
        ArrayList<char[]>  a = textToArrayAL(newlevel1);
        ArrayList<char[][]> chunks_of_a = getChunky(a, a.get(0).length);

        ArrayList<char[]>  b = textToArrayAL(newlevel2);
        ArrayList<char[][]> chunks_of_b = getChunky(b, b.get(0).length);

        ArrayList<char[]>  c = textToArrayAL(newlevel3);
        ArrayList<char[][]> chunks_of_c = getChunky(c, c.get(0).length);

        ArrayList<char[][]> generate_level = new ArrayList<>();
        //get fitness in array
        //generate our final arraylist

        for(int i = 0; i < chunks_of_a.size(); i ++){
            char[][] temp = get_fittest(chunks_of_a.get(i), chunks_of_b.get(i));
            char[][] new_temp = crossover(temp, chunks_of_c.get(i));
            generate_level.add(new_temp);
        }

        generate_level.set(9, add_flag(generate_level.get(9)));

        write_file(output_name, generate_level);
    }

    public static void main(String[] args) throws  IOException{

        int r = new Random().nextInt(1000) + 1;
        int r1 = new Random().nextInt(1000) + 1;
        int r2 = new Random().nextInt(1000) + 1;

        String level = "levels/ore/lvl-";
        String txtBit = ".txt";

        String newlevel1 = level + r + txtBit;
        String newlevel2 = level + r1 + txtBit;
        String newlevel3 = level + r2 + txtBit;

        //2D array of all the lines of characters/level y axis
        ArrayList<char[]>  a = textToArrayAL(newlevel1);
        ArrayList<char[][]> chunks_of_a = getChunky(a, a.get(0).length);

        ArrayList<char[]>  b = textToArrayAL(newlevel2);
        ArrayList<char[][]> chunks_of_b = getChunky(b, b.get(0).length);

        ArrayList<char[]>  c = textToArrayAL(newlevel3);
        ArrayList<char[][]> chunks_of_c = getChunky(c, c.get(0).length);

        ArrayList<char[][]> generate_level = new ArrayList<>();
        //get fitness in array
        //generate our final arraylist

        for(int i = 0; i < chunks_of_a.size(); i ++){
            char[][] temp = get_fittest(chunks_of_a.get(i), chunks_of_b.get(i));
//            print2D(temp);
            char[][] new_temp = crossover(temp, chunks_of_c.get(i));
//            print2D(new_temp);
            generate_level.add(new_temp);
        }
//        System.out.println(generate_level.size());
        generate_level.set(9, add_flag(generate_level.get(9)));

//        System.out.println("HEYO" + new String(generate_level.get(0)[0]));
//        for(int i = 0; i < generate_level.size(); i++){
//            print2D(generate_level.get(i));
//        }

        write_file("src/groupD/generated_level.txt", generate_level);

    }

}
