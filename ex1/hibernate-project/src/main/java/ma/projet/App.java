package ma.projet;

import ma.projet.util.HibernateUtil;

public class App {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().openSession();
    }
}