package ma.projet.service;

import java.util.List;

import ma.projet.beans.Mariage;
import ma.projet.dao.MariageDao;

public class MariageService {
    private final MariageDao mariageDao = new MariageDao();

    public boolean create(Mariage o) {
        return mariageDao.create(o);
    }

    public boolean update(Mariage o) {
        return mariageDao.update(o);
    }

    public boolean delete(Mariage o) {
        return mariageDao.delete(o);
    }

    public Mariage findById(int id) {
        return mariageDao.findById(id);
    }

    public List<Mariage> findAll() {
        return mariageDao.findAll();
    }
}
