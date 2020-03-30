/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criterialab;

import entity.BuyerBidProduct;
import entity.BuyerBuyProduct;
import entity.Category;
import entity.Product;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

/**
 *
 * @author DELL
 */
public class CriteriaLab {

    /**
     * @param args the command line arguments
     */
     static  SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
  static   Session session = sessionFactory.openSession();
    public static void main(String[] args) {
   
   
              diplayProductByCategory();
              displayProductByBids();
              showTotalAmount();
               
                
                
                  

    }
    public static void diplayProductByCategory(){
 
             List<Product> result= session.createCriteria(Product.class)
                .createAlias("categories", "p").add(Restrictions.eq("p.id", 1)).list();
                for(Product product:result){
                    System.out.println("Products Name:"+product.getName()+", Product desc"+product.getDescription());
                }
    }

    private static void displayProductByBids() {
     
                Product product = (Product)session.load(Product.class,1);
                Criteria criteria = session.createCriteria(BuyerBidProduct.class, "b");
                Criteria bidCriteria = criteria.add(Restrictions.eq("b.product.id", product.getId()));
                List<BuyerBidProduct> result = bidCriteria.list();
                for(BuyerBidProduct buyer:result){
                    System.out.println("Buyer Id : "+buyer.getId().getBuyerId()+", Buyer Name : "+buyer.getBuyer().getUser().getUserName()+" , Product : "+buyer.getProduct().getName());
                }
               
      
       
    }
    private static void showTotalAmount(){
       
                Criteria totalAmount = session.createCriteria(BuyerBuyProduct.class)
                .add(Restrictions.eq("product.id", 1))
                .setProjection(Projections.sqlProjection("sum( amount * quantity) as result",  new String[] {"result"} , new Type[] {Hibernate.DOUBLE}));
                System.out.println("Total Amount : "+totalAmount.uniqueResult());


        
    }
    
}
