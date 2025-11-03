package ma.projet.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

public class Test {
    private static final HommeService hommeService = new HommeService();
    private static final FemmeService femmeService = new FemmeService();
    private static final MariageService mariageService = new MariageService();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        try {
            // Initialisation
            System.out.println("=== Initialisation de la base de données ===\n");
            initialiserDonnees();

            // 1. Afficher la liste des femmes
            System.out.println("\n=== 1. Liste des femmes ===");
            afficherListeFemmes();

            // 2. Afficher la femme la plus âgée
            System.out.println("\n=== 2. Femme la plus âgée ===");
            afficherFemmeLaPlusAgee();

            // 3. Afficher les épouses d'un homme donné
            System.out.println("\n=== 3. Épouses d'un homme donné ===");
            Homme homme1 = hommeService.findAll().get(0);
            afficherEpousesHomme(homme1.getId());

            // 4. Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("\n=== 4. Nombre d'enfants d'une femme entre deux dates ===");
            Femme femme1 = femmeService.findAll().get(0);
            Date date1 = sdf.parse("01/01/1985");
            Date date2 = sdf.parse("31/12/2005");
            afficherNombreEnfantsEntreDates(femme1.getId(), date1, date2);

            // 5. Afficher les femmes mariées deux fois ou plus
            System.out.println("\n=== 5. Femmes mariées deux fois ou plus ===");
            afficherFemmesMarieesDeuxFoisOuPlus();

            // 6. Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("\n=== 6. Hommes mariés à quatre femmes entre deux dates ===");
            Date date3 = sdf.parse("01/01/1985");
            Date date4 = sdf.parse("31/12/2005");
            afficherHommesMariesQuatreFemmes(date3, date4);

            // 7. Afficher les mariages d'un homme avec tous les détails
            System.out.println("\n=== 7. Mariages d'un homme avec tous les détails ===");
            Homme homme2 = hommeService.findAll().get(0);
            hommeService.afficherMariagesHomme(homme2.getId());

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            // Fermer Hibernate
            ma.projet.util.HibernateUtil.shutdown();
        }
    }

    private static void initialiserDonnees() throws ParseException {
        // Créer 10 femmes
        Femme f1 = new Femme("RAMI", "SALIMA", "0612345678", "Casablanca", sdf.parse("15/05/1970"));
        Femme f2 = new Femme("ALI", "AMAL", "0623456789", "Rabat", sdf.parse("20/08/1975"));
        Femme f3 = new Femme("ALAOUI", "WAFA", "0634567890", "Fès", sdf.parse("10/03/1980"));
        Femme f4 = new Femme("ALAMI", "KARIMA", "0645678901", "Marrakech", sdf.parse("25/11/1965"));
        Femme f5 = new Femme("BENALI", "FATIMA", "0656789012", "Tanger", sdf.parse("05/02/1978"));
        Femme f6 = new Femme("CHAHIR", "LATIFA", "0667890123", "Agadir", sdf.parse("18/07/1982"));
        Femme f7 = new Femme("DAOUD", "NADIA", "0678901234", "Meknès", sdf.parse("12/09/1972"));
        Femme f8 = new Femme("ELAMI", "HANAE", "0689012345", "Oujda", sdf.parse("30/04/1976"));
        Femme f9 = new Femme("FARID", "SANA", "0690123456", "Tétouan", sdf.parse("08/12/1983"));
        Femme f10 = new Femme("GHALI", "YASMINE", "0612347890", "El Jadida", sdf.parse("22/06/1979"));

        femmeService.create(f1);
        femmeService.create(f2);
        femmeService.create(f3);
        femmeService.create(f4);
        femmeService.create(f5);
        femmeService.create(f6);
        femmeService.create(f7);
        femmeService.create(f8);
        femmeService.create(f9);
        femmeService.create(f10);

        // Créer 5 hommes
        Homme h1 = new Homme("SAFI", "SAID", "0611111111", "Casablanca", sdf.parse("10/01/1960"));
        Homme h2 = new Homme("ALAOUI", "AHMED", "0622222222", "Rabat", sdf.parse("15/03/1965"));
        Homme h3 = new Homme("BENALI", "MOHAMED", "0633333333", "Fès", sdf.parse("20/05/1970"));
        Homme h4 = new Homme("DAOUD", "HASSAN", "0644444444", "Marrakech", sdf.parse("25/07/1955"));
        Homme h5 = new Homme("ELAMI", "OMAR", "0655555555", "Tanger", sdf.parse("30/09/1968"));

        hommeService.create(h1);
        hommeService.create(h2);
        hommeService.create(h3);
        hommeService.create(h4);
        hommeService.create(h5);

        // Créer des mariages pour h1 (SAFI SAID)
        // Mariage échoué
        Mariage m1 = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f4);
        mariageService.create(m1);

        // Mariages en cours
        Mariage m2 = new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f1);
        mariageService.create(m2);

        Mariage m3 = new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f2);
        mariageService.create(m3);

        Mariage m4 = new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f3);
        mariageService.create(m4);

        // Créer des mariages pour h2
        Mariage m5 = new Mariage(sdf.parse("01/01/1990"), null, 2, h2, f5);
        mariageService.create(m5);

        Mariage m6 = new Mariage(sdf.parse("01/01/1995"), null, 1, h2, f6);
        mariageService.create(m6);

        Mariage m7 = new Mariage(sdf.parse("01/01/2000"), null, 3, h2, f7);
        mariageService.create(m7);

        Mariage m8 = new Mariage(sdf.parse("01/01/2005"), null, 2, h2, f8);
        mariageService.create(m8);

        // Créer des mariages pour h3
        Mariage m9 = new Mariage(sdf.parse("01/06/1992"), null, 2, h3, f9);
        mariageService.create(m9);

        Mariage m10 = new Mariage(sdf.parse("01/06/1997"), null, 1, h3, f10);
        mariageService.create(m10);

        System.out.println("10 femmes, 5 hommes et leurs mariages ont été créés.");
    }

    private static void afficherListeFemmes() {
        List<Femme> femmes = femmeService.findAll();
        if (femmes != null && !femmes.isEmpty()) {
            for (Femme f : femmes) {
                System.out.println("- " + f.getNom() + " " + f.getPrenom() + 
                                 " (Née le: " + sdf.format(f.getDateNaissance()) + ")");
            }
        } else {
            System.out.println("Aucune femme trouvée.");
        }
    }

    private static void afficherFemmeLaPlusAgee() {
        Femme femme = femmeService.getFemmeLaPlusAgee();
        if (femme != null) {
            System.out.println("Femme la plus âgée: " + femme.getNom() + " " + femme.getPrenom() + 
                             " (Née le: " + sdf.format(femme.getDateNaissance()) + ")");
        } else {
            System.out.println("Aucune femme trouvée.");
        }
    }

    private static void afficherEpousesHomme(int hommeId) {
        Date dateDebut = new Date(0); // 1 janvier 1970
        Date dateFin = new Date();
        List<Mariage> mariages = hommeService.getEpousesEntreDates(hommeId, dateDebut, dateFin);
        Homme homme = hommeService.findById(hommeId);
        
        if (homme != null) {
            System.out.println("Épouses de " + homme.getNom() + " " + homme.getPrenom() + ":");
            if (mariages != null && !mariages.isEmpty()) {
                for (Mariage m : mariages) {
                    System.out.println("- " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() + 
                                     " (Mariage: " + sdf.format(m.getDateDebut()) + ")");
                }
            } else {
                System.out.println("Aucune épouse trouvée.");
            }
        }
    }

    private static void afficherNombreEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        Long nombreEnfants = femmeService.getNombreEnfantsEntreDates(femmeId, dateDebut, dateFin);
        Femme femme = femmeService.findById(femmeId);
        
        if (femme != null) {
            System.out.println("Nombre d'enfants de " + femme.getNom() + " " + femme.getPrenom() + 
                             " entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ": " + nombreEnfants);
        }
    }

    private static void afficherFemmesMarieesDeuxFoisOuPlus() {
        List<Femme> femmes = femmeService.getFemmesMarieesAuMoinsDeuxFois();
        if (femmes != null && !femmes.isEmpty()) {
            for (Femme f : femmes) {
                System.out.println("- " + f.getNom() + " " + f.getPrenom());
            }
        } else {
            System.out.println("Aucune femme mariée deux fois ou plus trouvée.");
        }
    }

    private static void afficherHommesMariesQuatreFemmes(Date dateDebut, Date dateFin) {
        long nombre = hommeService.getNombreHommesMariesQuatreFemmes(dateDebut, dateFin);
        System.out.println("Nombre d'hommes mariés à quatre femmes entre " + 
                         sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ": " + nombre);
    }
}
