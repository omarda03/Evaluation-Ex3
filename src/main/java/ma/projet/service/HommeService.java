package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.HommeDao;
import ma.projet.util.HibernateUtil;

public class HommeService {
    private final HommeDao hommeDao = new HommeDao();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public boolean create(Homme o) {
        return hommeDao.create(o);
    }

    public boolean update(Homme o) {
        return hommeDao.update(o);
    }

    public boolean delete(Homme o) {
        return hommeDao.delete(o);
    }

    public Homme findById(int id) {
        return hommeDao.findById(id);
    }

    public List<Homme> findAll() {
        return hommeDao.findAll();
    }

    // Méthode pour afficher les épouses d'un homme entre deux dates
    public List<Mariage> getEpousesEntreDates(int hommeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Mariage> query = session.createQuery(
                "FROM Mariage m WHERE m.homme.id = :hommeId " +
                "AND m.dateDebut >= :dateDebut AND m.dateDebut <= :dateFin",
                Mariage.class
            );
            query.setParameter("hommeId", hommeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    // Méthode pour afficher les mariages d'un homme avec tous les détails
    public void afficherMariagesHomme(int hommeId) {
        Homme homme = findById(hommeId);
        if (homme == null) {
            System.out.println("Homme introuvable!");
            return;
        }

        System.out.println("\n**Nom : " + homme.getNom().toUpperCase() + " " + homme.getPrenom().toUpperCase());
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Mariage> query = session.createQuery(
                "FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut",
                Mariage.class
            );
            query.setParameter("hommeId", hommeId);
            List<Mariage> mariages = query.list();

            List<Mariage> mariagesEnCours = mariages.stream()
                .filter(Mariage::estEnCours)
                .collect(Collectors.toList());
            
            List<Mariage> mariagesEchoues = mariages.stream()
                .filter(m -> !m.estEnCours())
                .collect(Collectors.toList());

            if (!mariagesEnCours.isEmpty()) {
                System.out.println("**Mariages En Cours :");
                int i = 1;
                for (Mariage m : mariagesEnCours) {
                    System.out.println("   " + i + ". Femme : " + m.getFemme().getNom().toUpperCase() + " " + 
                                       m.getFemme().getPrenom().toUpperCase() + 
                                       ", Date Début : " + sdf.format(m.getDateDebut()) + 
                                       ", Nbr Enfants : " + m.getNbrEnfant());
                    i++;
                }
            }

            if (!mariagesEchoues.isEmpty()) {
                System.out.println("**Mariages échoués :");
                int i = 1;
                for (Mariage m : mariagesEchoues) {
                    System.out.println("   " + i + ". Femme : " + m.getFemme().getNom().toUpperCase() + " " + 
                                       m.getFemme().getPrenom().toUpperCase() + 
                                       ", Date Début : " + sdf.format(m.getDateDebut()) + 
                                       ", Date Fin : " + sdf.format(m.getDateFin()) + 
                                       ", Nbr Enfants : " + m.getNbrEnfant());
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Méthode utilisant l'API Criteria pour afficher le nombre d'hommes mariés à quatre femmes entre deux dates
    public long getNombreHommesMariesQuatreFemmes(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(DISTINCT m.homme.id) FROM Mariage m " +
                "WHERE m.dateDebut >= :dateDebut AND m.dateDebut <= :dateFin " +
                "GROUP BY m.homme.id HAVING COUNT(m.id) = 4",
                Long.class
            );
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            List<Long> results = query.list();
            return results.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }
}
