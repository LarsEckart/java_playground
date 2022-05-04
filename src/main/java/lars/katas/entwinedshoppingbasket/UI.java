package lars.katas.entwinedshoppingbasket;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// from https://github.com/AndyStewart/EntwinedShoppingBasket
public class UI {

  private JFrame frame;
  JButton addCheese;
  JButton addScrewdriver;
  JTextField poctext;
  JTextField exptext;
  JTextField postext;
  JTextField tottext;
  private ShoppingCart _shoppingCart;

  public UI() {
    frame = new JFrame();
    initialiseForm();
    _shoppingCart = new ShoppingCart(this);
  }

  public void display() {
    frame.setVisible(true);
  }

  private void initialiseForm() {
    frame.setLayout(new GridBagLayout());
    JPanel form = new JPanel();
    GroupLayout layout = new GroupLayout(form);
    form.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    JLabel poclabel = new JLabel("Price of cheese");
    poctext = new JTextField("0", 5);
    JLabel explabel = new JLabel("Expiry date");
    exptext = new JTextField(10);
    addCheese = new JButton();
    JLabel poslabel = new JLabel("Price of screwdriver");
    postext = new JTextField("0", 5);
    addScrewdriver = new JButton();
    JLabel totlabel = new JLabel("Total");
    tottext = new JTextField("0", 5);

    GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
    hGroup.addGroup(layout.createParallelGroup()
        .addComponent(poclabel)
        .addComponent(poslabel)
        .addComponent(totlabel));
    hGroup.addGroup(layout.createParallelGroup()
        .addComponent(poctext)
        .addComponent(postext)
        .addComponent(tottext));
    hGroup.addGroup(layout.createParallelGroup()
        .addComponent(explabel));
    hGroup.addGroup(layout.createParallelGroup()
        .addComponent(exptext));
    hGroup.addGroup(layout.createParallelGroup()
        .addComponent(addCheese)
        .addComponent(addScrewdriver));
    layout.setHorizontalGroup(hGroup);

    GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
    vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        .addComponent(poclabel)
        .addComponent(poctext)
        .addComponent(explabel)
        .addComponent(exptext)
        .addComponent(addCheese));
    vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        .addComponent(poslabel)
        .addComponent(postext)
        .addComponent(addScrewdriver));
    vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        .addComponent(totlabel)
        .addComponent(tottext));
    layout.setVerticalGroup(vGroup);

    frame.add(form);
    frame.setSize(600, 300);
  }

  private class ShoppingCart
  {
    private UI _form1;
    private List<Product> _products = new ArrayList<Product>();

    public ShoppingCart(UI form1)
    {
      _form1 = form1;
      _form1.addCheese.setAction(new AbstractAction("Add") {
        public void actionPerformed(ActionEvent e) {
          AddCheeseAndDisplay(e);
        }
      });
      _form1.addScrewdriver.setAction(new AbstractAction("Add") {
        public void actionPerformed(ActionEvent e) {
          AddScrewDriverAndUpdate(e);
        }
      });
    }

    private void AddScrewDriverAndUpdate(ActionEvent e)
    {
      _products.add(new Screwdriver(Integer.parseInt(_form1.postext.getText())));
      int total = 0;
      for (int i = 0; i < _products.size(); i++)
        total += _products.get(i).price();
      _form1.tottext.setText("" + total);
    }

    private void AddCheeseAndDisplay(ActionEvent e)
    {
      _products.add(new Cheese(Integer.parseInt(_form1.poctext.getText()), _form1.exptext.getText()));
      int tot = 0;
      Iterator<Product> iter = _products.iterator();
      while (iter.hasNext()) {
        tot += iter.next().price();
      }
      _form1.tottext.setText(Integer.toString(tot));
    }
  }

  class Screwdriver implements Product
  {
    private int _price;

    public Screwdriver(int parse)
    {
      _price = parse;
    }

    @Override
    public int price() {
      return _price;
    }
  }

  class Cheese implements Product
  {
    private int _price;

    public Cheese(int p, String text)
    {
      _price = p;
    }

    @Override
    public int price() {
      return _price;
    }
  }

  interface Product
  {
    int price();
  }

}
