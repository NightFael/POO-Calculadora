package Javacalculadora;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class CalculadoraRafael {

	private JFrame frmRafael;
	private JTextField JTFResultado;

	private double primeiroValor = 0;
	private double segundoValor = 0;
	private String operacao = "";

	// Paleta de cores
	private final Color COR_FUNDO     = new Color(129, 111, 206);
	private final Color COR_BOTAO     = new Color(160, 145, 230);
	private final Color COR_OPERADOR  = new Color(95, 80, 160);
	private final Color COR_IGUAL     = new Color(60, 200, 120);
	private final Color COR_LIMPAR    = new Color(220, 70, 70);
	private final Color COR_TEXTO     = Color.WHITE;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculadoraRafael window = new CalculadoraRafael();
					window.frmRafael.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CalculadoraRafael() {
		initialize();
	}

	// ============ MÉTODOS OBRIGATÓRIOS ============
	public double adicao(double a, double b) { return a + b; }
	public double subtracao(double a, double b) { return a - b; }
	public double multiplicacao(double a, double b) { return a * b; }
	public double divisao(double a, double b) {
		if (b == 0) throw new ArithmeticException("Divisão por zero");
		return a / b;
	}
	public double potencia(double base, double expoente) { return Math.pow(base, expoente); }
	

	class BotaoDestaque extends JButton {
		private Color corBase;
		private Color corHover;
		private Color corPress;
		private boolean hover = false;
		private boolean press = false;

		public BotaoDestaque(String texto, Color corBase) {
			super(texto);
			this.corBase = corBase;
			this.corHover = clarear(corBase, 0.15f);
			this.corPress = escurecer(corBase, 0.20f);
			setContentAreaFilled(false);
			setBorderPainted(false);
			setFocusPainted(false);
			setOpaque(false);
			setForeground(COR_TEXTO);
			setFont(new Font("Arial", Font.BOLD, 40));
			setBorder(new EmptyBorder(5, 5, 5, 5));

			addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent e) { hover = true; repaint(); }
				public void mouseExited(java.awt.event.MouseEvent e)  { hover = false; press = false; repaint(); }
				public void mousePressed(java.awt.event.MouseEvent e) { press = true; repaint(); }
				public void mouseReleased(java.awt.event.MouseEvent e){ press = false; repaint(); }
			});
		}

		private Color clarear(Color c, float f) {
			int r = Math.min(255, (int)(c.getRed()   + 255 * f));
			int g = Math.min(255, (int)(c.getGreen() + 255 * f));
			int b = Math.min(255, (int)(c.getBlue()  + 255 * f));
			return new Color(r, g, b);
		}

		private Color escurecer(Color c, float f) {
			int r = Math.max(0, (int)(c.getRed()   * (1 - f)));
			int g = Math.max(0, (int)(c.getGreen() * (1 - f)));
			int b = Math.max(0, (int)(c.getBlue()  * (1 - f)));
			return new Color(r, g, b);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Color atual = press ? corPress : (hover ? corHover : corBase);

			g2.setColor(new Color(0, 0, 0, 60));
			g2.fillRoundRect(3, 4, getWidth() - 4, getHeight() - 4, 18, 18);

			g2.setColor(atual);
			g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 18, 18);

			g2.setColor(new Color(255, 255, 255, 40));
			g2.fillRoundRect(2, 2, getWidth() - 8, (getHeight() - 4) / 2, 16, 16);

			g2.dispose();
			super.paintComponent(g);
		}
	}

	private BotaoDestaque criarBotao(String texto, int x, int y, int w, int h, Color fundo, ActionListener acao) {
		BotaoDestaque btn = new BotaoDestaque(texto, fundo);
		btn.setBounds(x, y, w, h);
		btn.addActionListener(acao);
		frmRafael.getContentPane().add(btn);
		return btn;
	}

	private void initialize() {
		frmRafael = new JFrame();
		frmRafael.setTitle("FORM - Rafael Neris Sampaio Ferreira - RA 0025154");
		frmRafael.setBounds(100, 100, 450, 550);
		frmRafael.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRafael.getContentPane().setBackground(COR_FUNDO);
		frmRafael.getContentPane().setLayout(null);

		JTFResultado = new JTextField();
		JTFResultado.setFont(new Font("Arial Black", Font.BOLD, 45));
		JTFResultado.setBounds(10, 24, 414, 54);
		JTFResultado.setBackground(new Color(40, 30, 80));
		JTFResultado.setForeground(new Color(0, 255, 160));
		JTFResultado.setHorizontalAlignment(JTextField.RIGHT);
		JTFResultado.setEditable(false);
		frmRafael.getContentPane().add(JTFResultado);

		ActionListener num = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				JTFResultado.setText(JTFResultado.getText() + b.getText());
			}
		};

		ActionListener op = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!JTFResultado.getText().isEmpty()) {
					primeiroValor = Double.parseDouble(JTFResultado.getText());
					String t = ((JButton) e.getSource()).getText();
					if (t.equals("+")) operacao = "Soma";
					else if (t.equals("-")) operacao = "Subtracao";
					else if (t.equals("*")) operacao = "Multiplicacao";
					else if (t.equals("/")) operacao = "Divisao";
					else if (t.equals("^")) operacao = "Potencia";
					JTFResultado.setText("");
				}
			}
		};

		criarBotao("7", 0, 264, 62, 43, COR_BOTAO, num);
		criarBotao("8", 82, 264, 62, 43, COR_BOTAO, num);
		criarBotao("9", 154, 264, 62, 43, COR_BOTAO, num);
		criarBotao("*", 303, 264, 62, 43, COR_OPERADOR, op);

		criarBotao("4", 0, 327, 62, 43, COR_BOTAO, num);
		criarBotao("5", 82, 327, 62, 43, COR_BOTAO, num);
		criarBotao("6", 154, 327, 62, 43, COR_BOTAO, num);
		criarBotao("-", 303, 327, 62, 43, COR_OPERADOR, op);

		criarBotao("1", 0, 390, 62, 43, COR_BOTAO, num);
		criarBotao("2", 82, 390, 62, 43, COR_BOTAO, num);
		criarBotao("3", 154, 390, 62, 43, COR_BOTAO, num);
		criarBotao("+", 303, 390, 62, 43, COR_OPERADOR, op);

		criarBotao(",", 154, 444, 62, 43, COR_BOTAO, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!JTFResultado.getText().contains(".")) {
					JTFResultado.setText(JTFResultado.getText() + ".");
				}
			}
		});
		criarBotao("0", 82, 444, 62, 43, COR_BOTAO, num);

		criarBotao("c", 0, 199, 62, 43, COR_LIMPAR, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTFResultado.setText("");
				primeiroValor = 0;
				segundoValor = 0;
				operacao = "";
			}
		});
		criarBotao("^", 225, 199, 62, 43, COR_OPERADOR, op);
		criarBotao("/", 303, 199, 62, 43, COR_OPERADOR, op);

		criarBotao("=", 303, 444, 62, 43, COR_IGUAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!JTFResultado.getText().isEmpty()) {
					try {
						segundoValor = Double.parseDouble(JTFResultado.getText());
						double resultado = 0;
						if ("Soma".equals(operacao)) resultado = adicao(primeiroValor, segundoValor);
						else if ("Subtracao".equals(operacao)) resultado = subtracao(primeiroValor, segundoValor);
						else if ("Multiplicacao".equals(operacao)) resultado = multiplicacao(primeiroValor, segundoValor);
						else if ("Divisao".equals(operacao)) resultado = divisao(primeiroValor, segundoValor);
						else if ("Potencia".equals(operacao)) resultado = potencia(primeiroValor, segundoValor);
						else return;
						JTFResultado.setText(String.valueOf(resultado));
						operacao = "";
					} catch (ArithmeticException | NumberFormatException ex) {
						JTFResultado.setText("Erro");
					}
				}
			}
		});
	}
}