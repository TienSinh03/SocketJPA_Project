/**
 * @ (#) Client.java      4/3/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package fit.iuh.dev;

import entities.Category;
import entities.Customer;
import entities.Product;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/3/2024
 */
public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.1.3", 1234);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("""                     
                        0. Exit \s
                        1) Thực hiện CRUD \s
                        2) Tìm danh sách sản phẩm có giá cao nhất. \s
                        3) Tìm danh sách sản phẩm chưa bán được lần nào.\s
                        4) Thống kê số khách hàng theo từng bang.\s
                        5) Tính tổng tiền của đơn hàng khi biết mã số đơn hàng.\s
                        6) Đếm số đơn hàng của từng khách hàng.\s
                        7) Tính tổng số lượng của từng sản phẩm đã bán ra.\s
                        8) Tính tổng tiền của tất cả các hóa đơn trong một ngày nào đó.\s
                        9) Xóa tất cả các khách hàng chưa mua hàng.\s
                        10) Thống kê tổng tiền hóa đơn theo tháng / năm.
                        """);
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();
                out.writeInt(choice); // gửi lựa chọn cho server

                switch (choice) {
                    case 0:
                        System.out.println("Client is closing...");
                        System.exit(0);
                        break;
                    case 1:
                        boolean temp = true;
                        out.writeBoolean(temp); // gửi temp = true cho server
                        out.flush();
                        while (temp) {
                            System.out.println("""
                                    Enter choice for CRUD:\s
                                    0. Back \s
                                    1. Create \s
                                    2. Update \s
                                    3. Delete \s
                                    4. Find""");
                            int choiceCRUD = scanner.nextInt();
                            out.writeInt(choiceCRUD); // gửi lựa chọn cho server
                            switch (choiceCRUD) {
                                case 0:
                                    out.writeUTF("Yeu cau tro lai choice chinh"); // gửi thông báo yeu cau quay lai choice cho server
                                    out.flush();
                                    temp = in.readBoolean(); // nhận temp = false từ server để ngắt vòng lặp ben client
                                    out.writeBoolean(temp); // gửi temp = false cho server để ngắt vòng lặp ben server
                                    break;
                                case 1, 2, 3:
                                    out.writeUTF("CRUD Create/Update/Delete"); // gửi thông báo cho server
                                    out.flush();
                                    System.out.println("Server message: " + in.readUTF()); // in ra thông báo từ server
                                    break;
                                case 4:
                                    System.out.println("CRUD Find");
                                    System.out.println("Enter category id: ");
                                    int message = scanner.nextInt();
                                    out.writeInt(message); // gửi id cho server
                                    out.flush();

                                    Category category = (Category) ois.readObject();
                                    System.out.println("Server received: " + category);
                                    break;
                            }
                        }
                        break;
                    case 2:
                        out.writeUTF("Tìm danh sách sản phẩm có giá cao nhất"); // gửi thông báo cho server
                        out.flush();

                        List<Product> products = (List<Product>) ois.readObject();
                        System.out.println("Server received: ");
                        products.forEach(System.out::println);
                        break;
                    case 3:
                        out.writeUTF("Tìm danh sách sản phẩm chưa bán được lần nào"); // gửi thông báo cho server
                        out.flush();

                        List<Product> products1 = (List<Product>) ois.readObject();
                        System.out.println("Server received: ");
                        products1.forEach(System.out::println);
                        break;
                    case 4:
                        out.writeUTF("Thống kê số khách hàng theo từng bang"); // gửi thông báo cho server
                        out.flush();

                        System.out.println("Server received: ");
                        Map<String, Integer> map = (Map<String, Integer>) ois.readObject();
                        System.out.println("Server received: ");
                        map.forEach((key, value) -> System.out.println(key + " | So luong: " + value));
                        break;
                    case 5:
                        out.writeUTF("Tính tổng tiền của đơn hàng khi biết mã số đơn hàng"); // gửi thông báo cho server
                        System.out.println("Enter order id: ");
                        int idOrder = scanner.nextInt();
                        out.writeInt(idOrder); // gửi id cho server
                        out.flush();

                        double totalPrice = in.readDouble();
                        System.out.println("Server received totalPrice: " + totalPrice);
                        break;
                    case 6:
                        out.writeUTF("Đếm số đơn hàng của từng khách hàng"); // gửi thông báo cho server
                        out.flush();

                        Map<Customer, Integer> map1 = (Map<Customer, Integer>) ois.readObject();
                        System.out.println("Server received: ");
                        map1.forEach((key, value) -> System.out.println(key + " | So luong: " + value));
                        break;
                    case 7:
                        out.writeUTF("Tính tổng số lượng của từng sản phẩm đã bán ra"); // gửi thông báo cho server
                        out.flush();

                        Map<Product, Integer> map2 = (Map<Product, Integer>) ois.readObject();
                        System.out.println("Server received: ");
                        map2.forEach((key, value) -> System.out.println(key + " | Total: " + value));
                        break;
                    case 8:
                        out.writeUTF("Tính tổng tiền của tất cả các hóa đơn trong một ngày nào đó\n"); // gửi thông báo cho server
                        out.flush();

                        System.out.println("Enter the date based on the format (yyyy-MM-dd): ");
                        String date = scanner.next();
                        if (!date.isEmpty()) {
                            out.writeUTF(date); // gửi date cho server nếu không rỗng
                            out.flush();
                        } else {
                            System.out.println("Ngày không được để trống.");
                        }

                        double totalPriceByDate = in.readDouble();
                        System.out.println("Server received totalPriceByDate: " + totalPriceByDate);

                        break;
                    case 9:
                        out.writeUTF("Xóa tất cả các khách hàng chưa mua hàng"); // gửi thông báo cho server
                        out.flush();
                        System.out.println("Server received: " + in.readUTF());
                        break;

                    case 10:
                        out.writeUTF("Thống kê tổng tiền hóa đơn theo tháng / năm"); // gửi thông báo cho server
                        out.flush();

                        Map<String, Double> statistics = (Map<String, Double>) ois.readObject();
                        System.out.println("Server received: ");
                        statistics.forEach((key, value) -> System.out.println(key + " | Total: " + value));
                        break;


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
