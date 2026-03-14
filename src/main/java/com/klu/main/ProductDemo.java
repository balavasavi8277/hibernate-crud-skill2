package com.klu.main;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.klu.entity.Product;
import com.klu.util.HibernateUtil;

public class ProductDemo {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // INSERT
        Product p1 = new Product("Laptop","Gaming Laptop",75000,10);
        Product p2 = new Product("Keyboard","Mechanical Keyboard",2500,20);

        session.persist(p1);
        session.persist(p2);

        // RETRIEVE
        Product product = session.get(Product.class,1);
        System.out.println("Product Name: " + product.getName());

        // UPDATE
        product.setPrice(72000);
        session.merge(product);

        // DELETE
        Product deleteProduct = session.get(Product.class,2);
        session.remove(deleteProduct);

        tx.commit();
        session.close();

        System.out.println("CRUD Operations Completed Successfully");
    }
}