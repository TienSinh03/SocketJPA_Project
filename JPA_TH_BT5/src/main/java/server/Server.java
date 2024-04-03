/**
 * @ (#) Server.java      4/3/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package server;

import dao.DaoCategory;
import dao.DaoCustomer;
import dao.DaoOrder;
import dao.DaoProduct;
import entities.Category;
import entities.Customer;
import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/3/2024
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("Server is listening on port 1234");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                System.out.println("Client IP: " + socket.getInetAddress().getHostAddress());
                System.out.println("Client Port: " + socket.getPort());

                Server server = new Server();
                Handler handler = server.new Handler(socket);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private class Handler implements Runnable {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo_SQL");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        private Socket socket;
        private DaoCategory daoCategory;
        private DaoProduct daoProduct;
        private DaoCustomer daoCustomer;
        private DaoOrder daoOrder;

        public Handler(Socket socket) {
            this.socket = socket;
            daoCategory = new DaoCategory(em, tx);
            daoProduct = new DaoProduct(em, tx);
            daoCustomer = new DaoCustomer(em, tx);
            daoOrder = new DaoOrder(em, tx);
        }

        @Override
        public void run() {
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    int choice = in.readInt(); // đọc lựa chọn từ client

                    System.out.println("Client choice: " + choice);

                    switch (choice) {
                        case 0:
                            System.out.println("Server is closing...");
                            socket.close();
                            System.exit(0);
                            break;
                        case 1:
                            boolean temp = in.readBoolean(); // đọc temp = true từ client
                            // khi temp = false thì ngắt vòng lặp
                            while (temp) {
                                int choiceCURD = in.readInt();
                                System.out.println("Client choice CURD: " + choiceCURD);
                                switch (choiceCURD) {
                                    case 0:
                                        // Nhận yêu cầu từ client
                                        System.out.println("Client is message: " + in.readUTF());
                                        out.writeBoolean(false); // gửi temp = false cho client
                                        temp = in.readBoolean(); // nhận temp = false từ client va de ngắt vòng loop
                                        break;
                                    case 1, 2, 3:
                                        String message = in.readUTF(); // đọc thông báo từ client
                                        System.out.println("Client message: " + message);

                                        out.writeUTF("CRUD dang bao tri. Vui long chon lua chon khac...");
                                        out.flush();
                                        break;
                                    case 4:
                                        System.out.println("CRUD Find");
                                        int id = in.readInt(); // đọc id từ client
                                        System.out.println("Client Sent Id: " + id);
                                        Category category = daoCategory.findById(id);
                                        objectOutputStream.writeObject(category);
                                        break;
                                }
                            }
                            break;
                        case 2:
                            System.out.println("Client is message: " + in.readUTF());
                            List<Product> products = daoProduct.listProductsMaxPrice();
                            objectOutputStream.writeObject(products);
                            break;
                        case 3:
                            System.out.println("Client is message: " + in.readUTF());
                            List<Product> products1 = daoProduct.listProductsNullOrders();
                            objectOutputStream.writeObject(products1);
                            break;
                        case 4:
                            System.out.println("Client is message: " + in.readUTF());
                            Map<String, Integer> map = daoCustomer.getNumberCustomerByState();
                            objectOutputStream.writeObject(map);
                            break;
                        case 5:
                            System.out.println("Client is message: " + in.readUTF());
                            int idOrder = in.readInt();
                            System.out.println("Client Sent Id Order: " + idOrder);
                            double totalPrice = daoOrder.getTotalOrderById(idOrder);
                            out.writeDouble(totalPrice);
                            break;
                        case 6:
                            System.out.println("Client is message: " + in.readUTF());
                            Map<Customer, Integer> maps = daoCustomer.getOrdersByCustomers();
                            objectOutputStream.writeObject(maps);
                            break;
                        case 7:
                            System.out.println("Client is message: " + in.readUTF());
                            Map<Product, Integer> map1 = daoProduct.getTotalProducts();
                            objectOutputStream.writeObject(map1);
                            break;
                        case 8:
                            System.out.println("Client is message: " + in.readUTF());

                            String dateStr = in.readUTF().trim(); // Đọc và loại bỏ dấu xuống dòng
                            if (!dateStr.isEmpty()) {
                                System.out.println("Client Sent Date: " + dateStr);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate date = LocalDate.parse(dateStr, formatter);
                                double totalPriceByDate = daoOrder.getTotalOrderByDate(date);
                                out.writeDouble(totalPriceByDate);
                            } else {
                                System.out.println("Client did not provide a date.");
                                // Xử lý trường hợp client không cung cấp ngày.
                            }
                            break;

                        case 9:
                            System.out.println("Client is message: " + in.readUTF());
                            int n = daoCustomer.deleteCustomerNotOrder();
                            if(n > 0) {
                                out.writeUTF("Xoa thanh cong khach hang");
                            } else {
                                out.writeUTF("Khong co khach hang nao de xoa");
                            }
                            break;

                        case 10:
                            System.out.println("Client is message: " + in.readUTF());
                            Map<String, Double> statistics = daoOrder.statisticsTotalPriceByMonthAndYear();
                            objectOutputStream.writeObject(statistics);
                            break;
                    }


                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
