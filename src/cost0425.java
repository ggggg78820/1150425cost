import java.util.Scanner;

public class cost0425 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. 定義可選擇的支出類別
        String[] categories = {
            "三餐",
            "點心",
            "飲料",
            "水電瓦斯",
            "生活用品",
            "交通相關",
            "治裝美容",
            "雜支"
        };

        // 每一個類別對應一個累計金額，初始時全部為 0
        double[] totals = new double[categories.length];

        boolean keepRunning = true; // 控制是否繼續迴圈

        while (keepRunning) {
            // 顯示選單清單
            System.out.println("===== 簡易記帳本 =====");
            for (int i = 0; i < categories.length; i++) {
                System.out.printf("%d. %s\n", i + 1, categories[i]);
            }
            System.out.println("0. 結束程式");
            System.out.print("請輸入類別編號 (0-8): ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                // 若輸入非數字，清除錯誤輸入並繼續要求輸入
                scanner.next();
                System.out.println("輸入格式錯誤，請輸入數字。\n");
                continue;
            }

            // 判斷是否結束程式
            if (choice == 0) {
                keepRunning = false;
                break;
            }

            // 驗證選項是否在有效範圍內
            if (choice < 1 || choice > categories.length) {
                System.out.println("選項超出範圍，請重新輸入。\n");
                continue;
            }

            int categoryIndex = choice - 1; // 轉換為陣列索引
            System.out.printf("您選擇的類別：%s\n", categories[categoryIndex]);
            System.out.print("請輸入金額：");

            double amount = 0;
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
            } else {
                scanner.next();
                System.out.println("金額格式錯誤，請輸入數字。\n");
                continue;
            }

            // 若金額為負數也不接受，提示使用者重新輸入
            if (amount < 0) {
                System.out.println("金額不可為負值，請重新輸入。\n");
                continue;
            }

            // 根據玩家選項執行：將輸入金額加到對應類別的累計上
            totals[categoryIndex] += amount;
            System.out.printf("已新增 %s 支出：%.2f 元\n", categories[categoryIndex], amount);
            System.out.println();
        }

        // 程式結束前，顯示各類別累計金額與總和
        System.out.println("\n===== 本次記帳總覽 =====");
        double grandTotal = 0;
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("%s：%.2f 元\n", categories[i], totals[i]);
            grandTotal += totals[i];
        }
        System.out.printf("總支出：%.2f 元\n", grandTotal);
        System.out.println("感謝使用記帳本，程式已結束。");

        scanner.close();
    }
}
