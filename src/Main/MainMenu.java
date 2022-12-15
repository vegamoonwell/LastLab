package Main;

import Objects.GiftNew;
import Objects.SweetsNew;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenu {
    private JFrame frame;
    private JList<GiftNew> giftList;
    private JLabel label;
    private JPanel panel;
    private JButton button1;
    private JButton button2;

    DefaultListModel<GiftNew> list;

    public MainMenu() {
        button1.setVisible(false);

        list = new DefaultListModel<GiftNew>();
        giftList.setModel(list);
        giftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        giftList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                button1.setVisible(giftList.getSelectedValue() != null);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateGift dialog = new CreateGift(new CreateGiftListener() {
                    @Override
                    public void onCreateGift(String boxForm) {
                        try {
                            SessionFactory factory;
                            factory = new Configuration().addAnnotatedClass(SweetsNew.class).addAnnotatedClass(GiftNew.class).setProperty("hibernate.hdm2ddl.auto", "create-drop").configure().buildSessionFactory();
                            Session session = factory.openSession();
                            GiftNew gift = new GiftNew(boxForm);
                            Transaction tx = session.beginTransaction();
                            session.persist(gift);
                            tx.commit();
                            String hql = "from gift";
                            Query query = session.createQuery(hql);
                            List<GiftNew> gifts = query.getResultList();
                            list.clear();
                            list.addAll(gifts);
                            session.close();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                });
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GiftNew gift = giftList.getSelectedValue();
                new MainGiftMenu(new BackListener() {
                    @Override
                    public void back() {
                        frame.setVisible(true);
                    }
                }, gift
                ).show();
                frame.setVisible(false);
            }
        });
    }

    public void show() {
        frame = new JFrame("App");
        JPanel currentPanel = panel;
        updateListFromDb();
        currentPanel.setPreferredSize(new Dimension(640, 480));
        frame.setContentPane(currentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void updateListFromDb() {
        SessionFactory factory;
        factory = new Configuration().addAnnotatedClass(SweetsNew.class).addAnnotatedClass(GiftNew.class).setProperty("hibernate.hdm2ddl.auto", "create-drop").configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "select a from gift a";
        TypedQuery query = session.createQuery(hql, GiftNew.class);
        List<GiftNew> gifts = query.getResultList();
        list.clear();
        list.addAll(gifts);
        session.close();
    }
}
