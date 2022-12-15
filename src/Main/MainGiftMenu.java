package Main;

import Objects.GiftNew;
import Objects.SweetsNew;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainGiftMenu {
    private JButton backButton;
    private JPanel panel;
    private JLabel lable;
    private JList<SweetsNew> sweetsList;
    private JButton addSweetsButton;

    private DefaultListModel<SweetsNew> dataList;
    private JFrame frame;
    private GiftNew gift;

    public MainGiftMenu(BackListener listener, GiftNew gift) {
        dataList = new DefaultListModel<>();
        sweetsList.setModel(dataList);
        lable.setText(gift.getBoxForm());
        this.gift = gift;
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.toBack();
                frame.setVisible(false);
                frame.dispose();
                listener.back();
            }
        });
        addSweetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateSweetDialog();
            }
        });
    }

    void show() {
        frame = new JFrame("App");
        JPanel currentPanel = panel;
        updateListFromDb();
        currentPanel.setPreferredSize(new Dimension(640, 480));
        frame.setContentPane(currentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createSweets(String name, int price, String type) {
        SessionFactory factory;
        factory = new Configuration().addAnnotatedClass(SweetsNew.class).addAnnotatedClass(GiftNew.class).setProperty("hibernate.hdm2ddl.auto", "create-drop").configure().buildSessionFactory();
        Session session = factory.openSession();
        SweetsNew sweets = new SweetsNew(name, price, type);
        sweets.setGift(gift);
        Transaction tx = session.beginTransaction();
        session.persist(sweets);
        tx.commit();
        session.close();
        updateListFromDb();
    }

    private void openCreateSweetDialog() {
        CreateSweetsDialog dialog = new CreateSweetsDialog(new CreateSweetsListener() {
            @Override
            public void onCreate(String name, int price, String type) {
                createSweets(name, price, type);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }

    private void updateListFromDb() {
        SessionFactory factory;
        factory = new Configuration().addAnnotatedClass(SweetsNew.class).addAnnotatedClass(GiftNew.class).setProperty("hibernate.hdm2ddl.auto", "create-drop").configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "select e from sweets e where e.gift.id = :id";
        Query query = session.createQuery(hql).setParameter("id", gift.getId());
        List<SweetsNew> sweets = query.getResultList();
        dataList.clear();
        dataList.addAll(sweets);
        session.close();
    }
}
