import java.io.*;
import java.util.Scanner;

public class FileCopyBinary {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Nhập đường dẫn tệp nguồn: ");
            String sourcePath = scanner.nextLine();
            File sourceFile = new File(sourcePath);

            if (!sourceFile.exists()) {
                System.out.println("Tệp nguồn không tồn tại!");
                return;
            }

            System.out.print("Nhập đường dẫn tệp đích: ");
            String targetPath = scanner.nextLine();
            File targetFile = new File(targetPath);

            // Kiểm tra tệp đích
            if (targetFile.exists()) {
                System.out.println("Tệp đích đã tồn tại. Bạn có muốn ghi đè? (y/n): ");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("y")) {
                    System.out.println("Hủy sao chép.");
                    return;
                }
            }

            long totalBytes = copyFile(sourceFile, targetFile);
            System.out.println("Sao chép hoàn tất!");
            System.out.println("Tổng số byte đã sao chép: " + totalBytes);
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static long copyFile(File source, File target) throws IOException {
        long byteCount = 0;

        // Sử dụng try-with-resources để tự động đóng luồng
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(target)) {

            byte[] buffer = new byte[1024]; // Bộ nhớ đệm 1KB
            int bytesRead;

            // Đọc từng khối dữ liệu từ tệp nguồn và ghi vào tệp đích
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
        }

        return byteCount;
    }
}
