package ma.projet.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ma.projet.beans.Femme;
import ma.projet.dao.FemmeDao;
import ma.projet.util.HibernateUtil;

public class FemmeService {
    private final FemmeDao femmeDao = new FemmeDao();

    public boolean create(Femme o) {
        return femmeDao.create(o);
    }

    public boolean update(Femme o) {
        return femmeDao.update(o);
    }

    public boolean delete(Femme o) {
        return femmeDao.delete(o);
    }

    public Femme findById(int id) {
        return femmeDao.findById(id);
    }

    public List<Femme> findAll() {
        return femmeDao.findAll();
    }

    // Méthode pour exécuter une requête native nommée retournant le nombre d'enfants d'une femme entre deux dates
    public Long getNombreEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            @SuppressWarnings("unchecked")
            Query<Object> query = session.createNamedQuery("Femme.nombreEnfantsEntreDates");
            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            Object result = query.uniqueResult();
            if (result == null) {
                return 0L;
            }
            // Pour les requêtes natives retournant SUM, le résultat peut être un BigInteger ou Long
            if (result instanceof Number) {
                return ((Number) result).longValue();
            }
            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            session.close();
        }
    }

    // Méthode pour exécuter une requête nommée retournant les femmes mariées au moins deux fois
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createNamedQuery("Femme.marieeAuMoinsDeuxFois", Femme.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    // Méthode pour trouver la femme la plus âgée
    public Femme getFemmeLaPlusAgee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery(
                "FROM Femme ORDER BY dateNaissance ASC",
                Femme.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
