package ma.projet;

import ma.projet.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().openSession();
    }
}