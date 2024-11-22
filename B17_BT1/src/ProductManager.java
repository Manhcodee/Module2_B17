import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.NotSerializableException;
public class ProductManager {
    private static final String FILE_PATH = "product.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị danh sách sản phẩm");
            System.out.println("3. Tìm kiếm sản phẩm theo mã");
            System.out.println("4. Thoát");
            System.out.print("Chọn một tùy chọn: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    displayProduct();
                    break;
                case 3:
                    searchProduct(scanner);
                    break;
                case 4:
                    System.out.println("Chương trình kết thúc");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");

            }
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Nhập mã sản phẩm: ");
        String id = scanner.nextLine();
        System.out.print("Nhập tên sản phẩm: ");
        String name = scanner.nextLine();
        System.out.print("Nhập giá sản phẩm: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Nhập hãng sản xuất: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Nhập mô tả sản phẩm: ");
        String description = scanner.nextLine();

        Product product = new Product(id,name, price, manufacturer, description);
        List<Product> products = readProductsFromFile();
        products.add(product);
        writeProductsToFile(products);
        System.out.println("Sản phẩm đã được thêm thành công.");
    }

    private static void displayProduct() {
        List<Product> products = readProductsFromFile();
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào");
        } else {
            System.out.println("Danh sách sản phẩm");
            for(Product product1 : products) {
                System.out.println(product1);
            }
        }
    }

    private static void searchProduct(Scanner scanner) {
        System.out.println("Nhập mã sản phẩm cần tìm: ");
        String id = scanner.nextLine();

         List<Product> products = readProductsFromFile();
        for (Product product : products) {
            if(product.getId().equals(id)) {
                System.out.println("Sản phẩm được tìm thấy");
                System.out.println(product);
                return;
            }
        }
        System.out.println("Không tìm thấy mã " + id);
    }

    private static List<Product> readProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            products = (List<Product>) ois.readObject();

        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi đọc file " + e.getMessage());
        }
        return products;
    }

    private static void writeProductsToFile(List<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(products);
        }catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

}
