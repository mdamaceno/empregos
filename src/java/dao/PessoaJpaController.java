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
import models.Pessoa;

/**
 *
 * @author Marco
 */
public class PessoaJpaController implements Serializable {

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa pessoa) {
        if (pessoa.getReuniaoList() == null) {
            pessoa.setReuniaoList(new ArrayList<Reuniao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reuniao> attachedReuniaoList = new ArrayList<Reuniao>();
            for (Reuniao reuniaoListReuniaoToAttach : pessoa.getReuniaoList()) {
                reuniaoListReuniaoToAttach = em.getReference(reuniaoListReuniaoToAttach.getClass(), reuniaoListReuniaoToAttach.getId());
                attachedReuniaoList.add(reuniaoListReuniaoToAttach);
            }
            pessoa.setReuniaoList(attachedReuniaoList);
            em.persist(pessoa);
            for (Reuniao reuniaoListReuniao : pessoa.getReuniaoList()) {
                Pessoa oldPessoaIdOfReuniaoListReuniao = reuniaoListReuniao.getPessoaId();
                reuniaoListReuniao.setPessoaId(pessoa);
                reuniaoListReuniao = em.merge(reuniaoListReuniao);
                if (oldPessoaIdOfReuniaoListReuniao != null) {
                    oldPessoaIdOfReuniaoListReuniao.getReuniaoList().remove(reuniaoListReuniao);
                    oldPessoaIdOfReuniaoListReuniao = em.merge(oldPessoaIdOfReuniaoListReuniao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoa pessoa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa persistentPessoa = em.find(Pessoa.class, pessoa.getId());
            List<Reuniao> reuniaoListOld = persistentPessoa.getReuniaoList();
            List<Reuniao> reuniaoListNew = pessoa.getReuniaoList();
            List<String> illegalOrphanMessages = null;
            for (Reuniao reuniaoListOldReuniao : reuniaoListOld) {
                if (!reuniaoListNew.contains(reuniaoListOldReuniao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reuniao " + reuniaoListOldReuniao + " since its pessoaId field is not nullable.");
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
            pessoa.setReuniaoList(reuniaoListNew);
            pessoa = em.merge(pessoa);
            for (Reuniao reuniaoListNewReuniao : reuniaoListNew) {
                if (!reuniaoListOld.contains(reuniaoListNewReuniao)) {
                    Pessoa oldPessoaIdOfReuniaoListNewReuniao = reuniaoListNewReuniao.getPessoaId();
                    reuniaoListNewReuniao.setPessoaId(pessoa);
                    reuniaoListNewReuniao = em.merge(reuniaoListNewReuniao);
                    if (oldPessoaIdOfReuniaoListNewReuniao != null && !oldPessoaIdOfReuniaoListNewReuniao.equals(pessoa)) {
                        oldPessoaIdOfReuniaoListNewReuniao.getReuniaoList().remove(reuniaoListNewReuniao);
                        oldPessoaIdOfReuniaoListNewReuniao = em.merge(oldPessoaIdOfReuniaoListNewReuniao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoa.getId();
                if (findPessoa(id) == null) {
                    throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.");
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
            Pessoa pessoa;
            try {
                pessoa = em.getReference(Pessoa.class, id);
                pessoa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Reuniao> reuniaoListOrphanCheck = pessoa.getReuniaoList();
            for (Reuniao reuniaoListOrphanCheckReuniao : reuniaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pessoa (" + pessoa + ") cannot be destroyed since the Reuniao " + reuniaoListOrphanCheckReuniao + " in its reuniaoList field has a non-nullable pessoaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoa> findPessoaEntities() {
        return findPessoaEntities(true, -1, -1);
    }

    public List<Pessoa> findPessoaEntities(int maxResults, int firstResult) {
        return findPessoaEntities(false, maxResults, firstResult);
    }

    private List<Pessoa> findPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa.class));
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

    public Pessoa findPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoa> rt = cq.from(Pessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Pessoa getPessoaByLoginAndPassword(String email, String password) {
        Query qry = getEntityManager().createNamedQuery("Pessoa.findByLoginAndPassword");

        qry.setParameter("ema", email);
        qry.setParameter("pass", password);

        try {
            return (Pessoa) qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return (Pessoa) qry.getResultList().get(0);
        }
    }

    public List<Pessoa> getPessoaByFuncao(int funcao) {

        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa.class));

            Query q = getEntityManager().createNamedQuery("Pessoa.findByFuncao");

            q.setParameter("func1", funcao);
            q.setParameter("func2", funcao);
            q.setParameter("func3", funcao);

            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
