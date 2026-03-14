package com.klu.main;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.klu.entity.Product;
import com.klu.util.HibernateUtil;

public class ProductHQLDemo {

 public static void main(String[] args) {

  Session session = HibernateUtil.getSessionFactory().openSession();
  Transaction tx = session.beginTransaction();

  // 1. SORT BY PRICE ASC
  Query<Product> q1 = session.createQuery("FROM Product ORDER BY price ASC", Product.class);
  List<Product> list1 = q1.list();
  System.out.println("Price Ascending");
  list1.forEach(p -> System.out.println(p.getName()+" "+p.getPrice()));

  // 2. SORT BY PRICE DESC
  Query<Product> q2 = session.createQuery("FROM Product ORDER BY price DESC", Product.class);
  List<Product> list2 = q2.list();
  System.out.println("Price Descending");
  list2.forEach(p -> System.out.println(p.getName()+" "+p.getPrice()));

  // 3. SORT BY QUANTITY (Highest First)
  Query<Product> q3 = session.createQuery("FROM Product ORDER BY quantity DESC", Product.class);
  List<Product> list3 = q3.list();
  System.out.println("Quantity Highest First");
  list3.forEach(p -> System.out.println(p.getName()+" "+p.getQuantity()));

  // 4. PAGINATION (FIRST 3)
  Query<Product> q4 = session.createQuery("FROM Product", Product.class);
  q4.setFirstResult(0);
  q4.setMaxResults(3);
  System.out.println("First 3 Products");
  q4.list().forEach(p -> System.out.println(p.getName()));

  // 5. PAGINATION (NEXT 3)
  Query<Product> q5 = session.createQuery("FROM Product", Product.class);
  q5.setFirstResult(3);
  q5.setMaxResults(3);
  System.out.println("Next 3 Products");
  q5.list().forEach(p -> System.out.println(p.getName()));

  // 6. COUNT TOTAL PRODUCTS
  Query<Long> q6 = session.createQuery("SELECT COUNT(*) FROM Product", Long.class);
  System.out.println("Total Products: "+q6.uniqueResult());

  // 7. COUNT PRODUCTS WHERE quantity > 0
  Query<Long> q7 = session.createQuery("SELECT COUNT(*) FROM Product WHERE quantity > 0", Long.class);
  System.out.println("Available Products: "+q7.uniqueResult());

  // 8. MIN AND MAX PRICE
  Query<Object[]> q8 = session.createQuery(
    "SELECT MIN(price), MAX(price) FROM Product", Object[].class);
  Object[] result = q8.uniqueResult();
  System.out.println("Min Price: "+result[0]);
  System.out.println("Max Price: "+result[1]);

  // 9. GROUP BY DESCRIPTION
  Query<Object[]> q9 = session.createQuery(
    "SELECT description, COUNT(*) FROM Product GROUP BY description", Object[].class);

  List<Object[]> groupList = q9.list();
  groupList.forEach(r -> System.out.println(r[0]+" : "+r[1]));

  // 10. PRICE RANGE FILTER
  Query<Product> q10 = session.createQuery(
    "FROM Product WHERE price BETWEEN 1000 AND 50000", Product.class);

  q10.list().forEach(p -> System.out.println("Range Product: "+p.getName()));

  // LIKE QUERIES

  // Names starting with letter
  Query<Product> q11 = session.createQuery(
    "FROM Product WHERE name LIKE 'L%'", Product.class);

  // Names ending with letter
  Query<Product> q12 = session.createQuery(
    "FROM Product WHERE name LIKE '%p'", Product.class);

  // Names containing substring
  Query<Product> q13 = session.createQuery(
    "FROM Product WHERE name LIKE '%top%'", Product.class);

  // Exact length (5 characters)
  Query<Product> q14 = session.createQuery(
    "FROM Product WHERE name LIKE '_____'", Product.class);

  tx.commit();
  session.close();
 }
}