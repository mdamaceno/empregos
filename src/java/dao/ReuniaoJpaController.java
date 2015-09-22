/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Empresa;
import models.Pessoa;
import models.Reuniao;

/**
 *
 * @author Marco
 */
public class ReuniaoJpaController implements Serializable {

    public ReuniaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reuniao reuniao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empresaId = reuniao.getEmpresaId();
            if (empresaId != null) {
                empresaId = em.getReference(empresaId.getClass(), empresaId.getId());
                reuniao.setEmpresaId(empresaId);
            }
            Pessoa pessoaId = reuniao.getPessoaId();
            if (pessoaId != null) {
                pessoaId = em.getReference(pessoaId.getClass(), pessoaId.getId());
                reuniao.setPessoaId(pessoaId);
            }
            em.persist(reuniao);
            if (empresaId != null) {
                empresaId.getReuniaoList().add(reuniao);
                empresaId = em.merge(empresaId);
            }
            if (pessoaId != null) {
                pessoaId.getReuniaoList().add(reuniao);
                pessoaId = em.merge(pessoaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reuniao reuniao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reuniao persistentReuniao = em.find(Reuniao.class, reuniao.getId());
            Empresa empresaIdOld = persistentReuniao.getEmpresaId();
            Empresa empresaIdNew = reuniao.getEmpresaId();
            Pessoa pessoaIdOld = persistentReuniao.getPessoaId();
            Pessoa pessoaIdNew = reuniao.getPessoaId();
            if (empresaIdNew != null) {
                empresaIdNew = em.getReference(empresaIdNew.getClass(), empresaIdNew.getId());
                reuniao.setEmpresaId(empresaIdNew);
            }
            if (pessoaIdNew != null) {
                pessoaIdNew = em.getReference(pessoaIdNew.getClass(), pessoaIdNew.getId());
                reuniao.setPessoaId(pessoaIdNew);
            }
            reuniao = em.merge(reuniao);
            if (empresaIdOld != null && !empresaIdOld.equals(empresaIdNew)) {
                empresaIdOld.getReuniaoList().remove(reuniao);
                empresaIdOld = em.merge(empresaIdOld);
            }
            if (empresaIdNew != null && !empresaIdNew.equals(empresaIdOld)) {
                empresaIdNew.getReuniaoList().add(reuniao);
                empresaIdNew = em.merge(empresaIdNew);
            }
            if (pessoaIdOld != null && !pessoaIdOld.equals(pessoaIdNew)) {
                pessoaIdOld.getReuniaoList().remove(reuniao);
                pessoaIdOld = em.merge(pessoaIdOld);
            }
            if (pessoaIdNew != null && !pessoaIdNew.equals(pessoaIdOld)) {
                pessoaIdNew.getReuniaoList().add(reuniao);
                pessoaIdNew = em.merge(pessoaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reuniao.getId();
                if (findReuniao(id) == null) {
                    throw new NonexistentEntityException("The reuniao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reuniao reuniao;
            try {
                reuniao = em.getReference(Reuniao.class, id);
                reuniao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reuniao with id " + id + " no longer exists.", enfe);
            }
            Empresa empresaId = reuniao.getEmpresaId();
            if (empresaId != null) {
                empresaId.getReuniaoList().remove(reuniao);
                empresaId = em.merge(empresaId);
            }
            Pessoa pessoaId = reuniao.getPessoaId();
            if (pessoaId != null) {
                pessoaId.getReuniaoList().remove(reuniao);
                pessoaId = em.merge(pessoaId);
            }
            em.remove(reuniao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reuniao> findReuniaoEntities() {
        return findReuniaoEntities(true, -1, -1);
    }

    public List<Reuniao> findReuniaoEntities(int maxResults, int firstResult) {
        return findReuniaoEntities(false, maxResults, firstResult);
    }

    private List<Reuniao> findReuniaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reuniao.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Reuniao findReuniao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reuniao.class, id);
        } finally {
            em.close();
        }
    }

    public int getReuniaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reuniao> rt = cq.from(Reuniao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
