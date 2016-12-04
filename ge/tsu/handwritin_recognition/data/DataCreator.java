package ge.tsu.handwritin_recognition.data;

import java.util.Scanner;

public abstract class DataCreator {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static InputData createByScanner(boolean withAns) {
        System.out.print("სიმაღლე: ");
        int height = scanner.nextInt();
        System.out.print("სიგრძე: ");
        int width = scanner.nextInt();
        boolean grid[][] = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int tmp = scanner.nextInt();
                if (tmp == -1) {
                    System.out.println("შეყვანა იწყება თავიდან!" + System.lineSeparator());
                    return createByScanner(withAns);
                }
                grid[i][j] = tmp != 0;
            }
        }
        InputData data = new InputData(height, width, grid);
        if (withAns) {
            System.out.println("სწორი პასუხი: ");
            char letter = scanner.next().charAt(0);
            data.setLetter(letter);
        }
        return data;
    }   
}
