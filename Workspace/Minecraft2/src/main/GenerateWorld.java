package main;

import java.util.Random;

public class GenerateWorld {
	public static Random rand = new Random();
	public static int world[][] = new int[16][12];
	public static int[][] Generate(){
		int x = 0;
		int y = 0;
		while(x<16){
			while(y<3){
			world[x][11-y] = 1;
			y++;
			}
			y = 0;
			x++;
		}
		int i = 0;
		while(i < 16){
			
			int  n = rand.nextInt(2) + 0;
			//if(n==0){world[i][7] = 6;}
			i++;
		}
		i = 0;
		while(i < 16){
			if(world[i][7] == 0){
				world[i][8] = 6;
				if(!(i>13)){world[i+2][8] = 6;}
				}else{world[i][8] = 1;}
			i++;
		}
		i = 0;
		while(i < 16){
			world[i][11] = 8;
			i++;
		}
		i = 0;
		while(i < 16){
			int  n = rand.nextInt(2) + 0;
			if(n==0){world[i][10] = 8;}
			i++;
		}
		GenerateTree(rand.nextInt(13) + 2);
		return world;
		
	}
	private static void GenerateTree(int i) {
		world[i][7] = 3;
		world[i][6] = 3;
		//world[i][5] = 3;
		
		world[i-1][5] = 7;
		world[i][5] = 7;
		world[i+1][5] = 7;
		world[i-1][4] = 7;
		world[i][4] = 7;
		world[i+1][4] = 7;
	}

}
