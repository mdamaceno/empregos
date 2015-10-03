/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Reuniao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import models.Empresa;

/**
 *
 * @author Marco
 */
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) {
        if (empresa.getReuniaoList() == null) {
            empresa.setReuniaoList(new ArrayList<Reuniao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reuniao> attachedReuniaoList = new ArrayList<Reuniao>();
            for (Reuniao reuniaoListReuniaoToAttach : empresa.getReuniaoList()) {
                reuniaoListReuniaoToAttach = em.getReference(reuniaoListReuniaoToAttach.getClass(), reuniaoListReuniaoToAttach.getId());
                attachedReuniaoList.add(reuniaoListReuniaoToAttach);
            }
            empresa.setReuniaoList(attachedReuniaoList);
            em.persist(empresa);
            for (Reuniao reuniaoListReuniao : empresa.getReuniaoList()) {
                Empresa oldEmpresaIdOfReuniaoListReuniao = reuniaoListReuniao.getEmpresaId();
                reuniaoListReuniao.setEmpresaId(empresa);
                reuniaoListReuniao = em.merge(reuniaoListReuniao);
                if (oldEmpresaIdOfReuniaoListReuniao != null) {
                    oldEmpresaIdOfReuniaoListReuniao.getReuniaoList().remove(reuniaoListReuniao);
                    oldEmpresaIdOfReuniaoListReuniao = em.merge(oldEmpresaIdOfReuniaoListReuniao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getId());
            List<Reuniao> reuniaoListOld = persistentEmpresa.getReuniaoList();
            List<Reuniao> reuniaoListNew = empresa.getReuniaoList();
            List<String> illegalOrphanMessages = null;
            for (Reuniao reuniaoListOldReuniao : reuniaoListOld) {
                if (!reuniaoListNew.contains(reuniaoListOldReuniao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reuniao " + reuniaoListOldReuniao + " since its empresaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Reuniao> attachedReuniaoListNew = new ArrayList<Reuniao>();
            for (Reuniao reuniaoListNewReuniaoToAttach : reuniaoListNew) {
                reuniaoListNewReuniaoToAttach = em.getReference(reuniaoListNewReuniaoToAttach.getClass(), reuniaoListNewReuniaoToAttach.getId());
                attachedReuniaoListNew.add(reuniaoListNewReuniaoToAttach);
            }
            reuniaoListNew = attachedReuniaoListNew;
            empresa.setReuniaoList(reuniaoListNew);
            empresa = em.merge(empresa);
            for (Reuniao reuniaoListNewReuniao : reuniaoListNew) {
                if (!reuniaoListOld.contains(reuniaoListNewReuniao)) {
                    Empresa oldEmpresaIdOfReuniaoListNewReuniao = reuniaoListNewReuniao.getEmpresaId();
                    reuniaoListNewReuniao.setEmpresaId(empresa);
                    reuniaoListNewReuniao = em.merge(reuniaoListNewReuniao);
                    if (oldEmpresaIdOfReuniaoListNewReuniao != null && !oldEmpresaIdOfReuniaoListNewReuniao.equals(empresa)) {
                        oldEmpresaIdOfReuniaoListNewReuniao.getReuniaoList().remove(reuniaoListNewReuniao);
                        oldEmpresaIdOfReuniaoListNewReuniao = em.merge(oldEmpresaIdOfReuniaoListNewReuniao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empresa.getId();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Reuniao> reuniaoListOrphanCheck = empresa.getReuniaoList();
            for (Reuniao reuniaoListOrphanCheckReuniao : reuniaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Reuniao " + reuniaoListOrphanCheckReuniao + " in its reuniaoList field has a non-nullable empresaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Empresa getEmpresaByCnpjAndPassowrd(String cnpj, String password) {
        Query qry = getEntityManager().createNamedQuery("Empresa.findByCnpjAndPassword");
        
        qry.setParameter("doc", cnpj);
        qry.setParameter("pass", password);
        
        try {
            return (Empresa) qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch(NonUniqueResultException e){
            return (Empresa) qry.getResultList().get(0);
        }
    }
    
}
