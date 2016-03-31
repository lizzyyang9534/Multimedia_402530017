import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class calculator extends JFrame {
	private static JLabel label;
	private static JLabel label1; 
	private static JButton digits[] = new JButton[10];
	private static GridBagConstraints c[] = new GridBagConstraints[10];
	private static JButton point, add, substract, multiply, divide, equal,
			clean, back;
	private static double num;
	private static double num2;
	private static byte op;// operator
	private static byte error = 0;// error or not
	private static boolean end = false;
	private static final int DEF_DIV_SCALE = 10;

	public calculator() {
		label = new JLabel("0");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(200, 25));
		label1 = new JLabel("");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setPreferredSize(new Dimension(200, 25));

		JPanel panel = new JPanel();
		Border border = BorderFactory.createLineBorder(Color.gray);
		panel.setBorder(border);
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(220, 60));
		panel.add(label1);
		panel.add(label);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());

		for (int i = 1; i <= 9; i++) {
			digits[i] = new JButton(Integer.toString(i));
			c[i] = new GridBagConstraints();
			if (i <= 3) {
				c[i].gridx = i - 1;
				c[i].gridy = 3;
			} else if (i <= 6) {
				c[i].gridx = i - 4;
				c[i].gridy = 2;
			} else if (i <= 9) {
				c[i].gridx = i - 7;
				c[i].gridy = 1;
			}
			c[i].fill = GridBagConstraints.BOTH;
			panel1.add(digits[i], c[i]);
			digits[i].addActionListener(new ButtonHandeler());
		}

		digits[0] = new JButton("0");
		c[0] = new GridBagConstraints();
		c[0].gridx = 0;
		c[0].gridy = 4;
		c[0].gridwidth = 2;
		c[0].fill = GridBagConstraints.BOTH;
		digits[0].addActionListener(new ButtonHandeler());

		point = new JButton(".");
		GridBagConstraints c_point = new GridBagConstraints();
		c_point.gridx = 2;
		c_point.gridy = 4;
		c_point.fill = GridBagConstraints.BOTH;
		point.addActionListener(new ButtonHandeler());

		add = new JButton("+");
		GridBagConstraints c_plus = new GridBagConstraints();
		c_plus.gridx = 3;
		c_plus.gridy = 4;
		c_plus.fill = GridBagConstraints.BOTH;
		add.addActionListener(new ButtonHandeler());

		substract = new JButton("-");
		GridBagConstraints c_minus = new GridBagConstraints();
		c_minus.gridx = 3;
		c_minus.gridy = 3;
		c_minus.fill = GridBagConstraints.BOTH;
		substract.addActionListener(new ButtonHandeler());

		multiply = new JButton("*");
		GridBagConstraints c_times = new GridBagConstraints();
		c_times.gridx = 3;
		c_times.gridy = 2;
		c_times.fill = GridBagConstraints.BOTH;
		multiply.addActionListener(new ButtonHandeler());

		divide = new JButton("/");
		GridBagConstraints c_divide = new GridBagConstraints();
		c_divide.gridx = 3;
		c_divide.gridy = 1;
		c_divide.fill = GridBagConstraints.BOTH;
		divide.addActionListener(new ButtonHandeler());

		clean = new JButton("C");
		GridBagConstraints c_c = new GridBagConstraints();
		c_c.gridx = 4;
		c_c.gridy = 2;
		c_c.fill = GridBagConstraints.BOTH;
		clean.addActionListener(new ButtonHandeler());

		back = new JButton("<==");
		GridBagConstraints c_back = new GridBagConstraints();
		c_back.gridx = 4;
		c_back.gridy = 1;
		c_back.fill = GridBagConstraints.BOTH;
		back.addActionListener(new ButtonHandeler());

		equal = new JButton("=");
		GridBagConstraints c_equal = new GridBagConstraints();
		c_equal.gridx = 4;
		c_equal.gridy = 3;
		c_equal.gridheight = 2;
		c_equal.fill = GridBagConstraints.BOTH;
		equal.addActionListener(new ButtonHandeler());

		panel1.add(digits[0], c[0]);
		panel1.add(point, c_point);
		panel1.add(add, c_plus);
		panel1.add(substract, c_minus);
		panel1.add(multiply, c_times);
		panel1.add(divide, c_divide);
		panel1.add(equal, c_equal);
		panel1.add(clean, c_c);
		panel1.add(back, c_back);

		JFrame f = new JFrame();
		f.setLayout(new FlowLayout());
		f.setVisible(true);
		f.setSize(250, 230);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(panel);
		f.add(panel1);
	}

	private class ButtonHandeler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();

			// print 1~9
			for (int i = 0; i <= 9; i++) {
				if (button == digits[i]) {
					output_digit(digits[i]);
					break;
				}
			}
			if (button == clean) {
				num = 0;
				num2 = 0;
				op = 0;
				error = 0;
				label.setText("0");
				label1.setText("");
			} else if (button == back) {// backspace
				label.setText(""
						+ label.getText().substring(0,
								label.getText().length() - 1));
				if (label.getText().length() == 0)
					label.setText("0");
			} else if (button == point) {// point
				if (label.getText().contains(".") == false)
					label.setText("" + label.getText() + ".");
			} else if (button == add) {// add
				operation(op);
				if (error == 0) {
					label1.setText(num + " + ");
					label.setText("0");
					op = 1;
				} else
					error_message();
			} else if (button == substract) {// substract
				operation(op);
				if (error == 0) {
					label1.setText(num + " - ");
					label.setText("0");
					op = 2;
				} else
					error_message();
			} else if (button == multiply) {// multiply
				operation(op);
				if (error == 0) {
					label1.setText(num + " * ");
					label.setText("0");
					op = 3;
				} else
					error_message();
			} else if (button == divide) {// divide
				operation(op);
				if (error == 0) {
					label1.setText(num + " / ");
					label.setText("0");
					op = 4;
				} else
					error_message();
			} else if (button == equal) {
				operation(op);
				if (error == 0) {
					label.setText(Double.toString(num));
					label1.setText("");
					num = 0;
					num2 = 0;
					op = 0;
					end = true;
					error = 0;
				} else
					error_message();
			}
		}

		// print1~9
		private void output_digit(JButton button) {
			if (label.getText() == "0" || end == true) {
				label.setText(button.getText());
				end = false;
			} else {
				if (label.getText().length() >= 28) {
				} else
					label.setText(label.getText() + button.getText());
			}
		}

		// do operation
		private void operation(byte op) throws ArithmeticException {
			if (label.getText() != "Error")
				num2 = Double.parseDouble(label.getText());
			else {
				num = 0;
				num2 = 0;
			}

			BigDecimal b1 = new BigDecimal(Double.toString(num));
			BigDecimal b2 = new BigDecimal(Double.toString(num2));

			try {
				switch (op) {
				case 1:
					num = b1.add(b2).doubleValue();
					break;
				case 2:
					num = b1.subtract(b2).doubleValue();
					break;
				case 3:
					num = b1.multiply(b2).doubleValue();
					break;
				case 4:
					num = b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					break;
				default:
					num = num2;
				}
			} catch (ArithmeticException ae) {
				System.out.println(ae);
				error = 1;
			}
		}

		private void error_message() {
			label.setText("Error");
			label1.setText("");
			num = 0;
			num2 = 0;
			op = 0;
			end = true;
			error = 0;
		}
	}

	public static void main(String[] args) {
		calculator calculator1 = new calculator();
	}
}